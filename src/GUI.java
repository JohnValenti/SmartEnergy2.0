import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
	Boolean gooddata = false;
	ScheduleData sd;
	EnergyData ed;
	DecimalFormat df = new DecimalFormat("0.0000");
	
	
	
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
		  txtfilename = new JTextField(20);
		  txtfilename.setText("C:\\Users\\jvalenti\\SmartPower\\SmartPower\\Data\\GOAT.txt");
		  cmdfile = new JButton("Choose File");
		  lblgng = new JLabel("No good");
		  lblgng.setForeground(Color.RED);
		  filepane.add(txtfilename);
		  filepane.add(cmdfile);
		  filepane.add(lblgng);
		  
		  cmdfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setFileChosen(!gooddata);
			}
			  
		  });
				  
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
							new ScheduleGraph(sd);
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
							if(parsed&&vary>0&&vary<24) {
								new GUIText(anal.changeSchedules(vary));
							}else {
								JOptionPane.showMessageDialog(null, "Varience must be a number >0 && <24", "Doy", JOptionPane.INFORMATION_MESSAGE);
							}
							
						}
						if(butnewschedule.isSelected()) {
							//get new schedule
							//pass into schedule graph
							//pop up with total saving
							if(parsed&&vary>0&&vary<24) {
								ArrayList<String> terriblecodingpractice = anal.changeSchedules(vary);
								ScheduleData nsd = new ScheduleData();
								nsd.employees = anal.getNewSchedule();
								nsd.setDates(sd.startofweek, sd.endofweek);
								nsd.setDataPerHour();
								new EmployeeGraph(nsd);
								new ScheduleGraph(nsd);
								terriblecodingpractice.clear();
								double prev = anal.getTotalAverageDay(sd.averageday);
								double new1 = anal.getTotalAverageDay(nsd.averageday);
								terriblecodingpractice.add("Previous Average Daily Electricity: "+ df.format(prev)+ " Mw/hours");
								terriblecodingpractice.add("New Average Daily Electricity: "+ df.format(new1)+ " Mw/hours");
								terriblecodingpractice.add("Average Daily Saving: "+df.format(prev-new1)+ " Mw/hours");
								//new GUIText(terriblecodingpractice);
							}else {
								JOptionPane.showMessageDialog(null, "Varience must be a number >0 && <13", "Doy", JOptionPane.INFORMATION_MESSAGE);
							}
							
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
	  
	  public void setFileChosen(Boolean b) {
		  gooddata = b;
		  if(b) {
			  lblgng.setForeground(Color.GREEN);
			  lblgng.setText("Good!");
		  }else {
			  lblgng.setForeground(Color.RED);
			  lblgng.setText("No good");
		  }
	  }
	  
	  

}
