/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Date;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CierresModel extends Conexiondb {

	String consulta;
	Connection cn;
	PreparedStatement pst;
	int banderin;
	private float cordobas,
		dolar,
		tasaCompra,
		tasaVenta;
	private String descripcion, user;
	private Date fecha;
	private int caja;

	public float getCordobas() {
		return cordobas;
	}

	public void setCordobas(float cordobas) {
		this.cordobas = cordobas;
	}

	public float getDolar() {
		return dolar;
	}

	public void setDolar(float dolar) {
		this.dolar = dolar;
	}

	public float getTasaCompra() {
		return tasaCompra;
	}

	public void setTasaCompra(float tasaCompra) {
		this.tasaCompra = tasaCompra;
	}

	public float getTasaVenta() {
		return tasaVenta;
	}

	public void setTasaVenta(float tasaVenta) {
		this.tasaVenta = tasaVenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCaja() {
		return caja;
	}

	public void setCaja(int caja) {
		this.caja = caja;
	}

	public void guardarCierres() {
		this.cn = Conexion();
		this.consulta = "INSERT INTO cierres(fecha,caja,cordobas,dolares,usuario,descripcion,tasaVenta,tasaCompra) VALUES(?,?,?,?,?,?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha);
			this.pst.setInt(2, this.caja);
			this.pst.setFloat(3, this.cordobas);
			this.pst.setFloat(4, this.dolar);
			this.pst.setString(5, this.user);
			this.pst.setString(6, this.descripcion);
			this.pst.setFloat(7, this.tasaVenta);
			this.pst.setFloat(8, this.tasaCompra);
			System.out.println(this.dolar);
			//this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Cierre realizado con exito.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
