package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlFacturacion implements ActionListener, CaretListener, MouseListener, KeyListener {

	boolean banderaActualizacion;
	int idfactura,
		idProducto,
		idCredito,
		idFormaPago;
	float cantidad,
		precio,
		totalDetalle,
		isv,
		total,
		totalDolar,
		subTotal,
		pagoCon,
		cambio,
		descuento,
		precioDolar;
	String formaPago,
		comprador,
		cliente,
		nombreProduct,
		tipoVenta,
		usuario;

	String[][] items;
	java.sql.Timestamp fechaFactura;
	IMenu menu;
	int permiso;
	Facturacion facturaModel;
	DetalleFactura detalle;
	EstadoCreditos estadosCreditos;
	Date fecha;
	DefaultTableModel modelo;
	DefaultTableCellRenderer formatColumn;
	Productos modelProduct;
	SockectCliente socketCliente;
	//formato para los totales
	DecimalFormat formato;
	Reportes reportes;
	Creditos creditos;
	SpinnerNumberModel sModel;
	JSpinner spiner;
	String[] nD;

	public CtrlFacturacion(IMenu menu, Facturacion factura, int permiso) {
		this.fecha = new Date();
		this.permiso = permiso;
		this.menu = menu;
		this.facturaModel = factura;
		this.detalle = new DetalleFactura();

		this.estadosCreditos = new CambioEstadoCreditoFactura();
		this.menu.cmbFormaPago.setModel(factura.FormasPago());
		this.formato = new DecimalFormat("#,###,###,###,#00.00");
		this.creditos = new Creditos();
		this.reportes = new Reportes();
		this.modelo = new DefaultTableModel();
		this.modelProduct = new Productos();
		this.socketCliente = new SockectCliente();
		this.formatColumn = new DefaultTableCellRenderer();
		this.menu.btnActualizarFactura.setVisible(false);
		this.menu.btnGuardarFactura.addActionListener(this);
		this.menu.btnGuardarFactura.addKeyListener(this);
		this.menu.btnGuardarSalidaMoneda.addActionListener(this);
		this.menu.btnSalidaMonedas.addActionListener(this);
		this.menu.btnActualizarFactura.addActionListener(this);
		this.menu.btnEliminarFilaFactura.addActionListener(this);
		this.menu.btnNuevaFactura.addActionListener(this);
		this.menu.btnCreditoFactura.addActionListener(this);
		this.menu.tblAddCreditoFactura.addMouseListener(this);
		this.menu.tblAddProductoFactura.addMouseListener(this);
		this.menu.btnEditarImpuesto.addActionListener(this);
		this.menu.btnAgregarProductoFactura.addActionListener(this);
		this.menu.btnEditarFactura.addActionListener(this);
		this.menu.Descuento.addActionListener(this);
		this.menu.btnLimpiarCliente.addActionListener(this);
		this.menu.btnAgregar.addActionListener(this);
		this.menu.btnAgregar.addKeyListener(this);
//		this.menu.btnDividirPago.addActionListener(this);
		this.menu.btnGuardarMonedasRecibidas.addActionListener(this);
		this.menu.txtCodBarraFactura.addKeyListener(this);
		this.menu.btnAgregarProductoFactura.addKeyListener(this);
		this.menu.addDescuento.addActionListener(this);
		this.menu.addDescuentoDirecto.addActionListener(this);
		this.menu.addDescuentoPorcentaje.addActionListener(this);
		this.menu.addMasProducto.addActionListener(this);
		this.menu.tblFactura.addKeyListener(this);
		this.menu.txtBuscarPorCategoria.addCaretListener(this);
		this.menu.txtBuscarPorLaboratorio.addCaretListener(this);
		this.menu.txtPagoConCordobas.addCaretListener(this);
		this.menu.txtPagoConCordobas.addKeyListener(this);
		this.menu.txtPagoConDolares.addCaretListener(this);
		this.menu.txtPagoConDolares.addKeyListener(this);
		this.menu.txtPagoConDolaresVenta.addCaretListener(this);
		this.menu.txtPagoConDolaresVenta.addKeyListener(this);
		this.menu.txtCambio.addCaretListener(this);
		this.menu.txtTotalCordobas.addCaretListener(this);
		this.menu.cmbFormaPago.addActionListener(this);
		this.sModel = new SpinnerNumberModel();
		this.sModel.setMinimum(0.00);
		this.sModel.setValue(0.00);
		this.sModel.setStepSize(0.01);
		this.spiner = new JSpinner(sModel);
		EstiloTablaFacturacion();
		editarISV("");
		DeshabilitarBtnGuardarFactura();
		validarPermiso();
		this.menu.jcFechaFactura.setDate(fecha);
		menu.txtNumeroFactura.setText(factura.ObtenerIdFactura());
		menu.txtCodBarraFactura.requestFocus();
	}

	public void alinearTextoTabla() {
		this.formatColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		menu.tblFactura.getColumnModel().getColumn(4).setCellRenderer(this.formatColumn);
		menu.tblFactura.getColumnModel().getColumn(5).setCellRenderer(this.formatColumn);
	}

	public String CleanChars(String str) {
		str = str.replace("C", "");
		str = str.replace("$", "");
		str = str.replace(",", "");
		return str;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (e.getSource() == menu.btnGuardarFactura) {
			String totalF = this.CleanChars(menu.txtTotalCordobas.getText());
			String idCreditoL = menu.txtCreditoFactura.getText();
			float saldo, sumar, limite;
			if (!idCreditoL.equals("")) {
				saldo = this.estadosCreditos.saldoCredito(Integer.parseInt(idCreditoL));
				limite = this.estadosCreditos.limiteCredito(idCreditoL);
				sumar = saldo + Float.parseFloat(totalF);
				if (sumar > limite) {
					JOptionPane.showMessageDialog(
						null,
						"Está excediendo el límite de crédito",
						"Advertencia",
						JOptionPane.WARNING_MESSAGE
					);
					menu.btnGuardarFactura.setEnabled(false);
				} else {
					this.guardarFactura();
				}
			} else {
				guardarFactura();
			}
		}
		if (e.getSource() == menu.btnActualizarFactura) {
			actualizarFactura();
		}
		if (e.getSource() == menu.btnEditarFactura) {
			editarFactura();
		}
		if (e.getSource() == menu.btnAgregarProductoFactura) {
			mostrarVentanaProductos();
		}
		if (e.getSource() == menu.btnEliminarFilaFactura) {
			eliminarFilaFactura();
		}
		if (e.getSource() == menu.btnNuevaFactura) {
			nuevaFactura();
		}
		if (e.getSource() == menu.btnEditarImpuesto) {
			actualizarIVA();
		}
		if (e.getSource() == menu.btnCreditoFactura) {
			mostrarVentanaCreditos();
		}
		if (e.getSource() == menu.Descuento) {

		}
		if (e.getSource() == menu.btnLimpiarCliente) {
			limpiarFormularioClienteFactura();
		}
		if (e.getSource() == menu.btnAgregar) {
			String code = this.menu.txtCodBarraFactura.getText();
			if (!code.equals("")) {
				AgregarProductoFacturaEnter();
			} else {
				JOptionPane.showMessageDialog(null, "Escriba un código de barras..");
			}
		}
		if (e.getSource() == menu.addDescuento) {
			addDescuento();
		}
		if (e.getSource() == menu.addDescuentoDirecto) {
			addDescuentoDirecto();
		}
		if (e.getSource() == menu.addDescuentoPorcentaje) {
			addDescuentoPorcentaje();
		}
		if (e.getSource() == menu.addMasProducto) {
			addMasProducto();
		}
//		if (e.getSource() == menu.btnDividirPago) {
//			this.menu.jdMonedasRecibidas.setSize(325, 260);
//			this.menu.jdMonedasRecibidas.setLocationRelativeTo(null);
//			this.menu.jdMonedasRecibidas.setVisible(true);
//			this.menu.chexIngresoMonedasFactura.setSelected(true);
//			this.menu.chexIngresoMonedasFactura.setEnabled(false);
//			this.menu.chexIngresoMonedasPago.setSelected(false);
//			this.menu.chexIngresoMonedasPago.setEnabled(false);
//			this.menu.chexIngresoCompraDolar.setSelected(false);
//			this.menu.jsFacturaPago.setValue(Integer.parseInt(menu.txtNumeroFactura.getText()));
//		}
		if (e.getSource() == menu.btnSalidaMonedas) {
			this.menu.jdSalidaMonedas.setSize(325, 260);
			this.menu.jdSalidaMonedas.setLocationRelativeTo(null);
			this.menu.jdSalidaMonedas.setVisible(true);
			this.menu.chexEgresoMonedasFactura.setSelected(true);
			this.menu.chexEgresoMonedasFactura.setEnabled(false);
			this.menu.chexEgresoMonedasPago.setSelected(false);
			this.menu.chexEgresoMonedasPago.setEnabled(false);
		}
		if (e.getSource() == menu.cmbFormaPago) {
			String formaPago = menu.cmbFormaPago.getSelectedItem().toString();
			if (formaPago.equals("Efectivo")) {
				menu.txtCreditoFactura.setText("");
				menu.txtNClienteFactura.setText("");
				menu.txtAClienteFactura.setText("");
			}
		}
	}

	public boolean isNumeric(String valor) {
		try {
			Float.parseFloat(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	public void cambio() {
		/*Logica de cambio*/
		float pagoConCordobas = 0,
			pagoConDolaresCompra = 0,
			pagoConDolaresVenta = 0,
			totalFactura = Float.parseFloat(CleanChars(this.menu.txtTotalGlobalCordobas.getText())),
			cambio = 0,
			precioDolarCompra,
			precioDolarVenta;
		pagoConCordobas = (!isNumeric(this.menu.txtPagoConCordobas.getText()))
			? 0 : Float.parseFloat(this.menu.txtPagoConCordobas.getText());
		pagoConDolaresCompra = (!isNumeric(this.menu.txtPagoConDolares.getText()))
			? 0 : Float.parseFloat(this.menu.txtPagoConDolares.getText());
		pagoConDolaresVenta = (!isNumeric(this.menu.txtPagoConDolaresVenta.getText()))
			? 0 : Float.parseFloat(this.menu.txtPagoConDolaresVenta.getText());
		precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText());
		precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText());

		cambio = ((pagoConDolaresCompra * precioDolarCompra)
			+ (pagoConDolaresVenta * precioDolarVenta)
			+ pagoConCordobas)
			- totalFactura;

		menu.txtCambio.setText(this.formato.format(cambio));
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == menu.txtPagoConCordobas
			|| e.getSource() == menu.txtPagoConDolares
			|| e.getSource() == menu.txtPagoConDolaresVenta) {
			cambio();
		}
		if (e.getSource() == menu.txtBuscarPorLaboratorio) {
			String laboratorio = menu.txtBuscarPorLaboratorio.getText();
			MostrarPorMarca(laboratorio);
		}
		if (e.getSource() == menu.txtBuscarPorCategoria) {
			String categoria = menu.txtBuscarPorCategoria.getText();
			MostrarPorCategoria(categoria);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (e.getSource() == menu.tblAddProductoFactura) {
			addProductoFactura();
		}
		if (e.getSource() == menu.tblAddCreditoFactura) {
			if (e.getClickCount() == 2) {
				addCreditoFactura();
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e
	) {
	}

	@Override
	public void mouseReleased(MouseEvent e
	) {
	}

	@Override
	public void mouseEntered(MouseEvent e
	) {
	}

	@Override
	public void mouseExited(MouseEvent e
	) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.VK_ENTER == e.getKeyCode()) {
			String code = this.menu.txtCodBarraFactura.getText();
			if (!code.equals("")) {
				AgregarProductoFacturaEnter();
			} else {
				JOptionPane.showMessageDialog(null, "Escriba un código de barras..");
			}
		}
		if (e.VK_F10 == e.getKeyCode()) {
			mostrarVentanaProductos();
		}
		if (e.VK_F9 == e.getKeyCode()) {
			String totalF = this.CleanChars(menu.txtTotalCordobas.getText());
			String idCreditoL = menu.txtCreditoFactura.getText();
			float saldo, sumar, limite;
			if (!idCreditoL.equals("")) {
				saldo = this.estadosCreditos.saldoCredito(Integer.parseInt(idCreditoL));
				limite = this.estadosCreditos.limiteCredito(idCreditoL);
				sumar = saldo + Float.parseFloat(totalF);
				if (sumar > limite) {
					JOptionPane.showMessageDialog(
						null,
						"Está excediendo el límite de crédito", "Advertencia",
						JOptionPane.WARNING_MESSAGE
					);
					menu.btnGuardarFactura.setEnabled(false);
				} else {
					guardarFactura();
				}
			} else {
				guardarFactura();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void validar() {
		try {
			if (this.banderaActualizacion) {
				this.menu.jcFechaFactura.setDate(new Date());
			}
			this.pagoCon = (menu.txtPagoConCordobas.getText().equals("")) ? 0 : Float.parseFloat(menu.txtPagoConCordobas.getText());
			this.cambio = (menu.txtCambio.getText().equals("")) ? 0 : Float.parseFloat(menu.txtCambio.getText());
			this.subTotal = (menu.txtSubTotal.getText().equals("")) ? 0 : Float.parseFloat(this.CleanChars(menu.txtSubTotal.getText()));
			this.comprador = menu.txtCompradorFactura.getText();
			this.cliente = menu.txtNClienteFactura.getText() + " " + menu.txtAClienteFactura.getText();
			this.fecha = menu.jcFechaFactura.getDate();//capturo la fecha del dateshooser
			//convertir la fecha obtenida a formato sql
			this.fechaFactura = new java.sql.Timestamp(this.fecha.getTime());
			this.usuario = this.menu.lblUsuarioSistema.getText();
			//obtengo el numero de credito al que pertenecera la facturaModel
			this.idCredito = (menu.txtCreditoFactura.getText().equals("")) ? 0 : Integer.parseInt(menu.txtCreditoFactura.getText());
			if (idCredito == 0) {
				this.tipoVenta = "Contado";
			} else {
				this.tipoVenta = "Credito";
			}
			this.idfactura = Integer.parseInt(menu.txtNumeroFactura.getText());
			this.isv = (menu.txtImpuesto.getText().equals("")) ? 0 : Float.parseFloat(this.CleanChars(menu.txtImpuesto.getText()));//obtengo el iva
			this.total = Float.parseFloat(this.CleanChars(menu.txtTotalCordobas.getText()));//obtengo total de facturaModel
			this.totalDolar = Float.parseFloat(this.CleanChars(menu.txtTotalDolar.getText()));
			//capturo el nombre de forma de pago
			this.formaPago = (String) menu.cmbFormaPago.getSelectedItem();
			//capturo el id de la forma de pago que retorna la funcion obtenerformapago de la clase facturacion
			this.idFormaPago = this.facturaModel.ObtenerFormaPago(formaPago);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, e + "Metodo validar en ctrl facturacion");
		}

	}

	public void guardarFactura() {
		try {
			this.banderaActualizacion = true;
			this.validar();
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			int filas = this.modelo.getRowCount();//Cuento las filas de la tabla Factura
			this.items = new String[filas][5];
			if (menu.btnGuardarFactura.isEnabled()) {
				String[] ArregloImprimir = new String[filas];
				this.facturaModel.setCaja(1);
				this.facturaModel.setFecha(this.fechaFactura);
				this.facturaModel.setNombreComprador(this.comprador);
				this.facturaModel.setUsuario(this.usuario);
				this.facturaModel.setCredito(this.idCredito);
				this.facturaModel.setPago(this.idFormaPago);
				this.facturaModel.setIva(this.isv);
				this.facturaModel.setTotalCordobas(0);
				this.facturaModel.setTotalDolar(0);
				this.facturaModel.GuardarFactura();
				for (int cont = 0; cont < filas; cont++) {
					//capturo el id de producto para guardar en detallefactura
					this.idProducto = Integer.parseInt(this.modelo.getValueAt(cont, 0).toString());
					//capturo la cantidad de producto de la columna dos y la paso a String para guardar en detallefactura
					this.cantidad = Float.parseFloat(this.modelo.getValueAt(cont, 2).toString());
					//capturo el nombre de producto
					this.nombreProduct = (String) this.modelo.getValueAt(cont, 3);
					//capturo el precio de producto para guardar en detallefactura
					this.precio = Float.parseFloat(this.CleanChars(this.modelo.getValueAt(cont, 4).toString()));
					//capturo el total de detalle compra de producto para guardar en detallefactura
					this.totalDetalle = Float.parseFloat(this.CleanChars(this.modelo.getValueAt(cont, 5).toString()));
					//envio los datos a guardar de los detalles
					this.items[cont][0] = String.valueOf(this.idfactura);
					this.items[cont][1] = String.valueOf(this.idProducto);
					this.items[cont][2] = String.valueOf(this.precio);
					this.items[cont][3] = String.valueOf(this.cantidad);
					this.items[cont][4] = String.valueOf(this.totalDetalle);
					//validar si el nombre del producto es mayor de 10 caracteres
					if (nombreProduct.length() > 10) {
						nombreProduct = nombreProduct.substring(0, 10);
					}
					ArregloImprimir[cont] = nombreProduct + " " + cantidad + "   " + precio + "  " + totalDetalle + "\n";
				}
				this.facturaModel.setItems(this.items);
				this.facturaModel.guardarDetalle();
				menu.txtNumeroFactura.setText(this.facturaModel.ObtenerIdFactura());
				this.multiconexion();
				menu.txtCodBarraFactura.setText("");
				menu.txtCodBarraFactura.requestFocus();
				//limpio la facturaModel
				DeshabilitarBtnGuardarFactura();
				this.estadosCreditos.updateApendiente(idCredito);
				//this.estadosCreditos.updateAabierto(idCredito);
				//creditos.MostrarCreditos("");
				this.MostrarCreditosCreados("");
				this.MostrarReportesDario(this.fecha);
				this.reportesDiarios(this.fecha);
				this.MostrarCreditos("");
				this.MostrarCreditosAddFactura("");
				Imprimir(
					String.valueOf(this.idfactura),
					comprador,
					cliente,
					tipoVenta,
					formaPago,
					ArregloImprimir,
					String.valueOf(subTotal),
					String.valueOf(isv),
					String.valueOf(total),
					String.valueOf(this.totalDolar),
					fechaFactura.toString(),
					/*String.valueOf(pagoCon)*/ "",
					/*String.valueOf(cambio)*/ "",
					this.usuario
				);
				LimpiarTablaFactura();
			} else {
				JOptionPane.showMessageDialog(null, "La factura esta vacia");
			}
		} catch (Exception err) {
			//JOptionPane.showMessageDialog(null, err);
			err.printStackTrace();
		}
	}

	public void guardarFacturaSinImprimir() {
		try {
			this.validar();
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			int filas = this.modelo.getRowCount();//Cuento las filas de la tabla Factura
			if (menu.btnGuardarFactura.isEnabled()) {
				String[] ArregloImprimir = new String[filas];
				this.facturaModel.setCaja(1);
				this.facturaModel.setFecha(this.fechaFactura);
				this.facturaModel.setNombreComprador(this.comprador);
				this.facturaModel.setCredito(this.idCredito);
				this.facturaModel.setPago(this.idFormaPago);
				this.facturaModel.setIva(this.isv);
				this.facturaModel.setTotalCordobas(0);
				this.facturaModel.GuardarFactura();
				for (int cont = 0; cont < filas; cont++) {
					//capturo el id de producto para guardar en detallefactura
					this.idProducto = Integer.parseInt(this.modelo.getValueAt(cont, 0).toString());
					//capturo la cantidad de producto de la columna dos y la paso a String para guardar en detallefactura
					this.cantidad = Float.parseFloat(this.modelo.getValueAt(cont, 2).toString());
					//capturo el nombre de producto
					this.nombreProduct = (String) this.modelo.getValueAt(cont, 3);
					//capturo el precio de producto para guardar en detallefactura
					this.precio = Float.parseFloat(this.modelo.getValueAt(cont, 4).toString());
					//capturo el total de detalle compra de producto para guardar en detallefactura
					this.totalDetalle = Float.parseFloat(this.modelo.getValueAt(cont, 5).toString());
					//envio los datos a guardar de los detalles
					this.detalle.setFactura(this.idfactura);
					this.detalle.setProductoDetalle(idProducto);
					this.detalle.setPrecio(precio);
					this.detalle.setCantidad(cantidad);
					this.detalle.setImporteDetalle(totalDetalle);
					this.detalle.DetalleFactura();
					//funcion para diminuir el stock segun la cantidad que se venda
					//validar si el nombre del producto es mayor de 10 caracteres
					if (nombreProduct.length() > 10) {
						nombreProduct = nombreProduct.substring(0, 10);
					}
					ArregloImprimir[cont] = nombreProduct + " " + cantidad + "   " + precio + "  " + totalDetalle + "\n";
				}
				menu.txtNumeroFactura.setText(this.facturaModel.ObtenerIdFactura());
				this.multiconexion();
				menu.txtCodBarraFactura.setText("");
				menu.txtCodBarraFactura.requestFocus();
				//limpio la facturaModel
				LimpiarTablaFactura();
				DeshabilitarBtnGuardarFactura();
				this.estadosCreditos.updateApendiente(idCredito);
				this.estadosCreditos.updateAabierto(idCredito);
				//creditos.MostrarCreditos("");
				this.MostrarCreditosCreados("");
				this.MostrarReportesDario(this.fecha);
				this.reportesDiarios(this.fecha);
				this.MostrarCreditos("");
				this.MostrarCreditosAddFactura("");
			} else {
				JOptionPane.showMessageDialog(null, "La factura esta vacia");
			}
		} catch (Exception err) {
			//JOptionPane.showMessageDialog(null, err);
		}

	}

	public void multiconexion() {
		if (Configuraciones.multiconexion) {
//			this.socketCliente.setIps(Configuraciones.listIpsCliente);
			this.socketCliente.socketInit(this.facturaModel);
		} else {

		}
	}

	public void mostrarVentanaProductos() {
		menu.AddProductoFactura.setSize(1071, 456);
		menu.AddProductoFactura.setVisible(true);
		menu.AddProductoFactura.setLocationRelativeTo(null);
		if (menu.rbBuscarNombreCodBarra.isSelected() == true) {
			menu.txtBuscarPorNombre.setEnabled(true);
			menu.txtBuscarPorCategoria.setEnabled(false);
			menu.txtBuscarPorLaboratorio.setEnabled(false);
			menu.txtBuscarPorNombre.requestFocus();
			menu.txtBuscarPorNombre.selectAll();
		}
	}

	public void addThisLine(){
		
	}

	public void AgregarProductoFacturaEnter() {
		String codBarra = menu.txtCodBarraFactura.getText(),
			id;
		float precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText());
		float precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText());
		int filas = 0;
		float sacarImpuesto = 0,
			porcentajeImp = 0;
		this.facturaModel.obtenerPorCodBarra(codBarra);
		this.facturaModel.Vender(this.facturaModel.getProducto()[0], this.facturaModel.getProducto()[2]);
		if (this.facturaModel.isExito()) {
			if (this.facturaModel.vender) {
				this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
				this.modelo.addRow(this.facturaModel.getProducto());
				if (this.facturaModel.getMonedaVenta().equals("Dolar")) {
					this.totalImporteDolar += Float.parseFloat(CleanChars(this.facturaModel.getProducto()[5]));
				} else {
					this.totalImporteCordobas += Float.parseFloat(CleanChars(this.facturaModel.getProducto()[5]));
				}
				sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
				porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());
				this.totalGlobalCordobas = totalImporteCordobas + (totalImporteDolar * precioDolarVenta);
				this.totalGlobalDolar = totalImporteDolar + (totalImporteCordobas / precioDolarCompra);
				this.total = totalImporteCordobas;
				this.totalDolar = totalImporteDolar;
				this.isv = ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
				this.subTotal = this.total - this.isv;
				menu.txtSubTotal.setText("" + formato.format(this.subTotal));
				menu.txtImpuesto.setText("" + formato.format(this.isv));
				menu.txtTotalCordobas.setText("" + formato.format(this.total));
				menu.txtTotalDolar.setText(this.formato.format(this.totalDolar));
				menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
				menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
				menu.txtCodBarraFactura.setText("");
				DeshabilitarBtnGuardarFactura();
				this.mostrarProductosVender("");
			} else {
				JOptionPane.showMessageDialog(null, "No hay suficiente producto en stock para realizar la venta");
			}
		} else {
			this.menu.txtCodBarraFactura.setText("");
		}
	}

	public void Retornar() {
		this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
		menu.pnlVentas.setVisible(false);
		menu.pnlReportes.setVisible(true);
		menu.btnActualizarFactura.setVisible(false);
		try {
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			int filas = this.modelo.getRowCount();//numero de filas de la tabla facturaModel
			for (int i = 0; i < filas; i++) {
				this.modelo.removeRow(0);//remover filas de la tabla facturaModel
			}
			this.mostrarProductosVender("");//acturalizar tabla que muestra productos a vender
			//limpiar
			menu.btnGuardarFactura.setEnabled(true);
			menu.txtNClienteFactura.setText("");
			menu.txtAClienteFactura.setText("");
			menu.txtCreditoFactura.setText("");
			menu.txtCompradorFactura.setText("");
			this.total = 0;
			this.totalDolar = 0;
			this.totalGlobalCordobas = 0;
			this.totalGlobalDolar = 0;
			this.subTotal = 0;
			this.isv = 0;
			//inicializar a 0.0
			menu.txtSubTotal.setText("" + this.total);
			menu.txtImpuesto.setText("" + this.subTotal);
			menu.txtTotalCordobas.setText("" + this.isv);
			menu.txtTotalDolar.setText("" + this.totalDolar);
			menu.txtTotalGlobalCordobas.setText("" + this.totalGlobalCordobas);
			menu.txtTotalGolbalDolar.setText("" + this.totalGlobalDolar);

		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}
		menu.txtNumeroFactura.setText(facturaModel.ObtenerIdFactura());//actualizar numero de facturaModel
	}

	public void Imprimir(
		String Nfactura,
		String comprador,
		String cliente,
		String tipoVenta,
		String formaPago,
		String[] Datos,
		String subtotal,
		String isv,
		String total,
		String totalDolares,
		String fecha,
		String recibido,
		String cambio,
		String usuario
	) {
		Configuraciones info = new Configuraciones();
		info.obtenerInfoFactura();
		Ticket d = new Ticket();
		d.nameLocal = info.getNombre();
		d.direccion = info.getDireccion();
		d.telefono = info.getTelefono();
		d.RFC = info.getRfc();
		d.Rango = info.getRangoInicio() + " - " + info.getRangoFinal();
		d.box = "1";
		d.ticket = Nfactura;
		d.caissier = usuario;
		d.comprador = comprador;
		d.cliente = cliente;
		d.tipoVenta = tipoVenta;
		d.formaPago = formaPago;
		d.dateTime = fecha;
		d.items = Datos;
		d.subTotal = subtotal;
		d.iva = isv;
		d.totalCordobas = total;
		d.totalDolares = totalDolares;
		d.recibo = recibido;
		d.change = cambio;
		d.message = info.getAnotaciones();
		try {
			d.llenarTicket();
			d.printFactura();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//metodo para editar el impuesto de la facturaModel
	public void editarISV(String isv) {
		if (isv.equals("")) {
			menu.lblImpuestoISV.setText("15");
		} else {
			menu.lblImpuestoISV.setText(isv);
		}
	}

	public void LimpiarTablaFactura() {
		try {
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			int filas = this.modelo.getRowCount();
			for (int i = 0; i < filas; i++) {
				this.modelo.removeRow(0);
			}
			this.facturaModel.vaciartableproductos_temp();
			this.mostrarProductosVender("");
			menu.txtNClienteFactura.setText("");
			menu.txtAClienteFactura.setText("");
			menu.txtCreditoFactura.setText("");
			menu.txtCompradorFactura.setText("");
			menu.cmbFormaPago.setSelectedItem("Efectivo");
			menu.txtPagoConCordobas.setText("");
			menu.txtPagoConDolares.setText("");
			menu.txtPagoConDolaresVenta.setText("");
			menu.txtCambio.setText("");
			this.total = 0;
			this.totalDolar = 0;
			this.totalGlobalCordobas = 0;
			this.totalGlobalDolar = 0;
			this.totalImporteCordobas = 0;
			this.totalImporteDolar = 0;
			this.subTotal = 0;
			this.isv = 0;
			menu.txtSubTotal.setText("" + this.total);
			menu.txtImpuesto.setText("" + this.subTotal);
			menu.txtTotalCordobas.setText("" + this.isv);
			menu.txtTotalDolar.setText("" + this.totalDolar);
			menu.txtTotalGlobalCordobas.setText("" + this.totalGlobalCordobas);
			menu.txtTotalGolbalDolar.setText("" + this.totalGlobalDolar);
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}

	}

	public void EstiloTablaFacturacion() {
		menu.tblFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblFactura.getTableHeader().setOpaque(false);
		menu.tblFactura.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblFactura.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		this.alinearTextoTabla();
	}

	public void DeshabilitarBtnGuardarFactura() {
		if (menu.tblFactura.getRowCount() > 0) {
			menu.btnGuardarFactura.setEnabled(true);
			//menu.btnCobrarSinImprimir.setEnabled(true);
		} else {
			menu.btnGuardarFactura.setEnabled(false);
			//menu.btnCobrarSinImprimir.setEnabled(false);
		}
	}

	float totalImporteDolar = 0, totalImporteCordobas = 0, totalGlobalCordobas = 0, totalGlobalDolar = 0;

	public void addProductoFactura() {
		int filaseleccionada = menu.tblAddProductoFactura.getSelectedRow();
		try {
			String id, codigo, nombre, precio, cantidad, monedaVenta = "", simboloMoneda = "";
			float imp = 0,
				impuesto,
				stock,
				cantidadPVender,
				importe = 0,
				precioDolarCompra = Float.parseFloat(menu.txtPrecioDolarCompra.getText()),
				precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
				? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText()),
				sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText()),
				porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());

			if (filaseleccionada != -1) {
				this.modelo = (DefaultTableModel) menu.tblAddProductoFactura.getModel();
				id = modelo.getValueAt(filaseleccionada, 0).toString();
				codigo = modelo.getValueAt(filaseleccionada, 1).toString();
				nombre = modelo.getValueAt(filaseleccionada, 2).toString();
				precio = modelo.getValueAt(filaseleccionada, 3).toString();
				monedaVenta = (String) modelo.getValueAt(filaseleccionada, 4);
				stock = Float.parseFloat(modelo.getValueAt(filaseleccionada, 6).toString());
				cantidad = JOptionPane.showInputDialog(null, "Cantidad:");
				cantidadPVender = (cantidad.equals("")) ? 0 : Float.parseFloat(cantidad);
				this.facturaModel.Vender(id,cantidad);
				if (this.facturaModel.vender) {
					if (cantidadPVender > 0) {
						if (monedaVenta.equals("Córdobas")) {
							importe = (Float.parseFloat(precio) * cantidadPVender);
							this.totalImporteCordobas += importe;
							simboloMoneda = "C$";
						} else if (monedaVenta.equals("Dolar")) {
							importe = (Float.parseFloat(precio) * cantidadPVender);
							this.totalImporteDolar += importe;
							simboloMoneda = "$";
						}
						this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
						String[] FilaElementos = {
							id,
							codigo,
							cantidad,
							nombre,
							simboloMoneda + " " + precio,
							simboloMoneda + " " + this.formato.format(importe)
						};
						this.modelo.addRow(FilaElementos);
						this.totalGlobalCordobas = totalImporteCordobas + (totalImporteDolar * precioDolarVenta);
						this.totalGlobalDolar = totalImporteDolar + (totalImporteCordobas / precioDolarCompra);
						this.total = totalImporteCordobas;
						this.totalDolar = totalImporteDolar;
						this.isv = ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
						this.subTotal = this.totalGlobalCordobas - this.isv;

						menu.txtImpuesto.setText(formato.format(this.isv));
						menu.txtSubTotal.setText(formato.format(this.subTotal));
						menu.txtTotalCordobas.setText(formato.format(this.total));
						menu.txtTotalDolar.setText(formato.format(this.totalDolar));
						this.menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
						this.menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
						//this.facturaModel.Vender(id, cantidad);
						this.mostrarProductosVender("");
						menu.txtBuscarPorNombre.selectAll();
						DeshabilitarBtnGuardarFactura();
					}
				} else {
					JOptionPane.showMessageDialog(
						null,
						"No hay suficiente producto en stock para realizar esta venta",
						"Advertencia",
						JOptionPane.WARNING_MESSAGE
					);
				}
			}
		} catch (Exception err) {
			//JOptionPane.showMessageDialog(null, e);
		}
	}

	public void addCreditoFactura() {
		int filaseleccionada = menu.tblAddCreditoFactura.getSelectedRow();
		String nombre, apellido, credito;
		try {
			if (filaseleccionada == -1) {

			} else {
				int confirmacion;
				long now = this.fecha.getTime();
				String dias90 = "7776000000";
				java.sql.Date d = new java.sql.Date(now - Long.parseLong(dias90));
				this.modelo = (DefaultTableModel) menu.tblAddCreditoFactura.getModel();
				credito = (String) this.modelo.getValueAt(filaseleccionada, 0);
				nombre = (String) this.modelo.getValueAt(filaseleccionada, 2);
				apellido = (String) this.modelo.getValueAt(filaseleccionada, 3);

				if (this.creditos.isPagoAtrasado(d, credito)) {
					confirmacion = JOptionPane.showConfirmDialog(null, "El cliente " + nombre + " " + apellido + " no ha realizado"
						+ " pagos desde hace 3 o mas meses. Agregar crédito de todas formas?");
					if (confirmacion == JOptionPane.YES_OPTION) {
						menu.txtCreditoFactura.setText(credito);
						menu.txtNClienteFactura.setText(nombre);
						menu.txtAClienteFactura.setText(apellido);
						menu.cmbFormaPago.setSelectedItem("Pendiente");
						menu.AddCreditoFactura.setVisible(false);
						menu.txtCodBarraFactura.requestFocus();
					} else {

					}
				} else {
					menu.txtCreditoFactura.setText(credito);
					menu.txtNClienteFactura.setText(nombre);
					menu.txtAClienteFactura.setText(apellido);
					menu.cmbFormaPago.setSelectedItem("Pendiente");
					menu.AddCreditoFactura.setVisible(false);
					menu.txtCodBarraFactura.requestFocus();
				}
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}
	}

	public void mostrarVentanaCreditos() {
		menu.AddCreditoFactura.setSize(681, 363);
		menu.AddCreditoFactura.setVisible(true);
		menu.AddCreditoFactura.setLocationRelativeTo(null);
	}

	public void addMasProducto() {
		String nombre = "",
			id = "",
			codBarra,
			iu,
			moneda = "";
		float cantidadIngresar = 0,
			cantidadActual = 0,
			precio = 0,
			cantidadUpdate = 0,
			importeUpdate = 0,
			precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText()),
			precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText()),
			sacarImpuesto = 0,
			porcentajeImp = 0;
		int filaseleccionada = 0;
		try {
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			filaseleccionada = menu.tblFactura.getSelectedRow();
			if (filaseleccionada == -1) {
				JOptionPane.showMessageDialog(
					null,
					"Seleccione un producto",
					"Advertencia",
					JOptionPane.WARNING_MESSAGE
				);
			} else {

				id = (String) this.modelo.getValueAt(filaseleccionada, 0);
				nombre = (String) this.modelo.getValueAt(filaseleccionada, 3);
				codBarra = (String) this.modelo.getValueAt(filaseleccionada, 1);
				facturaModel.obtenerPorId(id);
				JOptionPane.showMessageDialog(
					null,
					spiner,
					"Cantidad de " + nombre + " a agregar:",
					JOptionPane.INFORMATION_MESSAGE
				);
				cantidadIngresar = Float.parseFloat(spiner.getValue().toString());
				this.facturaModel.Vender(id, String.valueOf(cantidadIngresar));
				if (this.facturaModel.vender) {
					cantidadActual = Float.parseFloat(this.modelo.getValueAt(filaseleccionada, 2).toString());
					precio = Float.parseFloat(this.CleanChars(this.modelo.getValueAt(filaseleccionada, 4).toString()));
					this.facturaModel.monedaVentaProducto(id);
					cantidadUpdate = cantidadActual + cantidadIngresar;
					if (this.facturaModel.getMonedaVenta().equalsIgnoreCase("Dolar")) {
						importeUpdate = (cantidadUpdate * precio);
						this.totalImporteDolar += (cantidadIngresar * precio);
						moneda = "$";
					} else if (this.facturaModel.getMonedaVenta().equalsIgnoreCase("Córdobas")) {
						importeUpdate = (cantidadUpdate * precio);
						this.totalImporteCordobas += (cantidadIngresar * precio);
						moneda = "C$";
					}
					this.modelo.setValueAt(String.valueOf(cantidadUpdate), filaseleccionada, 2);
					this.modelo.setValueAt(moneda + " " + formato.format(importeUpdate), filaseleccionada, 5);
					//this.facturaModel.Vender(id, String.valueOf(cantidadIngresar));
					this.totalGlobalCordobas = totalImporteCordobas + (totalImporteDolar * precioDolarVenta);
					this.totalGlobalDolar = totalImporteDolar + (totalImporteCordobas / precioDolarCompra);
					sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
					porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());
					this.total = this.totalImporteCordobas;
					this.totalDolar = this.totalImporteDolar;
					this.isv = (float) ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
					this.subTotal = this.totalGlobalCordobas - this.isv;
					menu.txtSubTotal.setText("" + formato.format(this.subTotal));
					menu.txtImpuesto.setText("" + formato.format(this.isv));
					menu.txtTotalCordobas.setText("" + formato.format(this.total));
					menu.txtTotalDolar.setText(this.formato.format(this.totalDolar));
					menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
					menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
					menu.txtCodBarraFactura.requestFocus();
					spiner.setValue(0.00);
					this.mostrarProductosVender("");
				} else {
					JOptionPane.showMessageDialog(
						null,
						"No hay suficiente producto en stock para realizar la venta"
					);
				}
			}
		} catch (Exception err) {
		}
	}

	public void addDescuento() {
		String nombre = "",
			id = "",
			moneda = "";
		int filaseleccionada = 0, confirmar = 0;
		float cantidad = 0,
			precioUpdate = 0,
			precio = 0,
			importeUpdate = 0,
			sacarImpuesto = 0,
			porcentajeImp,
			descuento = 0;
		filaseleccionada = menu.tblFactura.getSelectedRow();
		try {
			//modelo va a ser igual al modelo de la tabla facturaModel
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			//validar si hay una fila seleccionada
			if (filaseleccionada != -1) {

				//obtengo los valores de la tabla facturaModel para las operaciones
				cantidad = Float.parseFloat(this.modelo.getValueAt(filaseleccionada, 2).toString());
				nombre = (String) this.modelo.getValueAt(filaseleccionada, 3);
				precio = Float.parseFloat(this.CleanChars(this.modelo.getValueAt(filaseleccionada, 4).toString()));
				id = (String) this.modelo.getValueAt(filaseleccionada, 0);
				float precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
					? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText());
				float precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
					? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText());
				confirmar = JOptionPane.showConfirmDialog(
					null,
					spiner,
					"Agregar descuento a " + nombre,
					JOptionPane.OK_CANCEL_OPTION
				);
				if (confirmar == JOptionPane.YES_OPTION) {
					descuento = Float.parseFloat(spiner.getValue().toString());
					//realiza el escuento
					precioUpdate = precio - descuento;
					//establecer precio minimo del producto seleccionado
					modelProduct.precioMinimo(id);
					if (precioUpdate < modelProduct.getPrecioMinimo()) {
						JOptionPane.showMessageDialog(
							null,
							"Esta excediendo el precio minimo ´"
							+ modelProduct.getPrecioMinimo()
							+ "´ del producto con el descuento aplicado",
							"Advertencia",
							JOptionPane.WARNING_MESSAGE
						);
					} else {
						//obtengo desde el modelo facturta la moneda de venta de el producto na aplicarle el descuento
						this.facturaModel.monedaVentaProducto(id);
						importeUpdate = precioUpdate * cantidad;
						//descuento total a diminuir en total 
						descuento = descuento * cantidad;
						//validar que moneda de venta tiene el producto a aplicarse el descuento
						if (this.facturaModel.getMonedaVenta().equals("Dolar")) {
							moneda = "$";
							this.totalImporteDolar = this.totalImporteDolar - descuento;
						} else if (this.facturaModel.getMonedaVenta().equals("Córdobas")) {
							moneda = "C$";
							this.totalImporteCordobas = this.totalImporteCordobas - descuento;
						}
						//actualizar el importe y el precio
						this.modelo.setValueAt(moneda + " " + formato.format(precioUpdate), filaseleccionada, 4);
						this.modelo.setValueAt(moneda + " " + formato.format(importeUpdate), filaseleccionada, 5);
						/* ------------------------------------------ TOTALES -----------------------------------------------*/
						this.total = this.totalImporteCordobas;
						this.totalDolar = this.totalImporteDolar;
						this.totalGlobalCordobas = this.totalImporteCordobas + (this.totalImporteDolar * precioDolarVenta);
						this.totalGlobalDolar = this.totalImporteDolar + (this.totalImporteCordobas / precioDolarCompra);
						/*---------------------------------------------------------------------------------------------------*/
						//formatreo de le inpuesto IVA
						sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
						//obtengo el IVA en entero "15" o cualquier que sea el impuesto
						porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());// "descProduct = descuento de producto"
						this.isv = (float) ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
						//calcular el subtotal de la facturaModel
						this.subTotal = this.totalGlobalCordobas - this.isv;
						//setear los campos total, subtotal, IVA
						menu.txtSubTotal.setText(formato.format(this.subTotal));
						menu.txtImpuesto.setText(formato.format(this.isv));
						menu.txtTotalCordobas.setText(formato.format(this.total));
						menu.txtTotalDolar.setText(this.formato.format(this.totalDolar));
						menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
						menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
						menu.txtCodBarraFactura.requestFocus();
						spiner.setValue(0.00);
					}
				}
			} else {
				JOptionPane.showMessageDialog(
					null,
					"Seleccione un producto.",
					"Advertencia",
					JOptionPane.WARNING_MESSAGE
				);
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(
				null,
				err + " en Agregar descuento al producto en factura"
			);
		}
	}

	public void addDescuentoDirecto() {
		//variables para el nombre e id de producto
		String nombre = "", id = "", moneda = "";
		//variable para el numero de filas de la tabla facturaModel
		int filaseleccionada = 0, filas = 0, confirmar = 0;
		//variables para las operaciones
		float importeAdesminuir = 0,
			cantidad = 0,
			precioUpdate = 0,
			precio = 0,
			importeUpdate = 0,
			totalImports = 0,
			sacarImpuesto = 0,
			porcentajeImp,
			precioDolar = 0,
			descuento = 0,
			precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText()),
			precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText());
		//variable para obtener la filaseleccionada de la tabla facturaModel
		filaseleccionada = menu.tblFactura.getSelectedRow();
		try {
			//modelo va a ser igual al modelo de la tabla facturaModel
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			//obtener el nuemero de filas de la tabla facturaModel
			filas = this.modelo.getRowCount();
			//validar si hay una fila seleccionada
			if (filaseleccionada == -1) {
				JOptionPane.showMessageDialog(
					null,
					"Seleccione un producto.", "Advertencia",
					JOptionPane.WARNING_MESSAGE
				);
			} else {
				//obtengo los valores de la tabla facturaModel para las operaciones
				cantidad = Float.parseFloat(
					this.CleanChars(this.modelo.getValueAt(filaseleccionada, 2).toString())
				);
				nombre = (String) this.modelo.getValueAt(filaseleccionada, 3);
				precio = Float.parseFloat(
					this.CleanChars(this.modelo.getValueAt(filaseleccionada, 4).toString())
				);
				importeAdesminuir = Float.parseFloat(
					this.CleanChars(this.modelo.getValueAt(filaseleccionada, 5).toString())
				);
				id = (String) this.modelo.getValueAt(filaseleccionada, 0);
				this.facturaModel.monedaVentaProducto(id);
				confirmar = JOptionPane.showConfirmDialog(
					null,
					spiner,
					"Agregar descuento a " + nombre,
					JOptionPane.OK_CANCEL_OPTION
				);
				if (confirmar == JOptionPane.YES_OPTION) {
					descuento = Float.parseFloat(spiner.getValue().toString());
					//realiza el escuento
					precioUpdate = (importeAdesminuir - descuento) / cantidad;
					importeUpdate = importeAdesminuir - descuento;
					if (this.facturaModel.getMonedaVenta().equals("Dolar")) {
						this.totalImporteDolar = this.totalImporteDolar - descuento;
						moneda = "$";
					} else {
						this.totalImporteCordobas = this.totalImporteCordobas - descuento;
						moneda = "C$";
					}
					//actualizar el importe y el precio
					this.modelo.setValueAt(moneda + " " + formato.format(precioUpdate), filaseleccionada, 4);
					this.modelo.setValueAt(moneda + " " + formato.format(importeUpdate), filaseleccionada, 5);
					//formatreo de le inpuesto IVA
					sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
					//TOTAL DE FACTURA
					/* ------------------------------------------ TOTALES -----------------------------------------------*/
					this.totalGlobalCordobas = this.totalImporteCordobas + (this.totalImporteDolar * precioDolarVenta);
					this.totalGlobalDolar = this.totalImporteDolar + (this.totalImporteCordobas / precioDolarCompra);
					this.total = totalImporteCordobas;
					this.totalDolar = this.totalImporteDolar;
					/*---------------------------------------------------------------------------------------------------*/
					//obtengo el IVA en entero "15" o cualquier que sea el impuesto
					porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());// "descProduct = descuento de producto"
					this.isv = (float) ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
					//calcular el subtotal de la facturaModel
					this.subTotal = this.totalGlobalCordobas - this.isv;
					//setear los campos total, subtotal, IVA
					menu.txtSubTotal.setText("" + formato.format(this.subTotal));
					menu.txtImpuesto.setText("" + formato.format(this.isv));
					menu.txtTotalCordobas.setText("" + formato.format(this.total));
					menu.txtTotalDolar.setText(this.formato.format(this.totalDolar));
					menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
					menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
					menu.txtCodBarraFactura.requestFocus();
					spiner.setValue(0.00);
				}
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(
				null,
				err + " en Agregar descuento al producto en factura"
			);
		}
	}

	public void addDescuentoPorcentaje() {
		//variables para el nombre e id de producto
		String nombre = "", id = "", moneda = "";
		//variable para el numero de filas de la tabla facturaModel
		int filaseleccionada = 0, confirmar = 0;
		//variables para las operaciones
		float importeAdesminuir = 0,
			cantidad = 0,
			precioUpdate = 0,
			precio = 0,
			importeUpdate = 0,
			sacarImpuesto = 0,
			porcentajeImp,
			descuento = 0,
			precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText()),
			precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText());
		//variable para obtener la filaseleccionada de la tabla facturaModel
		filaseleccionada = menu.tblFactura.getSelectedRow();
		try {
			//modelo va a ser igual al modelo de la tabla facturaModel
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			//obtener el nuemero de filas de la tabla facturaModel
			//validar si hay una fila seleccionada
			if (filaseleccionada != -1) {

				//obtengo los valores de la tabla facturaModel para las operaciones
				precioDolar = Float.parseFloat(menu.txtPrecioDolarVenta.getText());
				cantidad = Float.parseFloat(
					this.CleanChars(this.modelo.getValueAt(filaseleccionada, 2).toString())
				);
				nombre = (String) this.modelo.getValueAt(filaseleccionada, 3);
				precio = Float.parseFloat(
					this.CleanChars(this.modelo.getValueAt(filaseleccionada, 4).toString())
				);
				importeAdesminuir = Float.parseFloat(
					this.CleanChars(this.modelo.getValueAt(filaseleccionada, 5).toString())
				);
				id = (String) this.modelo.getValueAt(filaseleccionada, 0);
				this.facturaModel.monedaVentaProducto(id);
				confirmar = JOptionPane.showConfirmDialog(null, spiner, "Agregar descuento a " + nombre, JOptionPane.OK_CANCEL_OPTION);
				if (confirmar == JOptionPane.YES_OPTION) {
					descuento = Float.parseFloat(spiner.getValue().toString());
					importeUpdate = importeAdesminuir - (importeAdesminuir * descuento / 100);
					//realiza el escuento
					precioUpdate = importeUpdate / cantidad;
					if (this.facturaModel.getMonedaVenta().equals("Dolar")) {
						this.totalImporteDolar = this.totalImporteDolar - (importeAdesminuir * descuento / 100);
						moneda = "$";
					} else {
						this.totalImporteCordobas = this.totalImporteCordobas - (importeAdesminuir * descuento / 100);
						moneda = "C$";
					}

					/*  establecer el nuevo importe y el nuevo precio con el descuento  */
					this.modelo.setValueAt(moneda + " " + formato.format(precioUpdate), filaseleccionada, 4);
					this.modelo.setValueAt(moneda + " " + formato.format(importeUpdate), filaseleccionada, 5);

					//formatreo de le inpuesto IVA
					sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
					//TOTAL DE FACTURA
					/*-------------------------------------- TOTALES --------------------------------------------------*/
					this.totalGlobalCordobas = this.totalImporteCordobas + (this.totalImporteDolar * precioDolarVenta);
					this.totalGlobalDolar = this.totalImporteDolar + (this.totalImporteCordobas / precioDolarCompra);
					this.total = this.totalImporteCordobas;
					this.totalDolar = this.totalImporteDolar;
					/*-------------------------------------------------------------------------------------------------*/
					//obtengo el IVA en entero "15" o cualquier que sea el impuesto
					porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());// "descProduct = descuento de producto"
					this.isv = (float) ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
					//calcular el subtotal de la facturaModel
					this.subTotal = this.totalGlobalCordobas - this.isv;
					//setear los campos total, subtotal, IVA
					menu.txtSubTotal.setText("" + formato.format(this.subTotal));
					menu.txtImpuesto.setText("" + formato.format(this.isv));
					menu.txtTotalCordobas.setText("" + formato.format(this.total));
					menu.txtTotalDolar.setText(this.formato.format(this.totalDolar));
					menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
					menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
					menu.txtCodBarraFactura.requestFocus();
					spiner.setValue(0.00);
				}
			} else {
				JOptionPane.showMessageDialog(
					null,
					"Seleccione un producto.",
					"Advertencia",
					JOptionPane.WARNING_MESSAGE
				);
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(
				null,
				err + " en Agregar descuento al producto en factura"
			);
		}
	}

	public void agregarProductoBoton() {
		String codBarra = menu.txtCodBarraFactura.getText();
		String precioDolar = menu.txtPrecioDolarVenta.getText(), id = "";
		int filas = 0;
		float totalImports = 0,
			sacarImpuesto = 0,
			porcentajeImp = 0,
			cantidadUpdate = 0,
			importeUpdate = 0,
			cantidadActual = 0,
			precio = 0;
		if (!menu.isNumeric(precioDolar) || precioDolar.equals("0")) {
			menu.txtPrecioDolarVenta.setText("1");
		} else {
			precioDolar = menu.txtPrecioDolarVenta.getText();
			this.facturaModel.obtenerPorCodBarra(codBarra);
			if (this.facturaModel.getProducto()[0] != null) {
				this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
				this.modelo.addRow(this.facturaModel.getProducto());
				filas = this.modelo.getRowCount();
				this.facturaModel.Vender(this.facturaModel.getProducto()[0], this.facturaModel.getProducto()[2]);
				for (int i = 0; i < filas; i++) {
					totalImports += Float.parseFloat(this.modelo.getValueAt(i, 5).toString());
				}
				sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
				//obtengo el IVA en entero "15" o cualquier que sea el impuesto
				porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());// "descProduct = descuento de producto"
				this.total = totalImports;
				this.isv = ((this.total / sacarImpuesto) * porcentajeImp) / 100;
				this.subTotal = this.total - this.isv;
				menu.txtSubTotal.setText("" + formato.format(this.subTotal));
				menu.txtImpuesto.setText("" + formato.format(this.isv));
				menu.txtTotalCordobas.setText("" + formato.format(this.total));
				menu.txtCodBarraFactura.setText("");
				DeshabilitarBtnGuardarFactura();
				menu.txtCodBarraFactura.requestFocus();
			} else {
				JOptionPane.showMessageDialog(null, "El producto no esta ingresado...");
				menu.txtCodBarraFactura.setText("");
			}
		}
	}

	public void actualizarFactura() {
		try {
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();//obtengo el modelo de tabla facturaModel y sus datos
			int filas = this.modelo.getRowCount();//Cuento las filas de la tabla Factura
			/*nD quiere decir numero de detalles condicion para guardar solo los cambios de las filas de la facturaModel actual no
			se pueda agregar mas productos ni quitar solo cambiar ya que solo es edicio de la facura*/
			if (filas == nD.length) {
				this.banderaActualizacion = false;
				this.validar();
				this.facturaModel.setId(idfactura);
				this.facturaModel.setCaja(1);
				this.facturaModel.setFecha(this.fechaFactura);
				this.facturaModel.setNombreComprador(this.comprador);
				this.facturaModel.setCredito(this.idCredito);
				this.facturaModel.setPago(this.idFormaPago);
				this.facturaModel.setIva(this.isv);
				this.facturaModel.setTotalCordobas(this.total);
				this.facturaModel.ActualizarFactura();//envio los datos a actualizar de la facturaModel
				for (int cont = 0; cont < filas; cont++)//for para recorrer la tabla facturaModel
				{
					//capturo el id de producto para guardar en detallefactura
					this.idProducto = Integer.parseInt(this.modelo.getValueAt(cont, 0).toString());
					//capturo la cantidad de producto de la columna dos y la paso a String para guardar en detallefactura
					this.cantidad = Float.parseFloat(this.modelo.getValueAt(cont, 2).toString());
					//capturo el precio de producto para guardar en detallefactura
					this.precio = Float.parseFloat(this.modelo.getValueAt(cont, 4).toString());
					//capturo el total de detalle compra de producto para guardar en detallefactura
					this.totalDetalle = Float.parseFloat(this.modelo.getValueAt(cont, 5).toString());
					//envio los datos para actualizar los detalles de la facturaModel
					this.facturaModel.ActualizarDetalle(nD[cont], idProducto, precio, cantidad, totalDetalle);
					//this.facturaModel.Vender(id, cantidad);//funcion para diminuir el stock segun la cantidad que se venda
				}
				//Actualizo el campo numero de facturaModel con la funcion obtenerIdFactura
				menu.txtNumeroFactura.setText(this.facturaModel.ObtenerIdFactura());
				//limpio la facturaModel
				LimpiarTablaFactura();
				menu.txtCreditoFactura.setText("");
				//deshabilito el boton guadar facturaModel
				DeshabilitarBtnGuardarFactura();
				menu.pnlVentas.setVisible(false);
				menu.pnlReportes.setVisible(true);
				menu.btnActualizarFactura.setVisible(false);
				menu.btnVentas.setVisible(true);
				menu.btnReportes.setVisible(true);
				menu.btnCerrarSesion.setVisible(true);
				menu.btnUsuarios.setVisible(true);
				menu.btnClientes.setVisible(true);
				menu.btnNotificaciones.setVisible(true);
				menu.btnTransacciones.setVisible(true);
				menu.btnInventario.setVisible(true);
				menu.btnInfoFactura.setVisible(true);
				menu.btnAgregar.setEnabled(true);
				menu.txtCodBarraFactura.setEnabled(true);
				//deshabilitar boton guardarFactura
				//menu.btnGuardarFactura.setEnabled(true);
				//deshabilitar boton AgregarProducto a facturaModel
				menu.btnAgregarProductoFactura.setEnabled(true);
				//deshabilitar boton Nueva Factura
				menu.btnNuevaFactura.setEnabled(true);
				//deshabilitar boton EliminarFila Factura
				menu.btnEliminarFilaFactura.setEnabled(true);
				menu.jcFechaFactura.setDate(this.fecha);
				//actualizar la tabla de productos a vender
				this.mostrarProductosVender("");
				this.estadosCreditos.updateApendiente(idCredito);
				this.estadosCreditos.updateAabierto(idCredito);
				this.reportesDiarios(this.fecha);
				//actualizar reportes
				this.MostrarReportesDario(this.fecha);
				//actualizar datos de reportes
				//actualizar Dato de Inversion
				//Actualizar creditos
				this.MostrarCreditos("");
				//actualizar los creditos en facturaModel
				this.MostrarCreditosAddFactura("");
			} else {
				JOptionPane.showMessageDialog(null, "La factura depende de " + nD.length + " filas");
			}

		} catch (NullPointerException err) {
			JOptionPane.showMessageDialog(null, err);
		}
	}

	public void editarFactura() {
		int filaF = menu.tblFactura.getRowCount();
		//APARTADO PARA LIMPIAR TABLA DE FACTURACION Y DEVOLVER AL INVENTARIO LOS PRODUCTOS EXISTENTES EN LA MISMA
		String cP, idProd;
		for (int i = 0; i < filaF; i++) {
			idProd = menu.tblFactura.getValueAt(i, 0).toString();
			cP = menu.tblFactura.getValueAt(i, 2).toString();
			//modelProduct.AgregarProductoStock(idProd, cP,1);
		}
		LimpiarTablaFactura();
		//RECOLECCION DE DATOS PARA LA EDICION
		String nombre = "", apellido = "";
		//obtengo la fila seleccionda de la tabla reporte diario    obtengo el numero las filas de la tabla detalleFactura
		int filaseleccionada = menu.tblReporte.getSelectedRow();

		int filas = menu.tblMostrarDetalleFactura.getRowCount();
		//la variable modelo va a tomar el modelo de la tabla facturaModel
		this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
		//variables para obtener los valores que se ocupan para la actualizacion

		//convertir el formato sql a Date con simpleDateFormat
		SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
		//nD quiere decir numero de detalles es la variable que guarda el numero de detalles que van en la facturaModel a editar
		this.nD = new String[filas];
		try {
			//idFactura obtiene el id de facturaModel de la tabla reporte diario
			this.idfactura = Integer.parseInt(menu.tblReporte.getValueAt(filaseleccionada, 0).toString());
			this.facturaModel.setId(this.idfactura);
			this.facturaModel.editar();
			//lleno los campos del formulario facturaModel
			menu.jcFechaFactura.setDate(this.facturaModel.getFecha());
			menu.txtSubTotal.setText("");
			menu.txtTotalCordobas.setText("" + this.facturaModel.getTotalCordobas());
			menu.txtImpuesto.setText("" + this.facturaModel.getIva());
			menu.txtNumeroFactura.setText("" + this.facturaModel.getId());
			menu.txtCompradorFactura.setText(this.facturaModel.getNombreComprador());
			menu.txtNClienteFactura.setText(this.facturaModel.getNombres());
			menu.txtAClienteFactura.setText(this.facturaModel.getApellidos());
			menu.txtCreditoFactura.setText("" + this.facturaModel.getCredito());
			menu.cmbFormaPago.setSelectedItem(this.facturaModel.getFormapago());
			String detalle,
				codBarra,
				nombreP,
				cantidadP,
				precioP,
				importe,
				idP;
			//for para recorrer la tabla detalleFactura
			for (int i = 0; i < filas; i++) {
				detalle = menu.tblMostrarDetalleFactura.getValueAt(i, 0).toString();//obtengo numero de detalle
				idP = menu.tblMostrarDetalleFactura.getValueAt(i, 1).toString();//obtengo id de producto
				codBarra = menu.tblMostrarDetalleFactura.getValueAt(i, 2).toString();//obtengo cod barra del producto
				nombreP = menu.tblMostrarDetalleFactura.getValueAt(i, 3).toString();//obtengo nombre del producto
				cantidadP = menu.tblMostrarDetalleFactura.getValueAt(i, 4).toString();
				precioP = menu.tblMostrarDetalleFactura.getValueAt(i, 5).toString();//obtengo precio del producto
				importe = menu.tblMostrarDetalleFactura.getValueAt(i, 6).toString();//obtengo total de venta del producto

				nD[i] = detalle;//lleno el array los id correspondiente a cada detalles
				String[] addFila = {idP, codBarra, cantidadP, nombreP, precioP, importe};
				this.modelo.addRow(addFila);//agrego la fila de array creado anteriormente a la tabla facturaModel para la edicion
			}
			//System.out.println(nD[0]+" "+nD[1]);  
			menu.pnlVentas.setVisible(true);//mostrar panel de ventas 
			menu.btnActualizarFactura.setVisible(true);//mostrar boton actualizar
			menu.btnGuardarFactura.setEnabled(false);//deshabilitar boton guardarFactura
			menu.btnAgregarProductoFactura.setEnabled(false);//deshabilitar boton AgregarProducto a facturaModel
			menu.btnNuevaFactura.setEnabled(false);//deshabilitar boton Nueva Factura
			menu.btnEliminarFilaFactura.setEnabled(false);//deshabilitar boton EliminarFila Factura
			menu.pnlReportes.setVisible(false);//ocultar panel Reportes
			menu.btnInfoFactura.setVisible(false);
			menu.vistaDetalleFacturas.setVisible(false);//ocultar la ventana de detalle de facturaModel de reportes
			menu.btnVentas.setVisible(false);
			menu.btnReportes.setVisible(false);
			menu.btnCerrarSesion.setVisible(false);
			menu.btnUsuarios.setVisible(false);
			menu.btnClientes.setVisible(false);
			menu.btnAgregar.setEnabled(false);
			menu.txtCodBarraFactura.setEnabled(false);
			menu.btnNotificaciones.setVisible(false);
			menu.btnTransacciones.setVisible(false);
			menu.btnInventario.setVisible(false);
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err + "editar Factura");
		}
	}

	public void eliminarFilaFactura() {
		int filaseleccionada = menu.tblFactura.getSelectedRow();
		float importe, totalActual, sacarImpuesto, porcentajeImp,
			precioDolarVenta = (!isNumeric(this.menu.txtPrecioDolarVenta.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText()),
			precioDolarCompra = (!isNumeric(this.menu.txtPrecioDolarCompra.getText()))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarCompra.getText());
		String cantidad, id;
		try {
			if (filaseleccionada != -1) {
				this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
				id = (String) modelo.getValueAt(filaseleccionada, 0);
				cantidad = (String) modelo.getValueAt(filaseleccionada, 2);
				importe = Float.parseFloat(
					this.CleanChars(modelo.getValueAt(filaseleccionada, 5).toString())
				);
				this.facturaModel.monedaVentaProducto(id);
				if (this.facturaModel.getMonedaVenta().equals("Dolar")) {
					this.totalImporteDolar = this.totalImporteDolar - importe;
				} else {
					this.totalImporteCordobas = this.totalImporteCordobas - importe;
				}
				//formatreo de le inpuesto IVA
				sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
				//obtengo el IVA en entero "15" o cualquier que sea el impuesto
				porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());// "descProduct = descuento de producto"
				this.total = this.totalImporteCordobas;
				this.totalDolar = this.totalImporteDolar;
				this.totalGlobalCordobas = this.totalImporteCordobas + (this.totalImporteDolar * precioDolarVenta);
				this.totalGlobalDolar = this.totalImporteDolar + (this.totalImporteCordobas / precioDolarCompra);
				this.isv = ((this.totalGlobalCordobas / sacarImpuesto) * porcentajeImp) / 100;
				this.subTotal = this.totalGlobalCordobas - isv;
				menu.txtTotalCordobas.setText("" + formato.format(this.total));
				menu.txtTotalDolar.setText(this.formato.format(this.totalDolar));
				menu.txtTotalGlobalCordobas.setText(this.formato.format(this.totalGlobalCordobas));
				menu.txtTotalGolbalDolar.setText(this.formato.format(this.totalGlobalDolar));
				menu.txtSubTotal.setText("" + formato.format(this.subTotal));
				menu.txtImpuesto.setText("" + formato.format(this.isv));
				modelProduct.AgregarProductoStock(id, cantidad,0);
				this.mostrarProductosVender("");
				this.modelo.removeRow(filaseleccionada);
				DeshabilitarBtnGuardarFactura();
				menu.txtCodBarraFactura.requestFocus();
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}
	}

	public void nuevaFactura() {
		DeshabilitarBtnGuardarFactura();
		try {
			String id, cantidad;
			this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
			int filas = this.modelo.getRowCount();
			for (int i = 0; i < filas; i++) {
				id = (String) this.modelo.getValueAt(i, 0);
				cantidad = (String) this.modelo.getValueAt(i, 2);
				//modelProduct.AgregarProductoStock(id, cantidad);
			}
			LimpiarTablaFactura();
			DeshabilitarBtnGuardarFactura();
			menu.txtCodBarraFactura.requestFocus();
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err);
		}
	}

	public void actualizarIVA() {
		int confirmar = JOptionPane.showConfirmDialog(null, spiner, "Valor de Impuesto IVA:", JOptionPane.OK_CANCEL_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			menu.lblImpuestoISV.setText(spiner.getValue().toString());
			spiner.setValue(0.00);
		}
	}

	public void limpiarFormularioClienteFactura() {
		menu.txtNClienteFactura.setText("");
		menu.txtAClienteFactura.setText("");
		menu.txtCreditoFactura.setText("");
		menu.cmbFormaPago.setSelectedItem("Efectivo");
	}

	public void validarPermiso() {
		if (this.permiso == 1) {
//            menu.addDescuentoDirecto.setEnabled(false);
//            menu.addDescuentoPorcentaje.setEnabled(false);
		} else if (this.permiso == 2) {
			menu.addDescuentoDirecto.setEnabled(false);
			menu.addDescuentoPorcentaje.setEnabled(false);
		}
	}

	public void updateNumberFactura(String number) {
		this.menu.txtNumeroFactura.setText(number);
	}

	public void mostrarProductosVender(String Buscar) {

		menu.tblAddProductoFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblAddProductoFactura.getTableHeader().setOpaque(false);
		menu.tblAddProductoFactura.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblAddProductoFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblAddProductoFactura.setModel(facturaModel.BusquedaGeneralProductoVender(Buscar));
		menu.tblAddProductoFactura.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
	}

	//metodo para mostrar los productos a vender por filtro de Marca
	public void MostrarPorMarca(String laboratorio) {
		menu.tblAddProductoFactura.setModel(facturaModel.BuscarPorMarca(laboratorio));
	}

	//metodo para mostrar los productos a vender por filtro de Categoria
	public void MostrarPorCategoria(String categoria) {
		menu.tblAddProductoFactura.setModel(facturaModel.BuscarPorCategoria(categoria));
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

	public void MostrarReportesDario(Date fecha1)//metodo para llenar la tabla de reortes por rango o mensual del menu reportes
	{
		long f1 = fecha1.getTime();//
		java.sql.Date fechaInicio = new java.sql.Date(f1);//convertir la fecha a formato sql
		menu.tblReporte.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblReporte.getTableHeader().setOpaque(false);
		menu.tblReporte.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblReporte.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblReporte.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		try {
			menu.tblReporte.setModel(reportes.ReporteDiario(fechaInicio));
		} catch (Exception err) {

		}

	}

	public void reportesDiarios(Date fecha) {
		float totalVendidio = 0,
			exisCaja = 0,
			ingresosVentaE = 0,
			ingresosVentasT = 0,
			ingresosPagosE,
			ingresosPagoT = 0,
			ingresosEfectivo = 0,
			Ingresosbancos = 0,
			creditos = 0,
			egresos = 0,
			base = 0,
			dolaresComprados = 0,
			dolaresVendidos = 0,
			totalCordobasPorCompraDolar = 0,
			totalCordobasPorVentaDolar = 0,
			diferenciaEnCordobas = 0,
			utilidad = 0;
		float precioCompraDolar = Float.parseFloat(menu.txtPrecioDolarCompra.getText()),
			precioVentaDolar = Float.parseFloat(menu.txtPrecioDolarVenta.getText());
		long f1 = fecha.getTime();
		java.sql.Date fechaInicio = new java.sql.Date(f1);//convertir la fecha a formato sql
		reportes.ventasEfectivoDiario(fechaInicio);
		reportes.abonosEfectivoCordobasDiario(fechaInicio);
		reportes.ingresoEfectivoDiarioCordobas(fechaInicio);
		reportes.ventasCreditoDiario(fechaInicio);
		reportes.salidaEfectivoDiarioCordobas(fechaInicio);
		reportes.abonosTarjetaCordobasDiario(fechaInicio);
		reportes.IngresosTotalesDiario(fechaInicio);
		//base
		base = reportes.baseEfectivoDiario(fechaInicio);
		//ingresos por ventas en efectivo
		ingresosVentaE = this.reportes.getVentasEfectivoDiarioCordobas();
		//ingresos por ventas cobradas con tarjeta
		//ingresos por pagos cobrados en efectivo
		ingresosPagosE = this.reportes.getAbonosDiariosCordobas();
		//ingresos por pagos cobrados con tarjeta
		ingresosPagoT = this.reportes.getAbonosTajetaCordobasDiario();
		//ingresos totales diarios en efectivo
		ingresosEfectivo
			= this.reportes.getVentasEfectivoDiarioCordobas()
			+ this.reportes.getAbonosDiariosCordobas()
			+ this.reportes.getIngresoDiarioCordobas();
		//ingreso a bancos por ventas con tarjeta y pagos con tarjeta diarios
		//Ingresosbancos = reportes.IngresoAbancosDiarioCordobas(fechaInicio) + reportes.abonosTarjetaCordobasDiario(fechaInicio);
		//creditos realizados 
		creditos = reportes.getVentasCreditoDiarioCordobas();
		//egresos realizados
		egresos = this.reportes.getSalidaDiarioCordobas();
		//total vendido
		totalVendidio = this.reportes.getVentasTotalesCordobasDiario();
		//Margen de utilidad
		/*reportes.setPrecioDolar(precioVentaDolar);
        utilidad = reportes.precioVenta(fechaInicio);*/
		//existencia real en caja
		exisCaja = (ingresosEfectivo + base) - egresos;
		//ejecutar funcion para obtener los dolares y cordobas recibidos
		reportes.MonedasRecibidas(fechaInicio);
		reportes.EgresoMonedas(fechaInicio);
		//dolares recibidos
		dolaresVendidos = reportes.getDolares() - reportes.getEgresoDolares();
		//cordobas recibidos
		reportes.MonedasRecibidasCompra(fechaInicio);
		dolaresComprados = reportes.getDolaresComprados();
		//dolares convertidos a cordobas
		totalCordobasPorCompraDolar = dolaresComprados * precioCompraDolar;
		totalCordobasPorVentaDolar = dolaresVendidos * precioVentaDolar;
		//difencia en cordobas para el cuadre del reporte diario
		diferenciaEnCordobas = exisCaja - (totalCordobasPorCompraDolar + totalCordobasPorVentaDolar);
		//llenar los lbls
//		menu.lblBase.setText("" + this.formato.format(base));
//		menu.lblVentasEfectivoDiario.setText("" + this.formato.format(ingresosVentaE));
//		menu.lblVentasTarjetaDiario.setText("" + this.formato.format(ingresosVentasT));
//		menu.lblIngresosPagosEfectivoDiario.setText("" + this.formato.format(ingresosPagosE));
//		menu.lblIngresosPagosTarjetaDiario.setText("" + this.formato.format(ingresosPagoT));
//		menu.lblIngresoEfectivo.setText("" + this.formato.format(reportes.getIngresoDiarioCordobas()));
//		menu.lblCreditosDiarios.setText("" + this.formato.format(creditos));
//		menu.lblEgresosDiarios.setText("" + this.formato.format(egresos));
//		menu.lblTotalExistenciaCajaDiario.setText("" + this.formato.format(exisCaja));
//		menu.lblIngresosBancosDiario.setText("" + this.formato.format(Ingresosbancos));
//		menu.lbltotalVendidoDiario.setText("" + this.formato.format(totalVendidio));
		//menu.lblTotalUtilidadDiario.setText(""+this.formato.format(utilidad));
		/*menu.lblCantidadCordobas.setText("" + this.formato.format(dolaresComprados));
		menu.lblCantidadDolares.setText("" + this.formato.format(dolaresVendidos));
		menu.lblPrecioCompraDolarEnCordobas.setText("" + precioCompraDolar);
		menu.lblPrecioVentaDolarEnCordobas.setText("" + precioVentaDolar);
		menu.lblTotalCordobasPorCompraDolar.setText("" + this.formato.format(totalCordobasPorCompraDolar));
		menu.lblTotalCordobasPorVentaDolar.setText("" + this.formato.format(totalCordobasPorVentaDolar));
		menu.lblDiferenciaEnCordobas.setText("" + this.formato.format(diferenciaEnCordobas));*/
	}

	public void MostrarCreditos(String buscar) {
		menu.tblCreditos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblCreditos.getTableHeader().setOpaque(false);
		menu.tblCreditos.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblCreditos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblCreditos.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		menu.tblCreditos.setModel(this.creditos.Mostrar(buscar));
	}

	public void MostrarCreditosAddFactura(String buscar) {
		menu.tblAddCreditoFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblAddCreditoFactura.getTableHeader().setOpaque(false);
		menu.tblAddCreditoFactura.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblAddCreditoFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.jcFechaCredito.setDate(this.fecha);
		menu.tblAddCreditoFactura.setModel(creditos.MostrarCreditosAddFactura(buscar));
	}
}
