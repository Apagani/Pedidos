package pedido.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import pedido.Activitys.detalle_productoActivity;
import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.ItemAdapter.ItemAdapter_producto;
import pedido.ItemAdapter.ItemAdapter_producto_carrito;
import pedido.Logica.Item;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 30/8/2017.
 */

public class Carrito extends Fragment {
    private List<Item> lista_productos;
    private ListView listview_productos;
    private Comunicacion_via_Socket comunicacion;
    ProgressBar mLoading;
    private DatabaseHandler sqlite;
    private ProgressDialog dialog;
    private int idPedido;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_carrito,container,false);

        mLoading = (ProgressBar) root.findViewById(R.id.loading);
        mLoading.setVisibility(View.VISIBLE);

        //abro la base de datos
        sqlite = new DatabaseHandler(getActivity());
        sqlite.Abrir();

        idPedido = sqlite.existePedido_en_carrito();

        if (idPedido == 0){
            Toast.makeText(getActivity().getApplicationContext(), "No existen productos agregados al carrito.", Toast.LENGTH_SHORT).show();
        }else{
            lista_productos = sqlite.getAllItemsPedido(idPedido);
            Cargar_detalle_en_vista();
        }

        return root;
      }

    public void Cargar_detalle_en_vista(){
        //seteo el adaptador a la lista con una lista de productos
        listview_productos.setAdapter(new ItemAdapter_producto_carrito(getActivity(), lista_productos));
        listview_productos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Intent intent = new Intent(getActivity(), detalle_productoActivity.class);
                Producto objeto = (Producto) adapter.getItemAtPosition(position);
                intent.putExtra("producto",objeto);
                getActivity().startActivity(intent);
            }
        });
    }

}
