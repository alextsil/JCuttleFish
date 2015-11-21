package util;

import obfuscations.layout.ModifyAst;
import obfuscations.layout.visitors.*;
import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;


public class CastToAndVisit
{

    static public <V extends ASTNode> void methodInvocation ( V node, ObfuscationInfo obfuscationInfo )
    {
        MethodInvocation methodInvocation = ( MethodInvocation ) node;
        MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor( obfuscationInfo );
        methodInvocationVisitor.visit( methodInvocation );
    }

    static public <V extends ASTNode> void fieldAccess ( V node, ObfuscationInfo obfuscationInfo )
    {
        FieldAccess fieldAccess = ( FieldAccess ) node;
        FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( obfuscationInfo );
        fieldAccessVisitor.visit( fieldAccess );
    }

    static public <V extends ASTNode> void qualifiedName ( V node, ObfuscationInfo obfuscationInfo )
    {
        QualifiedName qualifiedName = ( QualifiedName ) node;
        QualifiedNameVisitor qualifiedNameVisitor = new QualifiedNameVisitor( obfuscationInfo );
        qualifiedNameVisitor.visit( qualifiedName );

        if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( obfuscationInfo.getObfuscatedVarName() ) )
        {
            ModifyAst.thisifyName( obfuscationInfo.getAst(), qualifiedName );
        }
    }

    static public <V extends ASTNode> void variableDeclarationStatement ( V node, ObfuscationInfo obfuscationInfo )
    {
        VariableDeclarationStatement vds = ( VariableDeclarationStatement ) node;
        VariableDeclarationStatementVisitor visitor = new VariableDeclarationStatementVisitor( obfuscationInfo );
        visitor.visit( vds );
    }

    static public <V extends ASTNode> void enhancedForStatement ( V node, ObfuscationInfo obfuscationInfo )
    {
        EnhancedForStatement enhancedForStatement = ( EnhancedForStatement ) node;
        EnhancedForStatementVisitor visitor = new EnhancedForStatementVisitor( obfuscationInfo );
        visitor.visit( enhancedForStatement );
    }

    static public <V extends ASTNode> void ifStatement ( V node, ObfuscationInfo obfuscationInfo )
    {
        IfStatement ifStatement = ( IfStatement ) node;
        IfStatementVisitor visitor = new IfStatementVisitor( obfuscationInfo );
        visitor.visit( ifStatement );
    }

    static public <V extends ASTNode> void assignment ( V node, ObfuscationInfo obfuscationInfo )
    {
        Assignment assignment = ( Assignment ) node;
        AssignmentVisitor assignmentVisitor = new AssignmentVisitor( obfuscationInfo );
        assignmentVisitor.visit( assignment );
    }

    static public <V extends ASTNode> void infixExpression ( V node, ObfuscationInfo obfuscationInfo )
    {
        InfixExpression infixExpression = ( InfixExpression ) node;
        InfixExpressionVisitor visitor = new InfixExpressionVisitor( obfuscationInfo );
        visitor.visit( infixExpression );
    }

    static public <V extends ASTNode> void prefixExpression ( V node, ObfuscationInfo obfuscationInfo )
    {
        PrefixExpression prefixExpression = ( PrefixExpression ) node;
        PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( obfuscationInfo );
        visitor.visit( prefixExpression );
    }

}
