/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.IMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Configuraciones extends Conexiondb {

	/* ------------- DATOS PARA LA CONFIGUIRACION DE DATOS DE FACTURA -------------*/
	private String nombre, direccion, rfc, rangoInicio, rangoFinal, telefono, anotaciones;
	private final int id = 1;
	/* ------------- DATOS PARA GRESTIONAR IPS ------------- */
	private int idIp;
	private String ip,
		tipoOrdenador;
	public static boolean multiconexion;
	public static String[] listIpsCliente;
	/* ------------ CONFIURACION DE IMPRESORAS ----------- */
	public static String impresora;
	/* ----------- OBJETOS PARA MANIPULACION DE DATOS MYSQL ---------- */
	int banderin;
	public DefaultTableModel tableModel;
	Connection cn;
	PreparedStatement pst;
	Statement st;
	ResultSet rs;
	String consulta;

	public Configuraciones() {
		this.cn = null;
		this.pst = null;
		this.rs = null;
		this.consulta = "";
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRangoInicio() {
		return rangoInicio;
	}

	public void setRangoInicio(String rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	public int getId() {
		return id;
	}

	public String getRangoFinal() {
		return rangoFinal;
	}

	public void setRangoFinal(String rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTipoOrdenador() {
		return tipoOrdenador;
	}

	public void setTipoOrdenador(String tipoOrdenador) {
		this.tipoOrdenador = tipoOrdenador;
	}

	public int getIdIp() {
		return idIp;
	}

	public void setIdIp(int idIp) {
		this.idIp = idIp;
	}

	/* ACTUALIZAR DATOS DE FACTURA */
	public void updateInfoFactura() {
		this.cn = Conexion();
		this.consulta = "UPDATE infoFactura SET nombre = ?, telefono = ?, direccion = ?, RFC = ?, inicioRango = ?, finalRango = ?,"
			+ " anotaciones = ? WHERE id = ?";

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, nombre);
			this.pst.setString(2, telefono);
			this.pst.setString(3, direccion);
			this.pst.setString(4, rfc);
			this.pst.setString(5, rangoInicio);
			this.pst.setString(6, rangoFinal);
			this.pst.setString(7, anotaciones);
			this.pst.setInt(8, id);
			int banderin = this.pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Datos de factura Actualizados Correctamente.");
			}
			this.cn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	/* OBTENER DATOS DE FACTURA */
	public void obtenerInfoFactura() {
		this.cn = Conexion();
		this.consulta = "SELECT * FROM infoFactura";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.nombre = rs.getString("nombre");
				this.telefono = rs.getString("telefono");
				this.direccion = rs.getString("direccion");
				this.rfc = rs.getString("RFC");
				this.rangoInicio = rs.getString("inicioRango");
				this.rangoFinal = rs.getString("finalRango");
				this.anotaciones = rs.getString("anotaciones");
			}
			this.cn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	/* ESTABLECER IMPRESORA A UTILIZAR EL SISTEMA */
	public void updateImpresora() {
		this.cn = Conexion();
		this.consulta = "UPDATE impresoras SET impresora = ? WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.impresora);
			this.pst.setInt(2, this.id);
			int banderin = this.pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Impresora " + this.impresora + " Establecida con exito.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* OBTENER LA IMPRESORA ESTABLECIDA */
	public void getImpresora() {
		this.cn = Conexion();
		this.consulta = "SELECT impresora FROM impresoras WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.impresora = this.rs.getString("impresora");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en el metodo Impresora en el modelo Configuraciones");
			e.printStackTrace();
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* GUARDAR IPS DE ORDENADORES PARA LA MILTICONEXION */
	public void guardarIps() {
		this.cn = Conexion();
		this.consulta = "INSERT INTO ips(ipDireccion,tipoOrdenador) VALUES(?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.ip);
			this.pst.setString(2, this.tipoOrdenador);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "IP guardada con exito.", "Información", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* CREAR USUARIO Y PERMISOS PARA CONECTARME A SERVIDOR MARIADB DESDE LAS APPS CLIENTE */
	public void crearUsuarioDB() {
		this.cn = Conexion();
		this.consulta = "GRANT INSERT,SELECT,DELETE,UPDATE,EXECUTE ON cdstorered.* TO 'clienteCDstore'@'" + this.ip + "' IDENTIFIED BY '19199697tsoCD'";
		try {
			this.st = this.cn.createStatement();
			this.st.execute(this.consulta);
			this.st = this.cn.createStatement();
			this.st.execute("GRANT SELECT ON mysql.proc TO 'clienteCDstore'@'" + this.ip + "'");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo crearUsuarioBD en modelo Configuraciones");
		} finally {
			try {
				this.cn.close();
				this.flushPrivileges();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* ACTUALIZAR PRIVILEGES */
	public void flushPrivileges() {
		this.cn = Conexion();
		this.consulta = "FLUSH PRIVILEGES";
		try {
			this.st = this.cn.createStatement();
			this.st.execute(this.consulta);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo flushPrivileges en modelo Configuraciones");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* ELIMINAR USUARIO PARA ACCESO A SERVIDOR MARIADB */
	public void eliminarUsuarioDB() {
		this.cn = Conexion();
		this.consulta = "DELETE FROM mysql.user WHERE host = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.ip);
			this.pst.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo eliminarUsuarioDB en modelo Configuraciones");
		} finally {
			try {
				this.cn.close();
				this.flushPrivileges();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* ACTUALIZAR USUARIO DE SEVIDOR MARIADB */
	public void actualizarUsuarioDB() {
		this.cn = Conexion();
		this.consulta = "UPDATE mysql.user SET host=? WHERE host = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.ip);
			this.pst.setString(2, this.ip);
			this.pst.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo eliminarUsuarioDB en modelo Configuraciones");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* MOSTRAR LAS IPS GUARDADAS */
	public void mostrarIps() {
		this.cn = Conexion();
		this.consulta = "SELECT * FROM ips";
		String[] titulos = {"ID", "Tipo", "Direccion IP"};
		String[] datos = new String[3];
		this.tableModel = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.st = this.cn.createStatement();
			this.rs = this.st.executeQuery(this.consulta);
			while (this.rs.next()) {
				datos[0] = this.rs.getString("id");
				datos[1] = this.rs.getString("tipoOrdenador");
				datos[2] = this.rs.getString("ipDireccion");
				this.tableModel.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo mostrarIps en modelo configuraciones.");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* OBTENER DATOS DE ORDENADOR PARA LA EDICION */
	public void getIpOrdenador() {
		this.cn = Conexion();
		this.consulta = "SELECT * FROM ips WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.idIp);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.idIp = this.rs.getInt("id");
				this.tipoOrdenador = this.rs.getString("tipoOrdenador");
				this.ip = this.rs.getString("ipDireccion");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo getIpOrdenador en modelo configuraciones.");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* ELIMINAR ORDENADOR */
	public void eliminarIp() {
		this.cn = Conexion();
		this.consulta = "DELETE FROM ips WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, idIp);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Ip eliminado con exito.");
			}
		} catch (Exception e) {
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* OBTENER LAS DIRECCIONES IP DE LOS ORDENADORES CLIENTE PARA EL ENVIO DE DATOS */
	public void getIpsOrdenadoresCliente() {
		int cont = 0;
		this.cn = Conexion();
		this.consulta = "SELECT ipDireccion AS ip FROM ips WHERE tipoOrdenador = 'Cliente'";
		try {
			this.st = this.cn.createStatement();
			this.rs = this.st.executeQuery(this.consulta);
			while (this.rs.next()) {
				cont++;
			}
			this.listIpsCliente = new String[cont];
			cont = 0;
			this.rs.first();
			while (this.rs.next()) {
				cont++;
				this.listIpsCliente[cont] = this.rs.getString("ip");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* SABER SI SE USARA MULTICONEXION  */
	public void getMulticonexion() {
		this.cn = Conexion();
		this.consulta = "SELECT accion FROM config WHERE tipoDeConfig = 'multiconexion'";
		try {
			this.st = this.cn.createStatement();
			this.rs = this.st.executeQuery(this.consulta);
			while (this.rs.next()) {
				if (this.rs.getString("accion").equals("si")) {
					this.multiconexion = true;
				} else if (this.rs.getString("accion").equals("no")) {
					this.multiconexion = false;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo getMultiConexion en el modelo Configuraciones");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/* ESTABLECER MULTICONEXION */
	public void setMulticonexion(String accion) {
		this.cn = Conexion();
		this.consulta = "UPDATE config SET accion=? WHERE tipoDeConfig = 'multiconexion'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, accion);
			this.pst.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo setMulticxonexion en el modelo Configuraciones");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
