package demo;

import paycalc.GeneralPayCalc;
import paycalc.EricssonCorporation;

// This is a simple demo of the function.

public class PayCalcDemo {
	
	public static void main (String[] args) {
		System.out.println("here");
		
		GeneralPayCalc calc = EricssonCorporation.getInstance();
		
		calc.printHeader();
		
		calc.printPayCalc (1, 7.5f, 35);
		calc.printPayCalc (2, 8.2f, 47);
		calc.printPayCalc (3, 10f, 73);

	}
}
