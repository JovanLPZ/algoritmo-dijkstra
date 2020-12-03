package Grafos;

public class DatosV {
	private String Ciudad;
	private float Tiempo;
	private float Viaticos;
	
	public DatosV(String ciudad, float tiempo) {
		Ciudad = ciudad;
		Tiempo = tiempo;
	}

	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	public float getTiempo() {
		return Tiempo;
	}

	public void setTiempo(float tiempo) {
		Tiempo = tiempo;
	}

	public float getViaticos() {
		return Viaticos;
	}

	public void setViaticos(float viaticos) {
		Viaticos = viaticos;
	}
	
	
	
	
}
