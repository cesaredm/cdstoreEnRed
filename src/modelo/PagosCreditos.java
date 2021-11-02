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

	private int credito;
	private float monto;
	private Date fecha;
	private int formaPago;
	private String anotacion;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(int formaPago) {
		this.formaPago = formaPago;
	}

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	//metodo para Guardar pagos
	public void Guardar() {
		this.consulta = "INSERT INTO pagoscreditos(credito,monto,fecha,formaPago,anotacion) VALUES(?,?,?,?,?)";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, credito);
			pst.setFloat(2, monto);
			pst.setDate(3, fecha);
			pst.setInt(4, formaPago);
			pst.setString(5, anotacion);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Pago guardado exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//metodo para eliminar pago

	public void Eliminar(int id) {
		this.consulta = "DELETE FROM pagoscreditos WHERE id = ?";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, id);
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

	public void Actualizar(String id, int credito, float monto, Date fecha, int formaPago, String anotacion) {
		this.consulta = "UPDATE pagoscreditos SET credito = ?, monto = ?, fecha = ?, formaPago = ?, anotacion = ?  WHERE id = ?";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, credito);
			pst.setFloat(2, monto);
			pst.setDate(3, fecha);
			pst.setInt(4, formaPago);
			pst.setString(5, anotacion);
			pst.setString(6, id);
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
		this.consulta = "SELECT pagoscreditos.id AS idPago, monto as montoPago, credito, pagoscreditos.fecha, clientes.nombres,apellidos, formapago.tipoVenta,pagoscreditos.anotacion FROM pagoscreditos LEFT JOIN creditos ON(pagoscreditos.credito = creditos.id) LEFT JOIN formapago ON(formapago.id=pagoscreditos.formaPago) LEFT JOIN clientes ON(creditos.cliente = clientes.id) WHERE CONCAT(pagoscreditos.id, pagoscreditos.credito, pagoscreditos.fecha, clientes.nombres, clientes.apellidos) LIKE '%" + buscar + "%'";
		this.resgistros = new String[8];
		String[] titulos = {"Id Pago", "Monto de Pago", "Al Credito", "Fecha De Pago", "Nombres Cliente", "Apellidos Cliente", "Forma Pago", "Anotación"};
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
				this.resgistros[2] = rs.getString("credito");
				this.resgistros[3] = rs.getString("fecha");
				this.resgistros[4] = rs.getString("nombres");
				this.resgistros[5] = rs.getString("apellidos");
				this.resgistros[6] = rs.getString("tipoVenta");
				this.resgistros[7] = rs.getString("anotacion");
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

	public float pagosCredito(int idCredito) {
		cn = Conexion();
		float pagos = 0;
		this.consulta = "SELECT SUM(pagoscreditos.monto) AS pago FROM pagoscreditos INNER JOIN creditos ON(pagoscreditos.credito=creditos.id)"
			+ " INNER JOIN clientes ON(creditos.cliente = clientes.id) WHERE creditos.id = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, idCredito);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				pagos = rs.getFloat("pago");//total de pagos de credito
			}
			cn.close();
		} catch (SQLException e) {
		}
		return credito;
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

	public String cliente(String id) {
		this.consulta = "select c.nombres, c.apellidos from clientes as c inner join creditos on(c.id = creditos.cliente) where creditos.id = ?";
		this.cn = Conexion();
		String nombre = "";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				nombre = rs.getString("nombres") + " " + rs.getString("apellidos");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion cliente en modelo pagodCreditos");
		}
		return nombre;
	}

	public float PagosSegunCredito(String id) {
		this.consulta = "SELECT SUM(p.monto) AS pagos FROM pagoscreditos AS p INNER JOIN creditos AS c ON(c.id = p.credito) WHERE c.id = ?";
		this.cn = Conexion();
		float monto = 0;
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				monto = rs.getFloat("pagos");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion saldo en modelo pagodCreditos");
		}
		return monto;
	}

	public float deuda(String id) {
		this.consulta = "SELECT SUM(f.totalFactura) AS deuda FROM facturas AS f INNER JOIN creditos AS c ON(f.credito=c.id) WHERE c.id= ?";
		this.cn = Conexion();
		float monto = 0;
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				monto = rs.getFloat("deuda");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion saldo en modelo pagodCreditos");
		}
		return monto;
	}
}
