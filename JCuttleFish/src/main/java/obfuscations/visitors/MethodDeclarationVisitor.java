package obfuscations.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import pojo.ObfuscationInfo;

import java.util.List;


public class MethodDeclarationVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public MethodDeclarationVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( MethodDeclaration methodDeclaration )
    {
        List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
        return false;
    }
}
