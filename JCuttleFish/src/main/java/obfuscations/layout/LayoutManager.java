package obfuscations.layout;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import providers.ObfuscatedNamesProvider;
import util.ConvenienceWrappers;
import util.OptionalUtils;
import util.SourceUtil;
import util.enums.ObfuscatedNamesVariations;

import java.util.*;

import static java.util.stream.Collectors.toList;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public Collection<UnitNode> obfuscate ( Collection<UnitNode> unitNodeCollection )
    {
        for ( UnitNode unitNode : unitNodeCollection )
        {
            unitNode.recordMofications();

            Collection<MethodDeclaration> methods = ConvenienceWrappers.returnMethodDeclarations( unitNode.getUnitSource().getTypeDeclarationIfIsClass() );
            methods.stream().forEach( m -> {
                this.obfuscateMethodParameters( unitNode, m );
                this.obfuscateMethodDeclaredVariables( unitNode, m );
            } );

            this.obfuscateClassLocalVarsAndReferences( unitNode );
            SourceUtil.replace( unitNode.getUnitSource() );
        }

        return unitNodeCollection;
    }

    private void obfuscateMethodDeclaredVariables ( UnitNode unitNode, MethodDeclaration methodDeclaration )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.METHOD_LOCAL_VARS );

        Comparator<VariableDeclarationStatement> byOccurrence =
                ( VariableDeclarationStatement o1, VariableDeclarationStatement o2 ) -> o1.getStartPosition() - o2.getStartPosition();

        Collection<List<ASTNode>> eksw = unitNode.getCollectedNodes().values();
        List<VariableDeclarationStatement> methodDeclaredVarDeclStatements = new ArrayList<>();
        //TODO : fix monstrosity
        for ( List<ASTNode> mesaLista : eksw )
        {
            for ( ASTNode node : mesaLista )
            {
                if ( node instanceof VariableDeclarationStatement )
                {
                    VariableDeclarationStatement vds = ( VariableDeclarationStatement )node;
                    VariableDeclarationFragment vdf = ( VariableDeclarationFragment )vds.fragments().get( 0 );
                    Optional<IVariableBinding> oivb = OptionalUtils.getIVariableBinding( vdf );
                    if ( oivb.isPresent() )
                    {
                        if ( oivb.get().getDeclaringMethod().isEqualTo( methodDeclaration.resolveBinding() ) )
                        {
                            methodDeclaredVarDeclStatements.add( vds );
                        }
                    }
                }
            }
        }

        methodDeclaredVarDeclStatements.sort( byOccurrence );
        methodDeclaredVarDeclStatements.stream()
                .map( VariableDeclarationStatement.class::cast )
                .forEach( vds ->
                {
                    VariableDeclarationFragment f = ( VariableDeclarationFragment )vds.fragments().get( 0 );
                    SimpleName sn = f.getName();
                    IVariableBinding fragIvb = OptionalUtils.getIVariableBinding( sn ).get();

                    unitNode.getCollectedNodes().getOrDefault( sn.getIdentifier(), Collections.emptyList() )
                            .stream()
                            .filter( occurence -> occurence instanceof SimpleName )
                            .map( SimpleName.class::cast )
                            .filter( foundsn -> OptionalUtils.getIVariableBinding( foundsn ).isPresent() )
                            .filter( foundsn -> OptionalUtils.getIVariableBinding( foundsn ).get().isEqualTo( fragIvb ) )
                            .forEachOrdered( foundsn -> foundsn.setIdentifier( obfuscatedVariableNames.peekFirst() ) );
                    sn.setIdentifier( obfuscatedVariableNames.poll() );
                } );
    }

    private void obfuscateMethodParameters ( UnitNode unitNode, MethodDeclaration methodDeclaration )
    {
        List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.METHOD_PARAMETERS );

        parameters.stream().forEach( p -> {
            //find occurences and replace
            IVariableBinding paramIvb = OptionalUtils.getIVariableBinding( p ).get();

            unitNode.getCollectedNodes().getOrDefault( p.getName().getIdentifier(), Collections.emptyList() )
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

    private void obfuscateClassLocalVarsAndReferences ( UnitNode unitNode )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        List<SimpleName> classLocalFields = ConvenienceWrappers.getPrivateFieldDeclarations( unitNode.getUnitSource().getTypeDeclarationIfIsClass() )
                .stream().map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                .map( vdf -> vdf.getName() )
                .collect( toList() );

        classLocalFields.stream().forEach( clf ->
                {
                    unitNode.getCollectedNodes().get( clf.getIdentifier() )
                            .stream().forEach( item -> {
                        if ( item instanceof SimpleName )
                        {
                            SimpleName simpleName = ( SimpleName )item;
                            Optional<IVariableBinding> ivb = OptionalUtils.getIVariableBinding( simpleName );
                            if ( ivb.isPresent() )
                            {
                                if ( ivb.get().isField() )
                                {
                                    ModifyAst.renameSimpleName( simpleName, obfuscatedVariableNames.peekFirst() );
                                }
                            }
                        } else if ( item instanceof FieldAccess )
                        {
                            FieldAccess fieldAccess = ( FieldAccess )item;
                            ModifyAst.renameFieldAccessName( fieldAccess, obfuscatedVariableNames.peekFirst() );
                        }
                    } );
                    obfuscatedVariableNames.poll();
                }
        );
    }

}
