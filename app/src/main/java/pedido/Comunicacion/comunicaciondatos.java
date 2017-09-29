package pedido.Comunicacion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pedido.Logica.Producto;
import youtube.demo.youtubedemo.R;

/**
 * Created by Alejandro on 22/9/2017.
 */

public class comunicaciondatos extends Activity {

        private Comunicacion_via_Socket comunicacion;
    private List<Producto> lista_productos;
        private ProgressDialog dialog;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_comunicacion_datos);
            dialog = new ProgressDialog(this);
            dialog.setMessage("Descargando...");
            dialog.setTitle("Progreso");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            //Realizamos cualquier otra operación necesaria
            //Creamos una nueva instancia y llamamos al método ejecutar
            //pasándole el string.
            new MiTarea().execute("http://www.ejemplo.com/file.zip");

            //obtengo los datos de conexion
            comunicacion = new Comunicacion_via_Socket("192.168.0.34",5555);



        }

        private class MiTarea extends AsyncTask<String, Float, Integer> {

            protected void onPreExecute() {
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.show(); //Mostramos el diálogo antes de comenzar
            }

            protected Integer doInBackground(String... urls) {
                if (comunicacion.ConnectTimeout()){
                    lista_productos = comunicacion.Enviar_peticion_Productos(1);
                }else{
                    //cartel informativo
                    Toast.makeText(getApplicationContext(), "En este momento no hay conexión de datos disponibles. Intentelo cuando tenga una conexión estable de datos", Toast.LENGTH_SHORT).show();
                }
                return 250;
            }

            protected void onProgressUpdate (Float... valores) {
                int p = Math.round(100*valores[0]);
                dialog.setProgress(p);
            }

            protected void onPostExecute(Integer bytes) {
                dialog.dismiss();
            }
        }

}
