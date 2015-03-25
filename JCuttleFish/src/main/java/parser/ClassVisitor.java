package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassVisitor extends ASTVisitor {

    //ena switch me ta isCLass isInterface klp. kai call tis katalhlhs sinartisis gia fakeloma
    public boolean visit ( TypeDeclaration typeDeclaration ) {
        ITypeBinding tb = typeDeclaration.resolveBinding();
        StringBuilder sb = new StringBuilder();
        sb.append( System.lineSeparator() + "------------ Class or w/e declaration ------------ " );
        sb.append( System.lineSeparator() + "is class: " + tb.isClass() );
        sb.append( System.lineSeparator() + "name: " + tb.getName() );
        sb.append( System.lineSeparator() + "full name: " + tb.getQualifiedName() );
        sb.append( System.lineSeparator() + "kind: " + tb.getKind() ); //2=type=?
        sb.append( System.lineSeparator() + "package: " + tb.getPackage().getName() );
        sb.append( System.lineSeparator() );
        System.out.print( sb.toString() );
        return true;
    }
}
