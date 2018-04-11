package juked.juked;

import android.content.Context;
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
    List<PlaylistSong> mData;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        v.findViewById(R.id.playlistSongItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("response", mData.get(position).getSongName());
            }
        });


        holder.tv_songName.setText(mData.get(position).getSongName());
        holder.tv_albumName.setText(mData.get(position).getAlbumName());
        holder.tv_artistName.setText(mData.get(position).getArtistName());
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
        private ImageView iv_albumArtwork;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_songName = (TextView) itemView.findViewById(R.id.song_name);
            tv_albumName = (TextView) itemView.findViewById(R.id.album_name);
            tv_artistName = (TextView) itemView.findViewById(R.id.artist_name);
            iv_albumArtwork = (ImageView) itemView.findViewById(R.id.userAvatar);

        }
    }

}
