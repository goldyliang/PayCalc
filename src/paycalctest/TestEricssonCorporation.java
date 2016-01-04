package paycalctest;

import static org.junit.Assert.*;

import org.junit.Test;

import paycalc.EricssonCorporation;
import paycalc.EricssonCorporation.CalcResult;
import paycalc.EricssonCorporation.CalcStatus;

public class TestEricssonCorporation {

	@Test
	public void testCalcWeeklyPay() {
		
		CalcResult r;
		
		r = EricssonCorporation.calcWeeklyPay(8.2f, 35);
		assertEquals (CalcStatus.ACCEPTED, r.getStatus());
		assertEquals ( 8.2f * 35, r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(8.2f, 40);
		assertEquals (CalcStatus.ACCEPTED, r.getStatus());
		assertEquals ( 8.2f * 40, r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(8.2f, 47);
		assertEquals (CalcStatus.ACCEPTED, r.getStatus());
		assertEquals ( 8.2f * 40 + 8.2f * 1.5 * 7, r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(8f, 47);
		assertEquals (CalcStatus.ACCEPTED, r.getStatus());
		assertEquals ( 8f * 40 + 8f * 1.5 * 7, r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(15f, 60);
		assertEquals (CalcStatus.ACCEPTED, r.getStatus());
		assertEquals ( 15f * 40 + 15f * 1.5 * 20, r.getTotalPay(), 0.005);

	}
	
	@Test
	public void testCalcWeeklyPay_Error() {
		
		CalcResult r;
		
		r = EricssonCorporation.calcWeeklyPay(7.9f, 35);
		assertEquals (CalcStatus.TOO_LOW_BASE_SALARY, r.getStatus());
		assertEquals ( 7.9f * 35, r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(10f, 61);
		assertEquals (CalcStatus.TOO_MANY_HOURS, r.getStatus());
		assertEquals ( 10f * 40 + 10f * 1.5 * (61-40), r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(7.5f, 70);
		assertEquals (CalcStatus.TOO_LOW_BASE_SALARY, r.getStatus());
		assertEquals ( 7.5f * 40 + 7.5f * 1.5 * 30, r.getTotalPay(), 0.005);
		
		r = EricssonCorporation.calcWeeklyPay(0, 15);
		assertEquals (CalcStatus.INVALID_INPUT, r.getStatus());

		r = EricssonCorporation.calcWeeklyPay(-1, 15);
		assertEquals (CalcStatus.INVALID_INPUT, r.getStatus());
		
		r = EricssonCorporation.calcWeeklyPay(8.2f, 0);
		assertEquals (CalcStatus.INVALID_INPUT, r.getStatus());
		
		r = EricssonCorporation.calcWeeklyPay(8.2f, -1);
		assertEquals (CalcStatus.INVALID_INPUT, r.getStatus());
	}

}
