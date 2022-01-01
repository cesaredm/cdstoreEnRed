/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.image.Bitonal;
import com.github.anastaciocintra.escpos.image.BitonalThreshold;
import com.github.anastaciocintra.escpos.image.CoffeeImageImpl;
import com.github.anastaciocintra.escpos.image.EscPosImage;
import com.github.anastaciocintra.escpos.image.RasterBitImageWrapper;
import com.github.anastaciocintra.output.PrinterOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import samplesCommon.SamplesCommon;

/**nit
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlImprimir {

    PrintService printService;
    PrinterOutputStream printerOutputStream;
    EscPos escpos;
    Style boldCenter,bold,centrar;
    Bitonal algorithm;
    // creating the EscPosImage, need buffered image and algorithm.
    BufferedImage githubBufferedImage;
    EscPosImage escposImage;
    // this wrapper uses esc/pos sequence: "GS 'v' '0'"
    RasterBitImageWrapper imageWrapper;

    public CtrlImprimir() {
        try {
            this.printService = PrinterOutputStream.getPrintServiceByName("EPSON TM-T20III Receipt");
            this.printerOutputStream = new PrinterOutputStream(printService);
            this.escpos = new EscPos(printerOutputStream);
            this.boldCenter = new Style(escpos.getStyle())
                    .setBold(true).setJustification(EscPosConst.Justification.Center);
            //estilo negrita sin centrar
            this.bold = new Style(escpos.getStyle()).setBold(true);
            //centrar texto
            this.centrar = new Style().setJustification(EscPosConst.Justification.Center);
            this.algorithm = new BitonalThreshold(127);
            this.githubBufferedImage = SamplesCommon.getImage(SamplesCommon.sampleImages.bless);
            this.escposImage = new EscPosImage(new CoffeeImageImpl(githubBufferedImage), algorithm);
            this.imageWrapper = new RasterBitImageWrapper();
            this.imageWrapper.setJustification(EscPosConst.Justification.Center);
        } catch (IOException ex) {
            Logger.getLogger(CtrlImprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reiniciar(){
        try {
            this.printService = PrinterOutputStream.getPrintServiceByName("EPSON TM-T20III Receipt");
            this.printerOutputStream = new PrinterOutputStream(printService);
            this.escpos = new EscPos(this.printerOutputStream);
        } catch (IOException ex) {
            Logger.getLogger(CtrlImprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            this.escpos.close();
        } catch (IOException ex) {
            Logger.getLogger(CtrlImprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
