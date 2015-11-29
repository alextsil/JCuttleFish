package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import util.CastToAndVisit;

import java.util.Collection;


public class AssignmentVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public AssignmentVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( Assignment assignment )
    {
        this.callbacks.stream().forEach( c -> c.notify( assignment ) );

        this.visitAssignmentSideExpression( assignment.getLeftHandSide() );
        this.visitAssignmentSideExpression( assignment.getRightHandSide() );
        return super.visit( assignment );
    }

    public void visitAssignmentSideExpression ( Expression expression )
    {
        int expressionNodeType = expression.getNodeType();

        if ( expressionNodeType == ASTNode.FIELD_ACCESS )
        {
            CastToAndVisit.fieldAccess( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.SIMPLE_NAME )
        {
            CastToAndVisit.simpleName( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.ARRAY_ACCESS )
        {
            CastToAndVisit.arrayAccess( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( expression, this.callbacks );

        } else if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.INFIX_EXPRESSION )
        {
            CastToAndVisit.infixExpression( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            CastToAndVisit.prefixExpression( expression, this.callbacks );
        }
    }
}
