import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class GUIText extends JFrame{
	 JPanel pane = new JPanel();
	 ArrayList<String> data;
	 JList lstdata;
	 DefaultListModel model = new DefaultListModel();
	
	public GUIText(ArrayList<String> data) // the frame constructor method
	  {
	    super("SMART POWER"); 
	    setBounds(300,300,300,700);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.data = data;
	    lstdata = new JList(model);
	    Populate();
	    pane.add(lstdata);
	    this.add(pane);
	    setVisible(true); // display this frame
	  }
	
	public void Populate() {
		for(int i =0;i<data.size();i++) {
			model.addElement(data.get(i));
		}
	}
}
