package stealthness.com.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import stealthness.com.R;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MathsAppGesturesInputActivity extends AbstractMathsInputActivity implements OnGesturePerformedListener {
 

	private GestureLibrary mLibrary;
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
		//Log.v("stealthness.com", "GesturesInput onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_gestures);

        // load stored gestures
        mLibrary = GestureLibraries.fromRawResource(this, R.raw.symbols);
        if (!mLibrary.load()) {
        	finish();
        }

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
    }
	
	/**
	 * This method called when a gesture has been completed
	 * modified form source http://developer.android.com/resources/articles/gestures.html
	 */
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		//Log.v("stealthness.com", "onGesturePerformed");
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

		// We want at least one prediction
		if (predictions.size() > 0) {
			Prediction prediction = predictions.get(0);
			// We want at least some confidence in the result
			if (prediction.score > 1.0) {
				// Show the spell
				addSymbol(prediction.name);
			}
		}
	}
	
	private void addSymbol(String symbol) {
		//Log.v("stealthness.com", "addSymbol");
		TextView tv = (TextView)this.findViewById(R.id.textViewExpression);
		tv.append(symbol);
		
	}
	
    public void onKeyPress(View v){
    	int btn = v.getId();
    	TextView tv = (TextView)this.findViewById(R.id.textViewExpression);
    	switch (btn){
    	case R.id.buttonGestureDelete:
    		//Log.v("stealthness.com", "buttonGestureDelete");
    		int length = tv.getText().length();
    		if(length>0){
    			tv.setText(tv.getText().subSequence(0, length-1));
    		}
    		break;
    	case R.id.buttonGestureEnter:
    		//Log.v("stealthness.com", "buttonGestureEnter");
            Intent result = new Intent();
            // Package the result we want into a bundle to send back
            Bundle b = new Bundle();
            b.putString("expression",tv.getText().toString() );
            result.putExtras(b);
            setResult(Activity.RESULT_OK, result);
            finish();
            break;
    	case R.id.buttonGestureCancel:
    		//Log.v("stealthness.com", "buttonGestureCancel");
    		setResult(Activity.RESULT_CANCELED,this.getIntent());
    		break;
    	case R.id.buttonGestureClear:
    		//Log.v("stealthness.com", "buttonGestureClear");
    		tv.setText("");
    		break;
    	default:
    		setResult(Activity.RESULT_CANCELED, this.getIntent());
    		tv.setText("");
    	}
    }
    
    
	public boolean onCreateOptionsMenu(Menu menu){
		//Log.v("stealthness.com", "onCreateOptionsMenu");
		return 	onCreateOptionsMenu(menu,R.menu.input_menu);
    }
	public boolean onOptionsItemSelected(MenuItem item){
		//Log.v("stealthness.com", "onOptionsItemSelected");		
		return onOptionsItemSelected(item,InputType.GESTURES);
	}

}