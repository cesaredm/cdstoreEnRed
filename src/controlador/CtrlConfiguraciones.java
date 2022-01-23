/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.github.anastaciocintra.output.PrinterOutputStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.IMenu;
import modelo.Configuraciones;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlConfiguraciones implements ActionListener {

	IMenu menu = null;
	Configuraciones config = null;
	String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
	String ip, tipoOrdenador;
	int idIp;

	public CtrlConfiguraciones(IMenu menu, Configuraciones info) {
		this.menu = menu;
		this.config = info;
		this.mostrarImpresoras();
		this.mostrarImpresoraEstablecida();
		this.mostrarIps();
		this.config.getIpsOrdenadoresCliente();
		this.config.getMulticonexion();
		this.saberSiHayMulticonexion();
		this.menu.btnActualizarInfoFactura.addActionListener(this);
		this.menu.btnActualizarInfoFactura.setActionCommand("btnActualizarInfoFactura");
		this.menu.btnConfigFactura.addActionListener(this);
		this.menu.btnConfigFactura.setActionCommand("btnConfigFactura");
		this.menu.btnImpresoras.addActionListener(this);
		this.menu.btnImpresoras.setActionCommand("btnImpresoras");
		this.menu.btnEstablecerImpresora.addActionListener(this);
		this.menu.btnEstablecerImpresora.setActionCommand("btnEstablecerImpresora");
		this.menu.btnConfigRedes.addActionListener(this);
		this.menu.btnConfigRedes.setActionCommand("btnConfigRedes");
		this.menu.btnGuardarIps.addActionListener(this);
		this.menu.btnGuardarIps.setActionCommand("btnGuardarIps");
		this.menu.editarIp.addActionListener(this);
		this.menu.editarIp.setActionCommand("mnEditarIp");
		this.menu.eliminarIp.addActionListener(this);
		this.menu.eliminarIp.setActionCommand("mnEliminarIp");
		this.menu.cbxMulticonexion.addActionListener(this);
		this.menu.cbxMulticonexion.setActionCommand("cbxMulticonexion");
		info.obtenerInfoFactura();
		this.menu.txtInfoActual.setText(
			info.getNombre()
			+ "\n" + info.getDireccion()
			+ "\n RFC:  " + info.getRfc()
			+ "\n Rango Permitido: " + info.getRangoInicio()
			+ " - "
			+ info.getRangoFinal()
			+ "\n Telefono: " + info.getTelefono()
			+ "\n" + info.getAnotaciones()
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnActualizarInfoFactura": {
				this.actualizarInfoFactura();
			}
			break;
			case "btnConfigFactura": {
				this.menu.jifConfigFactura.setVisible(true);
				this.menu.jifconfigImpresoras.setVisible(false);
				this.menu.jifRedes.setVisible(false);
			}
			break;
			case "btnImpresoras": {
				this.menu.jifConfigFactura.setVisible(false);
				this.menu.jifconfigImpresoras.setVisible(true);
				this.menu.jifRedes.setVisible(false);
			}
			break;
			case "btnEstablecerImpresora": {
				this.establecerImpresora();
			}
			break;
			case "btnConfigRedes": {
				this.menu.jifRedes.setVisible(true);
				this.menu.jifConfigFactura.setVisible(false);
				this.menu.jifconfigImpresoras.setVisible(false);
			}
			break;
			case "btnGuardarIps": {
				this.guardarIps();
			}
			break;
			case "mnEditarIp": {
				this.editar();
			}
			break;
			case "mnEliminarIp": {
				this.eliminar();
			}
			break;
			case "cbxMulticonexion": {
				this.setMulticonexion();
			}
			break;
		}
	}

	public void actualizarInfoFactura() {
		String nombre = menu.txtNombreTienda.getText();
		String direccion = menu.txtDireccionTienda.getText();
		String RFC = menu.txtRFCTienda.getText();
		String rangoInicio = menu.txtInicioRango.getText();
		String rangoFinal = menu.txtFinalRango.getText();
		String telefono = menu.txtTelefonoTienda.getText();
		String anotaciones = menu.txtAreaAnotacionesInfoFactura.getText();

		config.setNombre(nombre);
		config.setDireccion(direccion);
		config.setRfc(RFC);
		config.setRangoInicio(rangoInicio);
		config.setRangoFinal(rangoFinal);
		config.setTelefono(telefono);
		config.setAnotaciones(anotaciones);
		config.updateInfoFactura();
		limpiar();
		config.obtenerInfoFactura();
		menu.txtInfoActual.setText(config.getNombre() + "\n" + config.getDireccion() + "\n RFC:  " + config.getRfc() + "\n Rango Permitido: " + config.getRangoInicio() + " - " + config.getRangoFinal() + "\n Telefono: " + config.getTelefono() + "\n" + config.getAnotaciones());
	}

	public void limpiar() {
		menu.txtNombreTienda.setText("");
		menu.txtDireccionTienda.setText("");
		menu.txtRFCTienda.setText("");
		menu.txtInicioRango.setText("");
		menu.txtFinalRango.setText("");
		menu.txtAreaAnotacionesInfoFactura.setText("");
		menu.txtTelefonoTienda.setText("");
	}

	public void mostrarImpresoras() {
		for (String printServiceName : this.printServicesNames) {
			this.menu.cmbElegirImpresora.addItem(printServiceName);
		}
	}

	public void establecerImpresora() {
		Configuraciones.impresora = this.menu.cmbElegirImpresora.getSelectedItem().toString();
		this.config.updateImpresora();
		this.config.getImpresora();
		this.menu.lblImpresoraEstablecida.setText(Configuraciones.impresora);
	}

	public void mostrarImpresoraEstablecida() {
		this.config.getImpresora();
		this.menu.lblImpresoraEstablecida.setText(Configuraciones.impresora);
	}

	public void limpiarRedes() {
		this.menu.txtIpOrdenador.setText("");
	}

	public boolean validarIps() {
		boolean validar = true;
		this.ip = this.menu.txtIpOrdenador.getText();
		this.tipoOrdenador = this.menu.cmbTipoOrdenador.getSelectedItem().toString();
		if (this.ip.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public void guardarIps() {
		if (this.validarIps()) {
			this.config.setIp(this.ip);
			this.config.setTipoOrdenador(this.tipoOrdenador);
			this.config.guardarIps();
			if (this.tipoOrdenador.equals("Cliente")) {
				this.config.crearUsuarioDB();
			}
			this.mostrarIps();
			this.limpiar();
			this.config.getIpsOrdenadoresCliente();
		}
	}

	public void mostrarIps() {
		this.config.mostrarIps();
		this.menu.tblIps.setModel(this.config.tableModel);
	}

	public void editar() {
		int filaseleccionada = this.menu.tblIps.getSelectedRow();
		if (filaseleccionada != -1) {
			this.idIp = Integer.parseInt(this.menu.tblIps.getValueAt(filaseleccionada, 0).toString());
			this.config.setIdIp(this.idIp);
			this.config.getIpOrdenador();
			this.menu.cmbTipoOrdenador.setSelectedItem(this.config.getTipoOrdenador());
			this.menu.txtIpOrdenador.setText(this.config.getIp());
		}
	}

	public void eliminar() {
		int filaseleccionada = this.menu.tblIps.getSelectedRow();
		if (filaseleccionada != -1) {
			this.idIp = Integer.parseInt(this.menu.tblIps.getValueAt(filaseleccionada, 0).toString());
			this.ip = (String) this.menu.tblIps.getValueAt(filaseleccionada, 2);
			this.tipoOrdenador = (String) this.menu.tblIps.getValueAt(filaseleccionada, 1);
			this.config.setIdIp(this.idIp);
			this.config.setIp(this.ip);
			this.config.eliminarIp();
			if (this.tipoOrdenador.equals("Cliente")) {
				this.config.eliminarUsuarioDB();
			}
			this.mostrarIps();
			this.config.getIpsOrdenadoresCliente();
		}
	}

	public void setMulticonexion() {
		if (this.menu.cbxMulticonexion.isSelected()) {
			this.config.setMulticonexion("si");
			this.config.getMulticonexion();
		} else {
			this.config.setMulticonexion("no");
			this.config.getMulticonexion();
		}
		this.saberSiHayMulticonexion();
	}

	public void saberSiHayMulticonexion() {
		if (Configuraciones.multiconexion) {
			this.menu.cbxMulticonexion.setSelected(true);
			this.habilitarMultiConexion();
		} else {
			this.menu.cbxMulticonexion.setSelected(false);
			this.inhabilitarMulticonexion();
		}
	}

	public void habilitarMultiConexion() {
		this.menu.cmbTipoOrdenador.setEnabled(true);
		this.menu.txtIpOrdenador.setEnabled(true);
		this.menu.btnGuardarIps.setEnabled(true);
		this.menu.btnActualizarIp.setEnabled(true);
		this.menu.tblIps.setEnabled(true);
	}

	public void inhabilitarMulticonexion() {
		this.menu.cmbTipoOrdenador.setEnabled(false);
		this.menu.txtIpOrdenador.setEnabled(false);
		this.menu.btnGuardarIps.setEnabled(false);
		this.menu.btnActualizarIp.setEnabled(false);
		this.menu.tblIps.setEnabled(false);
	}
}
