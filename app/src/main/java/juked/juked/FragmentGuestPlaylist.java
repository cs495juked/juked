package juked.juked;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentGuestPlaylist extends android.support.v4.app.Fragment {

    View v;


    ListView list;
    GuestListViewAdapter adapter;
    SearchView editsearch;

    ArrayList<Song> arraylist = new ArrayList<Song>();

    //public static String lobbyCode;
    public static Database appDB;

    private static final int REQUEST_CODE = 1337;   //LEET
    private String accessToken2 = splashScreen.accessToken;
    private static String jsonReturn;

    private Player player = splashScreen.mPlayer;

    private RecyclerView myRecyclerView;
    private List<PlaylistSong> playlistSongs;
    //private DatabaseReference fDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("response", "I am the access token " + accessToken2);

        playlistSongs = new ArrayList<>();
    }

    public FragmentGuestPlaylist() {
    }

    public String getNextTrack(String input){

        String str = "";

        try {

            JSONObject json = new JSONObject(input);
            JSONObject items = json.getJSONObject("tracks");

            str = items.getString("next");

            Log.d("Response", "HELLO<<<<<<<   " + str);
            return str;

        }catch (JSONException e){
            Log.d("Response", "Failed getting album cover");
            e.printStackTrace();
            return "";
        }

    }//end getNextTrack


    public ArrayList<Song> populateSearch(String input){

        String json = "";
        ArrayList<Song> myList = new ArrayList<Song>();

        try {

            json = new HttpTask().execute(getQueryString(input), accessToken2).get();
            myList.add(new Song (0, getTrack(json), getSongName(json), getNameOfArtist(json), getAlbumCover(json), getAlbumName(json)));
            //searchSongs.add(new PlaylistSong(getSongName(json), getNameOfArtist(json), getAlbumName(json)));

            //Log.d("response", "Song name: " + getSongName(json));
            //Log.d("response", "next song is " + getNextTrack(json));

            for (int i = 0; i < 2; i++) {

                json = new HttpTask().execute(getNextTrack(json), accessToken2).get();
                //searchSongs.add(new PlaylistSong(getSongName(json), getNameOfArtist(json), getAlbumName(json)));
                myList.add(new Song (0, getTrack(json), getSongName(json), getNameOfArtist(json), getAlbumCover(json), getAlbumName(json)));

                //Log.d("response", "Song name: " + getSongName(json));
                //Song mySong = new Song(0, getTrack(jsonReturn), getSongName(jsonReturn), getNameOfArtist(jsonReturn), getAlbumCover(jsonReturn), getAlbumName(jsonReturn));

            }
        } catch(InterruptedException e){
            Log.d("Response", "InterruptedException");
            e.printStackTrace();
        }catch (ExecutionException e){
            Log.d("Response", "ExecutionException");
            e.printStackTrace();
        }

        Log.d("arraylist", Integer.toString(myList.size()));

        return myList;


    }//end populateSearch



    public void querySearchedSong(int position){

        //fDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("arraylist", Integer.toString(arraylist.size()));
        Log.d("positiion", Integer.toString(position));

        //String query = arraylist.get(position).getSongName() + " " +arraylist.get(position).getArtistName() + " " + arraylist.get(position).getAlbumName() ;
        String uri = arraylist.get(position).getSongURI();
        Log.d("response", "uri is: " + uri);

        list.setVisibility(v.GONE);
        PlaylistSong pls = new PlaylistSong(arraylist.get(position).getSongName(), arraylist.get(position).getArtistName(), arraylist.get(position).getAlbumName(), arraylist.get(position).getAlbumCover());

        playlistSongs.add(pls);
        GuestRecycledView.fgh.historySongs.add(pls);

        player.playUri(null, uri, 0, 0);
        Log.d("userSong", arraylist.get(position).getSongName());
        final Song userSong = arraylist.get(position);

        appDB.updateSong(userSong);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.guest_playlist_fragment,container,false);

        // Locate the ListView
        list = (ListView) v.findViewById(R.id.list_view);
        list.setVisibility(View.GONE);

        //Adds the listener for DB changes
        Log.d("DBTag","app.DB lobby is: "+appDB.lobby);
        appDB.appDatabase.child(appDB.lobby).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DBTag", "I made it inside on DataChange");
                ArrayList<jukeuser> userList = new ArrayList<jukeuser>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    jukeuser user = snapshot.getValue(jukeuser.class);
                    userList.add(user);
                }
                ArrayList<PlaylistSong> playList = new ArrayList<PlaylistSong>();
                int songs = 0;
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).song != null) {
                        songs++;
                        Log.d("DBTag","Song is: " + userList.get(i).song.getSongName());
                    }
                }
                int curr = 0;
                while (songs > curr) {
                    Log.d("DBTag",String.valueOf(songs)+ " > " + String.valueOf(curr));
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).song != null) {
                            Song userSong = userList.get(i).song;
                            int position = userSong.getPosition();
                            if (position == curr) {
                                PlaylistSong ps = new PlaylistSong(userSong.getSongName(), userSong.getArtistName(), userSong.getAlbumName(), userSong.getAlbumCover());
                                playList.add(ps);
                                curr++;
                            }
                        }
                    }
                }
                if (songs != 0) {
                    //playlistSongs.clear();
                    //Log.d("DBTag", "I made it here where songs do not equal zero!");
                    for (int i = 0; i < playList.size(); i++) {
                        //playlistSongs.add(playList.get(i));
                        Log.d("DBTag","playlist.get(" + String.valueOf(i) + ") is: " + playList.get(i).getSongName());
                    }
                    myRecyclerView = (RecyclerView) v.findViewById(R.id.playlistRecyclerView);
                    RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), playList);
                    myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    myRecyclerView.setAdapter(recyclerAdapter);
                    //update my UI object here
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DBTag",databaseError.getMessage());
            }
        });

        final SearchView songSearchBar = v.findViewById(R.id.guestSearchForSongBar);

        // Makes the entire searchBar clickable
        songSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songSearchBar.setIconified(false);
            }
        });

        songSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                arraylist = populateSearch(query);
                Log.d("arraylist", Integer.toString(arraylist.size()));
                ArrayList<String> songNameList = new ArrayList<>();

                for (int i = 0; i < arraylist.size(); i++) {
                    songNameList.add(arraylist.get(i).getSongName() + " by " + arraylist.get(i).getArtistName());
                }

                Context cont = v.getContext();

                // Pass results to HostListViewAdapter Class
                adapter = new GuestListViewAdapter(cont, songNameList);

                // Binds the Adapter to the ListView
                list.setAdapter(adapter);

                String text = query;
                adapter.filter(text);

                list.setVisibility(View.VISIBLE);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }


        });

        myRecyclerView = (RecyclerView) v.findViewById(R.id.playlistRecyclerView);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), playlistSongs);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerAdapter);
        return v;
    }



    public String getTrack(String temp){

        String str = "";

        try {

            JSONObject json = new JSONObject(temp);
            JSONObject items = json.getJSONObject("tracks");
            JSONArray jsonArray = items.getJSONArray("items");

            JSONObject help = jsonArray.getJSONObject(0);
            str = help.getString("uri");


            Log.d("Response", "HELLO<<<<<<<   " + str);
            return str;

        }catch (JSONException e){
            Log.d("Response", "Failed getting track uri");
            e.printStackTrace();
            return "";
        }

    }//end getTrack


    public String getQueryString(String input){

        String query = "https://api.spotify.com/v1/search?q=\"";

        String[] splitStr = input.split("\\s+");

        for(int i=0; i < splitStr.length; i++){
            query = query + "+" + splitStr[i];
        }

        query = query + "\"&type=track&limit=1";
        Log.d("Response", "QUERY = " + query);
        return query;

    }//end getQuery


    public String getNameOfArtist(String input){

        String str = "";

        try {

            JSONObject json = new JSONObject(input);
            JSONObject items = json.getJSONObject("tracks");
            JSONArray jsonArray = items.getJSONArray("items");
            JSONObject album = jsonArray.getJSONObject(0);
            JSONArray artist = album.getJSONArray("artists");
            JSONObject name = artist.getJSONObject(0);
            str = name.getString("name");


            Log.d("Response", "HELLO<<<<<<<   " + str);
            return str;

        }catch (JSONException e){
            Log.d("Response", "Failed getting the name of artist");
            e.printStackTrace();
            return "";
        }

    }//end getNameOfArtist


    public String getSongName(String input){

        String str = "";

        try {

            JSONObject json = new JSONObject(input);
            JSONObject items = json.getJSONObject("tracks");
            JSONArray jsonArray = items.getJSONArray("items");

            JSONObject help = jsonArray.getJSONObject(0);
            str = help.getString("name");


            Log.d("Response", "HELLO<<<<<<<   " + str);
            return str;

        }catch (JSONException e){
            Log.d("Response", "Failed getting track uri");
            e.printStackTrace();
            return "";
        }


    }//endGetSongName


    public String getAlbumCover(String input){

        String str = "";

        try {


            JSONObject json = new JSONObject(input);
            JSONObject items = json.getJSONObject("tracks");
            JSONArray jsonArray = items.getJSONArray("items");
            JSONObject album = jsonArray.getJSONObject(0);
            JSONObject help = album.getJSONObject("album");

            JSONArray images = help.getJSONArray("images");
            JSONObject smallest = images.getJSONObject(0);
            str = smallest.getString("url");

            Log.d("Response", "HELLO<<<<<<<   " + str);
            return str;

        }catch (JSONException e){
            Log.d("Response", "Failed getting album cover");
            e.printStackTrace();
            return "";
        }

    }

    public String getAlbumName(String input){

        String str = "";

        try {

            JSONObject json = new JSONObject(input);
            JSONObject items = json.getJSONObject("tracks");
            JSONArray jsonArray = items.getJSONArray("items");
            JSONObject album = jsonArray.getJSONObject(0);
            JSONObject help = album.getJSONObject("album");

            str = help.getString("name");

            Log.d("Response", "HELLO<<<<<<<   " + str);
            return str;

        }catch (JSONException e){
            Log.d("Response", "Failed getting album cover");
            e.printStackTrace();
            return "";
        }
    }

    public int getSongLength(String input){

        int length = 0;

        try {

            JSONObject json = new JSONObject(input);
            JSONObject items = json.getJSONObject("tracks");
            JSONArray jsonArray = items.getJSONArray("items");

            JSONObject help = jsonArray.getJSONObject(0);
            length = help.getInt("duration_ms");


            Log.d("Response", "HELLO<<<<<<<   " + length);
            return length;

        }catch (JSONException e){
            Log.d("Response", "Failed getting track uri");
            e.printStackTrace();
            return 0;
        }

    }//end getSongLength

}//end FragmentHostPlayer
