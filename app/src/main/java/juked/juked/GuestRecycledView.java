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

public class GuestRecycledView extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    public static String lobbyCode;
    public static int dark = 1;

    public static FragmentGuestPlaylist fgp = new FragmentGuestPlaylist();
    public static FragmentGuestHistory fgh = new FragmentGuestHistory();


    private Player player = splashScreen.mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_recycled_view);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        TextView tv_displayLobbyCode = findViewById(R.id.displayLobbyCodeNum);
        tv_displayLobbyCode.setText(tv_displayLobbyCode.getText() + " " + lobbyCode);

        if (dark ==0) {
            setTheme(R.style.LightTheme);
        }


        if (dark == 1 ){
            setTheme(R.style.AppTheme);
        }



        // add Fragment Here

        adapter.addFragments(fgp, "Playlist");
        adapter.addFragments(fgh, "History");
        adapter.addFragments(new FragmentGuestSettings(), "Settings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }






}
