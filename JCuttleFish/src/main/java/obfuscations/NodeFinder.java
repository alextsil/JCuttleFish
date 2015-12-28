package obfuscations;

import obfuscations.callbacks.*;
import obfuscations.visitors.StatementVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import pojo.UnitNode;
import pojo.UnitSource;
import util.ConvenienceWrappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class NodeFinder
{

    private Collection<AstNodeFoundCallback> callbacks;

    public UnitNode getUnitNodesFromUnitSource ( UnitSource unitSource )
    {
        this.initiateCallbacks();

        this.collectNodesFromMethods( unitSource.getTypeDeclarationIfIsClass() );

        Collection<ASTNode> collectedNodes = this.addCollectedNodesToCollection();

        this.addClassLocalFieldsToCollection( collectedNodes, unitSource.getTypeDeclarationIfIsClass() );

        return new UnitNode( unitSource, collectedNodes );
    }

    private void initiateCallbacks ()
    {
        this.callbacks = new ArrayList<>();
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

    private Collection<ASTNode> addCollectedNodesToCollection ()
    {
        Collection<ASTNode> collectedNodesAsList = new ArrayList<>();

        this.callbacks.stream().forEach( c ->
                c.getFoundNodes().stream()
                        .forEachOrdered( collectedNodesAsList::add ) );

        return collectedNodesAsList;
    }

    //Workaround. Will be replaced by visitors that visit TypeDeclaration instead of going straight to the methods.
    private void addClassLocalFieldsToCollection ( Collection<ASTNode> collection, TypeDeclaration typeDeclaration )
    {
        ConvenienceWrappers.getPrivateFieldDeclarations( typeDeclaration )
                .stream()
                .forEach( n -> collection.add( n ) );

    }
}
