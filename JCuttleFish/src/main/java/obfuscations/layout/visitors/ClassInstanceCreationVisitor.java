package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import pojo.ObfuscationInfo;

import java.util.List;


public class ClassInstanceCreationVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public ClassInstanceCreationVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( ClassInstanceCreation classInstanceCreation )
    {
        //Rename and thisify arguments
        List<Object> arguments = classInstanceCreation.arguments();
        ModifyAst.renameMethodInvocationArguments( arguments, this.obfuscationInfo );
        new MethodArgumentsVisitor( this.obfuscationInfo ).visit( arguments );
        return false;
    }
}
