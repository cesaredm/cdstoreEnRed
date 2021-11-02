/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * PrintReportes.java
 * 
 * 
 */
package controlador;
import com.github.anastaciocintra.escpos.EscPos;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class PrintReportes extends CtrlImprimir{

    String nombreTienda;
    private String listaProductosCreditos;
    private String nombreCliente;
    private String tatalCredito;
    private String totalPagos;
    private String saldo;


    //Ticket attribute content
    private String contentTicketDiario;
    
    private String ticketTotalV = ""
            + "{{nombreTienda}}            \n"
            + "\n"
            + "Fecha Inicio      Fecha Final      Total Vendido\n"
            + "\n"
            + "{{datos}}"
            + "\n\n\n\n\n";
    private String contentTicketGlobal = ""
            + "{{nombreTienda}}           \n"
            + "REPORTE GENERAL GLOBAL      \n"
            + "\n"
            + "Ingreso por ventas en efectivo   {{ventasE}}\n"
            + "Ingreso por ventas con tarjeta   {{ventasT}}\n"
            + "Ingresos por abonos en efectivo  {{pagosE}}\n"
            + "Ingresos por abonos con tarjeta  {{pagosT}}\n"
            + "Ingresos de efectivo             {{ingresosE}}\n"
            + "Creditos                         {{creditos}}\n"
            + "Egresos de efectivo de caja      {{egresos}}\n"
            + "============================================\n"
            + "Total efectivo en caja           {{existCaja}}\n"
            + "Total Bancos                     {{bancos}}\n"
            + "Total vendido                    {{totalV}}\n"
            + "..\n\n\n\n\n";

    //bussines Intelligense
    private String BI = ""
            + "{{nombreTienda}}            \n"
            + "Productos mas vendidos o solicitados\n"
            + "Fecha: {{fecha1}} Hasta {{fecha2}}\n"
            + "-----------------------------------------\n"
            + "N°   Nombre                     Vendido\n"
            + "-----------------------------------------\n"
            + "{{producto}}\n"
            + "\n\n\n\n\n";

    public void llenarTicketDiario(String NombreTienda, String fecha, String base, String ventasEfectivo, String ventasT, String pagosE, String pagosT, String ingresosE, String creditos, String egreso, String existCaja, String bancos, String totalV) {
        //DV = dolaresVendidos DC = dolaresComprados PDV = precioDolarVenta PDC = precioDolarCompra TCDV = total de cordobas por dolares vendidos TCDC = total de cordobas por dolares comprados
         this.nombreTienda = NombreTienda;
         this.contentTicketDiario = ""
            + "REPORTE DEL DIA "+fecha+"      \n"
            + "\n"
            + "Efctivo de apertura caja           "+base+"\n"
            + "Ingreso por ventas en efectivo     "+ventasEfectivo+"\n"
            + "Ingreso por ventas con tarjeta     "+ventasT+"\n"
            + "Ingresos por abonos en efectivo    "+pagosE+"\n"
            + "Ingresos por abonos con tarjeta    "+pagosT+"\n"
            + "Ingresos de efectivo               "+ingresosE+"\n"
            + "Creditos                           "+creditos+"\n"
            + "Egresos de efectivo de caja        "+egreso+"\n"
            + "===========================================\n"
            + "Total vendido                      "+totalV+"\n"
            + "Total Bancos                       "+bancos+"\n"
            + "Total efectivo en caja             "+existCaja+"\n"
            + "-------------------------------------------\n"
            + "..\n\n\n\n\n";
    }

    public void setListaProductosCreditos(String listaProductosCreditos) {
        this.listaProductosCreditos = listaProductosCreditos;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public void setTatalCredito(String tatalCredito) {
        this.tatalCredito = tatalCredito;
    }

    public void setTotalPagos(String totalPagos) {
        this.totalPagos = totalPagos;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    
    public void llenarTicketTotalV(String[] datos, String tienda) {
        StringBuffer a = new StringBuffer("");
        for (int i = 0; i < datos.length; i++) {
            a.append(datos[i]);
        }
        this.ticketTotalV = this.ticketTotalV.replace("{{nombreTienda}}", tienda);
        this.ticketTotalV = this.ticketTotalV.replace("{{datos}}", a);
        // System.out.println(this.ticketTotalV);
    }

    //bussines 
    public void BIP(String tienda, String fecha1, String fecha2, String[] P) {
        StringBuffer Producto = new StringBuffer("");
        int N = 0;
        for (int i = 0; i < P.length; i++) {
            Producto.append(P[i] + "\n");
        }
        this.BI = this.BI.replace("{{nombreTienda}}", tienda);
        this.BI = this.BI.replace("{{fecha1}}", fecha1);
        this.BI = this.BI.replace("{{fecha2}}", fecha2);
        this.BI = this.BI.replace("{{producto}}", Producto);
        //System.out.println(this.BI);
    }
    
    public void llenarTicketGlobal(String NombreTienda, String ventasEfectivo, String ventasT, String pagosE, String pagosT, String ingresosE, String creditos, String egreso, String existCaja, String bancos, String totalV) {
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{nombreTienda}}", NombreTienda);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{ventasE}}", ventasEfectivo);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{ventasT}}", ventasT);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{pagosE}}", pagosE);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{pagosT}}", pagosT);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{ingresosE}}", ingresosE);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{creditos}}", creditos);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{egresos}}", egreso);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{existCaja}}", existCaja);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{bancos}}", bancos);
        this.contentTicketGlobal = this.contentTicketGlobal.replace("{{totalV}}", totalV);
        // System.out.println(this.contentTicketGlobal);
    }
    
    public void print(String TipoReport) {
        try {
            
            reiniciar();
            //Agregamos la imagen
            escpos.write(imageWrapper, escposImage).feed(1);
            
            switch (TipoReport) {
                case "Diario": {
                    escpos.writeLF(contentTicketDiario)
                    .feed(2).cut(EscPos.CutMode.FULL);
                }
                break;
                case "TotalV": {
                    escpos.writeLF(ticketTotalV)
                    .feed(2).cut(EscPos.CutMode.FULL);
                }
                break;
                case "Global": {
                    escpos.writeLF(contentTicketGlobal)
                    .feed(2).cut(EscPos.CutMode.FULL);
                }
                break;
                case "BI": {
                    escpos.writeLF(BI)
                    .feed(2).cut(EscPos.CutMode.FULL);
                }
                break;
                case "ListaCredito":{
                    escpos.writeLF(boldCenter,this.nombreCliente).feed(2)
                          .writeLF(boldCenter,"Crédito:"+this.tatalCredito+" Pagos:"+this.totalPagos+" Saldo:"+this.saldo).feed(2)
                          .write(this.listaProductosCreditos)
                          .feed(4).cut(EscPos.CutMode.FULL);
                }
                break;
            }
            close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
