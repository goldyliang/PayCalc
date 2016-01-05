package paycalc;

import paycalc.GeneralPayCalc.CalcStatus;

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
public abstract class GeneralPayCalc {
	
	protected float MIN_PAY_PER_HOUR;
	protected int HOURS_THREASHOLD_EXTRA_PAY;
	protected float TIMES_EXTRA_PAY;
	protected int MAX_HOURS_PER_WEEK;
	
	/**
	 * Status codes for the payment calculation, indicating valid result or error.
	 */
	public enum CalcStatus {
		/**
		 * Accepted input and valid result returned
		 */
		ACCEPTED, 
		
		/**
		 * Input with working hours exceeding limit. Payment calculation returned anyway.
		 */
		TOO_MANY_HOURS,
		
		/**
		 * Input with base salary under minimum. Payment calculation returned anyway.
		 */
		TOO_LOW_BASE_SALARY,
		
		/**
		 * Invalid input. No payment calculation returned.
		 */
		INVALID_INPUT};
	
	/**
	 * Class to wrap the calculation status and result
	 */
	public static class CalcResult {
		private CalcStatus status;
		private float totalPay;
		
		// This class is only for returning the result
		// So public constructor disallowed.
		private CalcResult (CalcStatus status, float total) {
			this.status = status;
			this.totalPay = total;
		}
		
		/** 
		 * Get status of calculation
		 * @return Status code. See {@link CalcStatus} for detail definition
		 */
		public CalcStatus getStatus () {
			return status;
		}
		
		/**
		 * Get calculated total payment (in dollars)
		 * @return Total payment for the week
		 */
		public float getTotalPay () {
			return totalPay;
		}
	}
	
	public CalcStatus ruleCheck (float basePay, int hours) {
		CalcStatus status;
		
		if (basePay <= 0 || hours <= 0)
			return CalcStatus.INVALID_INPUT;
		
		if (basePay < MIN_PAY_PER_HOUR)
			status = CalcStatus.TOO_LOW_BASE_SALARY;
		else if (hours > MAX_HOURS_PER_WEEK)
			status = CalcStatus.TOO_MANY_HOURS;
		else
			status = CalcStatus.ACCEPTED;
		
		return status;
	}
	
	public float calcWeeklyPay (float basePay, int hours) {
		float pay = basePay * hours;
		
		if (hours > HOURS_THREASHOLD_EXTRA_PAY)
			pay += (hours - HOURS_THREASHOLD_EXTRA_PAY) * basePay * (TIMES_EXTRA_PAY - 1.0f);
		
		return pay;
	}
	
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
	public CalcResult checkAndCalc (float basePay, int hours) {
		CalcStatus status = ruleCheck (basePay, hours);
		
		float total;
		
		if (status != CalcStatus.INVALID_INPUT)
			total = calcWeeklyPay (basePay, hours);
		else
			total = 0;
		
		return new CalcResult (status, total);
	}
		
	
	// Helper method printing the header for calculation result (testing purpose only)
	// Ideally this can be put in a separate package
	public void printHeader() {
		System.out.println(String.format("%1$10s %2$15s %3$15s %4$15s %5$25s",
				"Employee", "Base Pay", "Hours Worked" ,"Total To Pay", "Error Info"));
	}
	
	// Helper method printing one record of calculation (testing purpose only)
	// Ideally this can be put in a separate package
	public void printPayCalc (int order, float basePay, int hours) {
		
		CalcResult res = checkAndCalc (basePay, hours);

		System.out.println(
				String.format("%1$10d %2$15.2f %3$15d %4$15.2f %5$25s",
				order, basePay, hours, res.totalPay, res.status));
	}
	
}
