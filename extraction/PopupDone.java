package extraction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import GUI.StartWindow;

public class PopupDone extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public JButton okbtn;
	public PopupDone(){
		super("DONE ");
		//JLabel donelbl=new JLabel("Extraction Done Successfully...!");
		okbtn=new JButton("Extraction Done Successfully");
		
		//add(donelbl);
		add(okbtn);
		okbtn.addActionListener(this);
		setLocation(400, 300);
		setSize(300,100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    new PopupDone();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource().equals(okbtn)){
		//SplashScreen.windowSelectionflag=1;
		try {
			StartWindow.mainwindow.addTabbedPanes();
			
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.dispose();
		}
	}

}
