package GUI;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import extraction.extractDetails;
//import Extraction.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DemoJFileChooser extends JPanel
   implements ActionListener {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JButton go;
   public static String s;
   JFileChooser chooser;
   String choosertitle;
   
  public DemoJFileChooser() {
    go = new JButton("Do it");
    go.addActionListener(this);
    add(go);
   }

  public void actionPerformed(ActionEvent e) {
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
   
   chooser.addChoosableFileFilter( new FileFilter(){
	   
	    public String getDescription() {
	        return "Java Files (*.java)";
	    }
	 
	    public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        } else {
	            return f.getName().toLowerCase().endsWith(".java");
	        }
	    }
	});

	
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());

      s=chooser.getSelectedFile().getAbsolutePath();
      new extractDetails("hello");
    //  MainWindow.flag
      }
    else {
      System.out.println("No Selection ");
      }
     }
   
  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }
    
  public static void main(String s[]) {
	  
    JFrame frame = new JFrame("");
    DemoJFileChooser panel = new DemoJFileChooser();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }
}