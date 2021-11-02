/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CDstore;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
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
    CDstore(){
        
    }
    public static void main(String[] args) {
            FlatIntelliJLaf.install();
            ILogin login = new ILogin();
            Login modelLogin = new Login();
            CtrlLogin ctrl = new CtrlLogin(login, modelLogin);
            ctrl.iniciar();
            try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
            } catch (Exception e) {
        }
    }
}
