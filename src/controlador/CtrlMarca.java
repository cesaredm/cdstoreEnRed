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

public class CtrlMarca implements ActionListener, CaretListener {

	String id, nombre, descripcion;
	IMenu menu;
	Marca marca;
	DefaultTableModel modelo;

	public CtrlMarca(IMenu menu, Marca L) {
		this.menu = menu;
		this.marca = L;
		this.modelo = new DefaultTableModel();
		MostrarMarca("");
		DeshabilitarMarca();
		this.menu.btnGuardarLaborotorio.addActionListener(this);
		this.menu.btnActualizarLaboratorio.addActionListener(this);
		this.menu.btnNuevoLaboratorio.addActionListener(this);
		this.menu.EditarLaboratorio.addActionListener(this);
		this.menu.BorrarLaboratorio.addActionListener(this);
		this.menu.txtBuscarLaboratorio.addCaretListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.btnGuardarLaborotorio) {
			this.guardar();
		}
		if (e.getSource() == menu.btnActualizarLaboratorio) {
			this.actualizar();
		}
		if (e.getSource() == menu.btnNuevoLaboratorio) {
			HabilitarMarca();
		}
		if (e.getSource() == menu.EditarLaboratorio) {
			this.editar();
		}
		if (e.getSource() == menu.BorrarLaboratorio) {

		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == menu.txtBuscarLaboratorio) {
			MostrarMarca(menu.txtBuscarLaboratorio.getText());
		}
	}

	public void guardar() {
		if (validar()) {
			this.marca.setNombre(this.nombre);
			this.marca.setDescripcion(this.descripcion);
			marca.Guardar();
			MostrarMarca("");
			LimpiarMarca();
		} else {

		}
	}

	public void editar() {
		int filaseleccionada;
		try {
			filaseleccionada = menu.tblLaboratorio.getSelectedRow();
			if (filaseleccionada != -1) {
				modelo = (DefaultTableModel) menu.tblLaboratorio.getModel();
				id = (String) modelo.getValueAt(filaseleccionada, 0);
				this.marca.setId(id);
				this.marca.editar();
				HabilitarMarca();
				LimpiarMarca();
				menu.txtNombreLaboratorio.setText(this.marca.getNombre());
				menu.txtDescripcionLaboratorio.setText(this.marca.getDescripcion());
				menu.btnActualizarLaboratorio.setEnabled(true);
				menu.btnGuardarLaborotorio.setEnabled(false);
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}
	}

	public void actualizar() {
		if (validar()) {
			this.marca.setNombre(nombre);
			this.marca.setDescripcion(descripcion);
			marca.Actualizar();
			MostrarMarca("");
			LimpiarMarca();
			menu.btnActualizarLaboratorio.setEnabled(false);
			menu.btnGuardarLaborotorio.setEnabled(true);
		} else {

		}
	}

	public void eliminar() {
		int filaseleccionada;
		try {
			filaseleccionada = menu.tblLaboratorio.getSelectedRow();
			if (filaseleccionada != -1) {
				int confirmar = JOptionPane.showConfirmDialog(
					null,
					"Seguro que Quieres Borrar este Marca",
					"Advertencia",
					JOptionPane.OK_CANCEL_OPTION
				);
				if (confirmar == JOptionPane.YES_OPTION) {
					modelo = (DefaultTableModel) menu.tblLaboratorio.getModel();
					id = (String) modelo.getValueAt(filaseleccionada, 0);
					this.marca.setId(id);
					marca.Eliminar();
					MostrarMarca("");
				}
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}
	}

	public boolean validar() {
		boolean validar = true;
		nombre = menu.txtNombreLaboratorio.getText();
		descripcion = menu.txtDescripcionLaboratorio.getText();
		if (nombre.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	//metodo para llenar la tabla de laboratorios con filtro por Nombre de marca
	public void MostrarMarca(String Buscar) {
		menu.tblLaboratorio.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblLaboratorio.getTableHeader().setOpaque(false);
		menu.tblLaboratorio.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblLaboratorio.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblLaboratorio.setModel(marca.Consulta(Buscar));
	}

	//metodo para limpiar campos de El formulario Laboratorio
	public void LimpiarMarca() {
		menu.txtNombreLaboratorio.setText("");
		menu.txtDescripcionLaboratorio.setText("");
	}
	//metodo para Habilitar los elementos inabilitados por el metodo DeshabilitarMarca

	public void HabilitarMarca() {
		menu.txtNombreLaboratorio.setEnabled(true);
		menu.txtDescripcionLaboratorio.setEnabled(true);
		menu.btnNuevoLaboratorio.setEnabled(true);
		menu.btnGuardarLaborotorio.setEnabled(true);
		menu.btnActualizarLaboratorio.setEnabled(false);
	}
	//metodo para dehabilitar elementos de formulario Laboratorio

	public void DeshabilitarMarca() {
		menu.txtNombreLaboratorio.setEnabled(false);
		menu.txtDescripcionLaboratorio.setEnabled(false);
		menu.btnNuevoLaboratorio.setEnabled(true);
		menu.btnGuardarLaborotorio.setEnabled(false);
		menu.btnActualizarLaboratorio.setEnabled(false);
	}
}
