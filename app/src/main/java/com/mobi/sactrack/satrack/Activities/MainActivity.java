package com.mobi.sactrack.satrack.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobi.sactrack.satrack.Models.User;
import com.mobi.sactrack.satrack.Models.UserPojo;
import com.mobi.sactrack.satrack.Networking.HttpService;
import com.mobi.sactrack.satrack.Networking.Service;
import com.mobi.sactrack.satrack.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * actividad principal con el mapa
 */
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap map;

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
     *
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
     */
    public void getUsers() {
        Service retrofit = new Service();
        HttpService service = retrofit.createService(HttpService.class, "");
        Call<ArrayList<UserPojo>> user = service.getUsers();

        user.enqueue(
                new Callback<ArrayList<UserPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserPojo>> call, Response<ArrayList<UserPojo>> response) {
                        if (response.isSuccessful()) {
                            addUser(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserPojo>> call, Throwable t) {
                        Log.e(TAG, " get failed " + t);
                    }
                });
    }

    /**
     * Permite agregar usuarios a la db
     *
     * @param response cuerpod e la peticion
     */
    public void addUser(Response<ArrayList<UserPojo>> response) {
        if (!response.body().isEmpty()) {
            ArrayList<User> data = (ArrayList<User>) response.body().clone();
            Realm realm =
                    Realm.getInstance(
                            new RealmConfiguration.Builder(getApplicationContext())
                                    .name("strack.realm")
                                    .build()
                    );

            for (int i = 0; i < data.size(); i++) {
                User user = realm.createObject(User.class);
                user.setId(user.getId());
                user.setNombre(user.getNombre());
                user.setUsuario(user.getUsuario());
            }
            realm.commitTransaction();

        } else {
            Log.e(TAG, "sin datos ");
        }
    }

    /**
     * Retorna todos los datos de los usuarios
     */
    public List<User> getAllUser(List<User> user) {
        Realm realm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(getApplicationContext())
                                .name("strack.realm")
                                .build()
                );
        RealmQuery query = realm.where(User.class);
        RealmResults results = query.findAll();
        for (int i = 0; i < results.size(); i++) {
            user.add((User) results.get(i));
        }
        return user;
    }

}
