/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import vista.IMenu;
import modelo.Configuraciones;
import modelo.Creditos;
import modelo.PagosCreditos;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlImprimirReport extends PrintReportes implements ActionListener {

	IMenu menu;
	Configuraciones info;
	Creditos creditosModel;
	static int nCredito;
	String cliente;

	DefaultTableModel modelo;

	public CtrlImprimirReport(IMenu menu, Configuraciones info) {
		this.menu = menu;
		this.info = info;
		this.creditosModel = new Creditos();
		this.modelo = new DefaultTableModel();
		this.menu.btnMostrarInversion.addActionListener(this);
		this.menu.btnImprimirReporteDiario.addActionListener(this);
		this.menu.btnImprimirTotalV.addActionListener(this);
		this.menu.btnImprimirReporteGlobal.addActionListener(this);
		this.menu.btnImprimirPmasV.addActionListener(this);
		this.menu.btnReImprimirFactura.addActionListener(this);
		this.menu.btnImprimirHistorialCrediticio.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.btnImprimirReporteGlobal) {
			imprimirReporteGlobal();
		}

		if (e.getSource() == menu.btnImprimirReporteDiario) {
			imprimirReporteDiarioCordobas();
			imprimirReporteDiarioDolares();
		}

		if (e.getSource() == menu.btnImprimirTotalV) {
			imprimirTotalV();
		}
		if (e.getSource() == menu.btnImprimirPmasV) {
			imprimirPmasV();
		}

		if (e.getSource() == menu.btnReImprimirFactura) {
			ReImprimirFactura();
		}
		if (e.getSource() == menu.btnImprimirHistorialCrediticio) {
			ImprimirInfoCrediticia();
		}
	}

	public void imprimirReporteGlobal() {
		info.obtenerInfoFactura();
		String nombreTienda = info.getNombre();
		String efectivoB = menu.lblIngresosCajaMes.getText(),
			ventasT = menu.lblIngresosVentasTarjetaMes.getText(),
			pagosE = menu.lblIngresosPagosEfectivoMes.getText(),
			pagosT = menu.lblIngresosPagosTarjetaMes.getText(),
			creditos = menu.lblCreditosFiltro.getText(),
			existCaja = menu.lblExistenciaCajaFiltro.getText(),
			bancos = menu.lblIngresosBancoFiltro.getText(),
			totalV = menu.lblTotalVendidoFiltro.getText(),
			egresos = menu.lblEgresosFiltro.getText();
		try {
			print("Global");
		} catch (Exception err) {
			//JOptionPane.showMessageDialog(null, err+" en la funcion de btn ImprimirReporteglobal");
		}
	}

	public void imprimirReporteDiarioCordobas() {
		info.obtenerInfoFactura();
		String nombreTienda = info.getNombre();
		Date fechaInicio = menu.jcFechaReporteDario.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
		String fechaR = sdf.format(fechaInicio);
		setFecha(fechaR);
		setBase(menu.tblReporteDiarioCordobas.getValueAt(0, 2).toString());
		setVentasEfectivo(menu.tblReporteDiarioCordobas.getValueAt(1, 2).toString());
		setVentasT(menu.tblReporteDiarioCordobas.getValueAt(2, 2).toString());
		setPagosE(menu.tblReporteDiarioCordobas.getValueAt(3, 2).toString());
		setPagosT(menu.tblReporteDiarioCordobas.getValueAt(4, 2).toString());
		setCreditos(menu.tblReporteDiarioCordobas.getValueAt(6, 2).toString());
		setEgreso(menu.tblReporteDiarioCordobas.getValueAt(7, 2).toString());
		setExistCaja(menu.tblReporteDiarioCordobas.getValueAt(10, 2).toString());
		setBancos(menu.tblReporteDiarioCordobas.getValueAt(9, 2).toString());
		setTotalV(menu.tblReporteDiarioCordobas.getValueAt(8, 2).toString());
		setIngresosE(menu.tblReporteDiarioCordobas.getValueAt(5, 2).toString());
		llenarTicketDiarioCordobas();
		try {
			print("DiarioCordobas");
		} catch (Exception err) {
			err.printStackTrace();
			//JOptionPane.showMessageDialog(null, "");
		}
	}

	public void imprimirReporteDiarioDolares() {
		info.obtenerInfoFactura();
		String nombreTienda = info.getNombre();
		Date fechaInicio = menu.jcFechaReporteDario.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
		String fechaR = sdf.format(fechaInicio);
		setFecha(fechaR);
		setVentasEfectivo(menu.tblReporteDiarioDolares.getValueAt(0, 2).toString());
		setVentasT(menu.tblReporteDiarioDolares.getValueAt(1, 2).toString());
		setPagosE(menu.tblReporteDiarioDolares.getValueAt(2, 2).toString());
		setPagosT(menu.tblReporteDiarioDolares.getValueAt(3, 2).toString());
		setCreditos(menu.tblReporteDiarioDolares.getValueAt(5, 2).toString());
		setEgreso(menu.tblReporteDiarioDolares.getValueAt(6, 2).toString());
		setExistCaja(menu.tblReporteDiarioDolares.getValueAt(9, 2).toString());
		setBancos(menu.tblReporteDiarioDolares.getValueAt(8, 2).toString());
		setTotalV(menu.tblReporteDiarioDolares.getValueAt(7, 2).toString());
		setIngresosE(menu.tblReporteDiarioDolares.getValueAt(4, 2).toString());
		llenarTicketDiarioDolares();
		try {
			print("DiarioDolares");
		} catch (Exception err) {
			//JOptionPane.showMessageDialog(null, "");
		}
	}

	public void imprimirTotalV() {
		info.obtenerInfoFactura();
		String nombreTienda = info.getNombre();
		String[] datos;
		String f1, f2, t;
		int filas = menu.tblMostrarTotalV.getRowCount();
		datos = new String[filas];
		for (int i = 0; i < filas; i++) {
			f1 = (String) menu.tblMostrarTotalV.getValueAt(i, 0);
			f2 = (String) menu.tblMostrarTotalV.getValueAt(i, 1);
			t = (String) menu.tblMostrarTotalV.getValueAt(i, 2);
			datos[i] = f1 + "      " + f2 + "      " + t + "\n";
		}
		llenarTicketTotalV(datos, nombreTienda);
		LimpiarTablaTotalV();
		try {
			print("TotalV");
		} catch (Exception err) {
		}
	}

	public void imprimirPmasV() {
		info.obtenerInfoFactura();
		int filas = menu.tblProductosMasVendidos.getRowCount(), n = 1;
		Date f1 = menu.jc1.getDate(), f2 = menu.jc2.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
		String fecha1 = sdf.format(f1), fecha2 = sdf.format(f2), tienda = info.getNombre(), nombre, nombreCorto = "", marca, vendido;
		String[] producto = new String[filas];
		for (int i = 0; i < filas; i++) {
			n = n + i;
			nombre = (String) menu.tblProductosMasVendidos.getValueAt(i, 1);
			marca = (String) menu.tblProductosMasVendidos.getValueAt(i, 2);
			vendido = (String) menu.tblProductosMasVendidos.getValueAt(i, 4);
			if (nombre.length() > 18) {
				nombre = nombre.substring(0, 18);
			}
			producto[i] = n + "  " + nombre + " " + marca + "       " + vendido;
		}
		BIP(tienda, fecha1, fecha2, producto);
		try {
			print("BI");
		} catch (Exception err) {
		}
	}

	public void ReImprimirFactura() {
		Configuraciones info = new Configuraciones();
		info.obtenerInfoFactura();
		int filaseleccionada = menu.tblReporte.getSelectedRow();
		int filas = menu.tblMostrarDetalleFactura.getRowCount();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
		try {
			String cliente = "",
				comprador = "",
				formaPago = "",
				fecha = "",
				factura = "",
				Listaproducto[] = new String[filas],
				producto = "",
				precio = "",
				cantidad = "",
				importe = "",
				tipoVenta = "",
				caja = "",
				creditoId = "",
				IVA = "";
			String total = "", subTotal = "", totaldolares = "";
			factura = (String) menu.tblReporte.getValueAt(filaseleccionada, 0);
			fecha = (String) menu.tblReporte.getValueAt(filaseleccionada, 1);
			//formaPago es con tarjeta efectivo o al credito
			IVA = (String) menu.tblReporte.getValueAt(filaseleccionada, 2);
			formaPago = (String) menu.tblReporte.getValueAt(filaseleccionada, 6);
			creditoId = (String) menu.tblReporte.getValueAt(filaseleccionada, 7);
			comprador = (String) menu.tblReporte.getValueAt(filaseleccionada, 5);
			caja = (String) menu.tblReporte.getValueAt(filaseleccionada, 8);
			total = (String) menu.tblReporte.getValueAt(filaseleccionada, 4);
			totaldolares = (String) menu.tblReporte.getValueAt(filaseleccionada, 3);

			for (int i = 0; i < filas; i++) {
				producto = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 3);
				cantidad = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 4);
				precio = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 5);
				importe = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 6);
				//total += Float.parseFloat(menu.tblMostrarDetalleFactura.getValueAt(i, 6).toString());
				if (producto.length() > 10) {
					producto = producto.substring(0, 10);
				}
				Listaproducto[i] = producto + " " + cantidad + "   " + precio + "  " + importe + "\n";
			}
			//calcular subtotal

			if (creditoId == null) {
				//tipoVenta es la forma al contado o credito
				tipoVenta = "Contado";
				cliente = "";
			} else {
				tipoVenta = "Credito";
				cliente = this.creditosModel.NombreCliente(Integer.parseInt(creditoId));
			}
			Ticket d = new Ticket();
			d.nameLocal = info.getNombre();
			d.direccion = info.getDireccion();
			d.telefono = info.getTelefono();
			d.RFC = info.getRfc();
			d.Rango = info.getRangoInicio() + " - " + info.getRangoFinal();
			d.box = "1";
			d.ticket = factura;
			d.caissier = "Cajero";
			d.comprador = comprador;
			d.cliente = cliente;
			d.tipoVenta = tipoVenta;
			d.formaPago = formaPago;
			d.dateTime = fecha;
			d.items = Listaproducto;
			d.subTotal = String.valueOf(0.00);
			d.iva = IVA;
			d.totalCordobas = String.valueOf(total);
			d.totalDolares = String.valueOf(totaldolares);
			d.recibo = "";
			d.change = "";
			d.message = info.getAnotaciones();
			d.llenarTicket();
			d.printFactura();
		} catch (Exception err) {
			err.printStackTrace();
			//JOptionPane.showMessageDialog(null, err + " Erro en la funcion btnReImprimirFactura");
		}
	}

	public void ImprimirInfoCrediticia() {
		try {
			/* TODO agrgar el nombre del cliente al historial */
			int filasDolar = this.menu.tblArticulosCredito.getRowCount();
			int filasCordobas = this.menu.tblArticulosCreditoCordobas.getRowCount();
			this.cliente = this.creditosModel.NombreCliente(nCredito);
			System.out.println(this.cliente);	
			String totalC = this.menu.lblTodalCreditoCordobas.getText(),
				totalD = this.menu.lblTotalCreditoDolar.getText(),
				totalPagosCordobas = this.menu.lblTotalAbonosCordobas.getText(),
				totalPagosDolar = this.menu.lblTotalAbonosDolar.getText(),
				saldoCordobas = this.menu.lblSaldoCordobas.getText(),
				saldoDolares = this.menu.lblSaldoDolar.getText();
			String listado = "PRODUCTOS EN DOLAR \n";

			for (int i = 0; i < filasDolar; i++) {
				listado += this.menu.tblArticulosCredito.getValueAt(i, 0).toString()
					+ " " + this.menu.tblArticulosCredito.getValueAt(i, 1).toString()
					+ " " + this.menu.tblArticulosCredito.getValueAt(i, 2).toString()
					+ " " + this.menu.tblArticulosCredito.getValueAt(i, 3).toString() + "$"
					+ " " + this.menu.tblArticulosCredito.getValueAt(i, 5).toString() + "\n";
			}
			listado += "\nPRODUCTOS EN CORDOBAS \n";
			for (int i = 0; i < filasCordobas; i++) {
				listado += this.menu.tblArticulosCreditoCordobas.getValueAt(i, 0).toString()
					+ " " + this.menu.tblArticulosCreditoCordobas.getValueAt(i, 1).toString()
					+ " " + this.menu.tblArticulosCreditoCordobas.getValueAt(i, 2).toString()
					+ " " + this.menu.tblArticulosCreditoCordobas.getValueAt(i, 3).toString() + " C$"
					+ " " + this.menu.tblArticulosCreditoCordobas.getValueAt(i, 5).toString() + "\n";
			}
			setTotalCreditoCordobas(totalC);
			setTotalCreditoDolares(totalD);
			setTotalPagosCordobas(totalPagosCordobas);
			setTotalPagosDolares(totalPagosDolar);
			setSaldoCordobas(saldoCordobas);
			setSaldoDolares(saldoDolares);
			setListaProductosCreditos(listado);
			setNombreCliente(this.cliente);
			print("ListaCredito");
		} catch (Exception e) {

		}
	}

	public void LimpiarTablaTotalV() {
		try {
			this.modelo = (DefaultTableModel) menu.tblMostrarTotalV.getModel();
			int filas = this.modelo.getRowCount();
			for (int i = 0; i < filas; i++) {
				this.modelo.removeRow(0);
			}
		} catch (Exception e) {

		}

	}
}
