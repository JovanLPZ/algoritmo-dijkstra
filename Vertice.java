package Grafos;

import java.util.ArrayList;

public class Vertice<T> 
{

	private int Cve;
	private boolean Visitado;
	private T Datos;
	private ArrayList<Arista<DatosA>> Adyacentes;
	private ArrayList<Vertice<T>> Path;
	
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

	public ArrayList<Vertice<T>> getPath() {
		return Path;
	}

	public void setPath(ArrayList<Vertice<T>> path) {
		Path = new ArrayList<Vertice<T>>();
		Path.addAll(path);
		Path.add(this);
	}
	
}

