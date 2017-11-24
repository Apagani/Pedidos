package pedido.Activitys;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pedido.Logica.Cliente;
import pedido.Logica.Item;
import pedido.Logica.Pedido;
import pedido.Logica.Producto;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by Alejandro on 14/9/2017.
 */

public class detalle_productoActivity extends Activity implements OnClickListener {
    Button menos, mas, addCarrito;
    TextView  nombreProducto, precioProducto,precioProductodolar, descripcionProducto, cantidadPedida;
    Producto p;
    private DatabaseHandler sqlite;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        p = ((Producto)getIntent().getExtras().getSerializable("producto"));

        Typeface face_regular=Typeface.createFromAsset(getAssets(),"fonts/Barlow-Regular.ttf");
        Typeface face_medium=Typeface.createFromAsset(getAssets(),"fonts/Barlow-Medium.ttf");
        Typeface face_semibold=Typeface.createFromAsset(getAssets(),"fonts/Barlow-SemiBold.ttf");


        //lleno la vista
        nombreProducto = (TextView)findViewById(R.id.TxtNombreProducto);
        nombreProducto.setText(p.getDenominacion());
        nombreProducto.setTypeface(face_regular);
        precioProducto = (TextView)findViewById(R.id.TxtPrecioProducto);
        precioProducto.setText(Double.toString(p.getPrecio()));
        precioProducto.setTypeface(face_medium);
        precioProductodolar = (TextView) findViewById(R.id.TxtPrecioProdu);
        precioProductodolar.setTypeface(face_medium);
        descripcionProducto = (TextView)findViewById(R.id.TxtDescripcionProducto);
        descripcionProducto.setText(p.getDescripcion());
        descripcionProducto.setTypeface(face_regular);
        cantidadPedida = (TextView)findViewById(R.id.Txtcantidadpedida);
        cantidadPedida.setText(Integer.toString(0));
        cantidadPedida.setTypeface(face_semibold);

        menos = (Button) findViewById(R.id.btn_menos);
        menos.setOnClickListener( this );
        mas = (Button) findViewById(R.id.btn_mas);
        mas.setOnClickListener( this );
        addCarrito = (Button) findViewById(R.id.btn_add_carrito);
        addCarrito.setOnClickListener( this );
        addCarrito.setTypeface(face_semibold);

        sqlite = new DatabaseHandler(this);

        String link = new String(p.getLink());
        if (isUrl(link)){
            new DownloadImageTask((ImageView) findViewById(R.id.ImagenProducto)).execute(link);
        }

    }

    public void onClick(View v) {
        switch ( v.getId() )
        {
            case R.id.btn_menos:
                int value = Integer.parseInt(cantidadPedida.getText().toString());
                if (value > 0){
                    value -= 1;
                    cantidadPedida.setText(Integer.toString(value));
                }
                break;
            case R.id.btn_mas:
                int value2 = Integer.parseInt(cantidadPedida.getText().toString());
                value2 += 1;
                cantidadPedida.setText(Integer.toString(value2));
                break;
            case R.id.btn_add_carrito:
                AddProductoCarrito();
                finish();
                break;
        }
    }

    private static boolean isUrl(String s) {
        String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";

        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();

        } catch (RuntimeException e) {
            return false;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void AddProductoCarrito(){
        //agregar al pedido existente el producto, o crear un pedido y agregarle el producto
        //verifico si existe un pedido aun sin enviar

        sqlite.Abrir();

        int idpedido = sqlite.existePedido_en_carrito();
        Item i = new Item(p,idpedido,Integer.parseInt(cantidadPedida.getText().toString()),"");

        if (idpedido != 0){
            if (sqlite.producto_no_esta_en_carrito(idpedido,p.getCod())){
                sqlite.Add_Item(i);
                Toast.makeText(getApplicationContext(), "El producto fue agregado al Carrito.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "El producto ya existe en el carrito", Toast.LENGTH_SHORT).show();
            }
        }else{
            //no existe un pedido en estado "Carrito", creo el pedido y le agrego el item
            sqlite.Add_pedido(sqlite.getIdUser(),ObtenerFechaActual(),i);
            Toast.makeText(getApplicationContext(), "El producto fue agregado al Carrito.", Toast.LENGTH_SHORT).show();
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
