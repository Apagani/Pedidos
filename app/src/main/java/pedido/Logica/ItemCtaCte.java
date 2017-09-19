package pedido.Logica;

import java.io.Serializable;

public class ItemCtaCte implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6074728729678747707L;

	private String fecha;
	private Double MontoFact;
	private Double MontoHaber;
	private Double MontoSaldo;
	
	public ItemCtaCte(){
		fecha= "";
		MontoFact = 0d;
		MontoHaber = 0d;
		MontoSaldo = 0d;
	}
	
	public ItemCtaCte(String f, Double Mf,Double MH, Double MS){
		fecha= f;
		MontoFact = Mf;
		MontoHaber = MH;
		MontoSaldo = MS;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Double getMontoFact() {
		return MontoFact;
	}

	public void setMontoFact(Double montoFact) {
		MontoFact = montoFact;
	}

	public Double getMontoHaber() {
		return MontoHaber;
	}

	public void setMontoHaber(Double montoHaber) {
		MontoHaber = montoHaber;
	}

	public Double getMontoSaldo() {
		return MontoSaldo;
	}

	public void setMontoSaldo(Double montoSaldo) {
		MontoSaldo = montoSaldo;
	}
	
}
