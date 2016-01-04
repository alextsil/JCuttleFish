package util;

import obfuscations.callbacks.AstNodeFoundCallback;
import obfuscations.callbacks.RenameTypesCallback;
import obfuscations.visitors.FieldDeclarationVisitor;
import obfuscations.visitors.MethodDeclarationVisitor;
import obfuscations.visitors.TypeDeclarationVisitor;
import obfuscations.visitors.TypeVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import providers.ObfuscatedNamesProvider;
import util.enums.ObfuscatedNamesVariations;

import java.util.*;

import static java.util.stream.Collectors.toList;


public class ObfuscationUtil
{

    private final Logger logger = LoggerFactory.getLogger( ObfuscationUtil.class );

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
                                    RenameNodeUtil.renameSimpleName( simpleName, obfuscatedVariableNames.peekFirst() );
                                }
                            }
                        } else if ( item instanceof FieldAccess )
                        {
                            FieldAccess fieldAccess = ( FieldAccess )item;
                            RenameNodeUtil.renameFieldAccessName( fieldAccess, obfuscatedVariableNames.peekFirst() );
                        } else if ( item instanceof FieldDeclaration )
                        {
                            FieldDeclaration fieldDeclaration = ( FieldDeclaration )item;
                            RenameNodeUtil.renameFieldDeclaration( fieldDeclaration, obfuscatedVariableNames.peekFirst() );
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
                .forEach( td -> {
                    TypeDeclaration typeDeclaration = ( TypeDeclaration )td;
                    ITypeBinding typeBinding = typeDeclaration.resolveBinding();
                    if ( typeBinding.isClass() && typeBinding.isFromSource() )
                    {
                        renameClassAndReferences( typeDeclaration, obfuscatedVariableNames.pollFirst(), unitNodes );
                    }
                } );
    }

    private static void renameClassAndReferences ( TypeDeclaration typeDeclaration, String obfuscatedName, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );
        String classIdentifier = typeDeclaration.getName().getIdentifier();

        Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
        callbacks.add( new RenameTypesCallback( classIdentifier, obfuscatedName ) );

        //Rename occurences in field declarations
        globalCollectedNodes.getOrDefault( FieldDeclaration.class, Collections.emptyList() ).stream()
                .map( FieldDeclaration.class::cast )
                .forEach( fd -> new FieldDeclarationVisitor( callbacks ).visit( fd ) );

        //Rename occurences in methods
        globalCollectedNodes.getOrDefault( MethodDeclaration.class, Collections.emptyList() ).stream()
                .map( MethodDeclaration.class::cast )
                .forEach( md -> {
                    new MethodDeclarationVisitor( callbacks ).visit( md );
                    //Rename occurences in method parameters
                    md.parameters().stream()
                            .forEach( p -> {
                                SingleVariableDeclaration svd = ( SingleVariableDeclaration )p;
                                new TypeVisitor( callbacks ).visit( svd.getType() );
                            } );
                } );

        //Rename occurences in import declarations
        globalCollectedNodes.getOrDefault( ImportDeclaration.class, Collections.emptyList() ).stream()
                .map( ImportDeclaration.class::cast )
                .forEach( impDecl -> {
                    //binding type might differ based on import type.
                    IBinding typeBinding = impDecl.resolveBinding();
                    if ( typeBinding.isEqualTo( typeDeclaration.resolveBinding() ) )
                    {
                        QualifiedName qualifiedName = ( QualifiedName )impDecl.getName();
                        SimpleName simpleName = qualifiedName.getName();
                        simpleName.setIdentifier( obfuscatedName );
                    }
                } );

        //Rename mixins - TODO : find a better solution
        new TypeDeclarationVisitor( callbacks ).visit( typeDeclaration );

        //Rename constructor(s)
        globalCollectedNodes.getOrDefault( MethodDeclaration.class, Collections.emptyList() ).stream()
                .map( MethodDeclaration.class::cast )
                .filter( md -> {
                    TypeDeclaration parentTypeDeclaration = ( TypeDeclaration )md.getParent();
                    return ( parentTypeDeclaration.resolveBinding().isEqualTo( typeDeclaration.resolveBinding() ) );
                } )
                .filter( MethodDeclaration::isConstructor )
                .forEach( md -> RenameNodeUtil.renameMethodDeclaration( md, obfuscatedName ) );

        //Rename actual class
        typeDeclaration.getName().setIdentifier( obfuscatedName );
    }

    private static Map<Class<? extends ASTNode>, List<ASTNode>> mergeNodeMapsToGlobalMap ( Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = new HashMap<>();

        unitNodes.stream().map( UnitNode::getCollectedNodesGroupedByClass )
                .forEach( m -> m.keySet()
                        .forEach( key -> {
                            List<ASTNode> nonEmptyNodeList = m.getOrDefault( key, Collections.emptyList() );

                            List<ASTNode> nodeListFromGlobalMap = globalCollectedNodes.getOrDefault( key, Collections.emptyList() );
                            if ( nodeListFromGlobalMap.isEmpty() )
                            {
                                globalCollectedNodes.put( key, nonEmptyNodeList );
                            } else
                            {
                                nodeListFromGlobalMap.addAll( nonEmptyNodeList );
                            }
                        } ) );
        return globalCollectedNodes;
    }
}
