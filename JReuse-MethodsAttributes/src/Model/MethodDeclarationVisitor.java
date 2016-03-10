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
	public boolean visit(MethodDeclaration node) {

		
	//	nameMethod.add(node.getName().toString());
		
		int startline = compilation.getLineNumber(node.getStartPosition());
        int endLine = compilation.getLineNumber(node.getStartPosition() + node.getLength());
        //return endLine - startline -1;
        this.loc.add(endLine - startline -2);
        
        this.nameMethod.add(node.getName().toString());
        //System.out.println("JESUS DE NAZARE "+(endLine - startline -1));	
		
			//System.out.println("1"+node.parameters().size());
			//System.out.println("2"+node.parameters().toString());
			//System.out.println("3"+node.parameters().toString());
			//System.out.println("4"+node.parameters().toString());
			//System.out.println("5"+node.parameters().toString());
		
		/*methodParam.setLoc(getLoc(method));
		methodParam.setParameters(method.parameters().size());
		methodParam.setReturns(!isVoid(method));
		methodParam.setStatic(Modifier.isStatic(method.getModifiers()));
		methodParam.setFinal(Modifier.isFinal(method.getModifiers()));
		methodParam.setAbstract(Modifier.isAbstract(method.getModifiers()));
		methodParam.setGetSet(isGetSetMethod(method));
		methodParam.setThrowsException(method.thrownExceptionTypes().size() > 0);*/
		
		
		numberMethods++;
		
		return super.visit(node);
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