package Model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import Model.Parameters;

import org.eclipse.jdt.core.dom.PrimitiveType;


public class MethodDeclarationVisitor extends ASTVisitor {

	private ArrayList<Integer> arrayloc = new ArrayList<Integer>();
	

	private ArrayList<String> arraynameMethod = new ArrayList<String>();
	private ArrayList<String> arrayvisib = new ArrayList<String>();
	private ArrayList<String> arraytyp = new ArrayList<String>();
	private ArrayList<Boolean> arrayisGetSet = new ArrayList<Boolean>();

	private int numberMethods;
	
	private String visibility;
	private CompilationUnit compilation;
	private boolean flag;
	private int startLine;
	private int endLine;


	private boolean isGetSet;
	

	@Override
	public boolean visit(MethodDeclaration method) {


		try {
			if (!(method.getBody() == null)) {
				startLine = compilation.getLineNumber(method.getBody().getStartPosition());
				endLine = compilation.getLineNumber(method.getBody().getStartPosition() + method.getBody().getLength());
				flag = true;
				arrayloc.add((endLine - startLine - 1));
			} else {
				arrayloc.add((endLine - startLine - 1));
			}
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
			// Array list que armazena o tipo do método
			arrayvisib.add(visibility.toString().toLowerCase());
			arraynameMethod.add(method.getName().toString().toLowerCase());
			
			// verifica se o retorno do método é nulo
			if (!(method.getReturnType2() == null)) {
				arraytyp.add(method.getReturnType2().toString().toLowerCase());
			} else {

				arraytyp.add("null");
			}
		    numberMethods++;
		    isGetSet=isGetSetMethod(method);
		    arrayisGetSet.add(isGetSet);
		    
		    
		} catch (Exception e) {
			System.out.println("Erro " + e);

			System.out.println("Metodo " + method.getName().toString());
			System.out.println("Type " + method.getReturnType2().toString());
			System.err.println("Erro na classe MethodDeclarationVisitor: " + e + "\n\r");
		}
		//return super.visit(method);
		return super.visit(method);
	}
	
	private boolean isVoid(MethodDeclaration method) {
		try{
			PrimitiveType type = (PrimitiveType)method.getReturnType2();
			return type.getPrimitiveTypeCode().equals(PrimitiveType.VOID);
		} catch(Exception e){
			return false;
		}
	}
	
	private boolean isGetSetMethod(MethodDeclaration method) {
		String name = method.getName().toString();
		boolean returns = !isVoid(method);
		if (name.startsWith("get")) {
			if (method.parameters().size() == 0 && returns) {
				// TODO verificar se retorna atributo da classe
				return true;
			}
		} else if (name.startsWith("set")) {
			if (method.parameters().size() == 1 && !returns) {
				// TODO verificar se seta atributo da classe
				return true;
			}
		}
		return false;
	}	
	
	public MethodDeclarationVisitor(CompilationUnit compilation) {
		this.compilation = compilation;
	}
	
	public ArrayList<Integer> getArrayloc() {
		return arrayloc;
	}


	public void setArrayloc(ArrayList<Integer> arrayloc) {
		this.arrayloc = arrayloc;
	}


	public ArrayList<String> getArraynameMethod() {
		return arraynameMethod;
	}


	public void setArraynameMethod(ArrayList<String> arraynameMethod) {
		this.arraynameMethod = arraynameMethod;
	}


	public ArrayList<String> getArrayvisib() {
		return arrayvisib;
	}


	public void setArrayvisib(ArrayList<String> arrayvisib) {
		this.arrayvisib = arrayvisib;
	}


	public ArrayList<String> getArraytyp() {
		return arraytyp;
	}


	public void setArraytyp(ArrayList<String> arraytyp) {
		this.arraytyp = arraytyp;
	}


	public int getNumberMethods() {
		return numberMethods;
	}


	public void setNumberMethods(int numberMethods) {
		this.numberMethods = numberMethods;
	}


	public String getVisibility() {
		return visibility;
	}


	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}


	public ArrayList<Boolean> getArrayisGetSet() {
		return arrayisGetSet;
	}

	public void setArrayisGetSet(ArrayList<Boolean> arrayisGetSet) {
		this.arrayisGetSet = arrayisGetSet;
	}

	

}