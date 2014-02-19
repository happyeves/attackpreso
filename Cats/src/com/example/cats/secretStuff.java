package com.example.cats;

import android.content.Context;
import android.location.*;
import android.os.Bundle;

public class secretStuff {
	
	protected void spyLocation(Context context) {
		
		//Get a reference to system location manager
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		//Make a listener that looks for location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				//Do something with this location
			}
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			public void onProviderEnabled(String provider) {}
			public void onProviderDisabled(String provider) {}
		};
		
		//Register the listener with the location manager
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, locationListener);
	}
	
	
}