package com.example.cats;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	
	ImageButton loginButton;
	Button xbutton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);
        
        loginButton = (ImageButton) this.findViewById(R.id.loginButton1);
        loginButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent i = new Intent();
        		i.putExtra("userId", getUserId());
        		i.putExtra("pwd", getPWD());
        		
        		setResult(100,i);
        		finish();
        	}
        });
        
        xbutton = (Button) this.findViewById(R.id.xbutton);
        xbutton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
    }
    
    public String getUserId()
    {
    	EditText useridField = (EditText) this.findViewById(R.id.username);
    	String userid = useridField.getText().toString();
    	return userid;
    }
    
    public String getPWD()
    {
    	EditText pwdField = (EditText) this.findViewById(R.id.password);
    	String pwdid = pwdField.getText().toString();
    	return pwdid;
    }
}