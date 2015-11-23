package util;

import obfuscations.layout.ModifyAst;
import obfuscations.layout.visitors.*;
import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;


public class CastToAndVisit
{

    public static <V extends ASTNode> void methodInvocation ( V node, ObfuscationInfo obfuscationInfo )
    {
        MethodInvocation methodInvocation = ( MethodInvocation ) node;
        new MethodInvocationVisitor( obfuscationInfo ).visit( methodInvocation );
    }

    public static <V extends ASTNode> void fieldAccess ( V node, ObfuscationInfo obfuscationInfo )
    {
        FieldAccess fieldAccess = ( FieldAccess ) node;
        new FieldAccessVisitor( obfuscationInfo ).visit( fieldAccess );
    }

    public static <V extends ASTNode> void qualifiedName ( V node, ObfuscationInfo obfuscationInfo )
    {
        QualifiedName qualifiedName = ( QualifiedName ) node;
        new QualifiedNameVisitor( obfuscationInfo ).visit( qualifiedName );

        if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( obfuscationInfo.getObfuscatedVarName() ) )
        {
            ModifyAst.thisifyName( obfuscationInfo.getAst(), qualifiedName );
        }
    }

    public static <V extends ASTNode> void variableDeclarationStatement ( V node, ObfuscationInfo obfuscationInfo )
    {
        VariableDeclarationStatement vds = ( VariableDeclarationStatement ) node;
        new VariableDeclarationStatementVisitor( obfuscationInfo ).visit( vds );
    }

    public static <V extends ASTNode> void enhancedForStatement ( V node, ObfuscationInfo obfuscationInfo )
    {
        EnhancedForStatement enhancedForStatement = ( EnhancedForStatement ) node;
        new EnhancedForStatementVisitor( obfuscationInfo ).visit( enhancedForStatement );
    }

    public static <V extends ASTNode> void ifStatement ( V node, ObfuscationInfo obfuscationInfo )
    {
        IfStatement ifStatement = ( IfStatement ) node;
        new IfStatementVisitor( obfuscationInfo ).visit( ifStatement );
    }

    public static <V extends ASTNode> void assignment ( V node, ObfuscationInfo obfuscationInfo )
    {
        Assignment assignment = ( Assignment ) node;
        new AssignmentVisitor( obfuscationInfo ).visit( assignment );
    }

    public static <V extends ASTNode> void infixExpression ( V node, ObfuscationInfo obfuscationInfo )
    {
        InfixExpression infixExpression = ( InfixExpression ) node;
        new InfixExpressionVisitor( obfuscationInfo ).visit( infixExpression );
    }

    public static <V extends ASTNode> void prefixExpression ( V node, ObfuscationInfo obfuscationInfo )
    {
        PrefixExpression prefixExpression = ( PrefixExpression ) node;
        new PrefixExpressionVisitor( obfuscationInfo ).visit( prefixExpression );
    }

    public static <V extends ASTNode> void block ( V node, ObfuscationInfo obfuscationInfo )
    {
        Block block = ( Block ) node;
        new BlockVisitor( obfuscationInfo ).visit( block );
    }

    public static <V extends ASTNode> void postfixExpression ( V node, ObfuscationInfo obfuscationInfo )
    {
        PostfixExpression postfixExpression = ( PostfixExpression ) node;
        new PostfixExpressionVisitor( obfuscationInfo ).visit( postfixExpression );
    }

}
