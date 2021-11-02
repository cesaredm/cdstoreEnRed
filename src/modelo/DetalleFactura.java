/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class DetalleFactura extends Conexiondb {

	private int factura,
		productoDetalle;
	private float precio,
		cantidad,
		importeDetalle;
	PreparedStatement pst;
	Connection cn;
	int banderin;
	String consulta;

	public DetalleFactura() {

	}

	public int getFactura() {
		return factura;
	}

	public void setFactura(int factura) {
		this.factura = factura;
	}

	public int getProductoDetalle() {
		return productoDetalle;
	}

	public void setProductoDetalle(int productoDetalle) {
		this.productoDetalle = productoDetalle;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public float getImporteDetalle() {
		return importeDetalle;
	}

	public void setImporteDetalle(float importeDetalle) {
		this.importeDetalle = importeDetalle;
	}
	
	public void DetalleFactura() {
		cn = Conexion();
		this.consulta = "INSERT INTO detalleFactura(factura, producto, precioProducto, cantidadProducto, totalVenta) VALUES(?,?,?,?,?)";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setInt(1, this.factura);
			pst.setInt(2, this.productoDetalle);
			pst.setFloat(3, precio);
			pst.setFloat(4, cantidad);
			pst.setFloat(5, this.importeDetalle);
			this.banderin = pst.executeUpdate();
			if (banderin > 0) {
				//JOptionPane.showMessageDialog(null, "detalle guardado");
			}
			cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "ERROR : en el metodo DetalleFactura en el modelo DetalleFactura");
		}
	}
}
