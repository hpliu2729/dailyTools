package study.extend;

public class Son extends Father{

	private String name;
	
	public void printName(){
		System.out.println(this.name);
	}
	
	public Son(){
		this.name = "son";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
