package Model;

import java.util.ArrayList;

public class Parameters {

	
	int numberMethods,idClass, idMethod, idAttribute,locMethod, numberAttribute;
	String visibilityMethod,nameMethod,returnTypeMethod,nameAttribute,visibilityAttribute,typeAttribute, nameClass,nameProject,absolutePath;
	Boolean isGetSet;
	
	public String getVisibilityAttribute() {
		return visibilityAttribute;
	}
	public void setVisibilityAttribute(String visibilityAttribute) {
		this.visibilityAttribute = visibilityAttribute;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getNameProject() {
		return nameProject;
	}
	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}
	public String getNameClass() {
		return nameClass;
	}
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
	public int getNumberAttribute() {
		return numberAttribute;
	}
	public void setNumberAttribute(int numberAttribute) {
		this.numberAttribute = numberAttribute;
	}
	public String getNameAttribute() {
		return nameAttribute;
	}
	public void setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}
	public String getTypeAttribute() {
		return typeAttribute;
	}
	public void setTypeAttribute(String typeAttribute) {
		this.typeAttribute = typeAttribute;
	}
	public int getNumberMethods() {
		return numberMethods;
	}
	public void setNumberMethods(int numberMethods) {
		this.numberMethods = numberMethods;
	}
	public int getIdClass() {
		return idClass;
	}
	public void setIdClass(int idClass) {
		this.idClass = idClass;
	}
	public int getIdMethod() {
		return idMethod;
	}
	public void setIdMethod(int idMethod) {
		this.idMethod = idMethod;
	}
	public int getIdAttribute() {
		return idAttribute;
	}
	public void setIdAttribute(int idAttribute) {
		this.idAttribute = idAttribute;
	}
	public int getLocMethod() {
		return locMethod;
	}
	public void setLocMethod(int locMethod) {
		this.locMethod = locMethod;
	}
	public String getVisibilityMethod() {
		return visibilityMethod;
	}
	public void setVisibilityMethod(String visibilityMethod) {
		this.visibilityMethod = visibilityMethod;
	}
	public String getNameMethod() {
		return nameMethod;
	}
	public void setNameMethod(String nameMethod) {
		this.nameMethod = nameMethod;
	}
	public String getReturnTypeMethod() {
		return returnTypeMethod;
	}
	public void setReturnTypeMethod(String returnTypeMethod) {
		this.returnTypeMethod = returnTypeMethod;
	}
	public Boolean getIsGetSet() {
		return isGetSet;
	}
	public void setIsGetSet(Boolean isGetSet) {
		this.isGetSet = isGetSet;
	}
	
}
