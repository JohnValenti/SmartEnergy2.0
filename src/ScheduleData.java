import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleData {
	Date startofweek;
	Date endofweek;
	ArrayList<Employee> employees = new ArrayList<Employee>();
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
}
