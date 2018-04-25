package juked.juked;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    public static List<PlaylistSong> mData;
    MyViewHolder vHolder;
    View v ;


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
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) +1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                }
                else if(mData.get(position).getVote().equals("down")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsupselected);
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdown);
                    mData.get(position).setVote("up");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) +2 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                }
                else if(mData.get(position).getVote().equals("up")){
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsup);
                    mData.get(position).setVote("none");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) -1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                }

            }
        });

        v.findViewById(R.id.downvote_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                if(mData.get(position).getVote().equals("none")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdownselected);
                    mData.get(position).setVote("down");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) -1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                }
                else if(mData.get(position).getVote().equals("up")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdownselected);
                    holder.iv_upvoteIcon.setImageResource(R.drawable.thumbsup);
                    mData.get(position).setVote("down");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) -2 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
                }
                else if(mData.get(position).getVote().equals("down")){
                    holder.iv_downvoteIcon.setImageResource(R.drawable.thumbsdown);
                    mData.get(position).setVote("none");
                    mData.get(position).setVoteTotal(Integer.valueOf(mData.get(position).getVoteTotal()) + 1 );
                    holder.tv_voteTotal.setText(String.valueOf(mData.get(position).getVoteTotal()));
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_songName;
        private TextView tv_artistName;
        private TextView tv_albumName;
        private TextView tv_adderNickname;
        private ImageView iv_albumArtwork;
        private ImageView iv_upvoteIcon;
        private ImageView iv_downvoteIcon;
        private TextView tv_voteTotal;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_songName = itemView.findViewById(R.id.song_name);
            tv_albumName = itemView.findViewById(R.id.album_name);
            tv_artistName = itemView.findViewById(R.id.artist_name);
            iv_albumArtwork = itemView.findViewById(R.id.userAvatar);
            tv_adderNickname = itemView.findViewById(R.id.user_nickname);
            iv_upvoteIcon = itemView.findViewById(R.id.upvote_icon);
            iv_downvoteIcon = itemView.findViewById(R.id.downvote_icon);
            tv_voteTotal = itemView.findViewById(R.id.vote_total);

        }
    }

}
