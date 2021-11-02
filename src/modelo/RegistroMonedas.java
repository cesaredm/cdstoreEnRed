/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class RegistroMonedas extends Conexiondb{
    PreparedStatement pst;
    Connection cn;
    int banderin;
    String consulta;
    DefaultTableModel modelo;
    
    public RegistroMonedas(){
        this.cn = null;
        this.pst = null;
        
    }
    
    public void guardarMonedasRecibidas(Date fecha, float dolares, String movimiento, int facturaOpago, String compraOventa, boolean bandera){
        this.cn = Conexion();
        if(bandera){
            this.consulta = "INSERT INTO monedasRecibidas(fecha,cantDolares,tipoMovimiento,factura,pago, compra_venta) VALUES(?,?,?,?,null,?)";   
        }else{
            this.consulta = "INSERT INTO monedasRecibidas(fecha,cantDolares,tipoMovimiento,factura,pago, compra_venta) VALUES(?,?,?,null,?,?)"; 
        }
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setDate(1,fecha);
            this.pst.setFloat(2,dolares);
            this.pst.setString(3, movimiento);
            this.pst.setInt(4, facturaOpago);
            this.pst.setString(5, compraOventa);
            this.banderin = this.pst.executeUpdate();
            if(this.banderin > 0){
                JOptionPane.showMessageDialog(null, "Guardado con exito.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "En la funcion GuardarMonedasRecibidas en el modelo facturacion.");
        }
    }
    
    public void actualizarMonedasRecibidas(int id ,int nPagoFactura, float cantDolares, boolean bandera){
        this.cn = Conexion();
        this.consulta = "UPDATE monedasRecibidas SET cantDolares = ? WHERE id = ?";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setFloat(1, cantDolares);
            this.pst.setInt(2, id);
            this.banderin = this.pst.executeUpdate();
            if(this.banderin > 1){
                JOptionPane.showMessageDialog(null, "Actualización exitosa.","Informacón", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "en la funcion actualizarMonedasRecibidas en modelo facturacion");
        }
    }
    public DefaultTableModel MostrarRegistrosMonedas(Date fecha){
        String[] registro = new String[7];
        String[] titulos = {"ID","Fecha","Dólares","Movimiento","N° Factura","N° Pago","Cambio"};
        this.modelo = new DefaultTableModel(null,titulos){
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        this.cn = Conexion();
        this.consulta = "SELECT * FROM monedasRecibidas WHERE fecha = ?";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setDate(1, fecha);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("fecha");
                registro[2] = rs.getString("cantDolares");
                registro[3] = rs.getString("tipoMovimiento");
                registro[4] = rs.getString("factura");
                registro[5] = rs.getString("pago");
                registro[6] = rs.getString("compra_venta");
                this.modelo.addRow(registro);
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "en la funcion MostrarRegistroMonedas en modelo Reportes");
        }
        return this.modelo;
    }
    
    public DefaultTableModel MostrarRegistrosMonedas(String id){
        String[] registro = new String[7];
        String[] titulos = {"ID","Fecha","Dólares","Movimiento","N° Factura","N° Pago","Cambio"};
        this.modelo = new DefaultTableModel(null,titulos){
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        this.cn = Conexion();
        this.consulta = "SELECT * FROM monedasRecibidas WHERE factura = ? OR pago = ?";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, id);
            this.pst.setString(2, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("fecha");
                registro[2] = rs.getString("cantDolares");
                registro[3] = rs.getString("tipoMovimiento");
                registro[4] = rs.getString("factura");
                registro[5] = rs.getString("pago");
                registro[6] = rs.getString("compra_venta");
                this.modelo.addRow(registro);
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "en la funcion MostrarRegistroMonedas en modelo Reportes");
        }
        return this.modelo;
    }
}
