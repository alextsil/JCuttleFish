package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
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
            SimpleName simpleName = ( SimpleName )expression;
            IVariableBinding varBinding = ( IVariableBinding )simpleName.resolveBinding();
            if ( varBinding.isField() )
            {
                ModifyAst.renameSimpleName( simpleName, this.obfuscationInfo.getOriginalVarSimpleName(),
                        this.obfuscationInfo.getObfuscatedVarName() );
                ModifyAst.thisifyName( this.obfuscationInfo.getAst(), simpleName );
            }
        } else if ( expressionNodeType == ASTNode.ARRAY_ACCESS )
        {
            ArrayAccess arrayAccess = ( ArrayAccess )expression;
            FieldAccess fieldAccess = ( FieldAccess )arrayAccess.getArray();
            ModifyAst.renameFieldAccessName( fieldAccess, this.obfuscationInfo.getOriginalVarSimpleName(),
                    this.obfuscationInfo.getObfuscatedVarName() );
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
