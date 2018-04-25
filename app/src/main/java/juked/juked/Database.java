package juked.juked;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Database {
    public ArrayList<jukeuser> users;
    public DatabaseReference appDatabase;
    public String lobby;
    private String uid;
    public Song uSong;

    public Database (String lobbyCode) {
        appDatabase = FirebaseDatabase.getInstance().getReference();
        lobby = lobbyCode;
        users = null;
    }
    public Database () {
        appDatabase = FirebaseDatabase.getInstance().getReference();
        users = null;
    }
    public void setLobby (String lobbyCode) {
        lobby = lobbyCode;
    }

    public void setID (String userid) {
        uid = userid;
    }


    public void deleteSong(final String songName) {
        appDatabase.child(lobby).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    userList.add(user);
                }
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).song.getSongName().equals(songName)) {
                        userList.get(i).song = null;
                        appDatabase.child(lobby).child("users").child(String.valueOf(userList.get(i).userId)).setValue(userList.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBTag",databaseError.getMessage());
            }
        });
    }

    public void setVote() {
        appDatabase.child(lobby).setValue("votes");
    }

    public void updateVote(final String songURI, final int update, final int prev) {
        appDatabase.child(lobby).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //grab the votes from the database, update the user vote
                int found = 0;
                ArrayList<Vote> votes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child("votes").child(songURI).getChildren()) {
                    Vote vote = snapshot.getValue(Vote.class);
                    votes.add(vote);
                }
                for (int i = 0; i < votes.size(); i++) {
                    if (votes.get(i).getUID().equals(uid)) {
                        found = 1;
                        votes.get(i).setVote(update);
                        appDatabase.child(lobby).child("votes").child(votes.get(i).getURI()).child(votes.get(i).getUID()).setValue(votes.get(i));
                        break;
                    }
                }
                if (found == 0) {
                    Vote vote = new Vote(songURI,uid);
                    appDatabase.child(lobby).child("votes").child(vote.getURI()).child(vote.getUID()).setValue(vote);
                }
                //update the song where it is stored
                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.child("users").getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    if ((user.song != null) && (user.song.getSongURI().equals(songURI))) {

                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBTag",databaseError.getMessage());
            }
        });
    }

    public void updateSong(final Song newSong) {
        appDatabase.child(lobby).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jukeuser pulledUser = dataSnapshot.child(uid).getValue(jukeuser.class);

                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    userList.add(user);
                }
                //Song songList[] = new Song[userList.size()];
                int totalsongs = 0;
                for (int i = 0; i < userList.size() ; i++) {
                    Song userSong = userList.get(i).song;
                    if ((userSong != null) && (userList.get(i).userId != pulledUser.userId)) {
                       // int pos = userSong.getPosition();
                        //songList[pos] = userSong;

                        totalsongs++;
                    }
                }
                newSong.setPosition(totalsongs);
                pulledUser.setUserSong(newSong);
                uSong = newSong;
                Vote uVote = new Vote(newSong.getSongURI(),uid);
                uVote.setVote(1);
                newSong.setNumVotes(1);
                newSong.setUpVotes(1);
                appDatabase.child(lobby).child("votes").child(uVote.getURI()).child(uVote.getUID()).setValue(uVote);
                appDatabase.child(lobby).child("users").child(uid).setValue(pulledUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBTag",databaseError.getMessage());
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
                appDatabase.child(String.valueOf(lobby)).child("users").child(String.valueOf(user.userId)).setValue(user);
                uid = String.valueOf(user.userId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
