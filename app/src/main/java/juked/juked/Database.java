package juked.juked;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.*;

public class Database {
    public ArrayList<jukeuser> users;
    public DatabaseReference appDatabase;
    public String lobby;
    public String uid;
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


    public void deleteSong(final String songURI) {

        appDatabase.child(lobby).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    userList.add(user);
                }

                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).song != null && userList.get(i).song.getSongURI().equals(songURI)) {
                        //userList.get(i).song = null;
                        uSong = null;
                        appDatabase.child(lobby).child("votes").child(songURI).removeValue();
                        appDatabase.child(lobby).child("users").child(String.valueOf(userList.get(i).userId)).child("song").removeValue();
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

    public void updateVote(final String songURI, final int voteBalanceChange, final int voteDir) {
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
                        votes.get(i).setVote(voteDir);
                        appDatabase.child(lobby).child("votes").child(votes.get(i).getURI()).child(votes.get(i).getUID()).setValue(votes.get(i));
                        break;
                    }
                }
                if (found == 0) {
                    Vote vote = new Vote(songURI,uid);
                    vote.setVote(voteDir);
                    appDatabase.child(lobby).child("votes").child(vote.getURI()).child(vote.getUID()).setValue(vote);
                }


                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();

                for (DataSnapshot snapshot : dataSnapshot.child("users").getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    if ((user.song != null) && (user.song.getSongURI().equals(songURI))) {

                        user.song.setVoteBalance(user.song.getVoteBalance()+voteBalanceChange);
                        appDatabase.child(lobby).child("users").child(String.valueOf(user.userId)).setValue(user);
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

    public void updateSong(final Song newSong) {
        appDatabase.child(lobby).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jukeuser pulledUser = dataSnapshot.child(uid).getValue(jukeuser.class);
                int listPosition = 0;
                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    userList.add(user);
                }
                //Song songList[] = new Song[userList.size()];
                int totalsongs = 0;
                int exists = 0;
                Log.d("here", newSong.getSongName());
                for (int i = 0; i < userList.size() ; i++) {
                    Song userSong = userList.get(i).song;
                    if (pulledUser.userId == userList.get(i).userId) {
                        listPosition = i;
                    }
                    if(userSong != null){
                        if (newSong.getSongURI().equals(userList.get(i).song.getSongURI())) {
                            Log.d("here", "made it 2");
                            exists = 1;
                            break;
                        }

                        if(userList.get(i).userId != pulledUser.userId){
                            totalsongs++;
                        }
                    }

                }
                if (exists == 0) {
                    Log.d("here", "made it");
                    newSong.setVoteBalance(1);
                    pulledUser.setUserSong(newSong);
                    uSong = newSong;
                    Vote uVote = new Vote(newSong.getSongURI(), uid);
                    uVote.setVote(1);
                    appDatabase.child(lobby).child("votes").child(uVote.getURI()).child(uVote.getUID()).setValue(uVote);
                    appDatabase.child(lobby).child("users").child(uid).setValue(pulledUser);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBTag",databaseError.getMessage());
            }
        });
    }

    public void addNewUser (final String nickname) {
        appDatabase.child(lobby).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
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
                Log.d("DBTag",databaseError.getMessage());
            }
        });
    }

    /*public void addNewUser (final String nickname) {
        appDatabase.child(lobby).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                if (count == (long) 0) {
                    throw new java.lang.RuntimeException("Invalid Lobby Code");
                }
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
                /*try {
                    long count = dataSnapshot.getChildrenCount();
                    if (count == (long) 0) {
                        throw new java.lang.RuntimeException("Invalid Lobby Code");
                    }
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
                catch (RuntimeException e) {
                    Log.d("DBTag",e.getMessage());
                    splashScreen.appDB = null;
                }*/
                /*int newUser = (int) count + 1;
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
                Log.d("DBTag",databaseError.getMessage());
            }
        });
    }*/
}
