//package edu.chip.carranet.carradatapipeline.pipeline.obfuscation;
//
//
//import org.cdisc.ns.odm.v1.ItemData;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.DateAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.edu.chip.carranet.data.time.Day;
//import org.jfree.edu.chip.carranet.data.time.TimeSeries;
//import org.jfree.edu.chip.carranet.data.time.TimeSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
//import org.joda.time.DateTime;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
//public class PatientChartDiffer extends ApplicationFrame {
//
//    private int i = 0;
//    private Button nextButton = new Button("next");
//    private Button prevButton = new Button("prev");
//    private Button writeResults = new Button("Write Results");
//
//    private JPanel panel = new JPanel(new BorderLayout());
//    private ChartPanel chartPanel1;
//    private JFreeChart chart1;
//
//    private List<String> patientKeys = new ArrayList<String>();
//    private Map<String, Patient> patients;
//    private Map<String, Patient> oldPatients;
//
//
//    public PatientChartDiffer(String title, Map<String, Patient> patients,
//                              Map<String, Patient> oldPatients) {
//        super(title);
//        this.patients = patients;
//        this.oldPatients = oldPatients;
//
//        panel.add(writeResults);
//
//
//        if (patients.size() == 0 || oldPatients.size() == 0 ||
//                patients.size() != oldPatients.size()) {
//            return;
//        }
//
//        setContentPane(panel);
//
//        patientKeys.addAll(patients.keySet());
//        nextButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                if (i < patientKeys.size()) {
//                    nextChart();
//                }
//            }
//        });
//
//        prevButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                if (i < patientKeys.size()) {
//                    prevChart();
//                }
//            }
//        });
//        updateChart(patients.get(patientKeys.get(0)), oldPatients.get(patientKeys.get(0)));
//        panel.add(nextButton, BorderLayout.EAST);
//        panel.add(prevButton, BorderLayout.WEST);
//        prevButton.setEnabled(false);
//        nextButton.setLocation(this.getWidth() - nextButton.getWidth(), 0);
//
//
//    }
//
//
//    private void nextChart() {
//        i++;
//        nextButton.setEnabled(true);
//        prevButton.setEnabled(true);
//
//        if (i == patientKeys.size() - 1) {
//            nextButton.setEnabled(false);
//        }
//        String key = patientKeys.get(i);
//        updateChart(patients.get(key), oldPatients.get(key));
//    }
//
//    private void prevChart() {
//        i--;
//        nextButton.setEnabled(true);
//        prevButton.setEnabled(true);
//        if (i == 0) {
//            prevButton.setEnabled(false);
//        }
//        String key = patientKeys.get(i);
//        updateChart(patients.get(key), oldPatients.get(key));
//
//    }
//
//
//    public static void main(String[] args) {
//        //PatientChartDiffer test = new PatientChartDiffer("Title");
//        //test.pack();
//        //RefineryUtilities.centerFrameOnScreen(test);
//        //test.setVisible(true);
//
//    }
//
//    private void updateChart(Patient p1, Patient p2) {
//        TimeSeriesCollection dataset = new TimeSeriesCollection();
//        TimeSeries birthdateSeries = new TimeSeries("Birth Dates");
//
//        List<ItemData> birthdates1 = new ArrayList<ItemData>();
//        birthdates1.addAll(p1.getObservations(Patient.DEMO_BDAY));
//        birthdates1.addAll(p1.getObservations(Patient.SCREEN_BDAY));
//
//        List<ItemData> birthdates2 = new ArrayList<ItemData>();
//        birthdates2.addAll(p2.getObservations(Patient.DEMO_BDAY));
//        birthdates2.addAll(p2.getObservations(Patient.SCREEN_BDAY));
//
//        for (int i = 0; i < birthdates1.size(); i++) {
//            birthdateSeries.addOrUpdate(dateToDayConverter(birthdates1.get(i).getValue()), 1);
//            birthdateSeries.addOrUpdate(dateToDayConverter(birthdates2.get(i).getValue()), 2);
//
//        }
//
//        List<ItemData> onsetDates = new ArrayList<ItemData>();
//        onsetDates.addAll(p1.getObservations(Patient.ONSETDT));
//
//        List<ItemData> onsetDates2 = new ArrayList<ItemData>();
//        onsetDates2.addAll(p2.getObservations(Patient.ONSETDT));
//
//        TimeSeries onsetSeries = new TimeSeries("Onset dates");
//        for (int i = 0; i < onsetDates.size(); i++) {
//            onsetSeries.addOrUpdate(dateToDayConverter(onsetDates.get(i).getValue()), 1);
//            onsetSeries.addOrUpdate(dateToDayConverter(onsetDates2.get(i).getValue()), 2);
//        }
//
//
//        List<ItemData> diagnosisDates = new ArrayList<ItemData>();
//        diagnosisDates.addAll(p1.getObservations(Patient.VISDT));
//
//        List<ItemData> diagnosisDates2 = new ArrayList<ItemData>();
//        diagnosisDates2.addAll(p2.getObservations(Patient.VISDT));
//
//        TimeSeries diagnosisSeries = new TimeSeries("Diagnosis Dates");
//        for (int i = 0; i < diagnosisDates.size(); i++) {
//            diagnosisSeries.addOrUpdate(dateToDayConverter(diagnosisDates.get(i).getValue()), 1);
//            diagnosisSeries.addOrUpdate(dateToDayConverter(diagnosisDates2.get(i).getValue()), 2);
//
//        }
//
//        List<ItemData> rheumDTDates = new ArrayList<ItemData>();
//        rheumDTDates.addAll(p1.getObservations(Patient.RHEUMDT));
//
//        List<ItemData> rheumDTDates2 = new ArrayList<ItemData>();
//        rheumDTDates2.addAll(p2.getObservations(Patient.RHEUMDT));
//        TimeSeries rheumDTSeries = new TimeSeries("Rheum DT");
//        for (int i = 0; i < rheumDTDates.size(); i++) {
//            rheumDTSeries.addOrUpdate(dateToDayConverter(rheumDTDates.get(i).getValue()), 1);
//            rheumDTSeries.addOrUpdate(dateToDayConverter(rheumDTDates2.get(i).getValue()), 2);
//
//
//        }
//
//        List<ItemData> biopsyDates = new ArrayList<ItemData>();
//        List<ItemData> biopsyDates2 = new ArrayList<ItemData>();
//
//
//        biopsyDates.addAll(p1.getObservations(Patient.BIODT));
//        biopsyDates2.addAll(p2.getObservations(Patient.BIODT));
//        TimeSeries biopsySeries = new TimeSeries("BiopsyDates");
//        for (int i = 0; i < biopsyDates.size(); i++) {
//            biopsySeries.addOrUpdate(dateToDayConverter(biopsyDates.get(i).getValue()), 1);
//            biopsySeries.addOrUpdate(dateToDayConverter(biopsyDates2.get(i).getValue()), 2);
//        }
//
//
//        dataset.addSeries(birthdateSeries);
//        dataset.addSeries(onsetSeries);
//        dataset.addSeries(diagnosisSeries);
//        dataset.addSeries(rheumDTSeries);
//        dataset.addSeries(biopsySeries);
//
//
//        chart1 = ChartFactory.createScatterPlot(
//                "Data for subject " + p1.getSubjectKey() + "(" + p2.getSubjectKey() + ")", // title
//                "X", "Y", // axis labels
//                dataset, // dataset
//                PlotOrientation.VERTICAL,
//                true, // legend? yes
//                false, // tooltips? yes
//                false // URLs? no
//        );
//
//
//        XYPlot plot = chart1.getXYPlot();
//        plot.setDomainAxis(new DateAxis());
//
//        if (chartPanel1 == null) {
//            chartPanel1 = new ChartPanel(chart1);
//        } else {
//            chartPanel1.setChart(chart1);
//        }
//
//
//        chartPanel1.setPreferredSize(new Dimension(500, 200));
//        panel.add(chartPanel1, BorderLayout.NORTH);
//
//
//    }
//
//    private Day dateToDayConverter(String d) {
//        DateTime dateTime = new DateTime(d.replace("-:-:-", "00:00:00").replace("--", "-1"));
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(dateTime.toDate());
//
//        int day = c1.get(Calendar.DAY_OF_MONTH);
//        int month = c1.get(Calendar.MONTH) + 1;
//        int year = c1.get(Calendar.YEAR);
//
//        return new Day(day, month, year);
//    }
//
//
//}
