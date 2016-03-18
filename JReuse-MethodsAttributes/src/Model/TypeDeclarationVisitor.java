package Model;

import java.awt.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class TypeDeclarationVisitor extends ASTVisitor {


	@Override
	public boolean visit(TypeDeclaration node) {
		/*boolean privateModifier = false;
		for (Object modifier : node.modifiers()) {
			if (modifier.getClass().equals(Modifier.class) && ((Modifier)modifier).isPrivate()) {
				privateModifier = true;
				break;
			}
//			System.out.println("TIPO "+node.getNameProperty());
//			System.out.println("TIPO DECLARADO "+node.getNameProperty());
		}*/
		

		return super.visit(node);
	}
}
