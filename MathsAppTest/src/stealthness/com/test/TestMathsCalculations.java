package stealthness.com.test;

import android.test.ActivityInstrumentationTestCase2;
import stealthness.com.*;
import stealthness.com.interpreter.Interpreter;

public class TestMathsCalculations extends ActivityInstrumentationTestCase2<MathsAppActivity> {
	
	static final String pre_= "->";
	MathsAppActivity mAddiMathsActivity;
	Interpreter ml;	
	
	public TestMathsCalculations() {		  
			super("stealthness.com.interpreter.AddimathsActivity", MathsAppActivity.class);
	} 
	
	@Override
	protected void setUp() throws Exception {
		  	super.setUp();
	
		  	setActivityInitialTouchMode(false);

		  	mAddiMathsActivity = getActivity();
		  	ml = new Interpreter(true);	  		
		
	
	} // end of setUp() method definition 
	
	private String getResult(String expression){
		  	return MathsAppInterpreterActivity.getResult("a="+expression,mAddiMathsActivity,null,ml);
	} 
	/**
	 * very simple test of addition with integers
	 */
	public void test_a1_Simple(){
		
			assertEquals(pre_+"2.0", getResult("1+1"));
		  	assertEquals(pre_+"1.0", getResult("1+0"));
		  	assertEquals(pre_+"0.0", getResult("1+-1"));
		  	assertEquals(pre_+"-1.0", getResult("0+-1"));
		  	assertEquals(pre_+"-2.0", getResult("-1+-1"));
		  	
			assertEquals(pre_+"1000.0", getResult("1+999"));
		  	assertEquals(pre_+"1000.0", getResult("1+999+0"));
		  	assertEquals(pre_+"1000.0", getResult("999+2-1"));
		  	assertEquals(pre_+"-1000.0", getResult("-999-1"));
		  	assertEquals(pre_+"-2000.0", getResult("-1999+-1"));
	}
	public void test_a2_Simple(){
		  	assertEquals(pre_+"2.5", getResult("1.5+1.0"));
		  	assertEquals(pre_+"1.5", getResult("1.0+0.5"));
		  	assertEquals(pre_+"0.0", getResult("1.1+-1.1"));
		  	assertEquals(pre_+"-1.5", getResult("-0.5+-1"));
		  	assertEquals(pre_+"-2.0", getResult("-0.8+-1.2"));
	}
	public void test_b1_Simple(){
	  	assertEquals(pre_+"1.0", getResult("1*1"));
	  	assertEquals(pre_+"0.0", getResult("1*0"));
	  	assertEquals(pre_+"-1.0", getResult("1*-1"));
	  	assertEquals(pre_+"0.0", getResult("0*1"));
	  	assertEquals(pre_+"1.0", getResult("-1*-1"));
	}
	public void test_b2_Simple(){
	  	assertEquals(pre_+"1.5", getResult("1.5*1.0"));
	  	assertEquals(pre_+"0.0", getResult("1.0*0"));
	  	assertEquals(pre_+"-1.8", getResult("1.5*-1.2"));
	  	assertEquals(pre_+"-0.5", getResult("0.5*-1"));
	  	assertEquals(pre_+"0.6", getResult("-0.5*-1.2"));
	}
	public void test_c1_Simple(){
	  	assertEquals(pre_+"1.0", getResult("1/1"));
	  	assertEquals(pre_+"Infinity", getResult("1/0"));
	  	assertEquals(pre_+"3.0", getResult("6/2"));
	  	assertEquals(pre_+"-7.0", getResult("21/-3"));
	  	assertEquals(pre_+"4.0", getResult("-8/-2"));
	}
	public void test_c2_Simple(){
	  	assertEquals(pre_+"0.5", getResult("3/6"));
	  	assertEquals(pre_+"0.25", getResult("7/28"));
	  	assertEquals(pre_+"2.2", getResult("22/10"));
	  	assertEquals(pre_+"-0.125", getResult("1/-8"));
	  	assertEquals(pre_+"1.0", getResult("-1/-1"));
	}
	public void test_d1_Simple(){
	  	assertEquals(pre_+"1.0", getResult("1^0"));
	  	assertEquals(pre_+"1.0", getResult("1.5^0"));
	  	assertEquals(pre_+"4.0", getResult("4^1"));
	  	assertEquals(pre_+"4.5", getResult("4.5^1"));
	  	assertEquals(pre_+"4.0", getResult("2^2"));
	}	
	public void test_d2_Simple(){
	  	assertEquals(pre_+"4.0", getResult("-2^2"));
	  	assertEquals(pre_+"2.0", getResult("4^0.5"));
	  	assertEquals(pre_+"8.0", getResult("2^3"));
	  	assertEquals(pre_+"-8.0", getResult("-2^3"));
	}
	
	public void test_e1_Special(){
		assertEquals(pre_+"0.0",getResult("sin(0)"));
		assertEquals(pre_+"1.0",getResult("cos(0)"));
	}
	
	public void test_f1_Funtions(){
		assertEquals(pre_+"3.14",getResult("pi"));
	}
	  
	public void test_g1_max_input(){
		String ex= "1000";
		double sum = 1000.0;
		for (int i= 0; i<108;i++){
			ex=ex+"+1000";
			sum=sum+1000;
		}
		assertEquals(pre_+Double.toString(sum),getResult(ex));
	}	
}
