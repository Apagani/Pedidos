package pedido.Activitys;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.Fragments.MainFragment;
import pedido.ItemAdapter.ItemAdapter_pedidos_enviados;
import pedido.ItemAdapter.ItemAdapter_producto_carrito;
import pedido.ItemAdapter.ItemAdapter_producto_pedido_enviado;
import pedido.Logica.Cliente;
import pedido.Logica.Item;
import pedido.Logica.Pedido;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 16/10/2017.
 */

public class Pedido_enviado extends Activity {
    private List<Item> lista_productos;
    private ListView listview_productos;
    private DatabaseHandler sqlite;
    private int idPedido;
    private TextView total;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_enviado);

        idPedido = (Integer)getIntent().getExtras().getSerializable("pedido");

        listview_productos = (ListView) findViewById(R.id.listproductos_pedido_enviado);
        total = (TextView) findViewById(R.id.textTotalPedido);

        //abro la base de datos
        sqlite = new DatabaseHandler(this);
        sqlite.Abrir();

        lista_productos = sqlite.getAllItemsPedido(idPedido);
        listview_productos.setAdapter(new ItemAdapter_producto_pedido_enviado(this, lista_productos));
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
}
