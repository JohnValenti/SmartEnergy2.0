import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Analytics {
	ScheduleData sd;
	EnergyData ed;
	private static double COSTPEREMPLOYEEHOUR = 1; 
	ArrayList<Double> costings = new ArrayList<Double>();
	//private static double CONSTANTPRICE = 1000; 
	int averageservicelevel = 0;
	int offset = 3;
	DateFormat datetime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
	ArrayList<Employee> newshifts = new ArrayList<Employee>();
	DecimalFormat df = new DecimalFormat("0.0000"); 
	
	public Analytics(ScheduleData sd, EnergyData ed) {
		this.sd = sd;
		this.ed = ed;
		setCostings();
	}
	
	public void setOffset(int i) {
		offset = i ;
	}
	
	public void setCostings() {
		double daycost = 0;
		for(int i = 0;i<sd.averageday.size();i++) {
			costings.add(sd.averageday.get(i)*ed.average.get(i));
			averageservicelevel = averageservicelevel+ sd.averageday.get(i);
		}
		averageservicelevel = averageservicelevel/sd.averageday.size();
	}
	
	public ArrayList<String> BaseAnalytics() {
		ArrayList<String> tempo = new ArrayList<String>();
		tempo.add("Average Hourly Service Level: "+averageservicelevel);
		tempo.add("-----------------------");
		tempo.add("Daily Electricity: "+getTotal()+" MW/hours");
		//weekly leecy/?
		tempo.add("-----------------------");
		tempo.add("***ELECTRICITY DATA***");
		tempo.add("Most Expensive hour of electricity is: "+getMax(ed.average)+":00");
		tempo.add("Cheapest hour of electricity is: "+getMin(ed.average)+":00");
		tempo.add("The cheapest electricity price (where employees>0): "+getMinEmployeeNonZero(ed.average)+":00");
		tempo.add("The cheapest electicity price (with above average service level): "+ getMinAboveAverageService(ed.average)+ ":00");		
		tempo.add("***COMPANY RUNNING COSTS***");
		tempo.add("Most Expensive hour is: "+getMax(costings)+":00");
		tempo.add("The cheapest hour (where employees>0): "+getMinEmployeeNonZero(costings)+":00");
		tempo.add("The cheapest hour (with above average service level): "+ getMinAboveAverageService(costings)+ ":00");
		return tempo;
	}
	
	public double getTotal() {
		double total = 0;
		for(int i = 0;i<24;i++) {
			total = total + sd.averageday.get(i)*ed.average.get(i);
		}
		return total;
	}
	
	public int getMax(ArrayList<Double> lst) {
		double max = lst.get((0));
		int position = 0;
		for(int i =1;i<lst.size();i++) {
			if(lst.get(i)>max) {
				max = lst.get(i);
				position = i;
			}
		}
		return position;
	}
	
	public int getMin(ArrayList<Double> lst) {
		double min = lst.get((0));
		int position = 0;
		for(int i =1;i<lst.size();i++) {
			if(lst.get(i)<min) {
				min = lst.get(i);
				position = i;
			}
		}
		return position;
	}
	
	public int getMinEmployeeNonZero(ArrayList<Double> lst) {
		int position = -1;
		for(int i= 0; i<lst.size();i++) {
			if(lst.get(i)>0) {
				position = i;
				break;
			}
		}
		if(position>=0) {
			double min = lst.get(position);
			for(int i =position+1;i<lst.size();i++) {
				if(sd.averageday.get(i)>0) {
					if(lst.get(i)<min) {
						min = lst.get(i);
						position = i;
					}
				}
			}
		}else {
			return -1;//no value>0
		}
		return position;
	}
	
	public int getMinAboveAverageService(ArrayList<Double> lst) {
		int position = -1;
		for(int i= 0; i<lst.size();i++) {
			if(sd.averageday.get(i)>averageservicelevel) {
				position = i;
				break;
			}
		}
		if(position>=0) {
			double min = lst.get(position);
			for(int i =position+1;i<lst.size();i++) {
				if(sd.averageday.get(i)>averageservicelevel) {
					if(lst.get(i)<min) {
						min = lst.get(i);
						position = i;
					}
				}
			}
		}else {
			return -1;//no value>average
		}
		return position;
	}
	
	public ArrayList<String> changeSchedules(int vary) {
		offset = vary;
		ArrayList<String> suggestions = new ArrayList<String>();
		suggestions.add("---SCHEDULE SUGGESTIONS---");
		for(int e =0;e<sd.employees.size();e++) {
			Employee newdude = new Employee();
			newdude.setName(sd.employees.get(e).name);
			newshifts.add(newdude);
			for(int s=0;s<sd.employees.get(e).shifts.size();s++) {
				//for each shift in each employee
				//check x hour befor, check x hour after
				int starthour =sd.employees.get(e).shifts.get(s).starttime.getHours();
				int endhour =sd.employees.get(e).shifts.get(s).starttime.getHours();
				boolean sameday =(sd.employees.get(e).shifts.get(s).starttime.getDate()==sd.employees.get(e).shifts.get(s).endtime.getDate());
				boolean backaday = false;
				//if(starthour>offset&&sameday&&endhour+offset<24) {
					//left or right
					//number of days 
					double left = 0;
					double right = 0;
					double leftholder =0;
					double rightholder=0;
					int numberofshiftholder = 0;
					for(int i=1; i<=offset;i++) {
						int leftlefttocheck = endhour-i+1;
						int leftrighttocheck = starthour-i;
						int rightlefttocheck = starthour+i-1;
						int rightrighttocheck = endhour+i;
						if(leftlefttocheck <0) {
							leftlefttocheck = leftlefttocheck +24;
							backaday = true;
						}
						if(leftrighttocheck <0) {
							leftrighttocheck = leftrighttocheck +24;
							backaday = true;
						}
						if(rightrighttocheck >23) {
							rightrighttocheck = rightrighttocheck - 24;
						}
						if(rightlefttocheck>23) {
							rightlefttocheck = rightlefttocheck - 24;
						}
						leftholder =leftholder + ed.average.get(leftlefttocheck) - ed.average.get(leftrighttocheck);
						rightholder = rightholder +ed.average.get(rightlefttocheck) - ed.average.get(rightrighttocheck);
						
						if(leftholder>left) {
							left = leftholder;
							numberofshiftholder = i;
						}
						if(rightholder>right) {
							right = rightholder;
							numberofshiftholder = i;
						}
					}
					if(numberofshiftholder>0) {
						if(left>right) {
							
							suggestions.add(sd.employees.get(e).name +"'s shift on "+datetime.format(sd.employees.get(e).shifts.get(s).starttime)+" would save "+df.format(left)+" £/MWh if started "+numberofshiftholder+" hour earlier");
							numberofshiftholder = -numberofshiftholder;
						}else {
							suggestions.add(sd.employees.get(e).name +"'s shift on "+datetime.format(sd.employees.get(e).shifts.get(s).starttime)+" would save "+df.format(right)+" £/MWh if started "+numberofshiftholder+" hour later");
						}
						Calendar cal = Calendar.getInstance();
						// remove next line if you're always using the current time.
						cal.setTime(sd.employees.get(e).shifts.get(s).starttime);
						cal.add(Calendar.HOUR_OF_DAY, +numberofshiftholder);
						Date newstart = cal.getTime();
						cal.setTime(sd.employees.get(e).shifts.get(s).endtime);
						cal.add(Calendar.HOUR_OF_DAY, +numberofshiftholder);
						Date newend = cal.getTime();
						//new shift shifted left
						newshifts.get(e).addShift(newstart, newend);
					}
				//}else {
					//need to check day before
				//}
			}
		}
		return suggestions;
		//System.out.println("---SCHEDULE SUGGESTIONS---");
		//for(int i = 0; i<suggestions.size();i++) {
			//System.out.println(suggestions.get(i));
		//}
		/*
		System.out.print("---NEW EMPLOYEE SCHEDULES---");
		for(int i = 0;i<newshifts.size();i++) {
			System.out.println("Name: "+newshifts.get(i).name);
			for(int j = 0; j<newshifts.get(i).shifts.size();j++) {
				System.out.println("Shift: "+datetime.format(newshifts.get(i).shifts.get(j).starttime)+" - "+datetime.format(newshifts.get(i).shifts.get(j).endtime));
			}
		}
		*/
	}
	
	public ArrayList<Employee> getNewSchedule() {
		return newshifts;
	}
	
	public double getTotalAverageDay(ArrayList<Integer> averageday) {
		double total = 0;
		for(int i =0;i<ed.average.size();i++) {
			total = total +((ed.average.get(i)*3)*averageday.get(i));
		}
		return total;
	}
	

}
