import java.util.ArrayList;

public class HourlyData {
	ArrayList<Integer> oneday = new ArrayList<Integer>();
	
	public HourlyData() {
		for(int i =0;i<12;i++) {
			oneday.add(0);
		}
	}
	
	public void addEmployeesolohour (int hour) {
		oneday.set(hour, oneday.get(hour)+1);
	}
	
	public void addEmployeehours (int starthour, int length) {
		System.out.println("HEre");
		for(int i = 0;i<length;i++) {
			addEmployeesolohour(starthour+i);
		}
	}
}
