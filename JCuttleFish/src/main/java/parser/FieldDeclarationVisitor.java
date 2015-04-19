package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public class FieldDeclarationVisitor extends ASTVisitor {

    @Override
    public boolean visit ( FieldDeclaration fieldDeclaration ) {
        return true;
    }
}
