package stealthness.com;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import stealthness.com.interpreter.Interpreter;

public class MathsAppInterpreterActivity {

	private static int decimalPlaces = 8;
	private static String global = "a";
	private static String error = "error";
	static String pre_ = "->";
	

	public static String getResult(String expression, Activity act,
			Handler handle, Interpreter ml) {
		
		try {
			ml.globals.setVariable(global, null);
			ml.executeExpression(expression, act, handle);

			// Big Decimal use to round to 8 sig. fig. to remove small errors
			Double result = ml.getScalarValueRe(global);

			// this part rounds up the result to the given decimal places
			if (!Double.isNaN(result) && !Double.isInfinite(result)) {
				BigDecimal bd = new BigDecimal(result);
				bd = bd.setScale(getDecimalPlaces(), BigDecimal.ROUND_HALF_UP);
				result = bd.doubleValue();
			}
			return pre_ + Double.toString(result);

		} catch (Error e) {
			return error;
		} catch (Exception e) {
			e.printStackTrace();
			return error;
		}
	}

	public static int getDecimalPlaces() {
		return decimalPlaces;
	}

	public static void setDecimalPlaces(int decimalPlaces) {
		Log.v("stealthness.com", "set dp to " + Integer.toString(decimalPlaces));
		MathsAppInterpreterActivity.decimalPlaces = decimalPlaces;
		Log.v("stealthness.com",
				"set dp "
						+ Integer
								.toString(MathsAppInterpreterActivity.decimalPlaces));
	}
}