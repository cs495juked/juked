package juked.juked;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.app.Dialog;

public class splashScreen extends AppCompatActivity {

    private Button joinButton;
    private Button createButton;
    private TextInputLayout lobbyCodeInput;
    private TextInputLayout nicknameInput;


    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        myDialog = new Dialog(this);

        joinButton = findViewById(R.id.joinBtn);
        createButton = findViewById(R.id.createBtn);
        myDialog.setContentView(R.layout.joinlobbypopup);


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show();
                Button joinLobbyFromPopup;
                joinLobbyFromPopup = myDialog.findViewById(R.id.joinLobbyBtn);

                joinLobbyFromPopup.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        lobbyCodeInput = myDialog.findViewById(R.id.inputLobbyCode);
                        String lobbyCode = lobbyCodeInput.getEditText().getText().toString();

                        nicknameInput = myDialog.findViewById(R.id.inputNickname);
                        String nickname = nicknameInput.getEditText().getText().toString();




                        myDialog.dismiss();

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
                Toast.makeText(splashScreen.this,
                        R.string.createToast,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}
