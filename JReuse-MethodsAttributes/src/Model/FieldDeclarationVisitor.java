package Model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class FieldDeclarationVisitor extends ASTVisitor {

	public Parameters parametersField;
	int numberFields = 0;
	private int tamanho;

	@Override
	public boolean visit(FieldDeclaration node) {

		List<VariableDeclarationFragment> fragments = node.fragments();

		if (fragments != null && !fragments.isEmpty()) {
			for (VariableDeclarationFragment variableDeclarationFragment : fragments) {
				if (variableDeclarationFragment.resolveBinding() != null) {
					parametersField.setNameAttribute(variableDeclarationFragment.getName().toString().toLowerCase());
					tamanho = node.modifiers().size();

					if (tamanho > 1) {
						parametersField.setVisibilityAttribute((node.modifiers().get(tamanho - 1)).toString());
					} else {
						if (tamanho == 1) {
							parametersField.setVisibilityAttribute(node.modifiers().get(0).toString());
						} else {
							parametersField.setVisibilityAttribute("public");
						}
					}
					numberFields++;
					parametersField.setNumberAttribute(numberFields);
					parametersField.setTypeAttribute(node.getType().toString().toLowerCase());
				}
			}
		}

		return super.visit(node);
	}
}
