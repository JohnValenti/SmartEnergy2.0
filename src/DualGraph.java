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
public class DualGraph extends JFrame {

	ScheduleData sd;
	EnergyData ed;
	
	public DualGraph(ScheduleData sd, EnergyData ed){
		super("Costings");
		this.sd = sd;
		this.ed = ed;
		
		JPanel graphPanel = createGraphPanel();
		add(graphPanel, BorderLayout.CENTER);
		setBounds(100,100,980,720);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	private JPanel createGraphPanel() {
		String chartTitle = "Daily Electricty Prices vs Employees";
		String xAxisLabel = "Hour";
		String yAxisLabel = "GBP/MWh || No. of Employees || GBP";

		XYSeriesCollection dataset = createData();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// sets thickness for series (using strokes)
		renderer.setStroke(new BasicStroke(3.0f));
		XYPlot plot = chart.getXYPlot();
		plot.setRenderer(renderer);
		return new ChartPanel(chart);
	}

	public XYSeriesCollection createData() {
		 XYSeriesCollection dataset = new XYSeriesCollection();

			// Set up series
			XYSeries series1 = new XYSeries("Average Energy Prices");
			XYSeries series2 = new XYSeries("Number of Employees");
			XYSeries series3 = new XYSeries("Estimation of Energy Costs");

			for(int i = 0;i<ed.August.size();i++) {
				series1.add(i, ed.average.get(i));
				series2.add(i,sd.averageday.get(i));
				double cost = ed.average.get(i) * sd.averageday.get(i)*0.2;
				series3.add(i, cost);
			}
			
			dataset.addSeries(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);

		return dataset;
	}
}