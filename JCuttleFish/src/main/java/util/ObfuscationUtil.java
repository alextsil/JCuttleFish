package util;

import obfuscations.layoutobfuscation.RenameNodeIdentifiers;
import org.eclipse.jdt.core.dom.*;
import pojo.UnitNode;
import providers.ObfuscatedNamesProvider;
import util.enums.ObfuscatedNamesVariations;

import java.util.*;

import static java.util.stream.Collectors.toList;


public class ObfuscationUtil
{

    public static void obfuscateMethodDeclaredVariables ( UnitNode unitNode, MethodDeclaration methodDeclaration )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.METHOD_LOCAL_VARS );

        Collection<List<ASTNode>> ASTNodeListCollection = unitNode.getCollectedNodesGroupedByIdentifier().values();

        List<VariableDeclarationStatement> methodDeclaredVarDeclStatements = ASTNodeListCollection.stream()
                .flatMap( List::stream )
                .filter( node -> node instanceof VariableDeclarationStatement )
                .map( VariableDeclarationStatement.class::cast )
                .filter( node -> {
                    VariableDeclarationFragment vdf = ( VariableDeclarationFragment )node.fragments().get( 0 );
                    Optional<IVariableBinding> oivb = OptionalUtils.getIVariableBinding( vdf );
                    if ( oivb.isPresent() )
                    {
                        if ( oivb.get().getDeclaringMethod().isEqualTo( methodDeclaration.resolveBinding() ) )
                        {
                            return true;
                        }
                    }
                    return false;
                } )
                .sorted( ( vds1, vds2 ) -> vds1.getStartPosition() - vds2.getStartPosition() )
                .collect( toList() );

        methodDeclaredVarDeclStatements.stream()
                .map( VariableDeclarationStatement.class::cast )
                .forEach( vds ->
                {
                    VariableDeclarationFragment f = ( VariableDeclarationFragment )vds.fragments().get( 0 );
                    SimpleName sn = f.getName();
                    IVariableBinding fragIvb = OptionalUtils.getIVariableBinding( sn ).get();

                    unitNode.getCollectedNodesGroupedByIdentifier().getOrDefault( sn.getIdentifier(), Collections.emptyList() )
                            .stream()
                            .filter( occurence -> occurence instanceof SimpleName )
                            .map( SimpleName.class::cast )
                            .filter( foundsn -> OptionalUtils.getIVariableBinding( foundsn ).isPresent() )
                            .filter( foundsn -> OptionalUtils.getIVariableBinding( foundsn ).get().isEqualTo( fragIvb ) )
                            .forEachOrdered( foundsn -> foundsn.setIdentifier( obfuscatedVariableNames.peekFirst() ) );
                    sn.setIdentifier( obfuscatedVariableNames.poll() );
                } );
    }

    public static void obfuscateMethodParameters ( UnitNode unitNode, MethodDeclaration methodDeclaration )
    {
        List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.METHOD_PARAMETERS );

        parameters.stream().forEach( p -> {
            //find occurences and replace
            IVariableBinding paramIvb = OptionalUtils.getIVariableBinding( p ).get();

            unitNode.getCollectedNodesGroupedByIdentifier().getOrDefault( p.getName().getIdentifier(), Collections.emptyList() )
                    .stream()
                    .filter( occurence -> occurence instanceof SimpleName )
                    .map( SimpleName.class::cast )
                    .filter( sn -> OptionalUtils.getIVariableBinding( sn ).isPresent() )
                    .filter( sn -> OptionalUtils.getIVariableBinding( sn ).get().isParameter() )
                    .filter( sn -> OptionalUtils.getIVariableBinding( sn ).get().isEqualTo( paramIvb ) )
                    .forEach( sn -> sn.setIdentifier( obfuscatedVariableNames.peekFirst() ) );

            //rename param on method declaration
            p.setName( unitNode.getUnitSource().getCompilationUnit().getAST()
                    .newSimpleName( obfuscatedVariableNames.poll() ) );
        } );
    }

    public static void obfuscateClassLocalVarsAndReferences ( UnitNode unitNode )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        List<SimpleName> classLocalFields = ConvenienceWrappers.getPrivateFieldDeclarations( ( TypeDeclaration )unitNode.getUnitSource()
                .getCompilationUnit().types().get( 0 ) )
                .stream()
                .map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                .map( VariableDeclaration::getName )
                .collect( toList() );

        classLocalFields.stream().forEach( clf ->
                {
                    unitNode.getCollectedNodesGroupedByIdentifier().get( clf.getIdentifier() )
                            .stream().forEach( item -> {
                        if ( item instanceof SimpleName )
                        {
                            SimpleName simpleName = ( SimpleName )item;
                            Optional<IVariableBinding> ivb = OptionalUtils.getIVariableBinding( simpleName );
                            if ( ivb.isPresent() )
                            {
                                if ( ivb.get().isField() )
                                {
                                    RenameNodeIdentifiers.renameSimpleName( simpleName, obfuscatedVariableNames.peekFirst() );
                                }
                            }
                        } else if ( item instanceof FieldAccess )
                        {
                            FieldAccess fieldAccess = ( FieldAccess )item;
                            RenameNodeIdentifiers.renameFieldAccessName( fieldAccess, obfuscatedVariableNames.peekFirst() );
                        } else if ( item instanceof FieldDeclaration )
                        {
                            FieldDeclaration fieldDeclaration = ( FieldDeclaration )item;
                            RenameNodeIdentifiers.renameFieldDeclaration( fieldDeclaration, obfuscatedVariableNames.peekFirst() );
                        }
                    } );
                    obfuscatedVariableNames.poll();
                }
        );
    }

    public static void obfuscateClassNames ( Collection<UnitNode> unitNodes )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( us -> us.getCompilationUnit().types().get( 0 ) )
                .forEach( td -> renameClassAndReferences( ( TypeDeclaration )td, obfuscatedVariableNames.pollFirst(), unitNodes ) );
    }

    private static void renameClassAndReferences ( TypeDeclaration typeDeclaration, String obfuscatedName, Collection<UnitNode> unitNodes )
    {
        SimpleName className = typeDeclaration.getName();

        Map<Class<? extends ASTNode>, List<ASTNode>> collectedNodes =
                unitNodes.stream().findFirst().get().getCollectedNodesGroupedByClass();

        List<ASTNode> fieldDeclarationRefs = collectedNodes.get( FieldDeclaration.class );

        FieldDeclaration fd1 = ( FieldDeclaration )fieldDeclarationRefs.get( 0 );
        FieldDeclaration fd2 = ( FieldDeclaration )fieldDeclarationRefs.get( 1 );

        fieldDeclarationRefs.stream()
                .map( FieldDeclaration.class::cast )
                .forEach( fd -> RenameNodeIdentifiers.renameTypeIdentifiers( fd.getType(), obfuscatedName ) );

        typeDeclaration.getName().setIdentifier( obfuscatedName );
    }

}
