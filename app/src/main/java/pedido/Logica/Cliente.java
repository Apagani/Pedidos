package pedido.Logica;

import java.io.Serializable;

public class Cliente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8512763333339579121L;
	private int Cod;
    private String Nombre;
    private String Domicilio;
    private String CUIT;
    private String IVA;
    private String Localidad;
    private String Telefono;
    private Double Saldo;


    //Enumerador que determina que accion se debe hacer cuando se sincriniza el cliente en el movil
  	public enum Accion_sincronizacion {modificar, agregar, borrar,all};
  	public Accion_sincronizacion accionS;
  	
  	public Cliente(int codi){
  		//este constructor es utilizado unicamente para transitir al smartphone los clientes eliminados
  		this.Cod = codi;
  	}

    public Cliente(int Cod, String Nombre, double Saldo){
		this.Cod = Cod;
		this.Nombre = Nombre;
		this.Saldo = Saldo;
    }
    
    public Cliente(int Cod, String Nombre, String Dir,String localidad,String Telefono,String Iva,String CUIT, double Saldo){
		this.Cod = Cod;
		this.Nombre = Nombre;
		this.Saldo = Saldo;
		this.Domicilio = Dir;
		this.Telefono = Telefono;
		this.IVA = Iva;
		this.CUIT = CUIT;
		this.Localidad = localidad;
    }

	public int getCod() {
		return Cod;
	}

	public void setCod(int cod) {
		Cod = cod;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getDomicilio() {
		return Domicilio;
	}

	public void setDomicilio(String domicilio) {
		Domicilio = domicilio;
	}

	public String getCUIT() {
		return CUIT;
	}

	public void setCUIT(String cUIT) {
		CUIT = cUIT;
	}

	public String getIVA() {
		return IVA;
	}

	public void setIVA(String iVA) {
		IVA = iVA;
	}

	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public Double getSaldo() {
		return Saldo;
	}

	public void setSaldo(Double saldo) {
		Saldo = saldo;
	}


}

