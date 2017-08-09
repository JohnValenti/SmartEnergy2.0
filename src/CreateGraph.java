//import some form of graphing library
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

public class CreateGraph extends JFrame{
	ScheduleData schedule;
	//EnergyData energy;
	
	
    public CreateGraph (ScheduleData sd)//, EnergyData ed)
    {
        this.schedule = sd;
        
        //this.energy = ed;
    }

    public void GraphData()
    {
        XYSeries series = new XYSeries("Employee shift data");
        series.add(20.0,10.0);
        series.add(40.0, 20.0);
        series.add(70.0, 50.0);
        XYDataset xyDataset = new XYSeriesCollection(series);
        
        JFreeChart chart = ChartFactory.createXYAreaChart(
        		 "Sample XY Chart", // Title
        		 "Height", // X-Axis label
        		 "Weight", // Y-Axis label
        		 xyDataset // Dataset
        		 //true // Show legend
        		 );
        try {
        	ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\jvalenti\\SmartPower\\SmartPower\\ScheduleData\\chart.jpg"), chart, 500, 300);
        	} catch (IOException e) {
        	System.err.println("Problem occurred creating chart.");
        	}
    }
    
}
