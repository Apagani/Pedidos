package pedido.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 30/8/2017.
 */

public class Carrito extends Fragment {
    private ProgressBar mLoading = null;
    Button prueba;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_carrito,container,false);

        mLoading = (ProgressBar) root.findViewById(R.id.loading);
        mLoading.setVisibility(View.VISIBLE);
        prueba = (Button) root.findViewById(R.id.button1);
        prueba.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if (mLoading.getVisibility()== View.GONE){
                    mLoading.setVisibility(View.VISIBLE);
                }else{
                    mLoading.setVisibility(View.GONE);
                }
            }
        });

        return root;
      }

}
