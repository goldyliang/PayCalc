package paycalc;

/**
 * A simple class for the service of calculating per-week payment, according to the following rules:
 * 
 *     For hours within {@value #HOURS_THREASHOLD_EXTRA_PAY}, pay by base hourly salary. 
 *     For hours over {@value #HOURS_THREASHOLD_EXTRA_PAY}, pay by {@value #TIMES_EXTRA_PAY} times of base hourly salary
 * 
 * Following conditions are considered error, and error code is to be returned.
 *    > Hourly base salary less than minimum which is {@value #MIN_PAY_PER_HOUR}.
 *    > Total working hour exceeding maximum which is {@value #MAX_HOURS_PER_WEEK}.
 *    
 * Limitation: if both error conditions are met, only the error code for the prior error is returned.
 *    
 * @author goldyliang@gmail.com
 *
 */
public class EricssonCorporation extends GeneralPayCalc {
	
	/**
	 * Calculate and return the total payment for the week.
	 * The calculation status is returned along with the calculated total payment
	 * through the wrapper class {@link CalcResult}
	 * 
	 * See {@link GeneralPayCalc} for detail rules of calculation.
	 * 
	 * @param basePay Base hourly salary (in dollars)
	 * @param hours Hours of working in the week
	 * @return Calculation result for the weekly payment, wrapped with a status code
	 */

	private EricssonCorporation() {
		MIN_PAY_PER_HOUR = 8.0f;
		HOURS_THREASHOLD_EXTRA_PAY = 40;
		TIMES_EXTRA_PAY = 1.5f;
		MAX_HOURS_PER_WEEK = 60;	
	}
	
	private static class InstanceHolder {
		static EricssonCorporation singletonInstance = new EricssonCorporation();
	}
	
	public static EricssonCorporation getInstance() {
		return InstanceHolder.singletonInstance;
	}

}
