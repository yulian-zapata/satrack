package com.mobi.sactrack.satrack;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.mobi.sactrack.satrack.Activities.MainActivity;
import com.mobi.sactrack.satrack.Models.Users;
import com.mobi.sactrack.satrack.Networking.HttpService;
import com.mobi.sactrack.satrack.Networking.Service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobi.sactrack.satrack.Utils.Utils.buildResponse;
import static org.easymock.EasyMock.expect;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNiceNew;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Test para la actividad IntroActivity
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivity.class, GoogleMap.class})
public class MainActivityTest {
    MainActivity spyActivity;
    Toolbar toolbar;
    Call call;
    Service service;
    HttpService httpService;



    @Before
    public void setup() throws Exception {
        spyActivity = spy(new MainActivity());
        service = mock(Service.class);
        call = mock(Call.class);
        httpService = mock(HttpService.class);
    }

    /**
     * Valida que se cargue el Layout correcto
     * para la actividad.
     */
    @Test
    public void testOnCreate() {
        Method[] appCompatActivityOnCreateMethods =
                PowerMockito.methods(AppCompatActivity.class, "onCreate");
        android.support.v4.app.FragmentManager fragment  = mock(android.support.v4.app.FragmentManager.class);
        SupportMapFragment supportMapFragment = mock(SupportMapFragment.class);
        PowerMockito.suppress(appCompatActivityOnCreateMethods);
        doNothing().when(spyActivity).setContentView(anyInt());
        doNothing().when(spyActivity).getUsers();
        when(spyActivity.getSupportFragmentManager())
                .thenReturn(fragment);
        when(fragment.findFragmentById(R.id.map))
                .thenReturn(supportMapFragment);
        spyActivity.onCreate(any(Bundle.class));
        verify(spyActivity, times(1)).setContentView(R.layout.activity_main);
    }

    /**
     * valdiad el llamado
     */
    @Test(expected = java.lang.AssertionError.class)
    public void testOnMapReady(){
        GoogleMap googleMap = createMock(GoogleMap.class);
        replay(googleMap);
        spyActivity.onMapReady(googleMap);
    }


    @Test
    public void testOnSuccess200() throws Exception {
        whenNew(Service.class).withNoArguments().thenReturn(service);
        when(service.createService(any(Class.class), anyString())).thenReturn(httpService);
        when(httpService.getUsers()).thenReturn(call);


        okhttp3.Response okHttp = buildResponse(200, "", null);
        final Response<List<Users>> response = Response.success(null, okHttp);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback<List<Users>> call =
                        (Callback<List<Users>>) invocation.getArguments()[0];
                call.onResponse(null, response);
                return null;
            }
        }).when(call).enqueue(any(Callback.class));
        spyActivity.getUsers();

        verify(spyActivity, Mockito.times(1)).addUser(any(Response.class));
    }

}