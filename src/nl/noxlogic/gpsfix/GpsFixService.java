package nl.noxlogic.gpsfix;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GpsFixService extends Service {
	private static final String TAG = GpsFixService.class.getSimpleName();
	
	private static final String PushUri = "http://<URL>/setcoord.php?lat=%s&long=%s";
	
	LocationManager locationManager;
	LocationListener gpsLocationListener;
		
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsLocationListener = new GPSLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000L, 100, gpsLocationListener);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(gpsLocationListener);		
	}
	
	
	 private class GPSLocationListener implements LocationListener {
	        @Override
	        public void onLocationChanged(Location location) {
	            Log.d(TAG, String.format(PushUri, location.getLatitude(), location.getLongitude()));
	            
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpParams params = httpclient.getParams();
	            HttpConnectionParams.setConnectionTimeout(params, 30000);
	            HttpConnectionParams.setSoTimeout(params, 15000);
	            
	            HttpGet httpget = new HttpGet(String.format(PushUri, location.getLatitude(), location.getLongitude()));
	            try {
					httpclient.execute (httpget);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }

	        @Override
	        public void onStatusChanged(String provider, int status, Bundle extras) {
	        }

	        @Override
	        public void onProviderEnabled(String provider) { }

	        @Override
	        public void onProviderDisabled(String provider) { }

	    }

}
