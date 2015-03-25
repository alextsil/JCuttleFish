package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor {

    @Override
    public boolean visit ( MethodDeclaration method ) {
        IMethodBinding mb = method.resolveBinding();
        StringBuilder sb = new StringBuilder();

        sb.append( System.lineSeparator() + "------------ method declaration ------------ " );
        sb.append( System.lineSeparator() + "name: " + mb.getName() );
        sb.append( System.lineSeparator() + "is constructor: " + mb.isConstructor() );
        sb.append( System.lineSeparator() + "modifiers: " + mb.getModifiers() ); //1,2,3 priv pub ..
        sb.append( System.lineSeparator() + "return type: " + mb.getReturnType().getName() );
        sb.append( System.lineSeparator() + "params: " + method.parameters() );
        sb.append( System.lineSeparator() + "declaration code: " + mb.getMethodDeclaration() );
        sb.append( System.lineSeparator() );
        System.out.print( sb.toString() );
        return true;
    }

    @Override
    // emfanizei mono ths klashs
    public boolean visit ( FieldDeclaration field ) {
        System.out.println( "\n---- field declaration ----" );
        System.out.println( field );

        return true;
    }

}
