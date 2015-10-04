package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;


public class ExpressionStatementVisitor extends ASTVisitor
{

    @Override
    public boolean visit ( ExpressionStatement node )
    {
        int nodeType = node.getNodeType();
        return true;
    }
}
