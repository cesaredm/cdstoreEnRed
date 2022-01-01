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
public class PrintReportes extends CtrlImprimir {
	String nombreTienda;
	/* ----------------------- Historial crediticio ---------------------*/
	private String listaProductosCreditos;
	private String nombreCliente;
	private String totalCreditoDolar, totalCreditoCordobas;
	private String totalPagosCordobas, totalPagosDolares;
	private String saldoCordobas, saldoDolares;
	/*------------------------ Reportes diario ----------------------*/
	private String NombreTienda,
		fecha,
		base,
		ventasEfectivo,
		ventasT,
		pagosE,
		pagosT,
		ingresosE,
		creditos,
		egreso,
		existCaja,
		bancos,
		totalV;

	//Ticket attribute content
	private String contentTicketDiarioCordobas;
	private String contentTicketDiarioDolar;

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

	
	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getVentasEfectivo() {
		return ventasEfectivo;
	}

	public void setVentasEfectivo(String ventasEfectivo) {
		this.ventasEfectivo = ventasEfectivo;
	}

	public String getVentasT() {
		return ventasT;
	}

	public void setVentasT(String ventasT) {
		this.ventasT = ventasT;
	}

	public String getPagosE() {
		return pagosE;
	}

	public void setPagosE(String pagosE) {
		this.pagosE = pagosE;
	}

	public String getPagosT() {
		return pagosT;
	}

	public void setPagosT(String pagosT) {
		this.pagosT = pagosT;
	}

	public String getIngresosE() {
		return ingresosE;
	}

	public void setIngresosE(String ingresosE) {
		this.ingresosE = ingresosE;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getEgreso() {
		return egreso;
	}

	public void setEgreso(String egreso) {
		this.egreso = egreso;
	}

	public String getBancos() {
		return bancos;
	}

	public void setBancos(String bancos) {
		this.bancos = bancos;
	}

	public String getTotalV() {
		return totalV;
	}

	public void setTotalV(String totalV) {
		this.totalV = totalV;
	}

	public String getExistCaja() {
		return existCaja;
	}

	public void setExistCaja(String existCaja) {
		this.existCaja = existCaja;
	}

	public void llenarTicketDiarioCordobas() {
		this.contentTicketDiarioCordobas = ""
			+ "REPORTE EN CORDOBAS DEL DIA " + this.fecha + "      \n"
			+ "\n"
			+ "Efctivo de apertura caja           " + this.base + "\n"
			+ "Ingreso por ventas en efectivo     " + this.ventasEfectivo + "\n"
			+ "Ingreso por ventas con tarjeta     " + this.ventasT + "\n"
			+ "Ingresos por abonos en efectivo    " + this.pagosE + "\n"
			+ "Ingresos por abonos con tarjeta    " + this.pagosT + "\n"
			+ "Ingresos de efectivo               " + this.ingresosE + "\n"
			+ "Creditos                           " + this.creditos + "\n"
			+ "Egresos de efectivo de caja        " + this.egreso + "\n"
			+ "===========================================\n"
			+ "Total vendido                      " + this.totalV + "\n"
			+ "Total Bancos                       " + this.bancos + "\n"
			+ "Total efectivo en caja             " + this.existCaja + "\n"
			+ "-------------------------------------------\n"
			+ "..\n\n\n\n\n";
	}

	public void llenarTicketDiarioDolares() {
		this.contentTicketDiarioDolar = ""
			+ "REPORTE EN DOLARES DEL DIA " + this.fecha + "      \n"
			+ "\n"
			+ "Ingreso por ventas en efectivo     " + this.ventasEfectivo + "\n"
			+ "Ingreso por ventas con tarjeta     " + this.ventasT + "\n"
			+ "Ingresos por abonos en efectivo    " + this.pagosE + "\n"
			+ "Ingresos por abonos con tarjeta    " + this.pagosT + "\n"
			+ "Ingresos de efectivo               " + this.ingresosE + "\n"
			+ "Creditos                           " + this.creditos + "\n"
			+ "Egresos de efectivo de caja        " + this.egreso + "\n"
			+ "===========================================\n"
			+ "Total vendido                      " + this.totalV + "\n"
			+ "Total Bancos                       " + this.bancos + "\n"
			+ "Total efectivo en caja             " + this.existCaja + "\n"
			+ "-------------------------------------------\n"
			+ "..\n\n\n\n\n";
	}

	public void setListaProductosCreditos(String listaProductosCreditos) {
		this.listaProductosCreditos = listaProductosCreditos;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public void setTotalCreditoCordobas(String tatalCredito) {
		this.totalCreditoCordobas = tatalCredito;
	}

	public void setTotalCreditoDolares(String totalCreditoDolares) {
		this.totalCreditoDolar = totalCreditoDolares;
	}

	public void setTotalPagosCordobas(String totalPagos) {
		this.totalPagosCordobas = totalPagos;
	}

	public void setTotalPagosDolares(String totalPagosDolares) {
		this.totalPagosDolares = totalPagosDolares;
	}

	public void setSaldoCordobas(String saldo) {
		this.saldoCordobas = saldo;
	}

	public void setSaldoDolares(String saldoDolar) {
		this.saldoDolares = saldoDolar;
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

	public void llenarTicketGlobal(
		String NombreTienda,
		String ventasEfectivo,
		String ventasT,
		String pagosE,
		String pagosT,
		String ingresosE,
		String creditos,
		String egreso,
		String existCaja,
		String bancos,
		String totalV) {
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
				case "DiarioCordobas": {
					escpos.writeLF(contentTicketDiarioCordobas)
						.feed(2).cut(EscPos.CutMode.FULL);
				}
				break;
				case "DiarioDolares": {
					escpos.writeLF(contentTicketDiarioDolar)
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
				case "ListaCredito": {
					escpos.writeLF(boldCenter, this.nombreCliente).feed(2).
						writeLF(boldCenter, "CORDOBAS")
						.writeLF(
							boldCenter,
							"Crédito:" + this.totalCreditoCordobas
							+ " Pagos:" + this.totalPagosCordobas
							+ " Saldo:" + this.saldoCordobas
						).feed(1)
						.writeLF(boldCenter, "DOLARES")
						.writeLF(
							boldCenter,
							"Crédito $:" + this.totalCreditoDolar
							+ " Pagos $:" + this.totalPagosDolares
							+ " Saldo $:" + this.saldoDolares
						).feed(2)
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
