package GUI;

import java.awt.List;
//import Extraction.extractDetails;
import javax.swing.filechooser.FileFilter;
//import java.awt.Component;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import activity.editor;

import extraction.clearDatabase;
import extraction.extractDetails;

//import acitivity.editor;

import sequence.StartSequence;
import viewClassDiagram.StartClassDiagram;

public class MainWindow implements ActionListener, ChangeListener, KeyListener {

	public JTextArea jta = new JTextArea(5, 1000);
	JFrame frame = new JFrame();
	Vector<?> data, columns;
	JLabel name1;
	JPanel p = new JPanel();

	List l1, l2;
	String s2 = null, s3 = null;

	JTable tabl1 = new JTable() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	JButton jblink;
	JTree tree;
	JScrollPane scrollPane;
	public static String s;
	JFileChooser chooser;
	String choosertitle;

	private ArrayList<Object> list = new ArrayList<Object>();
	public JButton button2;
	private boolean prjchanged;
	private String fname = "";
	private JMenuBar mbar;
	private JMenu mnuFile, mnuView, mnuHelp;
	private JMenu opnprj;
	private JMenuItem presprj;
	private JMenuItem frmdir;
	private JMenuItem dirs;
	private JMenuItem fileSave, fileExit;
	private JMenuItem viewClassDiagram, viewActivityDiagram,
			viewSequenceDiagram;
	private JMenuItem helpAbout;

	Connection conn;
	ResultSet rs, rs1, rs2;
	Statement stat;
	private JToolBar tlbrEditor;
	private JButton bttnNew, bttnOpen, bttnSave;
	private JButton bttnCut, bttnCopy, bttnPaste;

	private ImageIcon icoNew, icoOpen, icoSave;
	private ImageIcon icoCut, icoCopy, icoPaste;
	Container con;

	public MainWindow() throws Exception, NullPointerException {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		initMenu();

		openConn();
		// if(SplashScreen.windowSelectionflag==1)
		// {
		// addTabbedPanes();
		// }

		frame.setTitle("Reverse Engineering OOP");
		frame.setBackground(Color.gray);

		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);// set max. size
		frame.setLocation(200, 70);
		frame.setVisible(true);
		frame.addWindowListener(new WindowHandler());
	}

	void openConn() {
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:project_db");

		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(new JFrame(), sqle + "\n"
					+ "Please contact your system administrator.", "Error",
					JOptionPane.ERROR_MESSAGE);

			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			JOptionPane.showMessageDialog(new JFrame(), cnfe + "\n"
					+ "Please contact your system administrator.", "Error",
					JOptionPane.ERROR_MESSAGE);

			cnfe.printStackTrace();
		}
		finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	void closeConn() throws Exception, NullPointerException {
		try {
			rs.close();
			conn.close();
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(new JFrame(), sqle + "\n"
					+ "Please contact your system administrator.", "Error",
					JOptionPane.ERROR_MESSAGE);

			sqle.printStackTrace();
		}
		finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	void initIcons() {
		icoNew = new ImageIcon("D:/Java1/New_UI/icons/new.gif");
		icoOpen = new ImageIcon("D:/Java1/New_UI/icons/open.gif");

		icoSave = new ImageIcon("D:/Java1/New_UI/icons/save.gif");
		icoCut = new ImageIcon("D:/Java1/New_UI/icons/cut.gif");
		icoCopy = new ImageIcon("D:/Java1/New_UI/icons/copy.gif");
		icoPaste = new ImageIcon("D:/Java1/New_UI/icons/paste.gif");
	}

	void initMenu() {

		mbar = new JMenuBar();

		mnuFile = new JMenu("File");
		mnuView = new JMenu("View");
		mnuHelp = new JMenu("Help");

		opnprj = new JMenu("Create New Project");
		presprj = new JMenuItem("Open Previous Project");

		new JMenu("Add Class Path");

		frmdir = new JMenuItem("from directory...");

		dirs = new JMenuItem("directory...");

//		fileSave = new JMenuItem("Save Project", icoSave);

		fileExit = new JMenuItem("Exit");

		viewClassDiagram = new JMenuItem("View Class Diagram");
		viewActivityDiagram = new JMenuItem("View Activity Diagram");
		viewSequenceDiagram = new JMenuItem("View Sequence Diagram");

		helpAbout = new JMenuItem("About");

		mnuFile.add(opnprj);
		mnuFile.add(presprj);

		opnprj.add(frmdir);

		//mnuFile.add(fileSave);
		mnuFile.addSeparator();
		mnuFile.add(fileExit);

		mnuFile.setMnemonic(KeyEvent.VK_F);

		mnuView.add(viewClassDiagram);
		mnuView.add(viewActivityDiagram);
		mnuView.add(viewSequenceDiagram);

		mnuView.setMnemonic(KeyEvent.VK_V);

		mnuHelp.add(helpAbout);
		mnuHelp.setMnemonic(KeyEvent.VK_H);

		mbar.add(mnuFile);
		mbar.add(mnuView);
		mbar.add(mnuHelp);
		// if(SplashScreen.windowSelectionflag==0){
		mnuView.setVisible(false);
		// }

		frame.setJMenuBar(mbar);

		presprj.addActionListener(this);
		//fileSave.addActionListener(this);

		frmdir.addActionListener(this);

		dirs.addActionListener(this);

		fileExit.addActionListener(this);

		viewActivityDiagram.addActionListener(this);
		viewClassDiagram.addActionListener(this);
		viewSequenceDiagram.addActionListener(this);

		helpAbout.addActionListener(this);
	}

	void initToolbar() {
		bttnNew = new JButton(icoNew);
		bttnOpen = new JButton(icoOpen);
		bttnSave = new JButton(icoSave);

		bttnCut = new JButton(icoCut);
		bttnCopy = new JButton(icoCopy);
		bttnPaste = new JButton(icoPaste);

		tlbrEditor = new JToolBar();
		tlbrEditor.add(bttnNew);
		tlbrEditor.add(bttnOpen);
		tlbrEditor.add(bttnSave);

		tlbrEditor.addSeparator();

		tlbrEditor.add(bttnCut);
		tlbrEditor.add(bttnCopy);
		tlbrEditor.add(bttnPaste);

		bttnNew.addActionListener(this);
		bttnOpen.addActionListener(this);
		bttnSave.addActionListener(this);

		bttnCut.addActionListener(this);
		bttnCopy.addActionListener(this);
		bttnPaste.addActionListener(this);

	}

	public void addTabbedPanes() throws SQLException {
		mnuView.setVisible(true);

		Date dt = new Date();

		new JPanel();

		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:project_db");
			Statement stat = conn.createStatement();

			rs = stat.executeQuery("select * from classes;");

			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();

			Vector<String> columns = new Vector<String>(columnCount);

			// store column names
			for (int i = 1; i <= columnCount; i++)
				columns.add(md.getColumnName(i));

			Vector<Vector<String>> data = new Vector<Vector<String>>();
			Vector<String> row;

			while (rs.next()) {

				row = new Vector<String>(columnCount);
				for (int i = 1; i <= columnCount; i++) {

					row.add(rs.getString(i));
				}
				data.add(row);

			}

			DefaultTableModel tableModel = new DefaultTableModel(data, columns);

			tabl1.setModel(tableModel);
			p.setLayout(new BorderLayout());
			p.setPreferredSize(new Dimension(965, 150));

			JTabbedPane tab1 = new JTabbedPane();

			// openConn();

			list.add("Class Browser");
			try {
				String sql = "select class_name,name from classes,function where classes.class_id=function.fun_id;";

				stat = conn.createStatement();
				rs = stat.executeQuery(sql);

				while (rs.next()) {
					Object value[] = { rs.getString(1), rs.getString(2) };
					list.add(value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			Object hierarchy[] = list.toArray();
			DefaultMutableTreeNode root = processHierarchy(hierarchy);
			tree = new JTree(root);

			tab1.add("Class Browser", new JScrollPane(tree));
			p.add(tab1, BorderLayout.WEST);

			tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					doMouseClicked(me);
				}
			});

			JTabbedPane tab2 = new JTabbedPane();
			JTextArea codeDetails = new JTextArea();

			try {
				FileReader fr = new FileReader("MainWindow.java");
				BufferedReader reader = new BufferedReader(fr);
				while ((reader.readLine()) != null) {

					codeDetails.read(reader, "");
				}
			} catch (IOException ioe) {
				System.err.println(ioe);
				System.exit(1);
			}
			Font myFont = new Font("Serif", Font.BOLD, 12);
			codeDetails.setFont(myFont);
			codeDetails.setEditable(false);
		  
		
			tab2.add("Code Preview", new JScrollPane(codeDetails));
			
			p.add(tab2, BorderLayout.CENTER);

			jta.setText("LOG\n");

			jta.setText(dt.toString()
					+ "::Application started Successfully\nNo Error Found\n");
			jta.setEditable(false);
			Font f = new Font("Times New Roman", Font.PLAIN, 12);
			jta.setFont(f);

			p.add(new JScrollPane(jta), BorderLayout.SOUTH);
			frame.setContentPane(p);
			frame.pack();
		}

		catch (SQLException sqle) {
			JOptionPane.showMessageDialog(new JFrame(), sqle + "\n"
					+ "Please contact your system administrator.", "Error",
					JOptionPane.ERROR_MESSAGE);

			sqle.printStackTrace();
		}

		catch (ClassNotFoundException cnfe) {
			JOptionPane.showMessageDialog(new JFrame(), cnfe + "\n"
					+ "Please contact your system administrator.", "Error",
					JOptionPane.ERROR_MESSAGE);

			cnfe.printStackTrace();
		}
		finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		DefaultMutableTreeNode child = null;
		for (int i = 1; i < hierarchy.length; i++) {
			Object nodeSpecifier = hierarchy[i];
			if (nodeSpecifier instanceof Object[]) {
				child = processHierarchy((Object[]) nodeSpecifier);
			} else {
				child = new DefaultMutableTreeNode(nodeSpecifier);

			}
			node.add(child);
		}
		return (node);
	}

	void doMouseClicked(MouseEvent me) {

		TreePath kk = tree.getSelectionPath();
		kk.toString();

		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());

		
		s2 = (String) tp.toString();

		if (tp != null) {
			s3 = s2.replace(",", "");
			s3 = s3.replace("[", "");
			s3 = s3.replace("]", "");

			s3 = s3.replace("Class Browser", "");
			s3 = s3.replace(" ", "");
			System.out.println("Clicked on:-"+s3);
			processDetails(s3);

		} 
			//System.out.println("Data is");

	}

	public String getTreeDetails(String s1) {
		return s1;
	}

	public void processDetails(String s3) {
		System.out.println("inside function we have=" + s3);
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:project_db");
			Statement statement3 = connection.createStatement();
			s3.toString();

			ResultSet rs = statement3.executeQuery("select * from classes");// where class_name='"+s3+"'");
			int id = 0;
			JTabbedPane tab3 = new JTabbedPane();
			JPanel pnl = new JPanel();
			pnl.setLayout(new BorderLayout());

			while (rs.next()) {

				// System.out.println("inside while");
				id = rs.getInt(1);

				name1 = new JLabel(rs.getString(2));

				addVariableToTabbedPane(id);
				addFunctionToTabbedPane(id);

				pnl.add(name1, BorderLayout.NORTH);
				pnl.add(l1, BorderLayout.CENTER);

				pnl.add(l2, BorderLayout.SOUTH);

				tab3.add("Overview", pnl);
				p.add(tab3, BorderLayout.EAST);

			}
			rs.close();
			statement3.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR:");
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	void addVariableToTabbedPane(int id) {

		String var[] = new String[100];

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:project_db");
			Statement statement = connection.createStatement();

			ResultSet rs = statement
					.executeQuery("select * from variable where class_id=" + id);

			int i = 0;

			while (rs.next()) {

				String access = rs.getString("access");
				if (access.equals("default"))
					var[i] = "~ ";
				else if (access.equals("public"))
					var[i] = "+ ";
				else if (access.equals("private"))
					var[i] = "- ";
				else if (access.equals("protected"))
					var[i] = "#";

				var[i] = var[i].concat(rs.getString("name") + " : ");
				var[i] = var[i].concat(rs.getString("type"));

				i++;

			}
			rs.close();
			statement.close();
			l1 = new List(i);

			for (int k = 0; k < i; k++) {
				l1.add(var[k]);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR:");
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}

	}

	void addFunctionToTabbedPane(int id) {

		String var[] = new String[100];

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:project_db");
			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();

			ResultSet rs = statement
					.executeQuery("select * from function where class_id=" + id);

			int i = 0, fid = 0, t1 = 0;
			String type;
			while (rs.next()) {

				type = rs.getString("type");

				if (type.equals("-")) {

					var[i] = "<<constructor>>";

					i++;

				} else {
					if (t1 == 0) {
						var[i] = "     <<misc>>";

						i++;
						t1++;
					}

				}

				fid = rs.getInt("fun_id");
				String access = rs.getString("access");

				if (access.equals("default"))
					var[i] = "~ ";
				else if (access.equals("public"))
					var[i] = "+ ";
				else if (access.equals("private"))
					var[i] = "- ";
				else if (access.equals("protected"))
					var[i] = "# ";

				var[i] = var[i].concat(rs.getString("name") + "( ");

				{

					ResultSet rs1 = statement1
							.executeQuery("select * from parameter where fun_id="
									+ fid);
					int t = 0;
					while (rs1.next()) {
						if (t > 0)
							var[i] = var[i].concat("," + rs.getString("name")
									+ " : " + type);
						else
							var[i] = var[i].concat(rs.getString("name") + " : "
									+ type);
						t++;
					}

					rs1.close();
					statement1.close();

				}

				var[i] = var[i].concat(") : " + rs.getString("type"));

				i++;

			}
			rs.close();

			statement.close();
			l2 = new List(i);

			for (int k = 0; k < i; k++) {
				l2.add(var[k]);

			}

		} catch (SQLException e) {

			System.out.println("ERROR:");
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(frmdir)) {
			clearDatabase c1 = new clearDatabase();
			try {
				c1.clearDB();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle(choosertitle);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			chooser.addChoosableFileFilter(new FileFilter() {

				public String getDescription() {
					return "Java Files (*.java)";
				}

				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					} else {
						if(f.getName().toLowerCase().endsWith(".java"))
						return f.getName().toLowerCase().endsWith(".java");
						else
						{
							new errorPopup(f.getName());
							return false;
						}
					}
				}
			});

			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				s = chooser.getSelectedFile().getAbsolutePath();
				System.out.println("Path is:=" + s);
				new extractDetails("h");

			} else {
				System.out.println("No Selection ");
			}

		}

		/*else if (e.getSource().equals(fileSave)
				|| e.getSource().equals(bttnSave)) {
			fSave();
		}*/ else if (e.getSource().equals(helpAbout)) {
			// new Help();

		}

		else if (e.getSource().equals(viewActivityDiagram)) {

			try {
				new editor();
			} catch (ClassNotFoundException e1) {

				e1.printStackTrace();
			}

		} else if (e.getSource().equals(viewClassDiagram)) {

			try {
				new StartClassDiagram();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// classTrial t=new classTrial("Class Diagram");

		} else if (e.getSource().equals(viewSequenceDiagram)) {
			// DrawSequence d=new DrawSequence();
			try {
				new StartSequence();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(presprj)) {
			try {
				StartWindow.mainwindow.addTabbedPanes();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(fileExit)) {

			fExit();
		}

	}

	void fNew() {
		int ch;
		if (prjchanged == true) {
			ch = JOptionPane.showConfirmDialog(null, // parent
					"Do You Want To Save Changes", // message
					"File Open", // title
					JOptionPane.YES_NO_CANCEL_OPTION // message type
					);

			if (ch == JOptionPane.YES_OPTION) {
				fSave();
			} else if (ch == JOptionPane.NO_OPTION) {

			} else {
				return;
			}
		}
		prjchanged = false;

	}

	void fOpen() {
		int ch;
		if (prjchanged == true) {
			ch = JOptionPane.showConfirmDialog(null, // parent
					"Do You Want To Save Changes", // message
					"File Open", // title
					JOptionPane.YES_NO_CANCEL_OPTION // message type

					);

			if (ch == JOptionPane.YES_OPTION) {
				fSave();
			} else if (ch == JOptionPane.NO_OPTION) {

			} else {
				return;
			}
		}
		JFileChooser jfc = new JFileChooser();
		ch = jfc.showOpenDialog(null);
		if (ch == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getCurrentDirectory();

			String path = f.getAbsolutePath();

			fRead(path);
		}
	}

	void fSave() {
		if (fname.length() == 0) {
			int ch;
			JFileChooser jfc = new JFileChooser();
			ch = jfc.showSaveDialog(null);// this : parent
			if (ch == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				String path = f.getAbsolutePath();
				fWrite(path);
			}
		} else
			fWrite(fname);
	}

	void fWrite(String path) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.flush();
			bw.close();

			fname = path;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, // parent
					"File Save Error", // title
					"Error " + e.getMessage(), // message
					JOptionPane.ERROR_MESSAGE // error type
					);
		}

	}

	void fRead(String path) {
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String data;

			while ((data = br.readLine()) != null) {
				data = data + "\n";

			}
			br.close();

			fname = path;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, // parent
					"File Open Error", // title
					"Error " + e.getMessage(), // message
					JOptionPane.ERROR_MESSAGE // error type
					);
		}

	}

	void fExit() {
		int ch;
		ch = JOptionPane.showConfirmDialog(null, // parent
				"Do You Want To Save Changes", // message
				"File Exit", // title
				JOptionPane.YES_NO_CANCEL_OPTION // message type
				);

		if (ch == JOptionPane.YES_OPTION) {
			fSave();
			frame.dispose();
		} else if (ch == JOptionPane.NO_OPTION) {

			frame.dispose();
		} else // cancel
		{
			return;
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {

	}

	class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			fExit();
		}
	}

	public void setText(String s1) {

	}
}
