package pedido.Comunicacion;


import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje_data implements Serializable{
	  
   // private static final long serialVersionUID = 9178463713495654837L;
	
    public int Action;
    public boolean last_msg=false;
    public String texto;
    
   //Numero de cliente y pedido realizado
    public int NroCliente;
    public ArrayList<CantidadPedida>  Productos_Pedidos = new ArrayList<CantidadPedida>();
    
}
