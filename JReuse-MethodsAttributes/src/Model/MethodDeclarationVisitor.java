package Model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;





public class MethodDeclarationVisitor extends ASTVisitor {

	private ArrayList<Integer> loc = new ArrayList<Integer>();
	private ArrayList<String> nameMethod = new ArrayList<String>();
	private ArrayList<String> visib = new ArrayList<String>();
	private ArrayList<String> typ = new ArrayList<String>();
	
	private String visibility;
	int numberMethods = 0;
	private CompilationUnit compilation;
	private boolean flag;
	private int startLine;
	private int endLine;

	@Override
	public boolean visit(MethodDeclaration method) {
		
		try{
		//System.out.println("Aqui: "+method.);
		if(!(method.getBody()==null)){
			 startLine = compilation.getLineNumber(method.getBody().getStartPosition());
			 endLine = compilation.getLineNumber(method.getBody().getStartPosition() + method.getBody().getLength());
			flag=true;
			loc.add(endLine - startLine - 1);
		}else{
			loc.add(0);
		}
		
		 
		 
		
		//System.out.println(compilation.getLineNumber(method.getBody().getStartPosition()));
		//System.out.println(compilation.getLineNumber(method.getBody().getStartPosition() + method.getBody().getLength()));
		
		/* For visibility */
		int modifierIdent = method.getModifiers();
		if (Modifier.isPrivate(modifierIdent)) {
			visibility = "Private";
		} else if (Modifier.isProtected(modifierIdent)) {
			visibility = "Protected";
		} else if (Modifier.isPublic(modifierIdent)) {
			visibility = "Public";
		} else {
			visibility = "Package";
		}
		//Array list que armazena o tipo do método 
		visib.add(visibility);
		nameMethod.add(method.getName().toString());
	
		// verifica se o retorno do método é nulo 
		if(!(method.getReturnType2()==null)){
			typ.add(method.getReturnType2().toString());
		}else{
			typ.add("null");
		}
		
		//method.getJavadoc().getComment();
		
		numberMethods++;
		
		}catch(Exception e){
			System.out.println("Metodo "+method.getName().toString());
			System.out.println("Type "+method.getReturnType2().toString());
			System.err.println("Erro na classe MethodDeclarationVisitor: "+e+"\n\r");	
		}
		/*System.out.println("Visibilidade: " + visibility);
		System.out.println("Return Type:  " + method.getReturnType2());
		System.out.println("Method: 	  " + method.getName().toString());*/
		/*if(flag){
			System.out.println("Loc: 	  " + (endLine - startLine - 1) + "\n\r");
		}*/

		return super.visit(method);
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

	public ArrayList<String> getVisib() {
		return visib;
	}

	public void setVisib(ArrayList<String> visib) {
		this.visib = visib;
	}

	public ArrayList<String> getTyp() {
		return typ;
	}

	public void setTyp(ArrayList<String> typ) {
		this.typ = typ;
	}
	
	public int getNumberMethods() {
		return numberMethods;
	}

	public void setNumberMethods(int numberMethods) {
		this.numberMethods = numberMethods;
	}

	public MethodDeclarationVisitor(CompilationUnit compilation) {
		this.compilation = compilation;
	}
}