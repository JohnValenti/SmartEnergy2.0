
public class Program {

	public static void main(String[] args) {
		ScheduleData sd = new ScheduleData();
		ScheduleReader sr = new ScheduleReader(sd,"C:\\Users\\jvalenti\\SmartPower\\SmartPower\\ScheduleData\\Schedule.txt");
		sr.ParseFile();
		sd.PrintEmployees();
	}

}
