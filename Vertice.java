import java.util.ArrayList;
public class Vertice<T> {

	private int Cve;
	private boolean Visitado;
	private T Datos;
	private ArrayList<Arista<DatosA>> Adyacentes;
	private ArrayList<Vertice<T>> ruta;
	
	public Vertice(int cve, T datos) {
		Cve = cve;
		Datos = datos;
		Visitado = false;
		Adyacentes = new ArrayList<Arista<DatosA>>();
	}
	
	public boolean isVisitado() {
		return Visitado;
	}
	
	public void setVisitado(boolean visitado) {
		Visitado = visitado;
	}

	public int getCve() {
		return Cve;
	}

	public void setCve(int cve) {
		Cve = cve;
	}

	public T getDatos() {
		return Datos;
	}

	public void setDatos(T datos) {
		Datos = datos;
	}

	public ArrayList<Arista<DatosA>> getAdyacentes() {
		return Adyacentes;
	}

	public void setAdyacentes(ArrayList<Arista<DatosA>> adyacentes) {
		Adyacentes = adyacentes;
	}

	public ArrayList<Vertice<T>> getRuta() {
		return ruta;
	}

	public void setRuta(ArrayList<Vertice<T>> path) {
		this.ruta = new ArrayList<Vertice<T>>();
		this.ruta.addAll(path);
		this.ruta.add(this);
	}
	
}
