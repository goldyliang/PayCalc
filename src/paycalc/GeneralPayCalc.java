package paycalc;

/**
 * A general abstract class for the service of calculating per-week payment, 
 * according to the following (default) rules:
 * 
 *For hours within {@value #constHoursThresholdExtraPay}, pay by base hourly salary. 
 *For hours over {@value #constHoursThresholdExtraPay}, pay by {@value #constTimesExtraPay} 
 *times of base hourly salary
 * 
 * Following conditions are considered error, and error code is to be returned.
 *    > Hourly base salary less than minimum which is {@value #constMinPayPerHour}.
 *    > Total working hour exceeding maximum which is {@value #constMaxHoursPerWeek}.
 *    
 * Limitation: if both error conditions are met, only the error code for the prior error 
 * is returned.
 *    
 * Sub-classes can use default rules, or assign customized parameters to the protected 
 * variables, or even override public methods to provide different rules and calculation.
 * 
 * @author goldyliang@gmail.com
 *
 */
public abstract class GeneralPayCalc {
	
	/**
	 * Parameter for the rule of minimal payment per hour.
	 * Sub-class can assign different value as needed.
	 */
	protected float constMinPayPerHour = 8.0f;
	
	/**
	 * Parameter for the rule of extra-paid hours.
	 * Sub-class can assign different value as needed.
	 */
	protected int constHoursThresholdExtraPay = 40;
	
	/**
	 * Parameter for the rule of payment for extra-paid hours.
	 * Sub-class can assign different value as needed.
	 */
	protected float constTimesExtraPay = 1.5f;
	
	/**
	 * Parameter for the rule of maximum allowed working hours.
	 * Sub-class can assign different value as needed.
	 */
	protected int constMaxHoursPerWeek = 60;
	
	
	/**
	 * Status codes for the payment calculation, indicating valid result or error.
	 */
	public enum CalcStatus {
		/**
		 * Accepted input and valid result returned
		 */
		ACCEPTED, 
		
		/**
		 * Input with working hours exceeding limit. 
		 * (Payment calculation can be returned anyway)
		 */
		TOO_MANY_HOURS,
		
		/**
		 * Input with base salary under minimum
		 * (Payment calculation can be returned anyway)
		 */
		TOO_LOW_BASE_SALARY,
		
		/**
		 * Invalid input. 
		 * (Payment calculation will always return zero)
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
	
	/**
	 * Check whether the inputs are valid according to the defined rule.
	 * See {@link GeneralPayCalc} for the detail rules.
	 * 
	 * @param basePay Base hourly salary (in dollars)
	 * @param hours Hours of working in the week
	 * @return  A status code whether the input is valid.
	 */
	public CalcStatus ruleCheck (float basePay, int hours) {
		CalcStatus status;
		
		if (basePay <= 0 || hours <= 0)
			return CalcStatus.INVALID_INPUT;
		
		if (basePay < constMinPayPerHour)
			status = CalcStatus.TOO_LOW_BASE_SALARY;
		else if (hours > constMaxHoursPerWeek)
			status = CalcStatus.TOO_MANY_HOURS;
		else
			status = CalcStatus.ACCEPTED;
		
		return status;
	}
	
	/**
	 * Calculate weekly payment according to the defined rule.
	 * See {@link GeneralPayCalc} for the detail rules.
	 * 
	 * @param basePay Base hourly salary (in dollars)
	 * @param hours Hours of working in the week
	 * @return The calculated weekly payment
	 */
	public float calcWeeklyPay (float basePay, int hours) {
		float pay = basePay * hours;
		
		if (hours > constHoursThresholdExtraPay)
			pay += (hours - constHoursThresholdExtraPay) * basePay * (constTimesExtraPay - 1.0f);
		
		return pay;
	}
	
	/**
	 * Calculate and return the total payment for the week.
	 * 
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
		
	/**
	 * Helper method printing the header for calculation result.
	 * Use along with {@link #printPayCalc(int, float, int) printPayCalc}
	 * to format output.
	 */
	public void printHeader() {
		System.out.println(String.format("%1$10s %2$15s %3$15s %4$15s %5$25s",
				"Employee", "Base Pay", "Hours Worked" ,"Total To Pay", "Error Info"));
	}
	
	/**
	 * Helper method printing one record of input and calculation result.
	 * 
	 * Use along with {@link #printHeader() printHeader}
	 * to format output.
	 * 
	 * @param order  The order number to be displayed in the first column
	 * @param basePay Base hourly salary (in dollars)
	 * @param hours Hours of working in the week
	 */
	public void printPayCalc (int order, float basePay, int hours) {
		
		CalcResult res = checkAndCalc (basePay, hours);

		System.out.println(
				String.format("%1$10d %2$15.2f %3$15d %4$15.2f %5$25s",
				order, basePay, hours, res.totalPay, res.status));
	}
	
}
