import java.util.ArrayList;

public class Analytics {
	ScheduleData sd;
	EnergyData ed;
	private static double COSTPEREMPLOYEEHOUR = 0.4; //in pence
	ArrayList<Double> costings = new ArrayList<Double>();
	//private static double CONSTANTPRICE = 1000; 
	int averageservicelevel = 0;
	
	public Analytics(ScheduleData sd, EnergyData ed) {
		this.sd = sd;
		this.ed = ed;
		setCostings();
	}
	
	public void setCostings() {
		double daycost = 0;
		for(int i = 0;i<sd.averageday.size();i++) {
			costings.add(sd.averageday.get(i)*ed.August.get(i));
			averageservicelevel = averageservicelevel+ sd.averageday.get(i);
		}
		averageservicelevel = averageservicelevel/sd.averageday.size();
	}
	//***max hour***
	//cheapest hour with employees - companies cheapiest hour
	
	//***cheapest hour with above average employees***
	//*cheapest electricity price with non zero employees*
	//*cheapest electricity price with above average employees*
	
	//max hour with below average employees
	
	
}
