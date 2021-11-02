package modelo;

import controlador.CtrlProducto;
import vista.ILogin;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Login extends Conexiondb {
    Connection cn;
    String consulta;
    PreparedStatement pst;
    Date fecha;
    private String nombreUsuario, passUsuario, estado;
    private long fechaInstalacion;

    public Login() {
        this.cn = null;
        this.pst = null;
        this.consulta = "";
        this.estado = "";
    }
    
    public long getFechaIntalacion() {
        return fechaInstalacion;
    }

    public void setFechaIntalacion(long fechaIntalacion) {
        this.fechaInstalacion = fechaIntalacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }
    
    public int ValidacionAdmin() 
    {
        this.cn = Conexion();
        String consultaUsuario = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND password = ?";
        int bandera = 0;
        try {
            this.pst = cn.prepareStatement(consultaUsuario);
            this.pst.setString(1, this.nombreUsuario);
            this.pst.setString(2, this.passUsuario);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("nombreUsuario").equals(this.nombreUsuario) && rs.getString("password").equals(this.passUsuario)) {   
                        if(rs.getString("permiso").equals("Administrador"))
                        {
                            bandera = 1;//1 si es administardor
                        }else if(rs.getString("permiso").equals("Ventas"))
                        {
                            bandera = 2;//2 si es de ventas
                        }
                }else{
                    bandera = 0;//0 si no existe
                }

            }
            this.cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex+"login");
        }
        return bandera;
    }
    
    //metodo para validar si la fecha de instalacion esta guardada
    public void validarExist()
    {
        this.cn = Conexion();
        String fecha= "";
        this.consulta = "SELECT fechaInstalacion FROM infoFactura";
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            ResultSet rs = this.pst.executeQuery();
            while(rs.next())
            {
                fecha = rs.getString("fechaInstalacion");
            }
            //si el campo de fechaInstalacion esta vacia estado pasa a false si no pasa a true y obtengo la fecha de instalacion
            if(fecha.equals(""))
            {
                this.estado = "false";
                
            }else{
                this.estado = "true";
                this.fechaInstalacion = Long.parseLong(fecha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+" en la funcion validarExist en mdl login");
        }
    }
    
    //metodo para guardar la fecha de actulizacion si no hay una fecha de instalacion, se hace una actualizacion porque esta en la tabla infoFactura de la bd la cual arranca con datos de prueba
    public void fechaIntalacion(long fecha)
    {
        if(this.estado.equals("false"))
        {
            this.cn = Conexion();
            this.consulta = "UPDATE infoFactura SET fechaInstalacion = ? WHERE id = 1";
            try {
                this.pst = this.cn.prepareStatement(this.consulta);
                this.pst.setString(1, String.valueOf(fecha));
                this.pst.executeUpdate();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e+" en la funcion fechaInstalacion en mdl Login");
            }
        }
        
    }
    
}


