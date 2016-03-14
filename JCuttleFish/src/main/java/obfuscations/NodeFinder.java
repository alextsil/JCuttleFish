package obfuscations;

import obfuscations.callbacks.*;
import obfuscations.visitors.CompilationUnitVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import pojo.UnitNode;
import pojo.UnitSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


public class NodeFinder
{

    private Collection<AstNodeFoundCallback> callbacks;

    public UnitNode getUnitNodesFromUnitSource ( UnitSource unitSource )
    {
        this.initializeCallbacks();

        this.collectNodes( unitSource.getCompilationUnit() );

        Collection<ASTNode> collectedNodes = this.addCollectedNodesToCollection();

        return new UnitNode( unitSource, collectedNodes );
    }

    private void initializeCallbacks ()
    {
        this.callbacks = new ArrayList<>();
        this.callbacks.add( new SimpleNameCallback() );
        this.callbacks.add( new FieldAccessCallback() );
        this.callbacks.add( new QualifiedNameCallback() );
        this.callbacks.add( new VariableDeclarationStatementCallback() );
        this.callbacks.add( new ClassInstanceCreationCallback() );
        this.callbacks.add( new TypeDeclarationCallback() );
        this.callbacks.add( new FieldDeclarationCallback() );
        this.callbacks.add( new MethodDeclarationCallback() );
        this.callbacks.add( new ImportDeclarationCallback() );
        this.callbacks.add( new PackageDeclarationCallback() );
        this.callbacks.add( new MethodInvocationCallback() );
    }

    public Collection<UnitNode> getUnitNodesCollectionFromUnitSources ( Collection<UnitSource> unitSources )
    {
        return unitSources.stream()
                .map( this::getUnitNodesFromUnitSource )
                .collect( Collectors.toList() );
    }

    private void collectNodes ( CompilationUnit compilationUnit )
    {
        new CompilationUnitVisitor( this.callbacks ).visit( compilationUnit );
    }

    private Collection<ASTNode> addCollectedNodesToCollection ()
    {
        Collection<ASTNode> collectedNodesAsList = new ArrayList<>();

        this.callbacks.stream().forEach( c ->
                c.getFoundNodes().stream()
                        .forEachOrdered( collectedNodesAsList::add ) );

        return collectedNodesAsList;
    }
}
