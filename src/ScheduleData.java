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
	HashMap<String,ArrayList<Integer>> weeklydata = new HashMap<String,ArrayList<Integer>>();
	ArrayList<Integer> averageday = new ArrayList<Integer>();
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
			ArrayList<Integer> arr =new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
			weeklydata.put(date.format(d.getTime()),arr);
		}
	}
	
	public void setDataPerHour() {
		initDataPerHour();
		for(int e = 0;e<employees.size();e++) {
			for(int s=0;s<employees.get(e).shifts.size();s++) {
				if((employees.get(e).shifts.get(s).starttime.getDate()==employees.get(e).shifts.get(s).endtime.getDate())){
					int starthour = employees.get(e).shifts.get(s).starttime.getHours();
					int numberofhours = employees.get(e).shifts.get(s).endtime.getHours()-employees.get(e).shifts.get(s).starttime.getHours();
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp = weeklydata.get(date.format(employees.get(e).shifts.get(s).starttime));
					for(int i =starthour;i<starthour+numberofhours;i++) {
						temp.set(i, temp.get(i)+1);
					}
					weeklydata.put(date.format(employees.get(e).shifts.get(s).starttime), temp);

				}else {
					//System.out.println(employees.get(e).name + "'s Shift is over 2 days we need to code for this edge case");
					int starthour = employees.get(e).shifts.get(s).starttime.getHours();
					int numberofhours = 24+employees.get(e).shifts.get(s).endtime.getHours()-employees.get(e).shifts.get(s).starttime.getHours();
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp = weeklydata.get(date.format(employees.get(e).shifts.get(s).starttime));
					ArrayList<Integer> tempnextday = new ArrayList<Integer>();
					tempnextday = weeklydata.get(date.format(employees.get(e).shifts.get(s).AddADay()));
					for(int i =starthour;i<starthour+numberofhours;i++) {
						if(i<=23) {
							temp.set(i, temp.get(i)+1);
						}else {
							if (tempnextday!=null) {
								tempnextday.set(i-24, tempnextday.get(i-24)+1);
							}
						}
					}
					weeklydata.put(date.format(employees.get(e).shifts.get(s).starttime), temp);
					weeklydata.put(date.format(employees.get(e).shifts.get(s).AddADay()), tempnextday);
				}
				
			}
		}
		setAverageDay();
	}
	
	public void setAverageDay() {
		ArrayList<Integer> runningsum = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
		int count = 0;
		Iterator it = weeklydata.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ArrayList<Integer> temp = (ArrayList<Integer>) pair.getValue();
	        for(int i = 0; i<temp.size();i++) {
	        	runningsum.set(i, runningsum.get(i)+temp.get(i));
	        }
	        count++;
	    }
	    for(int i = 0;i<runningsum.size();i++) {
	    	runningsum.set(i, runningsum.get(i)/count);
	    }
	    averageday = runningsum;
	}
	
	
	
	public void FakeDataPerHour() {
		/*Calendar start = Calendar.getInstance();
		start.setTime(startofweek);
		Calendar end = Calendar.getInstance();
		end.setTime(endofweek);
		for (Calendar d = start;d.compareTo(end)<=0;d.add(Calendar.DAY_OF_MONTH, 1)){
			ArrayList<Integer> arr =new ArrayList<Integer>(Arrays.asList(0,0,3,5,8,8,8,6,4,3,2,0));
			weeklydata.put(d.getTime(),arr);
		}
		*/
		averageday =new ArrayList<Integer>(Arrays.asList(0,0,0,0,1,1,1,3,3,4,8,9,9,9,10,10,7,7,2,1,1,0,0,0));
	}
	
	public void printHashMap() {
		Iterator it = weeklydata.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	    }
	}
}
