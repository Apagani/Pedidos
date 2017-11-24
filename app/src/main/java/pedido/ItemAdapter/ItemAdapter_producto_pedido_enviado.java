package pedido.ItemAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import pedido.Logica.Item;
import youtube.demo.youtubedemo.R;

public class ItemAdapter_producto_pedido_enviado extends BaseAdapter {

    // Declare Variables
    Context context;
    private List<Item> productos;

    LayoutInflater inflater;

    public ItemAdapter_producto_pedido_enviado(Context context, List<Item> items ) {
        this.context = context;
        productos = items;
    }

    @Override
    public int getCount() {
        return this.productos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        ImageView imgImg;
        TextView txtTitle;
        TextView txtPrecio;
        TextView txtCantidad;
        TextView txtPrecioTotal;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row_carrito, parent, false);

        // Locate the TextViews in listview_item.xml
        imgImg = (ImageView) itemView.findViewById(R.id.imagen_producto_list_carrito);
        txtTitle = (TextView) itemView.findViewById(R.id.textDescripcionProductoCarrito);
        txtPrecio = (TextView) itemView.findViewById(R.id.text_precio_producto_carrito);
        txtCantidad = (TextView) itemView.findViewById(R.id.text_cantidad_producto_carrito);
        txtPrecioTotal = (TextView) itemView.findViewById(R.id.text_total_producto_carrito);

        // Capture position and set to the TextViews
        Item item = this.productos.get(position);
        txtTitle.setText(item.getProducto().getDenominacion());
        txtPrecio.setText("$"+Double.toString(item.getProducto().getPrecio()));
        txtCantidad.setText(Double.toString(item.getCantidad()));
        txtPrecioTotal.setText("$"+Double.toString(item.getProducto().getPrecio()*item.getCantidad()));

        String u = new String(item.getProducto().getLink());
        new DownloadImageTask((ImageView) itemView.findViewById(R.id.imagen_producto_list_carrito)).execute(u);

        return itemView;
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

