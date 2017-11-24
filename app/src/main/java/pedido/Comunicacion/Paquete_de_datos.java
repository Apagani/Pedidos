package pedido.Comunicacion;

import java.io.Serializable;
import java.util.ArrayList;

import pedido.Logica.Cliente;
import pedido.Logica.ItemCtaCte;
import pedido.Logica.Pedido;
import pedido.Logica.Producto;

public class Paquete_de_datos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8484443288197682376L;
	// listas para enviar por medio de los sockets, para cuando se necesite enviar mas de un objeto seguido.
	private Cliente cliente;
	private ArrayList<Producto> Lista_productos;
	private ArrayList<ItemCtaCte> Lista_CtaCte;
	private Pedido pedido;
	private String dolar;
	
	//Enumerador que determina que tipo de data va a enviar o recibir
	public enum Accion {Login, NuevoUsuario,ErrorLogin,ListaProductos, Pedido, PedidoOk, Detalle_CtaCte_Cliente, Fin};
	public Accion Operacion;
	
	
	public Paquete_de_datos(){

	}

	public ArrayList<Producto> getLista_productos() {
		return Lista_productos;
	}

	public void setLista_productos(ArrayList<Producto> lista_productos) {
		Lista_productos = lista_productos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
		Operacion = Accion.Pedido;
	}
	
	public ArrayList<ItemCtaCte> getLista_Detalle_ctacte() {
		return Lista_CtaCte;
	}

	public void setLista_Detalle_CtaCte(ArrayList<ItemCtaCte> lista_ctacte) {
		Lista_CtaCte = lista_ctacte;
	}

	public Accion getOperacion() {
		return Operacion;
	}

	public void setOperacion(Accion operacion) {
		Operacion = operacion;
	}

	public void setDolar(String d){
		this.dolar = d;
	}

	public String getDolar(){
		return this.dolar;
	}
	
}
