package extraction;

import java.util.Vector;

public class Function {
	public String type;
	public String name;
	public String access;
	public boolean static1;
	public boolean final1;
	public boolean const1;
	public Vector<Parameter1> parameter = new Vector<Parameter1>();
	public Vector<Parameter1> localVar = new Vector<Parameter1>();

	public Function() {
		access = "default";
	}

	public void print_function_details() {
		System.out.println("\nFunction name :: " + name);
		System.out.println("Data Type :: " + type);
		System.out.println("Access specifier :: " + access);
		System.out.println("Static ? :: " + static1);
		System.out.println("Final ? :: " + final1);
		System.out.println("Const ? : " + const1);

		for (int i = 0; i < parameter.size(); i++) {
			System.out.print(parameter.get(i).type + "  "
					+ parameter.get(i).name + " , ");

		}

	}
}