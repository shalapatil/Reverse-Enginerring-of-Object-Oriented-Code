package viewClassDiagram;

import java.awt.*;

public class classPattern implements classMeasurements{
	
	public Label className;
	public List variable;
	public List funtion;
	
	
	void draw(int x,int y)
	{
		className.setBounds(x,y,200,20);
		variable.setBounds(10,30,200,100);
		funtion.setBounds(10,131,200,100);
		
	}
	
	

}
