/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CDstore;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMonokaiProIJTheme;
import modelo.*;
import controlador.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vista.ILogin;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CDstore {

	CDstore() {

	}

	public static void main(String[] args) {
		FlatMonokaiProIJTheme.install();
		UIManager.put("Button.arc", 999);
		UIManager.put("Component.arc", 999);
		UIManager.put("ProgressBar.arc", 999);
		UIManager.put("TextComponent.arc", 999);
		ILogin login = new ILogin();
		Login modelLogin = new Login();
		CtrlLogin ctrl = new CtrlLogin(login, modelLogin);
		ctrl.iniciar();
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			//UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
			UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMonokaiProIJTheme");
		} catch (Exception e) {
		}
	}
}
