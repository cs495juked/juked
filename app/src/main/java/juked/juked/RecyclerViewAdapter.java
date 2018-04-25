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

                if(mData.get(position).getVote().equals("none")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsupselected);
                    mData.get(position).setVote("up");
                }
                else if(mData.get(position).getVote().equals("down")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsupselected);
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdown);
                    mData.get(position).setVote("up");
                }
                else if(mData.get(position).getVote().equals("up")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsup);
                    mData.get(position).setVote("none");
                }

            }
        });

        v.findViewById(R.id.downvote_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                if(mData.get(position).getVote().equals("none")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdownselected);
                    mData.get(position).setVote("down");
                }
                else if(mData.get(position).getVote().equals("up")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdownselected);
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsup);
                    mData.get(position).setVote("down");
                }
                else if(mData.get(position).getVote().equals("down")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdown);
                    mData.get(position).setVote("none");
                }
            }
        });

        holder.tv_songName.setSelected(true); //allow scrolling text
        holder.tv_songName.setText(mData.get(position).getSongName());
        holder.tv_albumName.setText(mData.get(position).getAlbumName());
        holder.tv_artistName.setText(mData.get(position).getArtistName());
        holder.tv_adderNickname.setText(mData.get(position).getAdderNickname());
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
            if (adapterPos != RecyclerView.NO_POSITION && splashScreen.isHost) {
                Toast.makeText(v.getContext(), "ViewItem: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                notifyItemRemoved(adapterPos);
                notifyItemRangeChanged(adapterPos, getItemCount());
                appDB.deleteSong(mData.get(adapterPos).getSongName());
                mData.remove(adapterPos); // might need to delete later because ^^
                if(!mData.isEmpty()) {
                    player.playUri(null, mData.get(0).getSongURI(),0,0);
                }
                else {
                    player.pause(null);
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
