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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import modelo.*;
import vista.IMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlClientes implements ActionListener, CaretListener {

	String nombres = "", apellidos = "", telefono = "", direccion = "", id;
	IMenu menu;
	Clientes clientes;
	Creditos creditos;
	DefaultTableModel modelo;

	public CtrlClientes(IMenu menu, Clientes clientes) {
		this.menu = menu;
		this.clientes = clientes;
		this.creditos = new Creditos();
		this.modelo = new DefaultTableModel();
		this.menu.btnGuardarCliente.addActionListener(this);
		this.menu.btnActualizarCliente.addActionListener(this);
		this.menu.btnNuevorClientes.addActionListener(this);
		this.menu.EditarCliente.addActionListener(this);
		this.menu.BorrarCliente.addActionListener(this);
		this.menu.txtBuscarCliente.addCaretListener(this);
		this.menu.txtBuscar.addCaretListener(this);
		MostrarClientes("");
		MostrarClienteCredito("");
		Deshabilitar();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.btnGuardarCliente) {
			this.guardar();
		}
		if (e.getSource() == menu.btnActualizarCliente) {
			this.actualizar();
		}
		if (e.getSource() == menu.btnNuevorClientes) {
			Limpiar();
			Habilitar();
		}
		if (e.getSource() == menu.BorrarCliente) {
			this.eliminar();
		}
		if (e.getSource() == menu.EditarCliente) {
			this.editar();
		}
	}

	public void guardar() {
		if (this.validar()) {
			this.clientes.setNombres(nombres);
			this.clientes.setApellidos(apellidos);
			this.clientes.setTelefono(telefono);
			this.clientes.setDireccion(direccion);
			clientes.Guardar();
			MostrarClientes("");
			MostrarClienteCredito("");
			Limpiar();
		} else {

		}
	}

	public void editar() {
		int filaSeleccionada = 0;
		try {
			filaSeleccionada = menu.tblClientes.getSelectedRow();
			if (filaSeleccionada != -1) {
				Habilitar();
				Limpiar();
				modelo = (DefaultTableModel) menu.tblClientes.getModel();
				id = (String) modelo.getValueAt(filaSeleccionada, 0);
				this.clientes.setId(id);
				this.clientes.editar();
				menu.txtNombresCliente.setText(this.clientes.getNombres());
				menu.txtApellidosCliente.setText(this.clientes.getApellidos());
				menu.txtTelefonoCliente.setText(this.clientes.getTelefono());
				menu.txtDireccionCliente.setText(this.clientes.getDireccion());
				menu.btnGuardarCliente.setEnabled(false);
				menu.btnActualizarCliente.setEnabled(true);
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion de editar cliente");
		}
	}

	public void actualizar() {
		if (this.validar()) {
			this.clientes.setNombres(nombres);
			this.clientes.setApellidos(apellidos);
			this.clientes.setTelefono(telefono);
			this.clientes.setDireccion(direccion);
			clientes.Actualizar();
			MostrarClientes("");
			Limpiar();
			menu.btnGuardarCliente.setEnabled(true);
			menu.btnActualizarCliente.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "Llene los campos nombres y apellidos", "Advertencia", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void eliminar() {
		int filaSelect;
		try {
			filaSelect = menu.tblClientes.getSelectedRow();
			if (filaSelect != -1) {
				modelo = (DefaultTableModel) menu.tblClientes.getModel();
				id = (String) modelo.getValueAt(filaSelect, 0);
				//valido si tiene un credito pendiente
				if (!clientes.ValidarClienteCredito(id)) {
					int confirmar = JOptionPane.showConfirmDialog(
						null,
						"Seguro que quires borrar este cliente",
						"Informacion",
						JOptionPane.OK_CANCEL_OPTION
					);
					if (confirmar == JOptionPane.YES_OPTION) {
						this.clientes.setId(id);
						clientes.Eliminar();
						MostrarClientes("");
						creditos.MostrarCreditosCreados("");
					}
				} else {
					JOptionPane.showMessageDialog(
						null,
						"No se puede borrar el cliente por que tiene un crédito pendiente."
					);
				}
			} else {
				/*  */
			}

		} catch (Exception err) {

		}
	}

	public boolean validar() {
		boolean validar = true;
		nombres = menu.txtNombresCliente.getText();
		apellidos = menu.txtApellidosCliente.getText();
		telefono = menu.txtTelefonoCliente.getText();
		direccion = menu.txtDireccionCliente.getText();
		if (nombres.equals("")) {
			validar = false;
		} else if (apellidos.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public void MostrarClientes(String Buscar) {
		menu.tblClientes.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblClientes.getTableHeader().setOpaque(false);
		menu.tblClientes.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblClientes.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblClientes.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		menu.tblClientes.setModel(clientes.Consulta(Buscar));
	}

	//metodo para limpiar el formulario clientes
	public void Limpiar() {
		menu.txtNombresCliente.setText("");
		menu.txtApellidosCliente.setText("");
		menu.txtTelefonoCliente.setText("");
		menu.txtDireccionCliente.setText("");
	}

	//metodo para habilitar los elementos que deshabilto el metodo deshabilitar de formulario cliente
	public void Habilitar() {
		menu.btnGuardarCliente.setEnabled(true);
		menu.btnActualizarCliente.setEnabled(false);
		menu.txtNombresCliente.setEnabled(true);
		menu.txtApellidosCliente.setEnabled(true);
		menu.txtTelefonoCliente.setEnabled(true);
		menu.txtDireccionCliente.setEnabled(true);
	}

	//metodo para llenar la tabla mostrar los cliente para a agregar a Credito
	public void MostrarClienteCredito(String buscar) {
		//lineas para darle stilo al emcabezado de las tabblas
		menu.tblAddClienteCredito.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblAddClienteCredito.getTableHeader().setOpaque(false);
		menu.tblAddClienteCredito.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblAddClienteCredito.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblAddClienteCredito.setModel(clientes.Consulta(buscar));
	}

	//metodo para dehabilitar elementos de formulario Cliente
	public void Deshabilitar() {
		menu.btnGuardarCliente.setEnabled(false);
		menu.btnActualizarCliente.setEnabled(false);
		menu.txtNombresCliente.setEnabled(false);
		menu.txtApellidosCliente.setEnabled(false);
		menu.txtTelefonoCliente.setEnabled(false);
		menu.txtDireccionCliente.setEnabled(false);
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == menu.txtBuscarCliente) {
			String Buscar = menu.txtBuscarCliente.getText();
			MostrarClientes(Buscar);
		}
		if (e.getSource() == menu.txtBuscar) {
			MostrarClienteCredito(menu.txtBuscar.getText());
		}
	}
}
