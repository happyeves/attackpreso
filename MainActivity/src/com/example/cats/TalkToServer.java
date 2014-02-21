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

import android.os.AsyncTask;

public class TalkToServer extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... arg0) {
		postData();
		return null;
	}
	
	public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://evarobert.com/script.php");

        System.out.println("Attempting to print");
        try {
            // Add your data
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            System.out.println(response.toString());
            
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        	System.out.println("UGH EXCEPTION");
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	System.out.println("UGH EXCEPTION");
        }
    } 
}
