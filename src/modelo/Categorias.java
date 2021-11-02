/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Categorias extends Conexiondb {

	private String nombre,
		descripcion,
		id;
	DefaultTableModel modelo;
	Connection cn;
	PreparedStatement pst;
	ResultSet rs;
	String consulta;

	public Categorias() {
		cn = null;
		pst = null;
		consulta = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void Guardar() {
		cn = Conexion();
		consulta = "INSERT INTO categorias(nombre, descripcion) VALUES(?,?)";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setString(1, this.nombre);
			pst.setString(2, this.descripcion);
			int banderin = pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Categoria creada exitosamente");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void editar() {
		cn = Conexion();
		consulta = "SELECT * FROM categorias WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.id);
			this.rs = this.pst.executeQuery();
			while (rs.next()) {
				this.nombre = this.rs.getString("nombre");
				this.descripcion = this.rs.getString("descripcion");
			}
				cn.close();
			}catch (SQLException ex) {
				Logger.getLogger(Categorias.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	

	public void Actualizar() {
		cn = Conexion();
		consulta = "UPDATE categorias SET nombre=?, descripcion=? WHERE id=?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setString(1, this.nombre);
			pst.setString(2, this.descripcion);
			pst.setString(3, this.id);
			int banderin = pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Dato actualizado exitosamente");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void Eliminar() {
		cn = Conexion();
		consulta = "DELETE FROM categorias WHERE id=?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setString(1, this.id);
			int banderin = pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Dato borrado exitosamente");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel Consulta(String Buscar) {
		cn = Conexion();
		consulta = "SELECT * FROM categorias WHERE CONCAT(id, nombre, descripcion) LIKE '%" + Buscar + "%'";
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
