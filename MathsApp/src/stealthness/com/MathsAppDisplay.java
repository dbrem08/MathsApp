package stealthness.com;

import java.util.ArrayList;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This is for creating and storing the the display of the MathsApp
 * 
 * @author Stealthness.com
 */
public class MathsAppDisplay {
	// the text of display
	StringBuffer displayText;
	TextView tv;
	EditText ed;
	// list of the previous expressions
	ArrayList<CharSequence> outputs;
	// index position of the list of the previous expressions
	int outputIndex;
	// sets test mode (true if in test mode)
	boolean isTestMode = false;

	/**
	 * Creates the Display, with int resource references to displayResults,
	 * editExpression
	 * 
	 * @param tv
	 *            TextView with the displayResults resource
	 * @param ed
	 *            EdiTView with the editExpression resource
	 * @param outputs
	 *            sets it to an ArrayList or if null, creates a new empty
	 *            ArryList.
	 * @param text
	 *            sets the display text to int resource string (from a saved
	 *            state)
	 */
	public MathsAppDisplay(TextView tv, EditText ed,
			ArrayList<CharSequence> outputs, String text) {
		super();
		outputIndex = 0;
		this.tv = tv;
		this.ed = ed;

		// sets the display text
		displayText = (text == null) ? new StringBuffer() : new StringBuffer(text);

		// set output which is a list of the outputs
		this.outputs = (outputs == null) ? new ArrayList<CharSequence>()
				: outputs;
		outputIndex = this.outputs.size();

		// moves cursor to the end of the display
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	/**
	 * This updates the the display
	 * 
	 * @param charSequance
	 *            is the text that to be added to the display
	 * @param flag
	 *            indicates the type of editing that is required
	 */
	public void updateEditDisplay(CharSequence charSequence, EditFlag flag) {
		CharSequence text;
		int pos = ed.getSelectionStart();
		switch (flag) {
		// cycles back up the list of previous expression
		case UP:
			if (outputs != null && 0 < outputIndex) {
				text = outputs.get(outputIndex - 1);
				ed.setText(text);
				outputIndex--;
				// move cursor to the end
				moveCursor(text.length());
			}
			break;
		// cycles forward down the list of previous expression
		case DOWN:
			if (outputs != null && outputs.size() > outputIndex + 1) {
				text = outputs.get(outputIndex + 1);
				ed.setText(text);
				outputIndex++;
				// move cursor to the end
				moveCursor(text.length());
			}
			break;
		case ERROR:
			updateDisplay("error", null);
			break;
		// Cursors updates
		// moves cursor to the left
		case LEFT:
			moveCursor(pos - 1);
			break;
		// moves the cursor to the right
		case RIGHT:
			moveCursor(pos + 1);
			break;
		// deletes the char to the left of the cursor, unless at beginning then
		// deletes to right
		case DELETE:
			deleteCursor(pos);
			break;
		// deletes all the the text in editExpression
		case DELETE_ALL:
		case CLEAR:
			ed.setText("");
			break;
		// take the text and inserts text into the cursor position
		default:
			if (charSequence != null) {
				if (ed.length() == 0 && charSequence.length() > 0) {
					ed.setText(charSequence);
					ed.setSelection(charSequence.length());
				} else if (charSequence.length() > 0) {
					text = ed.getText();
					ed.setText(text.subSequence(0, pos));
					ed.append(charSequence);
					ed.append(text.subSequence(pos, text.length()));
					ed.setSelection(pos + charSequence.length());
				}
			}

		}

	}

	/**
	 * adds an expression to the list of previous expressions
	 * 
	 * @param expression
	 */
	public void addExpression(String expression) {
		outputIndex++;
		outputs.add(expression);
	}

	/**
	 * this deletes the char to the left of the cursor , unless at beginning
	 * then deletes to right
	 * 
	 * @param pos
	 *            the cursor position in editExpression
	 */
	private void deleteCursor(int pos) {
		CharSequence text = ed.getText();
		int length = text.length();
		if (length < 2) {
			ed.setText("");
		} else if (pos == length) {
			ed.setText(text.subSequence(0, pos - 1));
			// moves the curse to the end of the line
			ed.setSelection(pos - 1);
		} else if (pos < 2) {
			ed.setText(text.subSequence(1, length));
		} else if (length > 2 && pos < length) {
			ed.setText(text.subSequence(0, pos - 1));
			ed.append(text.subSequence(pos, length));
			ed.setSelection(pos - 1);
		}
	}

	/**
	 * moves the cursor position to the the value of int pos
	 * 
	 * @param pos
	 *            the position that the cursor is to be at
	 */
	private void moveCursor(int pos) {
		if (pos >= 0 && pos <= ed.getText().length()) {
			ed.setSelection(pos);
		}
	}

	/**
	 * get the field ed
	 */
	public EditText getEd() {
		return ed;
	}

	/**
	 * set the field ed
	 * 
	 * @return
	 */
	public String getEdText() {
		return ed.getText().toString();
	}

	public void setEd(EditText ed) {
		this.ed = ed;
	}

	/**
	 * updates the display using the parameters
	 * 
	 * @param text
	 * @param flag
	 */
	public void updateDisplay(String text, EditFlag flag) {

		tv.append("\n\n" + text);
		// after we add the next line we scroll to the bottom
		scrollToEnd(tv);

	}

	/**
	 * scrolls top to the end of the display text
	 * 
	 * @param tv
	 */
	private void scrollToEnd(TextView tv) {
		tv.scrollTo(0,
				tv.getLayout().getLineTop(tv.getLineCount()) - tv.getHeight());
	}

	public TextView getTv() {
		return tv;
	}

	public void setTv(TextView tv) {
		this.tv = tv;
	}

	public ArrayList<CharSequence> getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList<CharSequence> outputs) {
		this.outputs = outputs;
	}

	public String getDisplayText() {
		return tv.getText().toString();
	}

	/**
	 * Sets the Display into test mode
	 */
	public void setTestMode(boolean b) {
		isTestMode = b;
		if (b) {
			displayText = new StringBuffer();
			outputs = new ArrayList<CharSequence>();
		}
	}

}
