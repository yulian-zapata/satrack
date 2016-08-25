package com.mobi.sactrack.satrack.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobi.sactrack.satrack.Models.Users;
import com.mobi.sactrack.satrack.Networking.HttpService;
import com.mobi.sactrack.satrack.Networking.Service;
import com.mobi.sactrack.satrack.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * actividad principal
 */
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        getUsers();
        mapFragment.getMapAsync(this);
    }


    /**
     * Se encarga de inicializar el mapa
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



    /**
     * Se encarga de traer todos los usuarios
     *
     */
    public void getUsers() {
        Service retrofit = new Service();
        HttpService service = retrofit.createService(HttpService.class, "");
        Call<Users> user = service.getUsers();

        user.enqueue(
                new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        if (response.isSuccessful()) {
                            // TODO : AGREGAR USUARIOS A LA DB SQLITE
                            Log.i(TAG, ""+response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Log.e(TAG, " get failed " + t);
                    }
                });
    }

}
