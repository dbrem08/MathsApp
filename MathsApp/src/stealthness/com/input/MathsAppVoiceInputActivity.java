package stealthness.com.input;


/* 
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.List;

import stealthness.com.MathsAppActivity;
import stealthness.com.MathsAppPrefActivity;
import stealthness.com.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Sample code that invokes the speech recognition intent API.
 */
public class MathsAppVoiceInputActivity extends AbstractMathsInputActivity implements OnClickListener {
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;   
    private ListView mList;
    

    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myLocalIntent = getIntent();
        
        // Inflate our UI from its XML layout description.
        setContentView(R.layout.input_voice);

        // Get display items for later interaction
        Button speakButton = (Button) findViewById(R.id.btn_speak);
        //Button cancelButton = (Button) findViewById(R.id.btn_cancel);
        
        mList = (ListView) findViewById(R.id.list);

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            startVoiceRecognitionActivity();
            setResult(Activity.RESULT_OK, myLocalIntent);         
        } 
        
        /**
         * attach the setOnItemClickListener ti mList
         */
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                onListItemClick(v,pos,id);
            }
            
	        protected void onListItemClick(View v, int pos, long id) {
	            
	            Intent result = new Intent();
	            Bundle b = new Bundle();
	            b.putString("expression",mList.getItemAtPosition((int) id).toString() );

	            result.putExtras(b);
	            setResult(Activity.RESULT_OK, result);
	            finish();
	        }                    
        });
    }

    /**
     * Handle the click on the start recognition button.
     */
    public void onClick(View v) {
    	switch(v.getId()){
    	case R.id.btn_cancel:
    		setResult(Activity.RESULT_CANCELED,this.getIntent());
    		finish();
    		break;
    	case R.id.btn_speak:
    		startVoiceRecognitionActivity();
    		break;
    	}
    }
    


    

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Expression");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            TextView tv = (TextView)findViewById(R.id.textVoiceSelect);
            tv.setVisibility(0);
            mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,matches));
        } 
        super.onActivityResult(requestCode, resultCode, data);
    }
    
	public boolean onCreateOptionsMenu(Menu menu){		
		return 	onCreateOptionsMenu(menu,R.menu.input_menu);
    }
	public boolean onOptionsItemSelected(MenuItem item){		
		return onOptionsItemSelected(item,InputType.VOICE);
	}
    

}
