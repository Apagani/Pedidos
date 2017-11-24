package pedido.ItemAdapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pedido.Logica.Pedido;
import youtube.demo.youtubedemo.R;
 
public class ItemAdapter_pedidos_enviados extends BaseAdapter {
	 
	    private Context context;
	    private List<Pedido> items;
	 
	    public ItemAdapter_pedidos_enviados(Context context, List<Pedido> items) {
	        this.context = context;
	        this.items = items;
	    }
	 
	    @Override
	    public int getCount() {
	        return this.items.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return this.items.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	 
	        View rowView = convertView;
	 
	        if (convertView == null) {
	            // Create a new view into the list.
	            LayoutInflater inflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            rowView = inflater.inflate(R.layout.list_pedidos_enviados, parent, false);
	        }
	 
	        // Set data into the view.
	        TextView cliente = (TextView) rowView.findViewById(R.id.TextclientePedido);
	        TextView fecha = (TextView) rowView.findViewById(R.id.TextfechaPedido);
	        ImageView imagePedido = (ImageView) rowView.findViewById(R.id.iconoPedido);
			
	        Pedido item = this.items.get(position);
	        cliente.setText("Pedido Nro: "+Integer.toString(item.getId()));
	        fecha.setText("Fecha enviado: "+item.getFecha());
	        imagePedido.setImageResource(R.drawable.car);
	 
	        return rowView;
	    }
	 
}
