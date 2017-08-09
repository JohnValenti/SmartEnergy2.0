import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import au.com.bytecode.opencsv.CSVReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@SuppressWarnings("serial")
public class PriceGraph extends JFrame {

	String filename;
	
	public PriceGraph(String filename) throws IOException {
		super("XY Line Chart Example with JFreechart");
		this.filename = filename;

		JPanel graphPanel = createGraphPanel();
		add(graphPanel, BorderLayout.CENTER);
		setSize(980,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	@SuppressWarnings("deprecation")
	private JPanel createGraphPanel() throws NumberFormatException, IOException {
		String chartTitle = "Daily Electricty Prices";
		String xAxisLabel = "Hour";
		String yAxisLabel = "GBP/MWh";

		XYSeriesCollection dataset = createData();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// sets thickness for series (using strokes)
		renderer.setStroke(new BasicStroke(3.0f));
		XYPlot plot = chart.getXYPlot();
		plot.setRenderer(renderer);
		return new ChartPanel(chart);
	}

	public XYSeriesCollection createData() throws NumberFormatException, IOException {
		 XYSeriesCollection dataset = new XYSeriesCollection();
		try {
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
				double August = Double.valueOf(readNextLine[1]);
				double July = Double.valueOf(readNextLine[2]);
				double June = Double.valueOf(readNextLine[3]);
				double May = Double.valueOf(readNextLine[4]);
				double April = Double.valueOf(readNextLine[5]);
				double March = Double.valueOf(readNextLine[6]);
				double February = Double.valueOf(readNextLine[7]);
				double January = Double.valueOf(readNextLine[8]);

				series1.add(Hour, August);
				series2.add(Hour, July);
				series3.add(Hour, June);
				series4.add(Hour, May);
				series5.add(Hour, April);
				series6.add(Hour, March);
				series7.add(Hour, February);
				series8.add(Hour, January);
			}


			dataset.addSeries(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);
			dataset.addSeries(series4);
			dataset.addSeries(series5);
			dataset.addSeries(series6);
			dataset.addSeries(series7);
			dataset.addSeries(series8);
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		return dataset;
	}
}