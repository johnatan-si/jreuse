package Model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FieldDeclarationVisitor extends ASTVisitor {
	int numberFields = 0;

	public ArrayList<String> nameAtr = new ArrayList<String>();
	public ArrayList<String> tipo = new ArrayList<String>();

	@Override
	public boolean visit(FieldDeclaration node) {

		List<VariableDeclarationFragment> fragments = node.fragments();

		if (fragments != null && !fragments.isEmpty()) {
			for (VariableDeclarationFragment variableDeclarationFragment : fragments) {
				if (variableDeclarationFragment.resolveBinding() != null) {

					nameAtr.add(variableDeclarationFragment.getName().toString());
					nameAtr.add(variableDeclarationFragment.getName().toString());
					tipo.add(node.modifiers().toString());

					numberFields++;

				}
			}
		}
		return super.visit(node);
	}

	public int getNumberFields() {
		return numberFields;
	}

}
