package pedido.SQlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import pedido.Logica.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

	private SQLiteDatabase db;

	private static final int DATABASE_VERSION = 1;
	private static String DB_PATH = "/data/data/com.example.pedidomobil/databases/";
	private static final String DATABASE_NAME = "Basedatos.db";
	private final Context myContext;
	/*
	 * *********************************  DEFINICION DE TABLAS   ***************************************
	 * */
	
	// Definici�n tabla cliente
	private static final String Table_Cliente = "Cliente";
	public static final class Clientes implements BaseColumns {
		private Clientes() {
		}
		private static final String id_cliente = "Cod";
		private static final String Nomyape_cliente = "Nombre";
		private static final String Domicilio_cliente = "Domicilio";
		private static final String Localidad_cliente = "Localidad";
		private static final String CUIT_cliente = "CUIT";
		private static final String IVA_cliente = "IVA";
		private static final String Telefono_cliente = "Telefono";
		private static final String Saldo_cliente = "Saldo";
	}

	// Definicion tabla Producto
	private static final String Table_Producto = "Producto";
	public static final class Productos implements BaseColumns {
		private Productos() {
		}

		private static final String cod_producto = "cod";
		private static final String denominacion_producto = "denominacion";
		private static final String precio_producto = "precio";
		private static final String stock_producto = "stock";
	}

	// Definicion tabla Pedido
	private static final String Table_Pedido = "Pedido";

	public static final class Pedidos implements BaseColumns {
		private Pedidos() {
		}
		private static final String id_pedido = "id";
		private static final String comentarios_pedido = "comentario";
		private static final String cliente_pedido = "cliente";
		private static final String estado_pedido = "estado";// creado y no
																// enviado,
																// enviado
		private static final String fecha_pedido = "fecha";
	}

	// Tabla Cantidades pedidas en cada pedido
	private static final String Table_cantidad_pedida = "Cantidad_pedida";

	public static final class Cantidades_pedidas implements BaseColumns {
		private Cantidades_pedidas() {
		}
		private static final String id_cp = "id";
		private static final String producto_cp = "producto";
		private static final String pedido_cp = "pedido";
		private static final String cantidad_cp = "cantidad";
		private static final String nota_cp = "nota"; // nota para agregar a cada item del pedido

	}
	
	// Definici�n tabla conexion
	private static final String Table_Conexion = "Conexion";

		public static final class Conexiones implements BaseColumns {
			private Conexiones() {
			}
			private static final String dir_ip = "ip";
			private static final String puerto = "puerto";
		}

	// Definici�n tabla zonas
		private static final String Table_Zonas = "Zona";

		public static final class Zonas implements BaseColumns {
			private Zonas() {
			}
			private static final String nombre_zona = "nombreZona";
			}

	// Definici�n tabla zonas
		private static final String Table_Grupos = "Grupo";

		public static final class Grupos implements BaseColumns {
			private Grupos() {
			}
			private static final String nombre_grupo = "nombreGrupo";
			}
		
		
    /*
	 * ********************************  CREACION DE TABLAS   ***************************************
	* */
		
	// Sentencias para la creaci�n de tablas
	public static final String CLIENTE_TABLE_CREATE = "CREATE TABLE "
			+ Table_Cliente + " (" + Clientes.id_cliente
			+ " INTEGER PRIMARY KEY, " + Clientes.Nomyape_cliente
			+ " TEXT NOT NULL);";

	private static final String PRODUCTO_TABLE_CREATE = "CREATE TABLE "
			+ Table_Producto + " (" + Productos.cod_producto
			+ " INTEGER PRIMARY KEY , " + Productos.denominacion_producto
			+ " TEXT NOT NULL UNIQUE, " + Productos.precio_producto
			+ " DOUBLE, " + Productos.stock_producto + " DOUBLE);";

	private static final String PEDIDO_TABLE_CREATE = "CREATE TABLE "
			+ Table_Pedido + " (" + Pedidos.id_pedido
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ Pedidos.comentarios_pedido + " TEXT, " + Pedidos.cliente_pedido
			+ " INTEGER NOT NULL, " + Pedidos.estado_pedido + " TEXT, "
			+ Pedidos.fecha_pedido + " TEXT, " + "FOREIGN KEY ("
			+ Pedidos.cliente_pedido + ") REFERENCES " + Table_Cliente + " ("
			+ Clientes.id_cliente + "));";

	private static final String CANTIDAD_PEDIDA_TABLE_CREATE = "CREATE TABLE "
			+ Table_cantidad_pedida + " (" + Cantidades_pedidas.id_cp + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " 
			+ Cantidades_pedidas.producto_cp	+ " INTEGER, " 
			+ Cantidades_pedidas.pedido_cp + " INTEGER, " 
			+ Cantidades_pedidas.cantidad_cp + " INTEGER NOT NULL, " 
			+ Cantidades_pedidas.nota_cp + " TEXT);";
		
	
	private static final String CONEXION_TABLE_CREATE = "CREATE TABLE "
			+ Table_Conexion + " (" + Conexiones.dir_ip
			+ " TEXT PRIMARY KEY, " + Conexiones.puerto
			+ " TEXT NOT NULL);";
	
	private static final String ZONA_TABLE_CREATE = "CREATE TABLE "
			+ Table_Zonas + " (" + Zonas.nombre_zona
			+ " TEXT PRIMARY KEY);";
	
	private static final String GRUPO_TABLE_CREATE = "CREATE TABLE "
			+ Table_Grupos + " (" + Grupos.nombre_grupo
			+ " TEXT PRIMARY KEY);";
	
	/*
	 * *********************************  CONSTRUCTOR  ***************************************
	 * */
	

	// Constructor de clase
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	// Creacion de base de datos
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(CLIENTE_TABLE_CREATE);
		//db.execSQL(PRODUCTO_TABLE_CREATE);
		//db.execSQL(PEDIDO_TABLE_CREATE);
		//db.execSQL(CANTIDAD_PEDIDA_TABLE_CREATE);
		//db.execSQL(CONEXION_TABLE_CREATE);
		//db.execSQL(ZONA_TABLE_CREATE);
		//db.execSQL(GRUPO_TABLE_CREATE);
		//System.out.println("Las Tablas fueron creadas..");

//		//copio los datos dentro de ella
//		try {
//			createDataBase();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	// actualizacion de base de datos
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXISTS " + Table_Cliente);
//		db.execSQL("DROP TABLE IF EXISTS " + Table_Producto);
//		db.execSQL("DROP TABLE IF EXISTS " + Table_Pedido);
//		db.execSQL("DROP TABLE IF EXISTS " + Table_cantidad_pedida);
//		db.execSQL("DROP TABLE IF EXISTS " + Table_Conexion);
//		db.execSQL("DROP TABLE IF EXISTS " + Table_Zonas);
//		db.execSQL("DROP TABLE IF EXISTS " + Table_Grupos);
//		System.out.println("Las Tablas fueron borradas..");
//		
//		//creo las tablas 
//		onCreate(db);
		
		
	}
	
	
	/*
	 * ********************************* Borrado de datos de tablas   ***************************************
	 * */	
	public void deleteAllDataProduct() {
		db.delete(Table_Producto, null, null);
	}
	
	public void deleteAllDateCliente(){
		db.delete(Table_Cliente, null, null);
	}

	public void deleteAllDataPedido() {
		db.delete(Table_Pedido, null, null);
	}

	public void deleteAllDataCantPedido() {
		db.delete(Table_cantidad_pedida, null, null);
	}
	
	public void deleteAllDataConexion() {
		db.delete(Table_Conexion, null, null);
	}
	
	public void deleteAllDataZona() {
		db.delete(Table_Zonas, null, null);
	}
	public void deleteAllDataGrupo() {
		db.delete(Table_Grupos, null, null);
	}

	/*
	 * ********************************* Abrir y cerrar base de datos ***************************************
	 * */
	
	 
	public void Abrir() throws SQLException{
		 
		//Abre la base de datos
		try {
		     createDataBase();
		} catch (IOException e) {
		     throw new Error("Ha sido imposible crear la Base de Datos");
		}
		 
		String myPath = DB_PATH + DATABASE_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
		 
		}
	
	
    public synchronized void Cerrar(){
 
        if (db != null)
            db.close();
 
        super.close();
    }
    
    
    public void createDataBase() throws IOException{
    	 
    	boolean dbExist = checkDataBase();
    	 
    	if(dbExist){
    	//do nothing - database already exist
    	}else{
    	 
	    	//By calling this method and empty database will be created into the default system path
	    	//of your application so we are gonna be able to overwrite that database with our database.
	    	this.getReadableDatabase();
	    	 
	    	try {
	    	 
	    		copyDataBase();
	    	 
	    	} catch (IOException e) {
	    	 
	    		throw new Error("Error copying database");
	    	 
	    	}
    	}
    	 
    }
    
    private boolean checkDataBase() {
    	 
        SQLiteDatabase checkDB = null;
 
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
        } catch (SQLiteException e) {
            // Base de datos no creada todavia
        }
 
        if (checkDB != null) {
 
            checkDB.close();
        }
 
        return checkDB != null ? true : false;
 
    }
    
    private void copyDataBase() throws IOException{
    	 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
    	 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DATABASE_NAME;
    	 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    	 
    	}
    
	
	/*
	 * ********************************* Alta de registros en tablas ***************************************
	 * */

	public void Add_pedido(Pedido p) {
		Abrir();
			//cada pedido se agrega con el id autoincremental, por lo que no hay problema de repetidos
			ContentValues values = new ContentValues();
			values.put(Pedidos.comentarios_pedido, p.getComentario());
			values.put(Pedidos.cliente_pedido, p.getCliente().getCod());
			values.put(Pedidos.estado_pedido, p.getEstado());
			values.put(Pedidos.fecha_pedido, p.getFecha());
			db.insert(Table_Pedido, null, values);	
			
			//Agrego los items de este pedido
			Add_items_pedido(p);
		
		Cerrar();
	}
	
	public void Add_items_pedido(Pedido p){
		//pasado un pedido como parametro , agrega a la base de datos todos los items del pedido
		Abrir();
		
		//agrego a la tabla de cantidades_pedidas, las cantidades del pedido pasado como parametro
		Iterator i = p.getProductosPedidos().iterator();
		while (i.hasNext()){
			Item item = (Item)i.next();
			this.Add_Item(item);
		}
		
		Cerrar();
	}
	
	public void Cambiar_estado_pedido(Pedido p){
		Abrir();
		
		String valor = "Creado y no enviado";
		
		ContentValues values = new ContentValues();
		values.put(Pedidos.estado_pedido, "Creado y no enviado");
		//db.update(Table_Pedido, values, Pedidos.id_pedido + "=?",	new String[]{String.valueOf(p.getId())});
		db.update(Table_Pedido, values, Pedidos.id_pedido + "=" + p.getId() , null);
		//db.execSQL("UPDATE " + Table_Pedido + " SET "+ Pedidos.estado_pedido + " = "+ valor + " WHERE " + Pedidos.id_pedido+"="+p.getId());
		
		Cerrar();
	}

	public boolean Add_producto(Producto p) {
		Abrir();

		if (getProducto(p.getCod()) == null) { //no existe el producto en la base de datos
			ContentValues values = new ContentValues();
			values.put(Productos.cod_producto, p.getCod());
			values.put(Productos.precio_producto, p.getPrecio());
			values.put(Productos.denominacion_producto, p.getDenominacion());
			values.put(Productos.stock_producto, p.getStock());
			db.insert(Table_Producto, null, values);
			Cerrar();
			return true;
		}else{
			Update_Producto(p);  //en caso que el producto exista se modifica para tener los datos sincronizados de manera correcta.
			Cerrar();
			return false;
		}
	}

	public boolean Add_cliente(Cliente c){
		Abrir();
		
		if (getCliente(c.getCod())== null){ //si el cliente no existe 
			ContentValues values = new ContentValues();
			values.put(Clientes.id_cliente, c.getCod());
			values.put(Clientes.Nomyape_cliente, c.getNombre());
			values.put(Clientes.Saldo_cliente, c.getSaldo());
			values.put(Clientes.Domicilio_cliente, c.getDomicilio());
			values.put(Clientes.Localidad_cliente, c.getLocalidad());
			values.put(Clientes.CUIT_cliente, c.getCUIT());
			values.put(Clientes.IVA_cliente, c.getIVA());
			values.put(Clientes.Telefono_cliente, c.getTelefono());
			
			db.insert(Table_Cliente, null, values);
			Cerrar();
			return true;   
		}else{
			Update_cliente(c);  //en caso que exista el cliente se modifica por si algun dato cambio, esto sirve para sincronizar todos los 
			//clientes.
			Cerrar();
			return true;  //el cliente ya existe
		}
	}

	public void Add_Item(Item cp) {
		Abrir();

		ContentValues values = new ContentValues();
			values.put(Cantidades_pedidas.producto_cp, cp.getIdProducto());
			values.put(Cantidades_pedidas.pedido_cp, cp.getIdPedido());
			values.put(Cantidades_pedidas.cantidad_cp, cp.getCantidad());
			values.put(Cantidades_pedidas.nota_cp , cp.getNota());
			db.insert(Table_cantidad_pedida, null, values);
			Cerrar();
		
	}
	
	public void Add_conexion(String ip, String puerto) {
		Abrir();
		
		String selectQuery = "SELECT  * FROM " + Table_Conexion;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Si la tabla tiene algo, lo borro asi ingreso nuevamente una conexion nueva
		// Esta tabla solo contendra un unico registro
		if (cursor.moveToFirst()) {
			deleteAllDataConexion();
			
		}
		ContentValues values = new ContentValues();
		values.put(Conexiones.dir_ip, ip);
		values.put(Conexiones.puerto, puerto);
		db.insert(Table_Conexion, null, values);
		
		Cerrar();
	}

	public void Add_Zona(String zona) {
		Abrir();

		ContentValues values = new ContentValues();
		values.put(Zonas.nombre_zona, zona);
		db.insert(Table_Zonas, null, values);

		Cerrar();
	}
	public void Add_Grupo(String grupo) {
		Abrir();

		ContentValues values = new ContentValues();
		values.put(Grupos.nombre_grupo, grupo);
		db.insert(Table_Grupos, null, values);

		Cerrar();
	}
		
	//*********************************************** UPDATE  *********************************************************
	
	public void Update_cliente(Cliente c){
		//Actualiza el nombre del cliente
		Abrir();

		//Establecemos los campos-valores a actualizar
		ContentValues valores = new ContentValues();
		valores.put(Clientes.Nomyape_cliente ,c.getNombre());
		valores.put(Clientes.Domicilio_cliente ,c.getDomicilio());
		valores.put(Clientes.Localidad_cliente ,c.getLocalidad());
		valores.put(Clientes.Telefono_cliente ,c.getTelefono());
		valores.put(Clientes.CUIT_cliente ,c.getCUIT());
		valores.put(Clientes.IVA_cliente ,c.getIVA());
		 
		//Actualizamos el registro en la base de datos
		db.update(Table_Cliente, valores, Clientes.id_cliente + "=" + c.getCod(), null);
		
		Cerrar();
	}
	
	public void Update_Producto(Producto p){
		//Actualiza el saldo del cliente con el codigo pasado por parametro
		Abrir();

		//Establecemos los campos-valores a actualizar
		ContentValues valores = new ContentValues();
		valores.put(Productos.denominacion_producto,p.getDenominacion());
		valores.put(Productos.precio_producto,p.getPrecio());
		valores.put(Productos.stock_producto, p.getStock());
		 
		//Actualizamos el registro en la base de datos
		db.update(Table_Producto, valores, Productos.cod_producto + "=" + p.getCod(), null);
		
		Cerrar();
	}
	
	public void Update_saldo_cliente(int codCliente, Double saldo){
		//Actualiza el saldo del cliente con el codigo pasado por parametro
		Abrir();

		//Establecemos los campos-valores a actualizar
		ContentValues valores = new ContentValues();
		valores.put("Saldo",saldo);
		 
		//Actualizamos el registro en la base de datos
		db.update(Table_Cliente, valores, "Cod=" + codCliente, null);
		
		Cerrar();
	}
	
	public void Update_estado_pedido(String estado_pedido, int p){
		//Actualiza el saldo del cliente con el codigo pasado por parametro
		Abrir();

		//Establecemos los campos-valores a actualizar
		ContentValues values = new ContentValues();
		values.put(Pedidos.estado_pedido, estado_pedido);
		
		//Actualizamos el registro en la base de datos
		db.update(Table_Pedido, values, Pedidos.id_pedido + "=" + p, null);
		
		Cerrar();
	}
	
	//******************************************  DELETE *****************************************************

	public void deleteProducto_de_pedido(int idPedido,int producto){
        Abrir();
		
		//Eliminar un registro
		db.delete(Table_cantidad_pedida,"pedido=? and producto=?",new String[]{ Integer.toString(idPedido),Integer.toString(producto)});
		Cerrar();
	}
	
	
	public void deletePedido(Pedido p){
		Abrir();
		
		db.delete(Table_Pedido, Pedidos.id_pedido + "=" + p.getId(), null);
		
		Cerrar();
	}
	
	public void delete_pedidos_no_enviados(){
		//me borra de la base de datos los pedidos no enviados
		 Abrir();
			
	     db.delete(Table_Pedido, Pedidos.estado_pedido + "=" + "Creado y no enviado", null);
			
		 Cerrar();
		
	}
	
	/*
	 * ***************************************** GET **********************************************************
	 * */
	// Getting single Producto y cliente
	public Cliente getCliente(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Cliente, new String[] {
				Clientes.id_cliente,
				Clientes.Nomyape_cliente, Clientes.Saldo_cliente }, Clientes.id_cliente + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor.moveToFirst()){
			Cliente c = new Cliente(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getDouble(2));
			return c;
		}else
			return null;  //no encontro el cliente buscado.
	}

	public Cliente getCliente(String nombre) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Cliente, new String[] {Clientes.id_cliente,Clientes.Nomyape_cliente,Clientes.Saldo_cliente }, Clientes.Nomyape_cliente + "=?",new String[] { nombre }, null, null, null, null);

		if (cursor.moveToFirst()){
    		Cliente c = new Cliente(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getDouble(2));
     		return c;
		}else{
			return null;  //no encontro el cliente buscado.
		}

	}
	
	public Cliente getClienteCompleto(String nombre) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Cliente, new String[] {Clientes.id_cliente,Clientes.Nomyape_cliente,Clientes.Domicilio_cliente, Clientes.Localidad_cliente,Clientes.Telefono_cliente, Clientes.CUIT_cliente, Clientes.IVA_cliente, Clientes.Saldo_cliente }, Clientes.Nomyape_cliente + "=?",new String[] { nombre }, null, null, null, null);

		if (cursor.moveToFirst()){
    		Cliente c = new Cliente(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getDouble(7));
     		return c;
		}else{
			return null;  //no encontro el cliente buscado.
		}

	}
	
	public int getNroNuevoCliente(){
		Cursor c = db.rawQuery("SELECT MAX("+ Clientes.id_cliente +") FROM " + Table_Cliente, null);
		c.moveToFirst();
		int id = c.getInt(0) + 1;
		return id;

	}

	public Producto getProducto(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Producto, new String[] {
				Productos.denominacion_producto,Productos.precio_producto, Productos.stock_producto},
				Productos.cod_producto + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor.moveToFirst()){
			boolean venderxBulto = cursor.getInt(3)>0;
			boolean stockPorKilo = cursor.getInt(5)>0;
			boolean facturarporkilo = cursor.getInt(7)>0;
    		Producto p = new Producto(id,cursor.getString(0), Double.parseDouble(cursor.getString(1)),cursor.getDouble(2));
    		return p;
		}else{
			return null;  //no encontro el producto buscado.
		}
	}
	
	public int getNroPedidoActual(){
		Cursor c = db.rawQuery("SELECT MAX("+ Pedidos.id_pedido +") FROM " + Table_Pedido, null);
		c.moveToFirst();
		int id = c.getInt(0) + 1;
		return id;
	}

	public String getNombreProducto(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query(Table_Producto, new String[] {
		                 Productos.cod_producto, Productos.stock_producto,Productos.precio_producto, Productos.denominacion_producto },
				         Productos.cod_producto + "=?",new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor.moveToFirst()){
			return cursor.getString(3);
		}else{
			return null;
		}
	}

	public String Productos_Pedidos(int pedido){
		String cp = "";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_cantidad_pedida, new String[] { Cantidades_pedidas.producto_cp,Cantidades_pedidas.cantidad_cp, 
				Cantidades_pedidas.nota_cp}, Cantidades_pedidas.pedido_cp + "=?",
				new String[] {String.valueOf(pedido)  }, null, null, null, null);

       // looping through all rows and adding to list
    	if (cursor.moveToFirst()) {
			do {
				cp += "Producto: " + getNombreProducto(cursor.getInt(0))  + "\r\n";
				cp += "Cantidad: " + cursor.getString(1) + "\r\n";
				//si tiene nota la agrego		
				if (cursor.getString(2) != "")
					cp += "Nota: " + cursor.getString(2) + "\r\n";

			} while (cursor.moveToNext());
			return cp;
		}else{
			return null;     //no existe el pedido pasado como parametro
		}
		
	}
	
	public Pedido getPedido(int idPedido){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Pedido, new String[] {
				Pedidos.id_pedido,Pedidos.fecha_pedido, Pedidos.estado_pedido ,Pedidos.comentarios_pedido,Pedidos.cliente_pedido},
				Pedidos.id_pedido + "=?",
				new String[] { String.valueOf(idPedido) }, null, null, null, null);

		if (cursor.moveToFirst()){
			//int id,String comentario, Cliente cliente, String estado, String fecha
    		Pedido p = new Pedido(idPedido,cursor.getString(3),this.getCliente(cursor.getInt(4)),cursor.getString(2),cursor.getString(1) );
    		return p;
		}else{
			return null;  //no encontro el producto buscado.
		}
	}
	
	public List<String> getAllItems() {
		//Esto hay que acomodarlo, hay que pasarle el pedido
		String item = "";
		
		List<String> ListaProductos = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + Table_cantidad_pedida;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				item += "Producto: [" + cursor.getInt(0) + "]\r\n";
				item += "Pedido: " + cursor.getString(2) + "\r\n";
				item += "Cantidad: " + cursor.getString(1) + "\r\n";
				item += "Nota: " + cursor.getString(4) + "\r\n";
				ListaProductos.add(item);
				item = "";
			} while (cursor.moveToNext());
		}
		return ListaProductos;
	}
	
	/*public List<Item> getAllItemsPedido(int idPedido) {

		List<Item> ListaProductos = new ArrayList<Item>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_cantidad_pedida, new String[] { Cantidades_pedidas.producto_cp,Cantidades_pedidas.cantidad_cp,Cantidades_pedidas.nota_cp}, Cantidades_pedidas.pedido_cp + "=?",
				new String[] {String.valueOf(idPedido)  }, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				//Producto producto, int pedido, double cantidad, boolean unitBulto, String nota, double descuento
				boolean valueUnit = cursor.getInt(2)>0;
				Item item = new Item(this.getProducto(cursor.getInt(0)),idPedido,cursor.getDouble(1),valueUnit,cursor.getString(3),cursor.getDouble(4));
				ListaProductos.add(item);
			} while (cursor.moveToNext());
		}
		return ListaProductos;
	}*/
	
	/**
	 * Dado un Cursor con los registros de la base de datos, da formato y
	 * retorna resultado
	 * 
	 * @return ArrayList<String>
	 * */
	public ArrayList<Pedido> getLista_pedidos_no_enviados(){
		String selectQuery = "SELECT  * FROM " + Table_Pedido + " where estado = 'Creado y no enviado'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		ArrayList<Pedido> listData = new ArrayList<Pedido>();
		
		if (cursor.moveToFirst()) {
			do {
				Cliente c = this.getCliente(cursor.getInt(2));
				Pedido p = new Pedido(cursor.getInt(0),cursor.getString(1), c, cursor.getString(3), cursor.getString(4));
				listData.add(p);

			} while (cursor.moveToNext());
		}
		return listData;
	}
	
	public ArrayList<Producto> getLista_productos_lista_precio(String text){
		String selectQuery;
		
		//if (text.length() != 0){
		//	text = "%" + text + "%";
		//}
		
		if (text.equals("")){
			selectQuery = "SELECT denominacion,precio FROM " + Table_Producto;
			SQLiteDatabase db = this.getWritableDatabase();	
		}else{
			text = "'%" + text + "%'";
			
			selectQuery = "SELECT denominacion,precio FROM " + Table_Producto + " where denominacion like " + text;

			SQLiteDatabase db = this.getWritableDatabase();
		}
		
		
		
		Cursor cursor = db.rawQuery(selectQuery,null);
		
		ArrayList<Producto> listData = new ArrayList<Producto>();
		
		if (cursor.moveToFirst()) {
			do {    //int cod, String denominacion, double precio, double stock
				Producto p = new Producto(0,cursor.getString(0),cursor.getDouble(1),0d);
				listData.add(p);

			} while (cursor.moveToNext());
		}
		return listData;
	}
	
	public ArrayList<Pedido> getLista_pedidos_estado_Armando(){
		String selectQuery = "SELECT  * FROM " + Table_Pedido + " where estado = 'Armando'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		ArrayList<Pedido> listData = new ArrayList<Pedido>();
		
		if (cursor.moveToFirst()) {
			do {
				Cliente c = this.getCliente(cursor.getInt(2));
				Pedido p = new Pedido(cursor.getInt(0),cursor.getString(1), c, cursor.getString(3), cursor.getString(4));
				listData.add(p);

			} while (cursor.moveToNext());
		}
		return listData;
	}
	

	public ArrayList<Pedido> getLista_pedidos_enviados(){
		String selectQuery = "SELECT  * FROM " + Table_Pedido + " where estado = 'Creado y enviado' order by id DESC";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		ArrayList<Pedido> listData = new ArrayList<Pedido>();
		
		if (cursor.moveToFirst()) {
			do {
				Cliente c = this.getCliente(cursor.getInt(2));
				Pedido p = new Pedido(cursor.getInt(0),cursor.getString(1), c, cursor.getString(3), cursor.getString(4));
				listData.add(p);

			} while (cursor.moveToNext());
		}
		return listData;
	}
	
	public ArrayList<String> getFormato_Pedidos_simple() {
		String selectQuery = "SELECT  * FROM " + Table_Pedido;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		ArrayList<String> listData = new ArrayList<String>();
		String item = "";
		if (cursor.moveToFirst()) {
			do {
				item += "ID: [" + cursor.getInt(0) + "]\r\n";
				item += "Cliente: " + cursor.getString(2) + "\r\n";
				item += "Fecha: " + cursor.getString(4) + "\r\n";
				item += "Comentarios: " + cursor.getString(1) + "\r\n";
				item += "Estado: " + cursor.getString(3) + "";
				listData.add(item);
				item = "";

			} while (cursor.moveToNext());
		}
		return listData;
	}
	
	public ArrayList<String> getFormato_clientes_Pedidos() {
		String selectQuery = "SELECT cliente FROM " + Table_Pedido;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		ArrayList<String> listData = new ArrayList<String>();
		String item = "";
		if (cursor.moveToFirst()) {
			do {
				item += "Cliente: " + cursor.getString(2) + "\r\n";
				listData.add(item);
				item = "";

			} while (cursor.moveToNext());
		}
		return listData;
	}

	public ArrayList<String> getFormato_Pedidos_Completo() {
		String selectQuery = "SELECT  * FROM " + Table_Pedido;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		ArrayList<String> listData = new ArrayList<String>();
		String item = "";
		if (cursor.moveToFirst()) {
			do {
				item += "ID: [" + cursor.getInt(0) + "]\r\n";
				item += "Cliente: " + cursor.getString(2) + "\r\n";
				item += "Fecha: " + cursor.getString(4) + "\r\n";
				item += "Comentarios: " + cursor.getString(1) + "\r\n";
				item += "Estado: " + cursor.getString(3) + "\r\n";
				item += Productos_Pedidos(cursor.getInt(0));
				listData.add(item);
				item = "";

			} while (cursor.moveToNext());
		}
		return listData;
	}
	
	public String[] getClientes_para_control_autocomplete(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		String selectQuery = "SELECT " +  Clientes.Nomyape_cliente  + " FROM " + Table_Cliente;
		Cursor c = db.rawQuery(selectQuery, null);
		        
	    String contenido = new String();
    	if (c.moveToFirst()){
	    	do{
	    		contenido += c.getString(0)+",";
	    	}while (c.moveToNext());
    	}
    	//se divide el string asi lo puede entender el autocomplete
    	final String[] datos = contenido.split(",");
    	
    	return datos;
	}
	
	public String[] getProdcutos_para_control_autocomplete(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		String selectQuery = "SELECT " +  Productos.denominacion_producto  + " FROM " + Table_Producto;
		Cursor c = db.rawQuery(selectQuery, null);
		        
	    String contenido = new String();
    	if (c.moveToFirst()){
	    	do{
	    		contenido += c.getString(0)+",";
	    	}while (c.moveToNext());
    	}
    	//se divide el string asi lo puede entender el autocomplete
    	final String[] datos = contenido.split(",");
    	
    	return datos;
	}
	
	public String getPuerto(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Conexion, new String[] {Conexiones.puerto},null, null, null, null, null, null);

       // looping through all rows and adding to list
    	if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}else{
			return null;     
		}
	}
	
	
	public String getIp(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_Conexion, new String[] {Conexiones.dir_ip},null, null, null, null, null, null);

       // looping through all rows and adding to list
    	if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}else{
			return null;     
		}
	}

}