package GUI;

import javax.swing.*;

import java.awt.event.*;


public class StartWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static MainWindow mainwindow;
	private JLabel select_lang;
	private JButton nextbtn;
	private String[] languages1 = {
		     "C++", "Java", "Python",
		    "JavaScript", "Perl", "Ruby", "C#"
		  };
	private JComboBox langs=new JComboBox();
	JFrame frm=new JFrame();
    int count=0;
    String selected_lang;
	
	
	public StartWindow()
	{
		try{
		 for(int i = 0; i < languages1.length; i++)
			   langs.addItem(languages1[count++]);
		initComponents();
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		frm.setSize(380, 270);
		frm.setVisible(true);
		frm.setTitle("Reverse Engineering OOP");
		frm.setLocation(400, 300);
		frm.setResizable(false);
		
	    frm.setDefaultCloseOperation(getDefaultCloseOperation());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void initComponents()
	{
		frm.setLayout(null);
        select_lang=new JLabel("Select your Language");
		select_lang.setBounds(60,60, 140, 60);
		
		langs.setBounds(210, 80, 90, 25);
		
		nextbtn=new JButton("Next");
		nextbtn.setBounds(210, 170, 90, 32);
		nextbtn.addActionListener(this);
		
		
		frm.add(select_lang);
		frm.add(nextbtn);
		frm.add(langs);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==nextbtn)
		{
			try{
			    mainwindow=new MainWindow();
			    this.dispose();
			
			 
			}
			catch(Exception e1){
		
				e1.printStackTrace();
		
				System.out.println(e1.getMessage());
			}
		
			
		}
	}
	
}
