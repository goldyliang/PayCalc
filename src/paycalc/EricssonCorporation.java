package paycalc;

/**
 * A simple class for the service of calculating per-week payment, according to the default rules
 * dereived from base class.
 * 
 * See base class {@link GeneralPayCalc} for definition of the default rules.
 *    
 * @author goldyliang@gmail.com
 *
 */
public class EricssonCorporation extends GeneralPayCalc {

	private EricssonCorporation() { }
	
	// The class itself is simple, so lazy-initialization is not required
	private static final EricssonCorporation _instance = new EricssonCorporation();
	
	/**
	 * Use this method to get the singleton instance for payment calculation.
	 * @return
	 */
	public static EricssonCorporation getInstance() {
		return _instance;
	}

}
