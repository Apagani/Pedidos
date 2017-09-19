package pedido.Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pedido.Logica.*;


public class Pedido implements Serializable{
	/**
	 *	implementa serializable ya que este objeto sera enviado via socket al servidor 
	 */
	private static final long serialVersionUID = 4016880691402289938L;
	private int id;
  	private String comentario;
   	private Cliente cliente;
  	private String estado;// creado y no enviado, enviado
   	private String fecha;
   	private List<Item> ProductosPedidos;
   	private Double Monto;
   	
	public Pedido(String comentario, Cliente cliente, String estado, String fecha ){
		this.comentario = comentario;
		this.cliente = cliente;
		this.estado = estado;
		this.fecha = fecha;
		this.Monto = 0d;
		ProductosPedidos = new ArrayList<Item>();
	}
	
	public Pedido(int id,String comentario, Cliente cliente, String estado, String fecha ){
		this.id = id;
		this.comentario = comentario;
		this.cliente = cliente;
		this.estado = estado;
		this.fecha = fecha;
		this.Monto = 0d;
		ProductosPedidos = new ArrayList<Item>();
	}
	
	public Pedido(){
		//este costructor es solo para indicar al servidor que es el ultimo pedido enviado desde el cel
		//y que de de baja el thread
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}
	
	public Double getMontoPedido(){
		return Monto;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	
	public void AgregarItem_al_pedido(Item item){
		ProductosPedidos.add(item);
	}
	
	public void EliminarItem_al_pedido(int pos){
		ProductosPedidos.remove(pos);
	}
	
	public ArrayList<String> getFormato_productos_pedidos() {
		Iterator<Item> i = ProductosPedidos.iterator();		
		
		ArrayList<String> listData = new ArrayList<String>();
		String item = "";
		while (i.hasNext()){
				Item it = (Item)i.next();
				item += it.toString();
				listData.add(item);
				item = "";
		}

		return listData;
	}
	
	public List<Item> getProductosPedidos(){
		return ProductosPedidos;
	}
	
	public void setProductosPedidos(List<Item> items){
		this.ProductosPedidos = items;
	}
	
}
