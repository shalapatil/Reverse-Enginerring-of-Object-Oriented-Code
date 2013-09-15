package extraction;

public class JavaGrammer {

	static String class1="class";
    static String class_name = "[a-zA-Z][a-zA-Z0-9_$]*";
    static String keyword =" for| while| do| System|out|println|for";
    static String datatype = " boolean|char| byte| short| int| long| float| double| void|int ";
    static String extra="public|private|protected|abstract|final|static|const"; 
    static String class_end = "}";
    static String extend ="extends";
    static String implement ="implements";
    static String any ="[a-z A-Z0-9_$\\.,\\*]*";    
    static String name = "[a-zA-Z0-9_$\\.\\*]*";
    static String is_fun = "\\(";
    static String fun_call = "\\.";    
    static String end_fun = "\\)";  
    static String block_start = "\\{";
    static String block_end = "\\}";
    static String mem_allocation = "new";
    static String template="[<>]";
    static String end_statment=";";
    static String new_statment="\\n";
    public static String listenerMethod[]={"getActionCommand","itemStateChanged"};
    public static String ignoreMethod[]={"equals"};
    
    
    
}

