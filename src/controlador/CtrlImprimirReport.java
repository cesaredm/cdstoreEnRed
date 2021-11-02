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
import modelo.InfoFactura;
import modelo.Creditos;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlImprimirReport extends PrintReportes implements ActionListener {

    IMenu menu;
    InfoFactura info;
    Creditos credito;
    DefaultTableModel modelo;

    public CtrlImprimirReport(IMenu menu, InfoFactura info) {
        this.menu = menu;
        this.info = info;
        this.credito = new Creditos();
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
            imprimirReporteDiario();
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
        String efectivoB = menu.lblIngresosCajaMes.getText(), ventasT = menu.lblIngresosVentasTarjetaMes.getText(), pagosE = menu.lblIngresosPagosEfectivoMes.getText(),
                pagosT = menu.lblIngresosPagosTarjetaMes.getText(), creditos = menu.lblCreditosFiltro.getText(), existCaja = menu.lblExistenciaCajaFiltro.getText(),
                bancos = menu.lblIngresosBancoFiltro.getText(), totalV = menu.lblTotalVendidoFiltro.getText(), egresos = menu.lblEgresosFiltro.getText(),
                ingresosE = menu.lblIngresosEfectivo.getText();
        llenarTicketGlobal(nombreTienda, efectivoB, ventasT, pagosE, pagosT, ingresosE, creditos, egresos, existCaja, bancos, totalV);
        try {
            print("Global");
        } catch (Exception err) {
            //JOptionPane.showMessageDialog(null, err+" en la funcion de btn ImprimirReporteglobal");
        }
    }

    public void imprimirReporteDiario() {
        info.obtenerInfoFactura();
        String nombreTienda = info.getNombre();
        Date fechaInicio = menu.jcFechaReporteDario.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
//        String fechaR = sdf.format(fechaInicio),
//		base = menu.lblBase.getText(),
//		ventasE = menu.lblVentasEfectivoDiario.getText(),
//		ventasT = menu.lblVentasTarjetaDiario.getText(),
//                pagosE = menu.lblIngresosPagosEfectivoDiario.getText(),
//		pagosT = menu.lblIngresosPagosTarjetaDiario.getText(),
//                creditos = menu.lblCreditosDiarios.getText(),
//		egresos = menu.lblEgresosDiarios.getText(),
//		existCaja = menu.lblTotalExistenciaCajaDiario.getText(),
//                bancos = menu.lblIngresosBancosDiario.getText(),
//		totalV = menu.lbltotalVendidoDiario.getText(),
//                ingresosE = menu.lblIngresoEfectivo.getText();
                /*cordobas = menu.lblDiferenciaEnCordobas.getText(),
                DV = menu.lblCantidadDolares.getText(),
                DC = menu.lblCantidadCordobas.getText(),
                PDC = menu.lblPrecioCompraDolarEnCordobas.getText(),
                PDV = menu.lblPrecioVentaDolarEnCordobas.getText(),
                TCDV = menu.lblTotalCordobasPorVentaDolar.getText(),
                TCDC = menu.lblTotalCordobasPorCompraDolar.getText();*/
//        llenarTicketDiario(nombreTienda, fechaR, base, ventasE, ventasT, pagosE, pagosT, ingresosE, creditos, egresos, existCaja, bancos, totalV);
        try {
            print("Diario");
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
            producto[i] = n+"  "+nombre + " " + marca + "       " + vendido;
        }
        BIP(tienda, fecha1, fecha2, producto);
        try {
            print("BI");
        } catch (Exception err) {
        }
    }

    public void ReImprimirFactura() {
        InfoFactura info = new InfoFactura();
        info.obtenerInfoFactura();
        int filaseleccionada = menu.tblReporte.getSelectedRow();
        int filas = menu.tblMostrarDetalleFactura.getRowCount();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
        try {
            String cliente = "", comprador = "", formaPago = "", fecha = "", factura = "", Listaproducto[] = new String[filas], producto = "", precio = "", cantidad = "", importe = "", tipoVenta = "", caja = "", creditoId = "", IVA = "";
            float total = 0, subTotal =0;
            factura = (String) menu.tblReporte.getValueAt(filaseleccionada, 0);
            fecha = (String) menu.tblReporte.getValueAt(filaseleccionada, 1);
            //formaPago es con tarjeta efectivo o al credito
            IVA = (String) menu.tblReporte.getValueAt(filaseleccionada, 2);
            formaPago = (String) menu.tblReporte.getValueAt(filaseleccionada, 5);
            creditoId = (String) menu.tblReporte.getValueAt(filaseleccionada, 6);
            comprador = (String) menu.tblReporte.getValueAt(filaseleccionada, 4);
            System.out.println(comprador);
            caja = (String) menu.tblReporte.getValueAt(filaseleccionada, 7);
            for (int i = 0; i < filas; i++) {
                producto = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 3);
                cantidad = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 4);
                precio = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 5);
                importe = (String) menu.tblMostrarDetalleFactura.getValueAt(i, 6);
                total += Float.parseFloat(menu.tblMostrarDetalleFactura.getValueAt(i, 6).toString());
                if (producto.length() > 10) {
                    producto = producto.substring(0, 10);
                }
                Listaproducto[i] = producto + " " + cantidad + "   " + precio + "  " + importe + "\n";
            }
            //calcular subtotal
            subTotal = total -Float.parseFloat(IVA);
            
            if(creditoId == null) {
                //tipoVenta es la forma al contado o credito
                tipoVenta = "Contado";
                cliente = "";
            }else{
                tipoVenta = "Credito";
                cliente = this.credito.NombreCliente(creditoId);
            }
            Ticket d = new Ticket(info.getNombre(), info.getDireccion(), info.getTelefono(), info.getRfc(), info.getRango(), "1", factura, "Cajero", comprador, cliente, tipoVenta, formaPago, fecha, Listaproducto, String.valueOf(subTotal), IVA, String.valueOf(total), "", "");
            d.printInfo();
        } catch (Exception err) {
            //JOptionPane.showMessageDialog(null, err + " Erro en la funcion btnReImprimirFactura");
        }
    }
    
    public void ImprimirInfoCrediticia(){
        try {
            int filasDolar = this.menu.tblArticulosCredito.getRowCount();
            int filasCordobas = this.menu.tblArticulosCreditoCordobas.getRowCount();
            int filaseleccionada = this.menu.tblCreditos.getSelectedRow();
            String cliente = this.menu.tblCreditos.getValueAt(filaseleccionada, 4).toString() + " " + this.menu.tblCreditos.getValueAt(filaseleccionada, 5).toString();
            String totalC = this.menu.lblTodalCreditoPorCliente.getText(),
                   totalP = this.menu.lblTotalAbonosPorCliente.getText(),
                   saldo = this.menu.lblSaldoCliente.getText();
            String listado = "PRODUCTOS EN DOLAR \n";
            
            for (int i = 0; i < filasDolar; i++) {
                listado += this.menu.tblArticulosCredito.getValueAt(i,2).toString()
                           +" "+ this.menu.tblArticulosCredito.getValueAt(i,1).toString()
                           +" "+ this.menu.tblArticulosCredito.getValueAt(i,3).toString()+"$\n";
            }
            listado += "\nPRODUCTOS EN CORDOBAS \n";
            for (int i = 0; i < filasCordobas; i++) {
                listado += this.menu.tblArticulosCreditoCordobas.getValueAt(i,2).toString()
                           +" "+ this.menu.tblArticulosCreditoCordobas.getValueAt(i,1).toString()
                           +" "+ this.menu.tblArticulosCreditoCordobas.getValueAt(i,3).toString()+" C$\n";
            }
            setTatalCredito(totalC);
            setTotalPagos(totalP);
            setSaldo(saldo);
            setListaProductosCreditos(listado);
            setNombreCliente(cliente);
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
