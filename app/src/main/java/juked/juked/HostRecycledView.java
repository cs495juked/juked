package juked.juked;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;

public class HostRecycledView extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    public static FragmentHostPlaylist fhp = new FragmentHostPlaylist();
    public static FragmentHostHistory fhh = new FragmentHostHistory();
    public static String lobbyCode;
    static FloatingActionButton playPauseButton;

    private Player player = splashScreen.mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycled_view);

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

}
