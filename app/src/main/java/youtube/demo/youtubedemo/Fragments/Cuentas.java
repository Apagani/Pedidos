package youtube.demo.youtubedemo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 30/8/2017.
 */

public class Cuentas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estado_cuenta,container,false);
    }
}
