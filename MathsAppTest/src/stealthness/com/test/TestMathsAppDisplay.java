package stealthness.com.test;

import stealthness.com.MathsAppActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class  TestMathsAppDisplay extends ActivityInstrumentationTestCase2<MathsAppActivity> {

	static final String pre_= "->";
	MathsAppActivity act;
	private EditText ed;
	private TextView result;
	String disply;
	

	
	public TestMathsAppDisplay() {
		super("stealthness.com.interpreter.AddimathsActivity", MathsAppActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		  	super.setUp();
	
		  	setActivityInitialTouchMode(false);
		  	act = getActivity();
		  	ed =(EditText) act.findViewById(stealthness.com.R.id.editExpression);
		  	result = (TextView)act.findViewById(stealthness.com.R.id.textResult);
		  	disply = "";
	} // end of setUp() method definition 
	
	
	
	/**
	 * Test simple key inputs with any calculation
	 * @throws Throwable
	 */
	public void testKeyInput() throws Throwable{
		
		act.findViewById(stealthness.com.R.id.buttonChangeKeyboard);
		
		final Button  key1= (Button)act.findViewById(stealthness.com.R.id.buttonKey1);
		final Button  key2= (Button)act.findViewById(stealthness.com.R.id.buttonKey2);
		final Button  key3= (Button)act.findViewById(stealthness.com.R.id.buttonKey3);
		final Button  key4= (Button)act.findViewById(stealthness.com.R.id.buttonKey4);
		final Button  key5= (Button)act.findViewById(stealthness.com.R.id.buttonKey5);
//		act.findViewById(stealthness.com.R.id.buttonKey6);
//		act.findViewById(stealthness.com.R.id.buttonKey7);
//		act.findViewById(stealthness.com.R.id.buttonKey8);
//		act.findViewById(stealthness.com.R.id.buttonKey9);
//		act.findViewById(stealthness.com.R.id.buttonKey0);
		final Button  keyDel= (Button)act.findViewById(stealthness.com.R.id.buttonDelete);
		final Button  keyClear= (Button)act.findViewById(stealthness.com.R.id.buttonClear);
		final Button  keyPlus = (Button)act.findViewById(stealthness.com.R.id.buttonPlus);
		final Button  keyEnter= (Button)act.findViewById(stealthness.com.R.id.buttonEnter);
//		act.findViewById(stealthness.com.R.id.buttonMultiple);
//		act.findViewById(stealthness.com.R.id.buttonDivide);
		final Button  keyMinus = (Button) act.findViewById(stealthness.com.R.id.buttonMinus);
//		act.findViewById(stealthness.com.R.id.buttonDot);
		
		final Button  keyUp= (Button)act.findViewById(stealthness.com.R.id.buttonCursorUp);
		final Button  keyDown= (Button)act.findViewById(stealthness.com.R.id.buttonCursorDown);
		final Button  keyLeft= (Button)act.findViewById(stealthness.com.R.id.buttonCursorLeft);
		final Button  keyRight= (Button)act.findViewById(stealthness.com.R.id.buttonCursorRight);
		
			
		runTestOnUiThread(new Runnable() {
			    @Override
			    public void run() {
			    	
			    	keyClear.performClick();
			    	
			    	// Simple Expression 1
			    	key1.performClick();
			    	disply = "\n\n->1.0";
			    	keyEnter.performClick();
			    	assertEquals(7,result.length());	
					assertEquals(result.getText().toString(),disply );	
			    	
					// testing adding to editExpressing and cursor keys
			    	keyClear.performClick();
			    	key1.performClick();			    	
			    	key2.performClick();
			    	key3.performClick();
			    	key4.performClick();
			    	key5.performClick();
			    	assertEquals(ed.getText().toString(),"12345");
			    	keyLeft.performClick();
			    	keyDel.performClick();
			    	assertEquals(ed.getText().toString(),"1235");
			    	keyLeft.performClick();
			    	keyLeft.performClick();
			    	keyLeft.performClick();
			    	keyLeft.performClick();
			    	keyLeft.performClick();
			    	keyLeft.performClick();
			    	keyLeft.performClick();
			    	keyDel.performClick();
			    	assertEquals(ed.getText().toString(),"235");
			    	key3.performClick();
			    	key4.performClick();
			    	assertEquals("34235",ed.getText().toString());
			    	keyRight.performClick();
			    	keyRight.performClick();
			    	keyRight.performClick();
			    	key4.performClick();
			    	assertEquals("342354",ed.getText().toString());
			    				    	
					keyClear.performClick();
					
					// testing expression 15+5
			    	assertEquals("",ed.getText().toString());
			    	key1.performClick();
			    	assertEquals("1",ed.getText().toString());
			    	key5.performClick();
			    	keyPlus.performClick();
			    	key5.performClick();    	
			    	assertEquals("15+5",ed.getText().toString());
			    	keyEnter.performClick();
			    	disply += "\n\n->20.0";
					assertEquals(result.getText().toString(),disply);	
					
					// testing expression 25+5
			    	assertEquals(ed.getText().toString(),"");
			    	key2.performClick();
			    	key5.performClick();
			    	keyPlus.performClick();
			    	key5.performClick();    	
			    	assertEquals(ed.getText().toString(),"25+5");
			    	keyEnter.performClick();
			    	disply += "\n\n->30.0";
					assertEquals(result.getText().toString(),disply);
					
			    	// testing expression 25+5
			    	assertEquals("",ed.getText().toString());
			    	key3.performClick();
			    	key5.performClick();
			    	keyPlus.performClick();
			    	key5.performClick();
			    	assertEquals("35+5",ed.getText().toString());
			    	keyEnter.performClick();
			    	disply += "\n\n->40.0";
			    	assertEquals(result.getText().toString(),disply);

					
					keyUp.performClick();
			    	assertEquals("35+5",ed.getText().toString());
			    	keyUp.performClick();
			    	assertEquals("25+5",ed.getText().toString());
					keyUp.performClick();
			    	assertEquals("15+5",ed.getText().toString());
					keyUp.performClick();
					assertEquals("1",ed.getText().toString());
					keyUp.performClick();
			    	assertEquals("1",ed.getText().toString());
					keyDown.performClick();
			    	assertEquals("15+5",ed.getText().toString());
			    	keyDown.performClick();
			    	assertEquals("25+5",ed.getText().toString());
					keyUp.performClick();
			    	assertEquals("15+5",ed.getText().toString());
					keyDown.performClick();
			    	assertEquals("25+5",ed.getText().toString());
					keyDown.performClick();
			    	assertEquals("35+5",ed.getText().toString());
					keyDown.performClick();
			    	assertEquals("35+5",ed.getText().toString());
			    

					

			    	
			    }
			  });
	}					

}

