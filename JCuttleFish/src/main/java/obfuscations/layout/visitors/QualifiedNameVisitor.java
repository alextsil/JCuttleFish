package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
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
        SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
        simpleNameVisitor.visit( ( SimpleName ) qualifiedName.getQualifier() );

        //Run only if the variable has been obfuscated
        if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( this.obfuscatedVarName ) )
        {
            this.assignment.setLeftHandSide( ModifyAst.thisifyQualifiedName( this.ast, qualifiedName ) );
        }
        return false;
    }
}
