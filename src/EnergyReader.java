import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;

import au.com.bytecode.opencsv.CSVReader;

public class EnergyReader implements IFileReader{

	File file;
	EnergyData ed;
	String filename;
	ArrayList<Double> August = new ArrayList<Double>();
	ArrayList<Double> July = new ArrayList<Double>();
	ArrayList<Double> June = new ArrayList<Double>();
	ArrayList<Double> May = new ArrayList<Double>();
	ArrayList<Double> April = new ArrayList<Double>();
	ArrayList<Double> March = new ArrayList<Double>();
	ArrayList<Double> February = new ArrayList<Double>();
	ArrayList<Double> January = new ArrayList<Double>();
	
	public EnergyReader(EnergyData ed, String filename) {
		this.ed = ed;
		this.filename = filename;
	}
	
	@Override
	public File getFile() {
		return new File(filename);
	}

	@Override
	//TODO
	public void ParseFile() throws IOException {
			CSVReader reader = new CSVReader(new FileReader(filename), ',');
			// Read the header and chuck it away
			String[] readNextLine = reader.readNext();

			// Set up series
			XYSeries series1 = new XYSeries("August");
			XYSeries series2 = new XYSeries("July");
			XYSeries series3 = new XYSeries("June");
			XYSeries series4 = new XYSeries("May");
			XYSeries series5 = new XYSeries("April");
			XYSeries series6 = new XYSeries("March");
			XYSeries series7 = new XYSeries("February");
			XYSeries series8 = new XYSeries("January");


			while ((readNextLine = reader.readNext()) != null) {
				// add values to dataset
				double Hour = Double.valueOf(readNextLine[0]);
				double dAugust = Double.valueOf(readNextLine[1]);
				double dJuly = Double.valueOf(readNextLine[2]);
				double dJune = Double.valueOf(readNextLine[3]);
				double dMay = Double.valueOf(readNextLine[4]);
				double dApril = Double.valueOf(readNextLine[5]);
				double dMarch = Double.valueOf(readNextLine[6]);
				double dFebruary = Double.valueOf(readNextLine[7]);
				double dJanuary = Double.valueOf(readNextLine[8]);

				August.add(dAugust);
				July.add(dJuly);
				June.add(dJune);
				May.add(dMay);
				April.add(dApril);
				March.add(dMarch);
				February.add(dFebruary);
				January.add(dJanuary);

			}
			
			ed.setArrays(August, July, June, May, April, March, February, January);

			reader.close();
		
	}

}
