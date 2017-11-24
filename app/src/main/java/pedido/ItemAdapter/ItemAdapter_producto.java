package pedido.ItemAdapter;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import pedido.Logica.Producto;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import pedido.Fragments.MainFragment;
import youtube.demo.youtubedemo.R;

public class ItemAdapter_producto extends BaseAdapter {

    // Declare Variables
    Context context;
    private List<Producto> productos;
    private List<Producto> productos_a_mostrar;

    LayoutInflater inflater;

    public ItemAdapter_producto(Context context, List<Producto> items ) {
        this.context = context;
        productos = items;
        productos_a_mostrar = items;
    }

    @Override
    public int getCount() {
        return this.productos_a_mostrar.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productos_a_mostrar.get(position);
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

        Typeface face_regular=Typeface.createFromAsset(context.getAssets(),"fonts/Barlow-Regular.ttf");
        Typeface face_medium=Typeface.createFromAsset(context.getAssets(),"fonts/Barlow-Medium.ttf");
        Typeface face_semibold=Typeface.createFromAsset(context.getAssets(),"fonts/Barlow-SemiBold.ttf");

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row, parent, false);

        // Locate the TextViews in listview_item.xml
        imgImg = (ImageView) itemView.findViewById(R.id.imagen_producto_list);
        txtTitle = (TextView) itemView.findViewById(R.id.tv_titulo_single_post_circuito);
        txtContenido = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito);

        // Capture position and set to the TextViews
        Producto item = this.productos_a_mostrar.get(position);
        txtTitle.setText(item.getDenominacion());
        txtContenido.setText(Double.toString(item.getPrecio()));

        txtTitle.setTypeface(face_medium);
        txtContenido.setTypeface(face_semibold);

        String u = new String(item.getLink());
        new DownloadImageTask((ImageView) itemView.findViewById(R.id.imagen_producto_list)).execute(u);

        return itemView;
    }


    /** Método responsável pelo filtro. Utilizaremos em um EditText
     *
     * @return
     */
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();
                //se não foi realizado nenhum filtro insere todos os itens.
                if (filtro == null || filtro.length() == 0) {
                    results.count = productos.size();
                    results.values = productos;
                } else {
                    //creo la lista con los productos filtrados
                    List<Producto> productos_filtrados = new ArrayList<Producto>();

                    //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                    for (int i = 0; i < productos.size(); i++) {
                        Producto data = productos.get(i);

                        filtro = filtro.toString().toLowerCase();
                        String denominacionProducto = data.getDenominacion().toLowerCase();

                        if (denominacionProducto.contains(filtro)) {
                            //cunple con la condicion del filto, lo agrego a la lista a mostrar
                            productos_filtrados.add(data);
                        }
                    }
                    // Define o resultado do filtro na variavel FilterResults
                    results.count = productos_filtrados.size();
                    results.values = productos_filtrados;
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                productos_a_mostrar = (List<Producto>) results.values; // Valores filtrados.
                notifyDataSetChanged();  // Notifica a lista de alteração
            }

        };
        return filter;
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

