package sequence;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StartSequence extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public StartSequence() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
	super("Sequence Diagram");
	setContentPane(new DrawPanel());

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

  // setSize(400, 400);
    
  //  setBackground(Color.gray);
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);// set max. size
    setVisible(true); 
    }

	
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		new StartSequence();
	}
}
