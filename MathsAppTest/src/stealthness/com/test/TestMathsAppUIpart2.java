package stealthness.com.test;

import stealthness.com.MathsAppActivity;
import stealthness.com.MathsAppDisplay;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class  TestMathsAppUIpart2 extends ActivityInstrumentationTestCase2<MathsAppActivity> {

	static final String pre_= "->";
	MathsAppActivity act;
	private EditText ed;
	private TextView result;
	String display;
	

	
	public TestMathsAppUIpart2() {
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
		  	
		  	display = "";
	} // end of setUp() method definition 
	
	
	
	public void testPreConditions(){
		assertTrue(ed.getText()!=null);
		assertEquals(ed.getText().toString(),"");			
	}
	
	/**
	 * Test simple key inputs with any calculation
	 * @throws Throwable
	 */
	public void testKeyInput() throws Throwable{
		
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	
				final Button  key1= (Button)act.findViewById(stealthness.com.R.id.buttonKey1);
				final Button  key2= (Button)act.findViewById(stealthness.com.R.id.buttonKey2);
				final Button  key3= (Button)act.findViewById(stealthness.com.R.id.buttonKey3);
				final Button  key4= (Button)act.findViewById(stealthness.com.R.id.buttonKey4);
				final Button  key5= (Button)act.findViewById(stealthness.com.R.id.buttonKey5);
				final Button  key6= (Button)act.findViewById(stealthness.com.R.id.buttonKey6);
				final Button  key7= (Button)act.findViewById(stealthness.com.R.id.buttonKey7);
				final Button  key8= (Button)act.findViewById(stealthness.com.R.id.buttonKey8);
				final Button  key9= (Button)act.findViewById(stealthness.com.R.id.buttonKey9);
				final Button  key0= (Button)act.findViewById(stealthness.com.R.id.buttonKey0);
				final Button  keyDel= (Button)act.findViewById(stealthness.com.R.id.buttonDelete);
				final Button  keyClear= (Button)act.findViewById(stealthness.com.R.id.buttonClear);
				final Button  keyMultiple= (Button)act.findViewById(stealthness.com.R.id.buttonMultiple);
				final Button  keyPlus= (Button)act.findViewById(stealthness.com.R.id.buttonPlus);
				final Button  keyDivide= (Button)act.findViewById(stealthness.com.R.id.buttonDivide);
				final Button  keyMinus= (Button)act.findViewById(stealthness.com.R.id.buttonMinus);
				final Button  keyEnter= (Button)act.findViewById(stealthness.com.R.id.buttonEnter);
				final Button  keyDot= (Button)act.findViewById(stealthness.com.R.id.buttonDot);
				final Button  keyChangeKeyboard= (Button)act.findViewById(stealthness.com.R.id.buttonChangeKeyboard);   	
    	
				
				
				//keyChangeKeyboard.performClick();
		    	
		    	
				keyClear.performClick();
		    	key1.performClick();
		    	assertEquals("1",ed.getText().toString());
		    	keyDot.performClick();
		    	assertEquals("1.",ed.getText().toString());
		    	key5.performClick();
		    	
		    	keyPlus.performClick();
		    	key4.performClick();
		    	keyMultiple.performClick();;
		    	key5.performClick();
		    	assertTrue(ed.getText()!=null);
		    	assertEquals(ed.getText().toString(),"1.5+4*5");
		    	keyEnter.performClick();
				assertTrue(ed.getText()!=null);
				display = "\n\n->21.5";
				assertEquals(result.getText().toString(),display);

				keyClear.performClick();
		    	key6.performClick();
		    	assertEquals("6",ed.getText().toString());
		    	keyDot.performClick();
		    	assertEquals("6.",ed.getText().toString());
		    	key6.performClick();
		    	keyDivide.performClick();
		    	key3.performClick();
		    	keyPlus.performClick();;
		    	key2.performClick();
		    	assertTrue(ed.getText()!=null);
		    	assertEquals(ed.getText().toString(),"6.6/3+2");
		    	keyEnter.performClick();
				assertTrue(ed.getText()!=null);
				display +="\n\n->4.2";
				assertEquals(result.getText().toString(),display);	
		    	

				keyChangeKeyboard.performClick();
				
				
				final Button  keyT= (Button)act.findViewById(stealthness.com.R.id.buttonKeyT);
				final Button  keyE= (Button)act.findViewById(stealthness.com.R.id.buttonKeyE);
				final Button  keyS= (Button)act.findViewById(stealthness.com.R.id.buttonKeyS);
				final Button  keySpace= (Button)act.findViewById(stealthness.com.R.id.buttonSpace);		
				
				keyClear.performClick();
		    	keyT.performClick();
		    	keyE.performClick();
		    	keyS.performClick();
		    	keyT.performClick();
		    	assertEquals(ed.getText().toString(),"test");
		    	keySpace.performClick();
		    	keyT.performClick();
		    	keyE.performClick();
		    	keyS.performClick();
		    	keyT.performClick();
		    	assertTrue(ed.getText()!=null);
		    	assertEquals(ed.getText().toString(),"test test");
		    	keyDel.performClick();
		    	assertEquals(ed.getText().toString(),"test tes");
		    	keyDel.performClick();
		    	keyDel.performClick();
		    	keyDel.performClick();
		    	assertEquals(ed.getText().toString(),"test ");
		    	keyDel.performClick();
		    	assertEquals(ed.getText().toString(),"test");
		    	keyClear.performClick();
		    	assertEquals(ed.getText().toString(),"");
		    	key1.performClick();
		    	key2.performClick();
		    	key3.performClick();
		    	key4.performClick();
		    	key5.performClick();
		    	assertEquals(ed.getText().toString(),"12345");
		    	key6.performClick();
		    	key7.performClick();
		    	key8.performClick();
		    	key9.performClick();
		    	key0.performClick();
		    	assertTrue(ed.getText()!=null);
		    	assertEquals(ed.getText().toString(),"1234567890");
		    	keyDel.performClick();
		    	assertEquals(ed.getText().toString(),"123456789");
		    	keyDel.performClick();
		    	keyDel.performClick();
		    	keyDel.performClick();
		    	assertEquals(ed.getText().toString(),"123456");
		    	keyClear.performClick();
			    assertEquals(ed.getText().toString(),"");
			   
		    }
		  });
		
	}					

}

