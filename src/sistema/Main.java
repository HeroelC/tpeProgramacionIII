package sistema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//El main no debería ir en este package, luego podría sacarse
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Sistema sistemaAereo = new Sistema();
		
		leerArchivos(sistemaAereo);
		
		menu(sistemaAereo);
	}

	public static void menu(Sistema sistemaAereo) {

		
		mensajeBienvenida();
		
		listaOpcionesDeMenu();
		
		int opcion = pedirNumero();

		
		switch(opcion) {
		case 0:
			System.out.println("Gracias por utilizar el sistema de información a viajeros frecuentes");
			break;
		case 1:
			listarAeropuertos(sistemaAereo);
			break;
		case 2:
			ArrayList<Ruta> listaRuta = sistemaAereo.listarRutas();
			
			for(int i = 0 ; i < listaRuta.size(); i++) {
				System.out.println((i+1) + "." + listaRuta.get(i).toString());
			}
			break;
		case 3:
			listarAeropuertos(sistemaAereo);
			System.out.println("Ingrese aerolinea origen");
			int origen = pedirNumero();
			System.out.println("Ingrese aerolinea destino");
			int destino = pedirNumero();
			System.out.println(sistemaAereo.buscarVueloDirecto(origen, destino));
			break;
		default: 
			System.out.println("La opción ingresada es incorrecta");
		}
	}
	
	public static void leerArchivos(Sistema sistemaAereo) {

		String csvAeropuertos = "C:/Users/Airways/eclipse-workspace/tpeProgramacionIII/src/sistema/dataset/Aeropuertos.csv";
		String csvRutas = "C:/Users/Airways/eclipse-workspace/tpeProgramacionIII/src/sistema/dataset/Rutas.csv";
		String csvReservas = "C:/Users/Airways/eclipse-workspace/tpeProgramacionIII/src/sistema/dataset/Reservas.csv";
		String line = "";
		String cvsSplitBy = ";";

		try (BufferedReader br = new BufferedReader(new FileReader(csvAeropuertos))) {

			while ((line = br.readLine()) != null) {

				String[] aeropuertos = line.split(cvsSplitBy);

				sistemaAereo.addAeropuerto(aeropuertos[0], aeropuertos[1], aeropuertos[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(csvRutas))) {

			while ((line = br.readLine()) != null) {

				String[] rutas = line.split(cvsSplitBy);
				
				if (rutas[3].equals("1")) {
					rutas[3] = "false";
				}
				else {
					rutas[3] = "true";
				}
				
				sistemaAereo.ge
				
				Ruta ruta = new Ruta(rutas[0], rutas[1], Double.parseDouble(rutas[2]), Boolean.parseBoolean(rutas[3]));
				sistemaAereo.addRuta();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try (BufferedReader br = new BufferedReader(new FileReader(csvReservas))) {
//
//			while ((line = br.readLine()) != null) {
//
//				String[] reservas = line.split(cvsSplitBy);
//
//				sistemaAereo.addAeropuerto(aeropuertos[0], aeropuertos[1], aeropuertos[2]);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void escribirArchivos() {
		BufferedWriter bw = null;
		try {
			File file = new File("[PATH-AL-ARCHIVO]/salida.csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			// Escribo la primer linea del archivo
			String contenidoLinea1 = "Usuario1;Tiempo1";
			bw.write(contenidoLinea1);
			bw.newLine();

			// Escribo la segunda linea del archivo
			String contenidoLinea2 = "Usuario2;Tiempo2";
			bw.write(contenidoLinea2);
			bw.newLine();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				System.out.println("Error cerrando el BufferedWriter" + ex);
			}
		}
	}
	
	public static void mensajeBienvenida() {
		System.out.println("Bienvenido al Sistema Centralizado de Información"
				+ " a Viajeros Frecuentes");
		System.out.println("¿Qué operación desea realizar?");
	}
	
	public static void listaOpcionesDeMenu() {
		System.out.println("1. Listar todos los aeropuertos");
		System.out.println("2. Listar todas las rutas"); //cambiar a reservas realizadas
		System.out.println("3. Servicio 1: Verificar vuelos directos");
		System.out.println("4. Servicio 2: Obtener vuelos sin aerolinea");
		System.out.println("5. Servicio 3: Vuelos disponibles");
		System.out.println("0. Salir del sistema");
	}
	public static int pedirNumero() {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		int opcion;
		try {	
			opcion = new Integer(entrada.readLine());	
		}catch(Exception e) {
			opcion = 0;
		}
		return opcion;
	}
	public static void listarAeropuertos(Sistema sistemaAereo) {
		ArrayList<Aeropuerto> listaAeropuertos = sistemaAereo.listarAeropuertos();
		
		for(int i = 0; i < listaAeropuertos.size(); i++) {
			System.out.println((i+1) + "." + listaAeropuertos.get(i).toString());
		}
	}
}
