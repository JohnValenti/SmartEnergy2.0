
public class Analytics {
	ScheduleData sd;
	EnergyData ed;
	private static double COSTPEREMPLOYEEHOUR = 100; //in pence
	//private static double CONSTANTPRICE = 1000; 
	
	public Analytics(ScheduleData sd, EnergyData ed) {
		this.sd = sd;
		this.ed = ed;
		getPrice();
	}
	
	public void getPrice() {
		double daycost = 0;
		for(int i = 0;i<sd.averageday.size();i++) {

			daycost = daycost+ (sd.averageday.get(i)*ed.August.get(i)*COSTPEREMPLOYEEHOUR);
		}
		//daycost = daycost + CONSTANTPRICE;
		System.out.println("the electricity cost for running 1 average day in August = £"+ String.format("%1$,.2f", (daycost/100)));
	}
}
