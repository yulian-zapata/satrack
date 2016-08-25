package com.mobi.sactrack.satrack.Networking;

import com.mobi.sactrack.satrack.Models.UserPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Interfaces para conección a servicios de la rest
 */
public interface HttpService {

    @GET("GET-api-Usuarios")
    Call<ArrayList<UserPojo>> getUsers(
    );

}