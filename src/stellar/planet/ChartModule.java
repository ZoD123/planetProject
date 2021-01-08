package stellar.planet;

// Plot import

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import stellar.ResourceDTO.ResourcePlotDTO;
import stellar.ResourceDTO.SingleResourceDataDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChartModule extends JFrame {


    private ArrayList<XYSeries> seriesList;


    /**
     * public constructor - creates a chart module with the necessary information
     *
     * @param resourcePlotDTO Data which has to be printed.
     */
    public ChartModule(ResourcePlotDTO resourcePlotDTO) {
        XYSeries currentSeries;
        seriesList = new ArrayList<XYSeries>();
        SingleResourceDataDTO singleResourceDataDTO;
        String className;
        HashMap<Integer, Integer> measureMap;

        for (Map.Entry<String, SingleResourceDataDTO> plotData : resourcePlotDTO.plotData.entrySet()) {
            className = plotData.getKey();
            singleResourceDataDTO = plotData.getValue();
            measureMap = singleResourceDataDTO.measureMap;
            currentSeries = new XYSeries(className);

            for (Map.Entry<Integer, Integer> measureData : measureMap.entrySet()) {
                currentSeries.add(measureData.getKey(), measureData.getValue());
            }
            seriesList.add(currentSeries);
        }

    }


    /**
     * show the chart
     */
    public void showChart() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Resource Graph");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * create dataset and add serieses to the set
     *
     * @return
     */
    private XYDataset createDataset() {

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (XYSeries series : seriesList
        ) {
            dataset.addSeries(series);
        }
        return dataset;
    }


    /**
     * create the chart depending on the given dataset
     *
     * @param dataset
     * @return the chart object
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Resource Graph",
                "Age",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        // renderer.setSeriesPaint(0, Color.RED);
        // renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);


        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("planet",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }
}
