import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.time.LocalDate;

public class ScheduleData {
	Date startofweek;
	Date endofweek;
	ArrayList<Employee> employees = new ArrayList<Employee>();
	//HourlyData hourlydata;
	HashMap<Date,ArrayList<Integer>> weeklydata = new HashMap<Date,ArrayList<Integer>>();
	DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	DateFormat datetime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
	
	
	public void addEmployee(Employee e) {
		employees.add(e);
	}
	
	public void PrintEmployees() {
		System.out.println("For Week "+date.format(startofweek)+" - "+date.format(endofweek));
		for(int i =0;i<employees.size();i++) {
			System.out.println("##################");
			System.out.println("Name: "+employees.get(i).name);
			for(int j=0;j<employees.get(i).shifts.size();j++) {
				System.out.println("Shift: "+datetime.format(employees.get(i).shifts.get(j).starttime)+" - "+datetime.format(employees.get(i).shifts.get(j).endtime));
			}
		}
	}
	
	public void setDates(Date start, Date end) {
		this.startofweek = start;
		this.endofweek = end;
	}
	
	
	public void initDataPerHour() {
		
		Calendar start = Calendar.getInstance();
		start.setTime(startofweek);
		Calendar end = Calendar.getInstance();
		end.setTime(endofweek);
		for (Calendar d = start;d.compareTo(end)<=0;d.add(Calendar.DAY_OF_MONTH, 1)){
			ArrayList<Integer> arr =new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0,0,0,0,0,0,0,0));
			weeklydata.put(d.getTime(),arr);
			System.out.println("donehashmap");
		}
	}
	
	public void setDataPerHour() {
		initDataPerHour();
		printHashMap();
		for(int e = 0;e<employees.size();e++) {
			for(int s=0;s<employees.get(e).shifts.size();s++) {
				if((employees.get(e).shifts.get(s).starttime.getDate()==employees.get(e).shifts.get(s).endtime.getDate())){
					int starthour = employees.get(e).shifts.get(s).starttime.getHours();
					int numberofhours = employees.get(e).shifts.get(s).endtime.getHours()-employees.get(e).shifts.get(s).starttime.getHours();
					System.out.println(starthour);
					System.out.println(numberofhours);
					ArrayList<Integer> temp = weeklydata.get(employees.get(e).shifts.get(s).starttime);//.addEmployee(starthour, numberofhours);
					System.out.println(employees.get(e).shifts.get(s).starttime);
					int a = 0;
					System.out.println(temp.size());
					System.out.println(a);
					/*
					for(int i= starthour;i<numberofhours;i++) {
						int q =temp.get(i);
						temp.add(i,q);
					}
					*/
					weeklydata.put(employees.get(e).shifts.get(s).starttime,temp);
					//System.out.println(starthour);
					//System.out.println(numberofhours);
					//temp.addEmployeesolohour(starthour);
					//weeklydata.put(employees.get(e).shifts.get(s).starttime, temp);
					//weeklydata.get(employees.get(e).shifts.get(s).starttime.getDate()).addEmployee(starthour, numberofhours);
				}else {
					System.out.println(employees.get(e).name + "'s Shift is over 2 days we need to code for this edge case");
				}
				
			}
		}
	}
	
	public void FakeDataPerHour() {
		Calendar start = Calendar.getInstance();
		start.setTime(startofweek);
		Calendar end = Calendar.getInstance();
		end.setTime(endofweek);
		for (Calendar d = start;d.compareTo(end)<=0;d.add(Calendar.DAY_OF_MONTH, 1)){
			ArrayList<Integer> arr =new ArrayList<Integer>(Arrays.asList(0,0,3,5,8,8,8,6,4,3,2,0));
			weeklydata.put(d.getTime(),arr);
		}
	}
	
	public void printHashMap() {
		Iterator it = weeklydata.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        	System.out.println(pair.getKey() + " = " + pair.getValue());
	        
	        it.remove();
	    }
	    

	}
}
