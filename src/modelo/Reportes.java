/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Reportes extends Conexiondb {

	String consulta;
	Connection cn;
	PreparedStatement pst;
	int banderin;
	DefaultTableModel modelo;
	private float Dolares, EgresoDolares, dolaresComprados, precioDolar;
	private float ventasEfectivoDiarioDolar,
		ventasEfectivoDiarioCordobas,
		ventasEfectivoRangoDolar,
		ventasEfectivoRangoCordobas,
		ventasCreditoDiarioDolar,
		ventasCreditoDiarioCordobas,
		ventasCreditoRangoDolar,
		ventasCreditoRangoCordobas,
		ventasTarjetaDiarioDolar,
		ventasTajetaDiarioCordobas,
		ventasTarjetaRangoDolar,
		ventasTarjetaRangoCordobas,
		abonosDiariosDolar,
		abonosDiariosCordobas,
		abonosRangoDolar,
		abonosRangoCordobas,
		abonosTajetaCordobasDiario,
		abonosTarjetaDolarDiario,
		abonosTarjetaCordobasRango,
		abonosTarjetaDolarRango,
		ingresoDiarioDolar,
		ingresoDiarioCordobas,
		ingresoRangoDolar,
		ingresoRangoCordobas,
		salidaDiarioDolares,
		salidaDiarioCordobas,
		salidaRangoDolares,
		salidaRangoCordobas,
		ventasTotalesDolarRango,
		ventasTotalesCordobasRango,
		ventasTotalesCordobasDiario,
		ventasTotalesDolarDiario,
		totalIngresoBancosCordobas,
		totalIngresosBancosDolar,
		inversionDolares,
		inversionCordobas;

	public Reportes() {
		this.pst = null;
		this.consulta = null;
		this.cn = null;
	}

	public float getVentasEfectivoDiarioDolar() {
		return ventasEfectivoDiarioDolar;
	}

	public void setVentasEfectivoDiarioDolar(float ventasEfectivoDiarioDolar) {
		this.ventasEfectivoDiarioDolar = ventasEfectivoDiarioDolar;
	}

	public float getVentasEfectivoDiarioCordobas() {
		return ventasEfectivoDiarioCordobas;
	}

	public void setVentasEfectivoDiarioCordobas(float ventasEfectivoDiarioCordobas) {
		this.ventasEfectivoDiarioCordobas = ventasEfectivoDiarioCordobas;
	}

	public float getVentasEfectivoRangoDolar() {
		return ventasEfectivoRangoDolar;
	}

	public void setVentasEfectivoRangoDolar(float ventasEfectivoRangoDolar) {
		this.ventasEfectivoRangoDolar = ventasEfectivoRangoDolar;
	}

	public float getVentasEfectivoRangoCordobas() {
		return ventasEfectivoRangoCordobas;
	}

	public void setVentasEfectivoRangoCordobas(float ventasEfectivoRangoCordobas) {
		this.ventasEfectivoRangoCordobas = ventasEfectivoRangoCordobas;
	}

	public float getVentasCreditoDiarioDolar() {
		return ventasCreditoDiarioDolar;
	}

	public void setVentasCreditoDiarioDolar(float ventasCreditoDiarioDolar) {
		this.ventasCreditoDiarioDolar = ventasCreditoDiarioDolar;
	}

	public float getVentasCreditoDiarioCordobas() {
		return ventasCreditoDiarioCordobas;
	}

	public void setVentasCreditoDiarioCordobas(float ventasCreditoDiarioCordobas) {
		this.ventasCreditoDiarioCordobas = ventasCreditoDiarioCordobas;
	}

	public float getVentasCreditoRangoDolar() {
		return ventasCreditoRangoDolar;
	}

	public void setVentasCreditoRangoDolar(float ventasCreditoRangoDolar) {
		this.ventasCreditoRangoDolar = ventasCreditoRangoDolar;
	}

	public float getVentasCreditoRangoCordobas() {
		return ventasCreditoRangoCordobas;
	}

	public void setVentasCreditoRangoCordobas(float ventasCreditoRangoCordobas) {
		this.ventasCreditoRangoCordobas = ventasCreditoRangoCordobas;
	}

	public float getVentasTarjetaDiarioDolar() {
		return ventasTarjetaDiarioDolar;
	}

	public void setVentasTarjetaDiarioDolar(float ventasTarjetaDiarioDolar) {
		this.ventasTarjetaDiarioDolar = ventasTarjetaDiarioDolar;
	}

	public float getVentasTajetaDiarioCordobas() {
		return ventasTajetaDiarioCordobas;
	}

	public void setVentasTajetaDiarioCordobas(float ventasTajetaDiarioCordobas) {
		this.ventasTajetaDiarioCordobas = ventasTajetaDiarioCordobas;
	}

	public float getVentasTarjetaRangoDolar() {
		return ventasTarjetaRangoDolar;
	}

	public void setVentasTarjetaRangoDolar(float ventasTarjetaRangoDolar) {
		this.ventasTarjetaRangoDolar = ventasTarjetaRangoDolar;
	}

	public float getVentasTarjetaRangoCordobas() {
		return ventasTarjetaRangoCordobas;
	}

	public void setVentasTarjetaRangoCordobas(float ventasTarjetaRangoCordobas) {
		this.ventasTarjetaRangoCordobas = ventasTarjetaRangoCordobas;
	}

	public float getAbonosDiariosDolar() {
		return abonosDiariosDolar;
	}

	public void setAbonosDiariosDolar(float abonosDiariosDolar) {
		this.abonosDiariosDolar = abonosDiariosDolar;
	}

	public float getAbonosDiariosCordobas() {
		return abonosDiariosCordobas;
	}

	public void setAbonosDiariosCordobas(float abonosDiariosCordobas) {
		this.abonosDiariosCordobas = abonosDiariosCordobas;
	}

	public float getAbonosRangoDolar() {
		return abonosRangoDolar;
	}

	public void setAbonosRangoDolar(float abonosRangoDolar) {
		this.abonosRangoDolar = abonosRangoDolar;
	}

	public float getIngresoDiarioDolar() {
		return ingresoDiarioDolar;
	}

	public void setIngresoDiarioDolar(float ingresoDiarioDolar) {
		this.ingresoDiarioDolar = ingresoDiarioDolar;
	}

	public float getIngresoDiarioCordobas() {
		return ingresoDiarioCordobas;
	}

	public void setIngresoDiarioCordobas(float ingresoDiarioCordobas) {
		this.ingresoDiarioCordobas = ingresoDiarioCordobas;
	}

	public float getSalidaDiarioDolares() {
		return salidaDiarioDolares;
	}

	public void setSalidaDiarioDolares(float salidaDiarioDolares) {
		this.salidaDiarioDolares = salidaDiarioDolares;
	}

	public float getSalidaDiarioCordobas() {
		return salidaDiarioCordobas;
	}

	public void setSalidaDiarioCordobas(float salidaDiarioCordobas) {
		this.salidaDiarioCordobas = salidaDiarioCordobas;
	}

	public float getAbonosRangoCordobas() {
		return abonosRangoCordobas;
	}

	public void setAbonosRangoCordobas(float abonosRangoCordobas) {
		this.abonosRangoCordobas = abonosRangoCordobas;
	}

	public float getIngresoRangoDolar() {
		return ingresoRangoDolar;
	}

	public void setIngresoRangoDolar(float ingresoRangoDolar) {
		this.ingresoRangoDolar = ingresoRangoDolar;
	}

	public float getSalidaRangoDolares() {
		return salidaRangoDolares;
	}

	public void setSalidaRangoDolares(float salidaRangoDolares) {
		this.salidaRangoDolares = salidaRangoDolares;
	}

	public float getSalidaRangoCordobas() {
		return salidaRangoCordobas;
	}

	public void setSalidaRangoCordobas(float salidaRangoCordobas) {
		this.salidaRangoCordobas = salidaRangoCordobas;
	}

	public float getAbonosTajetaCordobasDiario() {
		return abonosTajetaCordobasDiario;
	}

	public void setAbonosTajetaCordobasDiario(float abonosTajetaCordobasDiario) {
		this.abonosTajetaCordobasDiario = abonosTajetaCordobasDiario;
	}

	public float getAbonosTarjetaDolarDiario() {
		return abonosTarjetaDolarDiario;
	}

	public void setAbonosTarjetaDolarDiario(float abonosTarjetaDolarDiario) {
		this.abonosTarjetaDolarDiario = abonosTarjetaDolarDiario;
	}

	public float getAbonosTarjetaCordobasRango() {
		return abonosTarjetaCordobasRango;
	}

	public void setAbonosTarjetaCordobasRango(float abonosTarjetaCordobasRango) {
		this.abonosTarjetaCordobasRango = abonosTarjetaCordobasRango;
	}

	public float getAbonosTarjetaDolarRango() {
		return abonosTarjetaDolarRango;
	}

	public void setAbonosTarjetaDolarRango(float abonosTarjetaDolarRango) {
		this.abonosTarjetaDolarRango = abonosTarjetaDolarRango;
	}

	public float getIngresoRangoCordobas() {
		return ingresoRangoCordobas;
	}

	public void setIngresoRangoCordobas(float ingresoRangoCordobas) {
		this.ingresoRangoCordobas = ingresoRangoCordobas;
	}

	public float getVentasTotalesDolarRango() {
		return ventasTotalesDolarRango;
	}

	public void setVentasTotalesDolarRango(float ventasTotalesDolarRango) {
		this.ventasTotalesDolarRango = ventasTotalesDolarRango;
	}

	public float getVentasTotalesCordobasRango() {
		return ventasTotalesCordobasRango;
	}

	public void setVentasTotalesCordobasRango(float ventasTotalesCordobasRango) {
		this.ventasTotalesCordobasRango = ventasTotalesCordobasRango;
	}

	public float getVentasTotalesCordobasDiario() {
		return ventasTotalesCordobasDiario;
	}

	public void setVentasTotalesCordobasDiario(float ventasTotalesCordobasDiario) {
		this.ventasTotalesCordobasDiario = ventasTotalesCordobasDiario;
	}

	public float getVentasTotalesDolarDiario() {
		return ventasTotalesDolarDiario;
	}

	public void setVentasTotalesDolarDiario(float ventasTotalesDolarDiario) {
		this.ventasTotalesDolarDiario = ventasTotalesDolarDiario;
	}

	public float getTotalIngresoBancosCordobas() {
		return totalIngresoBancosCordobas;
	}

	public void setTotalIngresoBancosCordobas(float totalIngresoBancosCordobas) {
		this.totalIngresoBancosCordobas = totalIngresoBancosCordobas;
	}

	public float getTotalIngresosBancosDolar() {
		return totalIngresosBancosDolar;
	}

	public void setTotalIngresosBancosDolar(float totalIngresosBancosDolar) {
		this.totalIngresosBancosDolar = totalIngresosBancosDolar;
	}

	public void setPrecioDolar(float precio) {
		this.precioDolar = precio;
	}

	public float getDolares() {
		return Dolares;
	}

	public float getEgresoDolares() {
		return this.EgresoDolares;
	}

	public float getDolaresComprados() {
		return this.dolaresComprados;
	}

	public DefaultTableModel getModeloTable() {
		return this.modelo;
	}

	public float getInversionDolares() {
		return inversionDolares;
	}

	public void setInversionDolares(float inversionDolares) {
		this.inversionDolares = inversionDolares;
	}

	public float getInversionCordobas() {
		return inversionCordobas;
	}

	public void setInversionCordobas(float inversionCordobas) {
		this.inversionCordobas = inversionCordobas;
	}

	

	//mostrar facturas realizadas por dia 
	public DefaultTableModel ReporteDiario(Date Fecha) {
		cn = Conexion();
		this.consulta = "SELECT facturas.id,facturas.fecha AS fechaFactura, impuestoISV, totalDolares, totalCordobas, nombre_comprador,"
			+ " formapago.tipoVenta,creditos.id as idCredito, cajas.caja, facturas.usuario from facturas LEFT JOIN formapago ON(formapago.id = facturas.tipoVenta)"
			+ " LEFT JOIN creditos ON(facturas.credito = creditos.id) LEFT JOIN cajas ON(facturas.caja=cajas.id) WHERE"
			+ " DATE(facturas.fecha) = ? ORDER BY facturas.id DESC";
		String[] Resultados = new String[10];
		String[] titulos = {"Factura", "Fecha y hora", "IVA", "Total Dolar", "Total Cordobas", "Comprador", "Forma Pago", "N° Credito", "Caja", "Usuario"};
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			pst = this.cn.prepareStatement(consulta);
			pst.setDate(1, Fecha);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Resultados[0] = rs.getString("id");
				Resultados[1] = rs.getString("fechaFactura");
				Resultados[2] = rs.getString("impuestoISV");
				Resultados[3] = rs.getString("totalDolares");
				Resultados[4] = rs.getString("totalCordobas");
				Resultados[5] = rs.getString("nombre_comprador");
				Resultados[6] = rs.getString("tipoVenta");
				Resultados[7] = rs.getString("idCredito");
				Resultados[8] = rs.getString("caja");
				Resultados[9] = rs.getString("usuario");
				modelo.addRow(Resultados);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion reporteDiario");
		}
		return modelo;
	}

	//mostrar detalles de facturas
	public DefaultTableModel DetalleFactura(int id) {
		cn = Conexion();
		this.consulta = "SELECT productos.id,codigoBarra,nombre, detallefactura.id AS idDetalle,precioProducto,cantidadProducto,"
			+ "totalVenta FROM productos RIGHT JOIN detallefactura ON(productos.id = detallefactura.producto) LEFT JOIN facturas"
			+ " ON(detallefactura.factura = facturas.id) WHERE facturas.id = " + id + " AND detallefactura.cantidadProducto > 0";
		String[] registros = new String[7];
		String[] titulos = {"Detalle", "Id Producto", "Codigo Barra", "Producto", "Cantidad", "Precio", "Total"};
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery(this.consulta);
			while (rs.next()) {
				registros[0] = rs.getString("idDetalle");
				registros[1] = rs.getString("id");
				registros[2] = rs.getString("codigoBarra");
				registros[3] = rs.getString("nombre");
				registros[4] = rs.getString("cantidadProducto");
				registros[5] = rs.getString("precioProducto");
				registros[6] = rs.getString("totalVenta");
				this.modelo.addRow(registros);
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return modelo;
	}

	//mostrar total de creditos realizados diariamente
	public void ventasCreditoDiario(Date fecha) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas"
			+ " INNER JOIN creditos ON(facturas.credito = creditos.id) INNER JOIN cajas ON(facturas.caja=cajas.id)"
			+ " WHERE DATE(facturas.fecha) = ? AND cajas.caja='CAJA1'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.ventasCreditoDiarioCordobas = rs.getFloat("totalCordobas");
				this.ventasCreditoDiarioDolar = rs.getFloat("totalDolares");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " credito diario");
		}
	}

	public float TotalCreditosGlobal() {
		cn = Conexion();
		float totalCreditos = 0;
		this.consulta = "SELECT SUM(totalFactura) AS totalCredito FROM facturas INNER JOIN creditos ON(facturas.credito = creditos.id)"
			+ " INNER JOIN cajas ON(facturas.caja=cajas.id) WHERE cajas.caja='CAJA1'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				totalCreditos = rs.getFloat("totalCredito");
			}
			if (totalCreditos == 0) {
				totalCreditos = 0;
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " credito global");
		}
		return totalCreditos;
	}

	//mostrar creditso realizados segun rango de fechas
	public void ventasCreditosRango(Date fechaInicio, Date fechaFinal) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas"
			+ " INNER JOIN creditos ON(facturas.credito = creditos.id) INNER JOIN cajas ON(facturas.caja=cajas.id)"
			+ " WHERE DATE(facturas.fecha) BETWEEN ? AND ? AND cajas.caja='CAJA1'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fechaInicio);
			pst.setDate(2, fechaFinal);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ventasCreditoRangoCordobas = rs.getFloat("totalCordobas");
				ventasCreditoRangoDolar = rs.getFloat("totalDolares");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " funcion creditoMensual");
		}
	}

	//mostrar los Egresos por rango de fechas
	public void salidaEfectivoRango(Date fecha1, Date fecha2) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS totalGasto FROM transaccion INNER JOIN cajas ON(transaccion.caja=cajas.id)"
			+ " WHERE DATE(fecha) BETWEEN ? AND ? AND tipoTransaccion = 'Egreso' AND cajas.caja = 'CAJA1'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.salidaRangoCordobas = rs.getFloat("totalGasto");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " funcion totalgastos");
		}
	}

	//mostrar los egresos por dias
	public void salidaEfectivoDiarioCordobas(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS totalGasto FROM transaccion INNER JOIN cajas ON(transaccion.caja = cajas.id)"
			+ " WHERE DATE(fecha) = ? AND cajas.caja='CAJA1' AND transaccion.tipoTransaccion = 'Egreso' AND moneda = 'Córdobas'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.salidaDiarioCordobas = rs.getFloat("totalGasto");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " gasto diario");
		}
	}

	public void salidaEfectivoDiarioDolar(Date fecha) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS totalGasto FROM transaccion INNER JOIN cajas ON(transaccion.caja = cajas.id)"
			+ " WHERE DATE(fecha) = ? AND cajas.caja='CAJA1' AND transaccion.tipoTransaccion = 'Egreso' AND moneda = 'Dolar'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.salidaDiarioDolares = rs.getFloat("totalGasto");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " gasto diario");
		}

	}

	public float TotalGastosGlobal() {
		cn = Conexion();
		float totalGasto = 0;
		this.consulta = "SELECT SUM(monto) AS totalGasto FROM transaccion INNER JOIN cajas ON(transaccion.caja = cajas.id) WHERE"
			+ " cajas.caja='CAJA1' AND transaccion.tipoTransaccion = 'Egreso'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				totalGasto = rs.getFloat("totalGasto");
			}
			if (totalGasto == 0) {
				totalGasto = 0;
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " gasto diario");
		}
		return totalGasto;
	}

	public float TotalIngresoEfectivoGlobal() {
		float ingresos = 0;
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS totalGasto FROM transaccion INNER JOIN cajas ON(transaccion.caja = cajas.id) WHERE"
			+ " cajas.caja='CAJA1' AND transaccion.tipoTransaccion = 'Ingreso'";
		try {
			pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ingresos = rs.getFloat("totalGasto");
			}
			if (ingresos == 0) {
				ingresos = 0;
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " funcion TotalIngresosEfectivos");
		}
		return ingresos;
	}

	public void ingresoEfectivoDiarioCordobas(Date fecha) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(t.monto) AS total FROM transaccion AS t "
			+ "INNER JOIN cajas AS c ON(t.caja = c.id) WHERE DATE(t.fecha) = ? AND c.caja = 'CAJA1' AND t.tipoTransaccion = 'Ingreso' AND "
			+ "moneda = 'Córdobas'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.ingresoDiarioCordobas = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion IngresoDiarioEfectivo en modelo Reportes");
		}
	}

	public void ingresoEfectivoDiarioDolar(Date fecha) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(t.monto) AS total FROM transaccion AS t "
			+ "INNER JOIN cajas AS c ON(t.caja = c.id) WHERE DATE(t.fecha) = ? AND c.caja = 'CAJA1' AND t.tipoTransaccion = 'Ingreso' AND "
			+ "moneda = 'Dolar'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.ingresoDiarioDolar = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion IngresoDiarioEfectivo en modelo Reportes");
		}
	}

	public void ingresoEfecitivoRango(Date fecha1, Date fecha2) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(t.monto) AS total FROM transaccion AS t INNER JOIN cajas AS c ON(t.caja = c.id) WHERE"
			+ " DATE(t.fecha) BETWEEN ? AND ? AND c.caja = 'CAJA1' AND t.tipoTransaccion = 'Ingreso'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha1);
			this.pst.setDate(2, fecha2);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.ingresoRangoCordobas = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion IngresoDiarioEfectivo en modelo Reportes");
		}
	}
	//metodo para Obtener el total de ingreso por pagos de creditos en efectivo por rangos de fechas

	public void abonosEfectivoCordobasRango(Date fecha1, Date fecha2) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Efectivo'"
			+ " AND pagoscreditos.moneda = 'Cordobas' AND DATE(pagoscreditos.fecha) BETWEEN ? AND ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosRangoCordobas = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la totalpagosefectivo");
		}
	}

	public void abonosEfectivoDolarRango(Date fecha1, Date fecha2) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Efectivo'"
			+ " AND pagoscreditos.moneda = 'Dolar' AND DATE(pagoscreditos.fecha) BETWEEN ? AND ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosRangoDolar = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo totalpagosefectivo en modelo reportes");
		}
	}

	//metodo para Obtener el total de Ingresos por pagos en efectivo por dia 
	public void abonosEfectivoCordobasDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Efectivo' AND pagoscreditos.moneda = 'cordobas'"
			+ " AND DATE(pagoscreditos.fecha) = ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosDiariosCordobas = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagoefectivodiario");
		}
	}

	public void abonosEfecitvoDolarDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Efectivo' AND pagoscreditos.moneda = 'Dolar'"
			+ " AND DATE(pagoscreditos.fecha) = ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosDiariosDolar = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagoefectivodiario");
		}
	}

	public float totalPagosEfectivoGlobal() {
		cn = Conexion();
		float pagos = 0;
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Efectivo'";
		try {
			pst = cn.prepareStatement(consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				pagos = rs.getFloat("totalPagos");
			}
			if (pagos == 0) {
				pagos = 0;
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagoefectivodiario");
		}
		return pagos;
	}

	//metodo para Obtener el total de ingreso por pagos de creditos con tarjeta por dia
	public void abonosTarjetaCordobasDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Tarjeta' AND pagoscreditos.moneda = 'Cordobas'"
			+ " AND DATE(pagoscreditos.fecha) = ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosTajetaCordobasDiario = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagotarjetadiario");
		}
	}

	public void abonosTarjetaDolarDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Tarjeta' AND pagoscreditos.moneda = 'Dolar'"
			+ " AND DATE(pagoscreditos.fecha) = ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosTarjetaDolarDiario = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagotarjetadiario");
		}
	}

	public float totalPagosTarjetaGlobal() {
		cn = Conexion();
		float pagos = 0;
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Tarjeta'";
		try {
			pst = cn.prepareStatement(consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				pagos = rs.getFloat("totalPagos");
			}
			if (pagos == 0) {
				pagos = 0;
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagotarjetadiario");
		}
		return pagos;
	}

	//metodo para Obtener el total de ingreso por pagos de creditos con tarjeta  por rango de fechas
	public void abonosTarjetaCordobasRango(Date fecha1, Date fecha2) {
		cn = Conexion();
		float pagos = 0;
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Tarjeta' AND pagoscreditos.moneda = 'cordobas' AND"
			+ " DATE(pagoscreditos.fecha) BETWEEN ? AND ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosTarjetaCordobasRango = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " funcion totalPagosTarjeta");
		}
	}

	public void abonosTarjetaDolarRango(Date fecha1, Date fecha2) {
		cn = Conexion();
		float pagos = 0;
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN formapago"
			+ " ON(pagoscreditos.formaPago = formapago.id) WHERE formapago.tipoVenta = 'Tarjeta' AND pagoscreditos.moneda = 'cordobas' AND"
			+ " DATE(pagoscreditos.fecha) BETWEEN ? AND ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.abonosTarjetaDolarRango = rs.getFloat("TotalPagos");
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " funcion totalPagosTarjeta");
		}
	}

	//metodo para obtener todos los pagos de forma global sumando los pagos con tarjeta y en efectivo
	public String totalPagos(Date fecha1, Date fecha2) {
		cn = Conexion();
		String pagos = "";
		this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos WHERE DATE(pagoscreditos.fecha) BETWEEN ? AND ?";
		try {
			pst = cn.prepareStatement(consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				pagos = rs.getString("totalPagos");
			}
			if (pagos == null) {
				pagos = "0";
			}
			cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " totalpagos");
		}
		return pagos;
	}

	//obtener nombres de cliente segun el id
	public String nombreCliente(String id) {
		cn = Conexion();
		String Nombres = "";
		this.consulta = "SELECT clientes.nombres FROM clientes INNER JOIN creditos ON(clientes.id = creditos.cliente) WHERE creditos.id = ?";
		try {
			this.pst = this.cn.prepareStatement(consulta);
			this.pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Nombres = rs.getString("nombres");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion nombres clientes");
		}
		return Nombres;
	}

	public String apellidoCliente(String id) {
		cn = Conexion();
		String apellidos = "";
		this.consulta = "SELECT clientes.apellidos FROM clientes INNER JOIN creditos ON(clientes.id = creditos.cliente) WHERE creditos.id = ?";
		try {
			this.pst = this.cn.prepareStatement(consulta);
			this.pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				apellidos = rs.getString("apellidos");
			}

			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion apellidos clientes");
		}
		return apellidos;
	}

	public void proyeccionVentas() {
		cn = Conexion();
		this.consulta = "SELECT DISTINCT (SELECT SUM(precioVenta*stock) FROM productos WHERE monedaVenta='Dolar' AND estado = 1)"
			+ " AS proyeccionDolares, (SELECT SUM(precioVenta*stock) FROM productos WHERE monedaVenta='Córdobas' AND estado = 1)"
			+ " AS proyeccionCordobas FROM productos";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.inversionCordobas = rs.getFloat("proyeccionCordobas");
				this.inversionDolares = rs.getFloat("proyeccionDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion inversion en modelo");
		}
	}

	public void inversionCordobas() {
		cn = Conexion();
		this.consulta = "SELECT SUM(precioCompra*stock) AS inversion FROM productos WHERE monedaCompra = 'Córdobas' AND estado = 1";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.inversionCordobas = rs.getFloat("inversion");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion inversion en modelo");
		}
	}

	public void inversionDolar() {
		cn = Conexion();
		this.consulta = "SELECT SUM(precioCompra*stock) AS inversion FROM productos WHERE monedaCompra = 'Dolar' AND estado = 1";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.inversionDolares = rs.getFloat("inversion");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion inversion en modelo");
		}
	}

	//Obtener Ingresos efectivo en caja por rango de fechas
	public void ventasEfectivoRango(Date fecha1, Date fecha2) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas"
			+ " INNER JOIN formapago ON(formapago.id=facturas.tipoVenta) INNER JOIN cajas ON(facturas.caja=cajas.id)"
			+ " WHERE DATE(fecha) BETWEEN ? AND ? AND formapago.tipoVenta = 'Efectivo' AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.ventasEfectivoRangoCordobas = rs.getFloat("totalCordobas");
				this.ventasEfectivoRangoDolar = rs.getFloat("totalDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresoEfectivoCaja en modelo");
		}
	}

	//obtener Ingresos en efectivo a cajas por dia
	public void ventasEfectivoDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas"
			+ " INNER JOIN formapago ON(formapago.id=facturas.tipoVenta) INNER JOIN cajas ON(facturas.caja=cajas.id)"
			+ " WHERE DATE(fecha) = ? AND formapago.tipoVenta = 'Efectivo' AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.ventasEfectivoDiarioDolar = rs.getFloat("totalDolares");
				this.ventasEfectivoDiarioCordobas = rs.getFloat("totalCordobas");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresoEfectivoCaja en modelo reportes");
		}
	}

	public void ventasTarjetaDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas"
			+ " INNER JOIN formapago ON(formapago.id=facturas.tipoVenta) INNER JOIN cajas ON(facturas.caja=cajas.id)"
			+ " WHERE DATE(fecha) = ? AND formapago.tipoVenta = 'Tarjeta' AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.ventasTarjetaDiarioDolar = rs.getFloat("totalDolares");
				this.ventasTajetaDiarioCordobas = rs.getFloat("totalCordobas");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresoEfectivoCaja en modelo reportes");
		}
	}

	public float ingresoEfectivoCajaGlobal() {
		float total = 0;
		cn = Conexion();
		this.consulta = "SELECT SUM(totalFactura) AS total FROM facturas INNER JOIN formapago ON(formapago.id=facturas.tipoVenta) INNER JOIN cajas ON(facturas.caja=cajas.id) WHERE formapago.tipoVenta = 'Efectivo' AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresoEfectivoCaja en modelo");
		}
		return total;
	}

	//obtener los ingresos totales osea todo lo vendido seun rango de fechas
	public void IngresosTotales(Date fecha1, Date fecha2) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas"
			+ " INNER JOIN cajas ON(facturas.caja=cajas.id) WHERE DATE(fecha) BETWEEN ? AND ? AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.ventasTotalesCordobasRango = rs.getFloat("totalCordobas");
				this.ventasTotalesDolarRango = rs.getFloat("totalDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresosTotales en modelo");
		}
	}

	//obtener los ingresos totales osea todo lo vendido por dia
	public void IngresosTotalesDiario(Date fecha1) {
		cn = Conexion();
		this.consulta = "SELECT SUM(totalCordobas) AS totalCordobas, SUM(totalDolares) AS totalDolares FROM facturas INNER JOIN"
			+ " cajas ON(facturas.caja=cajas.id) WHERE DATE(fecha) = ? AND cajas.caja = 'CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				this.ventasTotalesCordobasDiario = rs.getFloat("totalCordobas");
				this.ventasTotalesDolarDiario = rs.getFloat("totalDolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresosTotales en modelo");
		}
	}

	public float IngresosTotalesGlobal() {
		float total = 0;
		cn = Conexion();
		this.consulta = "SELECT SUM(totalFactura) AS total FROM facturas INNER JOIN cajas ON(facturas.caja=cajas.id) WHERE cajas.caja = 'CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion IngresosTotales en modelo");
		}
		return total;
	}

	//Ingresos totales a bancos segun rangos de fecha (ventas con tarjeta de credito o debito)
	public float IngresoAbancos(Date fecha1, Date fecha2) {
		float total = 0;
		cn = Conexion();
		this.consulta = "SELECT SUM(totalFactura) AS total FROM facturas INNER JOIN formapago"
			+ " ON(formapago.id=facturas.tipoVenta) INNER JOIN cajas ON(facturas.caja=cajas.id) WHERE"
			+ " DATE(fecha) BETWEEN ? AND ? AND formapago.tipoVenta = 'Tarjeta' AND cajas.caja = 'CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha1);
			pst.setDate(2, fecha2);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion Ingreso A bancos en modelo");
		}
		return total;
	}

	//Ingresos totales a bancos por dia (ventas con tarjeta de credito o debito)
	public void IngresoAbancosDiarioCordobas() {
		this.totalIngresoBancosCordobas = this.abonosTajetaCordobasDiario + this.ventasTajetaDiarioCordobas;
	}

	public void IngresoAbancosDiarioDolares() {
		this.totalIngresosBancosDolar = this.abonosTarjetaDolarDiario + this.ventasTarjetaDiarioDolar;
	}

	public float IngresoAbancosGlobal() {
		float total = 0;
		cn = Conexion();
		this.consulta = "SELECT SUM(totalFactura) AS total FROM facturas INNER JOIN formapago ON(formapago.id=facturas.tipoVenta) INNER JOIN cajas ON(facturas.caja=cajas.id) WHERE formapago.tipoVenta = 'Tarjeta' AND cajas.caja = 'CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion Ingreso A bancos en modelo");
		}
		return total;
	}

	public float baseEfectivoDiario(Date fecha) {
		float base = 0;
		this.cn = Conexion();
		this.consulta = "SELECT efectivo FROM aperturas INNER JOIN cajas ON(aperturas.caja=cajas.id)"
			+ " WHERE aperturas.fecha = ? AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			pst.setDate(1, fecha);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				base = rs.getFloat("efectivo");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion baseEfectivoDiario");
		}
		return base;
	}

	public float PrimeraApertura() {
		float monto = 0;
		this.cn = Conexion();
		this.consulta = "SELECT efectivo FROM aperturas INNER JOIN cajas ON(aperturas.caja=cajas.id) WHERE aperturas.id = 1 AND cajas.caja='CAJA1'";
		try {
			PreparedStatement pst = this.cn.prepareStatement(this.consulta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				monto = rs.getFloat("efectivo");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "funcion UltimaApertura");
		}

		return monto;
	}

	/*                                            REPORTES BUSSINESS INTELIGENCE                                                  */
 /*------------------------------------------------------------------------------------------------------------------------------------------------*/
	public DefaultTableModel productosMasVendidos(Date fecha1, Date fecha2) {
		int n = 1;
		this.cn = Conexion();
		this.consulta = "SELECT p.id,p.nombre, m.nombre AS marca, p.descripcion, SUM(d.cantidadProducto) AS vendido FROM productos AS p "
			+ "LEFT JOIN detalleFactura AS d ON(p.id=d.producto) LEFT JOIN marca AS m ON(p.marca=m.id) LEFT JOIN facturas AS f ON(d.factura = f.id) "
			+ "WHERE f.fecha BETWEEN ? AND ? GROUP BY p.id ORDER BY vendido DESC";
		String[] registros = new String[5];
		String[] title = {"ID", "Nombre", "Marca", "Descripción", "Vendido"};
		this.modelo = new DefaultTableModel(null, title) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha1);
			this.pst.setDate(2, fecha2);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				registros[0] = rs.getString("id");
				registros[1] = rs.getString("nombre");
				registros[2] = rs.getString("marca");
				registros[3] = rs.getString("descripcion");
				registros[4] = rs.getString("vendido") + " Unidades";
				this.modelo.addRow(registros);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion productosMasVendidos");
		}

		return this.modelo;

	}

	public DefaultTableModel BuscarFactura(int id) {
		this.cn = Conexion();
		this.consulta = "SELECT facturas.id,facturas.fecha AS fechaFactura, impuestoISV, totalDolares,totalCordobas, nombre_comprador, formapago.tipoVenta, creditos.id as idCredito, cajas.caja,facturas.usuario from facturas LEFT JOIN formapago ON(formapago.id = facturas.tipoVenta) LEFT JOIN creditos ON(facturas.credito = creditos.id) LEFT JOIN cajas ON(facturas.caja=cajas.id) WHERE facturas.id = ?";
		String[] facturas = new String[10];
		String[] titulos = {"Factura", "Fecha", "IVA", "Total Dólar", "Total Córdobas", "Comprador", "Forma Pago", "N° Credito", "Caja", "Usuario"};
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
//            this.pst.setDate(1, fecha);
			this.pst.setInt(1, id);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				facturas[0] = rs.getString("id");
				facturas[1] = rs.getString("fechaFactura");
				facturas[2] = rs.getString("impuestoISV");
				facturas[3] = rs.getString("totalDolares");
				facturas[4] = rs.getString("totalCordobas");
				facturas[5] = rs.getString("nombre_comprador");
				facturas[6] = rs.getString("tipoVenta");
				facturas[7] = rs.getString("idCredito");
				facturas[8] = rs.getString("caja");
				facturas[9] = rs.getString("usuario");
				modelo.addRow(facturas);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "Error en la funcion BuscarFactura en modelo Reportes");
		}
		return this.modelo;
	}

	public void MonedasRecibidas(Date fecha) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(cantDolares) AS Dolares FROM monedasRecibidas WHERE fecha = ? AND tipoMovimiento = 'Ingreso' AND compra_venta = 'venta'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.Dolares = rs.getFloat("Dolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion MonedasRecibidas en modelo Reportes");
		}
	}

	public void MonedasRecibidasCompra(Date fecha) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(cantDolares) AS Dolares FROM monedasRecibidas WHERE fecha = ? AND tipoMovimiento = 'Ingreso' AND compra_venta = 'compra'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.dolaresComprados = rs.getFloat("Dolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en la funcion MonedasRecibidasCompra en modelo");
		}
	}

	public void EgresoMonedas(Date fecha) {
		this.cn = Conexion();
		this.consulta = "SELECT SUM(cantDolares) AS Dolares FROM monedasRecibidas WHERE fecha = ? AND tipoMovimiento = 'Salida'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				this.EgresoDolares = rs.getFloat("Dolares");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " en la funcion MonedasRecibidas en modelo Reportes");
		}
	}

	public float precioVenta(Date fecha) {
		float total = 0, totalUtilidades = 0;
		this.cn = Conexion();
		this.consulta = "select sum(f.totalFactura) as total from facturas as f where f.fecha=?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
			totalUtilidades = total - (precioCompraDolar(fecha) + precioCompraCordobas(fecha));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en la funcion precioVenta en el modelo de reporte");
		}
		return totalUtilidades;
	}

	public float precioCompraCordobas(Date fecha) {
		float total = 0;
		this.cn = Conexion();
		this.consulta = "select sum(df.cantidadProducto*p.precioCompra) as total from detalleFactura as df"
			+ " inner join facturas as f on(f.id=df.factura) inner join productos as p on(p.id=df.producto)"
			+ " where f.fecha = ? and p.monedaCompra='Córdobas'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en la funcion precioCompra en el modelo de reporte");
		}
		return total;
	}

	public float precioCompraDolar(Date fecha) {
		float total = 0;
		this.cn = Conexion();
		this.consulta = "select sum(df.cantidadProducto*p.precioCompra) as total from detalleFactura as df"
			+ " inner join facturas as f on(f.id=df.factura) inner join productos as p on(p.id=df.producto)"
			+ " where f.fecha = ? and p.monedaCompra='Dolar'";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				total = rs.getFloat("total");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "en la funcion precioCompra en el modelo de reporte");
		}
		return total * this.precioDolar;
	}

	public void productosVentasDiarias(Date fecha) {
		this.cn = Conexion();
		String[] titulos = {"N°", "Cantidad", "Nombre", "Precio", "Importe", "Factura"};
		String[] datos = new String[6];
		String numeracion = "SET @numero=0";
		this.consulta = "SELECT @numero:=@numero+1 AS num,df.cantidadProducto,p.nombre, df.precioProducto, df.totalVenta,"
			+ " p.monedaVenta, df.factura FROM detalleFactura AS df INNER JOIN facturas AS f ON(df.factura=f.id) INNER JOIN "
			+ "productos AS p ON(df.producto=p.id) WHERE DATE(f.fecha) = ? ORDER BY df.id ASC";
		this.modelo = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		try {
			this.pst = this.cn.prepareStatement(numeracion);
			this.pst.execute();
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha);
			ResultSet rs = this.pst.executeQuery();
			while (rs.next()) {
				datos[0] = rs.getString("num");
				datos[1] = rs.getString("cantidadProducto");
				datos[2] = rs.getString("nombre");
				datos[3] = rs.getString("precioProducto");
				datos[4] = rs.getString("totalVenta");
				datos[5] = rs.getString("factura");
				this.modelo.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR: en el metodo productosVentasDiarias en el modelo reportes");
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
