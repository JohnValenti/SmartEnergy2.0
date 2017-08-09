import java.util.ArrayList;

public class EnergyData {
	ArrayList<Double> August;
	ArrayList<Double> July;
	ArrayList<Double> June;
	ArrayList<Double> May;
	ArrayList<Double> April;
	ArrayList<Double> March;
	ArrayList<Double> February;
	ArrayList<Double> January;
	ArrayList<Double> average;
	
	public void setArrays(ArrayList<Double> aug,ArrayList<Double> jul,ArrayList<Double> jun,ArrayList<Double> may,ArrayList<Double> apr,ArrayList<Double> mar,ArrayList<Double> feb,ArrayList<Double> jan) {
		August = aug;
		July = jul;
		June = jun;
		May = may;
		April = apr;
		March = mar;
		February = feb;
		January = jan;
		average = new ArrayList<Double>();
		setAverageArray();
	}
	
	public void setAverageArray() {

		for(int i =0;i<August.size();i++) {
			double total = August.get(i)+July.get(i)+June.get(i)+May.get(i)+April.get(i)+March.get(i)+February.get(i)+January.get(i);
			total = total / 8;
			average.add(i, total);
		}
	}
}
