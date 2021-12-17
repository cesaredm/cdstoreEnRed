/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import javax.swing.JOptionPane;
import vista.IMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class InfoFactura extends Conexiondb {

	private String nombre, direccion, rfc, rangoInicio, rangoFinal, telefono, anotaciones;
	private final int id = 1;
	Connection cn;
	PreparedStatement pst;
	String consulta;

	public InfoFactura() {
		this.cn = null;
		this.pst = null;
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

	public void updateInfoFactura() {
		this.cn = Conexion();
		this.consulta = "UPDATE infoFactura SET nombre = ?, telefono = ?, direccion = ?, RFC = ?, inicioRango = ?, finalRango = ?, anotaciones = ? WHERE id = ?";

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

}
