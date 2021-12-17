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
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CtrlTransacciones implements ActionListener, CaretListener {

	private float monto;
	private java.sql.Date fechaSQL;
	private String Descripcion,
		TipoTrans,
		nombreCaja,
		moneda;
	private int idCaja,
		id;
	IMenu menu;
	Transacciones transaccion;
	Date fecha,
		now;
	Reportes r;
	DefaultTableModel modelo;

	public CtrlTransacciones(IMenu menu, Transacciones gastos) {
		this.fecha = new Date();
		this.menu = menu;
		this.transaccion = gastos;
		r = new Reportes();
		this.modelo = new DefaultTableModel();
		menu.cmbCajaTransaccion.setModel(transaccion.mostrarCajas());
		this.menu.btnGuardarGasto.addActionListener(this);
		this.menu.btnActualizarGasto.addActionListener(this);
		this.menu.btnNuevoGasto.addActionListener(this);
		this.menu.EditarGastos.addActionListener(this);
		this.menu.EliminarGasto.addActionListener(this);
		MostrarGastos("");
		DeshabilitarGastos();
		this.menu.jcFechaGasto.setDate(fecha);
	}

	public boolean validar() {
		boolean validar = true;
		this.monto = Float.parseFloat(menu.jsMontoTransaccion.getValue().toString());
		this.Descripcion = menu.txtDescripcionGasto.getText();
		this.nombreCaja = (String) menu.cmbCajaTransaccion.getSelectedItem();
		this.idCaja = this.transaccion.IdCaja(nombreCaja);
		this.TipoTrans = (String) menu.cmbTipoTransaccion.getSelectedItem();
		this.moneda = (String) menu.cmbMonedaTransaccion.getSelectedItem();
		now = menu.jcFechaGasto.getDate();
		long f = now.getTime();
		this.fechaSQL = new java.sql.Date(f);
		if (this.Descripcion.equals("")) {
			JOptionPane.showMessageDialog(null, "Escriba una descripción");
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public void guardar() {
		if (this.validar()) {
			this.transaccion.setMonto(monto);
			this.transaccion.setMoneda(this.moneda);
			this.transaccion.setDescripcion(Descripcion);
			this.transaccion.setIdCaja(idCaja);
			this.transaccion.setTipoTrans(TipoTrans);
			this.transaccion.setFecha(fechaSQL);
			this.transaccion.Guardar();
			MostrarGastos("");
			LimpiarGastos();
			menu.btnActualizarGasto.setEnabled(false);
			menu.btnGuardarGasto.setEnabled(true);
		}
	}

	public void actualizar() {
		if (this.validar()) {
			this.transaccion.setMonto(monto);
			this.transaccion.setMoneda(this.moneda);
			this.transaccion.setDescripcion(Descripcion);
			this.transaccion.setIdCaja(idCaja);
			this.transaccion.setTipoTrans(TipoTrans);
			this.transaccion.setFecha(fechaSQL);
			this.transaccion.Actualizar();
			MostrarGastos("");
			LimpiarGastos();
			menu.btnActualizarGasto.setEnabled(false);
			menu.btnGuardarGasto.setEnabled(true);
		}
	}

	public void editar() {
		int filaseleccionada = menu.tblGastos.getSelectedRow();
		try {
			if (filaseleccionada != -1) {
				this.modelo = (DefaultTableModel) menu.tblGastos.getModel();
				this.id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
				HabilitarGastos();
				LimpiarGastos();
				this.transaccion.setId(this.id);
				this.transaccion.editar();
				menu.jsMontoTransaccion.setValue(this.transaccion.getMonto());
				menu.cmbMonedaTransaccion.setSelectedItem(this.transaccion.getMoneda());
				menu.txtDescripcionGasto.setText(this.transaccion.getDescripcion());
				menu.jcFechaGasto.setDate(this.transaccion.getFecha());
				menu.cmbCajaTransaccion.setSelectedItem(this.transaccion.getNombreCaja());
				menu.cmbTipoTransaccion.setSelectedItem(this.transaccion.getTipoTrans());
				menu.btnActualizarGasto.setEnabled(true);
				menu.btnGuardarGasto.setEnabled(false);
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion de EditarGastos");
		}
	}

	public void eliminar() {
		int filaseleccionada = menu.tblGastos.getSelectedRow(), confirmacion = 0;
		try {
			if (filaseleccionada != -1) {
				confirmacion = JOptionPane.showConfirmDialog(
					null,
					"Seguro que quieres borrar este gasto",
					"Advertencia",
					JOptionPane.WARNING_MESSAGE
				);
				if (confirmacion == JOptionPane.YES_OPTION) {
					this.modelo = (DefaultTableModel) menu.tblGastos.getModel();
					this.id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
					this.transaccion.setId(this.id);
					this.transaccion.Eliminar();
					MostrarGastos("");
				}
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion ElimarGasto en la clase IMenu");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (e.getSource() == menu.btnGuardarGasto) {
			this.guardar();
		}
		if (e.getSource() == menu.btnActualizarGasto) {
			this.actualizar();
		}
		if (e.getSource() == menu.btnNuevoGasto) {
			HabilitarGastos();
			LimpiarGastos();
		}
		if (e.getSource() == menu.EditarGastos) {
			this.editar();
		}
		if (e.getSource() == menu.EliminarGasto) {
			this.eliminar();
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {

	}

	public void MostrarGastos(String Buscar) {
		menu.tblGastos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblGastos.getTableHeader().setOpaque(false);
		menu.tblGastos.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblGastos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.jcFechaGasto.setDate(this.fecha);
		menu.tblGastos.setModel(transaccion.Mostrar(Buscar));
	}

	public boolean isNumeric(String cadena) {//metodo para la validacion de campos numericos
		try {
			Float.parseFloat(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public void LimpiarGastos() {
		menu.jsMontoTransaccion.setValue(0);
		menu.txtDescripcionGasto.setText("");
		menu.cmbMonedaTransaccion.setSelectedIndex(1);
	}

	public void HabilitarGastos() {
		menu.jsMontoTransaccion.setEnabled(true);
		menu.cmbMonedaTransaccion.setEnabled(true);
		menu.cmbTipoTransaccion.setEnabled(true);
		menu.btnActualizarGasto.setEnabled(false);
		menu.btnGuardarGasto.setEnabled(true);
		menu.txtDescripcionGasto.setEnabled(true);
		menu.cmbCajaTransaccion.setEnabled(true);
	}

	public void DeshabilitarGastos() {
		menu.jsMontoTransaccion.setEnabled(false);
		menu.cmbMonedaTransaccion.setEnabled(false);
		menu.txtDescripcionGasto.setEnabled(false);
		menu.btnActualizarGasto.setEnabled(false);
		menu.btnGuardarGasto.setEnabled(false);
		menu.cmbCajaTransaccion.setEnabled(false);
		menu.cmbTipoTransaccion.setEnabled(false);
	}
}
