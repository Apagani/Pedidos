package pedido.Comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import pedido.Logica.*;
import android.app.Activity;
import android.util.Log;

import pedido.SQlite.DatabaseHandler;
import pedido.Comunicacion.Paquete_de_datos.Accion;



/* cliente*/
public class Comunicacion_via_Socket{

	private String IP;
	private int Puerto;
	private boolean connected = false;
	private Activity actividad;
	private DatabaseHandler sqlite;
	
	Socket miCliente;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public Comunicacion_via_Socket(String ip, int puerto){
		IP = ip;
		Puerto = puerto;
	}
	
	public Comunicacion_via_Socket(String ip, int puerto, DatabaseHandler bd){
		IP = ip;
		Puerto = puerto;
		sqlite = bd;
	}

	//Conectamos
	public boolean Connect() {
		//creamos sockets con la ip y el puerto
		try {
			miCliente = new Socket(IP,Puerto);
			//si nos conectamos
			if (miCliente.isConnected() == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			//Si hubo algun error mostrmos error
		    System.out.println("Error connect()" + e.getMessage());
			return false;
		}
	}
	
	public boolean ConnectTimeout() {
		//creamos sockets con la ip y el puerto
		try {
			//creo una conecxion via socket con un tiempo de espera seteado a 4 segundos
			miCliente = new Socket();
			miCliente.connect(new InetSocketAddress(IP, Puerto), 4000);
			//si nos conectamos
			if (miCliente.isConnected() == true) {
				return true;
			} else {
				return false;
			}
		
		}
		catch (UnknownHostException e) {
			//Si hubo algun error mostrmos error
		    System.out.println("Error connect()" + e.getMessage());
		    return false;
		}
		catch (IOException ioe) {
			//Si hubo algun error mostrmos error
		    System.out.println("Error connect()" + ioe.getMessage());
		    return false;
		}
		
	}

	//Metodo de desconexion
	public void Disconnect() {
		try {
			//Prepramos mensaje de desconexion
			Paquete_de_datos msgFin = new Paquete_de_datos();
			//msgFin.Operacion = Accion.Fin;
			
			//avisamos al server que cierre el canal
			boolean val_acc = Snd_Msg(msgFin);

			if (!val_acc) {//hubo un error
					//System.out.println("Disconnect() -> ERROR!");
					//cartel informativo
					
			} else {
				//ok nos desconectamos, cerramos socket	
				miCliente.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*Metodo para enviar mensaje por socket
	 *recibe como parmetro un objeto Mensaje_data
	 *retorna boolean segun si se pudo establecer o no la conexion
	 */
	public boolean Snd_Msg(Paquete_de_datos msg) {
		try {
			//Accedo a flujo de salida
			oos = new ObjectOutputStream(miCliente.getOutputStream());
			if (miCliente.isConnected())// si la conexion continua
			{
				Paquete_de_datos p = new Paquete_de_datos();
				p = msg;
				//Envio mensaje por flujo
				oos.writeObject(p);
				//envio ok
				return true;
			} else {//en caso de que no halla conexion al enviar el msg
				return false;
			}

		} catch (IOException e) {// hubo algun error
			Log.e("Snd_Msg() ERROR -> ", "" + e);
			return false;
		}
	}
	
	public void Recibir_datos_del_servidor(){
		try {
			// Manejamos flujo de Entrada
			ObjectInputStream ois = new ObjectInputStream(miCliente.getInputStream());
			Object aux = ois.readObject();// leemos objeto
			
			// si el objeto es una instancia de Pedido, Cliente, Producto
			if (aux instanceof Paquete_de_datos) {
					Paquete_de_datos p = (Paquete_de_datos)aux;	
					Almacenar_datos_recibidos_en_base_local(p);
		     }
			
	   } catch (Exception e) {
		    e.printStackTrace();
	   }
	}
	
	
	/*public Cartelera Enviar_peticion_Cartelera_actual(){
		Enviar_peticion_de_datos_al_servidor(Accion.Cartelera);
		
		//Ahora me quedo esperando al servidor que me envie los datos
		try {
			// Manejamos flujo de Entrada
			ObjectInputStream ois = new ObjectInputStream(miCliente.getInputStream());
			Object aux = ois.readObject();// leemos objeto
			
			// si el objeto es una instancia de Pedido, Cliente, Producto
			if (aux instanceof Paquete_de_datos) {
					Paquete_de_datos p = (Paquete_de_datos)aux;
					return p.Get_cartelera();
		     }else{
		    	 return null;
		     }
			
	   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
	   }	
	}
	*/
	
	public ArrayList<Producto> Enviar_peticion_Productos(int cliente){
		Enviar_peticion_de_datos_al_servidor(Accion.ListaProductos, cliente);
		
		//Ahora me quedo esperando al servidor que me envie los datos
		try {
			// Manejamos flujo de Entrada
			ObjectInputStream ois = new ObjectInputStream(miCliente.getInputStream());
			Object aux = ois.readObject();// leemos objeto
			
			// si el objeto es una instancia de Pedido, Cliente, Producto
			if (aux instanceof Paquete_de_datos) {
					Paquete_de_datos p = (Paquete_de_datos)aux;
					return p.getLista_productos();
		     }else{
		    	 return null;
		     }
			
	   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
	   }	
	}
	
/*	public ArrayList<Evento> Enviar_peticion_Eventos(){
		Enviar_peticion_de_datos_al_servidor(Accion.Eventos);
		
		//Ahora me quedo esperando al servidor que me envie los datos
		try {
			// Manejamos flujo de Entrada
			ObjectInputStream ois = new ObjectInputStream(miCliente.getInputStream());
			Object aux = ois.readObject();// leemos objeto
			
			// si el objeto es una instancia de Pedido, Cliente, Producto
			if (aux instanceof Paquete_de_datos) {
					Paquete_de_datos p = (Paquete_de_datos)aux;
					return p.getEventos();
		     }else{
		    	 return null;
		     }
			
	   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
	   }	
	}*/

	
	public void Enviar_peticion_de_datos_al_servidor(Accion accion, int cliente){
		 //envio un paquete de datos al servidor indicandole que me envie datos actualizados
		//stock, cta.cte, clientes, productos.
		 Paquete_de_datos m = new Paquete_de_datos();
		 m.Operacion = accion;
         m.setCliente(new Cliente(cliente));
		 Snd_Msg(m);
	}
	
	public void Almacenar_datos_recibidos_en_base_local(Paquete_de_datos p ){
		
	   /* switch (p.Operacion){
	    case Cartelera:
	    	
	    	break;
	    }*/

	}
	
	 
}