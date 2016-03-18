package Model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FieldDeclarationVisitor extends ASTVisitor {
	int numberFields = 0;

	public ArrayList<String> nameAtr = new ArrayList<String>();
	public ArrayList<String> type = new ArrayList<String>();

	@Override
	public boolean visit(FieldDeclaration node) {

		List<VariableDeclarationFragment> fragments = node.fragments();

		if (fragments != null && !fragments.isEmpty()) {
			for (VariableDeclarationFragment variableDeclarationFragment : fragments) {
				if (variableDeclarationFragment.resolveBinding() != null) {

					nameAtr.add(variableDeclarationFragment.getName().toString());
					type.add(node.modifiers().toString());

					numberFields++;
					//System.out.println("Atributos " + variableDeclarationFragment.getName().toString());
					// System.out.println("Type "+node.modifiers().toString());
					//System.out.println("Type " + node.getType());
				}
			}
		}
		//System.out.println("qtd " + numberFields);

		return super.visit(node);
	}

	public int getNumberFields() {
		return numberFields;
	}

	public ArrayList<String> getNameAtr() {
		return nameAtr;
	}

	public void setNameAtr(ArrayList<String> nameAtr) {
		this.nameAtr = nameAtr;
	}

	public ArrayList<String> getType() {
		return type;
	}

	public void setType(ArrayList<String> type) {
		this.type = type;
	}

}
