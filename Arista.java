package Grafos;

public class Arista<DatosA>
{
    private int CveV;
	private DatosA Datos;
	
    public Arista(int ClaveVertice, DatosA datos) 
    {
		CveV = ClaveVertice;
		Datos = datos;
	}

    public int getCveV() 
    {
		return CveV;
	}

    public void setCveV(int cveV) 
    {
		CveV = cveV;
	}

    public DatosA getDatos() 
    {
		return Datos;
	}

    public void setDatos(DatosA datos) 
    {
		Datos = datos;
	}
}


