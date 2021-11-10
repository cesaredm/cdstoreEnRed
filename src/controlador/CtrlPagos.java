/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.github.anastaciocintra.escpos.EscPos;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
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
public class CtrlPagos extends CtrlImprimir implements ActionListener, CaretListener, MouseListener {

	private int id;
	private int credito;
	private float monto,
		saldo,
		saldoActual;

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
	String fechaString;
	private java.sql.Date fechaSQL;
	private int formaPago;
	private String anotacion;
	private String moneda;
	IMenu menu;
	PagosCreditos pagos;
	SockectCliente socketCliente;
	Creditos creditos;
	Reportes reportes;
	EstadoCreditos estadoCreditos;
	PrintReportes print;
	InfoFactura info;
	DefaultTableModel modelo;
	Date fecha;
	DecimalFormat formato;

	public CtrlPagos(IMenu menu, PagosCreditos pagos) {
		this.menu = menu;
		this.pagos = pagos;
		this.formato = new DecimalFormat("############0.00");
		this.reportes = new Reportes();
		this.creditos = new Creditos();
		this.estadoCreditos = new CambioEstadoCreditoPagos();
		this.modelo = new DefaultTableModel();
		this.print = new PrintReportes();
		this.info = new InfoFactura();
		this.socketCliente = new SockectCliente();
		this.fecha = new Date();
		this.menu.cmbFormaPagoCredito.setModel(pagos.FormasPago());
		this.menu.btnGuardarPago.addActionListener(this);
		this.menu.btnActualizarPago.addActionListener(this);
		this.menu.btnNuevoPago.addActionListener(this);
		this.menu.EditarPago.addActionListener(this);
		this.menu.BorrarPago.addActionListener(this);
		this.menu.txtBuscarPago.addCaretListener(this);
		this.menu.btnMostrarPagosRegistrados.addActionListener(this);
		this.menu.tblPagos.addMouseListener(this);
		MostrarPagos("");
		DeshabilitarPagos();
		UltimoPago();
		this.menu.jcFechaPago.setDate(fecha);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (e.getSource() == menu.btnGuardarPago) {
			guardarPago();
		}
		if (e.getSource() == menu.btnActualizarPago) {
			this.actualizar();
		}
		if (e.getSource() == menu.btnNuevoPago) {
			HabilitarPago();
			LimpiarPago();
			menu.jsMontoPago.requestFocus();
		}
		if (e.getSource() == menu.EditarPago) {
			this.editar();
		}
		if (e.getSource() == menu.BorrarPago) {
			this.eliminar();

		}
		if (e.getSource() == menu.btnMostrarPagosRegistrados) {
			menu.pagosAcreditos.setSize(860, 540);
			menu.pagosAcreditos.setVisible(true);
			menu.pagosAcreditos.setLocationRelativeTo(null);
		}
	}

	public void eliminar() {
		int filaseleccionada = menu.tblPagos.getSelectedRow();
		try {
			if (filaseleccionada == -1) {

			} else {
				this.modelo = (DefaultTableModel) menu.tblPagos.getModel();
				this.id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
				this.credito = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 2).toString());
				this.pagos.setId(this.id);
				this.pagos.Eliminar();
				MostrarPagos("");
				this.estadoCreditos.updateApendiente(this.credito);
				this.estadoCreditos.updateAabierto(this.credito);
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + " en la funcion Borrar Pago", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void editar() {
		int filaseleccionada = menu.tblPagos.getSelectedRow();
		try {
			if (filaseleccionada != -1) {
				this.modelo = (DefaultTableModel) menu.tblPagos.getModel();
				this.id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
				this.pagos.setId(this.id);
				this.pagos.editar();
				HabilitarPago();
				menu.jcFechaPago.setDate(this.pagos.getFecha());
				menu.cmbFormaPagoCredito.setSelectedItem(this.pagos.getFormaPagoString());
				menu.txtAreaAnotacionPago.setText(this.pagos.getAnotacion());
				menu.cmbMonedaPago.setSelectedItem(this.pagos.getMoneda());
				menu.txtCreditoPago.setText(String.valueOf(this.pagos.getCredito()));
				menu.btnGuardarPago.setEnabled(false);
				menu.btnActualizarPago.setEnabled(true);
			}
		} catch (Exception err) {
		}
	}

	public void actualizar() {
		if (this.validar()) {
			this.pagos.setCredito(this.credito);
			this.pagos.setMonto(this.monto);
			this.pagos.setMoneda(this.moneda);
			this.pagos.setFecha(this.fechaSQL);
			this.pagos.setFormaPago(this.formaPago);
			this.pagos.setAnotacion(this.anotacion);
			pagos.Actualizar();
			MostrarPagos("");
			MostrarCreditos("");
			estadoCreditos.updateApendiente(this.credito);
			estadoCreditos.updateAabierto(this.credito);
			LimpiarPago();
			menu.btnGuardarPago.setEnabled(true);
			menu.btnActualizarPago.setEnabled(false);
		}
	}

	public void MostrarPagos(String buscar) {
		menu.jcFechaPago.setDate(this.fecha);
		menu.tblPagos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblPagos.getTableHeader().setOpaque(false);
		menu.tblPagos.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblPagos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblPagos.setModel(this.pagos.Mostrar(buscar));
	}

	public boolean isNumeric(String cadena) {//metodo para la validacion de campos numericos
		try {
			Float.parseFloat(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	//metodo para limpiar el formulario Pagos
	public void LimpiarPago() {
		menu.jsMontoPago.setValue(0);
		menu.cmbFormaPagoCredito.setSelectedItem("Efectivo");
		menu.txtAreaAnotacionPago.setText("");
	}

	public void HabilitarPago() {
		menu.btnActualizarPago.setEnabled(false);
		menu.btnGuardarPago.setEnabled(true);
	}
	//funcion para deshabilitar componentes de el formulario de pago

	public void DeshabilitarPagos() {
		menu.btnActualizarPago.setEnabled(false);
		menu.btnGuardarPago.setEnabled(false);
	}//lbls para hacer visible el pago en la ventana pago

	//mostrar el id del pago actual
	public void UltimoPago() {
		this.menu.lblNumeroPago.setText("" + creditos.obtenerUltimoPago());
	}

	public boolean validar() {
		boolean validar = true;
		this.credito = (this.menu.txtCreditoPago.getText().equals("")) ? 0 : Integer.parseInt(menu.txtCreditoPago.getText());
		this.monto = Float.parseFloat(menu.jsMontoPago.getValue().toString());
		this.formaPago = Integer.parseInt(this.pagos.ObtenerFormaPago(this.menu.cmbFormaPagoCredito.getSelectedItem().toString()));
		this.anotacion = menu.txtAreaAnotacionPago.getText();
		this.fechaSQL = new java.sql.Date(this.menu.jcFechaPago.getDate().getTime());
		this.moneda = this.menu.cmbMonedaPago.getSelectedItem().toString();
		this.fechaString = sdf.format(this.menu.jcFechaPago.getDate());
		if (this.credito == 0) {
			validar = false;
		} else if (this.monto == 0) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public void guardarPago() {
		String[] ips = {"127.0.0.1"};
		float saldo = 0,
			saldoActual = 0;
		String numeroPago = menu.lblNumeroPago.getText();
		if (this.validar()) {
			try {
				saldo = pagos.deuda(String.valueOf(this.credito)) - pagos.PagosSegunCredito(String.valueOf(this.credito));
				this.pagos.setCredito(this.credito);
				this.pagos.setMonto(this.monto);
				this.pagos.setMoneda(moneda);
				this.pagos.setFecha(this.fechaSQL);
				this.pagos.setFormaPago(this.formaPago);
				this.pagos.setAnotacion(this.anotacion);
				pagos.Guardar();
				this.estadoCreditos.updateAabierto(this.credito);
				this.socketCliente.setIps(ips);
				this.socketCliente.socketInit(this.pagos);
				saldoActual = pagos.deuda(String.valueOf(this.credito)) - pagos.PagosSegunCredito(String.valueOf(this.credito));
				UltimoPago();
				info.obtenerInfoFactura();
				imprimir(
					info.getNombre(),
					numeroPago,
					fechaString,
					this.pagos.cliente(String.valueOf(this.credito)),
					String.valueOf(this.credito),
					this.formato.format(saldo),
					this.formato.format(this.monto),
					this.formato.format(saldoActual));
				MostrarPagos("");
				LimpiarPago();
				this.MostrarCreditos("");
				menu.btnGuardarPago.setEnabled(true);
				menu.btnActualizarPago.setEnabled(false);
			} catch (Exception e) {

			}
		}

	}

	public void MostrarCreditos(String buscar) {
		menu.tblCreditos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblCreditos.getTableHeader().setOpaque(false);
		menu.tblCreditos.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblCreditos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblCreditos.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		menu.tblCreditos.setModel(this.creditos.Mostrar(buscar));
	}
	//IMPRIMIR TICKET COMPOBANTE DE PAGO

	public void imprimir(String tienda, String idPago, String fecha, String cliente, String credito, String totalCredito, String monto, String saldo) {
		try {

			reiniciar();
			escpos.write(imageWrapper, escposImage).feed(1);

			escpos.writeLF(boldCenter, tienda)
				.feed(1)
				.writeLF(boldCenter, "COMPROBANTE DE PAGO")
				.feed(2)
				.writeLF("N° crédito:" + credito)
				.writeLF("N° pago:" + idPago)
				.writeLF("Cliente:" + cliente)
				.writeLF("Fecha:" + fecha)
				.writeLF("Total crédito:" + totalCredito)
				.writeLF("Monto de abono:" + monto)
				.write("Saldo:").writeLF(bold, saldo)
				.feed(4)
				.writeLF(centrar, "_____________________________________")
				.writeLF(centrar, "Firma vendedor")
				.feed(8)
				.writeLF(centrar, "_____________________________________")
				.writeLF(centrar, "Firma cliente")
				.feed(3)
				.cut(EscPos.CutMode.FULL);

			close();

		} catch (Exception e) {

		}
	}

	;
    
    
    @Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == menu.txtBuscarPago) {
			String valor = menu.txtBuscarPago.getText();
			MostrarPagos(valor);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == menu.tblPagos) {
			if (e.getClickCount() == 2) {
				String anotacion;
				int filaseleccionada = menu.tblPagos.getSelectedRow();
				if (filaseleccionada != -1) {
					anotacion = (String) menu.tblPagos.getValueAt(filaseleccionada, 7);
					menu.txtAreaInfoPago.setText(anotacion);
					menu.jdInfoPago.setLocationRelativeTo(null);
					menu.jdInfoPago.setSize(600, 270);
					menu.jdInfoPago.setVisible(true);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
