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

	private int tamanho;

	@Override
	public boolean visit(FieldDeclaration node) {

		List<VariableDeclarationFragment> fragments = node.fragments();

		if (fragments != null && !fragments.isEmpty()) {
			for (VariableDeclarationFragment variableDeclarationFragment : fragments) {
				if (variableDeclarationFragment.resolveBinding() != null) {
					nameAtr.add(variableDeclarationFragment.getName().toString().toLowerCase());
					tamanho=node.modifiers().size();
					
					if(tamanho>1){	
						type.add((node.modifiers().get(tamanho-1)).toString());	
					}else{
						if(tamanho==1){
					    type.add(node.modifiers().get(0).toString());
					    }else{
					    	type.add(" ");
					    }
					}
					numberFields++;
				}
			}
		}
		
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
