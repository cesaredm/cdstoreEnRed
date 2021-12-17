/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import modelo.RegistroMonedas;
import modelo.Reportes;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlRegistroMonedas /*implements ActionListener*/ {

//    Menu menu;
//    RegistroMonedas registro;
//    int id;
//    String pagoOfactura;
//    Date fecha;
//    Reportes reportes;
//    CtrlReportes ctrlreportes;
//
//    public CtrlRegistroMonedas(IMenu menu, RegistroMonedas Registro) {
//        this.menu = menu;
//        registro = Registro;
//        this.fecha = new Date();
//        reportes = new Reportes();
//        ctrlreportes = new CtrlReportes(menu, reportes);
//        mostrarRegistroMonedasActual(fecha);
//        this.menu.btnMostrarRegistroMonedaHoy.addActionListener(this);
//        this.menu.btnMostrarRegistroMoneda.addActionListener(this);
//        this.menu.editarRegistroMonedas.addActionListener(this);
//        this.menu.btnActualizarIngresoMoneda.addActionListener(this);
//        this.menu.btnActualizarSalidaMoneda.addActionListener(this);
//        this.menu.btnGuardarMonedasRecibidas.addActionListener(this);
//        this.menu.btnGuardarSalidaMoneda.addActionListener(this);
//    }
//
//    public void actualizarRegistroMoneda() {
//        float cantDolares;
//        int facturaPago;
//        switch (this.pagoOfactura) {
//            case "IngresoFactura": {
//                facturaPago = Integer.parseInt(menu.jsFacturaPago.getValue().toString());
//                cantDolares = Float.parseFloat(menu.jsCantidadDolar.getValue().toString());
//                this.registro.actualizarMonedasRecibidas(this.id, facturaPago, cantDolares, true);
//                menu.jdMonedasRecibidas.setVisible(false);
//                menu.btnGuardarMonedasRecibidas.setEnabled(true);
//                menu.btnActualizarIngresoMoneda.setEnabled(false);
//            }
//            break;
//            case "IngresoPago": {
//                facturaPago = Integer.parseInt(menu.jsFacturaPago.getValue().toString());
//                cantDolares = Float.parseFloat(menu.jsCantidadDolar.getValue().toString());
//                this.registro.actualizarMonedasRecibidas(this.id, facturaPago, cantDolares, false);
//                menu.jdMonedasRecibidas.setVisible(false);
//                menu.btnGuardarMonedasRecibidas.setEnabled(true);
//                menu.btnActualizarIngresoMoneda.setEnabled(false);
//            }
//            break;
//            case "SalidaFactura": {
//                facturaPago = Integer.parseInt(menu.jsFacturaPagoSalida.getValue().toString());
//                cantDolares = Float.parseFloat(menu.jsCantidadDolar1.getValue().toString());
//                this.registro.actualizarMonedasRecibidas(this.id, facturaPago, cantDolares, true);
//                menu.jdSalidaMonedas.setVisible(false);
//                menu.btnGuardarSalidaMoneda.setEnabled(true);
//                menu.btnActualizarSalidaMoneda.setEnabled(false);
//            }
//            break;
//            case "SalidaPago": {
//                facturaPago = Integer.parseInt(menu.jsFacturaPagoSalida.getValue().toString());
//                cantDolares = Float.parseFloat(menu.jsCantidadDolar1.getValue().toString());
//                this.registro.actualizarMonedasRecibidas(this.id, facturaPago, cantDolares, false);
//                menu.jdSalidaMonedas.setVisible(false);
//                menu.btnGuardarSalidaMoneda.setEnabled(true);
//                menu.btnActualizarSalidaMoneda.setEnabled(false);
//            }
//            break;
//            default: {
//
//            }
//            break;
//        }
//        menu.jsFacturaPago.setValue(0);
//        menu.jsCantidadDolar.setValue(0);
//        menu.jsFacturaPagoSalida.setValue(0);
//        menu.jsCantidadDolar1.setValue(0);
//        mostrarRegistroMonedasActual(this.fecha);
//
//    }
//
//    public void EditarIngresoDeMoneda() {
//        int filaseleccionada = this.menu.tblRegistroMonedas.getSelectedRow();
//        String factura, pago;
//        if (filaseleccionada == -1) {
//
//        } else {
//            String mov = menu.tblRegistroMonedas.getValueAt(filaseleccionada, 3).toString();
//            int numeroFacturaPago;
//            float cantDolares;
//            factura = (String) menu.tblRegistroMonedas.getValueAt(filaseleccionada, 4);
//            pago = (String) menu.tblRegistroMonedas.getValueAt(filaseleccionada, 5);
//            this.id = Integer.parseInt(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 0).toString());
//            if (mov.equals("Salida")) {
//                this.menu.jdSalidaMonedas.setSize(325, 260);
//                this.menu.jdSalidaMonedas.setLocationRelativeTo(null);
//                this.menu.jdSalidaMonedas.setVisible(true);
//                this.menu.btnActualizarSalidaMoneda.setEnabled(true);
//                this.menu.btnGuardarSalidaMoneda.setEnabled(false);
//                if (factura != null && pago == null) {
//                    this.pagoOfactura = "SalidaFactura";
//                    numeroFacturaPago = Integer.parseInt(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 4).toString());
//                    cantDolares = Float.parseFloat(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 2).toString());
//                    this.menu.jsFacturaPagoSalida.setValue(numeroFacturaPago);
//                    this.menu.jsCantidadDolar.setValue(cantDolares);
//                    this.menu.chexEgresoMonedasFactura.setSelected(true);
//                    this.menu.chexEgresoMonedasPago.setSelected(false);
//                } else {
//                    this.pagoOfactura = "SalidaPago";
//                    numeroFacturaPago = Integer.parseInt(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 5).toString());
//                    cantDolares = Float.parseFloat(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 2).toString());
//                    this.menu.jsFacturaPagoSalida.setValue(numeroFacturaPago);
//                    this.menu.jsCantidadDolar1.setValue(cantDolares);
//                    this.menu.chexEgresoMonedasFactura.setSelected(false);
//                    this.menu.chexEgresoMonedasPago.setSelected(true);
//                }
//            } else if (mov.equals("Ingreso")) {
//                this.menu.jdMonedasRecibidas.setSize(325, 260);
//                this.menu.jdMonedasRecibidas.setLocationRelativeTo(null);
//                this.menu.jdMonedasRecibidas.setVisible(true);
//                this.menu.btnActualizarIngresoMoneda.setEnabled(true);
//                this.menu.btnGuardarMonedasRecibidas.setEnabled(false);
//                if (factura != null && pago == null) {
//                    this.pagoOfactura = "IngresoFactura";
//                    numeroFacturaPago = Integer.parseInt(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 4).toString());
//                    cantDolares = Float.parseFloat(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 2).toString());
//                    this.menu.jsFacturaPago.setValue(numeroFacturaPago);
//                    this.menu.jsCantidadDolar.setValue(cantDolares);
//                    this.menu.chexIngresoMonedasFactura.setSelected(true);
//                    this.menu.chexIngresoMonedasPago.setSelected(false);
//                } else {
//                    this.pagoOfactura = "IngresoPago";
//                    numeroFacturaPago = Integer.parseInt(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 5).toString());
//                    cantDolares = Float.parseFloat(menu.tblRegistroMonedas.getValueAt(filaseleccionada, 2).toString());
//                    this.menu.jsFacturaPago.setValue(numeroFacturaPago);
//                    this.menu.jsCantidadDolar.setValue(cantDolares);
//                    this.menu.chexIngresoMonedasFactura.setSelected(false);
//                    this.menu.chexIngresoMonedasPago.setSelected(true);
//                }
//            }
//        }
//    }
//
//    public void mostrarRegistroMonedas(String id) {
//        menu.tblRegistroMonedas.setModel(this.registro.MostrarRegistrosMonedas(id));
//    }
//
//    public void mostrarRegistroMonedasActual(Date fecha) {
//        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
//        menu.tblRegistroMonedas.setModel(registro.MostrarRegistrosMonedas(fechaSQL));
//    }
//
//    public void GuardarMonedasRecibidas() {
//        float cantDolar = (float) this.menu.jsCantidadDolar.getValue();
//        int numeroFacturaPago = (int) this.menu.jsFacturaPago.getValue();
//        
//        Date fecha = this.menu.jcFechaFactura.getDate();
//        long f = fecha.getTime();
//        java.sql.Date fechaMonedas = new java.sql.Date(f);
//
//        if (this.menu.chexIngresoMonedasFactura.isSelected()) {
//            if(this.menu.chexIngresoCompraDolar.isSelected()){
//                this.registro.guardarMonedasRecibidas(fechaMonedas, cantDolar, "Ingreso", numeroFacturaPago, "compra", true);
//            }else{
//                this.registro.guardarMonedasRecibidas(fechaMonedas, cantDolar, "Ingreso", numeroFacturaPago, "venta", true);
//            }
//        } else {
//            if(this.menu.chexIngresoCompraDolar.isSelected()){
//                this.registro.guardarMonedasRecibidas(fechaMonedas, cantDolar, "Ingreso", numeroFacturaPago, "compra", true);
//            }else{
//                this.registro.guardarMonedasRecibidas(fechaMonedas, cantDolar, "Ingreso", numeroFacturaPago, "venta", true);
//            }
//        }
//        this.menu.jsCantidadDolar.setValue(0);
//        this.menu.jsFacturaPago.setValue(0);
//        this.ctrlreportes.reportesDiarios(fecha);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        if (this.menu.btnActualizarIngresoMoneda == e.getSource()) {
//            actualizarRegistroMoneda();
//        } else if (this.menu.btnActualizarSalidaMoneda == e.getSource()) {
//            actualizarRegistroMoneda();
//        } else if (this.menu.btnMostrarRegistroMoneda == e.getSource()) {
//            String id = this.menu.jsNingreso.getValue().toString();
//            mostrarRegistroMonedas(id);
//        } else if (this.menu.btnMostrarRegistroMonedaHoy == e.getSource()) {
//            mostrarRegistroMonedasActual(this.fecha);
//        } else if (this.menu.btnGuardarMonedasRecibidas == e.getSource()) {
//            GuardarMonedasRecibidas();
//        } else if (e.getSource() == menu.btnGuardarSalidaMoneda) {
//            float cantDolar = (float) this.menu.jsCantidadDolar1.getValue();
//            int numeroFacturaPago = (int) this.menu.jsFacturaPagoSalida.getValue();
//
//            Date fecha = this.menu.jcFechaReporteDario.getDate();
//            long f = fecha.getTime();
//            java.sql.Date fechaMonedas = new java.sql.Date(f);
//
//            if (this.menu.chexEgresoMonedasFactura.isSelected()) {
//                this.registro.guardarMonedasRecibidas(fechaMonedas, cantDolar, "Salida", numeroFacturaPago, "" ,true);
//            } else {
//                this.registro.guardarMonedasRecibidas(fechaMonedas, cantDolar, "Salida", numeroFacturaPago, "" ,false);
//            }
//            this.menu.jsCantidadDolar1.setValue(0);
//            this.menu.jsFacturaPagoSalida.setValue(0);
//
//            this.ctrlreportes.reportesDiarios(fecha);
//        } else if (this.menu.editarRegistroMonedas == e.getSource()) {
//            EditarIngresoDeMoneda();
//        }
//    }
}
