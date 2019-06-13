package sistema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;


//El main no debería ir en este package, luego podría sacarse
public class Main {

	public static void main(String[] args) {

		Sistema sistemaAereo = new Sistema();

		leerArchivos(sistemaAereo);

		menu(sistemaAereo);
	}

	public static void menu(Sistema sistemaAereo) {

		ArrayList<String> salida = new ArrayList<String>();
		
		mensajeBienvenida();

		boolean repetir = true;
		
		while(repetir) {
		
		listaOpcionesDeMenu();
		
		int opcion = pedirNumero();

		switch (opcion) {
		case 0:
			System.out.println("Gracias por utilizar el sistema de información a viajeros frecuentes");
			repetir = false;
			break;
		case 1:
			listarAeropuertos(sistemaAereo);
			break;
		case 2:
			// pido todas las rutas y las imprimo mostrando las reservas de cada aerolinea
			// dentro de cada ruta
			salida.clear();
			ArrayList<Reserva> reservas = sistemaAereo.getReservas();
			for (int i = 0; i < reservas.size(); i++) {
				System.out.println(reservas.get(i).toString());
			}
			break;
		case 3:
			salida.clear();
			listarAeropuertos(sistemaAereo);
			System.out.println("Ingrese aeropuerto origen");
			int origen = pedirNumero();
			System.out.println("Ingrese aeropuerto destino");
			int destino = pedirNumero();
			ArrayList<String> aerolineas = sistemaAereo.listarAerolineas();
			for (int i = 0; i < aerolineas.size(); i++) {
				System.out.println((i + 1) + ". " + aerolineas.get(i));
			}
			System.out.println("Ingrese aerolinea deseada");
			int aerolinea = pedirNumero();
			ArrayList<Vuelo> vuelo = sistemaAereo.buscarVueloDirecto(origen, destino, aerolineas.get(aerolinea - 1));
			if (vuelo != null) {
				for(int i = 0; i<vuelo.size(); i++) {
					System.out.println(vuelo.get(i).toString());
					salida.add(vuelo.get(i).toString());
				}
				escribirArchivos(salida, opcion);
			} else {
				System.out.println(
						"No se ha encontrado un vuelto directo entre los aeropuertos y la aerolinea especificada");
			}
			break;
		case 4:
			salida.clear();
			listarAeropuertos(sistemaAereo);
			System.out.println("Ingrese aeropuerto origen");
			int Aorigen = pedirNumero();
			System.out.println("Ingrese aeropuerto destino");
			int Adestino = pedirNumero();
			ArrayList<String> aerolineasDisponibles = sistemaAereo.listarAerolineas();
			for (int i = 0; i < aerolineasDisponibles.size(); i++) {
				System.out.println((i + 1) + ". " + aerolineasDisponibles.get(i));
			}
			System.out.println("Ingrese aerolinea no deseada: ");
			int aerolineaNoDeseada = pedirNumero();
			ArrayList<VueloConEscala> rutas = sistemaAereo.obtenerVuelosDisponibles(Aorigen, Adestino,
					aerolineasDisponibles.get(aerolineaNoDeseada-1));
			if (rutas.isEmpty()) {
				System.out.println("No se han encontrado vuelos disponibles");
			}else {
				for ( int i = 0 ; i < rutas.size(); i ++) {
					System.out.println(rutas.get(i).toString());
					salida.add(rutas.get(i).toString());
				}
				escribirArchivos(salida, opcion);
			}
			break;
		case 5:
			salida.clear();
			ArrayList<String> paises = sistemaAereo.listarPaises();
			for (int i = 0; i < paises.size(); i++) {
				System.out.println((i + 1) + ". " + paises.get(i));
			}
			System.out.println("Ingrese país origen");
			int paisOrigen = pedirNumero();
			System.out.println("Ingrese país destino");
			int paisDestino = pedirNumero();

			ArrayList<Vuelo> vuelosDirectos = sistemaAereo.obtenerVueloEntrePaises(paises.get(paisOrigen - 1),
					paises.get(paisDestino - 1));
			if (!vuelosDirectos.isEmpty()) {
				for (int j = 0; j < vuelosDirectos.size(); j++) {
					System.out.println(vuelosDirectos.get(j).toString());
					salida.add(vuelosDirectos.get(j).toString());
				}
				escribirArchivos(salida, opcion);
			} else {
				System.out.println("No hay vuelos disponibles entre los paises especificados.");
			}
			break;
		case 6:
			salida.clear();
			listarAeropuertos(sistemaAereo);
			int principio = pedirNumero();
			Recorrido recorrido = sistemaAereo.supervisarFuncionamiento(principio);
			System.out.println(recorrido);
			break;
		default:
			System.out.println("La opción ingresada es incorrecta.");
			repetir = false;
		}
		System.out.println("*****************************************************************");
		System.out.println("¿Desea realizar otra consulta?");
	  }
	}

	public static void leerArchivos(Sistema sistemaAereo) {

		// CAMBIAR RUTAS SEGUN LA PC
		String csvAeropuertos = "src/sistema/dataset/Aeropuertos.csv";
		String csvRutas = "src/sistema/dataset/Rutas.csv";
		String csvReservas = "src/sistema/dataset/Reservas.csv";
		String line = "";
		String cvsSplitBy = ";";

		try (BufferedReader br = new BufferedReader(new FileReader(csvAeropuertos))) {

			while ((line = br.readLine()) != null) {

				String[] aeropuertos = line.split(cvsSplitBy);

				// Cambio de recibir todo los parametros a solo recibir el aeropuerto construido
				Aeropuerto aeropuerto = new Aeropuerto(aeropuertos[0], aeropuertos[1], aeropuertos[2]);
				sistemaAereo.addAeropuerto(aeropuerto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(csvRutas))) {

			while ((line = br.readLine()) != null) {

				String[] rutas = line.split(cvsSplitBy);

				ArrayList<Aerolinea> aux = new ArrayList<>();

				// Esto separa las aerolineas que vienen individualmente
				String[] aerolineas = rutas[4].split(",");

				for (int i = 0; i < aerolineas.length; i++) {
					// cambiar esto para splitear por guion
					aerolineas[i] = aerolineas[i].replaceAll("\\}", "");
					aerolineas[i] = aerolineas[i].replaceAll("\\{", "");
					String[] aeroNombreCant = aerolineas[i].split("-");
					aux.add(new Aerolinea(aeroNombreCant[0], Integer.parseInt(aeroNombreCant[1])));
				}

				if (rutas[3].equals("1")) {
					rutas[3] = "false";
				} else {
					rutas[3] = "true";
				}
				/*
				 * busco los aeropuertos por el nombre, podria ser busarAeropuertoPorNombre(mas
				 * representativo)
				 */
				Aeropuerto origen = sistemaAereo.devolverAeropuerto(rutas[0]);
				Aeropuerto destino = sistemaAereo.devolverAeropuerto(rutas[1]);
				// le paso por parametro a la instancia de ruta los aeropuertos encontrados
				Ruta ruta = new Ruta(origen, destino, Double.parseDouble(rutas[2]), Boolean.parseBoolean(rutas[3]));

				ruta.addAerolineas(aux);

				sistemaAereo.addRuta(ruta);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(csvReservas))) {

			while ((line = br.readLine()) != null) {

				String[] reservas = line.split(cvsSplitBy);
				Reserva reserva = new Reserva(reservas[0], reservas[1], reservas[2], Integer.parseInt(reservas[3]));
				sistemaAereo.addReserva(reserva);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void escribirArchivos(ArrayList<String> salida, int archivo) {
		BufferedWriter bw = null;
		try {
			switch(archivo) {
			case 3:			
			File file1 = new File("src/sistema/dataget/servicio1.csv");
			if (!file1.exists()) {
				file1.createNewFile();
			}
			FileWriter fw = new FileWriter(file1);
			bw = new BufferedWriter(fw);
			for ( int i = 0 ; i < salida.size(); i++) {
				String contenidoLinea1 = salida.get(i);
				bw.write(contenidoLinea1);
				bw.newLine();
			}
			break;
			case 4:
			File file2 = new File("src/sistema/dataget/servicio2.csv");
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter fw2 = new FileWriter(file2);
			bw = new BufferedWriter(fw2);
			for ( int i = 0 ; i < salida.size(); i++) {
				String contenidoLinea1 = salida.get(i);
				bw.write(contenidoLinea1);
				bw.newLine();
			}
			break;
			case 5:
			File file3 = new File("src/sistema/dataget/servicio3.csv");
			if (!file3.exists()) {
				file3.createNewFile();
			}
			FileWriter fw3 = new FileWriter(file3);
			bw = new BufferedWriter(fw3);
			for ( int i = 0 ; i < salida.size(); i++) {
				String contenidoLinea1 = salida.get(i);
				bw.write(contenidoLinea1);
				bw.newLine();
			}
			break;
			}		
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
		System.out.println("Bienvenido al Sistema Centralizado de Información" + " a Viajeros Frecuentes");
		System.out.println("¿Qué operación desea realizar?");
	}

	public static void listaOpcionesDeMenu() {
		System.out.println("1. Listar todos los aeropuertos");
		System.out.println("2. Listar todas las reservas"); // cambiar a reservas realizadas
		System.out.println("3. Servicio 1: Verificar vuelos directos");
		System.out.println("4. Servicio 2: Obtener vuelos sin aerolinea");
		System.out.println("5. Servicio 3: Vuelos disponibles");
		System.out.println("6. Supervisar funcionamiento de los aeropuertos");
		System.out.println("0. Salir del sistema");
	}

	public static int pedirNumero() {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		int opcion;
		try {
			opcion = new Integer(entrada.readLine());
		} catch (Exception e) {
			opcion = 0;
		}
		return opcion;
	}

	public static void listarAeropuertos(Sistema sistemaAereo) {
		ArrayList<Aeropuerto> listaAeropuertos = sistemaAereo.listarAeropuertos();

		for (int i = 0; i < listaAeropuertos.size(); i++) {
			System.out.println((i + 1) + "." + listaAeropuertos.get(i).toString());
		}
	}
}
