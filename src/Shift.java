import java.util.Calendar;
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
	
	public Date AddADay() {
		Calendar plus1 = Calendar.getInstance();
		plus1.setTime(starttime);
		plus1.add(Calendar.DAY_OF_MONTH,1);
		return plus1.getTime();
	}
	
	//returns hour length of shift
	public int LengthOfShift() {
		int length = 0;
		length = endtime.getHours()-starttime.getHours();
		if(length<0) {//if length is negative shift is over 2 days
			length = 24 + length;
		}
		return length;
	}
}