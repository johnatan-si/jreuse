package Model;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;

public class MethodDeclarationVisitor extends ASTVisitor {

	private int numberMethods;
	private String visibility;
	private CompilationUnit compilation;
	private int startLine;
	private int endLine;

	private boolean isGetSet;

	public Parameters parametersMethod;

	@Override
	public boolean visit(MethodDeclaration method) {

		try {
			if (!(method.getBody() == null)) {
				startLine = compilation.getLineNumber(method.getBody().getStartPosition());
				endLine = compilation.getLineNumber(method.getBody().getStartPosition() + method.getBody().getLength());
				parametersMethod.setLocMethod(endLine - startLine - 1);
			} else {
				// param.setLocMethod(endLine - startLine - 1);
				parametersMethod.locMethod = endLine - startLine - 1;

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
			parametersMethod.setVisibilityMethod(visibility.toString().toLowerCase());
			parametersMethod.setNameMethod(method.getName().toString().toLowerCase());

			// verifica se o retorno do método é nulo
			if (!(method.getReturnType2() == null)) {
				parametersMethod.setReturnTypeMethod(method.getReturnType2().toString().toLowerCase());
			} else {

				parametersMethod.setReturnTypeMethod("null");
			}
			numberMethods++;
			isGetSet = isGetSetMethod(method);
			parametersMethod.setIsGetSet(isGetSet);
			parametersMethod.setNumberMethods(numberMethods);

			//System.out.println("numberMethods "+parametersMethod.getNumberMethods()+" "+"locMethod "+parametersMethod.getLocMethod()+" "+" visibilityMethod "+parametersMethod.visibilityMethod+" "+"namemethod "+parametersMethod.nameMethod);
			
		} catch (Exception e) {
			System.out.println("Erro " + e);

			System.out.println("Metodo " + method.getName().toString());
			System.out.println("Type " + method.getReturnType2().toString());
			System.err.println("Erro na classe MethodDeclarationVisitor: " + e + "\n\r");
		}
		//return super.visit(method);
		return true;
	}
	public MethodDeclarationVisitor(CompilationUnit compilation) {
		this.compilation = compilation;
	}
	

	private boolean isVoid(MethodDeclaration method) {
		try {
			PrimitiveType type = (PrimitiveType) method.getReturnType2();
			return type.getPrimitiveTypeCode().equals(PrimitiveType.VOID);
		} catch (Exception e) {
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

	
}