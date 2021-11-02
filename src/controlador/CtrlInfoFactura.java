/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.IMenu;
import modelo.InfoFactura;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlInfoFactura implements ActionListener{
    IMenu menu = null;
    InfoFactura info = null;
    public CtrlInfoFactura(IMenu menu, InfoFactura info)
    {
        this.menu = menu;
        this.info = info;
        menu.btnActualizarInfoFactura.addActionListener(this);
        info.obtenerInfoFactura();
        menu.txtInfoActual.setText(" "+info.getNombre()+"\n "+info.getDireccion()+"\n RFC:  "+info.getRfc()+"\n Rango Permitido:  "+info.getRango());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menu.btnActualizarInfoFactura){
            String nombre = menu.txtNombreTienda.getText();
            String direccion = menu.txtDireccionTienda.getText();
            String RFC = menu.txtRFCTienda.getText();
            String rango = menu.txtRangoPermitido.getText();
            String telefono = menu.txtTelefonoTienda.getText();
            info.updateInfoFactura(info.getId(), nombre, telefono, direccion, RFC, rango);
            limpiar();
            info.obtenerInfoFactura();
            menu.txtInfoActual.setText(info.getNombre()+"\n"+info.getDireccion()+"\n RFC:  "+info.getRfc()+"\n Rango Permitido:  "+info.getRango()+"\n Telefono: "+info.getTelefono());
        }
    }
    
    public void limpiar()
    {
        menu.txtNombreTienda.setText("");
        menu.txtDireccionTienda.setText("");
        menu.txtRFCTienda.setText("");
        menu.txtRangoPermitido.setText("");
        menu.txtTelefonoTienda.setText("");
    }
    
}
