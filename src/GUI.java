import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame{
	JPanel pane = new JPanel();
	JPanel filepane; 
	JTextField txtfilename;
	JButton cmdfile;
	JLabel lblgng;
	JPanel bottomholder;
	JPanel graphpane;
	//radio buttons
	JCheckBox butenergy;
	JCheckBox butemployees;
	JCheckBox energyvemployees;
	JButton cmdgraph;
	JPanel smartpane;
	//radiobuttons
	JCheckBox butanalytics;
	JCheckBox butsuggestions;
	JCheckBox butnewschedule;
	JTextField txtvary;
	JButton cmdsmart;
	Boolean gooddata = true;
	ScheduleData sd;
	EnergyData ed;
	
	
	
	  public GUI(ScheduleData sd, EnergyData ed) // the frame constructor method
	  {
	    super("SMART POWER"); 
	    setBounds(100,100,600,350);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Container con = this.getContentPane(); // inherit main frame
	    this.sd =sd;
	    this.ed=ed;
	    FilePane();
	    pane.add(filepane);
	    GraphPane();
	    SmartPane();
	    bottomholder = new JPanel();
	    bottomholder.add(graphpane);
	    bottomholder.add(smartpane);
	    // customize panel here
	    // pane.add(someWidget);
	    pane.add(bottomholder);
	    this.add(pane);
	    setVisible(true); // display this frame
	  }
	   
	  
	  
	  public void FilePane() {
		  filepane = new JPanel();
		  txtfilename = new JTextField("filename.txt goes here");
		  cmdfile = new JButton("Choose File");
		  lblgng = new JLabel("No good");
		  lblgng.setForeground(Color.RED);
		  filepane.add(txtfilename);
		  filepane.add(cmdfile);
		  filepane.add(lblgng);
	  }
	  
	  public void GraphPane() {
		  graphpane = new JPanel();
		  graphpane.setLayout(new BoxLayout(graphpane,BoxLayout.PAGE_AXIS));
		  butenergy = new JCheckBox("Energy Data"); 
		  butemployees = new JCheckBox("Employee Schedules");
		  energyvemployees = new JCheckBox("Energy vs Employees vs Costings"); ;
		  cmdgraph = new JButton("Graphage!");
		  cmdgraph.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(gooddata) {
						if(butenergy.isSelected()) {
							EnergyGraph eg1 = new EnergyGraph(ed);
						}
						if(butemployees.isSelected()) {
							EmployeeGraph eg = new EmployeeGraph(sd);
						}
						if(energyvemployees.isSelected()) {
							DualGraph dg = new DualGraph(sd,ed);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Please import schedule data", "Doy", JOptionPane.INFORMATION_MESSAGE);
					}
				}
		       });

		  graphpane.add(new JLabel("   "));
		  graphpane.add(new JLabel("   "));
		  graphpane.add(butenergy);
		  graphpane.add(butemployees);
		  graphpane.add(energyvemployees);
		  graphpane.add(cmdgraph);
	  }
	  
	  public void SmartPane() {
		  smartpane = new JPanel();
		  smartpane.setLayout(new BoxLayout(smartpane,BoxLayout.PAGE_AXIS));
		  butanalytics = new JCheckBox("Analytics");
		  txtvary = new JTextField(2);
		  JPanel midflow = new JPanel();
		  midflow.add(new JLabel("Schedule Varience +/-"));
		  midflow.add(txtvary);
		  midflow.add(new JLabel(" hours"));
		  butsuggestions = new JCheckBox("Schedule Suggestions");
		  butnewschedule = new JCheckBox("Create New Schedule");
		  cmdsmart = new JButton("Do Smart Things!");
		  cmdsmart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(gooddata) {
						Analytics anal = new Analytics(sd,ed); 
						int vary = -1;
						boolean parsed = false;
						//try parse
						try {
							vary = Integer.parseInt(txtvary.getText());
							parsed = true;
						}catch(NumberFormatException e){
							
						}
						if(butanalytics.isSelected()) {
							new GUIText(anal.BaseAnalytics());
						}
						if(butsuggestions.isSelected()) {
							if(parsed&&vary>=0&&vary<24) {
								new GUIText(anal.changeSchedules(vary));
							}else {
								JOptionPane.showMessageDialog(null, "Varience must be a number >=0 && <24", "Doy", JOptionPane.INFORMATION_MESSAGE);
							}
							
						}
						if(butnewschedule.isSelected()) {
							//graph new schedule
							
						}
					}else {
						JOptionPane.showMessageDialog(null, "Please import schedule data", "Doy", JOptionPane.INFORMATION_MESSAGE);
					}
				}
		       });
		  smartpane.add(midflow);
		  smartpane.add(butanalytics);
		  smartpane.add(butsuggestions);
		  smartpane.add(butnewschedule);
		  smartpane.add(cmdsmart);
	  }
	  
	  

}
