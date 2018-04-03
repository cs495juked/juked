package juked.juked;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.app.Dialog;
import java.util.Random;

public class splashScreen extends AppCompatActivity {

    private Button joinButton;
    private Button createButton;
    private TextInputLayout lobbyCodeInput;
    private TextInputLayout nicknameInput;
    private TextInputLayout hostNicknameInput;
    private TextView generatedLobbyCodeText;

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




        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinDialog.show();
                Button joinLobbyFromPopup;
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
                        String lobbyCode = lobbyCodeInput.getEditText().getText().toString();



                        nicknameInput = joinDialog.findViewById(R.id.inputNickname);
                        String nickname = nicknameInput.getEditText().getText().toString();

                        joinDialog.dismiss();
                        setContentView(R.layout.activity_host_lobby);

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
                String lobby = String.format("%04d", randomLobbyInt);


                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        createDialog.dismiss();
                    }
                });

                generatedLobbyCodeText.setText(lobby);

                createLobbyFromPopup.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        hostNicknameInput = createDialog.findViewById(R.id.inputHostNickname);
                        String hostNickname = hostNicknameInput.getEditText().getText().toString();


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
