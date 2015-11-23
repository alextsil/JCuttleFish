package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;
import pojo.ObfuscationInfo;

import java.util.List;


public class BlockVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public BlockVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( Block block )
    {
        List<Statement> statements = block.statements();
        statements.stream().forEach( s -> new StatementVisitor( this.obfuscationInfo ).visit( s ) );

        return false;
    }
}
