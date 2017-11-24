package pedido.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pedido.Logica.Cliente;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 30/8/2017.
 */

public class MiCuenta extends Fragment {
    private DatabaseHandler sqlite;
    private Cliente c;
    private TextView nombre, dir, localidad, iva, cuit, telefono, saldo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_micuenta,container,false);

        nombre = (TextView) v.findViewById(R.id.text_nombre);
        dir = (TextView) v.findViewById(R.id.text_direccion);
        localidad = (TextView) v.findViewById(R.id.text_localidad);
        iva = (TextView) v.findViewById(R.id.text_iva);
        cuit = (TextView) v.findViewById(R.id.text_cuit);
        telefono = (TextView) v.findViewById(R.id.text_telefono);
        saldo = (TextView) v.findViewById(R.id.text_saldo);


        //abro la base de datos
        sqlite = new DatabaseHandler(getActivity());
        sqlite.Abrir();

        c = sqlite.getClienteCompleto();

        nombre.setText(c.getNombre());
        dir.setText(c.getDomicilio());
        localidad.setText(c.getLocalidad());
        iva.setText(c.getIVA());
        cuit.setText(c.getCUIT());
        telefono.setText(c.getTelefono());
        saldo.setText(c.getSaldo().toString());


        return v;
    }
}