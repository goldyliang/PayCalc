package paycalc;

public class EricssonCorporation {
	
	private static final float MIN_PAY_PER_HOUR = 8.0f;
	private static final int HOURS_THREASHOLD_EXTRA_PAY = 40;
	private static final float TIMES_EXTRA_PAY = 1.5f;
	private static final int MAX_HOURS_PER_WEEK = 60;
	
	public enum CalcStatus {
		ACCEPTED, 
		TOO_MANY_HOURS, 
		TOO_LOW_BASE_SALARY,
		INVALID_INPUT};
	
	public static class CalcResult {
		private CalcStatus status;
		private float totalPay;
		
		private CalcResult (CalcStatus status, float total) {
			this.status = status;
			this.totalPay = total;
		}
				
		public CalcStatus getStatus () {return status;}
		public float getTotalPay () {return totalPay;}
	}
	
	public static CalcResult calcWeeklyPay (float basePay, int hours) {
		
		if (basePay <= 0 || hours <= 0)
			return new CalcResult (CalcStatus.INVALID_INPUT, 0);
		
		float pay = basePay * hours;
		
		if (hours > HOURS_THREASHOLD_EXTRA_PAY)
			pay += (hours - HOURS_THREASHOLD_EXTRA_PAY) * basePay * (TIMES_EXTRA_PAY - 1.0f);
		
		CalcStatus status;
		
		if (basePay < MIN_PAY_PER_HOUR)
			status = CalcStatus.TOO_LOW_BASE_SALARY;
		else if (hours > MAX_HOURS_PER_WEEK)
			status = CalcStatus.TOO_MANY_HOURS;
		else
			status = CalcStatus.ACCEPTED;
		
		return new CalcResult (status, pay);
	}
	
	static void printHeader() {
		System.out.println(String.format("%1$10s %2$15s %3$15s %4$15s %5$25s",
				"Employee", "Base Pay", "Hours Worked" ,"Total To Pay", "Error Info"));
	}
	
	static void printPayCalc (int order, float basePay, int hours) {
		
		CalcResult res = calcWeeklyPay (basePay, hours);

		System.out.println(
				String.format("%1$10d %2$15.2f %3$15d %4$15.2f %5$25s",
				order, basePay, hours, res.totalPay, res.status));
	}
	
	public static void main (String[] args) {
		
		printHeader();
		
		printPayCalc (1, 7.5f, 35);
		printPayCalc (2, 8.2f, 47);
		printPayCalc (3, 10f, 73);

	}
}
