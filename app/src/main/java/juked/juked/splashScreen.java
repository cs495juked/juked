package juked.juked;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.app.Dialog;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class splashScreen extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

//public class splashScreen extends AppCompatActivity {

    private Button joinButton;
    private Button createButton;
    private TextInputLayout lobbyCodeInput;
    private TextInputLayout nicknameInput;
    private TextInputLayout hostNicknameInput;
    private TextView generatedLobbyCodeText;


    private DatabaseReference mDatabase; // reference to firebase database

    Dialog joinDialog;
    Dialog createDialog;

    public static Player mPlayer;

    private static final String CLIENT_ID = "3bf8e5d5bae64c319395b084204e71ea";
    private static final String REDIRECT_URI = "juked://callback";

    private static final int REQUEST_CODE = 1337;       //LEEEEEEETTT

    public static String accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        //Log.d("response", "I am the access token " + accessToken);

        joinDialog = new Dialog(this);
        createDialog = new Dialog(this);

        joinButton = findViewById(R.id.joinBtn);
        createButton = findViewById(R.id.createBtn);
        joinDialog.setContentView(R.layout.joinlobbypopup);
        createDialog.setContentView(R.layout.createlobbypopup);

        mDatabase = FirebaseDatabase.getInstance().getReference(); // firebase ref

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinDialog.show();
                final Button joinLobbyFromPopup;
                TextView backFromJoinBtn;
                joinLobbyFromPopup = joinDialog.findViewById(R.id.joinLobbyBtn);
                backFromJoinBtn = joinDialog.findViewById(R.id.backToHomeJoin);

                backFromJoinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinDialog.dismiss();
                    }
                });

                joinLobbyFromPopup.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        lobbyCodeInput = joinDialog.findViewById(R.id.inputLobbyCode);
                        final String lobbyCode = lobbyCodeInput.getEditText().getText().toString();



                        nicknameInput = joinDialog.findViewById(R.id.inputNickname);
                        String nickname = nicknameInput.getEditText().getText().toString();

                        //when creating a new user need to read from firebase and find last created userid in lobby
                        final jukeuser user = new jukeuser(1, nickname, "mr brightside", 0);
                        //Query queryRef = mDatabase.orderByChild("lobbyId").equalTo(lobbyCode);




                        joinDialog.dismiss();
                        startActivity(new Intent(splashScreen.this, testRecycledView.class));

//                        Toast.makeText(splashScreen.this,
//                                nickname + lobbyCode,
//                                Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog.show();
                Button createLobbyFromPopup;
                TextView backBtn = createDialog.findViewById(R.id.backtoHomeBtn);
                createLobbyFromPopup = createDialog.findViewById(R.id.createLobbyBtn);

                generatedLobbyCodeText = createDialog.findViewById(R.id.generatedLobbyCode);
                Random r = new Random();
                final int randomLobbyInt = r.nextInt(9999);


                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        createDialog.dismiss();
                    }
                });

                //generatedLobbyCodeText.setText(lobby); // gets the lobby code

                createLobbyFromPopup.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        hostNicknameInput = createDialog.findViewById(R.id.inputHostNickname);
                        String hostNickname = hostNicknameInput.getEditText().getText().toString();

                        //create new jukeuser for host
                        jukeuser host = new jukeuser(01, hostNickname, "Finalcountdown", 1);
                        jukelobby lobby = new jukelobby(randomLobbyInt, host);
                        //add host user class into the lobby class via ArrayList
                        //store Lobby in database



                        mDatabase.child(String.valueOf(lobby.lobbyId)).setValue(lobby);
                        createDialog.dismiss();
                        setContentView(R.layout.activity_test_recycled_view);

                        Toast.makeText(splashScreen.this,
                                hostNickname + generatedLobbyCodeText.getText(),
                                Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }//end onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            if (response.getType() == AuthenticationResponse.Type.TOKEN) {

                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                accessToken = response.getAccessToken();

                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {

                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(splashScreen.this);
                        mPlayer.addNotificationCallback(splashScreen.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }


                });//this end Spotify.getPlayer

            }//end inner if

        }//end if

    }//end onActivityResult


    /*******************
     * YOU MUST CALL SPOTIFY.DESTROYPLAYER
     * IF NOT IT KEEPS RUNNING AND IS A MEMORY LEAK
     * MEMORY LEAKS ARE BAD MMMMMMMMMKAAAAAAAAAYYYYYYYYYYYY
     */
    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }


    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");

    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error err) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    public String getToken(){
        return accessToken;



    }

}
