import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;

import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.IntervalXYDataset;



public class ScheduleGraph extends JFrame {
	ArrayList<Employee> schedule;
	ScheduleData sd;
	ArrayList<String> empName = new ArrayList<String>();
	
	public ScheduleGraph(ScheduleData sd) {
		super("The Schedule");
		//createSchedule();
		 this.sd = sd;
        JPanel graphPanel = createDemoPanel();
        add(graphPanel, BorderLayout.CENTER);
        setSize(980, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
	}


    public JFreeChart createChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYBarChart("Employee Shifts", "Resource", false, "Timing", dataset,
                PlotOrientation.HORIZONTAL, true, true, false);

        chart.setBackgroundPaint(Color.white);
        for (int e = 0; e < sd.employees.size(); e++) {
            empName.add(sd.employees.get(e).name);
        }
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRangePannable(true);
        String[] arr = new String[sd.employees.size()];
        for (int e = 0; e < sd.employees.size(); e++) {
            arr[e] = sd.employees.get(e).getName();
        }
        SymbolAxis xAxis = new SymbolAxis("Employee", arr);

        xAxis.setGridBandsVisible(true);
        plot.setDomainAxis(xAxis);
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setMargin(-2);
        renderer.setUseYInterval(true);
        plot.setRangeAxis(new DateAxis("Time"));
        ChartUtilities.applyCurrentTheme(chart);

        return chart;
    }

    /**
     * Creates a panel for the demo.
     *
     * @return A panel.
     */
    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    /**
     * Creates the sample dataset.
     *
     * @return The sample dataset.
     */
    public IntervalXYDataset createDataset() {
        sd.averageday.size();
        return new XYTaskDataset(createTasks());
    }

    /**
     * Creates a collection of tasks.
     *
     * @return A collection of tasks.
     */
    public TaskSeriesCollection createTasks() {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        for (int e = 0; e < sd.employees.size(); e++) {
            TaskSeries s1 = new TaskSeries(sd.employees.get(e).getName() );
            for (int s = 0; s < sd.employees.get(e).shifts.size(); s++) {
                s1.add(new Task("", new SimpleTimePeriod(sd.employees.get(e).shifts.get(s).starttime.getTime(),
                        sd.employees.get(e).shifts.get(s).endtime.getTime())));
                s1.add(new Task("", new SimpleTimePeriod(sd.employees.get(e).shifts.get(s).starttime.getTime(),
                        sd.employees.get(e).shifts.get(s).endtime.getTime())));
                s1.add(new Task("", new SimpleTimePeriod(sd.employees.get(e).shifts.get(s).starttime.getTime(),
                        sd.employees.get(e).shifts.get(s).endtime.getTime())));
                s1.add(new Task("", new SimpleTimePeriod(sd.employees.get(e).shifts.get(s).starttime.getTime(),
                        sd.employees.get(e).shifts.get(s).endtime.getTime())));
                s1.add(new Task("", new SimpleTimePeriod(sd.employees.get(e).shifts.get(s).starttime.getTime(),
                        sd.employees.get(e).shifts.get(s).endtime.getTime())));
            }
            dataset.add(s1);
        }

        return dataset;
    }
}