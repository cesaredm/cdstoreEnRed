/*
	* codigo sin terminar, nesecita revision y test y agregar al escript de la tabla cierres
	* 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import modelo.CierresModel;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CierresController implements ActionListener {

	private static CierresController instancia = null;
	IMenu menu;
	CierresModel cierresModel;

	private CierresController(IMenu menu, CierresModel cierreModel) {
		this.menu = menu;
		this.cierresModel = cierreModel;
		this.menu.btnCierreCaja.addActionListener(this);
		this.menu.btnGuardarCierre.addActionListener(this);
	}

	public static void createInstanciaController(IMenu menu, CierresModel cierreModel) {
		if (instancia == null) {
			instancia = new CierresController(menu, cierreModel);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.btnGuardarCierre) {
			RealizarCorte();
		}
		if (e.getSource() == this.menu.btnCierreCaja) {
			this.llenarCorteCaja();
			this.menu.CortesCaja.setSize(385, 330);
			this.menu.CortesCaja.setLocationRelativeTo(null);
			this.menu.CortesCaja.setVisible(true);
		}
	}

	public void llenarCorteCaja() {
		try {
			this.menu.jcFechaCierre.setDate(new Date());
			this.menu.txtCajaCierre.setText("1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void LimpiarCierre() {
		menu.txtDescripcionCierre.setText("");
		menu.jcFechaCierre.setDate(new Date());
		menu.txtCajaCierre.setText("");
		this.menu.jsCordobasCierres.setValue(0);
		this.menu.jsDolarCierre.setValue(0);
	}

	public void RealizarCorte() {
		try {
			System.out.println(this.menu.jsDolarCierre.getValue().toString());
			this.cierresModel.setFecha(new java.sql.Date(this.menu.jcFechaCierre.getDate().getTime()));
			this.cierresModel.setCaja(Integer.parseInt(this.menu.txtCajaCierre.getText()));
			this.cierresModel.setCordobas(Float.parseFloat(this.menu.jsCordobasCierres.getValue().toString()));
			this.cierresModel.setDolar(Float.parseFloat(this.menu.jsDolarCierre.getValue().toString()));
			this.cierresModel.setDescripcion(this.menu.txtDescripcionCierre.getText());
			this.cierresModel.setUser(this.menu.lblUsuarioSistema.getText());
			this.cierresModel.setTasaCompra(Float.parseFloat(this.menu.txtPrecioDolarCompra.getText()));
			this.cierresModel.setTasaVenta(Float.parseFloat(this.menu.txtPrecioDolarVenta.getText()));
			this.cierresModel.guardarCierres();
			LimpiarCierre();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
