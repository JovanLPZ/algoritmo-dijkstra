import java.util.ArrayList;

public class Grafo 
{
	private ArrayList<Vertice<DatosV>> ArrayVertices;
	private boolean Dirigido;
	
	public Grafo(boolean dir) 
	{
		ArrayVertices = new ArrayList<Vertice<DatosV>>();
		Dirigido = dir;
	}
	
	public void AddVertice(int Cv, DatosV dat) 
	{
		ArrayVertices.add(new Vertice<DatosV>(Cv, dat));
	}
	
	public void AddArista(int cveVerO, int cveVerD, DatosA dat) 
	{
		int indexO = IndexOfV(cveVerO);
		int indexD = IndexOfV(cveVerD);

		if(indexO != -1 && indexD != -1)
		{
			ArrayVertices.get(indexO).getAdyacentes().add(new Arista<DatosA>(cveVerD, dat));
		}

		if(!Dirigido)
		{
			ArrayVertices.get(indexD).getAdyacentes().add(new Arista<DatosA>(cveVerO, dat));
		}
	}
	
	public void Recorrido(char tipoAP, int claveVerO) throws GrafoException 
	{
		switch(tipoAP) 
		{

		case 'a':
		case 'A':
			RPA(claveVerO, true);
			break;
		case 'p':
		case 'P':
			RPP(claveVerO);
			break;
			default:
				throw new GrafoException("Tipo de recorrido invalido solo, a: primero en anchura, p: primero en profundidad");
		}
	}
	
	
	
	private void RPA(int indInic, boolean esBasadoEnTim) 
	{
		PreparaInicioDeRecorrido();

		if(ArrayVertices.size() > 0) 
		{
			Vertice<DatosV> vr = ArrayVertices.get(IndexOfV(indInic));
			DatosV datosVerticeRaiz = vr.getDatos();

			vr.setVisitado(true);
			vr.setRuta(new ArrayList<Vertice<DatosV>>());
			datosVerticeRaiz.setTiempo(0);
			datosVerticeRaiz.setViaticos(0);
			ColaAdd(vr.getCve());
			
			int V = ColaRet(), indexV=0, NumAdy=0, indexVA=0;

			while(V >= 0) 
			{
				indexV = IndexOfV(V);
				if(indexV == -1) 
				{
					System.out.println("A ver cuando sale esto");
					return;
				}

				Vertice<DatosV> vActual = ArrayVertices.get(indexV);
				ArrayList<Arista<DatosA>> Ad = vActual.getAdyacentes();
				NumAdy = Ad.size();

				for(int i=0; i < NumAdy; i++) 
				{
					Arista<DatosA> aristaActual = Ad.get(i);
					indexVA = IndexOfV(aristaActual.getCveV());
					Vertice<DatosV> verticeConectado = ArrayVertices.get(indexVA);
					DatosV datosVerticeConectado = verticeConectado.getDatos();
					if(!verticeConectado.isVisitado()) 
					{
						ColaAdd(aristaActual.getCveV());
						datosVerticeConectado.setTiempo(vActual.getDatos().getTiempo() + aristaActual.getDatos().getTiempo());
						datosVerticeConectado.setViaticos(vActual.getDatos().getViaticos() + aristaActual.getDatos().getViaticos());
						verticeConectado.setRuta(vActual.getRuta());
						verticeConectado.setVisitado(true);
					}

					else 
					{
						if (!esBasadoEnTim && datosVerticeConectado.getViaticos() > vActual.getDatos().getViaticos() + aristaActual.getDatos().getViaticos()) 
						{
							datosVerticeConectado.setViaticos(vActual.getDatos().getViaticos() + aristaActual.getDatos().getViaticos());
							datosVerticeConectado.setTiempo(vActual.getDatos().getTiempo() + aristaActual.getDatos().getTiempo());
							verticeConectado.setRuta(vActual.getRuta());
					    }
						
						if (esBasadoEnTim && datosVerticeConectado.getTiempo() > vActual.getDatos().getTiempo() + aristaActual.getDatos().getTiempo()) 
						{
							datosVerticeConectado.setTiempo(vActual.getDatos().getTiempo() + aristaActual.getDatos().getTiempo());
							datosVerticeConectado.setViaticos(vActual.getDatos().getViaticos() + aristaActual.getDatos().getViaticos());
							verticeConectado.setRuta(vActual.getRuta());
					    }		
					}	
				}
				V=ColaRet();
			}
			System.out.println();
		}
		else
			System.out.println("Grafo Vacio");
	}
	
	private void RPP(int ind) 
	{
		Visitar(ArrayVertices.get(ind).getDatos());
		ArrayVertices.get(ind).setVisitado(true);

		for(int i=0; i < ArrayVertices.get(ind).getAdyacentes().size(); i++) 
		{
			int SigIndex = IndexOfV(ArrayVertices.get(ind).getAdyacentes().get(i).getCveV());

			if(!ArrayVertices.get(SigIndex).isVisitado())
			{
				RPP(SigIndex);
			}
		}
	}
	
	private int IndexOfV(int cve) 
	{
		for(int i=0; i < ArrayVertices.size(); i++)
		{
			if(ArrayVertices.get(i).getCve() == cve)
			{
				return i;
			}
		}

		return -1;
	}
	
	private int IndexOfA(ArrayList<Arista<DatosA>> LA, int cv) 
	{
		for(int i=0; i< LA.size(); i++)
		{
			if(LA.get(i).getCveV() == cv)
			{
				return i;
			}
		}
		return -1;
	}
	
	private ArrayList<Integer> C = new ArrayList<Integer>();
	
	private void ColaAdd(int vertice) 
	{
		C.add(vertice);
	}
	
	private int ColaRet() 
	{
		if(C.size() > 0) 
		{
			int SC = C.get(0);
			C.remove(0);
			return SC;
		}

		return -1;
	}
	
	public void DVertice(int cveV) throws GrafoException
	{
		int ind = IndexOfV(cveV);
		if(ind != -1) 
		{
			ArrayVertices.remove(ind);
			for(int v=0; v < ArrayVertices.size(); v++) 
			{
				ArrayList<Arista<DatosA>> adyacentes = ArrayVertices.get(v).getAdyacentes();
				for(int ay=0; ay < adyacentes.size(); ay++) 
				{
					if(adyacentes.get(ay).getCveV() == cveV)
						adyacentes.remove(ay);
				}	
			}
		}
		else
			throw new GrafoException("Vertice no encontrado");
	}
	
	public void DArista(int cveVerO, int cveVerD) throws GrafoException
	{
		int indexO = IndexOfV(cveVerO);
		if(indexO != -1) 
		{
			int indexD = IndexOfA(ArrayVertices.get(indexO).getAdyacentes(), cveVerD);
			if(indexD != -1) 
			{
				ArrayVertices.get(indexO).getAdyacentes().remove(indexD);
				if(!Dirigido) 
				{
					indexO = IndexOfV(cveVerD);
					if(indexO != -1) 
					{
						indexD = IndexOfA(ArrayVertices.get(indexO).getAdyacentes(), cveVerO);
						if(indexD != -1)
							ArrayVertices.get(indexO).getAdyacentes().remove(indexD);
					}
					else
						throw new GrafoException("El vertice no existe");
				}
			}
		}
		else
			throw new GrafoException("El vertice no existe");
	}
	
	private void PreparaInicioDeRecorrido() 
	{
		for(int i=0; i < ArrayVertices.size();i++) 
		{
			ArrayVertices.get(i).setVisitado(false);
		}
	}
	
	private static void Visitar(DatosV V) 
	{
		System.out.println(V.getCiudad() + ", ");
	}

	public int GradoSalida(int CveVert) 
	{
	    return ArrayVertices.get(IndexOfV(CveVert)).getAdyacentes().size();
	}
	
	public int getSize() 
	{
	    return ArrayVertices.size();
	}

	public int Grado(int CveVert) 
	{
	    return ArrayVertices.get(IndexOfV(CveVert)).getAdyacentes().size();
	}

	public int GradoEntrada(int CveVert) 
	{
	    int grado = 0;
		if (ArrayVertices.size() > 0) 
		{
			for (int v = 0; v < ArrayVertices.size(); v++) 
			{
	            ArrayList < Arista < DatosA > > Ady = ArrayVertices.get(v).getAdyacentes();
				for (int a = 0; a < Ady.size(); a++) 
				{
					if (Ady.get(a).getCveV() == CveVert) 
					{
	                    grado++;
	                }
	            }
	        }
	    }
	        return grado;
	}

	public boolean isDirigido() 
	{
	    return Dirigido;
	}

	
	public void RutaOptima(int claveVerticeO, int claveVerticeD) throws GrafoException 
	{
		Vertice<DatosV> verticeO = ArrayVertices.get(IndexOfV(claveVerticeO));
		Vertice<DatosV> verticeD = ArrayVertices.get(IndexOfV(claveVerticeD));

		try 
		{
			Recorrido('A', claveVerticeO);
			PrintPath(verticeD.getRuta());
			System.out.println("\n\nTiempo de viaje: "+verticeD.getDatos().getTiempo());
		}
		catch(Exception e)
		{
			System.out.print("No existe ruta entre "+ verticeO.getDatos().getCiudad() + " y " + verticeD.getDatos().getCiudad()+"\n");
		}
	}
	
	private void PrintPath(ArrayList<Vertice<DatosV>> path) 
	{
		float T=0;
		for(int i = 0; i < path.size(); i++) 
		{
			DatosV dat = path.get(i).getDatos();
			if((i-1) !=-1)
			T=path.get(i-1).getDatos().getTiempo();
			System.out.print(dat.getCiudad() + " [" + (dat.getTiempo()-T)  + "]");
			
			if((i+1) < path.size())
			{
				System.out.print(" -> ");
			}
	
		}
	}
	

	public ArrayList<Vertice<DatosV>> getArrayVertices() {
		return ArrayVertices;
	}
	
	public void imprimirVertices(ArrayList<Vertice<DatosV>> lVertices) 
	{
		for (int i = 0; i < lVertices.size(); i++) 
		{
			System.out.println(lVertices.get(i).getCve()+" \t\t "+lVertices.get(i).getDatos().getCiudad());
		}
		System.out.println("\n");
	}
	
}

