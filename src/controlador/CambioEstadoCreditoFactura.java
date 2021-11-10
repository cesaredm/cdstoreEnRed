package controlador;


/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CambioEstadoCreditoFactura implements EstadoCreditos {
	 float saldoCordobas,
		 saldoDolares;
	@Override
	public void updateApendiente(int idCredito) {
		if (idCredito > 0) {
			creditos.pagosPorCedito(idCredito);
			creditos.saldoPorCredito(idCredito);
			this.saldoCordobas = creditos.getSaldoCordobas() - creditos.getPagosCordobas();
			this.saldoDolares = creditos.getSaldoDolares() - creditos.getPagosDolar();
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
		creditos.saldoPorCredito(idCredito);
		this.saldoCordobas = creditos.getSaldoCordobas() - creditos.getPagosCordobas();
		this.saldoDolares = creditos.getSaldoDolares() - creditos.getPagosDolar();
		if (saldoCordobas <= 0 && saldoDolares <= 0) {
			creditos.ActualizarEstadoCredito(idCredito, "Abierto");
		}
	}

	@Override
	public float limiteCredito(String idCredito) {
		return creditos.limiteCredito(idCredito);
	}

	@Override
	public float saldoCredito(int idCredito) {
		float precioDolarVenta = (this.menu.txtPrecioDolarVenta.getText().equals(""))
			? 1 : Float.parseFloat(this.menu.txtPrecioDolarVenta.getText());
		float saldo = (this.saldoDolares * precioDolarVenta) + saldoCordobas;
		return  saldo;
	}

}
