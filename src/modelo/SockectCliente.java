/* * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class SockectCliente {

	final int PORT = 3000;
	Socket cliente;
	private String[] ips;
	ObjectOutputStream output;

	public SockectCliente() {

	}

	public void setIps(String[] ips) {
		this.ips = ips;
	}

	public void socketInit(Object objeto) {
		try {
			for (String ip : ips) {

				this.cliente = new Socket(ip, PORT);
				this.output = new ObjectOutputStream(this.cliente.getOutputStream());
				this.output.writeObject(objeto);

			}
			this.cliente.close();
			this.output.close();
		} catch (IOException ex) {
			Logger.getLogger(SockectCliente.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
