package pedido.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.List;
import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.ItemAdapter.ItemAdapter_producto;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import pedido.Activitys.detalle_productoActivity;
import youtube.demo.youtubedemo.R;


public class MainFragment extends Fragment {
    private List<Producto> lista_productos;
    private ListView listview_productos;
    private Comunicacion_via_Socket comunicacion;
    ProgressBar mLoading;
    private DatabaseHandler sqlite;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);

        listview_productos = (ListView) v.findViewById(R.id.listproductos);

        mLoading = (ProgressBar) v.findViewById(R.id.loading_productos);
        mLoading.setVisibility(View.VISIBLE);

        //abro la base de datos
        sqlite = new DatabaseHandler(getActivity());
        sqlite.Abrir();

        //obtengo los datos de conexion
        comunicacion = new Comunicacion_via_Socket(sqlite.getIp(),sqlite.getPuerto());

        new MiTarea().execute("");

        return v;
    }

    public void Cargar_detalle_en_vista(){


         //seteo el adaptador a la lista con una lista de productos
        listview_productos.setAdapter(new ItemAdapter_producto(getActivity(), lista_productos));
        listview_productos.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Intent intent = new Intent(getActivity(), detalle_productoActivity.class);
                Producto objeto = (Producto) adapter.getItemAtPosition(position);
                intent.putExtra("producto",objeto);
                getActivity().startActivity(intent);
            }
        });


    }

    public boolean Conectar_y_monstrar(){
        //primero conecto y y luego efectuo la operacion
        if (comunicacion.ConnectTimeout()){
            lista_productos = comunicacion.Enviar_peticion_Productos(1);
            return true;
        }else{
            //cartel informativo
            Toast.makeText(getActivity().getApplicationContext(), "En este momento no hay conexión de datos disponibles. Intentelo cuando tenga una conexión estable de datos", Toast.LENGTH_SHORT).show();
            mLoading.setVisibility(View.GONE);
            return false;
        }
    }


    private class MiTarea extends AsyncTask<String, Float, Integer> {

        protected void onPreExecute() {
            mLoading.setVisibility(View.VISIBLE);
        }

        protected Integer doInBackground(String... urls) {
            if (comunicacion.ConnectTimeout()){
                lista_productos = comunicacion.Enviar_peticion_Productos(1);
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
