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

public class Ticket extends CtrlImprimir{
    //Ticket attribute content

    private String contentTicket =
            ""
            +(char)27 + (char)112 + (char)0 + (char)10 + (char)100
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
            + "TOTAL:{{total}} \n"
            + "RECIBIDO: {{recibo}}\n"
            + "CAMBIO: {{change}}\n"
//            + "========================================== \n"
//            + "Un lector vive mil vidas antes\n"
//            + "de morir aquel que nunca lee vive\n"
//            + "solo una.\n"
            + "\n"
            + "\n";

    //El constructor que setea los valores a la instancia
    Ticket(String nameLocal, String direccion, String telefono, String RFC, String Rango, String box, String ticket, String caissier, String comprador, String cliente, String tipoVenta, String formaPago, String dateTime, String[] items, String subTotal, String iva, String total, String recibo, String change) {
        StringBuffer a = new StringBuffer("");
        this.contentTicket = this.contentTicket.replace("{{nameLocal}}", nameLocal);
        this.contentTicket = this.contentTicket.replace("{{direccion}}", direccion);
        this.contentTicket = this.contentTicket.replace("{{telefono}}", telefono);
        this.contentTicket = this.contentTicket.replace("{{rut}}", RFC);
        //this.contentTicket = this.contentTicket.replace("{{Rango}}", Rango);
        this.contentTicket = this.contentTicket.replace("{{box}}", box);
        this.contentTicket = this.contentTicket.replace("{{ticket}}", ticket);
        this.contentTicket = this.contentTicket.replace("{{Cajero}}", caissier);
        this.contentTicket = this.contentTicket.replace("{{comprador}}", comprador);
        this.contentTicket = this.contentTicket.replace("{{cliente}}", cliente);
        this.contentTicket = this.contentTicket.replace("{{tipoVenta}}", tipoVenta);
        this.contentTicket = this.contentTicket.replace("{{formaPago}}", formaPago);
        this.contentTicket = this.contentTicket.replace("{{dateTime}}", dateTime);
        //recorro el array de Productos ITEMS
        for (int i = 0; i < items.length; i++) {
            a.append(items[i]);
        }
        this.contentTicket = this.contentTicket.replace("{{items}}", a);
        //this.contentTicket = this.contentTicket.replace("{{subTotal}}", subTotal);
        //this.contentTicket = this.contentTicket.replace("{{iva}}", iva);
        this.contentTicket = this.contentTicket.replace("{{total}}", total);
        this.contentTicket = this.contentTicket.replace("{{recibo}}", recibo);
        this.contentTicket = this.contentTicket.replace("{{change}}", change);
        
        System.out.println(this.contentTicket);
    }

    public void printInfo() {
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
