package Model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class MethodDeclarationVisitor extends ASTVisitor {
	

	private ArrayList<Integer> loc = new ArrayList<Integer>();
	private ArrayList<String> nameMethod = new ArrayList<String>();
	private ArrayList<String> visib = new ArrayList<String>();
	private ArrayList<String> typ = new ArrayList<String>();
	private CompilationUnit compilation;
	private String visibility;
	int numberMethods = 0;

	  private Stack<ITypeBinding>    _typeBindings;
	private Object retorno; 

	
	
	@Override
	public boolean visit(MethodDeclaration method) {
		
		int startLine = compilation.getLineNumber(method.getBody().getStartPosition());
		int endLine = compilation.getLineNumber(method.getBody().getStartPosition() + method.getBody().getLength());

		
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
		
		// Adição na lista
		try{
		visib.add(visibility);
		nameMethod.add(method.getName().toString());
		
		//typ.add(method.getReturnType2().toString());
		  // declared method return type 
		if(!(method.getReturnType2()==null)){
			typ.add(method.getReturnType2().toString());
		}
		
		loc.add(endLine - startLine - 1);
		
		numberMethods++;
		}catch(Exception e){
			System.out.println("Metodo "+method.getName().toString());
			System.out.println("AQUI "+method.getReturnType2().toString());

			System.err.println(e+"\n\r");	
		}
		
		System.out.println("Visibilidade: " + visibility);
		System.out.println("Return Type:  " + method.getReturnType2());
		System.out.println("Method: 	  " + method.getName().toString());
		System.out.println("Loc: 		  " + (endLine - startLine - 1) + "\n\r");

		/*
		 * signature = new MethodSignature(name, visibility); parameters =
		 * node.parameters(); if (null != parameters) { for (Object parameter :
		 * parameters) { SingleVariableDeclaration parameterCasted =
		 * (SingleVariableDeclaration)parameter; String parameterName =
		 * parameterCasted.getName().toString(); String parameterType =
		 * parameterCasted.getType().toString(); MethodParameter parameterReady
		 * = new MethodParameter(parameterType, parameterName); //
		 * System.out.println("Parameter name: " + parameterName + " type: " +
		 * parameterType); signature.addMethodParameters(parameterReady); } }
		 */
		// System.out.println("Parameters: " + parameters);

		return super.visit(method);
	}
	
	
	private void resolveType(Type type, boolean isExtends, boolean isImplements, boolean isClassAnnotation) { 
		 
	    // return null if type == null 
	    if (type == null) { 
	      return; 
	    } 
	 
	    // resolve the type binding 
	    //resolveTypeBinding(type.resolveBinding(), isExtends, isImplements, isClassAnnotation); 
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