package pedido.ItemAdapter;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import pedido.Logica.Producto;
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

import pedido.Fragments.MainFragment;
import youtube.demo.youtubedemo.R;

public class ItemAdapter_producto extends BaseAdapter {

    // Declare Variables
    Context context;
    private List<Producto> productos;

    LayoutInflater inflater;

    public ItemAdapter_producto(Context context, List<Producto> items ) {
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
        TextView txtContenido;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row, parent, false);

        // Locate the TextViews in listview_item.xml
        imgImg = (ImageView) itemView.findViewById(R.id.imagen_producto_list);
        txtTitle = (TextView) itemView.findViewById(R.id.tv_titulo_single_post_circuito);
        txtContenido = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito);

        // Capture position and set to the TextViews
        Producto item = this.productos.get(position);
        imgImg.setImageResource(R.drawable.cel1);
        txtTitle.setText(item.getDenominacion());
        txtContenido.setText(Double.toString(item.getPrecio()));

        //String u = new String(item.getLink());
        //new DownloadImageTask((ImageView) itemView.findViewById(R.id.imagen_producto_list)).execute(u);

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

