/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CambioEstadoCreditoFactura implements EstadoCreditos {

	@Override
	public void updateApendiente(int idCredito) {
		float pagos = 0, credito = 0, saldo = 0;
		if (idCredito > 0) {
			pagos = this.pagos.pagosCredito(idCredito);
			credito = creditos.TotalCreditoCliente(idCredito);
			saldo = credito - pagos;
			if (saldo > 0.0) {
				creditos.ActualizarEstadoCredito(idCredito, "Pendiente");
			}
		} else {

		}
	}

	@Override
	public void updateAabierto(int idCredito) {
		//variable para almacenar total de credito de cliete
		float credito = creditos.creditoPorCliente(idCredito);
		//condicion para saber si el saldo esta en 0.0 o menor de 0.0
		if (credito == 0.0 || credito < 0) {
			creditos.ActualizarEstadoCredito(idCredito, "Abierto");
		}
	}

	@Override
	public float limiteCredito(String idCredito) {
		return creditos.limiteCredito(idCredito);
	}

	@Override
	public float saldoCredito(int idCredito) {
		return creditos.creditoPorCliente(idCredito);

	}

}
