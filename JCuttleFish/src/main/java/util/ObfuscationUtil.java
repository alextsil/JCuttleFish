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
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;


@SuppressWarnings ( "all" )
public class ObfuscationUtil
{

    public static void obfuscateMethodDeclaredVariables ( Collection<UnitNode> unitNodes )
    {
        unitNodes.stream().forEach( un -> {
            Collection<MethodDeclaration> methods = ConvenienceWrappers.getMethodDeclarationsAsList(
                    ( AbstractTypeDeclaration )un.getUnitSource().getCompilationUnit().types().get( 0 ) );
            methods.stream().forEach( md -> {

                ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
                Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.METHOD_LOCAL_VARS );

                Collection<List<ASTNode>> ASTNodeListCollection = un.getCollectedNodesGroupedByIdentifier().values();

                List<VariableDeclarationStatement> methodDeclaredVarDeclStatements = ASTNodeListCollection.stream()
                        .flatMap( List::stream )
                        .filter( node -> node instanceof VariableDeclarationStatement )
                        .map( VariableDeclarationStatement.class::cast )
                        .filter( vds -> ExclusionFilters.doesVariableBelongToMethod.apply( vds, md ) )
                        .sorted( Comparators.byOccurenceOnOriginalFile )
                        .collect( toList() );

                methodDeclaredVarDeclStatements.stream()
                        .map( VariableDeclarationStatement.class::cast )
                        .forEach( vds ->
                        {
                            VariableDeclarationFragment f = ( VariableDeclarationFragment )vds.fragments().get( 0 );
                            SimpleName sn = f.getName();
                            IVariableBinding fragIvb = OptionalUtils.getIVariableBinding( sn ).get();

                            un.getCollectedNodesGroupedByIdentifier().getOrDefault( sn.getIdentifier(), Collections.emptyList() )
                                    .stream()
                                    .filter( occurence -> occurence instanceof SimpleName )
                                    .map( SimpleName.class::cast )
                                    .filter( foundsn -> OptionalUtils.getIVariableBinding( foundsn ).isPresent() )
                                    .filter( foundsn -> OptionalUtils.getIVariableBinding( foundsn ).get().isEqualTo( fragIvb ) )
                                    .forEachOrdered( foundsn -> foundsn.setIdentifier( obfuscatedVariableNames.peekFirst() ) );
                            sn.setIdentifier( obfuscatedVariableNames.poll() );
                        } );
            } );
        } );
    }

    public static void obfuscateMethodParameters ( Collection<UnitNode> unitNodes )
    {
        unitNodes.stream()
                .filter( un -> ExclusionFilters.excludedAbstractTypeDeclarations.test( ( AbstractTypeDeclaration )un.getUnitSource().getCompilationUnit().types().get( 0 ) ) )
                .forEach( un -> {
                    Collection<MethodDeclaration> methods = ConvenienceWrappers.getMethodDeclarationsAsList(
                            ( AbstractTypeDeclaration )un.getUnitSource().getCompilationUnit().types().get( 0 ) );
                    methods.stream().forEach( md -> {
                        List<SingleVariableDeclaration> parameters = md.parameters();
                        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
                        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.METHOD_PARAMETERS );

                        parameters.stream().forEach( p -> {
                            //find occurences and replace
                            IVariableBinding paramIvb = OptionalUtils.getIVariableBinding( p ).get();

                            un.getCollectedNodesGroupedByIdentifier().getOrDefault( p.getName().getIdentifier(), Collections.emptyList() )
                                    .stream()
                                    .filter( occurence -> occurence instanceof SimpleName )
                                    .map( SimpleName.class::cast )
                                    .filter( simpleName -> ExclusionFilters.isSimpleNameEqualToMethodParam.apply( simpleName, paramIvb ) )
                                    .forEach( sn -> sn.setIdentifier( obfuscatedVariableNames.peekFirst() ) );

                            //rename param on method declaration
                            p.setName( un.getUnitSource().getCompilationUnit().getAST().newSimpleName( obfuscatedVariableNames.poll() ) );
                        } );
                    } );
                } );
    }

    public static void obfuscateClassLocalVarsAndReferences ( Collection<UnitNode> unitNodes )
    {
        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( UnitSource::getCompilationUnit )
                .forEach( cu -> cu.types().stream()
                        .filter( ExclusionFilters.excludedAbstractTypeDeclarations )
                        .forEach( atd -> {
                            AbstractTypeDeclaration abstractTypeDeclaration = ( AbstractTypeDeclaration )atd;
                            List<SimpleName> classLocalSimpleNames = ConvenienceWrappers.getFieldDeclarationsAsList( abstractTypeDeclaration ).stream()
                                    .map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                                    .map( VariableDeclaration::getName ).collect( toList() );
                            obfuscateSimpleNamesAndReferences( unitNodes, classLocalSimpleNames );
                        } ) );
    }

    private static void obfuscateSimpleNamesAndReferences ( Collection<UnitNode> unitNodes, Collection<SimpleName> simpleNames )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        simpleNames.stream().forEach( sn -> {
            unitNodes.stream().forEach( unitNode -> unitNode.getCollectedNodesGroupedByIdentifier().getOrDefault( sn.getIdentifier(), Collections.emptyList() )
                    .stream().forEach( node -> RenameNodeUtil.renameFieldASTNode.accept( node, obfuscatedVariableNames ) )
            );
            obfuscatedVariableNames.pollFirst();
        } );
    }

    public static void obfuscateAbstractTypeDeclarationsAndReferences ( Collection<UnitNode> unitNodes )
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( UnitSource::getCompilationUnit )
                .forEach( cu -> cu.types().stream()
                        .filter( ExclusionFilters.excludedAbstractTypeDeclarations )
                        .forEach( atd -> {
                            Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
                            callbacks.add( new RenameAbstractTypeDeclarationCallback( obfuscatedVariableNames.pollFirst(), unitNodes ) );
                            new AbstractTypeDeclarationVisitor( callbacks ).visit( ( AbstractTypeDeclaration )atd );
                        } )
                );
    }

    public static void renameAbstractTypeDeclarationAndReferences ( TypeDeclaration abstractTypeDeclaration, String obfuscatedName, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );
        String identifier = abstractTypeDeclaration.getName().getIdentifier();

        Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
        callbacks.add( new RenameTypesCallback( identifier, obfuscatedName ) );
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

        //Rename mixins
        abstractTypeDeclaration.superInterfaceTypes().stream()
                .filter( type -> type instanceof ParameterizedType )
                .forEach( type -> {
                    ParameterizedType parameterizedType = ( ParameterizedType )type;
                    parameterizedType.typeArguments().stream()
                            .filter( typeParameter -> typeParameter instanceof SimpleType )
                            .forEach( typeParameter -> {
                                SimpleType simpleTypeParameter = ( SimpleType )typeParameter;
                                simpleTypeParameter.setName( abstractTypeDeclaration.getAST().newName( obfuscatedName ) );
                            } );
                } );

        //Rename extends
        globalCollectedNodes.getOrDefault( TypeDeclaration.class, Collections.emptyList() ).stream()
                .map( TypeDeclaration.class::cast )
                .filter( td -> td.getSuperclassType() != null )
                .forEach( td -> {
                    if ( td.getSuperclassType().resolveBinding().isEqualTo( abstractTypeDeclaration.resolveBinding() ) )
                    {
                        if ( td.getSuperclassType() instanceof SimpleType )
                        {
                            SimpleType simpleType = ( SimpleType )td.getSuperclassType();
                            SimpleName simpleName = ( SimpleName )simpleType.getName();
                            RenameNodeUtil.renameSimpleName( simpleName, obfuscatedName );
                        } else
                        {
                            throw new RuntimeException( "Only SimpleTypes supported" );
                        }
                    }
                } );

        //Rename occurences on other classes that implement this one
        globalCollectedNodes.getOrDefault( TypeDeclaration.class, Collections.emptyList() ).stream()
                .map( TypeDeclaration.class::cast )
                .flatMap( td -> td.superInterfaceTypes().stream() )
                .filter( type -> type instanceof SimpleType )
                .forEach( type -> new TypeVisitor( callbacks ).visit( ( Type )type ) );

        //Rename constructor(s)
        globalCollectedNodes.getOrDefault( MethodDeclaration.class, Collections.emptyList() ).stream()
                .map( MethodDeclaration.class::cast )
                .filter( md -> ExclusionFilters.doesMethodBelongToAbstractTypeDeclaration.apply( md, abstractTypeDeclaration ) )
                .filter( MethodDeclaration::isConstructor )
                .forEach( md -> {
                    RenameNodeUtil.renameMethodDeclaration( md, obfuscatedName );
                    md.setJavadoc( null );
                } );

        //Rename actual class
        abstractTypeDeclaration.getName().setIdentifier( obfuscatedName );
        abstractTypeDeclaration.setJavadoc( null );
    }

    public static void obfuscateMethodNames ( Collection<UnitNode> unitNodes )
    {
        unitNodes.stream()
                .map( UnitNode::getUnitSource )
                .map( UnitSource::getCompilationUnit )
                .forEach( cu -> cu.types().stream()
                        .filter( ExclusionFilters.excludedAbstractTypeDeclarations )
                        .forEach( atd -> {
                            Collection<AstNodeFoundCallback> callbacks = new ArrayList<>();
                            callbacks.add( new RenameMethodsAndReferencesCallback( unitNodes ) );
                            new AbstractTypeDeclarationVisitor( callbacks ).visit( ( AbstractTypeDeclaration )atd );
                        } ) );
    }

    public static void renameBaseClassMethodsAndReferences ( TypeDeclaration typeDeclaration, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );

        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );
        //TODO : move this to a separate class after refactoring the names provider
        Consumer<MethodDeclaration> renameMethodDeclarationAndInvocations = ( md ) -> {
            //obfuscate refs
            globalCollectedNodes.getOrDefault( MethodInvocation.class, Collections.emptyList() ).stream()
                    .map( MethodInvocation.class::cast )
                    .filter( mi -> ExclusionFilters.isMethodInvocationOfThisMethodDeclaration.apply( md, mi ) )
                    .forEach( mi -> mi.getName().setIdentifier( obfuscatedVariableNames.peekFirst() ) );
            //obfuscate method name
            md.getName().setIdentifier( obfuscatedVariableNames.pollFirst() );
            md.setJavadoc( null );
        };
        //
        ConvenienceWrappers.getMethodDeclarationsAsList( typeDeclaration ).stream()
                .map( MethodDeclaration.class::cast )
                .filter( ExclusionFilters.excludedMethods )
                .forEach( renameMethodDeclarationAndInvocations );
    }

    public static void renameSubClassMethodsAndReferences ( TypeDeclaration typeDeclaration, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );

        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.SUBCLASS_METHODS );
        //TODO : move this to a separate class after refactoring the names provider
        Consumer<MethodDeclaration> renameMethodDeclarationAndInvocations = ( md ) -> {
            //obfuscate refs
            globalCollectedNodes.getOrDefault( MethodInvocation.class, Collections.emptyList() ).stream()
                    .map( MethodInvocation.class::cast )
                    .filter( mi -> ExclusionFilters.isMethodInvocationOfThisMethodDeclaration.apply( md, mi ) )
                    .forEach( mi -> mi.getName().setIdentifier( obfuscatedVariableNames.peekFirst() ) );
            //obfuscate method name
            md.getName().setIdentifier( obfuscatedVariableNames.pollFirst() );
            md.setJavadoc( null );
        };
        //
        List<MethodDeclaration> methodDeclarations = ConvenienceWrappers.getMethodDeclarationsAsList( typeDeclaration ).stream()
                .map( MethodDeclaration.class::cast ).collect( toList() );
        methodDeclarations.stream()
                .map( MethodDeclaration.class::cast )
                .filter( ExclusionFilters.excludedMethods )
                .forEach( renameMethodDeclarationAndInvocations );

        //Treat @Override
        methodDeclarations.stream()
                .forEach( subMd -> {
                    globalCollectedNodes.getOrDefault( MethodDeclaration.class, Collections.emptyList() ).stream()
                            .map( MethodDeclaration.class::cast )
                            .filter( md -> subMd.resolveBinding().isSubsignature( md.resolveBinding() ) )
                            .forEach( md -> {
                                //obfuscate refs
                                globalCollectedNodes.getOrDefault( MethodInvocation.class, Collections.emptyList() ).stream()
                                        .map( MethodInvocation.class::cast )
                                        .filter( mi -> ExclusionFilters.isMethodInvocationOfThisMethodDeclaration.apply( md, mi ) )
                                        .forEach( mi -> mi.getName().setIdentifier( md.getName().getIdentifier() ) );
                                //obfuscate method name
                                RenameNodeUtil.renameMethodDeclaration( subMd, md.getName().getIdentifier() );
                            } );
                } );
    }

    public static void renameInterfaceMethodsAndReferences ( TypeDeclaration interfaceDeclaration, Collection<UnitNode> unitNodes )
    {
        Map<Class<? extends ASTNode>, List<ASTNode>> globalCollectedNodes = mergeNodeMapsToGlobalMap( unitNodes );

        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.INTERFACE_METHODS );

        Collection<MethodDeclaration> interfaceMethods = ConvenienceWrappers.getMethodDeclarationsAsList( interfaceDeclaration );

        //All classes that implement this interface
        List<TypeDeclaration> implementers = globalCollectedNodes.getOrDefault( TypeDeclaration.class, Collections.emptyList() ).stream()
                .map( TypeDeclaration.class::cast )
                .filter( td -> ExclusionFilters.doesClassImplementThisInterface.test( td, interfaceDeclaration ) ).collect( toList() );

        //Loop over interfaceMethods, find each one of these methods on every implementer and obfuscate accordingly
        interfaceMethods.stream().forEach( imd -> {
            implementers.stream().forEach( td -> {
                ConvenienceWrappers.getMethodDeclarationsAsList( td ).stream()
                        .filter( md -> md.resolveBinding().isSubsignature( imd.resolveBinding() ) )
                        .forEach( md -> {
                            //obfuscate overriding method
                            md.getName().setIdentifier( obfuscatedVariableNames.peekFirst() );
                            //obfuscate refs
                            globalCollectedNodes.getOrDefault( MethodInvocation.class, Collections.emptyList() ).stream()
                                    .map( MethodInvocation.class::cast )
                                    .filter( mi -> ExclusionFilters.isMethodInvocationOfThisMethodDeclaration.apply( md, mi ) )
                                    .forEach( mi -> mi.getName().setIdentifier( obfuscatedVariableNames.peekFirst() ) );
                        } );
            } );
            //obfuscate original method name
            imd.getName().setIdentifier( obfuscatedVariableNames.pollFirst() );
            imd.setJavadoc( null );
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
