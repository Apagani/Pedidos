package pedido.ItemAdapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pedido.Logica.ItemCtaCte;
import youtube.demo.youtubedemo.R;
 
public class ItemAdapter_ctacte extends BaseAdapter {
	 
	    private Context context;
	    private List<ItemCtaCte> items;
	 
	    public ItemAdapter_ctacte(Context context, List<ItemCtaCte> items) {
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

			Typeface face_regular=Typeface.createFromAsset(context.getAssets(),"fonts/Barlow-Regular.ttf");
			Typeface face_medium=Typeface.createFromAsset(context.getAssets(),"fonts/Barlow-Medium.ttf");
			Typeface face_semibold=Typeface.createFromAsset(context.getAssets(),"fonts/Barlow-SemiBold.ttf");
	 
	        if (convertView == null) {
	            // Create a new view into the list.
	            LayoutInflater inflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            rowView = inflater.inflate(R.layout.list_item_ctacte, parent, false);
	        }
	 
	        // Set data into the view.
	        TextView fecha = (TextView) rowView.findViewById(R.id.TextFechaCteCte);
	        TextView montoFact = (TextView) rowView.findViewById(R.id.TextMontoFact);
	        TextView montoHaber = (TextView) rowView.findViewById(R.id.TextMontoHaber);
	        TextView montoSaldo = (TextView) rowView.findViewById(R.id.TextMontoSaldo);
	 
	        ItemCtaCte item = this.items.get(position);
	        fecha.setText(item.getFecha());
	        montoFact.setText("$"+item.getMontoFact().toString());
	        montoHaber.setText("$"+item.getMontoHaber().toString());
	        montoSaldo.setText("$"+item.getMontoSaldo().toString());

			fecha.setTypeface(face_regular);
			montoFact.setTypeface(face_medium);
			montoHaber.setTypeface(face_regular);
			montoSaldo.setTypeface(face_medium);

			/*if (position == (items.size()-1)){
				//pinto el saldo de rojo
				montoSaldo.setTextColor(Color.RED);
			}*/
	 
	        return rowView;
	    }
	 
}