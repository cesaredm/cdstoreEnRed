package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.print.PrintService;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelo.Creditos;
import modelo.PagosCreditos;
import modelo.Reportes;
import modelo.InfoFactura;
import samplesCommon.SamplesCommon;
import vista.IMenu;

public class CtrlCreditos extends PrintReportes implements ActionListener, CaretListener, MouseListener {

	private int id;
	private int cliente;
	private Date fecha;
	private String estado;
	private float limite,
		saldoInicialDolares,
		saldoInicialCordobas;
	private java.sql.Date fechaSQL;
	IMenu menu;
	Creditos creditos;
	PagosCreditos pagos;
	Reportes report;
	CtrlReportes ctrlReport;
	DefaultTableModel modelo;
	InfoFactura info;
	DecimalFormat formato;

	public CtrlCreditos(IMenu menu, Creditos creditos) {
		this.menu = menu;
		this.creditos = creditos;
		this.pagos = new PagosCreditos();
		this.report = new Reportes();
		this.ctrlReport = new CtrlReportes(menu, report);
		this.modelo = new DefaultTableModel();
		this.fecha = new Date();
		this.formato = new DecimalFormat("###,###,###,#00.00");
		info = new InfoFactura();
		this.menu.btnCrearCredito.addActionListener(this);
		this.menu.btnNuevoCredito.addActionListener(this);
		this.menu.btnActualizarCredito.addActionListener(this);
		this.menu.EditarCredito.addActionListener(this);
		this.menu.EliminarCredito.addActionListener(this);
		this.menu.GenerarPago.addActionListener(this);
		this.menu.btnAddClienteCredito.addActionListener(this);
		this.menu.txtBuscarCreditosCreados.addCaretListener(this);
		this.menu.txtBuscarCredito.addCaretListener(this);
		this.menu.txtBuscarCreditoFactura.addCaretListener(this);
		this.menu.tblAddClienteCredito.addMouseListener(this);
		this.menu.tblCreditos.addMouseListener(this);
		this.menu.tblCreditosCreados.addMouseListener(this);
		this.menu.btnYes.addActionListener(this);
		this.menu.btnCancel.addActionListener(this);
		this.menu.btnMonedasRecibidasPagoCreditos.addActionListener(this);
		this.menu.btnActualizarMorosos.addActionListener(this);
		this.menu.tblAbonosCreditos.addMouseListener(this);
		iniciar();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (e.getSource() == menu.btnCrearCredito) {
			crearCredito();
		}

		if (e.getSource() == menu.EliminarCredito) {
			// eliminarCredito();
		}
		if (e.getSource() == menu.btnYes) {
			confirmarBorarCredito();
		}
		if (e.getSource() == menu.btnCancel) {
			menu.ConfimarEliminarCredito.setVisible(false);
		}
		if (e.getSource() == menu.btnActualizarCredito) {
			actualizarCredito();
		}
		//
		if (e.getSource() == menu.btnNuevoCredito) {
			HabilitarCreditos();
			LimpiarCreditos();
		}
		//
		if (e.getSource() == menu.btnAddClienteCredito) {
			menu.BuscarClienteCredito.setSize(592, 277);
			menu.BuscarClienteCredito.setVisible(true);
			menu.BuscarClienteCredito.setLocationRelativeTo(null);
		}
		//
		if (e.getSource() == menu.EditarCredito) {
			editarCredito();
		}
		// 
		if (e.getSource() == menu.GenerarPago) {
			generarPago();
		}
		if (e.getSource() == menu.btnMonedasRecibidasPagoCreditos) {
			int numeroFacturaPago = Integer.parseInt(this.menu.lblNumeroPago.getText());
			this.menu.jdMonedasRecibidas.setSize(860, 493);
			this.menu.jdMonedasRecibidas.setLocationRelativeTo(null);
			this.menu.jdMonedasRecibidas.setVisible(true);
			this.menu.chexIngresoMonedasPago.setSelected(true);
			this.menu.chexIngresoMonedasPago.setEnabled(false);
			this.menu.chexIngresoMonedasFactura.setSelected(false);
			this.menu.chexIngresoMonedasFactura.setEnabled(false);
			this.menu.jsFacturaPago.setValue(numeroFacturaPago);

		}
		if (e.getSource() == menu.btnActualizarMorosos) {
			fechaLimiteMorosos();
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == menu.txtBuscarCreditosCreados) {
			MostrarCreditosCreados(menu.txtBuscarCreditosCreados.getText());
		}
		if (e.getSource() == menu.txtBuscarCredito) {
			MostrarCreditos(menu.txtBuscarCredito.getText());
		}
		if (e.getSource() == menu.txtBuscarCreditoFactura) {
			MostrarCreditosAddFactura(menu.txtBuscarCreditoFactura.getText());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == menu.tblAddClienteCredito) {
			int filaseleccionada = menu.tblAddClienteCredito.getSelectedRow();
			try {
				String id, nombres, apellidos;
				if (filaseleccionada == -1) {

				} else {
					this.modelo = (DefaultTableModel) menu.tblAddClienteCredito.getModel();
					nombres = this.modelo.getValueAt(filaseleccionada, 1).toString();
					apellidos = this.modelo.getValueAt(filaseleccionada, 2).toString();
					id = this.modelo.getValueAt(filaseleccionada, 0).toString();
					menu.txtClienteCredito.setText(id);
					menu.BuscarClienteCredito.setVisible(false);
				}

			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err);
			}
		}
		if (e.getSource() == menu.tblCreditos) {
			int filaseleccionada = menu.tblCreditos.getSelectedRow();

			if (e.getClickCount() == 2) {
				try {
					if (filaseleccionada != -1) {
						this.modelo = (DefaultTableModel) menu.tblCreditos.getModel();
						this.id = Integer.parseInt(
							this.modelo.getValueAt(filaseleccionada, 0)
								.toString()
						);
						//MostrarHistorialCrediticio(id);
						this.mostrarInfoCrediticiaActual(this.id);
						menu.jdInfoCrediticia.setSize(910, 538);
						menu.jdInfoCrediticia.setVisible(true);
						menu.jdInfoCrediticia.setLocationRelativeTo(null);
					} else {

					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, err + "mostrar facturasporcreditos");
				}
			}
		}
		if (e.getSource() == menu.tblCreditosCreados) {
			int filaseleccionada = menu.tblCreditosCreados.getSelectedRow();
			int id;
			if (e.getClickCount() == 2) {
				try {
					if (filaseleccionada == -1) {

					} else {
						this.modelo = (DefaultTableModel) menu.tblCreditosCreados.getModel();
						id = Integer.parseInt(
							this.modelo.getValueAt(filaseleccionada, 0)
								.toString()
						);
						MostrarHistorialCrediticio(id);
						menu.jdInfoCrediticia.setSize(910, 538);
						menu.jdInfoCrediticia.setVisible(true);
						menu.jdInfoCrediticia.setLocationRelativeTo(null);

					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, err + "mostrar facturasporcreditos");
				}
			}
		}
		if (e.getSource() == menu.tblAbonosCreditos) {
			if (e.getClickCount() == 2) {
				String anotacion;
				int filaseleccionada = menu.tblAbonosCreditos.getSelectedRow();
				try {
					if (filaseleccionada != -1) {
						anotacion = (String) menu.tblAbonosCreditos.getValueAt(filaseleccionada, 3);
						menu.txtAreaInfoPago.setText(anotacion);
						menu.jdInfoPago.setLocationRelativeTo(null);
						menu.jdInfoPago.setSize(600, 270);
						menu.jdInfoPago.setVisible(true);
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null, err + "en metodo MouseClick en ctrl creditos");
				}
			} else {

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

	public boolean validar() {
		boolean validar = true;
		this.fecha = menu.jcFechaCredito.getDate();
		long f = this.fecha.getTime();
		this.fechaSQL = new java.sql.Date(f);
		this.estado = menu.cmbEstadoCredito.getSelectedItem().toString();
		this.limite = Float.parseFloat(menu.jsLimiteCredito.getValue().toString());
		this.saldoInicialCordobas = Float.parseFloat(menu.jsSaldoInicialCordobasCredito.getValue().toString());
		this.saldoInicialDolares = Float.parseFloat(menu.jsSaldoInicialDolarCreditos.getValue().toString());

		if (menu.txtClienteCredito.getText().equals("")) {
			validar = false;
		} else {
			this.cliente = Integer.parseInt(menu.txtClienteCredito.getText());
			validar = true;
		}
		return validar;
	}

	public void iniciar() {
		menu.jcFechaCredito.setDate(fecha);
		MostrarCreditosAddFactura("");
		MostrarCreditosCreados("");
		MostrarCreditos("");
		DeshabilitarCreditos();
		menu.jcFechaPago.setDate(fecha);
		fechaLimiteMorosos();
	}

	//deshabilitar los elementos del form creditos
	public void DeshabilitarCreditos() {
		menu.btnActualizarCredito.setEnabled(false);
		menu.btnCrearCredito.setEnabled(false);
		menu.btnAddClienteCredito.setEnabled(false);
		menu.jsLimiteCredito.setEnabled(false);
	}

	public boolean isNumeric(String cadena) {//metodo para la validacion de campos numericos
		try {
			Float.parseFloat(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public void MostrarCreditosCreados(String buscar) {
		menu.tblCreditosCreados.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblCreditosCreados.getTableHeader().setOpaque(false);
		menu.tblCreditosCreados.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblCreditosCreados.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblCreditosCreados.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		menu.jcFechaCredito.setDate(this.fecha);
		menu.tblCreditosCreados.setModel(creditos.MostrarCreditosCreados(buscar));
	}

	public void MostrarCreditosAddFactura(String buscar) {
		menu.tblAddCreditoFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblAddCreditoFactura.getTableHeader().setOpaque(false);
		menu.tblAddCreditoFactura.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblAddCreditoFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.jcFechaCredito.setDate(this.fecha);
		menu.tblAddCreditoFactura.setModel(creditos.MostrarCreditosAddFactura(buscar));
	}

	public void HabilitarCreditos() {
		menu.btnActualizarCredito.setEnabled(false);
		menu.btnCrearCredito.setEnabled(true);
		menu.btnAddClienteCredito.setEnabled(true);
		menu.jsLimiteCredito.setEnabled(true);
	}

	public void LimpiarCreditos() {
		menu.txtClienteCredito.setText("");
		menu.cmbEstadoCredito.setSelectedItem("Abierto");
		menu.jsLimiteCredito.setValue(0);
		menu.jsSaldoInicialCordobasCredito.setValue(0);
		menu.jsSaldoInicialDolarCreditos.setValue(0);
	}

	public void MostrarCreditos(String buscar) {
		menu.tblCreditos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblCreditos.getTableHeader().setOpaque(false);
		menu.tblCreditos.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblCreditos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblCreditos.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		menu.tblCreditos.setModel(this.creditos.Mostrar(buscar));
	}

	public void mostrarInfoCrediticiaActual(int credito) {
		float saldoCordobas, saldoDolar;
		/* solicitar datos */
		this.creditos.saldoInicialCredito(credito);
		this.creditos.saldoPorCredito(credito);
		this.creditos.pagosPorCedito(credito);
		/*    crear borde con el nombre del cliente   */
		menu.jpInformacionCrediticia.setBorder(javax.swing.BorderFactory.createTitledBorder(creditos.NombreCliente(credito)));
		menu.tblArticulosCredito.setModel(creditos.listaProductoCreditosDolar(credito));
		menu.tblArticulosCreditoCordobas.setModel(creditos.listaProductoCreditosCordobas(credito));
		/*    historial de abonos     */
		menu.tblAbonosCreditos.setModel(creditos.MostrarAbonosCreditos(credito));
 		/*    calculos para sacar los saldo   */
		saldoCordobas = this.creditos.getSaldoCordobas() - this.creditos.getPagosCordobas();
		saldoDolar = this.creditos.getSaldoDolares() - this.creditos.getPagosDolar();
		/*    seteo de pagos, saldo en dolares y cordobas   */
		menu.lblTodalCreditoCordobas.setText(this.formato.format(saldoCordobas));
		menu.lblTotalCreditoDolar.setText(this.formato.format(saldoDolar));
		menu.lblTotalAbonosCordobas.setText(this.formato.format(this.creditos.getPagosCordobas()));
		menu.lblTotalAbonosDolar.setText(this.formato.format(this.creditos.getPagosDolar()));

		menu.lblSaldoCordobas.setText(this.formato.format(saldoCordobas));
		menu.lblSaldoDolar.setText(this.formato.format(saldoDolar));
	}

	public void MostrarHistorialCrediticio(int credito) {
		float saldoCordobas, saldoDolar;
		/*    llamar los datos    */
		this.creditos.saldoInicialCredito(credito);
		this.creditos.saldoPorCredito(credito);
		this.creditos.pagosPorCedito(credito);
		/*    crear borde con el nombre del cliente   */
		menu.jpInformacionCrediticia.setBorder(javax.swing.BorderFactory.createTitledBorder(creditos.NombreCliente(credito)));
		menu.tblArticulosCredito.setModel(creditos.MostrarHistorialProductosCreditoDolar(credito));
		menu.tblArticulosCreditoCordobas.setModel(creditos.MostrarHistorialProductosCreditoCordobas(credito));
		/*    historial de abonos     */
		menu.tblAbonosCreditos.setModel(creditos.MostrarAbonosCreditos(credito));
		/*    pagos, saldo en dolares y cordobas   */
		menu.lblTodalCreditoCordobas.setText(this.formato.format(creditos.getSaldoCordobas()));
		menu.lblTotalCreditoDolar.setText(this.formato.format(creditos.getSaldoDolares()));
		menu.lblTotalAbonosCordobas.setText(this.formato.format(this.creditos.getPagosCordobas()));
		menu.lblTotalAbonosDolar.setText(this.formato.format(this.creditos.getPagosDolar()));
		/*    calculos para scar los saldo   */
		saldoCordobas = this.creditos.getSaldoCordobas() - this.creditos.getPagosCordobas();
		saldoDolar = this.creditos.getSaldoDolares() - this.creditos.getPagosDolar();
		menu.lblSaldoCordobas.setText(this.formato.format(saldoCordobas));
		menu.lblSaldoDolar.setText(this.formato.format(saldoDolar));
	}

	public void crearCredito() {
		if (this.validar() && !this.creditos.VerificarExistenciaDeCredito(this.cliente)) {
			this.creditos.setCliente(cliente);
			this.creditos.setEstado(estado);
			this.creditos.setFecha(fechaSQL);
			this.creditos.setLimite(limite);
			this.creditos.setSaldoInicialCordobas(saldoInicialCordobas);
			this.creditos.setSaldoInicialDolares(saldoInicialDolares);
			this.creditos.GuardarCredito();
			MostrarCreditosCreados("");
			MostrarCreditosAddFactura("");
			HabilitarCreditos();
			LimpiarCreditos();
			menu.btnActualizarCredito.setEnabled(false);
			menu.btnCrearCredito.setEnabled(true);
		}
	}

	public void eliminarCredito() {
		int filaseleccionada = 0, id = 0, confirmacion = 0;

		try {
			filaseleccionada = menu.tblCreditosCreados.getSelectedRow();
			if (filaseleccionada == -1) {

			} else {
				menu.ConfimarEliminarCredito.setVisible(true);
				menu.ConfimarEliminarCredito.setLocationRelativeTo(null);
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion eliminar Credito");
		}
	}

	public void confirmarBorarCredito() {
		int filaseleccionada = 0, id = 0, confirmacion = 0;
		try {
			filaseleccionada = menu.tblCreditosCreados.getSelectedRow();
			if (filaseleccionada == -1) {

			} else {
				modelo = (DefaultTableModel) menu.tblCreditosCreados.getModel();
				id = Integer.parseInt(modelo.getValueAt(filaseleccionada, 0).toString());
				String estado = (String) modelo.getValueAt(filaseleccionada, 5);
				if (estado.equals("Abierto") || estado.equals("Cancelado")) {
					creditos.Eliminar();
					MostrarCreditosCreados("");
					menu.ConfimarEliminarCredito.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "El crédito no se puede eliminar por que esta pendiente", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion eliminar Credito");
		}
	}

	public void actualizarCredito() {
		if (this.validar()) {
			this.creditos.setCliente(cliente);
			this.creditos.setEstado(estado);
			this.creditos.setFecha(fechaSQL);
			this.creditos.setLimite(limite);
			this.creditos.setSaldoInicialCordobas(saldoInicialCordobas);
			this.creditos.setSaldoInicialDolares(saldoInicialDolares);
			this.creditos.Actualizar();
			MostrarCreditosCreados("");
			MostrarCreditosAddFactura("");
			HabilitarCreditos();
			LimpiarCreditos();
			menu.btnActualizarCredito.setEnabled(false);
			menu.btnCrearCredito.setEnabled(true);
		}
	}

	public void editarCredito() {
		int filaseleccionada = menu.tblCreditosCreados.getSelectedRow();
		try {
			if (filaseleccionada != -1) {
				this.modelo = (DefaultTableModel) menu.tblCreditosCreados.getModel();
				this.id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
				this.creditos.setId(this.id);
				this.creditos.editar();
				if (this.creditos.getEstado().equals("Pendiente")) {
					HabilitarCreditos();
					menu.txtClienteCredito.setText(String.valueOf(this.creditos.getCliente()));
					menu.jcFechaCredito.setDate(this.creditos.getFecha());
					menu.cmbEstadoCredito.setSelectedItem(this.creditos.getEstado());
					menu.cmbEstadoCredito.setEnabled(false);
					menu.jsLimiteCredito.setValue(this.creditos.getLimite());
					menu.jsSaldoInicialCordobasCredito.setValue(this.creditos.getSaldoInicialCordobas());
					menu.jsSaldoInicialDolarCreditos.setValue(this.creditos.getSaldoInicialDolares());

					menu.btnActualizarCredito.setEnabled(true);
					menu.btnCrearCredito.setEnabled(false);
				} else {
					HabilitarCreditos();
					menu.txtClienteCredito.setText(String.valueOf(this.creditos.getCliente()));
					menu.jcFechaCredito.setDate(this.creditos.getFecha());
					menu.cmbEstadoCredito.setSelectedItem(this.creditos.getEstado());
					menu.cmbEstadoCredito.setEnabled(true);
					menu.jsLimiteCredito.setValue(this.creditos.getLimite());
					menu.jsSaldoInicialCordobasCredito.setValue(this.creditos.getSaldoInicialCordobas());
					menu.jsSaldoInicialDolarCreditos.setValue(this.creditos.getSaldoInicialDolares());

					menu.btnActualizarCredito.setEnabled(true);
					menu.btnCrearCredito.setEnabled(false);
				}
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "en la funcion editar Credito");
		}
	}

	public void generarPago() {
		int filaseleccionada = menu.tblCreditos.getSelectedRow();
		String credito, totalCredito;
		try {
			if (filaseleccionada != -1) {
				this.modelo = (DefaultTableModel) menu.tblCreditos.getModel();
				credito = (String) this.modelo.getValueAt(filaseleccionada, 0);
				totalCredito = (String) this.modelo.getValueAt(filaseleccionada, 1);
				menu.txtCreditoPago.setText(credito);
				menu.jsMontoPago.setValue(0);
				menu.jsMontoPago.requestFocus();
				menu.pagosAcreditos.setSize(900, 540);
				menu.pagosAcreditos.setVisible(true);
				menu.pagosAcreditos.setLocationRelativeTo(null);
			} else {

			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "BTN GENERAR PAGO");
		}
	}

	//retorna el Saldo del cliente 
	public float limiteCredito(String id) {
		return creditos.limiteCredito(id);
	}

	public void fechaLimiteMorosos() {
		try {
			long now = this.fecha.getTime();
			String dias90 = "7776000000";
			java.sql.Date d = new java.sql.Date(now - Long.parseLong(dias90));
			menu.tblMorosos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
			menu.tblMorosos.getTableHeader().setOpaque(false);
			menu.tblMorosos.getTableHeader().setBackground(new Color(69, 76, 89));
			menu.tblMorosos.getTableHeader().setForeground(new Color(255, 255, 255));
			menu.tblMorosos.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
			menu.tblMorosos.setModel(this.creditos.morosos(d));
			if (this.creditos.isEmpty()) {
				menu.lblNotificacionClientes.setVisible(true);
			} else {
				menu.lblNotificacionClientes.setVisible(false);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo fechaLimiteMoroso en controlador creditos");
		}

	}

}
