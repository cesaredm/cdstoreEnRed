/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CambioEstadoCreditoPagos implements EstadoCreditos {
	float saldoCordobas,
		saldoDolares;
	@Override
	public void updateApendiente(int idCredito) {
		if (idCredito > 0) {
			creditos.pagosPorCedito(idCredito);
			creditos.saldoInicialCredito(idCredito);
			creditos.saldoPorCredito(idCredito);
			saldoCordobas = creditos.getSaldoCordobas() - creditos.getPagosCordobas();
			saldoDolares = creditos.getSaldoDolares() - creditos.getPagosDolar();
			if (saldoCordobas > 0.0 || saldoDolares > 0.0) {
				creditos.ActualizarEstadoCredito(idCredito, "Pendiente");
			}
		} else {

		}
	}

	@Override
	public void updateAabierto(int idCredito) {
		//variable para almacenar total de credito de cliete
		creditos.pagosPorCedito(idCredito);
		creditos.saldoInicialCredito(idCredito);
		creditos.saldoPorCredito(idCredito);
		saldoCordobas = creditos.getSaldoCordobas() - creditos.getPagosCordobas();
		saldoDolares = creditos.getSaldoDolares()- creditos.getPagosDolar();
		if (saldoCordobas <= 0 && saldoDolares <= 0) {
			creditos.ActualizarEstadoCredito(idCredito, "Abierto");
		}
	}

	@Override
	public float limiteCredito(String idCredito) {
		return 0;
	}

	@Override
	public float saldoCredito(int idCredito) {
		return 0;
	}

}
