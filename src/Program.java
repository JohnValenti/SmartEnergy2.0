import java.io.IOException;

public class Program {

	public static void main(String[] args) {
		ScheduleData sd = new ScheduleData();
		ScheduleReader sr = new ScheduleReader(sd,"C:\\Users\\jvalenti\\SmartPower\\SmartPower\\Data\\WorldXI.txt");
		
		EnergyData ed = new EnergyData();
		EnergyReader er = new EnergyReader(ed,"C:\\Users\\jvalenti\\SmartPower\\SmartPower\\Data\\NordElectrictyPrices.csv");
		
		
		try {
			sr.ParseFile();
			er.ParseFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//sd.FakeDataPerHour();
		sd.setDataPerHour();
		//EnergyGraph eg1 = new EnergyGraph(ed);
		//EmployeeGraph eg = new EmployeeGraph(sd);
		//DualGraph dg = new DualGraph(sd,ed);
		//Analytics anal = new Analytics(sd,ed); 
		//anal.BaseAnalytics();
		//anal.changeSchedules();
		GUI gooey = new GUI(sd,ed);
	}

}
 