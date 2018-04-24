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


public class FragmentHostSettings extends android.support.v4.app.Fragment {


    View v;
    // static android.app.Activity activity;
    //splashScreen spotifyPlayer = new splashScreen();  // this instance throws a warning.
    private Button sLogout;
    private Button jLogout;
    private Button dayTheme;
    private Button nightTheme;
   // private Switch mSwitch;
   private static final String Tag="Color";


    public FragmentHostSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.host_settings_fragment,container,false);
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
                HostRecycledView.dark = 1;

            }
        });
//        Context context = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
//        LayoutInflater localInflater = inflater.cloneInContext(context);
//        return localInflater.inflate(R.layout.host_settings_fragment, container, false);
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


//        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
//            HostRecycledView.dark =1;
//            HostRecycledView.notDark =0;
//            // setTheme(R.style.AppTheme);
//        }
//        else {
//            HostRecycledView.dark =0;
//            HostRecycledView.notDark =1;
//            //
//        }

//        mSwitch = (Switch) v.findViewById(R.id.nMode);
//        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
//            mSwitch.setChecked(true);
//        }
//        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, booleansetTheme(R.style.LightTheme); isChecked) {
//                if (isChecked){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    restartApp();
//                }
//                else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    restartApp();
//                }
//            }
//        });

        return v;
    }


//
//    public void restartApp(){
//
//        Intent i = new Intent(getActivity(),splashScreen.class);
//        startActivity(i);
//        getActivity().finish();
//    }
//


}
