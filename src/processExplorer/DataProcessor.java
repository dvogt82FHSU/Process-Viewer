/**
 * This module processes the dataset according to the parameters within DataInfo.
 * This module has constructor DataProcessor(DataInfo, ArrayList<Double>).
 * Included is class Sample, a superclass that consists of ArrayList for moving around data easier 
 */

package processExplorer;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.lang.Math;

import processExplorer.DataInfo;

public class DataProcessor {
	
	// Dataset for processing
	ArrayList<Double> rawData;
	
	// Sample Sets
	double pSampleSet [];
	double rSampleSet[];
	double xSampleSet[];
	double cSampleSet[];
	
	// Parameters for p-chart and c-chart
	DataInfo dataInfo = new DataInfo();
	
	// Controls for R-bar Chart
	double rUCL;
	double rLCL;
	double rBar;
	
	// Controls for X-bar Chart
	double xUCL;
	double xLCL;
	
	// Controls for p-Chart
	double pUCL;
	double pLCL;
	double pBar;
	
	// Data for c Chart
	int c;
	
	// Controls to c-Chart
	double cUCL;
	double cLCL;
	
	//Integers for processing sample sets
	int interval;
	int offset;
	
	// Process Info
	int sigma;
	double pci;
	double pcr;		
	double mean;
	
	// Constants for calculations as Arrays for reference 
	final double[] d3 = { 0, 0, 0, 0, 0, 0.076, 0.136, 0.184, 0.223, 0.256, 0.283, 0.307, 0.328, 0.347, 0.363, 0.378, 0.391, 0.403, 0.415, 0.425, 0.434, 0.443, 0.451, 0.459 };
	final double[] d4 = { 3.267, 2.574,	2.282, 2.114, 2.004, 1.924, 1.864, 1.816, 1.777, 1.744, 1.717, 1.693, 1.672, 1.653, 1.637, 1.622, 1.608, 1.597, 1.585, 1.575, 1.566, 1.557, 1.548, 1.541};
	final double[] a2 = { 1.88,	1.023, 0.729, 0.577, 0.483,	0.419, 0.373, 0.337, 0.308, 0.285, 0.266, 0.249, 0.235, 0.223, 0.212, 0.203, 0.194,	0.187, 0.18, 0.173, 0.167, 0.162, 0.157, 0.153};
	
	// Z-values for service level calculations
	final double [] z = new double[]{	0.5, 	0.504, 	0.508, 	0.512, 	0.516, 	0.5199, 0.5239, 0.5279, 0.5319, 0.5359,
										0.5398, 0.5438, 0.5478, 0.5517, 0.5557, 0.5596, 0.5636, 0.5675, 0.5714, 0.5753,
										0.5793, 0.5832, 0.5871,	0.591,	0.5948,	0.5987,	0.6026,	0.6064,	0.6103,	0.6141,
										0.6179, 0.6217,	0.6255,	0.6293,	0.6331,	0.6368,	0.6406,	0.6443,	0.648,	0.6517,
										0.6554,	0.6591, 0.6628,	0.6664,	0.67,	0.6736,	0.6772,	0.6808,	0.6844,	0.6879,
										0.6915, 0.695,	0.6985,	0.7019,	0.7054,	0.7088,	0.7123,	0.7157,	0.719,	0.7224,
										0.7257,	0.7291,	0.7324,	0.7357,	0.7389,	0.7422,	0.7454, 0.7486,	0.7517,	0.7549,
										0.758,	0.7611,	0.7642,	0.7673,	0.7704,	0.7734,	0.7764,	0.7794,	0.7823,	0.7852,
										0.7881,	0.791,	0.7939,	0.7967,	0.7995,	0.8023,	0.8051,	0.8078,	0.8106,	0.8133,
										0.8159,	0.8186,	0.8212,	0.8238,	0.8264,	0.8289,	0.8315,	0.834,	0.8365,	0.8389,
										0.8413,	0.8438,	0.8461,	0.8485,	0.8508, 0.8531,	0.8554,	0.8577,	0.8599,	0.8621,
										0.8643,	0.8665,	0.8686,	0.8708,	0.8729,	0.8749,	0.877,	0.879,	0.881,	0.883,
										0.8849,	0.8869,	0.8888,	0.8907,	0.8925,	0.8944,	0.8962,	0.898,	0.8997,	0.9015,
										0.9032,	0.9049,	0.9066,	0.9082,	0.9099,	0.9115,	0.9131,	0.9147,	0.9162,	0.9177,
										0.9192,	0.9207,	0.9222,	0.9236,	0.9251,	0.9265,	0.9279,	0.9292,	0.9306,	0.9319,
										0.9332,	0.9345,	0.9357,	0.937,	0.9382,	0.9394,	0.9406,	0.9418,	0.9429,	0.9441,
										0.9452,	0.9463,	0.9474,	0.9484,	0.9495,	0.9505,	0.9515, 0.9525,	0.9535,	0.9545,
										0.9554,	0.9564,	0.9573,	0.9582,	0.9591,	0.9599,	0.9608,	0.9616,	0.9625,	0.9633,
										0.9641,	0.9649,	0.9656,	0.9664,	0.9671,	0.9678,	0.9686,	0.9693,	0.9699,	0.9706,
										0.9713,	0.9719,	0.9726,	0.9732,	0.9738,	0.9744,	0.975,	0.9756,	0.9761,	0.9767,
										0.9772,	0.9778,	0.9783,	0.9788,	0.9793,	0.9798,	0.9803,	0.9808,	0.9812,	0.9817,
										0.9821,	0.9826,	0.983,	0.9834,	0.9838,	0.9842,	0.9846,	0.985,	0.9854,	0.9857,
										0.9861,	0.9864,	0.9868,	0.9871,	0.9875,	0.9878,	0.9881,	0.9884,	0.9887,	0.989,
										0.9893,	0.9896,	0.9898,	0.9901,	0.9904,	0.9906,	0.9909,	0.9911,	0.9913,	0.9916,
										0.9918,	0.992,	0.9922,	0.9925,	0.9927,	0.9929, 0.9931,	0.9932,	0.9934,	0.9936,
										0.9938,	0.994,	0.9941,	0.9943,	0.9945,	0.9946,	0.9948,	0.9949,	0.9951,	0.9952,
										0.9953,	0.9955,	0.9956,	0.9957,	0.9959,	0.996,	0.9961,	0.9962,	0.9963,	0.9964,
										0.9965,	0.9966,	0.9967,	0.9968,	0.9969,	0.997,	0.9971,	0.9972,	0.9973,	0.9974,
										0.9974,	0.9975,	0.9976,	0.9977,	0.9977,	0.9978,	0.9979,	0.9979,	0.998,	0.9981,
										0.9981,	0.9982,	0.9982,	0.9983,	0.9984,	0.9984,	0.9985,	0.9985,	0.9986,	0.9986,
										0.9987,	0.9987,	0.9987,	0.9988,	0.9988,	0.9989,	0.9989,	0.9989,	0.999,	0.999,
										0.999,	0.9991,	0.9991,	0.9991,	0.9992,	0.9992,	0.9992,	0.9992,	0.9993,	0.9993,
										0.9993,	0.9993,	0.9994,	0.9994,	0.9994,	0.9994,	0.9994,	0.9995,	0.9995,	0.9995,
										0.9995,	0.9995,	0.9995,	0.9996,	0.9996,	0.9996,	0.9996,	0.9996,	0.9996,	0.9997,
										0.9997,	0.9997,	0.9997,	0.9997,	0.9997,	0.9997,	0.9997,	0.9997,	0.9997,	0.9998};		
	
	DataProcessor(ArrayList<Double> rawData, DataInfo dataInfo) {
		this.rawData = rawData;
		this.dataInfo.target = dataInfo.target;
		this.dataInfo.samples = dataInfo.samples;
		this.dataInfo.sampleSize = dataInfo.sampleSize;	
		this.dataInfo.service = dataInfo.service;
	}
	
	// Method to build a sample set of samples
	// Builds set and averages for display.
	// Other builds are similar in design and function
	void buildXSamples() {
		xSampleSet = new double[dataInfo.samples];
		double subSet[] = new double[dataInfo.sampleSize];
		int interval = rawData.size() / dataInfo.samples;
		int offset = interval / dataInfo.sampleSize;
		int index = 0;
		for(int x = 0; x < dataInfo.samples; x++){
			index = x * interval;
			for(int y = 0; y < dataInfo.sampleSize; y++) {
				int position = index + (y * offset);
				subSet[y] = rawData.get(position);
			}
			DescriptiveStatistics average = new DescriptiveStatistics(subSet);
			xSampleSet[x] = average.getMean();
		}
	}

	void buildRangeSamples(){
		rSampleSet = new double[dataInfo.samples];
		double subSet[] = new double[dataInfo.sampleSize];
		int interval = rawData.size() / dataInfo.samples;
		int offset = interval / dataInfo.sampleSize;
		int index = 0;
		for(int x = 0; x < dataInfo.samples; x++){
			index = x * interval;
			for(int y = 0; y < dataInfo.sampleSize; y++) {
				int position = index + (y * offset);
				subSet[y] = rawData.get(position);
			}
			double smallest = subSet[0];
			double largest = subSet[0];
			for (int i = 1; i < dataInfo.sampleSize; i++){
				if(subSet[i] < smallest) {
					smallest = subSet[i];
				} else if (subSet[i] > largest) {
					largest = subSet[i];
				}
			}
			rSampleSet[x] = largest - smallest;
		}
		DescriptiveStatistics average = new DescriptiveStatistics(dataInfo.samples);
		rBar = average.getMean();
	}
	
	void buildPSamples() {
		this.pSampleSet = new double[dataInfo.samples];
		double subSet[] = new double[dataInfo.sampleSize];
		int interval = rawData.size() / dataInfo.samples;
		int offset = interval / dataInfo.sampleSize;
		int index = 0;
		for(int x = 0; x < dataInfo.samples; x++){
			index = x * interval;
			for(int y = 0; y < dataInfo.sampleSize; y++) {
				int position = index + (y * offset);
				subSet[y] = rawData.get(position);
			}
			double sum = 0;
			for (int i = 0; i < dataInfo.sampleSize; i++){
				if(subSet[i] < (dataInfo.target * 0.99)) {
					sum++;
				} else if (subSet[i] > (dataInfo.target * 1.02)) {
					sum++;
				}
			}
			pSampleSet[x] = sum / (1.0d * dataInfo.sampleSize);
		}
		
		DescriptiveStatistics average = new DescriptiveStatistics(pSampleSet);
		this.pBar = average.getMean();
	}	
	
	// Processes data for R-Bar chart
	void rBarChart() {
		buildRangeSamples();
		rUCL = this.rBar * d4[this.dataInfo.samples - 2];
		rLCL = this.rBar * d3[this.dataInfo.samples - 2];
	}
	
	void xBarChart() {
		buildXSamples();
		this.xLCL = this.dataInfo.target - (a2[this.dataInfo.samples - 2] * this.dataInfo.target);
		this.xUCL = this.dataInfo.target + (a2[this.dataInfo.samples - 2] * this.dataInfo.target);	
	}
	
	void pBarChart(){
		buildPSamples();
		double z = getZ(this.dataInfo.service);
		double stdDev = Math.sqrt((this.pBar * (1 - this.pBar)) / this.pSampleSet.length);
		this.pLCL = this.pBar - z * stdDev;
		this.pUCL = this.pBar + z * stdDev;
	}
	
	/**
	 * c Chart is a useful display to monitor defects per unit.
	 * I was unable to find a dataset with adequate data for c,
	 * so it is randomly generated based upon the possibility of 
	 * up to 3 errors per unit.  This demonstrates the math and execution,
	 * only it is on random data and is not directly tied to the
	 * p-chart, as it often is.
	 * 
	 */
	void cChart() {
		Random random = new Random();
		cSampleSet = new double[dataInfo.samples];
		for (int i = 0; i < this.dataInfo.samples; i++) {
			this.cSampleSet[i] = random.nextInt(16) * random.nextDouble();
		}
	
		c = random.nextInt(16);
		
		this.cLCL = c - getZ(this.dataInfo.service) * Math.sqrt(c);
		this.cUCL = c + getZ(this.dataInfo.service) * Math.sqrt(c);
		
		// Adjusts control limits for errors from random generation and actual limits imposed on model
		if (cLCL < 0) cLCL = 0;
	}
	
	// Converts service level to sigma value ( # Sigma = Z * 2 : 6 Sigma ~ 3 Z )
	double getZ(double sla) {
		
		/**
		 *  Comparator for finding index of nearest service level;
		 *  compares the difference between sla and the values in z
		 *  and returns the nearest to the sla.
		 *  
		 *  Defaults to the lowest index if the table provides the 
		 *  same sla value for multiple indexes
		 */
		
		double distance = Math.abs(z[0] - sla);
		int index = 0;
		for(int i = 1; i < z.length; i++){
		    double cdistance = Math.abs(z[i] - sla);
		    if(cdistance < distance){
		        index = i;
		        distance = cdistance;
		    }
		}	
		
		/** 
		 * z[] uses the index to represent the z-value. 
		 * If index is 243 that equal a z-value of 2.43.
		 */
		
		return (double) index/100;
	}

	public void pInfo() {
		xBarChart();
		double[] array =  new double[rawData.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = rawData.get(i);
		}
		DescriptiveStatistics stats = new DescriptiveStatistics(array);
		this.mean = stats.getMean();
		double stdDev = stats.getStandardDeviation();
		double pciU = (this.mean - this.xLCL) / ( 3.0d * stdDev);
		double pciL = (this.xUCL - this.mean) / ( 3.0d * stdDev);
		if (pciU < pciL) {
			this.pci = pciU;
		} else {
			this.pci = pciL;
		}
		
		
		//Mess to calculate sigma value
		if (this.pci >= 2) {
			this.sigma = 6;
 		} else if (this.pci > 1.6) {
			this.sigma = 5;
		} else if(this.pci > 1.33) {
			this.sigma = 4;
		} else if(this.pci > 1) {
			this.sigma = 3;
		} else if(this.pci > 0.66) {
			this.sigma = 2;
		} else if (this.pci > 0.33) {
			this.sigma = 1;
		} else {
			this.sigma = 0;
		}
		
		this.pcr = (this.xUCL - this.xLCL) / (6.0d * stdDev);
	}
}
