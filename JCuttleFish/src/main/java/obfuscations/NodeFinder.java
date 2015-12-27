package obfuscations;

import obfuscations.callbacks.*;
import obfuscations.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.*;
import pojo.UnitNode;
import pojo.UnitSource;
import util.ConvenienceWrappers;
import util.NodeIdentification;

import java.util.*;
import java.util.stream.Collectors;


public class NodeFinder
{

    private Collection<AstNodeFoundCallback> callbacks;

    public UnitNode getUnitNodesFromUnitSource ( UnitSource unitSource )
    {
        this.initiateCallbacks();

        this.collectNodesFromMethods( unitSource.getTypeDeclarationIfIsClass() );

        HashMap<String, List<ASTNode>> collectedNodes = new HashMap<>();

        this.groupFoundNodesToMap( collectedNodes );

        this.addClassLocalFieldsToMap( collectedNodes, unitSource.getTypeDeclarationIfIsClass() );

        return new UnitNode( unitSource, collectedNodes );
    }

    private void initiateCallbacks ()
    {
        this.callbacks = new ArrayList<AstNodeFoundCallback>();
        this.callbacks.add( new SimpleNameCallback() );
        this.callbacks.add( new FieldAccessCallback() );
        this.callbacks.add( new QualifiedNameCallback() );
        this.callbacks.add( new VariableDeclarationStatementCallback() );
        this.callbacks.add( new ClassInstanceCreationCallback() );
    }

    public Collection<UnitNode> getUnitNodesCollectionFromUnitSources ( Collection<UnitSource> unitSources )
    {
        return unitSources.stream()
                .map( this::getUnitNodesFromUnitSource )
                .collect( Collectors.toList() );
    }

    private void collectNodesFromMethods ( TypeDeclaration typeDeclaration )
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
        this.callbacks.stream().forEach( c ->
                c.getFoundNodes().stream()
                        .forEachOrdered( sn -> this.putToMapOrAddToListIfExists( map,
                                NodeIdentification.mapNodeToIdentifier( sn ), sn ) ) );
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
