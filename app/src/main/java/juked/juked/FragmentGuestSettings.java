package juked.juked;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentGuestSettings extends android.support.v4.app.Fragment {


    View v;


    public FragmentGuestSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.guest_settings_fragment,container,false);
        return v;
    }
}
