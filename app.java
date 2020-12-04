import java.util.Scanner;

public class app
{

    private static Scanner leer = new Scanner(System.in);
	private static Grafo G;
	
    public static void main(String [] args) throws GrafoException 
    {
		int op = -1;
		G = new Grafo(false);
		
		//agregar datos
		//Grafo de ejemplificacion
		G.AddVertice(1, new DatosV("Baja California",0)); //
		G.AddVertice(2, new DatosV("Durango",0));       
		G.AddVertice(3, new DatosV("Sonora",0));		//
		G.AddVertice(4, new DatosV("Monterrey",0));		
		G.AddVertice(5, new DatosV("Sinaloa",0));
		G.AddVertice(6, new DatosV("Veracruz",0));		//
		G.AddVertice(7, new DatosV("Yucatan",0));		//
		G.AddArista(1,2, new DatosA(0,13));
		G.AddArista(1,3, new DatosA(0,11));
		G.AddArista(2,4, new DatosA(0,8));
		G.AddArista(2,5, new DatosA(0,11));
		G.AddArista(2,6, new DatosA(0,10));
		G.AddArista(3,4, new DatosA(0,12));
		G.AddArista(3,5, new DatosA(0,7));
		G.AddArista(3,6, new DatosA(0,7));
		G.AddArista(4,7, new DatosA(0,9));
		G.AddArista(5,7, new DatosA(0,12));
		G.AddArista(6,7, new DatosA(0,6));
		
		
		
		do {
			menu();
			op = leer.nextInt();
			switch(op) {
			case 1:
				AgregarV();
				break;
			case 2:
				RetirarV();
				break;
			case 3:
				AgregarA();
				break;
			case 4: 
				RetirarA();
				break;
			case 5:
				try {
					G.Recorrido('p', op);
				}catch(GrafoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				try {
					G.Recorrido('a', op);
				}catch(GrafoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				System.out.println(G);
			    System.out.println("Total de vertices en el grafo: " + G.getSize());
				break;
			case 8:
				GradoVertice();
				break;
			case 9:
                 System.out.println("Ingrese el inicio: ");               
                int cveOrigen = leer.nextInt();  
                
                System.out.println("Ingrese el final: ");  
                int cveDestino = leer.nextInt();
                
                G.MejorRecorrido(cveOrigen, cveDestino);

				System.out.println("\n");
				break;
			case 0:
				System.out.println("\tADIOS");
				break;
			default:
				System.out.println("Opcion no valida.");
			}
		}while(op != 0);
		System.out.println("\t PROGRAMA TERMINADO");
		leer.close();
	}
	
	public static void menu() {
		System.out.println("\n M E N U");
		System.out.println();
		System.out.println("1.- Agregar vertice");
		System.out.println("2.- Retirar vertice");
		System.out.println("3.- Agregar arista");
		System.out.println("4.- Retirar arista");
		System.out.println("5.- Recorrido primero en profundidad");
		System.out.println("6.- Recorrido primero en Anchura");
		System.out.println("7.- Total vertices");
		System.out.println("8.- Grado de un vertice");
		System.out.println("9.- Camino optimo");
		System.out.println("0.- Salir");
		System.out.println();
		System.out.print("Opcion: ");
	}
	
	//METODOS QUE DE SEGURO VAN EN LA CLASE Grafo.java
	public static void AgregarA() {
		System.out.println("* Agregar nueva Arista *");
		System.out.println("Clave del vertice inicial: ");
		int cveOri = leer.nextInt();
		System.out.println("Clave del vertice destino: ");
		int cveDes = leer.nextInt();
		System.out.println("Cantida de viaticos: ");
		float viaticos = leer.nextFloat();
		System.out.println("Demora en minutos para este proyecto: ");
		float tiempo = leer.nextFloat();
		G.AddArista(cveOri, cveDes, new DatosA(viaticos, tiempo));
		System.out.println("Arista agregado.");
	}
	
	private static void RetirarA() {
		System.out.println("* Eliminar arista *");
		int cveOri = leer.nextInt();
		System.out.println("Clave del vertice destino: ");
		int cveDes = leer.nextInt();
		try {
			G.DelArista(cveOri, cveDes);
		}catch (GrafoException e) {
			System.out.println(e);
		}
	}
	
	private static void GradoVertice() {
		System.out.println("* Grado del vertice *");
		System.out.println("Clave del vertice: ");
		int cve = leer.nextInt();
		if(G.isDirigido()) {
			System.out.println("El grado de entrada del vertice es: " + G.GradoEntrada(cve));
			System.out.println("El grado de salida del vertice es: "+ G.GradoSalida(cve));
		}else
			System.out.println("El grado del vertice es: " + G.Grado(cve));
	}
	
	private static void AgregarV() {
		System.out.println("* Agregar nuevo vertice *");
		System.out.println("Clave del vertice: ");
		int cve = leer.nextInt();
		System.out.println("Nombre de la ciudad: ");
		String city = leer.next();
		System.out.println("Demora en minutos para esta ciudad: ");
		float tiempo = leer.nextFloat();
		G.AddVertice(cve, new DatosV(city,tiempo));
		System.out.println("Vertice agregado.");
	}
	
	private static void RetirarV() {
		System.out.println("* Eliminar vertice *");
		System.out.println("Clave del vertice: ");
		int cve = leer.nextInt();
		try {
			G.DelVertice(cve);
		}catch(GrafoException e) {
			System.out.println(e);
		}
	}

}
