package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlCategoria implements ActionListener, CaretListener {

	String nombre,
		descripcion,
		id;
	IMenu menu;
	Categorias categorias;
	DefaultTableModel modelo;

	public CtrlCategoria(IMenu menu, Categorias C) {
		this.menu = menu;
		this.categorias = C;
		MostrarCategorias("");
		DeshabilitarCategoria();
		this.menu.btnGuardarCategoria.addActionListener(this);
		this.menu.btnActualizarCategoria.addActionListener(this);
		this.menu.btnNuevoCategoria.addActionListener(this);
		this.menu.EditarCategoria.addActionListener(this);
		this.menu.BorrarCategoria.addActionListener(this);
		this.menu.txtBuscarCategoria.addCaretListener(this);
		this.modelo = new DefaultTableModel();
		this.id = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.btnGuardarCategoria) {
			this.guardar();
		}

		if (e.getSource() == menu.btnActualizarCategoria) {
			this.actualizar();
		}
		if (e.getSource() == menu.btnNuevoCategoria) {
			this.HabilitarCategoria();
			this.LimpiarCategoria();
		}
		if (e.getSource() == menu.EditarCategoria) {
			this.editar();
		}
		if (e.getSource() == menu.BorrarCategoria) {
			this.eliminar();
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == menu.txtBuscarCategoria) {
			MostrarCategorias(menu.txtBuscarCategoria.getText());
		}
	}

	public void guardar() {
		if (validar()) {
			this.categorias.setNombre(nombre);
			this.categorias.setDescripcion(descripcion);
			categorias.Guardar();
			MostrarCategorias("");
			LimpiarCategoria();
		} else {

		}
	}

	public void editar() {
		int filaseleccionada;
		String nombre, descripcion;
		try {
			filaseleccionada = menu.tblCategorias.getSelectedRow();
			if (filaseleccionada != -1) {
				HabilitarCategoria();
				LimpiarCategoria();
				modelo = (DefaultTableModel) menu.tblCategorias.getModel();
				this.id = (String) modelo.getValueAt(filaseleccionada, 0);
				this.categorias.setId(id);
				this.categorias.editar();
				menu.txtNombreCategoria.setText(this.categorias.getNombre());
				menu.txtDescripcionCategoria.setText(this.categorias.getDescripcion());
				menu.btnGuardarCategoria.setEnabled(false);
				menu.btnActualizarCategoria.setEnabled(true);
			} else {

			}
		} catch (Exception err) {

		}
	}

	public void actualizar() {
		if (validar()) {
			this.categorias.setNombre(nombre);
			this.categorias.setDescripcion(descripcion);
			categorias.Actualizar();
			MostrarCategorias("");
			LimpiarCategoria();
			menu.btnGuardarCategoria.setEnabled(true);
			menu.btnActualizarCategoria.setEnabled(false);
		} else {

		}
	}

	public void eliminar() {
		int filaseleccionada;
		try {
			filaseleccionada = menu.tblCategorias.getSelectedRow();
			if (filaseleccionada != -1) {

			} else {
				int confirmar = JOptionPane.showConfirmDialog(
					null,
					"Seguro que quieres borrar esta categoria",
					"Avertencia",
					JOptionPane.OK_CANCEL_OPTION
				);
				if (confirmar == JOptionPane.YES_OPTION) {
					modelo = (DefaultTableModel) menu.tblCategorias.getModel();
					id = (String) modelo.getValueAt(filaseleccionada, 0);
					this.categorias.setId(id);
					categorias.Eliminar();
					MostrarCategorias("");
				}
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion Borrar Categoria");
		}
	}

	public boolean validar() {
		boolean validar = true;
		nombre = menu.txtNombreCategoria.getText();
		descripcion = menu.txtDescripcionCategoria.getText();
		if (nombre.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	//metodo para llenar la tabla categorias del formulario Categorias
	public void MostrarCategorias(String Buscar) {
		menu.tblCategorias.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblCategorias.getTableHeader().setOpaque(false);
		menu.tblCategorias.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblCategorias.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblCategorias.setModel(categorias.Consulta(Buscar));
	}

	//metodo para limpiar el formulario categoria
	public void LimpiarCategoria() {
		menu.txtNombreCategoria.setText("");
		menu.txtDescripcionCategoria.setText("");
	}

	//metodo para Habilitar los elementos inabilitados por el metodo DeshabilitarCategoria
	public void HabilitarCategoria() {
		menu.txtNombreCategoria.setEnabled(true);
		menu.txtDescripcionCategoria.setEnabled(true);
		menu.btnNuevoCategoria.setEnabled(true);
		menu.btnGuardarCategoria.setEnabled(true);
		menu.btnActualizarCategoria.setEnabled(false);
	}

	//metodo para dehabilitar elementos de formulario Categoria
	public void DeshabilitarCategoria() {
		menu.txtNombreCategoria.setEnabled(false);
		menu.txtDescripcionCategoria.setEnabled(false);
		menu.btnNuevoCategoria.setEnabled(true);
		menu.btnGuardarCategoria.setEnabled(false);
		menu.btnActualizarCategoria.setEnabled(false);
	}

	public void hello() {
		System.out.println("hello word..!");
	}
}
