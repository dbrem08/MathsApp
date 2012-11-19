package stealthness.com;

import stealthness.com.input.AbstractMathsInputActivity;
import stealthness.com.input.InputType;
import stealthness.com.interpreter.Interpreter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MathsAppActivity extends Activity {

	// store a instance of Addi Intepreter
	private Interpreter interpreter;
	// stores an int referece for keyboard to be diplayed
	private int mKeyboardId;
	// stores an instance of display class
	public MathsAppDisplay disply;
	// use to reference stored shared preference
	private SharedPreferences mPrefs;
	// use to record if first time run for application
	boolean isFirstTime;
	// used to store number of decimal places
	private int decimalPlaces = 5;
	// store dummy code
	static final int GET_CODE = 0;

	/**
	 * onCreate is called when the application is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// intialise shared prefernce
		mPrefs = getSharedPreferences(null, 0);

		// set test mode
		boolean isTestMode = false;

		// Initialise decimal place for the application to use for the
		// Interpreter, take default value of decimalPlaces
		MathsAppInterpreterActivity.setDecimalPlaces(mPrefs.getInt(
				"decimal_plces_pref", decimalPlaces));
		// creates an instance of Interpreter
		interpreter = new Interpreter(true);

		// Sets the Keyboard (if in no shared prefs or Testmode set to numbers)
		mKeyboardId = (isTestMode) ? R.layout.keys_numbers : mPrefs.getInt(
				"keyboard", R.layout.keys_numbers);
		changeKeyboard(mKeyboardId);

		if (savedInstanceState != null) {
			// if a saved state exist load expression and display
			setmKeyboardId(savedInstanceState.getInt("KEYBOARD"));
			isFirstTime = false;
			// Initialise the disply with savedInstanceState
			disply = new MathsAppDisplay(
					(TextView) this.findViewById(R.id.textResult),
					(EditText) this.findViewById(R.id.editExpression),
					savedInstanceState.getCharSequenceArrayList("EXPRESSIONS"),
					savedInstanceState.getString("DISPLAY"));

			this.disply.updateEditDisplay(mPrefs.getString("Expresssion", ""),
					EditFlag.APPEND);
		} else {
			// set new empty display and edit expression
			isFirstTime = true;
			// intialize the disply
			disply = new MathsAppDisplay(
					(TextView) this.findViewById(R.id.textResult),
					(EditText) this.findViewById(R.id.editExpression), null,
					null);
		}

		disply.updateEditDisplay(mPrefs.getString("Expresssion", ""),
				EditFlag.APPEND);
		// test mode is use to test screen splash
		disply.setTestMode(isTestMode);

		// show screen splash help at the start unless not first time or in test
		// mode
		if (isFirstTime && !isTestMode) {
			if (mPrefs.getBoolean("showScreenSplash", true)) {
				// launch a dialog that provide a screen splash of information
				// on start up
				doDialog(R.string.app_name, R.string.welcome_text,
						R.layout.dialog_help);
			}
		}
	}

	/**
	 * saves state using shared preferences
	 */
	public void onPause() {
		super.onPause();
		SharedPreferences.Editor ed = mPrefs.edit();
		ed.putString("Expresssion", this.disply.getEdText());
		ed.putInt("keyboard", mKeyboardId);
		ed.commit();
	}

	/**
	 * onResume will restore previous state
	 * 
	 * @param savedInstanceState
	 */
	public void onResume(Bundle savedInstanceState) {

		setContentView(R.layout.main);
		disply = new MathsAppDisplay(
				(TextView) this.findViewById(R.id.textResult),
				(EditText) this.findViewById(R.id.editExpression),
				savedInstanceState.getCharSequenceArrayList("EXPRESSIONS"),
				savedInstanceState.getString("DISPLAY"));

		changeKeyboard(savedInstanceState.getInt("KEYBOARD"));
	}

	/**
	 * Store the state when the activity is paused
	 */
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("DISPLAY", this.disply.getDisplayText());
		outState.putInt("KEYBOARD", getmKeyboardId());
		outState.putCharSequenceArrayList("EXPRESSIONS",
				this.disply.getOutputs());
	}

	/**
	 * calculate is used to send an expression and get the the result from
	 * MathsInterpreter
	 */
	public void calculate() {
		// get our expression from display
		// Initialise or empty result
		String expression = this.disply.getEdText();
		String result = "error";

		// check that expression valid
		if (!expression.isEmpty()) {

			// remove equals if on it
			if (expression.charAt(expression.length() - 1) == '=') {
				expression = expression.substring(0, expression.length() - 1);
			}
			// get the result
			result = MathsAppInterpreterActivity.getResult("a=" + expression,
					this, new Handler(), interpreter);
		}

		// if the result
		if (result == "error") {
			result = result + " " + expression;
		} else {
			// add expression to expression list and clear edit expression
			this.disply.addExpression(expression);
			this.disply.updateEditDisplay("", EditFlag.CLEAR);
			this.disply.updateDisplay(result, null);
		}

	}

	/**
	 * This method deal with button click events
	 * 
	 * @param v
	 */
	public void onKeyClick(View v) {

		int btn = v.getId();
		Button button = (Button) this.findViewById(btn);
		switch (btn) {

		// INPUTS
		case R.id.buttonVoice:
			getExpressionInput(InputType.VOICE);
			break;
		case R.id.buttonGesture:
			getExpressionInput(InputType.GESTURES);
			break;

		// CURSOR KEYS
		case R.id.buttonCursorUp:
			this.disply.updateEditDisplay(null, EditFlag.UP);
			Log.v("stealthness.com", "up");
			break;
		case R.id.buttonCursorDown:
			this.disply.updateEditDisplay(null, EditFlag.DOWN);
			Log.v("stealthness.com", "down");
			break;
		case R.id.buttonCursorLeft:
			this.disply.updateEditDisplay(null, EditFlag.LEFT);
			Log.v("stealthness.com", "left");
			break;
		case R.id.buttonCursorRight:
			this.disply.updateEditDisplay(null, EditFlag.RIGHT);
			Log.v("stealthness.com", "right");
			break;

		// FUNCTION KEYS
		case R.id.buttonEnter:
			Log.v("stealthness.com", "Enter");
			calculate();
			break;
		case R.id.buttonClear:
			this.disply.updateEditDisplay(button.getText(), EditFlag.CLEAR);
			Log.v("stealthness.com", "Clear");
			break;
		case R.id.buttonDelete:
			this.disply.updateEditDisplay(button.getText(), EditFlag.DELETE);
			Log.v("stealthness.com", "Delete");
			break;

		// INPUT KEYS
		case R.id.buttonSpace:
			this.disply.updateEditDisplay(" ", EditFlag.APPEND);
			break;
		default:
			this.disply.updateEditDisplay(button.getText(), EditFlag.APPEND);
		}
	}

	/**
	 * this Method changes the keyboard to the param
	 * 
	 * @param v
	 */
	public void changeKeyboard(View v) {
		if (getmKeyboardId() == R.layout.keys_letters) {
			changeKeyboard(R.layout.keys_numbers);
		} else {
			changeKeyboard(R.layout.keys_letters);
		}
	}

	/**
	 * This sets the the keyboard layout by a changing to the resources id
	 * 
	 * @param layout
	 *            a resource ID to a XML layout
	 */
	private void changeKeyboard(int layout) {
		Log.v("stealthness.com", "changeKeyboard");
		LinearLayout keyboard = (LinearLayout) findViewById(R.id.include_keyboard);
		LayoutInflater inflater = getLayoutInflater();
		if (keyboard != null) {
			keyboard.removeAllViews();
			keyboard.addView(inflater.inflate(layout, null));
		}
		setmKeyboardId(layout);
	}

	/**
	 * Gets the the integer resource ID for the MathsApp keyboard
	 * 
	 * @return mKeyboard the current integer resource ID for a keyboard XML
	 *         layout
	 */
	public int getmKeyboardId() {
		return mKeyboardId;
	}

	/**
	 * Sets the the integer resource ID for the MathsApp keyboard
	 * 
	 * @param mKeyboardId
	 *            integer resource ID for a keyboard XML layout
	 */
	public void setmKeyboardId(int mKeyboardId) {
		this.mKeyboardId = mKeyboardId;
		// changes the buttonChangeKeyboardsetstext to appropriate message.
		Button btn = (Button) findViewById(R.id.buttonChangeKeyboard);
		if (btn != null) {
			if (mKeyboardId == R.layout.keys_numbers) {
				btn.setText(R.string.buttonKeyboardLetters);
			} else {
				btn.setText(R.string.buttonKeyboardNumbers);
			}
		}
	}

	// input //

	/**
	 * this will start an activity and will then update the display with the
	 * result of the activity
	 * 
	 * @param act
	 *            is an activity that will be a subclass of
	 *            AbstructMathsInputActivity
	 */
	public void doInput(AbstractMathsInputActivity act) {
		Log.v("stealthness.com", "doInput");
		// get the contact
		Context context = getApplicationContext();
		try {
			// start out input intent
			Intent intent = new Intent(context, act.getClass());
			startActivityForResult(intent, GET_CODE);
		} catch (Exception e) {
			Resources res = getResources();
			this.disply.updateDisplay(res.getString(R.string.errorMessage),
					null);
		}
	}

	/**
	 * This gets an expression by calling a subclass of
	 * AbstractMathsInputActivity based on type
	 * 
	 * @param input
	 *            the input type VOICE or GESTURE
	 */
	public void getExpressionInput(InputType input) {
		doInput(AbstractMathsInputActivity.createMathsInputActivity(input));
	}

	/**
	 * This responds to the result of either Gesture or Voice Activity
	 * 
	 * @param requestCode
	 *            an int that identifies the activities
	 * @param resultCode
	 *            an int that identifies the returning activity result
	 * @param data
	 *            associated with the returning activity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Log.v("stealthness.com", "onActivityResult resultCode = " +resultCode);
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GET_CODE) {
			if (resultCode == RESULT_OK) {
				this.disply.updateEditDisplay(
						data.getStringExtra("expression"), EditFlag.APPEND);
			} else {
				this.disply.updateEditDisplay("", EditFlag.ERROR);
			}
		}
	}

	// / options menu //

	/**
	 * This responds to an item being selected from the optionMenu
	 * 
	 * @param item
	 *            the Resource ID of the selected item in Option Menu
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		//Log.v("stealthness.com", "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.menu_prefs:
			// will start the preference Activity
			Intent intent = new Intent().setClass(getBaseContext(),
					MathsAppPrefActivity.class);
			this.startActivityForResult(intent, 0);
			return true;
		case R.id.menu_about:
			doDialog(R.string.menu_help_title, R.string.menu_about_text,
					R.layout.dialog_help);
			//Log.v("stealthness.com", "R.id.menu_about");
			return true;
		case R.id.menu_help:
			doDialog(R.string.menu_help_title, R.string.menu_help_text,
					R.layout.dialog_help);
			//Log.v("stealthness.com", "R.id.menu_help");
			return true;
		default:
		}
		// for the rest -- system options
		return super.onOptionsItemSelected(item);
	}

	/**
	 * This will inflate a OptionMenu
	 * 
	 * @param menu
	 *            Resource ID for an XML menu resource
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		//Log.v("stealthness.com", "onCreateOptionsMenu");
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.mainmenu, menu);
		return true;
	}

	/**
	 * This create a Dialog
	 * 
	 * @param title
	 *            the Resource ID for a string for the Dialog title
	 * @param menuText
	 *            the Resource ID for a string for the Dialog text
	 * @param layout
	 *            the Resource ID for a layout resource
	 */
	private void doDialog(int title, int menuText, int layout) {
		//Log.v("stealthness.com", "doDialog");
		final Dialog dialog = new Dialog(this);
		// sets the title content
		dialog.setContentView(layout);
		dialog.setTitle(title);
		TextView tv = (TextView) dialog.findViewById(R.id.textDialog);
		tv.setText(menuText);

		// creates an OK button and adds a listener to it
		Button btn = (Button) dialog.findViewById(R.id.buttonOK);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// remove the dialog
				dialog.dismiss();
			}
		});
		// shows the the dialog
		dialog.show();
	}

}