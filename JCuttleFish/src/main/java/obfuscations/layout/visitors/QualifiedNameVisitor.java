package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;


public class QualifiedNameVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public QualifiedNameVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( QualifiedName qualifiedName )
    {
        SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
        simpleNameVisitor.visit( ( SimpleName ) qualifiedName.getQualifier() );
        return false;
    }
}
