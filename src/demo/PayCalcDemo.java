package demo;

import paycalc.GeneralPayCalc;
import paycalc.GeneralPayCalc.CalcStatus;
import paycalc.EricssonCorporation;

/**
 * Demonstration of sub-classing the general payment class and define
 * customized rules.
 * 
 */
class AnotherCompany extends GeneralPayCalc {
	
	private static final int HOURS_THREADSHOLD_2 = 45;
	private static final float TIMES_ABOVE_THREADSHOLD_2 = 3f;

	// Put to private to avoid instantiation.
	private AnotherCompany () {
		// Assign a different threshold parameter
		constMaxHoursPerWeek = 50;
	}
	
	private static final AnotherCompany _instance = new AnotherCompany();
	
	public static AnotherCompany getInstance() {
		return _instance;
	}
	
	/**
	 * Override the payment calculation method to add a second threshold
	 */
	@Override
	public float calcWeeklyPay (float basePay, int hours) {
		
		float pay;
		
		if (hours <= HOURS_THREADSHOLD_2)
			pay = super.calcWeeklyPay(basePay, hours);
		else
			pay = super.calcWeeklyPay(basePay, HOURS_THREADSHOLD_2) + 
			         (hours - HOURS_THREADSHOLD_2) * 
			         basePay * TIMES_ABOVE_THREADSHOLD_2;
		
		return pay;
	}
}

/**
 * This is a simple demo of the payment calculation function.
 *
 */
public class PayCalcDemo {
	
	public static void main (String[] args) {
		
		System.out.println("Ericsson:");
		GeneralPayCalc calc = EricssonCorporation.getInstance();
		
		calc.printHeader();
		
		calc.printPayCalc (1, 7.5f, 35);
		calc.printPayCalc (2, 8.2f, 47);
		calc.printPayCalc (3, 10f, 73);
		
		System.out.println("\n\nAnother company:");

		calc = AnotherCompany.getInstance();
		
		calc.printHeader();
		
		calc.printPayCalc(1, 7.5f, 35);
		calc.printPayCalc(1, 8.2f, 45);
		calc.printPayCalc (2, 8.2f, 47);
		calc.printPayCalc (3, 10f, 55);		

	}
}
