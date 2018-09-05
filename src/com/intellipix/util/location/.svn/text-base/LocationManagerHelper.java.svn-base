package com.intellipix.util.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class LocationManagerHelper {
			
	private Context context;
	private LocationManager locationManager;
	private String provider;
	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			Log.v("LocationManagerHelper", "Location Changed [" + location + "]");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider,
				int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	};
	

	public LocationManagerHelper(Context context) {
		this(context, null);
	}
	
	/**
	 * @param provider One of {@link LocationManager#GPS_PROVIDER}, 
	 * 					{@link LocationManager#NETWORK_PROVIDER}, 
	 * 					{@link LocationManager#KEY_PROXIMITY_ENTERING}, 
	 *                  <code>null</code> select provider for me
	 */
	public LocationManagerHelper(Context context, String provider) {
		super();
		this.context = context;	
		this.provider = provider;
	}
	
	public Location getLastKnownLocation() {
		return getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public void startLoggingLocation() {		
		getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}

	public void stopLoggingLocation() {		
		getLocationManager().removeUpdates(locationListener);
	}

	
	/**
	 * @param provider One of {@link LocationManager#GPS_PROVIDER}, 
	 * 					{@link LocationManager#NETWORK_PROVIDER}, 
	 * 					{@link LocationManager#KEY_PROXIMITY_ENTERING}, 
	 *                  <code>null</code> select provider for me
	 */
	public void useProvider(String provider) {
		this.provider = provider;
	}
			
	public void addProximityAlert(double latitude, double longitude,
			float radius, long expiration, PendingIntent intent) {
		locationManager.addProximityAlert(latitude, longitude, radius,
				expiration, intent);
	}

	public void removeProximityAlert(PendingIntent intent) {
		locationManager.removeProximityAlert(intent);
	}

	public void removeUpdates(LocationListener listener) {
		locationManager.removeUpdates(listener);
	}

	public void requestLocationUpdates(long minTime,
			float minDistance, LocationListener listener, Looper looper) {
		locationManager.requestLocationUpdates(getProvider(), minTime, minDistance,
				listener, looper);
	}

	public void requestLocationUpdates(long minTime,
			float minDistance, LocationListener listener) {
		locationManager.requestLocationUpdates(getProvider(), minTime, minDistance,
				listener);
	}

	protected LocationManager getLocationManager() {
		if (locationManager == null) {
			locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		}
		return locationManager;
	}		
		
	protected String getProvider() {
		if (provider == null) {
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setAltitudeRequired(true);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(false);
			criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
			criteria.setSpeedRequired(false);
			provider = getLocationManager().getBestProvider(criteria, true);
		}
		if (provider == null) {
			provider = LocationManager.GPS_PROVIDER;
		}
		return provider;		
	}

	public long[] getLastKnownLocationAsLongArray() {		
		Location location = getLastKnownLocation();
		if (location != null) {
			return new long[] {
					Double.valueOf(location.getLatitude() * 1000000).longValue(),
					Double.valueOf(location.getLongitude() * 1000000).longValue()
			};
		}
		return null;
	}
}
