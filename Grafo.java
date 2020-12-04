import java.util.ArrayList;

public class Grafo {
	private ArrayList<Vertice<DatosV>> LVertices;
	private boolean Dirigido;
	
	public Grafo(boolean dirigido) {
		LVertices = new ArrayList<Vertice<DatosV>>();
		Dirigido = dirigido;
	}
	
	public void AddVertice(int Cve, DatosV datos) {
		LVertices.add(new Vertice<DatosV>(Cve, datos));
	}
	
	public void DelVertice(int cveVer) throws GrafoException{
		int index = IndexOfV(cveVer);
		if(index != -1) {
			LVertices.remove(index);
			for(int v=0; v < LVertices.size(); v++) {
				ArrayList<Arista<DatosA>> adyacentes = LVertices.get(v).getAdyacentes();
				for(int a=0; a < adyacentes.size(); a++) {
					if(adyacentes.get(a).getCveV() == cveVer)
						adyacentes.remove(a);
				}	
			}
		}else
			throw new GrafoException("Vertice no encontrado.");
	}
	
	public void AddArista(int cveVerOri, int cveVerDes, DatosA datos) {
		int indexO = IndexOfV(cveVerOri);
		int indexD = IndexOfV(cveVerDes);
		if(indexO != -1 && indexD != -1)
			LVertices.get(indexO).getAdyacentes().add(new Arista<DatosA>(cveVerDes, datos));
		if(!Dirigido)
			LVertices.get(indexD).getAdyacentes().add(new Arista<DatosA>(cveVerOri, datos));
	}
	
	public void DelArista(int cveVerOri, int cveVerDes) throws GrafoException{
		int indexO = IndexOfV(cveVerOri);
		if(indexO != -1) {
			int indexD = IndexOfA(LVertices.get(indexO).getAdyacentes(), cveVerDes);
			if(indexD != -1) {
				LVertices.get(indexO).getAdyacentes().remove(indexD);
				if(!Dirigido) {
					indexO = IndexOfV(cveVerDes);
					if(indexO != -1) {
						indexD = IndexOfA(LVertices.get(indexO).getAdyacentes(), cveVerOri);
						if(indexD != -1)
							LVertices.get(indexO).getAdyacentes().remove(indexD);
					}else
						throw new GrafoException("El vertice no existe");
				}
			}
		}else
			throw new GrafoException("El vertice no existe");
	}
	
	public void Recorrido(char tipo_A_P, int claveVerticeOrigen) throws GrafoException {
		switch(tipo_A_P) {
		case 'a':
		case 'A':
			RPA(claveVerticeOrigen, true);
			break;
		case 'p':
		case 'P':
			RPP();
			break;
			default:
				throw new GrafoException(
						"Tipo de recorrido invalido solo ( a= primero en anchura, p= primero en profundidad)");
		}
	}
	
	private void RPA(int initialIndex, boolean isBasedTime) {
		PreparaInicioDeRecorrido();
		if(LVertices.size() > 0) {
			Vertice<DatosV> rootVertice = LVertices.get(IndexOfV(initialIndex));
			DatosV datosRootVertice = rootVertice.getDatos();
			//Visitar(datosRootVertice);
			rootVertice.setVisitado(true);
			rootVertice.setPath(new ArrayList<Vertice<DatosV>>());
			datosRootVertice.setTiempo(0);
			datosRootVertice.setViaticos(0);
			ColaAdd(rootVertice.getCve());
			
			int v = ColaRet(), indexV=0, NumAdy=0, indexVA=0;
			while(v >= 0) {
				indexV = IndexOfV(v);
				if(indexV == -1) {
					System.out.println("A ver cuando sale esto");
					return;
				}
				Vertice<DatosV> currentVertice = LVertices.get(indexV);
				ArrayList<Arista<DatosA>> Ady = currentVertice.getAdyacentes();
				NumAdy = Ady.size();
				for(int a=0; a < NumAdy; a++) {
					Arista<DatosA> currentArista = Ady.get(a);
					indexVA = IndexOfV(currentArista.getCveV());
					Vertice<DatosV> connectedVertice = LVertices.get(indexVA);
					DatosV connectedDatosVertice = connectedVertice.getDatos();
					if(!connectedVertice.isVisitado()) {
						ColaAdd(currentArista.getCveV());
						//Visitar(connectedDatosVertice);
						connectedDatosVertice.setTiempo(connectedDatosVertice.getTiempo() + currentArista.getDatos().getTiempo());
						connectedDatosVertice.setViaticos(connectedDatosVertice.getViaticos() + currentArista.getDatos().getViaticos());
						connectedVertice.setPath(currentVertice.getPath());
						connectedVertice.setVisitado(true);
					}  
					else {
						if (!isBasedTime && connectedDatosVertice.getViaticos() > currentVertice.getDatos().getViaticos() + currentArista.getDatos().getViaticos()) {
							connectedDatosVertice.setViaticos(currentVertice.getDatos().getViaticos() + currentArista.getDatos().getViaticos());
							connectedDatosVertice.setTiempo(currentVertice.getDatos().getTiempo() + currentArista.getDatos().getTiempo());
							connectedVertice.setPath(currentVertice.getPath());
					    }
						
					}	
				}
				v = ColaRet();
			}
			System.out.println();
		}else
			System.out.println("Grafo Vacio.");
	}
	
	private void RPP() {
		PreparaInicioDeRecorrido();
		if(LVertices.size() > 0) {
			RPP(0);
			System.out.println();
		}else
			System.out.println("Grafo Vacio.");
	}
	
	private void RPP(int index) {
		Visitar(LVertices.get(index).getDatos());
		LVertices.get(index).setVisitado(true);
		for(int i=0; i < LVertices.get(index).getAdyacentes().size(); i++) {
			int SigIndex = IndexOfV(LVertices.get(index).getAdyacentes().get(i).getCveV());
			if(!LVertices.get(SigIndex).isVisitado())
				RPP(SigIndex);
		}
	}
	
	private int IndexOfV(int cve) {
		for(int i=0; i < LVertices.size(); i++)
			if(LVertices.get(i).getCve() == cve)
				return i;
		return -1;//aqui
	}
	
	private int IndexOfA(ArrayList<Arista<DatosA>> LA, int cve) {
		for(int i=0; i< LA.size(); i++)
			if(LA.get(i).getCveV() == cve)
				return i;
		return -1;
	}
	
	private ArrayList<Integer> cola = new ArrayList<Integer>();
	
	private void ColaAdd(int vertice) {
		cola.add(vertice);
	}
	
	private int ColaRet() {
		if(cola.size() > 0) {
			int SigCve = cola.get(0);
			cola.remove(0);
			return SigCve;
		}
		return -1;
	}
	
	private void PreparaInicioDeRecorrido() {
		for(int i=0; i < LVertices.size();i++) {
			LVertices.get(i).setVisitado(false);
		}
	}
	
	private static void Visitar(DatosV vertice) {
		System.out.println(vertice.getCiudad() + ", ");
	}
	
	 public int getSize() {
	        return LVertices.size();
	    }

	    public int Grado(int CveVert) {
	        return LVertices.get(IndexOfV(CveVert)).getAdyacentes().size();
	    }

	    public int GradoSalida(int CveVert) {
	        return LVertices.get(IndexOfV(CveVert)).getAdyacentes().size();
	    }

	    public int GradoEntrada(int CveVert) {
	        int grado = 0;
	        if (LVertices.size() > 0) {
	            for (int v = 0; v < LVertices.size(); v++) {
	                ArrayList < Arista < DatosA > > Ady = LVertices.get(v).getAdyacentes();
	                for (int a = 0; a < Ady.size(); a++) {
	                    if (Ady.get(a).getCveV() == CveVert) {
	                        grado++;
	                    }
	                }
	            }
	        }
	        return grado;
	    }

	    public boolean isDirigido() {
	        return Dirigido;
	    }

	    public void setDirigido(boolean diri) {
	        Dirigido = diri;
	    }

	    public String toString() {
	        String Salida = " ";
	        if (LVertices.size() > 0) {
	            for (int v = 0; v < LVertices.size(); v++) {
	                Salida += LVertices.get(v).getCve() + " : " + LVertices.get(v).getDatos().getCiudad() + "  --> ";
	                if (LVertices.get(v).getAdyacentes().size() > 0) {
	                    for (int a = 0; a < LVertices.get(v).getAdyacentes().size(); a++) {
	                        Salida += LVertices.get(v).getAdyacentes().get(a).getCveV() + " : " + LVertices
	                            .get(IndexOfV(LVertices.get(v).getAdyacentes().get(a).getCveV())).getDatos().getCiudad() + " , ";
	                    }
	                }
	                Salida += '\n';
	            }
	        }
	        return Salida + "\nFin del listado\n\n";
	    }
	
	public void MejorRecorrido(int claveVerticeOrigen, int claveVerticeDestino) throws GrafoException {
		Recorrido('A', claveVerticeOrigen);
		Vertice<DatosV> verticeDestino = LVertices.get(IndexOfV(claveVerticeDestino));
		PrintPath(verticeDestino.getPath()); // TODO: imprimir path
		verticeDestino.getDatos(); // datos totales
	}
	
	private void PrintPath(ArrayList<Vertice<DatosV>> path) {
		for(int i = 0; i < path.size(); i++) {
			DatosV datos = path.get(i).getDatos();
			System.out.print(datos.getCiudad() + " (" + datos.getViaticos() + ", " + datos.getTiempo()  + "), ");
		}
		
	}
}

