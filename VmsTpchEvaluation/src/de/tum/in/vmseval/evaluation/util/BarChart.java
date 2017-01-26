package de.tum.in.vmseval.evaluation.util;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.*;
import java.util.Arrays;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;

public class BarChart {
   
    // TODO: move to gnuPlot java API - http://javaplot.panayotis.com/
    
    public static String[] plotThroughputSummary(int tpchScaleFactor, String throughputSummary) throws IOException {
       
        //Ã¾Sum#Sum#localhost#2344Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0Ã¦0â†
        String summaryStatsString = throughputSummary.split("#")[3];
        summaryStatsString = summaryStatsString.substring(0, summaryStatsString.length() - 3);
        String[] summaryParts = summaryStatsString.split("Ã¦");
        System.setProperty("java.awt.headless", "true");
        
        /*
            totalUpdates: 3             // 0
            avgThroughput: 1            // 1          

            bu.totalUpdates: 3          //  2
            bu.throughput: 0
            bu.avgThroughput: 1         // 4
            bu.avgLatency: 115930       // 5

            vr.totalUpdates: 4,         // 6
            vr.avgThroughput: 1         // 7
            vr.avgLatency: 42963        // 8

            vu.totalUpdates: 1          // 9
            vu.avgThroughput: 1         // 10
            vu.avgLatency: 166914       // 11

            statisticsSent.totalUpdates: 0, 0, 0, 0, //12

            projection.totalUpdates: 1  // 16
            projection.avgLatency: 0    // 17

            delta.totalUpdates: 0,      // 18
            delta.avgLatency: 0,        // 19
        
            preAggregation.totalUpdates: 0, // 20
            preAggregation.avgLatency: 0    // 21
        */
                
       
        int width = 400; 
        int height = 400;
       
        // plot totalUpdates: start
        DefaultCategoryDataset totalUpdatesDataset = new DefaultCategoryDataset();
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[0]), "total", "total");
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[2]), "bu", "bu");
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[6]), "vr", "vr");
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[9]), "vu", "vu");
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[16]), "projetion", "projection");
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[18]), "delta", "delta");
        totalUpdatesDataset.addValue(Integer.parseInt(summaryParts[20]), "preAggregation", "preAggregation");

        JFreeChart totalUpdatesBarChart = ChartFactory.createBarChart(
          "totalUpdates@"+tpchScaleFactor+"x", 
          "category", "updates", 
          totalUpdatesDataset, PlotOrientation.HORIZONTAL, 
          false, false, false);
        
        CategoryPlot cplot = (CategoryPlot)totalUpdatesBarChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.inactiveCaption);

        ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        BarRenderer r = (BarRenderer)totalUpdatesBarChart.getCategoryPlot().getRenderer();
        r.setPaint(Color.BLUE);

        ChartUtilities.saveChartAsJPEG(new File("vms_eval_totalUpdates@"+tpchScaleFactor+"x.jpeg"), totalUpdatesBarChart, width, height);
        // plot totalUpdates: end
        
        // plot avgThroughput: start
        DefaultCategoryDataset avgThroughputDataset = new DefaultCategoryDataset();
        avgThroughputDataset.addValue(Integer.parseInt(summaryParts[1]), "total", "total");
        avgThroughputDataset.addValue(Integer.parseInt(summaryParts[4]), "bu", "bu");
        avgThroughputDataset.addValue(Integer.parseInt(summaryParts[7]), "vr", "vr");
        avgThroughputDataset.addValue(Integer.parseInt(summaryParts[10]), "vu", "vu");
        
        JFreeChart avgThroughputBarChart = ChartFactory.createBarChart(
          "avgThroughput@"+tpchScaleFactor+"x", 
          "category", "avgThroughput", 
          avgThroughputDataset, PlotOrientation.HORIZONTAL, 
          false, false, false);
        
        cplot = (CategoryPlot)avgThroughputBarChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.inactiveCaption);

        ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        
        r = (BarRenderer)avgThroughputBarChart.getCategoryPlot().getRenderer();
        r.setPaint(Color.GREEN);

        ChartUtilities.saveChartAsJPEG(new File("vms_eval_avgThroughput@"+tpchScaleFactor+"x.jpeg"), avgThroughputBarChart, width, height);
        // plot avgThroughput: end

        // plot avgLatency: start
        DefaultCategoryDataset avgLatencyDataset = new DefaultCategoryDataset();
        avgLatencyDataset.addValue(Integer.parseInt(summaryParts[5]), "bu", "bu");
        avgLatencyDataset.addValue(Integer.parseInt(summaryParts[8]), "vr", "vr");
        avgLatencyDataset.addValue(Integer.parseInt(summaryParts[11]), "vu", "vu");
        avgLatencyDataset.addValue(Integer.parseInt(summaryParts[17]), "projection", "projection");
        avgLatencyDataset.addValue(Integer.parseInt(summaryParts[19]), "delta", "delta");
        avgLatencyDataset.addValue(Integer.parseInt(summaryParts[21]), "preAggregation", "preAggregation");
        
        JFreeChart avgLatencyBarChart = ChartFactory.createBarChart(
          "avgLatency@"+tpchScaleFactor+"x", 
          "category", "avgLatency", 
          avgLatencyDataset, PlotOrientation.HORIZONTAL, 
          false, false, false);
        
        cplot = (CategoryPlot)avgLatencyBarChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.inactiveCaption);

        ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        
        r = (BarRenderer)avgLatencyBarChart.getCategoryPlot().getRenderer();
        r.setPaint(Color.CYAN);

        ChartUtilities.saveChartAsJPEG(new File("vms_eval_avgLatency@"+tpchScaleFactor+"x.jpeg"), avgLatencyBarChart, width, height);
        // plot avgLatency: end
        
        return summaryParts;
   }

}