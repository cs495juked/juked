package juked.juked;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    public static List<PlaylistSong> mData;
    MyViewHolder vHolder;
    View v;
    Database appDB = splashScreen.appDB;
    Player player = splashScreen.mPlayer;


    public interface ItemLongClickListener {
        void onItemLongClick(View v,int pos);
    }

    public RecyclerViewAdapter(Context mContext, List<PlaylistSong> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public static List<PlaylistSong> getPlayList() {
        return mData;
    }
    public static List<Vote> getVotes() {
        //LAURA add this
        return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(mContext).inflate(R.layout.playlist_item,parent,false);
        vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        v.findViewById(R.id.upvote_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Log.d("response", "upvote");
                Log.d("response", mData.get(position).getVote());
                int newvote = 1;
                int oldvote;
                String songURI = mData.get(position).getSongURI();
                if(mData.get(position).getVote().equals("none")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsupselected);
                    mData.get(position).setVote("up");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) +1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                    oldvote = 0;
                    //appDB.updateVote(songURI,newvote,oldvote);
                }
                else if(mData.get(position).getVote().equals("down")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsupselected);
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdown);
                    mData.get(position).setVote("up");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) +2 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                    oldvote = -1;
                    //appDB.updateVote(songURI,newvote,oldvote);
                }
                else if(mData.get(position).getVote().equals("up")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsup);
                    mData.get(position).setVote("none");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) -1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                    oldvote = 1;
                    //appDB.updateVote(songURI,newvote,oldvote);
                }
            }
        });

        v.findViewById(R.id.downvote_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                int newvote = -1;
                int oldvote;
                String songURI = mData.get(position).getSongURI();
                if(mData.get(position).getVote().equals("none")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdownselected);
                    mData.get(position).setVote("down");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) -1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                    oldvote = 0;
                    //appDB.updateVote(songURI,newvote,oldvote);
                }
                else if(mData.get(position).getVote().equals("up")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdownselected);
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsup);
                    mData.get(position).setVote("down");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) -2 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                    oldvote = 1;
                    //appDB.updateVote(songURI,newvote,oldvote);
                }
                else if(mData.get(position).getVote().equals("down")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdown);
                    mData.get(position).setVote("none");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) + 1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                    oldvote = -1;
                    //appDB.updateVote(songURI,newvote,oldvote);
                }

            }
        });

        holder.tv_songName.setSelected(true); //allow scrolling text
        holder.tv_songName.setText(mData.get(position).getSongName());
        holder.tv_albumName.setSelected(true); //allow scrolling text
        holder.tv_albumName.setText(mData.get(position).getAlbumName());
        holder.tv_artistName.setSelected(true); //allow scrolling text
        holder.tv_artistName.setText(mData.get(position).getArtistName());
        holder.tv_adderNickname.setText(mData.get(position).getAdderNickname());
        holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));

        Picasso.with(mContext).load(mData.get(position).getAlbumArtwork()).into(holder.iv_albumArtwork);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            int adapterPos = vHolder.getAdapterPosition();
            Log.d("onLongClick1", adapterPos + ":" + mData.get(adapterPos).getSongName());
            if (adapterPos != RecyclerView.NO_POSITION && splashScreen.isHost) {
                if(adapterPos == 0 && mData.size() == 1) {
                    for(int i = 0; i < mData.size(); i++)
                        Log.d("onLongClick2", "AdapterPos: " + adapterPos + " i: " + i + " @mData.get(i):" + mData.get(i).getSongName());

                    player.pause(null);
                    notifyItemRemoved(adapterPos);
                    notifyItemRangeChanged(adapterPos, mData.size());
                    appDB.deleteSong(mData.get(adapterPos).getSongURI());
                    mData.remove(adapterPos); // might need to delete later because ^^
                }
                else if(adapterPos == 0 && mData.size() > 1) {
                    for(int i = 0; i < mData.size(); i++)
                        Log.d("onLongClick3", "AdapterPos:" + adapterPos + " i: " + i + " @mData.get(i):" + mData.get(i).getSongName());

                    player.playUri(null, mData.get(adapterPos+1).getSongURI(),0,0);
                    notifyItemRemoved(adapterPos);
                    notifyItemRangeChanged(adapterPos, mData.size());
                    appDB.deleteSong(mData.get(adapterPos).getSongURI());
                    //mData.remove(adapterPos); // might need to delete later because ^^
                }
                else {
                    for(int i = 0; i < mData.size(); i++)
                        Log.d("onLongClick4", "AdapterPos:" + adapterPos + " i: " + i + " @mData.get(i):" + mData.get(i).getSongName());
                    notifyItemRemoved(adapterPos);
                    notifyItemRangeChanged(adapterPos, mData.size());
                    appDB.deleteSong(mData.get(adapterPos).getSongURI());
                    //mData.remove(adapterPos); // might need to delete later because ^^
                }
            }
            return false;
        }

        public void setItemLongClickListener(ItemLongClickListener ic) {
            this.itemLongClickListener=ic;
        }

        ItemLongClickListener itemLongClickListener;

        private TextView tv_songName;
        private TextView tv_artistName;
        private TextView tv_albumName;
        private TextView tv_adderNickname;
        private TextView tv_voteTotal;
        private ImageView iv_albumArtwork;
        private ImageView iv_upvoteIcon;
        private ImageView iv_downvoteIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tv_songName = (TextView) itemView.findViewById(R.id.song_name);
            tv_albumName = (TextView) itemView.findViewById(R.id.album_name);
            tv_artistName = (TextView) itemView.findViewById(R.id.artist_name);
            iv_albumArtwork = (ImageView) itemView.findViewById(R.id.userAvatar);
            tv_adderNickname = (TextView) itemView.findViewById(R.id.user_nickname);
            iv_upvoteIcon = (ImageView) itemView.findViewById(R.id.upvote_icon);
            iv_downvoteIcon = (ImageView) itemView.findViewById(R.id.downvote_icon);
            tv_voteTotal = itemView.findViewById(R.id.vote_total);


        }

//        @Override
//        public void onClick(View v) {
//            Log.d("HYBB", "onClick is here");
//            Toast.makeText(v.getContext(), "ViewItem: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//
//            //delete(getAdapterPosition()); //calls the method above to delete
//        }

    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }
}
