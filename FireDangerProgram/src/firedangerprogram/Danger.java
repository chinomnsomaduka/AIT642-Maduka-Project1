package firedangerprogram;

import java.io.*;
import java.util.*;

// Main Program
public class Danger{
	
public static void main (String [] args) throws IOException{
	System.out.println( "Welcome to the Fire Danger Program calculator.\n");
	File inFile = new File ("data.txt");
	Scanner x = new Scanner (inFile);
	
	File outFile = new File ("dataoutput.txt");
	FileWriter fWriter = new FileWriter(outFile);
	PrintWriter pWriter = new PrintWriter(fWriter);
	
	double finefuelmoisture0 = 99;
	//double adjustedfuelmoisture0 = 99;
	//double dryingfactor0 = 0;
	//double fireloadrating0 = 0;
	double grass = 0;
	double timber = 0;
	
	System.out.println(getYorN("Did it snow?"));
	if (true)
		{
		String a = x.next();
		String b = x.next();
		String c = x.next();
		String d = x.next();
		//String e = x.next();
		//String f = x.next();
		
		float precip = (Double.valueOf(a)).floatValue();
		float wind   = (Double.valueOf(b)).floatValue();
		float buo	 = (Double.valueOf(c)).floatValue();
		float iherb  = (Double.valueOf(d)).floatValue();
		//float index  = (Double.valueOf(e)).floatValue();
		
		double finefuelmoisture = FFMCalc(precip,buo);
		double adjustedfuelmoisture = ADFMCalc(precip, finefuelmoisture0, iherb);
		double dryingfactor = DFCalc(iherb, finefuelmoisture);
		double iSnow = iSnowCalc( grass, timber, precip, buo, wind);
		
		double fireloadrating = fload(buo, wind, precip);
		double buildupindex = buoCalc(precip);
		
		finefuelmoisture0 = finefuelmoisture;
		//adjustedfuelmoisture0 = adjustedfuelmoisture;
		//dryingfactor0 = dryingfactor;
		//fireloadrating0 = fireloadrating;
	}
	pWriter.close();
	x.close();
	return;
}

@SuppressWarnings("null")
private static boolean getYorN(String prompt) {
	while(true)
	{
		String answer;
		System.out.print("\n" + prompt + " (Y or N) ");
		Scanner sc = null;
		answer = sc.nextLine();
		if (answer.equalsIgnoreCase("Y"))
			return true;
		else if (answer.equalsIgnoreCase("N"))
			return false;
	}

}

private static double ADFMCalc(double precip, double finefuelmoisture0, double iherb) {
	
	finefuelmoisture0 = finefuelmoisture0 - 1;
	finefuelmoisture0 = finefuelmoisture0 + (iherb -1) * 5;
	precip = precip - .1;
	
	return 0;
}


private static double iSnowCalc(double grass, double timber, double precip,
		double buo, double wind) {
	
	boolean snow = true;
	
	if (snow){
		precip = precip - 0.1;
		buo = -50 * Math.log(1 - (1 - Math.exp(buo/50))* Math.exp(-1.175* (precip -1)));
	}
		else {
			fload(buo, wind, precip);
		}
	return buo;
}

private static double fload(double buo, double wind, double precip) {
	double diff, dry = 1, wet = 3, fireload, ffm;
	diff = dry - wet;
	fireload = diff - buo;
	ffm = wind * Math.exp(precip * diff);
	
	return fireload;
}

private static double DFCalc(double iherb, double finefuelmoisture) {
	double dryfact;
	dryfact = finefuelmoisture - iherb;
	return dryfact;
}

private static double buoCalc(double precip) {
	double buo = 0;
	buo = -50 * Math.log(1 - (1 - Math.exp(buo/50))* Math.exp(-1.175* (precip -1)));
		
	return buo;
}

private static double FFMCalc(double precip, double buo) {
	double diff, dry = 1, wet = 6, ffm;
	diff = dry - wet;
	ffm = precip * Math.exp(buo* diff);
	return ffm;
}
}

