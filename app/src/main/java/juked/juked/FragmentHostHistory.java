package juked.juked;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentHostHistory extends android.support.v4.app.Fragment {
    View v;
    private RecyclerView myRecyclerView;
    public  static ArrayList<PlaylistSong> historySongs;
    public  ArrayList<Integer> voteList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        historySongs = new ArrayList<>();
    }


    public FragmentHostHistory() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.host_history_fragment,container,false);

        myRecyclerView = (RecyclerView) v.findViewById(R.id.historyRecyclerView);
        ArrayList<Vote> listVotes = RecyclerViewAdapter.getVotes();
//        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), historySongs, voteList);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), historySongs, listVotes);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerAdapter);
        return v;
    }
}
