package pedido.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pedido.Activitys.Pedido_enviado;
import pedido.Activitys.detalle_productoActivity;
import pedido.ItemAdapter.ItemAdapter_pedidos_enviados;
import pedido.Logica.Pedido;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

public class Compras extends Fragment {
    private DatabaseHandler sqlite;
    private ListView listPedidos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compras,container,false);

        //lista que contendra los pedidos
        listPedidos = (ListView) v.findViewById(R.id.lista_pedidos_enviados);

        //abro la base de datos
        sqlite = new DatabaseHandler(getActivity());
        sqlite.Abrir();

        ArrayList<Pedido> Pedidos_enviados = sqlite.getLista_pedidos_enviados();

        if (Pedidos_enviados.size()== 0){
            Toast.makeText(getActivity().getApplicationContext(), "No ha realizado compras aun.", Toast.LENGTH_SHORT).show();
        }else{
            listPedidos.setAdapter(new ItemAdapter_pedidos_enviados(getActivity(), Pedidos_enviados));
            listPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
                {
                    Intent intent = new Intent(getActivity(), Pedido_enviado.class);
                    int idProducto = ((Pedido) adapter.getItemAtPosition(position)).getId();
                    intent.putExtra("pedido",idProducto);
                    getActivity().startActivity(intent);

                }

            });

        }


        return v;
    }
}
