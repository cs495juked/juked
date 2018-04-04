package juked.juked;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<PlaylistSong> mData;

    public RecyclerViewAdapter(Context mContext, List<PlaylistSong> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.playlist_item,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);


        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_songName.setText(mData.get(position).getSongName());
        holder.tv_albumName.setText(mData.get(position).getAlbumName());
        holder.tv_artistName.setText(mData.get(position).getArtistName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_songName;
        private TextView tv_artistName;
        private TextView tv_albumName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_songName = (TextView) itemView.findViewById(R.id.song_name);
            tv_albumName = (TextView) itemView.findViewById(R.id.album_name);
            tv_artistName = (TextView) itemView.findViewById(R.id.artist_name);
        }
    }

}