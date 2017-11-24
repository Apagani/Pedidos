package pedido.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pedido.Activitys.detalle_productoActivity;
import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.ItemAdapter.ItemAdapter_ctacte;
import pedido.ItemAdapter.ItemAdapter_producto;
import pedido.Logica.ItemCtaCte;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 30/8/2017.
 */

public class Cuenta_corriente extends Fragment {
    private ListView listDetalleCtaCte;
    private Comunicacion_via_Socket comunicacion;
    private DatabaseHandler sqlite;
    private ItemCtaCte detalleItem;
    private ArrayList<ItemCtaCte> Detalle_cliente;
    ProgressBar mLoading;
    private int cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =   inflater.inflate(R.layout.fragment_estado_cuenta,container,false);

        //lista que contendra los pedidos
        listDetalleCtaCte = (ListView) v.findViewById( R.id.list_cta_cte);
        mLoading = (ProgressBar)v.findViewById(R.id.loading_cta_cte);

        //abro la base de datos
        sqlite = new DatabaseHandler(getActivity());
        sqlite.Abrir();
        cliente = sqlite.getIdUser();
        //obtengo los datos de conexion
        comunicacion = new Comunicacion_via_Socket(sqlite.getIp(),sqlite.getPuerto());

        new MiTarea().execute("");

        return v;
    }

    public void Cargar_detalle_en_vista(){
        //seteo el adaptador a la lista con una lista de productos
        listDetalleCtaCte.setAdapter(new ItemAdapter_ctacte(getActivity(), Detalle_cliente));
    }


    private class MiTarea extends AsyncTask<String, Float, Integer> {

        protected void onPreExecute() {

            mLoading.setVisibility(View.VISIBLE);
        }

        protected Integer doInBackground(String... urls) {
            if (comunicacion.ConnectTimeout()){
                Detalle_cliente = comunicacion.Enviar_peticion_detalle_cuenta_corriente(cliente);
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "En este momento no hay conexión de datos disponibles. Intentelo cuando tenga una conexión estable de datos", Toast.LENGTH_SHORT).show();
            }
            return 250;
        }

        protected void onProgressUpdate (Float... valores) {

        }

        protected void onPostExecute(Integer bytes) {
            Cargar_detalle_en_vista();
            mLoading.setVisibility(View.GONE);
        }
    }
}
