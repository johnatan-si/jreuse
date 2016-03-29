package Model;

import java.util.ArrayList;

public class Parameters {

	private ArrayList<Integer> loc = new ArrayList<Integer>();
	private ArrayList<String> nameMethod = new ArrayList<String>();
	private ArrayList<String> visib = new ArrayList<String>();
	private ArrayList<String> typ = new ArrayList<String>();
	int numberMethods;
	
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
}
