package Model;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends ASTVisitor {
	int numberMethods = 0;

	public ArrayList<String> nameMethod = new ArrayList<String>();

	public ArrayList<String> nameClass = new ArrayList<String>();

	@Override
	public boolean visit(MethodDeclaration node) {

		nameMethod.add(node.getName().toString());
		numberMethods++;
		
		return super.visit(node);
	}

	public int getNumberMethods() {
		return numberMethods;
	}

}
