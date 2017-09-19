package pedido;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pedido.Fragments.MainFragment;
import pedido.Logica.Producto;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 14/9/2017.
 */

public class detalle_productoActivity extends Activity {
    Button menos, mas, addCarrito;
    TextView  nombreProducto, precioProducto, descripcionProducto, cantidadPedida;
    Producto p;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        p = ((Producto)getIntent().getExtras().getSerializable("producto"));

        //lleno la vista
        nombreProducto = (TextView)findViewById(R.id.TxtNombreProducto);
        nombreProducto.setText(p.getDenominacion());
        precioProducto = (TextView)findViewById(R.id.TxtPrecioProducto);
        precioProducto.setText(Double.toString(p.getPrecio()));
        descripcionProducto = (TextView)findViewById(R.id.TxtDescripcionProducto);
        descripcionProducto.setText(p.getDescripcion());

        String link = new String(p.getLink());
        if (isUrl(link)){
            new DownloadImageTask((ImageView) findViewById(R.id.ImagenProducto)).execute(link);
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
}
