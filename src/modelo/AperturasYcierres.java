/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class AperturasYcierres extends Conexiondb {

    DefaultTableModel modelo;
    Connection cn;
    PreparedStatement pst;
    String consulta;
    String[] resgistros;
    DefaultComboBoxModel combo;
    int banderin;

    public AperturasYcierres() {
        this.cn = null;
        this.pst = null;
        this.consulta = "";
        this.banderin = 0;
    }
    public void GuardarAperturas(Date fecha,int caja,float efectivo){
        this.cn = Conexion();
        this.consulta = "INSERT INTO aperturas(fecha,caja,efectivo) VALUES(?,?,?)";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setDate(1, fecha);
            this.pst.setInt(2,caja);
            this.pst.setFloat(3, efectivo);
            this.banderin = this.pst.executeUpdate();
            if(this.banderin>0)
            {
                JOptionPane.showMessageDialog(null, "Apertura de Tienda exitosamente");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " en la funcion guardaraperturas");
        }
    }
     public void GuardarCierre(String fechaInicio, String fechaFinal, String mes, int caja,float efectivo, float bancos,float creditos, float egresos, float totalV, float existenciaCaja, String descripcion){
        this.cn = Conexion();
        this.consulta = "INSERT INTO cierres(fechaInicio,fechaFinal,mes,caja,ingresoEfectivo,bancos,creditos,egresos,totalVendido,existenciaCaja,descripcion) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, fechaInicio);
            this.pst.setString(2,fechaFinal);
            this.pst.setString(3, mes);
            this.pst.setInt(4, caja);
            this.pst.setFloat(5, efectivo);
            this.pst.setFloat(6, bancos);
            this.pst.setFloat(7, creditos);
            this.pst.setFloat(8, egresos);
            this.pst.setFloat(9,totalV);
            this.pst.setFloat(10, existenciaCaja);
            this.pst.setString(11, descripcion);
            this.banderin = this.pst.executeUpdate();
            if(this.banderin>0)
            {
                JOptionPane.showMessageDialog(null, "Cierre de Mes realizado exitosamente");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " en la funcion GuardarCierre");
        }
    }
    public int IdCaja(String caja) {
        int id = 0;
        cn = Conexion();
        this.consulta = "SELECT id FROM cajas WHERE caja = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, caja);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " en la funcion idCaja");
        }
        return id;
    }
    public DefaultComboBoxModel mostrarCajas()
    {
        this.combo = new DefaultComboBoxModel();
        cn = Conexion();
        this.consulta = "SELECT caja FROM cajas";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                this.combo.addElement(rs.getString("caja"));
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+" en la funcion idCaja");
        }
        return this.combo;
    }
    
    public boolean ExistenciaApertura(Date fecha)
    {
        String fechaApertura = "";
        boolean yes = true;
        cn = Conexion();
        this.consulta = "SELECT caja FROM aperturas WHERE fecha = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            this.pst.setDate(1, fecha);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                fechaApertura = rs.getString("caja");
            }
            if(fechaApertura.equals("")){
                yes = false;
            }else{
                yes = true;
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+" en la funcion ExistenciaApertura");
        }
        return yes;
    }

}
