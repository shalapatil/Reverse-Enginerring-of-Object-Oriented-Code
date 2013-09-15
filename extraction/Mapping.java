package extraction;

public class Mapping {
	public String s_class;
	public String s_fun;
	public String d_class;
	public String d_fun;
	public int id;

	void print_mapping() {
		System.out.println(s_fun + "() [" + s_class + "] => " + d_fun + "() ["
				+ d_class + "]");
		
		
		

	}

}
