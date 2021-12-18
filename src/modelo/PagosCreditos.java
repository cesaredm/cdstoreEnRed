/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class PagosCreditos extends Conexiondb implements Serializable {

	private int id;
	private int credito;
	private float monto,
		pagosDolar,
		pagosCordobas;
	private Timestamp fecha;
	private int formaPago;
	private String anotacion,
		moneda,
		formaPagoString,
		verificarCancelado;

	transient DefaultTableModel modelo;
	transient Connection cn;
	transient PreparedStatement pst;
	String consulta;
	String[] resgistros;
	transient DefaultComboBoxModel combo;
	int banderin;

	public PagosCreditos() {
		this.cn = null;
		this.consulta = null;
		this.pst = null;
		this.banderin = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCredito() {
		return credito;
	}

	public void setCredito(int credito) {
		this.credito = credito;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public int getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(int formaPago) {
		this.formaPago = formaPago;
	}

	public String getFormaPagoString() {
		return formaPagoString;
	}

	public void setFormaPagoString(String formaPagoString) {
		this.formaPagoString = formaPagoString;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	public float getPagosDolar() {
		return pagosDolar;
	}

	public void setPagosDolar(float pagosDolar) {
		this.pagosDolar = pagosDolar;
	}

	public float getPagosCordobas() {
		return pagosCordobas;
	}

	public void setPagosCordobas(float pagosCordobas) {
		this.pagosCordobas = pagosCordobas;
	}

	public String getVerificarCancelado() {
		return verificarCancelado;
	}

	public void setVerificarCancelado(String verificarCancelado) {
		this.verificarCancelado = verificarCancelado;
	}

	//metodo para Guardar pagos
	public void Guardar() {
		this.consulta = "INSERT INTO pagoscreditos(credito,monto,moneda,fecha,formaPago,anotacion,pagoCancelacion) VALUES(?,?,?,?,?,?,?)";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, credito);
			pst.setFloat(2, monto);
			pst.setString(3, this.moneda);
			pst.setTimestamp(4, fecha);
			pst.setInt(5, formaPago);
			pst.setString(6, anotacion);
			pst.setString(7,verificarCancelado);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Pago guardado exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void editar() {
		this.cn = Conexion();
		this.consulta = "SELECT p.*, fp.tipoVenta FROM pagoscreditos AS p INNER JOIN formapago AS fp ON(p.formaPago = fp.id) WHERE p.id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.credito = rs.getInt("credito");
				this.fecha = rs.getTimestamp("fecha");
				this.monto = rs.getFloat("monto");
				this.formaPagoString = rs.getString("tipoVenta");
				this.anotacion = rs.getString("anotacion");
				this.moneda = rs.getString("moneda");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//metodo para eliminar pago

	public void Eliminar() {
		this.consulta = "DELETE FROM pagoscreditos WHERE id = ?";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, this.id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Pago eliminado exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//metodo para Actualizar Pagos

	public void Actualizar() {
		this.consulta = "UPDATE pagoscreditos SET credito = ?, monto = ?,moneda =?, fecha = ?, formaPago = ?, anotacion = ?  WHERE id = ?";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, this.credito);
			pst.setFloat(2, this.monto);
			pst.setString(3, this.moneda);
			pst.setTimestamp(4, this.fecha);
			pst.setInt(5, this.formaPago);
			pst.setString(6, this.anotacion);
			pst.setInt(7, this.id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Pago Actualizado Exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//metodo para mostrar todos los pagos

	public DefaultTableModel Mostrar(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT pagoscreditos.id AS idPago, monto as montoPago, pagoscreditos.moneda, credito, pagoscreditos.fecha,"
			+ " clientes.nombres,apellidos, formapago.tipoVenta,pagoscreditos.anotacion FROM pagoscreditos LEFT JOIN creditos"
			+ " ON(pagoscreditos.credito = creditos.id) LEFT JOIN formapago ON(formapago.id=pagoscreditos.formaPago) LEFT JOIN"
			+ " clientes ON(creditos.cliente = clientes.id) WHERE CONCAT(pagoscreditos.id, pagoscreditos.credito, pagoscreditos.fecha,"
			+ " clientes.nombres, clientes.apellidos) LIKE '%" + buscar + "%'";
		this.resgistros = new String[9];
		String[] titulos = {"Id Pago", "Monto de Pago", "Moneda", "Al Credito", "Fecha De Pago", "Nombres Cliente", "Apellidos Cliente", "Forma Pago", "Anotación"};
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.resgistros[0] = rs.getString("idPago");
				this.resgistros[1] = rs.getString("montoPago");
				this.resgistros[2] = rs.getString("moneda");
				this.resgistros[3] = rs.getString("credito");
				this.resgistros[4] = rs.getString("fecha");
				this.resgistros[5] = rs.getString("nombres");
				this.resgistros[6] = rs.getString("apellidos");
				this.resgistros[7] = rs.getString("tipoVenta");
				this.resgistros[8] = rs.getString("anotacion");
				this.modelo.addRow(resgistros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modelo;
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
	//metodo para obtener el id de la forma de pago segun el metodo de pago que recibe

	public String ObtenerFormaPago(String pago) {
		cn = Conexion();
		this.consulta = "SELECT id FROM formapago WHERE tipoVenta = '" + pago + "'";
		String id = "";
		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(this.consulta);
			while (rs.next()) {
				id = rs.getString("id");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return id;
	}

	//funcion que me obtiene el total de pagos que tiene el cliete
	public float PagosCliente(int id) {
		cn = Conexion();
		float credito = 0;
		this.consulta = "SELECT SUM(pagoscreditos.monto) AS pago FROM pagoscreditos INNER JOIN creditos ON(pagoscreditos.credito=creditos.id)"
			+ " INNER JOIN clientes ON(creditos.cliente = clientes.id) WHERE clientes.id = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				credito = rs.getFloat("pago");//total de pagos de cliente
			}
			cn.close();
		} catch (SQLException e) {
		}
		return credito;
	}

	public String cliente(int id) {
		this.consulta = "select c.nombres, c.apellidos from clientes as c inner join creditos on(c.id = creditos.cliente) where creditos.id = ?";
		this.cn = Conexion();
		String nombre = "";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				nombre = rs.getString("nombres") + " " + rs.getString("apellidos");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion cliente en modelo pagodCreditos");
		}
		return nombre;
	}

//	public float PagosSegunCredito(String id) {
//		this.consulta = "SELECT SUM(p.monto) AS pagos FROM pagoscreditos AS p INNER JOIN creditos AS c ON(c.id = p.credito) WHERE c.id = ?";
//		this.cn = Conexion();
//		float monto = 0;
//		try {
//			this.pst = this.cn.prepareStatement(this.consulta);
//			this.pst.setString(1, id);
//			ResultSet rs = this.pst.executeQuery();
//			while (rs.next()) {
//				monto = rs.getFloat("pagos");
//			}
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, e + " en la funcion saldo en modelo pagodCreditos");
//		}
//		return monto;
//	}

//	public float deuda(String id) {
//		this.consulta = "SELECT SUM(f.totalFactura) AS deuda FROM facturas AS f INNER JOIN creditos AS c ON(f.credito=c.id) WHERE c.id= ?";
//		this.cn = Conexion();
//		float monto = 0;
//		try {
//			this.pst = this.cn.prepareStatement(this.consulta);
//			this.pst.setString(1, id);
//			ResultSet rs = this.pst.executeQuery();
//			while (rs.next()) {
//				monto = rs.getFloat("deuda");
//			}
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, e + " en la funcion saldo en modelo pagodCreditos");
//		}
//		return monto;
//	}

	

}
