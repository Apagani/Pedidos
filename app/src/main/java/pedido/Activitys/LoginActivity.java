package pedido.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import pedido.Comunicacion.Comunicacion_via_Socket;
import pedido.Comunicacion.Paquete_de_datos;
import pedido.Logica.Cliente;
import pedido.SQlite.DatabaseHandler;
import youtube.demo.youtubedemo.R;

public class LoginActivity extends AppCompatActivity implements OnClickListener{

    // UI references.
    private EditText password, user;
    private View mProgressView;
    private Button sigIn;
    private DatabaseHandler sqlite;
    private Cliente lu;
    private Comunicacion_via_Socket comunicacion;
    ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Set up the login form.
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        mLoading = (ProgressBar) findViewById(R.id.login_progress);

        sigIn = (Button) findViewById(R.id.email_sign_in_button);
        sigIn.setOnClickListener(this);
        mProgressView = findViewById(R.id.login_progress);

        //abro la base de datos
        sqlite = new DatabaseHandler(this);
        sqlite.Abrir();

        //obtengo los datos de conexion
        comunicacion = new Comunicacion_via_Socket(sqlite.getIp(),sqlite.getPuerto(), sqlite);

        if (existeUser()) {
            Conectar(Paquete_de_datos.Accion.Login);
        }
    }


    private Boolean existeUser(){
        //Primero verificar si existe un usuario en la base de datos
        lu = new Cliente();
        if (sqlite.existeUser(lu)){
            password.setText(lu.getPass());
            user.setText(lu.getUser());
            return true;
        }else{
            return false;
        }
    }

    public void Verificar(){
         if ((isUserValid(user.getText().toString())) && (isPasswordValid(password.getText().toString()))) {
             lu = new Cliente();
             lu.setPass(password.getText().toString());
             lu.setUser(user.getText().toString());
             Conectar(Paquete_de_datos.Accion.NuevoUsuario);
         }else{
             //cartel informativo
             Toast.makeText(getApplicationContext(), "User y Pass con formato invalido", Toast.LENGTH_SHORT).show();
             mLoading.setVisibility(View.GONE);
         }
    }

    public void Conectar(Paquete_de_datos.Accion a){
        //primero conecto y y luego efectuo la operacion
        if (comunicacion.ConnectTimeout()) {
            if (comunicacion.Enviar_peticion_Login(lu, a)) {
                Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                mLoading.setVisibility(View.GONE);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();
            }else{
                Toast.makeText(getApplicationContext(), "El User o Pass son incorrectos", Toast.LENGTH_SHORT).show();
                mLoading.setVisibility(View.GONE);
            }
        } else {
            //cartel informativo
            Toast.makeText(getApplicationContext(), "En este momento no hay conexión de datos disponibles. Intentelo cuando tenga una conexión estable de datos", Toast.LENGTH_SHORT).show();
            mLoading.setVisibility(View.GONE);
        }
    }


    private boolean isUserValid(String email) {
        return email.length() > 2;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 2;
    }


    @Override
    public void onClick(View view) {
        switch ( view.getId() )
        {
            case R.id.email_sign_in_button:
                mLoading.setVisibility(View.VISIBLE);
                Verificar();
                break;
        }
    }



}

