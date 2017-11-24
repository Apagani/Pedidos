package pedido.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

public class ConexionActivity extends Activity implements OnClickListener {

	private EditText ip;
	private EditText puerto;
	private EditText alias_vendedor;
	private EditText descuento_maximo;
	
	private Button BtnGuardar;
	private Button BtnCancelar;
	private Button BtnGuardarAlias;
	private Button BtnCancelarAlias;
	private Button BtnGuardarPorcentajeMaximo;
	
	private DatabaseHandler sqlite;
	private Comunicacion_via_Socket comunicacion;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conexion);
		
		ip = (EditText)findViewById(R.id.editIp);
		puerto = (EditText)findViewById(R.id.editPuerto);
		
		BtnGuardar = (Button) findViewById(R.id.buttonGuardarPuertoIp);
		BtnGuardar.setOnClickListener(this);
		BtnCancelar = (Button) findViewById(R.id.buttonCancelarPuertoIp);
		BtnCancelar.setOnClickListener(this);

		//abro la base de datos
	    sqlite = new DatabaseHandler(this);
		sqlite.Abrir();
		
		//cargo en pantalla el Puerto, Ip y alias del vendedor almacenados en la base
		ip.setText(sqlite.getIp());
		puerto.setText(Integer.toString(sqlite.getPuerto()));

	}
	
	public void onClick(View v) {
		switch ( v.getId() ) 
		{
			case R.id.buttonGuardarPuertoIp:
				//modifico o agrego la conexion
				sqlite.Add_conexion(ip.getText().toString(), puerto.getText().toString());
				finish();
			    break;
			case R.id.buttonCancelarPuertoIp: 
				finish();
				break;

		}
	}
	
}
