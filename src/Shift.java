import java.util.Date;

public class Shift {
	Date starttime;
	Date endtime;
	
	public Shift(Date start, Date end) {
		this.starttime =start;
		this.endtime = end;
	}
	
	public Date getDateIgnoreTime() {
		Date newdate = starttime;
		newdate.setHours(0);
		newdate.setMinutes(0);
		newdate.setSeconds(0);
		return newdate;
	}
}