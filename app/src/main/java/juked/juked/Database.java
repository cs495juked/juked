package juked.juked;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Database {
    private DatabaseReference appDatabase;
    private String lobby;
    private String uid;

    public Database (String lobbyCode) {
        appDatabase = FirebaseDatabase.getInstance().getReference();
        lobby = lobbyCode;
    }
    public Database () {
        appDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void setLobby (String lobbyCode) {
        lobby = lobbyCode;
    }

    public void setID (String userid) {
        uid = userid;
    }

    public void updateSong(final Song newSong) {
        appDatabase.child(lobby).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jukeuser pulledUser = dataSnapshot.child(uid).getValue(jukeuser.class);

                pulledUser.setUserSong(newSong);
                appDatabase.child(lobby).child(uid).setValue(pulledUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNewUser (final String nickname) {
        appDatabase.child(lobby).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CHECK THIS AFTER WE ADD SONGS
                //this code may have issues with reading the children of users (song objects) as children as well, throwing off the overall count
                long count = dataSnapshot.getChildrenCount();
                int newUser = (int) count + 1;
                Log.d("responses", "count: " + String.valueOf(count) + " | newUser: " + String.valueOf(newUser));
                jukeuser user;
                if (newUser == 1) {
                    user = new jukeuser(newUser, nickname, 1);
                } else {
                    user = new jukeuser(newUser, nickname, 0);
                }
                //final jukeuser user = new jukeuser(newUser, nickname, 0);
                appDatabase.child(String.valueOf(lobby)).child(String.valueOf(user.userId)).setValue(user);
                uid = String.valueOf(user.userId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //appDatabase.child(lobby).child(String.valueOf(user.userId)).setValue(user);
    }

    public jukeuser getUser() {
        readUser(new FirebaseUserCallback() {
            @Override
            public void onCallback(jukeuser grabbedUser) {
                //return grabbedUser;
            }
        });
        return null;
    }

    private void readUser (final FirebaseUserCallback userCallback) {
        appDatabase.child(lobby).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jukeuser grabbedUser = dataSnapshot.child(uid).getValue(jukeuser.class);
                userCallback.onCallback(grabbedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBerror",databaseError.getMessage());
            }
        });
    }


    public ArrayList<jukeuser> getUserList() {
        Log.d("DBTAG","inside of getUserList");
        readUserList(new FirebaseUserListCallback() {
            @Override
            public void onCallback(ArrayList<jukeuser> userList) {
                Log.d("DBTAG","returned inside of readUserList");
                //return userList;
            }
        });
        Log.d("DBTAG","returned outside of readUserList");
        return null;
    }

    private void readUserList (final FirebaseUserListCallback userListCallback) {
        appDatabase.child(lobby).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    userList.add(user);
                }
                userListCallback.onCallback(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBerror",databaseError.getMessage());
            }
        });
    }


    //Interfaces used for asynch callbacks
    private interface FirebaseUserCallback {
        void onCallback(jukeuser grabbedUser);
    }
    private interface FirebaseUserListCallback {
        void onCallback(ArrayList<jukeuser> userlist);
    }
}
