package viewClassDiagram;
import java.awt.Color;

import javax.swing.*;

public class StartClassDiagram extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StartClassDiagram() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		super("Class Diagram");
		setContentPane(new DrawClassDiagraam());

	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    setSize(400, 400);
	    setBackground(Color.BLACK);
	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);// set max. size
	    setVisible(true); 
	}
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		new StartClassDiagram();
	}
}
