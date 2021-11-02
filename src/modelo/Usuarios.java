package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Usuarios extends Conexiondb {
	private int id;
	private String nombre,
		password,
		permiso;
	DefaultTableModel modelo;
	Connection cn;
	PreparedStatement pst;
	String consulta;
	String[] resgistros;
	int banderin;

	public Usuarios() {
		this.pst = null;
		this.cn = null;
		this.consulta = null;
		this.banderin = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public void Guardar() {
		this.consulta = "INSERT INTO usuarios(nombreUsuario, password, permiso) VALUES(?,?,?)";
		cn = Conexion();
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, nombre);
			pst.setString(2, password);
			pst.setString(3, permiso);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//metodo para Actualizar Usuarios

	public void Actualizar() {
		cn = Conexion();
		this.consulta = "UPDATE usuarios SET nombreUsuario = ?, password = ?, permiso = ? WHERE id = " + id;
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setString(1, nombre);
			pst.setString(2, password);
			pst.setString(3, permiso);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//metodo para Eliminar usuarios

	public void Eliminar() {
		cn = Conexion();
		this.consulta = "DELETE FROM usuarios WHERE id = ?";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			this.banderin = pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(
					null,
					"Usuario eliminado exitosamente",
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
		this.consulta = "SELECT * FROM usuarios WHERE id = ?";
		  try {
			  this.pst = this.cn.prepareStatement(this.consulta);
			  this.pst.setInt(1, this.id);
			  ResultSet rs = this.pst.executeQuery();
			  while(rs.next()){
				this.nombre = rs.getString("nombreUsuario");
				this.password = rs.getString("password");
				this.permiso = rs.getString("permiso");
			  }
			  this.cn.close();
		  } catch (SQLException e) {
			  JOptionPane.showMessageDialog(null,e);
		  }
	  }
	//metodo para Mostrar Usuarios

	public DefaultTableModel Mostrar(String buscar) {
		cn = Conexion();
		this.consulta = "SELECT * FROM usuarios WHERE CONCAT(nombreUsuario, permiso) LIKE '%" + buscar + "%'";
		String[] titulos = {"Id", "Nombre de Usuario", "Contraseña", "Permisos"};
		this.resgistros = new String[4];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		try {
			Statement st = this.cn.createStatement();
			ResultSet rs = st.executeQuery(this.consulta);
			while (rs.next()) {
				this.resgistros[0] = rs.getString("id");
				this.resgistros[1] = rs.getString("nombreUsuario");
				this.resgistros[2] = rs.getString("password");
				this.resgistros[3] = rs.getString("permiso");
				this.modelo.addRow(this.resgistros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modelo;
	}
}
