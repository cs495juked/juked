package juked.juked;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import juked.juked.splashScreen;

public class FragmentHostPlaylist extends android.support.v4.app.Fragment {

    View v;


    private static final int REQUEST_CODE = 1337;   //LEET
    private String accessToken2 = splashScreen.accessToken;
    private static String jsonReturn;

    private Player player = splashScreen.mPlayer;

    private RecyclerView myRecyclerView;
    private List<PlaylistSong> playlistSongs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("response", "I am the access token " + accessToken2);



        playlistSongs = new ArrayList<>();
    }

    public FragmentHostPlaylist() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.playlistfragment,container,false);
        SearchView songSearchBar = v.findViewById(R.id.searchForSongBar);

        songSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d( "response: " , query );


                try {

                    jsonReturn = new HttpTask().execute(getQueryString(query), accessToken2).get();

                }catch(InterruptedException e){
                    Log.d("Response", "InterruptedException");
                    e.printStackTrace();
                }catch (ExecutionException e){
                    Log.d("Response", "ExecutionException");
                    e.printStackTrace();
                }

                playlistSongs.add(new PlaylistSong(getSongName(jsonReturn), getNameOfArtist(jsonReturn), getAlbumName(jsonReturn)));
                Song mySong = new Song(0, getTrack(jsonReturn), getSongName(jsonReturn), getNameOfArtist(jsonReturn), getAlbumCover(jsonReturn), getAlbumName(jsonReturn));
                player.playUri(null, mySong.getSongURI(), 0, 0);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.d( "response" , newText );
                return false;
            }


//        CharSequence searcher = songSearchBar.getQuery();
//        android.util.Log.d("response: ", searcher.toString());
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

        String query = "https://api.spotify.com/v1/search?q=";

        String[] splitStr = input.split("\\s+");

        for(int i=0; i < splitStr.length; i++){
            query = query + "+" + splitStr[i];
        }

        query = query + "&type=track&limit=1";
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
