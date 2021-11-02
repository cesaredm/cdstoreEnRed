/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelo.Usuarios;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlUsuarios implements ActionListener, CaretListener {

	int id;
	String nombre,
		password,
		permiso;
	IMenu menu;
	Usuarios usuarios;
	DefaultTableModel modelo;

	public CtrlUsuarios(IMenu menu, Usuarios usuarios) {
		modelo = new DefaultTableModel();
		this.menu = menu;
		this.usuarios = usuarios;
		this.menu.btnGuardarUsuario.addActionListener(this);
		this.menu.btnActualizarUsuario.addActionListener(this);
		this.menu.btnNuevoUsuario.addActionListener(this);
		this.menu.mnEditarUsuarios.addActionListener(this);
		this.menu.mnBorrarUsuario.addActionListener(this);
		this.menu.txtBuscarUsuario.addCaretListener(this);
		MostrarUsuarios("");
		DeshabilitarUsuarios();
	}

	public boolean validar() {
		boolean validar = true;
		nombre = menu.txtNombreUsuario.getText();
		password = menu.txtPasswordUsuario.getText();
		permiso = menu.cmbPermisoUsuario.getSelectedItem().toString();
		if (this.nombre.equals("")) {
			validar = false;
		} else if (this.password.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (e.getSource() == menu.btnGuardarUsuario) {
			if (validar()) {
				this.usuarios.setNombre(nombre);
				this.usuarios.setPassword(password);
				this.usuarios.setPermiso(permiso);
				this.usuarios.Guardar();
				MostrarUsuarios("");
				LimpiarUsuarios();
				menu.btnGuardarUsuario.setEnabled(true);
				menu.btnActualizarUsuario.setEnabled(false);
			}
		}
		if (e.getSource() == menu.btnActualizarUsuario) {
			if (validar()) {
				this.usuarios.setNombre(nombre);
				this.usuarios.setPassword(password);
				this.usuarios.setPermiso(permiso);
				this.usuarios.Actualizar();
				MostrarUsuarios("");
				LimpiarUsuarios();
				menu.btnGuardarUsuario.setEnabled(true);
				menu.btnActualizarUsuario.setEnabled(false);
			}

		}
		if (e.getSource() == menu.btnNuevoUsuario) {
			HabilitarUsuarios();
			LimpiarUsuarios();
		}
		if (e.getSource() == menu.mnEditarUsuarios) {
			int filaseleccionada = menu.tblUsuarios.getSelectedRow();
			try {
				this.modelo = (DefaultTableModel) menu.tblUsuarios.getModel();
				if (filaseleccionada == -1) {

				} else {
					id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
					this.usuarios.setId(this.id);
					this.usuarios.editar();
					LimpiarUsuarios();
					HabilitarUsuarios();
					menu.txtNombreUsuario.setText(this.usuarios.getNombre());
					menu.txtPasswordUsuario.setText(this.usuarios.getPassword());
					menu.cmbPermisoUsuario.setSelectedItem(this.usuarios.getPermiso());

					menu.btnGuardarUsuario.setEnabled(false);
					menu.btnActualizarUsuario.setEnabled(true);

				}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err);
			}

		}
		if (e.getSource() == menu.mnBorrarUsuario) {
			int filaseleccionada = menu.tblUsuarios.getSelectedRow(), id;

			try {
				this.modelo = (DefaultTableModel) menu.tblUsuarios.getModel();
				if (filaseleccionada != -1) {
					int confirmar = JOptionPane.showConfirmDialog(
						null,
						"Seguro Que Quieres Borra Este Usuario",
						"Advertencia",
						JOptionPane.OK_CANCEL_OPTION
					);
					if (confirmar == JOptionPane.YES_OPTION) {
						id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
						this.usuarios.setId(this.id);
						this.usuarios.Eliminar();
						MostrarUsuarios("");
					}
				} else {

				}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err);
			}
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (e.getSource() == menu.txtBuscarUsuario) {
			MostrarUsuarios(menu.txtBuscarUsuario.getText());
		}
	}

	public void MostrarUsuarios(String buscar) {
		menu.tblUsuarios.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblUsuarios.getTableHeader().setOpaque(false);
		menu.tblUsuarios.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblUsuarios.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblUsuarios.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		menu.tblUsuarios.setModel(this.usuarios.Mostrar(buscar));
	}

	public void LimpiarUsuarios() {
		menu.txtNombreUsuario.setText("");
		menu.txtPasswordUsuario.setText("");
	}
	//metodo para Habilitar los elementos inabilitados por el metodo DeshabilitarUsuarios

	public void HabilitarUsuarios() {
		menu.txtNombreUsuario.setEnabled(true);
		menu.txtPasswordUsuario.setEnabled(true);
		menu.cmbPermisoUsuario.setEnabled(true);
		menu.btnGuardarUsuario.setEnabled(true);
		menu.btnActualizarUsuario.setEnabled(false);
	}
	//metodo para dehabilitar elementos de formulario Usuario

	public void DeshabilitarUsuarios() {
		menu.txtNombreUsuario.setEnabled(false);
		menu.txtPasswordUsuario.setEnabled(false);
		menu.cmbPermisoUsuario.setEnabled(false);
		menu.btnGuardarUsuario.setEnabled(false);
		menu.btnActualizarUsuario.setEnabled(false);
	}
}
