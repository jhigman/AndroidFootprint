package com.example.androidhelloworld;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidHelloWorld extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              // Called when a new location is found by the network location provider.
              makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
          };

          // Register the listener with the Location Manager to receive location updates
          locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);        
          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);        
    }
    
    public void makeUseOfNewLocation(Location location) {
        TextView myLatTextView = (TextView) findViewById(R.id.myLat);
        TextView myLngTextView = (TextView) findViewById(R.id.myLng);
        if(location != null){
        	myLatTextView.setText("" + location.getLatitude());
        	myLngTextView.setText("" + location.getLongitude());
        }
    }

    public void sendFootprint(View v) {

    	String url = "http://utils.kasabi.com/kasabi-digitalcities-externals/annotations.php";
    	
    	try {
	    	HttpClient client = new DefaultHttpClient();
	    	HttpPost post = new HttpPost(url);
	    	
	        TextView myLatTextView = (TextView) findViewById(R.id.myLat);
	        TextView myLngTextView = (TextView) findViewById(R.id.myLng);
	        TextView myTypeTextView = (TextView) findViewById(R.id.myType);
	        TextView myDescriptionTextView = (TextView) findViewById(R.id.myDescription);
	        String myLat = myLatTextView.getText().toString();
	        String myLng = myLngTextView.getText().toString();
	        String myType = myTypeTextView.getText().toString();
	        String myDescription = myDescriptionTextView.getText().toString();

	    	List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	    	pairs.add(new BasicNameValuePair("myLat", myLat));
	    	pairs.add(new BasicNameValuePair("myLng", myLng));
	    	pairs.add(new BasicNameValuePair("myType", myType));
	    	pairs.add(new BasicNameValuePair("myText", myDescription));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);
    		showToast("sent footprint");
    	} catch (Exception e) {
    		showToast(e.toString());
		}
    }

    public void showToast(String msg) {
		Context context = getApplicationContext();
		CharSequence text = msg;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
    }
}