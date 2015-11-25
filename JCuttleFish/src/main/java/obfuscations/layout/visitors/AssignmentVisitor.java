package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class AssignmentVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public AssignmentVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( Assignment assignment )
    {
        this.visitAssignmentSideExpression( assignment.getLeftHandSide() );
        this.visitAssignmentSideExpression( assignment.getRightHandSide() );
        return false;
    }

    public void visitAssignmentSideExpression ( Expression expression )
    {
        int expressionNodeType = expression.getNodeType();

        if ( expressionNodeType == ASTNode.FIELD_ACCESS )
        {
            CastToAndVisit.fieldAccess( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.SIMPLE_NAME )
        {
            CastToAndVisit.simpleName( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.ARRAY_ACCESS )
        {
            CastToAndVisit.arrayAccess( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( expression, this.obfuscationInfo );

        } else if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.INFIX_EXPRESSION )
        {
            CastToAndVisit.infixExpression( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            CastToAndVisit.prefixExpression( expression, this.obfuscationInfo );
        }
    }
}
