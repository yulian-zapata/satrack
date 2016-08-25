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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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

/**
 * Test para la actividad IntroActivity
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivity.class, GoogleMap.class})
public class MainActivityTest {
    MainActivity spyActivity;
    Toolbar toolbar;

    @Before
    public void setup() throws Exception {
        spyActivity = spy(new MainActivity());
        doReturn(toolbar).when(spyActivity).findViewById(R.id.toolbar);
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
    @Test
    public void testOnMapReady(){
        GoogleMap googleMap = createMock(GoogleMap.class);
        replay(googleMap);
        spyActivity.onMapReady(googleMap);
    }

}