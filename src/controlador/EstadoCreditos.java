/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Creditos;
import modelo.PagosCreditos;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public interface EstadoCreditos {
	IMenu menu = new IMenu();
	Creditos creditos = new Creditos();

	public void updateApendiente(int idCredito);

	public void updateAabierto(int idCredito);

	public float limiteCredito(String idCredito);

	public float saldoCredito(int idCredito);
}
