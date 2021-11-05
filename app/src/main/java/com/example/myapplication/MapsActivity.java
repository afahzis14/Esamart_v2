package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.server.DataParsing;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private DataParsing call = new DataParsing();
    private ProgressDialog progressDialog;

    int setOnVisibility = 1, setOnCameraMpasView=0;

    LinearLayout ll_menu, ll_camera, ll_galery, ll_history, ll_logout;
    TextView tx_camera, tx_galery, tx_history, tx_logout, tx_menu;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ll_menu = findViewById(R.id.ll_menu);
        ll_camera = findViewById(R.id.ll_camera);
        ll_galery = findViewById(R.id.ll_galery);
        ll_history = findViewById(R.id.ll_history);
        ll_logout = findViewById(R.id.ll_logout);

        tx_menu = findViewById(R.id.tx_menu);
        tx_camera = findViewById(R.id.tx_camera);
        tx_galery = findViewById(R.id.tx_galery);
        tx_history = findViewById(R.id.tx_history);
        tx_logout = findViewById(R.id.tx_logout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        onClick();
        setPermision();
        setVisibility();

        progressDialog = ProgressDialog.show(MapsActivity.this, "Mohon tunggu....",
                "Sedang memeriksa data.", true);
        progressDialog.setCancelable(false);

        ll_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility();
            }
        });
    }

    private void setVisibility(){
        if (setOnVisibility!=0){
            tx_menu.setVisibility(View.GONE);
            tx_camera.setVisibility(View.GONE);
            tx_galery.setVisibility(View.GONE);
            tx_history.setVisibility(View.GONE);
            tx_logout.setVisibility(View.GONE);
            setOnVisibility=0;
        }else{
            tx_menu.setVisibility(View.VISIBLE);
            tx_camera.setVisibility(View.VISIBLE);
            tx_galery.setVisibility(View.VISIBLE);
            tx_history.setVisibility(View.VISIBLE);
            tx_logout.setVisibility(View.VISIBLE);
            setOnVisibility=1;
        }
    }


    private void onClick() {
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.logout(MapsActivity.this);
            }
        });
    }

    public void setPermision() {
        if (!hasPermissions(MapsActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(MapsActivity.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            //show_foto();
        }
    }

    //[Mulai] Permision ===========================================================================
    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //show_foto();
                } else {
                    Toast.makeText(this, "Permission Denied, You cannot access.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    //[Selesai] -----------------------------------------------------------------------------------

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng myLocation = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.isMyLocationEnabled();
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(@NonNull Location location) {
                if (setOnCameraMpasView==0){
                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();
                    LatLng myLocation = new LatLng(currentLatitude,currentLongitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
                    progressDialog.dismiss();
                    setOnCameraMpasView=1;
                }
            }
        });

        //LatLng myLocation = new LatLng(, mMap.getMyLocation().getLongitude());
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }
}