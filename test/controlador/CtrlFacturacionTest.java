/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.CaretEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlFacturacionTest {
    
    public CtrlFacturacionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of actionPerformed method, of class CtrlFacturacion.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent e = null;
        CtrlFacturacion instance = null;
        instance.actionPerformed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of caretUpdate method, of class CtrlFacturacion.
     */
    @Test
    public void testCaretUpdate() {
        System.out.println("caretUpdate");
        CaretEvent e = null;
        CtrlFacturacion instance = null;
        instance.caretUpdate(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseClicked method, of class CtrlFacturacion.
     */
    @Test
    public void testMouseClicked() {
        System.out.println("mouseClicked");
        MouseEvent e = null;
        CtrlFacturacion instance = null;
        instance.mouseClicked(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mousePressed method, of class CtrlFacturacion.
     */
    @Test
    public void testMousePressed() {
        System.out.println("mousePressed");
        MouseEvent e = null;
        CtrlFacturacion instance = null;
        instance.mousePressed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseReleased method, of class CtrlFacturacion.
     */
    @Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent e = null;
        CtrlFacturacion instance = null;
        instance.mouseReleased(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseEntered method, of class CtrlFacturacion.
     */
    @Test
    public void testMouseEntered() {
        System.out.println("mouseEntered");
        MouseEvent e = null;
        CtrlFacturacion instance = null;
        instance.mouseEntered(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseExited method, of class CtrlFacturacion.
     */
    @Test
    public void testMouseExited() {
        System.out.println("mouseExited");
        MouseEvent e = null;
        CtrlFacturacion instance = null;
        instance.mouseExited(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of keyTyped method, of class CtrlFacturacion.
     */
    @Test
    public void testKeyTyped() {
        System.out.println("keyTyped");
        KeyEvent e = null;
        CtrlFacturacion instance = null;
        instance.keyTyped(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of keyPressed method, of class CtrlFacturacion.
     */
    @Test
    public void testKeyPressed() {
        System.out.println("keyPressed");
        KeyEvent e = null;
        CtrlFacturacion instance = null;
        instance.keyPressed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of keyReleased method, of class CtrlFacturacion.
     */
    @Test
    public void testKeyReleased() {
        System.out.println("keyReleased");
        KeyEvent e = null;
        CtrlFacturacion instance = null;
        instance.keyReleased(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of guardarFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testGuardarFactura() {
        System.out.println("guardarFactura");
        CtrlFacturacion instance = null;
        instance.guardarFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarVentanaProductos method, of class CtrlFacturacion.
     */
    @Test
    public void testMostrarVentanaProductos() {
        System.out.println("mostrarVentanaProductos");
        CtrlFacturacion instance = null;
        instance.mostrarVentanaProductos();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of AgregarProductoFacturaEnter method, of class CtrlFacturacion.
     */
    @Test
    public void testAgregarProductoFacturaEnter() {
        System.out.println("AgregarProductoFacturaEnter");
        CtrlFacturacion instance = null;
        instance.AgregarProductoFacturaEnter();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Retornar method, of class CtrlFacturacion.
     */
    @Test
    public void testRetornar() {
        System.out.println("Retornar");
        CtrlFacturacion instance = null;
        instance.Retornar();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Imprimir method, of class CtrlFacturacion.
     */
    @Test
    public void testImprimir() {
        System.out.println("Imprimir");
        String Nfactura = "";
        String comprador = "";
        String cliente = "";
        String tipoVenta = "";
        String formaPago = "";
        String[] Datos = null;
        String subtotal = "";
        String isv = "";
        String total = "";
        String fecha = "";
        String recibido = "";
        String cambio = "";
        CtrlFacturacion instance = null;
        instance.Imprimir(Nfactura, comprador, cliente, tipoVenta, formaPago, Datos, subtotal, isv, total, fecha, recibido, cambio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editarISV method, of class CtrlFacturacion.
     */
    @Test
    public void testEditarISV() {
        System.out.println("editarISV");
        String isv = "";
        CtrlFacturacion instance = null;
        instance.editarISV(isv);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarTablaFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testLimpiarTablaFactura() {
        System.out.println("LimpiarTablaFactura");
        CtrlFacturacion instance = null;
        instance.LimpiarTablaFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of EstiloTablaFacturacion method, of class CtrlFacturacion.
     */
    @Test
    public void testEstiloTablaFacturacion() {
        System.out.println("EstiloTablaFacturacion");
        CtrlFacturacion instance = null;
        instance.EstiloTablaFacturacion();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of DeshabilitarBtnGuardarFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testDeshabilitarBtnGuardarFactura() {
        System.out.println("DeshabilitarBtnGuardarFactura");
        CtrlFacturacion instance = null;
        instance.DeshabilitarBtnGuardarFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProductoFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testAddProductoFactura() {
        System.out.println("addProductoFactura");
        CtrlFacturacion instance = null;
        instance.addProductoFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCreditoFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testAddCreditoFactura() {
        System.out.println("addCreditoFactura");
        CtrlFacturacion instance = null;
        instance.addCreditoFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarVentanaCreditos method, of class CtrlFacturacion.
     */
    @Test
    public void testMostrarVentanaCreditos() {
        System.out.println("mostrarVentanaCreditos");
        CtrlFacturacion instance = null;
        instance.mostrarVentanaCreditos();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMasProducto method, of class CtrlFacturacion.
     */
    @Test
    public void testAddMasProducto() {
        System.out.println("addMasProducto");
        CtrlFacturacion instance = null;
        instance.addMasProducto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDescuento method, of class CtrlFacturacion.
     */
    @Test
    public void testAddDescuento() {
        System.out.println("addDescuento");
        CtrlFacturacion instance = null;
        instance.addDescuento();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDescuentoDirecto method, of class CtrlFacturacion.
     */
    @Test
    public void testAddDescuentoDirecto() {
        System.out.println("addDescuentoDirecto");
        CtrlFacturacion instance = null;
        instance.addDescuentoDirecto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDescuentoPorcentaje method, of class CtrlFacturacion.
     */
    @Test
    public void testAddDescuentoPorcentaje() {
        System.out.println("addDescuentoPorcentaje");
        CtrlFacturacion instance = null;
        instance.addDescuentoPorcentaje();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of agregarProductoBoton method, of class CtrlFacturacion.
     */
    @Test
    public void testAgregarProductoBoton() {
        System.out.println("agregarProductoBoton");
        CtrlFacturacion instance = null;
        instance.agregarProductoBoton();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizarFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testActualizarFactura() {
        System.out.println("actualizarFactura");
        CtrlFacturacion instance = null;
        instance.actualizarFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editarFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testEditarFactura() {
        System.out.println("editarFactura");
        CtrlFacturacion instance = null;
        instance.editarFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarFilaFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testEliminarFilaFactura() {
        System.out.println("eliminarFilaFactura");
        CtrlFacturacion instance = null;
        instance.eliminarFilaFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nuevaFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testNuevaFactura() {
        System.out.println("nuevaFactura");
        CtrlFacturacion instance = null;
        instance.nuevaFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizarIVA method, of class CtrlFacturacion.
     */
    @Test
    public void testActualizarIVA() {
        System.out.println("actualizarIVA");
        CtrlFacturacion instance = null;
        instance.actualizarIVA();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarFormularioClienteFactura method, of class CtrlFacturacion.
     */
    @Test
    public void testLimpiarFormularioClienteFactura() {
        System.out.println("limpiarFormularioClienteFactura");
        CtrlFacturacion instance = null;
        instance.limpiarFormularioClienteFactura();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validarPermiso method, of class CtrlFacturacion.
     */
    @Test
    public void testValidarPermiso() {
        System.out.println("validarPermiso");
        CtrlFacturacion instance = null;
        instance.validarPermiso();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
