package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Transacciones extends Conexiondb {

	private float monto;
	private Date fecha;
	private String Descripcion,
		TipoTrans,
		nombreCaja,
		moneda;
	private int idCaja,
		id;
	DefaultTableModel modelo;
	Connection cn;
	PreparedStatement pst;
	ResultSet rs;
	String consulta;
	String[] resgistros;
	DefaultComboBoxModel combo;
	int banderin;

	public Transacciones() {
		this.cn = null;
		this.pst = null;
		this.consulta = "";
		this.banderin = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.Descripcion = Descripcion;
	}

	public String getTipoTrans() {
		return TipoTrans;
	}

	public void setTipoTrans(String TipoTrans) {
		this.TipoTrans = TipoTrans;
	}

	public String getNombreCaja() {
		return nombreCaja;
	}

	public void setNombreCaja(String nombreCaja) {
		this.nombreCaja = nombreCaja;
	}

	public int getIdCaja() {
		return idCaja;
	}

	public void setIdCaja(int idCaja) {
		this.idCaja = idCaja;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public void Guardar() {
		cn = Conexion();
		this.consulta = "INSERT INTO transaccion(monto, fecha, descripcion, tipoTransaccion, caja, moneda) VALUES(?,?,?,?,?,?)";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setFloat(1, this.monto);
			pst.setDate(2, this.fecha);
			pst.setString(3, this.Descripcion);
			pst.setString(4, this.TipoTrans);
			pst.setInt(5, this.idCaja);
			pst.setString(6,this.moneda);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Gasto guardado exitosamente", "Infromacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void editar() {
		this.cn = Conexion();
		this.consulta = "SELECT t.*, c.caja AS ncaja FROM transaccion AS t INNER JOIN cajas AS c ON(t.caja=c.id) WHERE t.id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.TipoTrans = this.rs.getString("tipoTransaccion");
				this.fecha = this.rs.getDate("fecha");
				this.Descripcion = this.rs.getString("descripcion");
				this.monto = this.rs.getFloat("monto");
				this.nombreCaja = this.rs.getString("ncaja");
				this.moneda = this.rs.getString("moneda");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.closeConnection();
		}
	}

	public void closeConnection() {
		try {
			this.cn.close();
		} catch (SQLException ex) {
			Logger.getLogger(Transacciones.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void Actualizar() {
		cn = Conexion();
		this.consulta = "UPDATE transaccion SET tipoTransaccion = ?, monto = ?, caja = ?, fecha = ?, descripcion = ?, moneda = ? WHERE id = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, this.TipoTrans);
			pst.setFloat(2, monto);
			pst.setInt(3, this.idCaja);
			pst.setDate(4, fecha);
			pst.setString(5, Descripcion);
			pst.setString(6,this.moneda);
			pst.setInt(7, id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Gasto actualizado exitosamente", "Infromacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void Eliminar() {
		cn = Conexion();
		this.consulta = "DELETE FROM transaccion WHERE id = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, this.id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Gasto borrado exitosamente", "Infromacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel Mostrar(String Buscar) {
		cn = Conexion();
		this.resgistros = new String[7];
		String[] titulos = {"Id Transac.", "Tipo Transac", "Caja", "Monto", "Moneda", "Fecha", "Descripción"};
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		this.consulta = "SELECT transaccion.id,transaccion.tipoTransaccion,cajas.caja,transaccion.monto,moneda,transaccion.fecha,transaccion.descripcion FROM transaccion INNER JOIN cajas ON(transaccion.caja=cajas.id) WHERE CONCAT(transaccion.id, transaccion.monto, transaccion.fecha, cajas.caja, transaccion.tipoTransaccion) LIKE '%" + Buscar + "%'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.resgistros[0] = rs.getString("id");
				this.resgistros[1] = rs.getString("tipoTransaccion");
				this.resgistros[2] = rs.getString("caja");
				this.resgistros[3] = rs.getString("Monto");
				this.resgistros[4] = rs.getString("moneda");
				this.resgistros[5] = rs.getString("Fecha");
				this.resgistros[6] = rs.getString("descripcion");
				this.modelo.addRow(this.resgistros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modelo;
	}

	public int IdCaja(String caja) {
		int id = 0;
		cn = Conexion();
		this.consulta = "SELECT id FROM cajas WHERE caja = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, caja);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion idCaja");
		}
		return id;
	}

	public DefaultComboBoxModel mostrarCajas() {
		this.combo = new DefaultComboBoxModel();
		cn = Conexion();
		this.consulta = "SELECT caja FROM cajas";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.combo.addElement(rs.getString("caja"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion idCaja");
		}
		return this.combo;
	}
}
