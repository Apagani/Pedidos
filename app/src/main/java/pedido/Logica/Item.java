package pedido.Logica;

import java.io.Serializable;


public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6074728729678747707L;
	private Producto producto;
	private int idPedido;
	private Double cantidad;
	private String nota;   //nota para agregar a cada item del pedido

	public Item(Producto producto, int pedido, double cantidad, String nota){
		this.producto = producto;
		this.idPedido = pedido;
		this.cantidad = cantidad;
		this.nota= nota;
	}

	public int getIdProducto() {
		return producto.getCod();
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getIdPedido() {
		return idPedido;
	}


	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}


	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getMontoItem(){
		double precio = 0;
		precio = producto.getPrecio(); //precio del producto;

		String monto_redondeado = String.format("%.2f", (cantidad * precio));

		return monto_redondeado;
	}
	
	public double getMontoItemDouble(){
		double precio = 0;
		precio = producto.getPrecio(); //precio del producto;
		String monto_redondeado = String.format("%.2f", (cantidad * precio));
		
		return Double.parseDouble(monto_redondeado);
	}
	
	public String ItemCompletoString(){
		String items = "";
		
		items += "Prod: " + this.producto.getDenominacion() +"\r\n";
		items += "Cant: " + this.cantidad;
		items += "   $:" + this.getMontoItem() + "\r\n";
		if (this.nota != ""){
			items += "           " +"\r\n"+ this.nota;
		}
		return items;
	}
	
	public String ItemSimple(){
		 String items = "";
         String monto = this.getMontoItem();
		
        items += this.cantidad + "  ";
        items += this.getProducto().getPrecio() +"  "; 
        items += "   " + this.producto.getDenominacion();
			
		return items;
	}
	
	@Override
	public String toString(){
		return ItemSimple();
	}
	
	
	
}
