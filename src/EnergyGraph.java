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
public class EnergyGraph extends JFrame {

	EnergyData ed;
	
	public EnergyGraph(EnergyData ed){
		super("Energy Prices");
		this.ed = ed;

		JPanel graphPanel = createGraphPanel();
		add(graphPanel, BorderLayout.CENTER);
		setSize(980,720);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	private JPanel createGraphPanel() {
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

	public XYSeriesCollection createData() {
		 XYSeriesCollection dataset = new XYSeriesCollection();

			// Set up series
			XYSeries series1 = new XYSeries("August");
			XYSeries series2 = new XYSeries("July");
			XYSeries series3 = new XYSeries("June");
			XYSeries series4 = new XYSeries("May");
			XYSeries series5 = new XYSeries("April");
			XYSeries series6 = new XYSeries("March");
			XYSeries series7 = new XYSeries("February");
			XYSeries series8 = new XYSeries("January");

			for(int i = 0;i<ed.March.size();i++) {
				series1.add(i+1, ed.August.get(i));
				series2.add(i+1, ed.July.get(i));
				series3.add(i+1, ed.June.get(i));
				series4.add(i+1, ed.May.get(i));
				series5.add(i+1, ed.April.get(i));
				series6.add(i+1, ed.March.get(i));
				series7.add(i+1, ed.February.get(i));
				series8.add(i+1, ed.January.get(i));
			}
			dataset.addSeries(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);
			dataset.addSeries(series4);
			dataset.addSeries(series5);
			dataset.addSeries(series6);
			dataset.addSeries(series7);
			dataset.addSeries(series8);

		return dataset;
	}
}