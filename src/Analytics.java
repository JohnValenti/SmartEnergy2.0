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
			costings.add(sd.averageday.get(i)*ed.average.get(i));
			averageservicelevel = averageservicelevel+ sd.averageday.get(i);
		}
		averageservicelevel = averageservicelevel/sd.averageday.size();
	}
	
	public void brain() {
		System.out.println("Average Service Level: "+averageservicelevel);
		System.out.println("Most Expensive hour is: "+getMax(costings)+":00");
		System.out.println("Most Expensive hour of leccy is: "+getMax(ed.average)+":00");
		System.out.println("The cheapest hour (where employees>0): "+getMinEmployeeNonZero(costings)+":00");
		System.out.println("The cheapest hour (with above average service level): "+ getMinAboveAverageService(costings)+ ":00");
	}
	
	public int getMax(ArrayList<Double> lst) {
		double max = lst.get((0));
		int position = 0;
		for(int i =1;i<lst.size();i++) {
			if(lst.get(i)>max) {
				max = lst.get(i);
				position = i;
			}
		}
		return position;
	}
	
	public int getMinEmployeeNonZero(ArrayList<Double> lst) {
		int position = -1;
		for(int i= 0; i<lst.size();i++) {
			if(lst.get(i)>0) {
				position = i;
				break;
			}
		}
		if(position>=0) {
			double min = lst.get(position);
			for(int i =position+1;i<lst.size();i++) {
				if(sd.averageday.get(i)>0) {
					if(lst.get(i)<min) {
						min = lst.get(i);
						position = i;
					}
				}
			}
		}else {
			return -1;//no value>0
		}
		return position;
	}
	
	public int getMinAboveAverageService(ArrayList<Double> lst) {
		int position = -1;
		for(int i= 0; i<lst.size();i++) {
			if(sd.averageday.get(i)>averageservicelevel) {
				position = i;
				break;
			}
		}
		if(position>=0) {
			double min = lst.get(position);
			for(int i =position+1;i<lst.size();i++) {
				if(sd.averageday.get(i)>averageservicelevel) {
					if(lst.get(i)<min) {
						min = lst.get(i);
						position = i;
					}
				}
			}
		}else {
			return -1;//no value>average
		}
		return position;
	}
	//***max hour***
	//cheapest hour with employees - companies cheapiest hour
	
	//***cheapest hour with above average employees***
	//*cheapest electricity price with non zero employees*
	//*cheapest electricity price with above average employees*
	
	//max hour with below average employees
	
	
}
