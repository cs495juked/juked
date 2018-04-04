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

import java.util.ArrayList;
import java.util.List;

public class FragmentHostPlaylist extends android.support.v4.app.Fragment {

    View v;

    private RecyclerView myRecyclerView;
    private List<PlaylistSong> playlistSongs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playlistSongs = new ArrayList<>();
        playlistSongs.add(new PlaylistSong("Sweet Home Alabama", "Lynard Skynard", "idk"));
        playlistSongs.add(new PlaylistSong("Hello", "Adele", "24"));

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
}
