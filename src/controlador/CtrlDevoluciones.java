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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import vista.IMenu;
import modelo.Reportes;
import modelo.Facturacion;
import modelo.Productos;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlDevoluciones implements ActionListener, WindowListener {

	float precio = 0,
		cantidadActual = 0,
		total = 0,
		totalDolares,
		totalUpdate = 0,
		totalGlobalCordobas = 0,
		ivaUpdate = 0,
		subTotalUpdate = 0,
		cantidadUpdate = 0,
		cantidadDevolver = 0,
		sacarImpuesto = 0,
		porcentajeImp = 0,
		importe = 0,
		restar = 0,
		precioDolar;
	int idProducto = 0,
		filaseleccionada = 0,
		filaseleccionadaR = 0,
		idDetalle = 0,
		idFactura = 0,
		filas,
		credito = 0;
	String restarString = "";
	IMenu menu;
	Reportes reportes;
	Facturacion factura;
	Productos producto;
	EstadoCreditos estadoCredito;
	DefaultTableModel modelo;
	JSpinner spiner;
	SpinnerNumberModel sModel;
	Date fecha;
	DecimalFormat formato;

	public CtrlDevoluciones(IMenu menu, Reportes reportes) {
		this.menu = menu;
		this.reportes = reportes;
		formato = new DecimalFormat("#############.##");
		this.factura = new Facturacion();
		this.producto = new Productos();
		this.estadoCredito = new CambioEstadoCreditoPagos();
		this.modelo = new DefaultTableModel();
		menu.btnDevolverProducto.addActionListener(this);
		this.menu.vistaDetalleFacturas.addWindowListener(this);
		this.sModel = new SpinnerNumberModel();
		this.sModel.setMinimum(0.00);
		this.sModel.setValue(0.00);
		this.sModel.setStepSize(0.01);
		this.spiner = new JSpinner(sModel);
		this.fecha = new Date();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.btnDevolverProducto) {
			DevolverProducto();
		}
	}

	public boolean isNumeric(String dato) {
		try {
			Float.parseFloat(dato);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	//funcion para devolver productos
	public void DevolverProducto() {
		this.fecha = menu.jcFacturasEmitidas.getDate();
		String credito;
		this.precioDolar = (!isNumeric(menu.txtPrecioDolarVenta.getText())) ? 1 : Float.parseFloat(menu.txtPrecioDolarVenta.getText());

		this.modelo = (DefaultTableModel) menu.tblMostrarDetalleFactura.getModel();
		try {
			//numero de filas
			filas = this.modelo.getRowCount();
			//fila seleccionada de la tabla DetalleFactura
			filaseleccionada = menu.tblMostrarDetalleFactura.getSelectedRow();
			//fila seleccionada de la tabla reportes
			filaseleccionadaR = menu.tblReporte.getSelectedRow();
			//concatenacion para sacar el valor 1.xx para sacar el iva
			sacarImpuesto = Float.parseFloat(1 + "." + menu.lblImpuestoISV.getText());
			//obtengo el IVA en entero "15" o cualquier que sea el impuesto
			// "descProduct = descuento de producto"
			porcentajeImp = Float.parseFloat(menu.lblImpuestoISV.getText());

			if (filaseleccionada != -1) {
				int confirmar = JOptionPane.showConfirmDialog(null, spiner, "Cantidad a devolver:", JOptionPane.OK_CANCEL_OPTION);
				if (confirmar == JOptionPane.YES_OPTION) {
					this.cantidadDevolver = Float.parseFloat(spiner.getValue().toString());
					this.idDetalle = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
					this.idProducto = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 1).toString());
					this.precio = Float.parseFloat(this.modelo.getValueAt(filaseleccionada, 5).toString());
					this.cantidadActual = Float.parseFloat(this.modelo.getValueAt(filaseleccionada, 4).toString());
					this.idFactura = Integer.parseInt(menu.tblReporte.getValueAt(filaseleccionadaR, 0).toString());
					credito = (String) menu.tblReporte.getValueAt(filaseleccionadaR, 7);
					this.credito = (credito != null) ? Integer.parseInt(credito): 0;
					this.factura.obtenerTotalFacturaSeleccionada(idFactura);
					this.total = this.factura.getTotalCordobas();
					this.totalDolares = this.factura.getTotalDolar();
					this.factura.monedaVentaProducto(String.valueOf(idProducto));
					//validar que lo que se va a devolver sea menor o igual que lo que compro
					if (cantidadDevolver <= cantidadActual) {
						cantidadUpdate = cantidadActual - cantidadDevolver;
						//validar que moneda
						if (this.factura.getMonedaVenta().equals("Dolar")) {
							this.importe = (cantidadUpdate * precio);
							this.totalDolares = this.importe;
						} else {
							this.importe = this.cantidadUpdate * this.precio;
							this.total = this.importe;
						}
						//totalGlobal para calcular el impuesto
						this.totalGlobalCordobas = this.total + (this.totalDolares * this.precioDolar);
						//calcular el nuevo impuesto
						this.ivaUpdate = ((this.totalGlobalCordobas / this.sacarImpuesto) * this.porcentajeImp) / 100;
						//calcular el nuevo subtotal
						this.subTotalUpdate = this.totalGlobalCordobas - this.ivaUpdate;
						try {
							//llamar las funciones para actualizar los datos correpondientes
							this.factura.ActualizarDetalle(
								String.valueOf(this.idDetalle),
								this.idProducto,
								this.precio,
								this.cantidadUpdate,
								this.importe
							);
							this.factura.ActualizarDevolucion(
								this.idFactura,
								this.ivaUpdate,
								this.total,
								this.totalDolares
							);
							this.producto.AgregarProductoStock(
								String.valueOf(this.idProducto),
								String.valueOf(this.cantidadDevolver)
							);
							MostrarDetalleFactura(this.idFactura);
							MostrarProductos("");
							MostrarProductosVender("");
							if (this.credito != 0) {
								this.estadoCredito.updateApendiente(this.credito);
								this.estadoCredito.updateAabierto(this.credito);
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
								null,
								"Oops. Ocurrio un error al intentar hacer la devolución \n"
								+ "ERROR : " + e + " en el metodo devolverProducto en el ctrl Devoluciones.");
						}

					}
				}
			} else {

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage() + " en la funcion DevolverProducto en ctrlDevoluciones");
			ex.printStackTrace();
		}
	}

	public void MostrarDetalleFactura(int id)//metodo para llenar la tabla que muestra el detalle de las facturas de reportes
	{
		menu.tblMostrarDetalleFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblMostrarDetalleFactura.getTableHeader().setOpaque(false);
		menu.tblMostrarDetalleFactura.getTableHeader().setBackground(new Color(100, 100, 100));
		menu.tblMostrarDetalleFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblMostrarDetalleFactura.setModel(reportes.DetalleFactura(id));
	}

	public void MostrarReportesDario(Date fecha1)//metodo para llenar la tabla de reortes por rango o mensual del menu reportes
	{
		long f1 = fecha1.getTime();//
		java.sql.Date fechaInicio = new java.sql.Date(f1);
		menu.tblReporte.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblReporte.getTableHeader().setOpaque(false);
		menu.tblReporte.getTableHeader().setBackground(new Color(100, 100, 100));
		menu.tblReporte.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblReporte.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		try {
			menu.tblReporte.setModel(reportes.ReporteDiario(fechaInicio));

		} catch (Exception err) {

		}

	}

	public void MostrarProductos(String buscar) {
		menu.tblProductos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblProductos.getTableHeader().setOpaque(false);
		menu.tblProductos.getTableHeader().setBackground(new Color(100, 100, 100));
		menu.tblProductos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblProductos.setModel(this.producto.Consulta(buscar));
	}

	public void MostrarProductosVender(String Buscar) {
		menu.tblAddProductoFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblAddProductoFactura.getTableHeader().setOpaque(false);
		menu.tblAddProductoFactura.getTableHeader().setBackground(new Color(100, 100, 100));
		menu.tblAddProductoFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblAddProductoFactura.setModel(factura.BusquedaGeneralProductoVender(Buscar));
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		MostrarReportesDario(this.fecha);

	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
