package pedido.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import pedido.Activitys.detalle_productoActivity;
import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.Comunicacion.Paquete_de_datos;
import pedido.Global.ClassGlobal;
import pedido.ItemAdapter.ItemAdapter_producto;
import pedido.ItemAdapter.ItemAdapter_producto_carrito;
import pedido.Logica.Cliente;
import pedido.Logica.Item;
import pedido.Logica.Pedido;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 30/8/2017.
 */

public class Carrito extends Fragment implements View.OnClickListener{
    private List<Item> lista_productos;
    private ListView listview_productos;
    private Comunicacion_via_Socket comunicacion;
    ProgressBar mLoading;
    private DatabaseHandler sqlite;
    private ProgressDialog dialog;
    private int idPedido;
    private int posEliminar;
    private int idProductoEliminar;
    private TextView total;
    private TextView dolar;
    private Button enviar_pedido;
    private Pedido pedido;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_carrito,container,false);

        Typeface face_regular=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Barlow-Regular.ttf");
        Typeface face_medium=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Barlow-Medium.ttf");
        Typeface face_semibold=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Barlow-SemiBold.ttf");

        listview_productos = (ListView) root.findViewById(R.id.listproductos_carrito);
        mLoading = (ProgressBar) root.findViewById(R.id.loading_carrito);
        mLoading.setVisibility(View.INVISIBLE);
        total = (TextView) root.findViewById(R.id.textTotalCarrito);
        total.setTypeface(face_semibold);
        dolar = (TextView) root.findViewById(R.id.textDolarCarrito);
        enviar_pedido = (Button) root.findViewById(R.id.button_enviar_pedido);
        enviar_pedido.setOnClickListener(this);

        dolar.setText("Dolar: USD " + ClassGlobal.getInstance().getDolar() +  " (sujeto a variacion)" );


        //abro la base de datos
        sqlite = new DatabaseHandler(getActivity());
        sqlite.Abrir();

        //obtengo los datos de conexion
        comunicacion = new Comunicacion_via_Socket(sqlite.getIp(),sqlite.getPuerto());

        idPedido = sqlite.existePedido_en_carrito();

        if (idPedido == 0){
            //como es un fragment llamo al principal, que seria la lista de productos
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
            Toast.makeText(getActivity().getApplicationContext(), "No existen productos agregados al carrito.", Toast.LENGTH_SHORT).show();
        }else{
            lista_productos = sqlite.getAllItemsPedido(idPedido);
            pedido = new Pedido();
            pedido.setCliente(new Cliente(sqlite.getIdUser()));
            pedido.setFecha(ObtenerFechaActual());
            pedido.setProductosPedidos(lista_productos);
            pedido.setId(idPedido);
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
                posEliminar = position;
                Eliminar_item(position, (Item)adapter.getItemAtPosition(position));
            }
        });

        total.setText("TOTAL DE COMPRA $" + sumar_total_compra());
    }

    private String sumar_total_compra(){
        double suma = 0d;
        double cantidad;
        double precio;

        for ( int i = 0; i < lista_productos.size(); i ++ ) {
            cantidad = (((Item)lista_productos.get(i)).getCantidad());
            precio = ((Item)lista_productos.get(i)).getProducto().getPrecio();
            suma = suma + (cantidad * precio);
        }

        return Double.toString(suma);
    }

    public void Eliminar_item(int i, Item it){
        idProductoEliminar = it.getIdProducto();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Desea eliminar el producto del carrito? \n" + it.getProducto().getDenominacion())
                .setTitle("Advertencia")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        lista_productos.remove(posEliminar);
                        sqlite.deleteProducto_de_pedido(idPedido,idProductoEliminar );
                        //Cargar_listView_productos_pedidos();
                        Cargar_detalle_en_vista();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() )
        {
            case R.id.button_enviar_pedido:
                new MiTarea().execute("");
                break;
        }
    }


    private class MiTarea extends AsyncTask<String, Float, Integer> {

        private String result = new String("");

        protected void onPreExecute() {

            mLoading.setVisibility(View.VISIBLE);
        }

        protected Integer doInBackground(String... urls) {
            if (comunicacion.ConnectTimeout()){
                Paquete_de_datos p = new Paquete_de_datos();
                p.setPedido(pedido);
                comunicacion.Snd_Msg(p);
                result = "OK";
            }else{
                result = "fail";
            }
            return 250;
        }

        protected void onProgressUpdate (Float... valores) {

        }

        protected void onPostExecute(Integer bytes) {
            mLoading.setVisibility(View.GONE);
            if (result == "OK"){
                //TENGO QUE PASAR EL ESTADO DEL PEDIDO DE PENDIENTE A ARMADO
                sqlite.Update_estado_pedido("Enviado", pedido.getId());
                Toast.makeText(getActivity().getApplicationContext(), "EL PEDIDO FUE ENVIADO. MUCHAS GRACIAS POR SU COMPRA.", Toast.LENGTH_SHORT).show();
                //como es un fragment llamo al principal, que seria la lista de productos
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "En este momento no hay conexión de datos disponibles. Intentelo cuando tenga una conexión estable de datos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String ObtenerFechaActual(){
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate = df.format(date);

        return formatteDate;
    }
}
