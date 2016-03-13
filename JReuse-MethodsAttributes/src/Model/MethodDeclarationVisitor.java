package Model;



import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public class MethodDeclarationVisitor extends ASTVisitor {
	int numberMethods = 0;

	//public ArrayList<String> nameMethod = new ArrayList<String>();

	private  ArrayList<Integer> loc = new ArrayList<Integer>();
	private ArrayList<String> nameMethod = new ArrayList<String>();
	


	private CompilationUnit compilation;

	public MethodDeclarationVisitor(CompilationUnit compilation) {
		this.compilation = compilation;
	}
	
	
	@Override
	public boolean visit(MethodDeclaration method) {

		nameMethod.add(method.getName().toString());
		
        int startLine=compilation.getLineNumber(method.getBody().getStartPosition());
        int endLine= compilation.getLineNumber(method.getBody().getStartPosition()+method.getBody().getLength());
        
        //System.out.println(method.getBody().);
        
        System.out.println("method "+ method.getName().toString());
        System.out.println("loc "+(endLine-startLine-1));
     		
		
		numberMethods++;
		
		return super.visit(method);
	}

	public int getNumberMethods() {
		return numberMethods;
	}
	
	public ArrayList<Integer> getLoc() {
		return loc;
	}

	public void setLoc(ArrayList<Integer> loc) {
		this.loc = loc;
	}
	public ArrayList<String> getNameMethod() {
		return nameMethod;
	}


	public void setNameMethod(ArrayList<String> nameMethod) {
		this.nameMethod = nameMethod;
	}

}