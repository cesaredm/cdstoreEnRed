package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.ILogin;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlMenuOpciones implements MouseListener, ActionListener, WindowListener {
    int permiso;
    private String usuario;
    Date fecha;
    IMenu menu;
    DefaultTableModel modelo;
    Respaldo backup;
    Productos p;
    Clientes c;
    Creditos cred;
    Facturacion factura;
    Reportes reportes;
    Usuarios usuarios;
    Transacciones gastos;
    RegistroMonedas registroMonedas;
    PagosCreditos pagos;
    SocketServer socketServer;
    CtrlClientes ctrlClient;
    CtrlProducto ctrlP;
    CtrlCreditos ctrlCred;
    CtrlFacturacion ctrlFact;
    CtrlReportes ctrlRepo;
    CtrlUsuarios ctrlUsua;
    CtrlTransacciones ctrlGastos;
    CtrlPagos ctrlPagos;
    CtrlImprimirReport print;
    CtrlConfiguraciones CTRLinfo;
    Configuraciones info;
    CtrlDevoluciones devoluciones;
    CtrlGenCodBarra codBarra;
    CtrlRegistroMonedas ctrlRegistroMonedas;
    JSpinner spiner;
    SpinnerNumberModel sModel;
    
    public CtrlMenuOpciones(IMenu menu,int permiso, String usuario) {
        this.usuario = usuario;
        this.permiso = permiso;
        this.fecha = new Date();
        this.menu = menu;
        this.modelo = new DefaultTableModel();
        this.menu.btnInventario.addMouseListener(this);
        this.menu.btnClientes.addMouseListener(this);
        this.menu.btnVentas.addMouseListener(this);
        this.menu.btnReportes.addMouseListener(this);
        this.menu.btnUsuarios.addMouseListener(this);
        this.menu.btnTransacciones.addMouseListener(this);
        this.menu.btnNotificaciones.addMouseListener(this);
        this.menu.btnCerrarSesion.addMouseListener(this);
        this.menu.btnVerificarVencimientos.addActionListener(this);
        this.menu.btnInfoFactura.addMouseListener(this);
        this.menu.addWindowListener(this);
        this.backup = new Respaldo(menu);
        this.p = new Productos();
        this.c = new Clientes();
        this.cred = new Creditos();
        this.factura = new Facturacion();
        this.reportes = new Reportes();
        this.usuarios = new Usuarios();
        this.gastos = new Transacciones();
        this.pagos = new PagosCreditos();
        this.info = new Configuraciones();
        this.registroMonedas = new RegistroMonedas();
        this.CTRLinfo = new CtrlConfiguraciones(menu, info);
        ctrlClient = new CtrlClientes(menu, c);
        ctrlP = new CtrlProducto(p, menu, permiso);
        Notificacion();
        ctrlCred = new CtrlCreditos(menu, cred);
        ctrlFact = new CtrlFacturacion(menu, factura, permiso);
        ctrlRepo = new CtrlReportes(menu, reportes);
        ctrlUsua = new CtrlUsuarios(menu, usuarios);
        ctrlGastos = new CtrlTransacciones(menu, gastos);
        ctrlPagos = new CtrlPagos(menu, pagos);
//        this.ctrlRegistroMonedas = new CtrlRegistroMonedas(menu, registroMonedas);
        this.print = new CtrlImprimirReport(menu, info);
        this.devoluciones = new CtrlDevoluciones(menu, reportes);
        this.codBarra = new CtrlGenCodBarra(menu);
	this.socketServer = new SocketServer();
	this.socketServer.setfacturacionController(ctrlFact);
    }

    public void iniciarMenu() {
        this.menu.setVisible(true);
        this.menu.setLocationRelativeTo(null);
        this.menu.lblUsuarioSistema.setText(this.usuario);
    }     

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnInventario) {
            //menu.btnInventario.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Inventario");

//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));
            
            if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(true);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.btnGuardarProducto.setEnabled(false);
                menu.btnActualizarProducto.setEnabled(false);
                menu.btnNuevoProducto.setEnabled(false);
                menu.EditarProducto.setEnabled(false);
//                menu.lblTituloDeVentanas.setText("");
                menu.pnlBlanco.setVisible(false);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(true);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlInfoFactura.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnClientes) {
//            menu.btnClientes.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Clientes");
	    menu.lblNotificacionClientes.setVisible(false);

//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));

             if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(true);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
//                menu.lblTituloDeVentanas.setText("");
                menu.pnlBlanco.setVisible(false);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(true);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlInfoFactura.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnVentas) {
//            menu.btnVentas.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Facturación");

//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));
            
             if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(true);
                menu.txtCodBarraFactura.requestFocus();
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlBlanco.setVisible(false);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(true);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlInfoFactura.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnReportes) {
//            menu.btnReportes.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Reportes");

//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 249, 252));
//
//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));
            
            if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(true);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                //ocultar proyeccionVentas
                menu.pnlInversion.setVisible(false);
//                menu.lblTituloDeVentanas.setText("");
                menu.pnlBlanco.setVisible(false);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(true);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlInfoFactura.setVisible(false);
                
            }
        }
        if (e.getSource() == menu.btnUsuarios) {
//            menu.btnUsuarios.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Gestion de Usuarios");

//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));
            
            if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
//                menu.lblTituloDeVentanas.setText("");
                menu.pnlBlanco.setVisible(true);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(true);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlInfoFactura.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnTransacciones) {
//            menu.btnTransacciones.setBackground(new java.awt.Color(60,60,60));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Compras y Otros Gastos");

//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));

            if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(true);
//                menu.lblTituloDeVentanas.setText("");
                menu.pnlBlanco.setVisible(false);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(true);
                menu.pnlInfoFactura.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnNotificaciones) {
//            menu.btnNotificaciones.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Notificaciones");
            menu.lblNumeroNotificaciones.setVisible(false);

//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 249, 252));
//
//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 249, 252));
//
//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
//            
//            menu.btnInfoFactura.setBackground(new java.awt.Color(72,72,72));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,255,255));
            
            if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(true);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlBlanco.setVisible(false);
            }else if(this.permiso == 1)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(true);
                menu.pnlTransacciones.setVisible(false);
                menu.pnlInfoFactura.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnInfoFactura) {
//            menu.btnInfoFactura.setBackground(new java.awt.Color(60,60,60));
            menu.lblEditarInfoFactura.setForeground(new java.awt.Color(255,215,0));
//            menu.lblTituloDeVentanas.setText("Editar Informacion de Factura");

//            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255,255,255));
//            
//            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnCerrarSesion.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));
//
//            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

             if(this.permiso == 2)
            {
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
//                menu.lblTituloDeVentanas.setText("");
                menu.pnlBlanco.setVisible(true);
            }else if(this.permiso == 1)
            {
                menu.pnlInfoFactura.setVisible(true);
                menu.pnlClientes.setVisible(false);
                menu.pnlVentas.setVisible(false);
                menu.pnlReportes.setVisible(false);
                menu.pnlInventario.setVisible(false);
                menu.pnlUsuarios.setVisible(false);
                menu.pnlNotificaciones.setVisible(false);
                menu.pnlTransacciones.setVisible(false);
            }
        }
        if (e.getSource() == menu.btnCerrarSesion) {
            menu.btnCerrarSesion.setBackground(new java.awt.Color(60,60,60));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(0, 222, 171));

            menu.btnReportes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnTransacciones.setBackground(new java.awt.Color(72,72,72));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));
            
	    
            //crear nuevo Objeto de Interfaz de login
            ILogin login = new ILogin();
            //cerrar el sistema
            menu.dispose();
            //crear nuevo objeto de el modelo login
            Login l = new Login();
            //crear nuevo objeto de controlador login
            CtrlLogin ReinicioSesion = new CtrlLogin(login, l);
            devolverProductoStock();
            //inicira con la funcio iniciar de la clase CtrlLogin
            ReinicioSesion.iniciar();
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    public void Notificacion() {
        int cont = 0;
        menu.lblNumeroNotificaciones.setVisible(false);
        ArrayList lista = new ArrayList();//instancia de un nuevo array list
        DefaultListModel modeloLista = new DefaultListModel();//modelo para el Jlist
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//formateador de fecha
        Date fechaFinal, fechaInicio = this.fecha;//fecha de venvimiento de producto y fecha actual del sistema
        this.modelo = (DefaultTableModel) menu.tblProductos.getModel();
        int filas = this.modelo.getRowCount();//cuenta las filas de la tabla productos
        for (int i = 0; i < filas; i++)//recorro la tabla productos
        {
            try {
                fechaFinal = sdf.parse(this.modelo.getValueAt(i, 9).toString());//obtengo la fecha del producto de la tabla producto lo paso a formato Date
                int dias = (int) ((fechaFinal.getTime() - fechaInicio.getTime()) / 86400000);//calculo de diferencias de dias "conversion de milesegundos a dias"
                //System.out.println(fechaFinal +" - "+fecha+" = "+ dias);
                String nombre = (String) this.modelo.getValueAt(i, 2);//obtengo el nombre del producto de la tabla producto
                if (dias <= 60 && dias > 0)//
                {   
                    cont++;//contador de productos que venceran dentro de 60 dias 
                    menu.lblNumeroNotificaciones.setVisible(true);//hcaer visible el badge de numero de mensajes
                    //menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 7, 5, 255));//cambio de color si hay productos cerca de vencer
                    lista.add("Quedan " + dias + " días para que el producto " + nombre + " expire");//agrego un elemeto a lista
                    modeloLista.removeAllElements();//limpio el JList para que no repita los datos a mostrar
                    for (int l = 0; l < lista.size(); l++)//recorro la lista para ingresarla al modelo de Jlist
                    {
                        modeloLista.addElement(lista.get(l)); //add elemento a modeloLista   
                    }

                }
                menu.lblNumeroNotificaciones.setText(""+cont);//lleno el label que esta sirviendo de badge 
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err+"notificacines");
            }

        }
        menu.jlListaNotificaciones.setModel(modeloLista);//establesco el modeloLista  Jlist para mostrar las notificaciones
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnVerificarVencimientos) {
            Notificacion();
            //this.ctrlRepo.proyeccionVentas();
        }
    }
    //devolver productos que estan dentro de la tabla factura cuando se presiona el boton de cerrar sesion
    public void devolverProductoStock()
    {
        int filas = menu.tblFactura.getRowCount();
        String cantidad,id;
        for(int i =0;i<filas;i++)
        {
            id = (String) menu.tblFactura.getValueAt(i, 0);
            cantidad = (String) menu.tblFactura.getValueAt(i, 2);
            this.p.AgregarProductoStock(id, cantidad);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        devolverProductoStock();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
       
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

}
