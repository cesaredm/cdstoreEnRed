package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import controlador.CtrlFacturacion;
import controlador.CtrlPagos;
import java.io.EOFException;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class SocketServer implements Runnable {

	ServerSocket server;
	Socket cliente;
	private int port = 20000;
	ObjectInputStream entrada;
	Facturacion factura;
	PagosCreditos pagos;
	CtrlFacturacion facturacionController;
	CtrlPagos pagosContoller;
	static String objeto;
	Thread hiloServidor;
	

	public SocketServer() {
		this.factura = new Facturacion();
		this.pagos = new PagosCreditos();
		this.listenPort();
		this.hiloServidor = new Thread(this);
		this.hiloServidor.start();
	}

	public void setPagosController(CtrlPagos pagosController) {
		this.pagosContoller = pagosController;
	}

	public void setfacturacionController(CtrlFacturacion facturacionController) {
		this.facturacionController = facturacionController;
	}
	
	//establecer el puerto de escucha de peticiones del sevidor	
	public void listenPort() {
		try {
			this.server = new ServerSocket(port);
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//poner en escucha de peticiones al servidor
	public void listenServer() {
		try {
			this.cliente = this.server.accept();
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//crear el flujo de entrada
	public void createInputFlow() {
		try {
			this.entrada = new ObjectInputStream(this.cliente.getInputStream());
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//recibir la informacion del flujo de entrada
	public void getEntrada() {
		try {
			Object clase = this.entrada.readObject();
			SockectCliente cliente = new SockectCliente();
			String dato = ((Object) clase.getClass().getSimpleName()).toString();
			switch (dato) {
				case "Facturacion": {
					this.factura = (Facturacion) clase;
					this.factura.GuardarFactura();
					this.factura.guardarDetalle();
					this.facturacionController.updateNumberFactura(this.factura.ObtenerIdFactura());
					this.facturacionController.mostrarProductosVender("");
					cliente.socketInit(this.factura);
				}
				break;
				case "PagosCreditos": {
					this.pagos = (PagosCreditos) clase;
					this.pagos.Guardar();
					this.pagosContoller.UltimoPago();
					cliente.socketInit(clase);
					
				}
				break;
			}
		} catch (EOFException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//cerrar el flujo de entrada
	public void closeInputFlow() {
		try {
			this.entrada.close();
		} catch (IOException ex) {
			Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				this.listenServer();
				this.createInputFlow();
				this.getEntrada();
				this.closeInputFlow();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, " ERROR EN EL METODO RUN EN MODELO DE SOCKETSERVER" + e);
			e.printStackTrace();
		}

	}
}
