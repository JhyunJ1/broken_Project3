package ac.kr.dankook.broken_project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class location_activity extends AppCompatActivity
		implements OnMapReadyCallback,
		ActivityCompat.OnRequestPermissionsResultCallback{

	private GoogleMap mMap;
	private Marker currentMarker = null;

	private static final String TAG = "googlemap_example";
	private static final int GPS_ENABLE_REQUEST_CODE = 2001;
	private static final int UPDATE_INTERVAL_MS = 1000;  // 1???
	private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5???
	private static final int PERMISSIONS_REQUEST_CODE = 100;
	boolean needRequest = false;


	String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // ?????? ?????????


	Location mCurrentLocatiion;
	LatLng currentPosition;


	private FusedLocationProviderClient mFusedLocationClient;
	private LocationRequest locationRequest;
	private Location location;


	private View mLayout;

	private View decorView;
	private int uiOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.location);

		decorView = getWindow().getDecorView();
		uiOption = getWindow().getDecorView().getSystemUiVisibility();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
			uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
			uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
			uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		mLayout = findViewById(R.id.location);

		locationRequest = new LocationRequest()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL_MS)
				.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


		LocationSettingsRequest.Builder builder =
				new LocationSettingsRequest.Builder();

		builder.addLocationRequest(locationRequest);


		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(final GoogleMap googleMap) {
		Log.d(TAG, "onMapReady :");

		mMap = googleMap;

		setDefaultLocation();

		int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION);
		int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_COARSE_LOCATION);



		if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
				hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED)
			startLocationUpdates();

		else {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

				Snackbar.make(mLayout, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.",
						Snackbar.LENGTH_INDEFINITE).setAction("??????", new View.OnClickListener() {

					@Override
					public void onClick(View view) {

						ActivityCompat.requestPermissions( location_activity.this, REQUIRED_PERMISSIONS,
								PERMISSIONS_REQUEST_CODE);
					}
				}).show();
			}
			else {
				ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
						PERMISSIONS_REQUEST_CODE);
			}
		}

		mMap.getUiSettings().setMyLocationButtonEnabled(true);

		mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {

				Log.d( TAG, "onMapClick :");
			}
		});
	}

	LocationCallback locationCallback = new LocationCallback() {
		@Override
		public void onLocationResult(LocationResult locationResult) {
			super.onLocationResult(locationResult);

			List<Location> locationList = locationResult.getLocations();

			if (locationList.size() > 0) {
				location = locationList.get(locationList.size() - 1);

				currentPosition
						= new LatLng(location.getLatitude(), location.getLongitude());

				String markerTitle = getCurrentAddress(currentPosition);
				String markerSnippet = "??????:" + String.valueOf(location.getLatitude())
						+ " ??????:" + String.valueOf(location.getLongitude());

				Log.d(TAG, "onLocationResult : " + markerSnippet);


				//?????? ????????? ?????? ???????????? ??????
				setCurrentLocation(location, markerTitle, markerSnippet);

				mCurrentLocatiion = location;
			}
		}
	};



	private void startLocationUpdates() {

		if (!checkLocationServicesStatus()) {

			Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
			showDialogForLocationServiceSetting();
		}else {

			int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
					Manifest.permission.ACCESS_FINE_LOCATION);
			int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
					Manifest.permission.ACCESS_COARSE_LOCATION);



			if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
					hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

				Log.d(TAG, "startLocationUpdates : ????????? ???????????? ??????");
				return;
			}


			Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

			mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

			if (checkPermission())
				mMap.setMyLocationEnabled(true);

		}

	}


	@Override
	protected void onStart() {
		super.onStart();

		Log.d(TAG, "onStart");

		if (checkPermission()) {

			Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
			mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

			if (mMap!=null)
				mMap.setMyLocationEnabled(true);

		}


	}


	@Override
	protected void onStop() {

		super.onStop();

		if (mFusedLocationClient != null) {

			Log.d(TAG, "onStop : call stopLocationUpdates");
			mFusedLocationClient.removeLocationUpdates(locationCallback);
		}
	}




	public String getCurrentAddress(LatLng latlng) {
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());

		List<Address> addresses;

		try {
			addresses = geocoder.getFromLocation(
					latlng.latitude,
					latlng.longitude,
					1);
		} catch (IOException ioException) {
			Toast.makeText(this, "???????????? ????????? ????????????", Toast.LENGTH_LONG).show();
			return "???????????? ????????? ????????????";
		} catch (IllegalArgumentException illegalArgumentException) {
			Toast.makeText(this, "????????? GPS ??????", Toast.LENGTH_LONG).show();
			return "????????? GPS ??????";
		}

		if (addresses == null || addresses.size() == 0) {
			Toast.makeText(this, "?????? ?????????", Toast.LENGTH_LONG).show();
			return "?????? ?????????";

		} else {
			Address address = addresses.get(0);
			return address.getAddressLine(0).toString();
		}

	}


	public boolean checkLocationServicesStatus() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}


	public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {


		if (currentMarker != null) currentMarker.remove();


		LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(currentLatLng);
		markerOptions.title(markerTitle);
		markerOptions.snippet(markerSnippet);
		markerOptions.draggable(true);


		currentMarker = mMap.addMarker(markerOptions);

		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
		mMap.moveCamera(cameraUpdate);

	}


	public void setDefaultLocation() {

		LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
		String markerTitle = "???????????? ????????? ??? ??????";
		String markerSnippet = "?????? ???????????? GPS ?????? ?????? ???????????????";


		if (currentMarker != null) currentMarker.remove();

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(DEFAULT_LOCATION);
		markerOptions.title(markerTitle);
		markerOptions.snippet(markerSnippet);
		markerOptions.draggable(true);
		markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		currentMarker = mMap.addMarker(markerOptions);

		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
		mMap.moveCamera(cameraUpdate);
	}


	private boolean checkPermission() {

		int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION);
		int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_COARSE_LOCATION);



		if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
				hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
			return true;
		}
		return false;
	}



	@Override
	public void onRequestPermissionsResult(int permsRequestCode,
										   @NonNull String[] permissions,
										   @NonNull int[] grandResults) {

		super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
		if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

			boolean check_result = true;

			for (int result : grandResults) {
				if (result != PackageManager.PERMISSION_GRANTED) {
					check_result = false;
					break;
				}
			}

			if (check_result) {
				startLocationUpdates();
			} else {
				if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
						|| ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


					Snackbar.make(mLayout, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????. ",
							Snackbar.LENGTH_INDEFINITE).setAction("??????", new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							finish();
						}
					}).show();

				} else {
					Snackbar.make(mLayout, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ",
							Snackbar.LENGTH_INDEFINITE).setAction("??????", new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							finish();
						}
					}).show();
				}
			}

		}
	}


	private void showDialogForLocationServiceSetting() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("?????? ????????? ????????????");
		builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n"
				+ "?????? ????????? ???????????????????");
		builder.setCancelable(true);
		builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent callGPSSettingIntent
						= new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
			}
		});
		builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

			case GPS_ENABLE_REQUEST_CODE:
				if (checkLocationServicesStatus()) {
					if (checkLocationServicesStatus()) {

						Log.d(TAG, "onActivityResult : GPS ????????? ?????????");

						needRequest = true;

						return;
					}
				}
				break;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		// super.onWindowFocusChanged(hasFocus);

		if( hasFocus ) {
			decorView.setSystemUiVisibility( uiOption );
		}
	}
}