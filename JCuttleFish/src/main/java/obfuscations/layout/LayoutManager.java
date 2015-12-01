package obfuscations.layout;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import obfuscations.layout.callbacks.FieldAccessNodeFoundCallBack;
import obfuscations.layout.callbacks.QualifiedNameNodeFoundCallBack;
import obfuscations.layout.callbacks.SimpleNameNodeFoundCallBack;
import obfuscations.layout.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import providers.ObfuscatedNamesProvider;
import util.ConvenienceWrappers;
import util.OptionalUtils;
import util.SourceUtil;
import util.enums.ObfuscatedNamesVariations;

import java.util.*;
import java.util.stream.Collectors;


public class LayoutManager
{

    private Collection<AstNodeFoundCallback> callbacks = new ArrayList<AstNodeFoundCallback>()
    {{
        this.add( new SimpleNameNodeFoundCallBack() );
        this.add( new FieldAccessNodeFoundCallBack() );
        this.add( new QualifiedNameNodeFoundCallBack() );
    }};
    private HashMap<String, List<ASTNode>> collectedNodes = new HashMap<>();
    private List<SimpleName> classLocalFields;
    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public UnitSource obfuscate ( UnitSource unitSource )
    {
        CompilationUnit cu = unitSource.getCompilationUnit();
        cu.recordModifications();

        if ( this.isValidCompilationUnit( cu ) )
        {
            TypeDeclaration typeDeclaration = ( TypeDeclaration )cu.types().get( 0 );
            if ( typeDeclaration.resolveBinding().isClass() )
            {
                this.populateClassLocalFieldsCollection( typeDeclaration );
                this.addClassLocalVariablesToMap();
                this.collectNodes( typeDeclaration );

                this.groupFoundNodesToCollection();

                this.obfuscateCollectedNodes();
            }
        }

        //TODO: Move "replace" to ObfuscationCoordinator
        return SourceUtil.replace( unitSource );
    }

    private Collection<MethodDeclaration> returnMethodDeclarations ( TypeDeclaration typeDeclaration )
    {
        return Arrays.stream( typeDeclaration.getMethods() ).collect( Collectors.toList() );
    }

    private void populateClassLocalFieldsCollection ( TypeDeclaration typeDeclaration )
    {
        this.classLocalFields = ConvenienceWrappers.getPrivateFieldDeclarations( typeDeclaration )
                .stream().map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                .map( vdf -> vdf.getName() ).collect( Collectors.toList() );
    }

    private void putToMapOrAddToListIfExists ( String identifier, ASTNode node )
    {
        if ( this.collectedNodes.containsKey( identifier ) )
        {
            List<ASTNode> list = this.collectedNodes.get( identifier );
            list.add( node );
        } else
        {
            List<ASTNode> list = new ArrayList<>();
            list.add( node );
            this.collectedNodes.put( identifier, list );
        }
    }

    private void collectNodes ( TypeDeclaration typeDeclaration )
    {
        Collection<MethodDeclaration> methodDeclarations = this.returnMethodDeclarations( typeDeclaration );
        for ( MethodDeclaration methodDeclaration : methodDeclarations )
        {
            List<Statement> statements = methodDeclaration.getBody().statements();
            statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );
        }
    }

    private void addClassLocalVariablesToMap ()
    {
        this.classLocalFields.stream()
                .forEachOrdered( sn -> this.putToMapOrAddToListIfExists( sn.getIdentifier(), sn ) );
    }

    private boolean isValidCompilationUnit ( CompilationUnit cu )
    {
        return !cu.types().isEmpty();
    }

    private void obfuscateCollectedNodes ()
    {
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        this.classLocalFields.stream().forEach( clf ->
                {
                    this.collectedNodes.get( clf.getIdentifier() )
                            .stream().forEach( item -> {
                        if ( item instanceof SimpleName )
                        {
                            SimpleName simpleName = ( SimpleName )item;
                            ModifyAst.renameSimpleName( simpleName, obfuscatedVariableNames.peekFirst() );
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

    //TODO : find a way to run only one stream
    private void groupFoundNodesToCollection ()
    {
        this.callbacks.stream()
                .filter( c -> c instanceof SimpleNameNodeFoundCallBack )
                .map( c -> ( SimpleNameNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( SimpleName.class::cast )
                        .filter( sn -> ( OptionalUtils.getIVariableBinding( sn ) ).isPresent() )
                        .filter( sn -> ( OptionalUtils.getIVariableBinding( sn ) ).get().isField() )
                        .forEachOrdered( sn -> this.putToMapOrAddToListIfExists( sn.getIdentifier(), sn ) ) );

        this.callbacks.stream()
                .filter( c -> c instanceof FieldAccessNodeFoundCallBack )
                .map( c -> ( FieldAccessNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( FieldAccess.class::cast )
                        .forEachOrdered( fa -> this.putToMapOrAddToListIfExists( fa.getName().getIdentifier(), fa ) ) );

        this.callbacks.stream()
                .filter( c -> c instanceof QualifiedNameNodeFoundCallBack )
                .map( c -> ( QualifiedNameNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( QualifiedName.class::cast )
                        .forEachOrdered( qn -> this.putToMapOrAddToListIfExists( qn.getName().getIdentifier(), qn ) ) );
    }
}
