/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Marca extends Conexiondb {

	private String nombre, descripcion, id;
	DefaultTableModel modelo;
	String consulta;
	PreparedStatement pst;
	Connection cn;
	ResultSet rs;

	public Marca() {
		this.cn = null;
		this.consulta = null;
		this.pst = null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void Guardar() {
		cn = Conexion();
		consulta = "INSERT INTO marca(nombre, descripcion) VALUES(?,?)";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setString(1, nombre);
			pst.setString(2, descripcion);
			int banderin = pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null,
					"Marca guardada exitosamente",
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
		this.consulta = "SELECT * FROM marca WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(consulta);
			this.pst.setString(1, id);
			this.rs = this.pst.executeQuery();
			while(rs.next()){
				this.nombre = this.rs.getString("nombre");
				this.descripcion = this.rs.getString("descripcion");
			}
			this.cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "");	
		}
	}
	
	public void Actualizar() {
		cn = Conexion();
		consulta = "UPDATE marca SET nombre=?, descripcion=? WHERE id=?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setString(1, nombre);
			pst.setString(2, descripcion);
			pst.setString(3,this.id);
			int banderin = pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Dato actualizado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void Eliminar() {
		cn = Conexion();
		consulta = "DELETE FROM marca WHERE id=?";
		try {
			pst = cn.prepareStatement(consulta);
			this.pst.setString(1, this.id);
			int banderin = pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Dato eliminado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel Consulta(String Buscar) {
		cn = Conexion();
		consulta = "SELECT * FROM marca WHERE CONCAT(nombre, descripcion) LIKE '%" + Buscar + "%'";
		String[] registros = new String[3];
		String[] titulos = {"Id", "Nombre", "Descripción"};
		modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(consulta);
			while (rs.next()) {
				registros[0] = rs.getString("id");
				registros[1] = rs.getString("nombre");
				registros[2] = rs.getString("descripcion");
				modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return modelo;
	}

}
