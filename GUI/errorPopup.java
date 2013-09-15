package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class errorPopup extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public JButton okbtn;
	public errorPopup(String name){
		super("DONE ");
		//JLabel donelbl=new JLabel("Extraction Done Successfully...!");
		okbtn=new JButton("Extracting "+name+"\nError while extracting...");
		
		//add(donelbl);
		add(okbtn);
		okbtn.addActionListener(this);
		setLocation(400, 300);
		setSize(300,100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource().equals(okbtn)){
		//SplashScreen.windowSelectionflag=1;
		
			this.dispose();
		}
	}

}
