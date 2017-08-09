import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import au.com.bytecode.opencsv.CSVReader;

public class EmployeeGraph extends JFrame {

	ScheduleData sd;
	
	public EmployeeGraph(ScheduleData sd) {
		super("XY Line Chart Example with JFreechart");
		this.sd = sd;

		JPanel graphPanel = createGraphPanel();
		add(graphPanel, BorderLayout.CENTER);
		setSize(980,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	private JPanel createGraphPanel() {
		String chartTitle = "Average Daily Employees";
		String xAxisLabel = "Hour";
		String yAxisLabel = "Number of employees";

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
			XYSeries series1 = new XYSeries("Your Company");
			
			for(int i =0;i<sd.averageday.size();i++) {
				series1.add(i,sd.averageday.get(i));
			}


			dataset.addSeries(series1);
			
		return dataset;
	}
}
