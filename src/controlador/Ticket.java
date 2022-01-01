/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/*
 * Ticket.java

 * 
 */
import com.github.anastaciocintra.escpos.EscPos;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Ticket extends CtrlImprimir {

	String nameLocal,
		direccion,
		telefono,
		RFC,
		Rango,
		box,
		ticket,
		caissier,
		comprador,
		cliente,
		tipoVenta,
		formaPago,
		dateTime,
		subTotal,
		iva,
		totalCordobas,
		totalDolares,
		recibo,
		change,
		message;
	String[] items;
	//Ticket attribute content

	private String contentTicket
		= ""
		+ (char) 27 + (char) 112 + (char) 0 + (char) 10 + (char) 100
		+ "{{nameLocal}}\n"
		+ "{{direccion}}\n"
		+ "Rut : {{rut}}\n"
		+ "========================================== \n"
		+ "Tel:{{telefono}}\n"
		+ "Caja # {{box}} - Factura # {{ticket}}\n"
		+ "Atendió: {{Cajero}} \n"
		+ "Cliente: {{cliente}} {{comprador}}\n"
		+ "Venta:   {{tipoVenta}} \n"
		+ "Pago:    {{formaPago}}\n"
		+ "Fecha:   {{dateTime}} \n"
		+ "DESCRIP    CANT. PRECIO IMPORTE\n"
		+ "========================================== \n"
		+ "{{items}} \n"
		+ "========================================== \n"
		+ "TOTAL C$:{{totalCordobas}} \n"
		+ "TOTAL $ :{{totalDolares}} \n"
		+ "RECIBIDO: {{recibo}}\n"
		+ "CAMBIO: {{change}}\n"
		+ "========================================== \n"
		+ "{{message}}                            \n"
		+ "\n"
		+ "\n";

	//El constructor que setea los valores a la instancia
	Ticket() {

	}

	public void llenarTicket() {
		StringBuffer a = new StringBuffer("");
		this.contentTicket = this.contentTicket.replace("{{nameLocal}}", this.nameLocal);
		this.contentTicket = this.contentTicket.replace("{{direccion}}", this.direccion);
		this.contentTicket = this.contentTicket.replace("{{telefono}}", this.telefono);
		this.contentTicket = this.contentTicket.replace("{{rut}}", RFC);
		//this.contentTicket = this.contentTicket.replace("{{Rango}}", Rango);
		this.contentTicket = this.contentTicket.replace("{{box}}", this.box);
		this.contentTicket = this.contentTicket.replace("{{ticket}}", this.ticket);
		this.contentTicket = this.contentTicket.replace("{{Cajero}}", this.caissier);
		this.contentTicket = this.contentTicket.replace("{{comprador}}", this.comprador);
		this.contentTicket = this.contentTicket.replace("{{cliente}}", this.cliente);
		this.contentTicket = this.contentTicket.replace("{{tipoVenta}}", this.tipoVenta);
		this.contentTicket = this.contentTicket.replace("{{formaPago}}", this.formaPago);
		this.contentTicket = this.contentTicket.replace("{{dateTime}}", this.dateTime);
		this.contentTicket = this.contentTicket.replace("{{message}}", this.message);
		//this.contentTicket = this.contentTicket.replace("{{subTotal}}", subTotal);
		//this.contentTicket = this.contentTicket.replace("{{iva}}", iva);
		this.contentTicket = this.contentTicket.replace("{{totalCordobas}}", this.totalCordobas);
		this.contentTicket = this.contentTicket.replace("{{totalDolares}}", this.totalDolares);
		this.contentTicket = this.contentTicket.replace("{{recibo}}", this.recibo);
		this.contentTicket = this.contentTicket.replace("{{change}}", this.change);
		//recorro el array de Productos ITEMS
		for (int i = 0; i < this.items.length; i++) {
			a.append(this.items[i]);
		}
		this.contentTicket = this.contentTicket.replace("{{items}}", a);
	}

	public void printFactura() {
		try {
			escpos.write(imageWrapper, escposImage).feed(1);
			escpos.writeLF(contentTicket)
				.feed(2).cut(EscPos.CutMode.FULL);
			escpos.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex + "en metodo printInfo en controlador Ticket");
		}

	}
}
