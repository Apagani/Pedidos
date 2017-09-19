package pedido.Logica;

import java.io.Serializable;

public class Producto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2384973154455260963L;
	private int cod;
	private String denominacion;
	private double precio;
	private double stock;
	private String link;
	private String descripcion;
	
	
  	public Producto(int cod, String string, double v, double aDouble){
  		//este constructor es solamente para el caso de la sincronizacion de los productos eliminados con los smartphones
  		this.cod = cod;
  	}
  	
  	public Producto(int cod, String denominacion, double precio, double stock, String link, String descripcion){
		this.cod = cod;
		this.denominacion = denominacion;
		this.precio = precio;  //precio para la lista asignada al cliente
		this.stock = stock;
		this.link = link;
		this.descripcion = descripcion;
	}


	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}