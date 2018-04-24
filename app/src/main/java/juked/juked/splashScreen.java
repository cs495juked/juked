package juked.juked;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.app.Dialog;
import juked.juked.Database;

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

import java.util.HashMap;
import java.util.List;
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
    private boolean isHost;

    //private EditText userNickname;
    //private EditText lobbyNumber;


    //private DatabaseReference mDatabase; // refer to firebase database
    //private DatabaseReference jDatabase; // refer to the child
    private Database appDB;

    Dialog joinDialog;
    Dialog createDialog;
    public int globalUserId = 0;
    public int lobbyCode = 0;
    public static Player mPlayer;

    public List<PlaylistSong> songList; //= RecyclerViewAdapter.mData;

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

        builder.setScopes(new String[]{"user-read-private", "streaming", "user-modify-playback-state"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        //Log.d("response", "I am the access token " + accessToken);

        joinDialog = new Dialog(this);
        createDialog = new Dialog(this);

        joinButton = findViewById(R.id.joinBtn);
        createButton = findViewById(R.id.createBtn);
        joinDialog.setContentView(R.layout.joinlobbypopup);
        createDialog.setContentView(R.layout.createlobbypopup);
        appDB = new Database();

        //mDatabase = FirebaseDatabase.getInstance().getReference(); // firebase ref
        // jDatabase = FirebaseDatabase.getInstance().getReference().child("Lobby");

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinDialog.show();
                final Button joinLobbyFromPopup;
                TextView backFromJoinBtn;
                joinLobbyFromPopup = joinDialog.findViewById(R.id.joinLobbyBtn);
                backFromJoinBtn = joinDialog.findViewById(R.id.backToHomeJoin);

                isHost = false;
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
                        final String nickname = nicknameInput.getEditText().getText().toString();


                        //when the user selects a song, pass it here to user selection.

                        //final jukeuser user = new jukeuser(1, nickname, 0);

                        // add user to a party
                        // get the id number for the user
                        appDB.setLobby(lobbyCode);
                        appDB.addNewUser(nickname);
                       /* mDatabase.child(lobbyCode).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //CHECK THIS AFTER WE ADD SONGS
                                //this code may have issues with reading the children of users (song objects) as children as well, throwing off the overall count
                                long count = dataSnapshot.getChildrenCount();
                                int newUser = (int) count + 1;
                                Log.d("responses", "count: " + String.valueOf(count) + " | newUser: " + String.valueOf(newUser));
                                final jukeuser user = new jukeuser(newUser, nickname, 0);
                                mDatabase.child(String.valueOf(lobbyCode)).child(String.valueOf(user.userId)).setValue(user);
                                globalUserId = user.userId;
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/
                        //userId++;

                        //mDatabase.child(String.valueOf(lobbyCode)).child(String.valueOf(userId)).setValue(user); // keep



                        // jDatabase.push().setValue(user); // random value, will change later

                        //HashMap<String,String> dataMap = new HashMap<String, String>();   //another try
                        //dataMap.put("Lobby", lobbyCode);
                        //dataMap.put("user",nickname);
                        //mDatabase.push().setValue(dataMap);


                        joinDialog.dismiss();
                        startActivity(new Intent(splashScreen.this, GuestRecycledView.class));
                        GuestRecycledView.lobbyCode = lobbyCode;
                        FragmentGuestPlaylist.appDB = appDB;
//                        startActivity(new Intent(splashScreen.this, testRecycledView.class));
//                        testRecycledView.lobbyCode = lobbyCode;

                        Toast.makeText(splashScreen.this,
                                nickname + lobbyCode,
                                Toast.LENGTH_SHORT).show();
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
                isHost = true;

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
                        //jukeuser host = new jukeuser(01, hostNickname, 1);
                        //jukelobby lobby = new jukelobby(randomLobbyInt, host);
                        //add host user class into the lobby class via ArrayList
                        //store Lobby in database

                        int lobbyCode = randomLobbyInt;

                        appDB.setLobby(String.valueOf(lobbyCode));
                        appDB.addNewUser(hostNickname);
                        //mDatabase.child(String.valueOf(lobbyCode)).child(String.valueOf(host.userId)).setValue(host); //keep MR
                        //globalUserId = host.userId;
                        generatedLobbyCodeText.setText(Integer.toString(lobbyCode));

//                        mDatabase.child(String.valueOf(lobby.lobbyId)).setValue(lobby); // first code
                        createDialog.dismiss();
                        startActivity(new Intent(splashScreen.this, HostRecycledView.class));
                        //these will need used again in join lobby as well
                        HostRecycledView.lobbyCode = Integer.toString(lobbyCode);
                        FragmentHostPlaylist.appDB = appDB;
                        //FragmentHostPlaylist.lobbyCode = Integer.toString(lobbyCode);
                        //FragmentHostPlaylist.globalUserId = Integer.toString(globalUserId);

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

            case kSpPlaybackNotifyAudioDeliveryDone:

                Log.d("response", "song is over");
                //songList.clear();
                if(isHost) {
                    songList = RecyclerViewAdapter.mData;
                    songList.remove(0);
                    mPlayer.playUri(null, songList.get(0).getSongURI(), 0, 0);
                }
                break;

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
