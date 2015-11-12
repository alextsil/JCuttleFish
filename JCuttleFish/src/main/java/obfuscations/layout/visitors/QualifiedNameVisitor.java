package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;


public class QualifiedNameVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    private Assignment assignment;

    public QualifiedNameVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast, Assignment assignment )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
        this.assignment = assignment;
    }

    @Override
    public boolean visit ( QualifiedName qualifiedName )
    {
        SimpleName functionName = qualifiedName.getName();
        SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
        simpleNameVisitor.visit( ( SimpleName ) qualifiedName.getQualifier() );

        //Run only if the variable has been obfuscated
        if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( this.obfuscatedVarName ) )
        {
            //Creating 2 FieldAccess nodes to represent ThisExpression and nested calls
            FieldAccess fieldAccess1 = this.ast.newFieldAccess();
            fieldAccess1.setName( this.ast.newSimpleName( functionName.getIdentifier() ) ); //Outer call

            FieldAccess fieldAccess2 = this.ast.newFieldAccess();
            fieldAccess2.setExpression( this.ast.newThisExpression() );
            fieldAccess2.setName( this.ast.newSimpleName( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier() ) );

            fieldAccess1.setExpression( fieldAccess2 );

            this.assignment.setLeftHandSide( fieldAccess1 );
        }
        return false;
    }
}
