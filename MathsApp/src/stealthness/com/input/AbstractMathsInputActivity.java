package stealthness.com.input;

import stealthness.com.MathsAppPrefActivity;
import stealthness.com.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public abstract class AbstractMathsInputActivity extends Activity{
	
	public static AbstractMathsInputActivity createMathsInputActivity(InputType type){
		switch (type){
		case VOICE:
			return new MathsAppVoiceInputActivity();
		case GESTURES:
			return new MathsAppGesturesInputActivity();		
		}		
		return null;	
	}
	
	
    public boolean onCreateOptionsMenu(Menu item,int inputMenu){
    	
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(inputMenu, item);
		return true;  	
    }
    
	public boolean onOptionsItemSelected(MenuItem item, InputType type){
		
		int help=0;
		int layout=R.layout.dialog_help;
		switch( type){
		case VOICE:
			help = R.string.menu_voice_help;
			break;
		case GESTURES:
			help = R.string.menu_gestures_help;
			break;
		default:
			help = R.string.menu_no_help;
		}
    	switch(item.getItemId()){		
    		case R.id.menu_about: 
    			doDialog(R.string.app_name,R.string.menu_about_text,layout);
    			return true;
    		case R.id.menu_help:
    			doDialog(R.string.app_name,help,layout);
    			return true;
			default:			
    	}	
    	// for the rest -- system options
		return super.onOptionsItemSelected(item);	
    }
    
/*    protected void doDialog(MenuItem item, int menuHelpText){
		new AlertDialog.Builder(this)
	    .setTitle(item.getTitle())
	    .setMessage(menuHelpText)
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	     .show();
    }*/
    
    private void doDialog(int title,int menuText, int layout){
    	final Dialog dialog = new Dialog(this);
    	
    	dialog.setContentView(layout);
    	dialog.setTitle(title);
    	
    	TextView tv = (TextView)dialog.findViewById(R.id.textDialog);
    	tv.setText(menuText);
    	Button btn = (Button)dialog.findViewById(R.id.buttonOK);
    	btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});

    	dialog.show();
    }
    
    
}
