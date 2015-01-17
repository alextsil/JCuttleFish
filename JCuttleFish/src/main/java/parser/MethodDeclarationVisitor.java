package parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor {

	@Override
	public boolean visit(MethodDeclaration method) {
		IMethodBinding mb = method.resolveBinding();

		System.out.println("\n------------ method declaration ------------ ");
		System.out.println("modifiers: " + method.getModifiers());
		System.out.println("return type: " + method.getReturnType2());
		System.out.println("name: " + method.getName().getFullyQualifiedName());
		System.out.println("params: " + method.parameters());
		System.out.println("declaration code: " + mb.getMethodDeclaration());
		return true;
	}

	@Override
	// emfanizei mono ths klashs
	public boolean visit(FieldDeclaration field) {
		System.out.println("\n---- field declaration ----");
		System.out.println(field);

		return true;
	}
}
