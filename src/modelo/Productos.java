package modelo;

import controlador.CtrlProducto;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Productos extends Conexiondb {

	private String id,
		codigoBarra,
		nombre,
		monedaCompra,
		monedaVenta,
		categoria,
		laboratorio,
		ubicacion,
		descripcion;
	private Date fechaVencimiento;
	private float utilidad,
		precioCompra,
		precioVenta,
		precioMinimoVenta,
		stock,
		inversionDolares,
		inversionCordobas;

	DefaultTableModel modelo;
	DefaultComboBoxModel combo;
	Connection cn;
	PreparedStatement pst;
	String consulta;
	int banderin;
	private boolean existe = true;
	private float precioMinimo;

	public Productos() {
		this.cn = null;
		this.combo = new DefaultComboBoxModel();
		this.pst = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(float precioCompra) {
		this.precioCompra = precioCompra;
	}

	public String getMonedaCompra() {
		return monedaCompra;
	}

	public void setMonedaCompra(String monedaCompra) {
		this.monedaCompra = monedaCompra;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getMonedaVent() {
		return monedaVenta;
	}

	public void setMonedaVent(String monedaVent) {
		this.monedaVenta = monedaVent;
	}

	public float getPrecioMinimoVenta() {
		return precioMinimoVenta;
	}

	public void setPrecioMinimoVenta(float precioMinimoVenta) {
		this.precioMinimoVenta = precioMinimoVenta;
	}

	public float getStock() {
		return stock;
	}

	public void setStock(float stock) {
		this.stock = stock;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(float utilidad) {
		this.utilidad = utilidad;
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public float getPrecioMinimo() {
		return this.precioMinimo;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public float getInversionDolares() {
		return inversionDolares;
	}


	public float getInversionCordobas() {
		return inversionCordobas;
	}


	public void Guardar() {
		cn = Conexion();
		this.consulta = "INSERT INTO productos(codigoBarra, nombre, precioCompra, monedaCompra, precioVenta, precioMinimo, monedaVenta,"
			+ " fechaVencimiento, stock, categoria, marca, ubicacion, descripcion, utilidad) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		int Idcategoria = Integer.parseInt(categoria),
			Idlaboratorio = Integer.parseInt(laboratorio);
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, codigoBarra);
			pst.setString(2, nombre);
			pst.setFloat(3, precioCompra);
			pst.setString(4, monedaCompra);
			pst.setFloat(5, precioVenta);
			pst.setFloat(6, this.precioMinimoVenta);
			pst.setString(7, monedaVenta);
			pst.setDate(8, fechaVencimiento);
			pst.setFloat(9, stock);
			pst.setInt(10, Idcategoria);
			pst.setInt(11, Idlaboratorio);
			pst.setString(12, ubicacion);
			pst.setString(13, descripcion);
			pst.setFloat(14, utilidad);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Producto guardado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void editar() {
		this.cn = Conexion();
		this.consulta = "SELECT * FROM productos WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.codigoBarra = rs.getString("codigoBarra");
				this.nombre = rs.getString("nombre");
				this.precioCompra = rs.getFloat("precioCompra");
				this.monedaCompra = rs.getString("monedaCompra");
				this.precioVenta = rs.getFloat("precioVenta");
				this.monedaVenta = rs.getString("monedaVenta");
				this.precioMinimoVenta = rs.getFloat("precioMinimo");
				this.descripcion = rs.getString("descripcion");
				this.fechaVencimiento = rs.getDate("fechaVencimiento");
				this.stock = rs.getFloat("stock");
				this.categoria = rs.getString("categoria");
				this.laboratorio = rs.getString("laboratorio");
				this.ubicacion = rs.getString("ubicacion");
				this.utilidad = rs.getFloat("utilidad");
			}
			this.cn.close();
		} catch (SQLException e) {
		}
	}

	public void guardarKardexIncial(int producto, String user, float cantidad, String anotacion) {
		this.cn = Conexion();
		this.consulta = "INSERT INTO kardexentradas(producto,usuario,cantidad,anotacion) VALUES(?,?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, producto);
			this.pst.setString(2, user);
			this.pst.setFloat(3, cantidad);
			this.pst.setString(4, anotacion);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				//JOptionPane.showMessageDialog(null, "Kardex Actualizado exitosamente");
			} else {
				JOptionPane.showMessageDialog(null, "Error al actualizar kardex");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion guardarKardex en modelo producto.");
		}
	}

	public void Actualizar() {
		cn = Conexion();
		this.consulta = "UPDATE productos SET codigoBarra=?, nombre=?, precioCompra=?, monedaCompra=?, precioVenta=?, precioMinimo=?,"
			+ " monedaVenta=?, fechaVencimiento=?, stock=?, categoria=?, marca=?, ubicacion=?, descripcion=?, utilidad=? WHERE id = ?";

		int Idcategoria = Integer.parseInt(categoria),
			Idlaboratorio = Integer.parseInt(laboratorio);
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, codigoBarra);
			pst.setString(2, nombre);
			pst.setFloat(3, precioCompra);
			pst.setString(4, monedaCompra);
			pst.setFloat(5, precioVenta);
			pst.setFloat(6, this.precioMinimoVenta);
			pst.setString(7, monedaVenta);
			pst.setDate(8, fechaVencimiento);
			pst.setFloat(9, stock);
			pst.setInt(10, Idcategoria);
			pst.setInt(11, Idlaboratorio);
			pst.setString(12, ubicacion);
			pst.setString(13, descripcion);
			pst.setFloat(14, utilidad);
			pst.setString(15, id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel Consulta(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT p.id, p.codigoBarra, p.nombre AS nombreProducto, precioCompra, monedaCompra,"
			+ " precioVenta, monedaVenta, precioMinimo,fechaVencimiento, stock, ubicacion, p.descripcion,"
			+ " c.nombre AS nombreCategoria, m.nombre as nombreMarca, p.utilidad FROM productos AS p LEFT JOIN categorias AS c"
			+ " ON(p.categoria=c.id) LEFT JOIN marca AS m ON(p.marca=m.id) WHERE CONCAT(p.codigoBarra,"
			+ " p.nombre, c.nombre, m.nombre) LIKE '%" + buscar + "%' AND p.estado = 1 ORDER BY p.id DESC";
		String[] registros = new String[15];
		String[] titulos = {
			"Id",
			"Codigo Barra",
			"Nombre",
			"precioCompra",
			"Moneda",
			"precioVenta",
			"Moneda",
			"P. venta Min",
			"Descripción",
			"Fecha Vencimiento",
			"Stock",
			"Categoria",
			"marca",
			"Ubicación",
			"Utlidad%"
		};

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
				registros[3] = rs.getString("precioCompra");
				registros[4] = rs.getString("monedaCompra");
				registros[5] = rs.getString("precioVenta");
				registros[6] = rs.getString("monedaVenta");
				registros[7] = rs.getString("precioMinimo");
				registros[8] = rs.getString("descripcion");
				registros[9] = rs.getString("fechaVencimiento");
				registros[10] = rs.getString("stock");
				registros[11] = rs.getString("nombreCategoria");
				registros[12] = rs.getString("nombreMarca");
				registros[13] = rs.getString("ubicacion");
				registros[14] = rs.getString("utilidad");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return modelo;
	}
	//Mostrar todas la categorias para agregra al producto

	public DefaultTableModel MostrarCategorias(String nombre) {
		cn = Conexion();
		this.consulta = "SELECT * FROM categorias WHERE nombre LIKE '%" + nombre + "%'";
		String[] resultados = new String[3];
		String[] titulos = {"Id", "Nombre", "Descripcion"};
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

				resultados[0] = rs.getString("id");
				resultados[1] = rs.getString("nombre");
				resultados[2] = rs.getString("descripcion");
				this.modelo.addRow(resultados);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return modelo;
	}

	//Mostrar todas la Laboratorio para agregra al producto
	public DefaultTableModel MostrarMarca(String nombre) {
		cn = Conexion();
		this.consulta = "SELECT * FROM marca WHERE nombre LIKE '%" + nombre + "%'";
		String[] resultados = new String[3];
		String[] titulos = {"Id", "Nombre", "Descripcion"};
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

				resultados[0] = rs.getString("id");
				resultados[1] = rs.getString("nombre");
				resultados[2] = rs.getString("descripcion");
				modelo.addRow(resultados);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return modelo;
	}

	public void AgregarProductoStock(String id, String cantidad)//metodo para agregar producto al stock
	{
		cn = Conexion();
		float c = Float.parseFloat(cantidad);
		int idP = Integer.parseInt(id);
		this.consulta = "{CALL agregarProductoStock(?,?)}";
		try {
			CallableStatement cst = this.cn.prepareCall(this.consulta);
			cst.setInt(1, idP);
			cst.setFloat(2, c);
			this.banderin = cst.executeUpdate();
			if (banderin > 0) {
				//JOptionPane.showMessageDialog(null, "Se Agrego Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel MinimoStock(String categoria, float cantidad) {
		cn = Conexion();
		//Agregar precioVenta y MonedaVenta a la consulta y al titulo de la tabla
		this.consulta = "SELECT p.id, p.codigoBarra, p.nombre AS nombreProducto, precioVenta, monedaVenta,"
			+ " fechaVencimiento,stock, ubicacion, p.descripcion, c.nombre AS nombreCategoria, m.nombre as nombreMarca"
			+ " FROM productos AS p INNER JOIN categorias AS c ON(p.categoria=c.id) INNER JOIN marca AS m ON(p.marca=m.id)"
			+ " WHERE p.stock < " + cantidad + " AND c.nombre LIKE '%" + categoria + "%' AND p.estado = 1 ORDER BY p.stock";
		String[] titulos = {
			"Id",
			"Codigo Barra",
			"Nombre",
			"precioVenta",
			"Moneda",
			"Fecha Vencimiento",
			"Stock",
			"Categoria",
			"Marca",
			"Ubicacion",
			"Descripcion"
		};
		String[] registros = new String[12];
		modelo = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			Statement pst = this.cn.createStatement();
			//pst.setInt(1, cantidad);
			//pst.setString(2, categoria);
			ResultSet rs = pst.executeQuery(consulta);
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
		return this.modelo;
	}

	public void GenerarReporteStockMin(String categ, float cantidad) throws SQLException {
		try {
			this.cn = Conexion();
			JasperReport Reporte = null;
			String path = "/ReportesPDF/ProductosinStock.jasper";
			Map parametros = new HashMap();
			parametros.put("cantidad", cantidad);
			parametros.put("categoria", categ);
			//Reporte = (JasperReport) JRLoader.loadObject(path);
			Reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/minStock.jasper"));
			JasperPrint jprint = JasperFillManager.fillReport(Reporte, parametros, cn);
			JasperViewer vista = new JasperViewer(jprint, false);
			vista.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			vista.setVisible(true);
			cn.close();
		} catch (JRException ex) {
			Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	//metodo para obtener el total de proyeccionVentas en el negocio precio de compra

	public void proyeccionVentas() {
		cn = Conexion();
		this.consulta = "SELECT DISTINCT (SELECT SUM(precioVenta*stock) FROM productos WHERE monedaVenta='Dolar' AND estado = 1)"
			+ " AS proyeccionDolares, (SELECT SUM(precioVenta*stock) FROM productos WHERE monedaVenta='Córdobas' AND estado = 1)"
			+ " AS proyeccionCordobas FROM productos";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.inversionCordobas = rs.getFloat("proyeccionCordobas");
				this.inversionDolares = rs.getFloat("proyeccionDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion inversion en modelo");
		}
	}

	public void inversionCordobas() {
		cn = Conexion();
		this.consulta = "SELECT SUM(precioCompra*stock) AS inversion FROM productos WHERE monedaCompra = 'Córdobas' AND estado = 1";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.inversionCordobas = rs.getFloat("inversion");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion inversion en modelo");
		}
	}

	public void inversionDolar() {
		cn = Conexion();
		this.consulta = "SELECT SUM(precioCompra*stock) AS inversion FROM productos WHERE monedaCompra = 'Dolar' AND estado = 1";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.inversionDolares = rs.getFloat("inversion");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion inversion en modelo");
		}
	}

	public void ExitsCodBarra(String codBarra) {
		String producto = "";
		this.cn = Conexion();
		this.consulta = "SELECT codigoBarra FROM productos WHERE codigoBarra = ? AND estado = 1";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, codBarra);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				producto = rs.getString("codigoBarra");
			}
			if (producto.equals("")) {
				setExiste(false);
			} else {
				setExiste(true);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion ExistCodBarra en modelo productos");
		}
	}

	public void precioMinimo(String id) {
		this.cn = Conexion();
		this.consulta = "SELECT precioMinimo FROM productos WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.precioMinimo = rs.getFloat("precioMinimo");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en la funcion precioMinimo en el modelo producto");
		}
	}

	public DefaultTableModel kardexSalidas(String id) {
		this.cn = Conexion();
		this.consulta = "SELECT P.ID,P.CODIGOBARRA,P.NOMBRE,DF.CANTIDADPRODUCTO AS CANTIDADDESALIDA, F.FECHA AS FECHASALIDA, F.ID AS NUMEROFACTURA FROM"
			+ " PRODUCTOS AS P INNER JOIN DETALLEFACTURA AS DF ON(P.ID=DF.PRODUCTO) INNER JOIN FACTURAS AS F ON(DF.FACTURA=F.ID)"
			+ " WHERE P.ID = ? AND DF.CANTIDADPRODUCTO != 0";
		String[] titulos = {"ID", "COD. BARRA", "NOMBRE", "CANTIDAD SALIDA", "FECHA SALIDA", "N. FACTURA"};
		String[] datos = new String[6];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				datos[0] = rs.getString("ID");
				datos[1] = rs.getString("CODIGOBARRA");
				datos[2] = rs.getString("NOMBRE");
				datos[3] = rs.getString("CANTIDADDESALIDA");
				datos[4] = rs.getString("FECHASALIDA");
				datos[5] = rs.getString("NUMEROFACTURA");
				this.modelo.addRow(datos);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo kardex en modelo productos");
		}
		return this.modelo;
	}

	public String kardexInicial(String id) {
		String kardexInicial = "";
		this.consulta = "SELECT cantidad FROM kardexentradas WHERE producto = ? AND anotacion = 'inicial'";
		this.cn = Conexion();
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				kardexInicial = rs.getString("cantidad");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion kardexInicial en modelo productos");
		}
		return kardexInicial;
	}

	public String countKardexSalidas(String id) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(DF.CANTIDADPRODUCTO) AS SALIDA FROM DETALLEFACTURA AS DF INNER JOIN PRODUCTOS AS P ON(DF.PRODUCTO=P.ID)"
			+ " WHERE P.ID = ?";
		String salidas = "";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				salidas = rs.getString("salida");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo kardex en modelo productos");
		}
		return salidas;
	}

	public DefaultTableModel kardexEntradas(String id) {
		this.cn = Conexion();
		this.consulta = "SELECT P.ID,P.CODIGOBARRA,P.NOMBRE,K.CANTIDAD,K.FECHA, K.ANOTACION,K.USUARIO FROM PRODUCTOS AS P INNER JOIN"
			+ " KARDEXENTRADAS AS K ON(P.ID=K.PRODUCTO) WHERE P.ID = ? AND (K.ANOTACION = 'add' OR K.ANOTACION = 'edicion stock')";
		String[] titulos = {"ID", "COD. BARRA", "NOMBRE", "CANTIDAD ENTRADA", "FECHA ENTRADA", "ACCION", "USUARIO"};
		String[] datos = new String[7];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				datos[0] = rs.getString("ID");
				datos[1] = rs.getString("CODIGOBARRA");
				datos[2] = rs.getString("NOMBRE");
				datos[3] = rs.getString("CANTIDAD");
				datos[4] = rs.getString("FECHA");
				datos[5] = rs.getString("ANOTACION");
				datos[6] = rs.getString("USUARIO");
				this.modelo.addRow(datos);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo kardexEntradas en modelo productos");
		}
		return this.modelo;
	}

	public int ultimoRegistro() {
		int id = 0;
		this.cn = Conexion();
		this.consulta = "SELECT MAX(id) as ID FROM productos";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo ultimoRegistro en modelo productos");
		}
		return id;
	}

	public DefaultTableModel BusquedaGeneralProductoVender(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT p.id, p.codigoBarra, p.nombre AS nombreProducto, precioVenta, monedaVenta,"
			+ " fechaVencimiento, stock, ubicacion, p.descripcion, c.nombre AS nombreCategoria, m.nombre as nombreMarca"
			+ " FROM productos AS p LEFT JOIN categorias AS c ON(p.categoria=c.id) LEFT JOIN marca AS m ON(p.marca=m.id) WHERE"
			+ " CONCAT(p.codigoBarra, p.nombre) LIKE '%" + buscar + "%' AND p.estado = 1";
		String[] registros = new String[11];
		String[] titulos = {
			"Id",
			"Codigo Barra",
			"Nombre",
			"precioVenta",
			"Moneda",
			"Fecha Vencimiento",
			"Stock",
			"Categoria",
			"marca",
			"Ubicacion",
			"Descripcion"
		};
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
	
	public void eliminar(){
		this.cn = Conexion();
		this.consulta = "UPDATE productos SET estado = 0 WHERE id = ?";	
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1,this.id);
			this.pst.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo eliminar en el modelo productos.");
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
