package extraction;

public class Variable {
	public String type;
	public String name;
	public String access;
	public boolean static1;
	public boolean final1;
	public boolean abstract1;
	public boolean const1;

	public Variable() {
		access = "default";

	}

	public void print_variable_details() {
		System.out.println("\nVariable name :: " + name);
		System.out.println("Data Type :: " + type);
		System.out.println("Access specifier :: " + access);

		System.out.println("Static ? :: " + static1);
		System.out.println("Final ? :: " + final1);
		System.out.println("Abstract ? : " + abstract1);
		System.out.println("Const ? : " + const1);

	}

}