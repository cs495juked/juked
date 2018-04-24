package juked.juked;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;

import static juked.juked.R.layout.activity_test_recycled_view;

public class HostRecycledView extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    public static FragmentHostPlaylist fhp = new FragmentHostPlaylist();
    public static FragmentHostHistory fhh = new FragmentHostHistory();
    public static String lobbyCode;
    static FloatingActionButton playPauseButton;


    public static int dark =0;
   // public static int notDark =0;

    public Button dT;
    public Button nT;



    private Player player = splashScreen.mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabLayout = findViewById(R.id.tabs);



//        dT = (Button) findViewById(R.id.day);
//        nT = (Button) findViewById(R.id.night);
//
//        dT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTheme(R.style.LightTheme);
//            }
//        });
//
//        nT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTheme(R.style.LightTheme);
//            }
//        });

        // themeUtils.onActivityCreateSetTheme(this);

        if (dark ==0) {
            setTheme(R.style.LightTheme);
            //tabLayout.setAlpha(0f);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

            //findViewById(android.R.layout.activity_test_recycled_view).invalidate()
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
           // recreate();
           // restartApp();
//            getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
//            getWindow().getDecorView().findViewById(android.R.id.content).requestLayout();
            //findViewById(android.R.id.content).invalidate();
//            ViewGroup vg;
//            vg = findViewById (R.layout.activity_splash_screen);
//            vg.invalidate();
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

   //   }
//
        if (dark == 1 ){
            setTheme(R.style.DarkTheme);
            //tabLayout.setAlpha(1f);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }


//        if(dark == 0){
//
//        }
//        else setTheme(R.style.DarkTheme);

        setContentView(activity_test_recycled_view);

       // change the color



        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        TextView tv_displayLobbyCode = findViewById(R.id.displayLobbyCodeNum);
        tv_displayLobbyCode.setText(tv_displayLobbyCode.getText() + " " + lobbyCode);
        playPauseButton = findViewById(R.id.fab);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // spotify play/pause which calls flipPlayButton
                // toggle.spotifyPlayer();
                tempPlayPause();

                Snackbar.make(view, "Now Playing: Your Mum by Your Dad ft. Your Sister", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });


        // add Fragment Here

        adapter.addFragments(fhp, "Playlist");
        adapter.addFragments(fhh, "History");
        adapter.addFragments(new FragmentHostSettings(), "Settings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /* TODO: hook this function to the Spotify functions
     public void pausePlayer() {
     //do spotify pause

        player.pause(null);

        testRecycledView.flipPlayPauseButton(1);
     }

     public void playPlayer() {
     //do spotify play

        //player.play(null);

        testRecycledView.flipPlayPauseButton(0);
     }*/

    public  boolean tempVar = true;
    public  void tempPlayPause() {
        if(tempVar) {
            tempVar = false;
            player.pause(null);
            flipPlayPauseButton(false);

        }
        else {
            tempVar = true;
            player.resume(null);
            flipPlayPauseButton(true);
        }
    }

    public static void flipPlayPauseButton(boolean isPlaying) {
        if(isPlaying) {
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);

        }
        else {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }

//
//    public void restartApp(){
//
//        Intent i = new Intent(getActivity(),splashScreen.class);
//        startActivity(i);
//        getActivity().finish();
//    }

}
