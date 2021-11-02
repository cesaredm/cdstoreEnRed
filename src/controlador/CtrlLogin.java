package controlador;

import modelo.*;
import vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import modelo.AperturasYcierres;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlLogin implements ActionListener, KeyListener, FocusListener {

    private ILogin login;
    private Login modelLogin;
    public CtrlMenuOpciones menu;
    public IMenu m;
    public AperturasYcierres aperturas;
    Date fecha;
//    JSpinner spiner;
//    JSpinner spinerPrecioCompra;
//    SpinnerNumberModel sModel;
    File licencia;

    public CtrlLogin(ILogin login, Login modelLogin) {
        this.login = login;
        this.modelLogin = modelLogin;
        this.m = new IMenu();
        this.fecha = new Date();
        modelLogin.validarExist();//validamos la existencia de fecha de actualizacion
        modelLogin.fechaIntalacion(this.fecha.getTime());//si no existe una fecha de instalacion se guardara la fecha si no no hara nada 
        this.login.btnAceptar.addActionListener(this);
        this.login.btnAceptar.addKeyListener(this);
        this.login.txtNombreUsuario.addFocusListener(this);
        this.login.txtPassUsuario.addFocusListener(this);
        this.login.txtPassUsuario.addKeyListener(this);
        this.login.jsPrecioCompraDolar.addKeyListener(this);
        this.login.jsPrecioVentaDolar.addKeyListener(this);
        this.login.btnSetPrecioDolar.addActionListener(this);
    }

    public void iniciar() {
        this.login.setVisible(true);
        this.login.setLocationRelativeTo(null);
    }

    public void Preparar() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
        String f = sdf.format(this.fecha);
//        this.sModel = new SpinnerNumberModel();
//        this.sModel.setMinimum(1.00);
//        this.sModel.setValue(1.00);
//        this.sModel.setStepSize(0.01);
//        this.spiner = new JSpinner(sModel);
        this.login.lblFechaPrecioDolar.setText(f);
        this.login.jsPrecioVentaDolar.requestFocus();
        this.login.jdPrecioDolar.setSize(308, 210);
        this.login.jdPrecioDolar.setLocationRelativeTo(null);
        this.login.jdPrecioDolar.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login.btnAceptar) {
            String nombre = login.txtNombreUsuario.getText(), pass = login.txtPassUsuario.getText();
            //switch para metodo de prueba de 20 dias
            /*switch (ValidarLicencia()) {
                case "noValido": {
                    JOptionPane.showMessageDialog(null, "Su licencia de prueba finalizó..! \n para adquirir su licencia llama al : +505 85361324");
                }
                break;
                case "valido": {
                    if (!nombre.equals("") && !pass.equals("")) {
                        modelLogin.setNombreUsuario(nombre);
                        modelLogin.setPassUsuario(pass);
                        switch (modelLogin.ValidacionAdmin()) {
                            case 1: {
                                this.menu = new CtrlMenuOpciones(m, 1, nombre);
                                this.login.dispose();
                                Preparar();
                                menu.iniciarMenu();
                            }
                            break;
                            case 2: {
                                this.menu = new CtrlMenuOpciones(m, 2, nombre);
                                this.login.dispose();
                                Preparar();
                                menu.iniciarMenu();
                            }
                            break;
                            case 0:
                                JOptionPane.showMessageDialog(null, "Nombre o contraseña invalida");
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Complete los campos usuario y password");
                    }
                }
                break;
            }*/
            
            //metodo sin validacion de metodo de prueba osea licencia de por vida ..
            if (!nombre.equals("") && !pass.equals("")) {
                modelLogin.setNombreUsuario(nombre);
                modelLogin.setPassUsuario(pass);
                switch (modelLogin.ValidacionAdmin()) {
                    case 1: {
                        this.menu = new CtrlMenuOpciones(m, 1, nombre);
                        this.login.dispose();
                        Preparar();
                        //menu.iniciarMenu();
                        
                    }
                    break;
                    case 2: {
                        this.menu = new CtrlMenuOpciones(m, 2, nombre);
                        this.login.dispose();
                        Preparar();
                        //menu.iniciarMenu();
                    }
                    break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Nombre o contraseña invalida");
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Complete los campos usuario y password");
            }
        }
        if(login.btnSetPrecioDolar == e.getSource()){
            m.txtPrecioDolarVenta.setText(this.login.jsPrecioVentaDolar.getValue().toString());
            m.txtPrecioDolarCompra.setText(this.login.jsPrecioCompraDolar.getValue().toString());
            menu.iniciarMenu();
            this.login.jdPrecioDolar.setVisible(false);
        }
    }

    //metodo para la licencia de prueba de 20 dias
    public String ValidarLicencia() {
        String response = "";
        long f1 = modelLogin.getFechaIntalacion(), f2 = this.fecha.getTime();
        //resta de la fecha de instalacion - fecha final me da los dias trancurridos de uso 
        int dias = (int) ((f2 - f1) / 86400000);
        //si los dias de uso son menor de 20 se puede seguir usando
        if (dias < 20) {
            response = "valido";
        } else if (dias >= 20) {//si los dias de uso son mayor o igual a los 20 dias se negara la entrada al sistema
            response = "noValido";
        }
        return response;
    }

    public void Espera() {
        this.login.espera.setVisible(true);
        this.login.espera.setLocationRelativeTo(null);
        this.login.espera.setSize(330, 73);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.VK_ENTER == e.getKeyCode()) {
            String nombre = login.txtNombreUsuario.getText(), pass = login.txtPassUsuario.getText();
            //switch para metodo de prueba de 20 dias
            /*switch (ValidarLicencia()) {
                case "noValido": {
                    JOptionPane.showMessageDialog(null, "Su licencia de prueba finalizó..! \n para adquirir su licencia llama al : +505 85361324");
                }
                break;
                case "valido": {
                    if (!nombre.equals("") && !pass.equals("")) {
                        modelLogin.setNombreUsuario(nombre);
                        modelLogin.setPassUsuario(pass);
                        switch (modelLogin.ValidacionAdmin()) {
                            case 1: {
                                this.menu = new CtrlMenuOpciones(m, 1, nombre);
                                this.login.dispose();
                                Preparar();
                                menu.iniciarMenu();
                            }
                            break;
                            case 2: {
                                this.menu = new CtrlMenuOpciones(m, 2, nombre);
                                this.login.dispose();
                                Preparar();
                                menu.iniciarMenu();
                            }
                            break;
                            case 0:
                                JOptionPane.showMessageDialog(null, "Nombre o contraseña invalida");
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Complete los campos usuario y password");
                    }
                }
                break;
            }*/
            //
            
            if (!nombre.equals("") && !pass.equals("")) {
                modelLogin.setNombreUsuario(nombre);
                modelLogin.setPassUsuario(pass);
                switch (modelLogin.ValidacionAdmin()) {
                    case 1: {
                        this.menu = new CtrlMenuOpciones(m, 1, nombre);
                        this.login.dispose();
                        Preparar();
                        //menu.iniciarMenu();
                    }
                    break;
                    case 2: {
                        this.menu = new CtrlMenuOpciones(m, 2, nombre);
                        this.login.dispose();
                        Preparar();
                        //menu.iniciarMenu();
                    }
                    break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Nombre o contraseña invalida");
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Complete los campos usuario y password");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == login.txtNombreUsuario) {
            String Valor = login.txtNombreUsuario.getText();
            if (Valor.equals("Usuario")) {
                login.txtNombreUsuario.setText("");
            } else {

            }

        }
        if (e.getSource() == login.txtPassUsuario) {
            String valor = login.txtPassUsuario.getText();
            if (valor.equals("cdsoft")) {
                login.txtPassUsuario.setText("");
            } else {

            }

        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == login.txtNombreUsuario) {
            String valor = login.txtNombreUsuario.getText();
            if (valor.equals("") || valor.equals("Usuario")) {
                login.txtNombreUsuario.setText("Usuario");
            } else {

            }
        }
        if (e.getSource() == login.txtPassUsuario) {
            String valor = login.txtPassUsuario.getText();
            if (valor.equals("") || valor.equals("cdsoft")) {
                login.txtPassUsuario.setText("cdsoft");
            } else {

            }
        }
    }
}
