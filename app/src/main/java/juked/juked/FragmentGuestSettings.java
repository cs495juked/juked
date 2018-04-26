package juked.juked;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.widget.*;
import android.view.*;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;


public class FragmentGuestSettings extends android.support.v4.app.Fragment {


    View v;
    // static android.app.Activity activity;
    //splashScreen spotifyPlayer = new splashScreen();  // this instance throws a warning.
    private Button sLogout;
    private Button jLogout;
    private Button dayTheme;
    private Button nightTheme;
    // private Switch mSwitch;
    private static final String Tag="Color";


    public FragmentGuestSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.guest_settings_fragment,container,false);
        //final juked.juked.FragmentHostSettings.Activity activity = this;
        sLogout = (Button) v.findViewById(R.id.spLogout);
        jLogout = (Button) v.findViewById(R.id.jkLogout);
        dayTheme =(Button) v.findViewById(R.id.day);
        nightTheme= (Button) v.findViewById(R.id.night);

        sLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //place your action here
                splashScreen.mPlayer.logout();
            }
        });

        jLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //place your action here
                //activity.finish();
                System.exit(0);
            }
        });

        dayTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HostRecycledView.dark = 0;
            }
        });

        nightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuestRecycledView.dark = 1;

            }
        });
        return v;
    }



}
