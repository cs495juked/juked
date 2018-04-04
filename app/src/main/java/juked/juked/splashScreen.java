package juked.juked;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Random;


public class splashScreen extends AppCompatActivity {

    private Button joinButton;
    private Button createButton;
    private TextInputLayout lobbyCodeInput;
    private TextInputLayout nicknameInput;
    private TextInputLayout hostNicknameInput;
    private TextView generatedLobbyCodeText;


    private DatabaseReference mDatabase; // reference to firebase database

    Dialog joinDialog;
    Dialog createDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
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
                        setContentView(R.layout.activity_host_main);

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
                        setContentView(R.layout.activity_host_main);

                        Toast.makeText(splashScreen.this,
                                hostNickname + generatedLobbyCodeText.getText(),
                                Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });



    }

}
