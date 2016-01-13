package util;

import obfuscations.callbacks.*;
import obfuscations.visitors.AbstractTypeDeclarationVisitor;
import obfuscations.visitors.FieldDeclarationVisitor;
import obfuscations.visitors.MethodDeclarationVisitor;
import obfuscations.visitors.TypeVisitor;
import org.eclipse.jdt.core.dom.*;
import pojo.UnitNode;
import pojo.UnitSource;
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

    public static void obfuscateClassLocalVarsAndReferences ( Collection<UnitNode> unitNodes )
    {
        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( UnitSource::getCompilationUnit )
                .forEach( cu -> cu.types().stream()
                        .forEach( atd -> {
                            AbstractTypeDeclaration abstractTypeDeclaration = ( AbstractTypeDeclaration )atd;
                            List<SimpleName> classLocalSimpleNames = ConvenienceWrappers.getFieldDeclarationsAsList( abstractTypeDeclaration ).stream()
                                    .map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                                    .map( VariableDeclaration::getName ).collect( toList() );
                            obfuscateSimpleNamesAndReferences( unitNodes, classLocalSimpleNames );
                            if ( atd instanceof EnumDeclaration )
                            {
                                EnumDeclaration enumDeclaration = ( EnumDeclaration )atd;
                                List<EnumConstantDeclaration> enumConstantDeclarations = enumDeclaration.enumConstants();
                                //Call enum obfuscation
                            }
                        } ) );
    }

    private static void obfuscateSimpleNamesAndReferences ( Collection<UnitNode> unitNodes, Collection<SimpleName> simpleNames )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        simpleNames.stream().forEach( sn -> {
            unitNodes.stream().forEach( unitNode -> unitNode.getCollectedNodesGroupedByIdentifier().getOrDefault( sn.getIdentifier(), Collections.emptyList() )
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
                    } )
            );
            obfuscatedVariableNames.pollFirst();
        } );
    }

    public static void obfuscateAbstractTypeDeclarationNames ( Collection<UnitNode> unitNodes )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( UnitSource::getCompilationUnit )
                .forEach( cu -> cu.types().stream()
                        .forEach( atd -> {
                            Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
                            callbacks.add( new RenameAbstractTypeDeclarationCallback( obfuscatedVariableNames.pollFirst(), unitNodes ) );
                            new AbstractTypeDeclarationVisitor( callbacks ).visit( ( AbstractTypeDeclaration )atd );
                        } )
                );
    }

    public static void renameAbstractTypeDeclarationAndReferences ( AbstractTypeDeclaration abstractTypeDeclaration, String obfuscatedName, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );
        String classIdentifier = abstractTypeDeclaration.getName().getIdentifier();

        Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
        callbacks.add( new RenameTypesCallback( classIdentifier, obfuscatedName ) );
        callbacks.add( new RenameClassReferenceCallback( abstractTypeDeclaration.getName().resolveBinding(), obfuscatedName ) );

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
                    IBinding typeBinding = impDecl.resolveBinding();
                    if ( typeBinding.isEqualTo( abstractTypeDeclaration.resolveBinding() ) )
                    {
                        QualifiedName qualifiedName = ( QualifiedName )impDecl.getName();
                        SimpleName simpleName = qualifiedName.getName();
                        simpleName.setIdentifier( obfuscatedName );
                    }
                } );

        //Rename mixins - TODO : find a better solution
        new AbstractTypeDeclarationVisitor( callbacks ).visit( abstractTypeDeclaration );

        //Rename constructor(s)
        globalCollectedNodes.getOrDefault( MethodDeclaration.class, Collections.emptyList() ).stream()
                .map( MethodDeclaration.class::cast )
                .filter( md -> {
                    AbstractTypeDeclaration parentAbstractTypeDeclaration = ( AbstractTypeDeclaration )md.getParent();
                    return ( parentAbstractTypeDeclaration.resolveBinding().isEqualTo( abstractTypeDeclaration.resolveBinding() ) );
                } )
                .filter( MethodDeclaration::isConstructor )
                .forEach( md -> RenameNodeUtil.renameMethodDeclaration( md, obfuscatedName ) );

        //Rename actual class
        abstractTypeDeclaration.getName().setIdentifier( obfuscatedName );
    }

    public static void obfuscateMethodNames ( Collection<UnitNode> unitNodes )
    {
        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( UnitSource::getCompilationUnit )
                .forEach( cu -> cu.types().stream()
                        .forEach( atd -> {
                            Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
                            callbacks.add( new RenameMethodsAndReferencesCallback( unitNodes ) );
                            new AbstractTypeDeclarationVisitor( callbacks ).visit( ( AbstractTypeDeclaration )atd );
                        } ) );
    }

    public static void renameMethodsAndReferences ( AbstractTypeDeclaration abstractTypeDeclaration, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );

        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        ConvenienceWrappers.getMethodDeclarationsAsList( abstractTypeDeclaration ).stream()
                .map( MethodDeclaration.class::cast )
                .filter( md -> !md.isConstructor() )
                .filter( md -> !md.getName().getIdentifier().equals( "main" ) )
                .filter( md -> !md.getName().getIdentifier().equals( "compareTo" ) )
                .filter( md -> !md.getName().getIdentifier().equals( "equals" ) )
                .filter( md -> !md.getName().getIdentifier().equals( "toString" ) )
                .filter( md -> !md.getName().getIdentifier().equals( "accept" ) )
                .filter( md -> !md.getName().getIdentifier().equals( "clone" ) )
                .filter( md -> !md.getName().getIdentifier().equals( "finalize" ) )
                .forEach( md -> {
                    //obfuscate refs
                    globalCollectedNodes.getOrDefault( MethodInvocation.class, Collections.emptyList() ).stream()
                            .map( MethodInvocation.class::cast )
                            .filter( mi -> md.getName().resolveBinding().isEqualTo( mi.getName().resolveBinding() ) )
                            .forEach( mi -> mi.getName().setIdentifier( obfuscatedVariableNames.peekFirst() ) );
                    //obfuscate method name
                    md.getName().setIdentifier( obfuscatedVariableNames.pollFirst() );
                } );
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
