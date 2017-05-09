/**
 * Process Explorer
 * Drew Vogt
 * MIS 301 VC
 * 
 * Final Project
 * 
 * This is the main program for my final.
 * It uses card layout and a button panel to take DB and data details
 * as well as to switch displays to the selected chart.
 * 
 * It utilizes XChart, a lightweight utility for charting
 * 
 * I do not have full error checking implemented and not all exceptions are handled yet
 * 
 */

package processExplorer;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import processExplorer.DBInfo;
import processExplorer.DataInfo;
import processExplorer.DataProcessor;
import processExplorer.DBQuery;

import org.knowm.xchart.*;


public class ProcessExplorer {

	// Objects that are shared throughout 
	static DBInfo dbInfo = new DBInfo();
	static DataInfo dataInfo = new DataInfo();
	static ArrayList<Double> rawData = new ArrayList <Double>();

	// Quick checks to see if the DBInfo and DataInfo are completed
	static boolean dbAccessed = false;
	static boolean dataAccessed = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					mainGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static void mainGUI() {
		JFrame window = new JFrame("Process Explorer");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(700,650); 
		window.setLayout(new BorderLayout());

		final CardLayout cardLayout = new CardLayout(); 
		final JPanel cardPanel = new JPanel(cardLayout);

		// Builds button panel and cards and buttons
		// Cards are not built until used
		JPanel buttonPanel = new JPanel();
		JButton db = new JButton("DB Info");
		JButton data = new JButton("Data Info");
		JButton rBarBtn = new JButton("R-Bar");
		JButton xBarBtn = new JButton("X-Bar");
		JButton pChartBtn = new JButton("P-Chart");
		JButton cChartBtn = new JButton("C-Chart");
		JButton processBtn = new JButton("Process Info");

		buttonPanel.add(db);
		buttonPanel.add(data);
		buttonPanel.add(rBarBtn);
		buttonPanel.add(xBarBtn);
		buttonPanel.add(pChartBtn);
		buttonPanel.add(cChartBtn);
		buttonPanel.add(processBtn);

		
		// calls the DB input dialog
		db.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				accessDB();
			}
		});

		// calls the data input dialog
		data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				accessData();
			}
		});

		// Creates the r-bar chart and displays it.  The other charts work similarly
		rBarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// Check that db and data details were at least attempted to be collected and get them if not
				verify();
				// Feeds data to a new processor for building the chart
				DataProcessor dataProcessor = new DataProcessor(rawData, dataInfo);
				// Builds the chart data
				dataProcessor.rBarChart();
				// Returns the chart to add to the panel and display
				XChartPanel rBar = new XChartPanel(buildChart(dataProcessor.rUCL, dataProcessor.rLCL, dataProcessor.rSampleSet, "R-Bar Chart: Monitor range of output"));
				cardPanel.add(rBar,"R-Bar");
				// Repaint to ensure updated
				rBar.repaint();
				cardLayout.show(cardPanel, "R-Bar");
			}
		});
		
		xBarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				verify();
				DataProcessor dataProcessor = new DataProcessor(rawData, dataInfo);
				dataProcessor.xBarChart();
				XChartPanel xBar = new XChartPanel(buildChart(dataProcessor.xUCL, dataProcessor.xLCL, dataProcessor.xSampleSet, "X-Bar Chart: Monitor process output"));
				cardPanel.add(xBar,"X-Bar");
				xBar.repaint();
				cardLayout.show(cardPanel, "X-Bar");
			}
		});

		pChartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				verify();
				DataProcessor dataProcessor = new DataProcessor(rawData, dataInfo);
				dataProcessor.pBarChart();
				XChartPanel pBar = new XChartPanel(buildChart(dataProcessor.pUCL, dataProcessor.pLCL, dataProcessor.pSampleSet, "P-Bar Chart: Average # of Defects"));
				cardPanel.add(pBar,"P-Bar");
				pBar.repaint();
				cardLayout.show(cardPanel, "P-Bar");
			}
		}); 

		cChartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				verify();
				DataProcessor dataProcessor = new DataProcessor(rawData, dataInfo);
				dataProcessor.cChart();
				XChartPanel c = new XChartPanel(buildChart(dataProcessor.cUCL, dataProcessor.cLCL, dataProcessor.cSampleSet, "C Chart: Defects per Item"));
				cardPanel.add(c,"C");
				c.repaint();
				cardLayout.show(cardPanel, "C");
			}
		});

		processBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				DataProcessor dataProcessor = new DataProcessor(rawData, dataInfo);
				dataProcessor.pInfo();
				
				// Displays a message with the process information 
				JOptionPane.showMessageDialog(window,
					    "PCI: " + dataProcessor.pci + " PCR: " + dataProcessor.pcr + "Sigma: " + dataProcessor.sigma, 
					    "Process Capability", 
					    JOptionPane.PLAIN_MESSAGE);
			}
		});

		// Adds panel and buttons to the main window
		window.add(cardPanel,BorderLayout.CENTER);
		window.add(buttonPanel,BorderLayout.SOUTH);
		window.setVisible(true);
	}

	protected static double[] clYArray(double cl) {
		double [] empty = new double [dataInfo.samples];
		for (int i = 0; i < dataInfo.samples; i++){
			empty[i] = cl;
		}
		return empty;
	}
	
	protected static double[] emptyArray() {
		double [] empty = new double [dataInfo.samples];
		for (int i = 0; i < dataInfo.samples; i++) {
			empty[i] = i+1;
		}
		return empty;
	}
	
	static void accessDB() {
		dbInfo = new DBInfo();
		dbInfo.getDBInfoGUI();
		DBQuery dbQuery = new DBQuery(dbInfo);
		rawData = dbQuery.returnQuery();
		dbAccessed = true;
	}

	static void accessData() {
		dataInfo = new DataInfo();
		dataInfo.getDataInfoGUI();
		dataAccessed = true;
	}

	// Little method to ensure DB is queried and data input prior to graphing the data
	static void verify() {
		if(dbAccessed&&dataAccessed) {
		} else if(dbAccessed) {
			accessData();
		} else if(dataAccessed){
			accessDB();
		} else {
			accessDB();
			accessData();
		}
	}
	
	static XYChart buildChart(double ucl, double lcl, double[] samples, String title){
		XYChart chart = new XYChartBuilder().xAxisTitle("Sample").yAxisTitle("Value").width(650).height(500).build();
		chart.addSeries("UCL", emptyArray(), clYArray(ucl));
		chart.addSeries("LCL", emptyArray(), clYArray(lcl));
		chart.addSeries("Samples", emptyArray(), samples);
		chart.setTitle(title);
		return chart;
	}
}
