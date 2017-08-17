import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIText extends JFrame{
	 JPanel pane = new JPanel();
	 ArrayList<String> data;
	 JList lstdata;
	 DefaultListModel model = new DefaultListModel();
	
	public GUIText(ArrayList<String> data) // the frame constructor method
	  {
	    super("SMART POWER"); 
	    setBounds(300,300,750,900);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.data = data;
	    lstdata = new JList(model);
	    lstdata.setFont(new Font(Font.SANS_SERIF,java.awt.Font.PLAIN,16));
	    


	    Populate();
	    JScrollPane sp = new JScrollPane(lstdata,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
	    sp.setPreferredSize(new Dimension(700, 850));
	    pane.add(sp);
	    this.add(pane);
	    setVisible(true); // display this frame
	  }
	
	public void Populate() {
		for(int i =0;i<data.size();i++) {
			model.addElement(data.get(i));
		}
	}
}
