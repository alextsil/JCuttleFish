package util;

import obfuscations.callbacks.AstNodeFoundCallback;
import obfuscations.visitors.*;
import org.eclipse.jdt.core.dom.*;

import java.util.Collection;


public class CastToAndVisit
{

    public static <V extends ASTNode> void methodInvocation ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        MethodInvocation methodInvocation = ( MethodInvocation )node;
        new MethodInvocationVisitor( callbacks ).visit( methodInvocation );
    }

    public static <V extends ASTNode> void superMethodInvocation ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        SuperMethodInvocation superMethodInvocation = ( SuperMethodInvocation )node;
        new SuperMethodInvocationVisitor( callbacks ).visit( superMethodInvocation );
    }

    public static <V extends ASTNode> void fieldAccess ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        FieldAccess fieldAccess = ( FieldAccess )node;
        new FieldAccessVisitor( callbacks ).visit( fieldAccess );
    }

    public static <V extends ASTNode> void qualifiedName ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        QualifiedName qualifiedName = ( QualifiedName )node;
        new QualifiedNameVisitor( callbacks ).visit( qualifiedName );
    }

    public static <V extends ASTNode> void variableDeclarationStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        VariableDeclarationStatement vds = ( VariableDeclarationStatement )node;
        new VariableDeclarationStatementVisitor( callbacks ).visit( vds );
    }

    public static <V extends ASTNode> void enhancedForStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        EnhancedForStatement enhancedForStatement = ( EnhancedForStatement )node;
        new EnhancedForStatementVisitor( callbacks ).visit( enhancedForStatement );
    }

    public static <V extends ASTNode> void forStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        ForStatement forStatement = ( ForStatement )node;
        new ForStatementVisitor( callbacks ).visit( forStatement );
    }

    public static <V extends ASTNode> void ifStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        IfStatement ifStatement = ( IfStatement )node;
        new IfStatementVisitor( callbacks ).visit( ifStatement );
    }

    public static <V extends ASTNode> void assignment ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        Assignment assignment = ( Assignment )node;
        new AssignmentVisitor( callbacks ).visit( assignment );
    }

    public static <V extends ASTNode> void infixExpression ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        InfixExpression infixExpression = ( InfixExpression )node;
        new InfixExpressionVisitor( callbacks ).visit( infixExpression );
    }

    public static <V extends ASTNode> void prefixExpression ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        PrefixExpression prefixExpression = ( PrefixExpression )node;
        new PrefixExpressionVisitor( callbacks ).visit( prefixExpression );
    }

    public static <V extends ASTNode> void block ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        Block block = ( Block )node;
        new BlockVisitor( callbacks ).visit( block );
    }

    public static <V extends ASTNode> void postfixExpression ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        PostfixExpression postfixExpression = ( PostfixExpression )node;
        new PostfixExpressionVisitor( callbacks ).visit( postfixExpression );
    }

    public static <V extends ASTNode> void whileStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        WhileStatement whileStatement = ( WhileStatement )node;
        new WhileStatementVisitor( callbacks ).visit( whileStatement );
    }

    public static <V extends ASTNode> void doWhileStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        DoStatement doStatement = ( DoStatement )node;
        new DoStatementVisitor( callbacks ).visit( doStatement );
    }

    public static <V extends ASTNode> void switchStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        SwitchStatement switchStatement = ( SwitchStatement )node;
        new SwitchStatementVisitor( callbacks ).visit( switchStatement );
    }

    public static <V extends ASTNode> void classInstanceCreation ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation )node;
        new ClassInstanceCreationVisitor( callbacks ).visit( classInstanceCreation );
    }

    public static <V extends ASTNode> void tryStatement ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        TryStatement tryStatement = ( TryStatement )node;
        new TryStatementVisitor( callbacks ).visit( tryStatement );
    }

    public static <V extends ASTNode> void arrayAccess ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        ArrayAccess arrayAccess = ( ArrayAccess )node;
        new ArrayAccessVisitor( callbacks ).visit( arrayAccess );
    }

    public static <V extends ASTNode> void constructorInvocation ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        ConstructorInvocation constructorInvocation = ( ConstructorInvocation )node;
        new ConstructorInvocationVisitor( callbacks ).visit( constructorInvocation );
    }

    public static <V extends ASTNode> void simpleName ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        SimpleName simpleName = ( SimpleName )node;
        new SimpleNameVisitor( callbacks ).visit( simpleName );
    }

    public static <V extends ASTNode> void typeLitelar ( V node, Collection<AstNodeFoundCallback> callbacks )
    {
        TypeLiteral typeLiteral = ( TypeLiteral )node;
        new TypeLiteralVisitor( callbacks ).visit( typeLiteral );
    }

}
