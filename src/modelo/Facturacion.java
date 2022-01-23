package modelo;

import java.io.Serializable;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Facturacion extends Conexiondb implements Serializable {

	String consulta;
	transient DefaultTableModel modelo;
	transient Connection cn;
	transient PreparedStatement pst;
	transient DefaultComboBoxModel combo;
	transient DecimalFormat formato;
	transient ResultSet rs;
	int banderin;
	private String[] producto;
	private float stock;
	private String monedaVenta;
	private boolean exito;
	private int caja;
	private Timestamp fecha;
	private String nombreComprador,
		nombres,
		apellidos,
		formapago,
		usuario;
	private int credito;
	private int pago;
	private float iva;
	private float totalCordobas,
		totalDolar;
	private int id;

	public Facturacion() {
		this.cn = null;
		this.pst = null;
		formato = new DecimalFormat("##############0.00");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCaja(int caja) {
		this.caja = caja;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setCredito(int credito) {
		this.credito = credito;
	}

	public void setPago(int pago) {
		this.pago = pago;
	}

	public void setIva(float iva) {
		this.iva = iva;
	}

	public void setTotalCordobas(float total) {
		this.totalCordobas = total;
	}

	public int getCaja() {
		return caja;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public int getCredito() {
		return credito;
	}

	public int getPago() {
		return pago;
	}

	public String getFormapago() {
		return formapago;
	}

	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}

	public float getIva() {
		return iva;
	}

	public float getTotalCordobas() {
		return totalCordobas;
	}

	public float getTotalDolar() {
		return totalDolar;
	}

	public void setTotalDolar(float totalDolar) {
		this.totalDolar = totalDolar;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public boolean isExito() {
		return this.exito;
	}

	public String[] getProducto() {
		return producto;
	}

	public float getStock() {
		return stock;
	}

	public String getMonedaVenta() {
		return monedaVenta;
	}
	
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}

	public String getUsuario(){
		return this.usuario;
	}
//Guardar Factura
	public void GuardarFactura() {
		cn = Conexion();
		this.consulta = "INSERT INTO facturas(caja ,fecha, nombre_comprador, credito, tipoVenta, impuestoISV, totalCordobas, totalDolares,"
			+ " usuario) VALUES(?,?,?,?,?,?,?,?,?)";
		if (credito != 0) {
			try {
				pst = this.cn.prepareStatement(this.consulta);
				pst.setInt(1, caja);
				pst.setTimestamp(2, fecha);
				pst.setString(3, nombreComprador);
				pst.setInt(4, credito);
				pst.setInt(5, pago);
				pst.setFloat(6, iva);
				pst.setFloat(7, totalCordobas);
				pst.setFloat(8, totalDolar);
				pst.setString(9, this.usuario);
				this.banderin = pst.executeUpdate();
				if (banderin > 0) {
					//JOptionPane.showMessageDialog(null, "Factura Guardada Exitosamente", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
				cn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		} else {
			try {
				pst = this.cn.prepareStatement(this.consulta);
				pst.setInt(1, caja);
				pst.setTimestamp(2, fecha);
				pst.setString(3, nombreComprador);
				pst.setNull(4, java.sql.Types.INTEGER);
				pst.setInt(5, pago);
				pst.setFloat(6, iva);
				pst.setFloat(7, totalCordobas);
				pst.setFloat(8, totalDolar);
				pst.setString(9,this.usuario);
				this.banderin = pst.executeUpdate();
				if (banderin > 0) {
					//JOptionPane.showMessageDialog(null, "Factura Guardada Exitosamente", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
				cn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}

	}
//Guardar detalleFactura

	public void editar() {
		this.cn = Conexion();
		this.consulta = "SELECT f.*,cl.nombres,apellidos,formapago.tipoVenta AS pago FROM facturas AS f LEFT JOIN creditos AS c"
			+ " ON(f.credito=c.id) LEFT JOIN clientes AS cl ON(cl.id=c.cliente) INNER JOIN formapago ON(formaPago.id=f.tipoVenta)"
			+ " WHERE f.id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.id = this.rs.getInt("id");
				this.caja = this.rs.getInt("caja");
				this.credito = this.rs.getInt("credito");
				this.nombreComprador = this.rs.getString("nombre_comprador");
				this.nombres = this.rs.getString("nombres");
				this.apellidos = this.rs.getString("apellidos");
				this.fecha = this.rs.getTimestamp("fecha");
				this.formapago = this.rs.getString("pago");
				this.iva = this.rs.getFloat("impuestoISV");
				this.totalCordobas = this.rs.getFloat("totalCordobas");
				this.totalDolar = this.rs.getFloat("totalDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

//metodo para busqueda general y por nombre y cod. barra de producto
	public DefaultTableModel BusquedaGeneralProductoVender(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT p.id, p.codigoBarra, p.nombre AS nombreProducto, precioVenta, monedaVenta, fechaVencimiento, stock,"
			+ " ubicacion, p.descripcion, c.nombre AS nombreCategoria, m.nombre as nombreMarca FROM productos AS p LEFT JOIN "
			+ "categorias AS c ON(p.categoria=c.id) LEFT JOIN marca AS m ON(p.marca=m.id) WHERE "
			+ "CONCAT(p.codigoBarra, p.nombre) LIKE '%" + buscar + "%' AND p.estado = 1";
		String[] registros = new String[11];
		String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioVenta", "Moneda", "Fecha Vencimiento", "Stock", "Categoria", "marca", "Ubicacion", "Descripcion"};
		modelo = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(consulta);
			while (rs.next()) {
				registros[0] = rs.getString("id");
				registros[1] = rs.getString("codigoBarra");
				registros[2] = rs.getString("nombreProducto");
				registros[3] = rs.getString("precioVenta");
				registros[4] = rs.getString("monedaVenta");
				registros[5] = rs.getString("fechaVencimiento");
				registros[6] = rs.getString("stock");
				registros[7] = rs.getString("nombreCategoria");
				registros[8] = rs.getString("nombreMarca");
				registros[9] = rs.getString("ubicacion");
				registros[10] = rs.getString("descripcion");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return modelo;
	}
//metodo para filtro de busqueda del producto por categoria de producto

	public DefaultTableModel BuscarPorCategoria(String categoria) {
		cn = Conexion();
		this.consulta = "SELECT p.id, p.codigoBarra, p.nombre AS nombreProducto, precioVenta, monedaVenta, fechaVencimiento, stock,"
			+ " ubicacion, p.descripcion, c.nombre AS nombreCategoria, m.nombre as nombreMarca FROM productos AS p INNER JOIN"
			+ " categorias AS c ON(p.categoria=c.id) INNER JOIN marca AS m ON(p.marca=m.id)"
			+ " WHERE CONCAT(c.nombre) LIKE '%" + categoria + "%' AND p.estado = 1";
		String[] registros = new String[11];
		String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioVenta", "Moneda", "Fecha Vencimiento", "Stock", "Categoria", "marca", "Descuento", "Ubicacion", "Descripcion"};
		modelo = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(consulta);
			while (rs.next()) {
				registros[0] = rs.getString("id");
				registros[1] = rs.getString("codigoBarra");
				registros[2] = rs.getString("nombreProducto");
				registros[3] = rs.getString("precioVenta");
				registros[4] = rs.getString("monedaVenta");
				registros[5] = rs.getString("fechaVencimiento");
				registros[6] = rs.getString("stock");
				registros[7] = rs.getString("nombreCategoria");
				registros[8] = rs.getString("nombreMarca");
				registros[9] = rs.getString("ubicacion");
				registros[10] = rs.getString("descripcion");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return modelo;
	}
//metodo para filtro de busqueda del producto por laboratorio

	public DefaultTableModel BuscarPorMarca(String marca) {
		cn = Conexion();
		this.consulta = "SELECT p.id, p.codigoBarra, p.nombre AS nombreProducto, precioVenta, monedaVenta, fechaVencimiento, stock,"
			+ " ubicacion, p.descripcion, c.nombre AS nombreCategoria, m.nombre as nombreMarca FROM productos AS p INNER JOIN"
			+ " categorias AS c ON(p.categoria=c.id) INNER JOIN marca AS m ON(p.marca=m.id) WHERE CONCAT(marca.nombre)"
			+ " LIKE '%" + marca + "%' AND p.estado = 1";
		String[] registros = new String[11];
		String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioVenta", "Moneda", "Fecha Vencimiento", "Stock", "Categoria", "marca", "Descuento", "Ubicacion", "Descripcion"};
		modelo = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(consulta);
			while (rs.next()) {
				registros[0] = rs.getString("id");
				registros[1] = rs.getString("codigoBarra");
				registros[2] = rs.getString("nombreProducto");
				registros[3] = rs.getString("precioVenta");
				registros[4] = rs.getString("monedaVenta");
				registros[5] = rs.getString("fechaVencimiento");
				registros[6] = rs.getString("stock");
				registros[7] = rs.getString("nombreCategoria");
				registros[8] = rs.getString("nombreMarca");
				registros[9] = rs.getString("ubicacion");
				registros[10] = rs.getString("descripcion");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return modelo;
	}
//metodo para obtener los tipos de pago

	public DefaultComboBoxModel FormasPago() {
		cn = Conexion();
		this.consulta = "SELECT * FROM formapago";
		combo = new DefaultComboBoxModel();
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(this.consulta);
			while (rs.next()) {
				combo.addElement(rs.getString("tipoVenta"));
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return combo;
	}
//metodo que me retorna el id de la factura y sumo 1 para mostrar la factura siguiente 

	public String ObtenerIdFactura() {
		cn = Conexion();
		int sumaId = 0, s;
		String id = "", obtenerId = "";
		this.consulta = "SELECT MAX(id) AS id FROM facturas";
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(this.consulta);
			while (rs.next()) {
				obtenerId = rs.getString("id");
			}
			if (obtenerId != null) {
				sumaId = Integer.parseInt(obtenerId) + 1;
				id = String.valueOf(sumaId);
			} else {
				obtenerId = "0";
				sumaId = Integer.parseInt(obtenerId) + 1;
				id = String.valueOf(sumaId);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return id;
	}

	//metodo para obtener el id de la forma de pago segun el metodo de pago que recibe
	public int ObtenerFormaPago(String pago) {
		cn = Conexion();
		this.consulta = "SELECT id FROM formapago WHERE tipoVenta = '" + pago + "'";
		int id = 0;
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(this.consulta);
			while (rs.next()) {
				id = rs.getInt("id");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return id;
	}

	public void Vender(String id, String cantidad) {
		cn = Conexion();
		Float cantidadP = Float.parseFloat(cantidad);
		int idP = Integer.parseInt(id);
		this.consulta = "{CALL venderProductoStock(?,?)}";
		try {
			CallableStatement cst = this.cn.prepareCall(this.consulta);
			cst.setInt(1, idP);
			cst.setFloat(2, cantidadP);
			cst.execute();
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void ActualizarFactura() {
		cn = Conexion();
		this.consulta = "UPDATE facturas SET caja = ?, credito = ?, nombre_comprador = ?,fecha = ? , tipoVenta = ?, impuestoISV = ?,"
			+ " totalCordobas = ?, totalDolares = ? WHERE id=?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, this.caja);
			if (this.credito > 0) {
				this.pst.setInt(2, this.credito);
			} else {
				this.pst.setNull(2, java.sql.Types.INTEGER);
			}
			pst.setString(3, this.nombreComprador);
			pst.setTimestamp(4, this.fecha);
			pst.setInt(5, this.pago);
			pst.setFloat(6, this.iva);
			pst.setFloat(7, this.totalCordobas);
			pst.setFloat(8, this.totalDolar);
			pst.setInt(9, this.id);
			pst.execute();
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "factura actualizada correctamente");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void ActualizarDetalle(String id, int producto, float precio, float cant, float total) {
		cn = Conexion();
		this.consulta = "UPDATE detalleFactura SET producto = ?, precioProducto = ?, cantidadProducto = ?, totalVenta = ? WHERE id=?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, producto);
			pst.setFloat(2, precio);
			pst.setFloat(3, cant);
			pst.setFloat(4, total);
			pst.setString(5, id);
			pst.execute();
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {

			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	//VERIFICAR SI EXISTE EL PRODUCTO 
	public boolean verd(String codBarra) {
		boolean isYes = true;
		this.cn = Conexion();
		this.consulta = "SELECT id,codigoBarra, nombre, precioVenta, monedaVenta, stock FROM productos WHERE codigoBarra = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, codBarra);
			ResultSet rs = this.pst.executeQuery();
			if (rs.next()) {
				isYes = true;
			} else {
				isYes = false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "");
		}

		return isYes;
	}

	public String cleanChars(String value){
		value = value.replace("C", "");
		value = value.replace("$", "");
		return value;	
	}
	
	public void obtenerPorCodBarra(String codBarra) {
		this.producto = new String[6];
		float importe;
		this.cn = Conexion();
		this.consulta = "SELECT id,codigoBarra, nombre, precioVenta, monedaVenta, stock FROM productos WHERE codigoBarra = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, codBarra);
			ResultSet rs = this.pst.executeQuery();
			if (verd(codBarra)) {
				this.exito = true;
				while (rs.next()) {
					this.producto[0] = rs.getString("id");
					this.producto[1] = rs.getString("codigoBarra");
					this.producto[2] = "1.0";
					this.producto[3] = rs.getString("nombre");
					this.stock = rs.getFloat("stock");
					this.monedaVenta = rs.getString("monedaVenta");
					this.producto[4] = (this.monedaVenta.equals("Dolar")) 
						? "$" + rs.getString("precioVenta") : "C$" + rs.getString("precioVenta");
				}
				if (this.producto[4] != null) {
					importe = Float.parseFloat(producto[2]) * Float.parseFloat(cleanChars(producto[4]));
					if (this.monedaVenta.equals("Dolar")) {
						this.producto[5] = "$" + formato.format(importe);
					} else {
						this.producto[5] = "C$" + formato.format(importe);
					}
				} 
			} else {
				this.exito = false;
				JOptionPane.showMessageDialog(null, "Producto no esta insgresado.. O no tiene código de barra");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion obtenerPorCodBarra En modelo facturacion");
		}
	}

	public void obtenerPorId(String id) {
		this.producto = new String[6];
		float importe;
		this.cn = Conexion();
		this.consulta = "SELECT id,codigoBarra, nombre, precioVenta, monedaVenta, stock FROM productos WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.producto[0] = rs.getString("id");
				this.producto[1] = rs.getString("codigoBarra");
				this.producto[2] = "1.0";
				this.producto[3] = rs.getString("nombre");
				this.producto[4] = rs.getString("precioVenta");
				this.stock = rs.getFloat("stock");
				this.monedaVenta = rs.getString("monedaVenta");
			}
			if (this.producto[4] == null) {

			} else {
				importe = Float.parseFloat(producto[2]) * Float.parseFloat(producto[4]);
				if (this.monedaVenta.equals("Dolar")) {
					this.producto[5] = "$" + formato.format(importe);
				} else {
					this.producto[5] = "C$" + formato.format(importe);
				}
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion obtenerPorCodBarra En modelo facturacion");
		}
	}

	public void monedaVentaProducto(String id) {
		this.cn = Conexion();
		this.consulta = "SELECT monedaVenta FROM productos WHERE id = ?";

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.monedaVenta = rs.getString("monedaVenta");
			}
			this.cn.close();
		} catch (Exception e) {

		}
	}

	public void ActualizarDevolucion(int id, float iva, float totalCordobas, float totalDolares) {
		this.cn = Conexion();
		String IVA = formato.format(iva), 
			TOTALCORDOBAS = formato.format(totalCordobas),
			TOTALDOLARES = formato.format(totalDolares);
		this.consulta = "UPDATE facturas SET impuestoISV = ?, totalCordobas = ?, totalDolares = ? WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, IVA);
			this.pst.setString(2, TOTALCORDOBAS);
			this.pst.setString(3, TOTALDOLARES);
			this.pst.setInt(4, id);
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion ActualizarDevolucion en modelo Facturacion");
		}
	}

	//esta funcio no se esta utilizando
	public void eliminarDetalle(int id) {
		this.cn = Conexion();
		this.consulta = "DELETE FROM detalleFactura WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion eliminarDetalle en modelo Facturacion");
		}
	}

	public void obtenerTotalFacturaSeleccionada(int id) {
		this.cn = Conexion();
		this.consulta = "SELECT totalCordobas, totalDolares FROM facturas WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.totalCordobas = rs.getFloat("totalCordobas");
				this.totalDolar = rs.getFloat("totalDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion obtenerTotalFacturaSeleccionada en modelo Facturacion");
		}
	}

}
