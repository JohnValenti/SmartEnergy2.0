import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Analytics {
	ScheduleData sd;
	EnergyData ed;
	private static double COSTPEREMPLOYEEHOUR = 0.4; 
	ArrayList<Double> costings = new ArrayList<Double>();
	//private static double CONSTANTPRICE = 1000; 
	int averageservicelevel = 0;
	int offset = 1;
	DateFormat datetime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
	
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
	
	public void BaseAnalytics() {
		System.out.println("Average Service Level: "+averageservicelevel);
		System.out.println("-----------------------");
		System.out.println("***ELECTRICITY DATA***");
		System.out.println("Most Expensive hour of electricity is: "+getMax(ed.average)+":00");
		System.out.println("Cheapest hour of electricity is: "+getMin(ed.average)+":00");
		System.out.println("The cheapest electricity price (where employees>0): "+getMinEmployeeNonZero(ed.average)+":00");
		System.out.println("The cheapest electicity price (with above average service level): "+ getMinAboveAverageService(ed.average)+ ":00");		
		System.out.println("***COMPANY RUNNING COSTS***");
		System.out.println("Most Expensive hour is: "+getMax(costings)+":00");
		System.out.println("The cheapest hour (where employees>0): "+getMinEmployeeNonZero(costings)+":00");
		System.out.println("The cheapest hour (with above average service level): "+ getMinAboveAverageService(costings)+ ":00");
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
	
	public void changeSchedules() {
		ArrayList<String> suggestions = new ArrayList<String>();
		ArrayList<Employee> newshifts = new ArrayList<Employee>();
		for(int e =0;e<sd.employees.size();e++) {
			for(int s=0;s<sd.employees.get(e).shifts.size();s++) {
				//for each shift in each employee
				//check x hour befor, check x hour after
				int starthour =sd.employees.get(e).shifts.get(s).starttime.getHours();
				int endhour =sd.employees.get(e).shifts.get(s).starttime.getHours();
				boolean sameday =(sd.employees.get(e).shifts.get(s).starttime.getDate()==sd.employees.get(e).shifts.get(s).endtime.getDate());
				if(starthour>offset&&sameday&&endhour+offset<24) {
					for(int i=1; i<=offset;i++) {
						double leftshift = ed.average.get(endhour) - ed.average.get(starthour-i);
						double rightshift = ed.average.get(starthour) - ed.average.get(endhour+i);
						if(leftshift>0||rightshift>0) {
							if(leftshift>rightshift) {
								suggestions.add(sd.employees.get(e).name +"'s shift on "+datetime.format(sd.employees.get(e).shifts.get(s).starttime)+" would save "+leftshift+" £/MWh if started 1 hour earlier");
							}else {
								suggestions.add(sd.employees.get(e).name +"'s shift on "+datetime.format(sd.employees.get(e).shifts.get(s).starttime)+" would save "+rightshift+" £/MWh if started 1 hour later");
							}
						}
					}


				}else {
					//need to check day before
				}
			}
		}
		System.out.println("---SCHEDULE SUGGESTIONS---");
		for(int i = 0; i<suggestions.size();i++) {
			System.out.println(suggestions.get(i));
		}
		
	}
	

}
