package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Respaldo implements ActionListener {

	IMenu menu;

	public Respaldo(IMenu menu) {
		this.menu = menu;
		this.menu.btnRespaldo.addActionListener(this);
	}

	public void backUp() {
		try {
			int processCompleto;
			Process p = null;
			/* SI EL EL ARCHIVO NO ESTA EN program Files(x86) ENTONCES BUSCARA EN Progrma Files */
			try {
				p = Runtime.getRuntime().exec("C:/Program Files (x86)/MariaDB 10.5/bin/mysqldump.exe -u root -p19199697tsoCD"
					+ " --single-transaction --skip-lock-tables -R cdstorered");
			} catch (Exception e) {
				p = Runtime.getRuntime().exec("C:/Program Files/MariaDB 10.5/bin/mysqldump.exe -u root -p19199697tsoCD"
					+ " --single-transaction --skip-lock-tables -R cdstorered");
			}
			InputStream is = p.getInputStream();
			FileOutputStream fos = new FileOutputStream("C:\\RESPALDOCDSTORE\\BackUp_cdstore.sql");

			byte[] buffer = new byte[1000];
			int leido = is.read(buffer);

			while (leido > 0) {
				fos.write(buffer, 0, leido);
				leido = is.read(buffer);
			}
			fos.close();
			processCompleto = p.waitFor();
			if (processCompleto == 0) {
				JOptionPane.showMessageDialog(null, "Respaldo realizado con exito");
			} else {
				JOptionPane.showMessageDialog(null, "Ocurrio un error al realizar el respaldo.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "Al intentar hacer el respaldo.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.menu.btnRespaldo == e.getSource()) {
			backUp();
		}
	}
}
