package activity;

import java.awt.List;
import javax.swing.JInternalFrame;

public class activityBlock extends JInternalFrame {

	private static final long serialVersionUID = 4986486453330999795L;
	/**
	 * this List holds the all function call withing particular activty block
	 */

	List funCall;
	static int count = 1;
	int id = 0;

	/*
	 * this constructor creates basic activty block
	 */

	public activityBlock(String name, final List l, int x, int y, int ID) {
		// super(name);
		super(name, true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		id = ID;
		count++;
		funCall = l;
		add(funCall);

		functionCallHandler h = new functionCallHandler(this);
		funCall.addMouseListener(h);

		setSize(200, 200);
		// Set the window's location.
		setLocation(x, y);

	}

	public void stop() {
		stop();

	}

}
