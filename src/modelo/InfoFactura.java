/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
import javax.swing.JOptionPane;
import vista.IMenu;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class InfoFactura extends Conexiondb{
    private String nombre, direccion, rfc, rango, telefono;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    private int id = 1;
    Connection cn;
    PreparedStatement pst;
    String consulta;
    
    public InfoFactura(){
        this.cn = null;
        this.pst = null;
        this.consulta = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void updateInfoFactura(int id, String nombre, String telefono, String direccion, String RFC, String rango)
    {
        this.cn = Conexion();
        this.consulta = "UPDATE infoFactura SET nombre = ?, telefono = ?, direccion = ?, RFC = ?, rangoPermitido = ? WHERE id = ?";
        
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, nombre);
            this.pst.setString(2, telefono);
            this.pst.setString(3, direccion);
            this.pst.setString(4, RFC);
            this.pst.setString(5, rango);
            this.pst.setInt(6, id);
            int banderin = this.pst.executeUpdate();
            if(banderin > 0)
            {
                JOptionPane.showMessageDialog(null, "Datos de factura Actualizados Correctamente.");
            }
            this.cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void obtenerInfoFactura()
    {
        this.cn = Conexion();
        this.consulta = "SELECT * FROM infoFactura";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                this.id = rs.getInt("id");
                this.nombre = rs.getString("nombre");
                this.telefono = rs.getString("telefono");
                this.direccion = rs.getString("direccion");
                this.rfc = rs.getString("RFC");
                this.rango = rs.getString("rangoPermitido");
            }
            this.cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
}
