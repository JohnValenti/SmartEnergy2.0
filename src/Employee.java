import java.util.ArrayList;
import java.util.Date;

public class Employee {
	String name;
	ArrayList<Shift> shifts = new ArrayList<Shift>();
	
	
	void setName(String str) {
		name = str;
	}
	
	//needed??
	void setShifts(ArrayList<Shift> shifts ) {
		this.shifts = shifts;
	}
	
	void addShift(Date starttime, Date endtime) {
		shifts.add(new Shift(starttime, endtime));
	}
}
