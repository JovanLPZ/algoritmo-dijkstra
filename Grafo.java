package Grafos;

import java.util.ArrayList;

public class Grafo < T > {


    private ArrayList < Vertice < DatosV > > LVertices;
    private boolean Diri;

    public Grafo(boolean Dirigido) {
        LVertices = new ArrayList < Vertice < DatosV > > ();
        Diri = Dirigido;
    }

    public void AddVertice(int Cve, DatosV datos) {
        LVertices.add(new Vertice < DatosV > (Cve, datos));
    }

    public void DelVertice(int cveVer) throws GrafoException {
        int index = IndexOFV(cveVer);
        if (index != -1) {
            LVertices.remove(index);
            for (int v = 0; v < LVertices.size(); v++) {
                for (int a = 0; a < LVertices.get(v).getAdyacentes().size(); a++) {
                    if (LVertices.get(v).getAdyacentes().get(a).getCveV() == cveVer) {
                        LVertices.get(v).getAdyacentes().remove(a);
                    }
                }
            }
        } else {
            throw new GrafoException("Vertice no encontrado .");
        }
    }


    public void AddArista(int cveVerOri, int cveVerDes, DatosA datos) {
        int indexO = IndexOFV(cveVerOri);
        int indexD = IndexOFV(cveVerDes);
        if (indexO != -1 && indexD != -1) {
            LVertices.get(indexO).getAdyacentes().add(new Arista < DatosA > (cveVerDes, datos));
        }
        if (!Diri) {
            LVertices.get(indexD).getAdyacentes().add(new Arista < DatosA > (cveVerOri, datos));
        }
    }

    public void DelArista(int cveVerOri, int cveVerDes) throws GrafoException {
        int indexO = IndexOFV(cveVerOri);
        if (indexO != -1) {
            int indexD = IndexOfA(LVertices.get(indexO).getAdyacentes(), cveVerDes);
            if (indexD != -1) {
                LVertices.get(indexO).getAdyacentes().remove(indexD);
                if (!Diri) {
                    indexO = IndexOFV(cveVerDes);
                    if (indexO != -1) {
                        indexD = IndexOfA(LVertices.get(indexO).getAdyacentes(), cveVerOri);
                        if (indexD != -1) {
                            LVertices.get(indexO).getAdyacentes().remove(indexD);
                        }
                    } else {
                        throw new GrafoException("El vertice no existe ");
                    }
                }
            } else {
                throw new GrafoException("El vertice no existe ");
            }
        }
    }

    public void Recorrido(char tipo_A_P) throws GrafoException {
        switch (tipo_A_P) {
            case 'a':
            case 'A':
                RPA();
                break;
            case 'p':
            case 'P':
                RPP();
                break;
            default:
                throw new GrafoException("Tipo de recorrido invalido solo ( a= primero en anchura , p= primero en profundidad ");
        }
    }

    private void RPA() {
        PreparaInicioDeRecorrido();
        if (LVertices.size() > 0) {
            Visitar(LVertices.get(0).getDatos());
            LVertices.get(0).setVisitado(true);
            ColaAdd(LVertices.get(0).getCve());
            int v = ColaRet(), indexV = 0, NumAdy = 0, indexVA = 0;
            while (v > 0) {
                indexV = IndexOFV(v);
                if (indexV == -1) {
                    System.out.println("A ver cuando sale esto");
                    return;
                }
                ArrayList < Arista < DatosA > > Ady = LVertices.get(indexV).getAdyacentes();
                NumAdy = Ady.size();
                for (int a = 0; a < NumAdy; a++) {
                    indexVA = IndexOFV(Ady.get(a).getCveV());
                    if (!LVertices.get(indexVA).isVisitado()) {
                        ColaAdd(Ady.get(a).getCveV());
                        Visitar(LVertices.get(indexVA).getDatos());
                        LVertices.get(indexVA).setVisitado(true);
                    }
                }
                v = ColaRet();
            }
            System.out.println();
        } else {
            System.out.println("Grafo Vacio");
        }

    }

    private void RPP() {
        PreparaInicioDeRecorrido();
        if (LVertices.size() > 0) {
            RPP(0);
            System.out.println();
        } else {
            System.out.println("Grafo Vacio ");
        }
    }

    private void RPP(int index) {
        Visitar(LVertices.get(index).getDatos());
        LVertices.get(index).setVisitado(true);
        for (int i = 0; i < LVertices.get(index).getAdyacentes().size(); i++) {
            int SigIndex = IndexOFV(LVertices.get(index).getAdyacentes().get(i).getCveV());
            if (!LVertices.get(SigIndex).isVisitado()) {
                RPP(SigIndex);
            }
        }
    }
    private int IndexOFV(int cve) {
        for (int i = 0; i < LVertices.size(); i++) {
            if (LVertices.get(i).getCve() == cve) {
                return i;
            }

        }
        return -1;
    }

    private int IndexOfA(ArrayList < Arista < DatosA > > LA, int cve) {
        for (int i = 0; i < LA.size(); i++) {
            if (LA.get(i).getCveV() == cve) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList < Integer > cola = new ArrayList < Integer > ();

    private void ColaAdd(int vertice) {
        cola.add(vertice);
    }

    private int ColaRet() {
        if (cola.size() > 0) {
            int SigCve = cola.get(0);
            cola.remove(0);
            return SigCve;
        }
        return 0;
    }

    private void PreparaInicioDeRecorrido() {
        for (int i = 0; i < LVertices.size(); i++) {
            LVertices.get(i).setVisitado(false);
        }
    }

    private static void Visitar(DatosV vertice) {
        System.out.println(vertice.getCiudad() + " ,  ");
    }

    public int getSize() {
        return LVertices.size();
    }

    public int Grado(int CveVert) {
        return LVertices.get(IndexOFV(CveVert)).getAdyacentes().size();
    }

    public int GradoSalida(int CveVert) {
        return LVertices.get(IndexOFV(CveVert)).getAdyacentes().size();
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
        return Diri;
    }

    public void setDirigido(boolean diri) {
        Diri = diri;
    }

    public String toString() {
        String Salida = " ";
        if (LVertices.size() > 0) {
            for (int v = 0; v < LVertices.size(); v++) {
                Salida += LVertices.get(v).getCve() + " : " + LVertices.get(v).getDatos().getCiudad() + "  --> ";
                if (LVertices.get(v).getAdyacentes().size() > 0) {
                    for (int a = 0; a < LVertices.get(v).getAdyacentes().size(); a++) {
                        Salida += LVertices.get(v).getAdyacentes().get(a).getCveV() + " : " + LVertices
                            .get(IndexOFV(LVertices.get(v).getAdyacentes().get(a).getCveV())).getDatos().getCiudad() + " , ";
                    }
                }
                Salida += '\n';
            }
        }
        return Salida + "\nFin del listado\n\n";
    }
