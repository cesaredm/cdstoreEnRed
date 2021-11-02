package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Creditos extends Conexiondb {
	private int id;
	private int cliente;
	private Date fecha;
	private String estado;
	private float limite;
	DefaultTableModel modelo;
	Connection cn;
	String consulta;
	String[] registros;
	PagosCreditos pagos;
	PreparedStatement pst;
	boolean empty;
	int banderin;
	DecimalFormat formato;

	public Creditos() {
		this.cn = null;
		this.pst = null;
		this.consulta = null;
		this.banderin = 0;
		this.pagos = new PagosCreditos();
		this.empty = true;
		formato = new DecimalFormat("##########00.00");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public float getLimite() {
		return limite;
	}

	public void setLimite(float limite) {
		this.limite = limite;
	}

	
	//Funcion para guardar los creditos
	public void GuardarCredito() {
		cn = Conexion();
		//consulta sql para guardar creditos
		this.consulta = "INSERT INTO creditos(cliente, estado, fecha, limite) VALUES(?,?,?,?)";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, cliente);
			pst.setString(2, estado);
			pst.setDate(3, fecha);
			pst.setFloat(4, limite);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Credito Agregado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//Funcion para actualizar los creditos
	public void Actualizar() {
		cn = Conexion();
		this.consulta = "UPDATE creditos SET cliente = ?, estado = ?, fecha = ?, limite = ? WHERE id=?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, cliente);
			pst.setString(2, estado);
			pst.setDate(3, fecha);
			pst.setFloat(4, limite);
			pst.setInt(5, id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(
					null,
					"Credito " + id + " Actualizado exitosamente",
					"Informacion",
					JOptionPane.INFORMATION_MESSAGE
				);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void editar(){
		this.cn = Conexion();
		this.consulta = "SELECT * FROM creditos WHERE  id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			ResultSet rs = this.pst.executeQuery();
			while(rs.next()){
				this.cliente = rs.getInt("cliente");
				this.estado = rs.getString("estado");
				this.fecha = rs.getDate("fecha");
				this.limite = rs.getFloat("limite");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//funcion para eliminar creditos
	public void Eliminar() {
		cn = Conexion();
		this.consulta = "DELETE FROM creditos WHERE id = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(
					null,
					"Credito " + id + " Borrado exitosamente",
					"Informacion",
					JOptionPane.INFORMATION_MESSAGE
				);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//funcion de consulta de datos de creditos y retornar una tabla con los creditos para mostrarla en interfaz
	public DefaultTableModel Mostrar(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT c.id,SUM(f.totalFactura) AS totalCredito, c.limite ,cl.id as idCliente,nombres,apellidos, c.estado"
			+ " FROM creditos AS c INNER JOIN clientes AS cl ON(c.cliente = cl.id) INNER JOIN facturas AS f ON(f.credito = c.id)"
			+ " WHERE CONCAT(c.id, cl.nombres, cl.apellidos) LIKE '%" + buscar + "%' AND c.estado = 'Pendiente' GROUP BY cl.id";
		String[] titulos = {"N° Credito", "Saldo", "Limite", "Id Cliente", "Nombres", "Apellidos", "Estado"};
		float saldo = 0, monto = 0;
		this.registros = new String[7];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		DecimalFormat formato = new DecimalFormat("#############.##");
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//en la variable monto obtengo el total de pagos de el cliente
				monto = this.pagos.PagosCliente(rs.getInt("idCliente"));
				//en la variable saldo obtengo lo que queda de la resta de lo que debe el cliente menos total de pagos que ha hecho
				saldo = Float.parseFloat(rs.getString("totalCredito")) - monto;
				this.registros[0] = rs.getString("id");
				this.registros[1] = String.valueOf(formato.format(saldo));
				this.registros[2] = rs.getString("limite");
				this.registros[3] = rs.getString("idCliente");
				this.registros[4] = rs.getString("nombres");
				this.registros[5] = rs.getString("apellidos");
				this.registros[6] = rs.getString("estado");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " mostrar creditos");
		}
		return this.modelo;
	}

	//funcion para mostrar Los creditos abiertos o creados
	public DefaultTableModel MostrarCreditosCreados(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT creditos.id as idCredito,cliente, clientes.nombres, apellidos, creditos.fecha, limite, estado FROM creditos INNER JOIN clientes ON(clientes.id = creditos.cliente) WHERE CONCAT(creditos.id, clientes.nombres, clientes.apellidos) LIKE '%" + buscar + "%'";
		String[] titulos = {"N° Credito", "Id Cliente", "Nombres", "Apellidos", "fecha", "Limite", "Estado"};
		this.registros = new String[7];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.registros[0] = rs.getString("idCredito");
				this.registros[1] = rs.getString("cliente");
				this.registros[2] = rs.getString("nombres");
				this.registros[3] = rs.getString("apellidos");
				this.registros[4] = rs.getString("fecha");
				this.registros[5] = rs.getString("limite");
				this.registros[6] = rs.getString("estado");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " mostrar creditos");
		}
		return this.modelo;
	}

	//mostrar solo los creditos abiertos y pendientes menos los cancelados 
	public DefaultTableModel MostrarCreditosAddFactura(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT creditos.id as idCredito,cliente, clientes.nombres, apellidos, creditos.fecha, estado FROM creditos INNER JOIN clientes ON(clientes.id = creditos.cliente) WHERE CONCAT(creditos.id, clientes.nombres, clientes.apellidos) LIKE '%" + buscar + "%' AND creditos.estado!='Cancelado'";
		String[] titulos = {"N° Credito", "Id Cliente", "Nombres", "Apellidos", "fecha", "Estado"};
		this.registros = new String[6];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.registros[0] = rs.getString("idCredito");
				this.registros[1] = rs.getString("cliente");
				this.registros[2] = rs.getString("nombres");
				this.registros[3] = rs.getString("apellidos");
				this.registros[4] = rs.getString("fecha");
				this.registros[5] = rs.getString("estado");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " mostrar creditos");
		}
		return this.modelo;
	}

	//funcion para actualizar el estado del credito segun lo que debe o a ha pagado el cliente
	public void ActualizarEstadoCredito(int id, String estado) {
		cn = Conexion();
		this.consulta = "UPDATE creditos SET estado=? WHERE id=?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, estado);
			pst.setInt(2, id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				//JOptionPane.showMessageDialog(null,"Credito "+id+" Cancelado");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " Actualizar Estado de Credito");
		}
	}

	//funcion que que me obtiene el total de credito que debe el cliente
	public float TotalCreditoCliente(int id) {
		cn = Conexion();
		float creditoCliente = 0;
		int idCredito = 0;
		String estado = "";
		this.consulta = "SELECT creditos.id,SUM(facturas.totalFactura) AS totalCredito, clientes.id as idCliente,nombres,apellidos, "
			+ "creditos.estado FROM"
			+ " creditos INNER JOIN clientes ON(creditos.cliente = clientes.id) INNER JOIN facturas ON(facturas.credito = creditos.id)"
			+ " WHERE creditos.id = ? AND creditos.estado = 'Abierto'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				creditoCliente = rs.getFloat("totalCredito");
				idCredito = rs.getInt("id");
				estado = rs.getString("estado");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " total credito cliente");
		}
		return creditoCliente;
	}

	//
	public DefaultTableModel MostrarProductosCreditoDolar(int id) {
		this.cn = Conexion();
		float tasaCambio;
		String[] titulos = {"Fecha", "Nombre", "Cantidad", "Precio", "", "TC", "Total C$", "N. Fact"};
		registros = new String[8];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		this.consulta = "SELECT f.fecha, p.nombre, df.cantidadProducto, precioProducto, totalVenta AS totalImporte, f.id AS factura FROM facturas"
			+ " AS f INNER JOIN creditos AS c ON(f.credito=c.id) INNER JOIN detalleFactura AS df ON(f.id = df.factura)"
			+ " INNER JOIN productos AS p ON(df.producto=p.id)"
			+ " WHERE c.id = ? AND p.monedaVenta = 'Dolar' AND df.cantidadProducto > 0 ORDER BY f.id DESC";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				tasaCambio = (rs.getFloat("totalImporte") / rs.getFloat("cantidadProducto")) / rs.getFloat("precioProducto");
				registros[0] = rs.getString("fecha");
				registros[1] = rs.getString("nombre");
				registros[2] = rs.getString("cantidadProducto");
				registros[3] = rs.getString("precioProducto");
				registros[4] = "$";
				registros[5] = this.formato.format(tasaCambio);
				registros[6] = rs.getString("totalImporte");
				registros[7] = rs.getString("factura");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modelo;
	}

	public DefaultTableModel MostrarProductosCreditoCordobas(int id) {
		this.cn = Conexion();
		String[] titulos = {"Fecha", "Nombre", "Cantidad", "Precio", "", "Total C$", "N. Fact"};
		registros = new String[7];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		this.consulta = "SELECT f.fecha, p.nombre, df.cantidadProducto, precioProducto, totalVenta AS totalImporte, f.id AS factura FROM facturas"
			+ " AS f INNER JOIN creditos AS c ON(f.credito=c.id) INNER JOIN detalleFactura AS df ON(f.id = df.factura)"
			+ " INNER JOIN productos AS p ON(df.producto=p.id)"
			+ " WHERE c.id = ? AND p.monedaVenta = 'Cordobas' AND df.cantidadProducto > 0 ORDER BY f.id DESC";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				registros[0] = rs.getString("fecha");
				registros[1] = rs.getString("nombre");
				registros[2] = rs.getString("cantidadProducto");
				registros[3] = rs.getString("precioProducto");
				registros[4] = "C$";
				registros[5] = rs.getString("totalImporte");
				registros[6] = rs.getString("factura");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modelo;
	}

	public DefaultTableModel MostrarAbonosCliente(int id) {
		this.cn = Conexion();
		this.consulta = "SELECT p.id,p.fecha AS f,p.monto,p.anotacion FROM pagoscreditos AS p INNER JOIN creditos AS c ON(p.credito=c.id) WHERE c.id = ?";
		String[] titulos = {"Id Pago", "Fecha", "Monto", "Anotación"};
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		this.registros = new String[4];

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.registros[0] = rs.getString("id");
				this.registros[1] = rs.getString("f");
				this.registros[2] = rs.getString("monto");
				this.registros[3] = rs.getString("anotacion");
				this.modelo.addRow(this.registros);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en funcion MostrarAbonosCliente en modelo creditos");
		}

		return this.modelo;
	}

	//
	public void ActualizarCreditoFactura(int idFactura, int idCredito) {
		this.cn = Conexion();
		this.consulta = "UPDATE facturas SET credito = ? FROM id = ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setInt(1, idCredito);
			pst.setInt(2, idFactura);
			pst.execute();
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion ActualizarCreditoFactura en modelo creditos");
		}
	}

	public boolean VerificarExistenciaDeCredito(int id) {
		this.cn = Conexion();
		String valor = "";
		boolean validar = true;
		this.consulta = "SELECT clientes.id FROM clientes INNER JOIN creditos ON(clientes.id=creditos.cliente) WHERE clientes.id = ?";
		try {
			pst = cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				valor = rs.getString("id");
			}
			if (valor.equals("")) {
				validar = false;
			} else {
				validar = true;
				JOptionPane.showMessageDialog(null,"Ya existe una cuenta con el cliente numero "+id);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion VerificarExistenciaDeCredito");
		}
		return validar;
	}

	//metodo para obtener el nombre de cliente que tiene el credito
	public String NombreCliente(String idCredito) {
		String nombre = "";
		this.cn = Conexion();
		this.consulta = "SELECT c.nombres, c.apellidos FROM clientes AS c INNER JOIN creditos ON(c.id = creditos.cliente) WHERE creditos.id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, idCredito);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				nombre = rs.getString("nombres") + " " + rs.getString("apellidos");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion NombreCliente en el modelo credito");
		}
		return nombre;
	}

	public float creditoPorCliente(int id) {
		cn = Conexion();
		this.consulta = "SELECT SUM(facturas.totalFactura) AS totalCredito, clientes.id as idCliente FROM creditos"
			+ " INNER JOIN clientes ON(creditos.cliente = clientes.id) INNER JOIN facturas ON(facturas.credito = creditos.id)"
			+ " WHERE creditos.estado = 'Pendiente' AND creditos.id = ?";
		float saldo = 0, monto = 0;
		DecimalFormat formato = new DecimalFormat("#############.##");
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//en la variable monto obtengo el total de pagos de el cliente
				monto = this.pagos.PagosCliente(rs.getInt("idCliente"));
				//en la variable saldo obtengo lo que queda de la resta de lo que debe el cliente menos total de pagos que ha hecho
				saldo = rs.getFloat("totalCredito") - monto;
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " mostrar creditos");
		}
		return saldo;
	}

	public float limiteCredito(String id) {
		this.cn = Conexion();
		float limite = 0;
		this.consulta = "SELECT limite FROM creditos WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				limite = rs.getFloat("limite");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion limiteCredito en modelo creditos");
		}
		return limite;
	}

	public float creditoGlobalCliente(int id) {
		this.consulta = "SELECT SUM(facturas.totalFactura) AS totalCredito FROM creditos INNER JOIN clientes ON(creditos.cliente = clientes.id) "
			+ "INNER JOIN facturas ON(facturas.credito = creditos.id) WHERE creditos.id = ?";
		this.cn = Conexion();
		float total = 0;
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("totalCredito");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion creditoGlobalCliente en modelo creditos");
		}
		return total;
	}

	public float AbonoGlobalCliente(int id) {
		this.consulta = "SELECT SUM(p.monto) AS totalAbonos FROM pagoscreditos AS p "
			+ "INNER JOIN creditos AS c ON(p.credito = c.id) INNER JOIN clientes ON(c.cliente=clientes.id) WHERE c.id = ?";
		this.cn = Conexion();
		float total = 0;
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("totalAbonos");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion AbonosGlobalCliente en modelo creditos");
		}
		return total;
	}

	public int obtenerUltimoPago() {
		int id = 0;
		this.cn = Conexion();
		this.consulta = "SELECT MAX(id) AS id FROM pagoscreditos";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en la funcion obtenerUltimoPago en el modelo Creditos.. ");
		}
		return id + 1;
	}

	public DefaultTableModel morosos(Date fecha) {
		int contRow = 0;
		float saldo;
		this.cn = Conexion();
		String[] titulos = {"Nombres", "Apellidos", "Telefono", "Dirección", "F. pago", "Monto ultimo pago", "N pago", "Saldo"};
		this.registros = new String[8];
		String setLocalFormat = "SET lc_time_names = 'es_ES'";
		this.consulta = "SELECT p.id, p.credito, DATE_FORMAT(p.fecha,'%a, %d-%b-%Y') AS fechaFormat ,"
			+ " p.monto,cl.nombres,apellidos,telefono,direccion FROM pagoscreditos AS p"
			+ " INNER JOIN (SELECT credito, max(fecha) AS mfecha FROM pagoscreditos GROUP BY credito) AS ultimoPago ON"
			+ " p.credito=ultimoPago.credito INNER JOIN creditos AS c ON(p.credito=c.id) INNER JOIN clientes AS cl ON(cl.id=c.cliente)"
			+ " AND p.fecha=ultimoPago.mfecha AND c.estado='Pendiente' AND p.fecha < ?";
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(setLocalFormat);
			this.pst.execute();
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				saldo = this.creditoGlobalCliente(rs.getInt("credito")) - this.AbonoGlobalCliente(rs.getInt("credito"));
				this.registros[0] = rs.getString("nombres");
				this.registros[1] = rs.getString("Apellidos");
				this.registros[2] = rs.getString("telefono");
				this.registros[3] = rs.getString("direccion");
				this.registros[4] = rs.getString("fechaFormat");
				this.registros[5] = rs.getString("monto");
				this.registros[6] = rs.getString("id");
				this.registros[7] = this.formato.format(saldo);
				this.modelo.addRow(this.registros);
				contRow++;

			}
			if (contRow > 0) {
				this.empty = true;
			} else {
				this.empty = false;
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo morosos en modelo creditos");
		}
		return this.modelo;
	}

	public boolean isEmpty() {
		return empty;
	}

	public boolean isPagoAtrasado(Date fecha, String credito) {
		this.cn = Conexion();
		int cont = 0;
		boolean isPago = true;
		this.consulta = "SELECT * FROM pagoscreditos"
			+ " WHERE (SELECT fecha FROM pagoscreditos WHERE credito = ? ORDER BY fecha DESC LIMIT 1) < ?"
			+ " AND credito = ? ORDER BY fecha DESC LIMIT 1";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, credito);
			this.pst.setDate(2, fecha);
			this.pst.setString(3, credito);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				cont++;
			}
			if (cont > 0) {
				isPago = true;
			} else {
				isPago = false;
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo isPagoAtrasado en modelo creditos");
		}
		return isPago;
	}
}
