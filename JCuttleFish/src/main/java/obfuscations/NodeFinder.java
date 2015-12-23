package obfuscations;

import obfuscations.layout.callbacks.*;
import obfuscations.layout.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.*;
import pojo.UnitNode;
import pojo.UnitSource;
import util.ConvenienceWrappers;

import java.util.*;
import java.util.stream.Collectors;


public class NodeFinder
{

    private Collection<AstNodeFoundCallback> callbacks = new ArrayList<AstNodeFoundCallback>()
    {{
        this.add( new SimpleNameNodeFoundCallBack() );
        this.add( new FieldAccessNodeFoundCallBack() );
        this.add( new QualifiedNameNodeFoundCallBack() );
        this.add( new VariableDeclarationStatementNodeFoundCallBack() );
    }};

    public UnitNode getUnitNodesFromUnitSource ( UnitSource unitSource )
    {
        this.collectNodes( unitSource.getTypeDeclarationIfIsClass() );

        HashMap<String, List<ASTNode>> collectedNodes = new HashMap<>();

        this.groupFoundNodesToMap( collectedNodes );

        this.addClassLocalFieldsToMap( collectedNodes, unitSource.getTypeDeclarationIfIsClass() );

        return new UnitNode( unitSource, collectedNodes );
    }

    public Collection<UnitNode> getUnitNodesCollectionFromUnitSources ( Collection<UnitSource> unitSources )
    {
        return unitSources.stream()
                .map( this::getUnitNodesFromUnitSource )
                .collect( Collectors.toList() );
    }

    private void collectNodes ( TypeDeclaration typeDeclaration )
    {
        Collection<MethodDeclaration> methodDeclarations = ConvenienceWrappers
                .returnMethodDeclarations( typeDeclaration );

        methodDeclarations.stream().forEach( md -> {
            List<Statement> statements = md.getBody().statements();
            statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );
        } );
    }

    private void groupFoundNodesToMap ( Map<String, List<ASTNode>> map )
    {
        this.callbacks.stream()
                .filter( c -> c instanceof SimpleNameNodeFoundCallBack )
                .map( c -> ( SimpleNameNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( SimpleName.class::cast )
                        .forEachOrdered( sn -> this.putToMapOrAddToListIfExists( map, sn.getIdentifier(), sn ) ) );

        this.callbacks.stream()
                .filter( c -> c instanceof FieldAccessNodeFoundCallBack )
                .map( c -> ( FieldAccessNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( FieldAccess.class::cast )
                        .forEachOrdered( fa -> this.putToMapOrAddToListIfExists( map, fa.getName().getIdentifier(), fa ) ) );

        this.callbacks.stream()
                .filter( c -> c instanceof QualifiedNameNodeFoundCallBack )
                .map( c -> ( QualifiedNameNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( QualifiedName.class::cast )
                        .forEachOrdered( qn -> this.putToMapOrAddToListIfExists( map, qn.getName().getIdentifier(), qn ) ) );

        this.callbacks.stream()
                .filter( c -> c instanceof VariableDeclarationStatementNodeFoundCallBack )
                .map( c -> ( VariableDeclarationStatementNodeFoundCallBack )c )
                .findFirst().ifPresent( c ->
                c.getFoundNodes().stream()
                        .map( VariableDeclarationStatement.class::cast )
                        .forEachOrdered( vds -> {
                            VariableDeclarationFragment vdf = ( VariableDeclarationFragment )vds.fragments().get( 0 );
                            this.putToMapOrAddToListIfExists( map, vdf.getName().getIdentifier(), vds);
                        } ) );
    }

    private void addClassLocalFieldsToMap ( Map<String, List<ASTNode>> map, TypeDeclaration typeDeclaration )
    {
        ConvenienceWrappers.getPrivateFieldDeclarations( typeDeclaration )
                .stream().map( f -> ( VariableDeclarationFragment )f.fragments().get( 0 ) )
                .map( vdf -> vdf.getName() )
                .forEach( sn -> this.putToMapOrAddToListIfExists( map, sn.getIdentifier(), sn ) );

    }

    private void putToMapOrAddToListIfExists ( Map<String, List<ASTNode>> map, String identifier, ASTNode node )
    {
        if ( map.containsKey( identifier ) )
        {
            List<ASTNode> list = map.get( identifier );
            list.add( node );
        } else
        {
            List<ASTNode> list = new ArrayList<>();
            list.add( node );
            map.put( identifier, list );
        }
    }

}
