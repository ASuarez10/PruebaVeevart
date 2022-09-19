package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//Menú inicial por consola
		Scanner entradaEscaner = new Scanner (System.in);
		String entrada = "";
		
		System.out.println("Elija el modo de juego:");
		System.out.println("1. Un jugador (gana si iguala o supera 25)");
		System.out.println("2. Un jugador (gana solo si iguala 25)");
		System.out.println("3. Varios jugadores.");
		System.out.println("4. Un jugador (tamanio del tablero personalizado)");
		System.out.println("Ingrese un numero:");
		
		entrada = entradaEscaner.nextLine();
		int numero = Integer.parseInt(entrada);
		
		if(numero == 1) {
			escaleraOriginal(dictEscalera(), dictSerpiente());
		}else if(numero == 2) {
			escaleraBonus2(dictEscalera(), dictSerpiente());
		}else if(numero == 3) {
			System.out.println("Ingrese el numero de jugadores:");
			entrada = entradaEscaner.nextLine();
			numero = Integer.parseInt(entrada);
			escaleraBonus3(dictEscalera(), dictSerpiente(), numero);
		}else if(numero == 4) {
			System.out.println("Ingrese el numero de filas:");
			entrada = entradaEscaner.nextLine();
			int numeroFilas = Integer.parseInt(entrada);
			
			System.out.println("Ingrese el numero de columnas:");
			entrada = entradaEscaner.nextLine();
			int numeroColumnas = Integer.parseInt(entrada);
			
			int tamanio = numeroColumnas*numeroFilas;
			
			System.out.println("Cantidad de cuadros: " + tamanio);
			
			Map<Integer, Integer> escalera = dictEscaleraAleatorio(tamanio);
			
			escaleraBonus4(escalera , dictSerpienteAleatorio(escalera, tamanio), tamanio);
		}
			
	}
	
	//Esta función crea un diccionario para las casillas con escalera, el cual tiene como claves las casillas de inicio
	//y como valor las casillas a las que lleva la escalera.
	public static Map<Integer, Integer> dictEscalera(){
		
		Map<Integer, Integer> dictEscalera = new HashMap<Integer, Integer>();
		
		dictEscalera.put(3, 11);
		dictEscalera.put(6, 17);
		dictEscalera.put(9, 18);
		dictEscalera.put(10, 12);
		
		return dictEscalera;
	}
	
	//Esta función crea un diccionario para las casillas con serpientes, el cual tiene como claves las casillas de inicio
	//y como valor las casillas a las que lleva la serpiente.
	public static Map<Integer, Integer> dictSerpiente(){
		
		Map<Integer, Integer> dictSerpiente = new HashMap<Integer, Integer>();
		
		dictSerpiente.put(14, 4);
		dictSerpiente.put(19, 8);
		dictSerpiente.put(22, 20);
		dictSerpiente.put(24, 16);
		
		return dictSerpiente;
	}
	
	//Esta función crea un diccionario para las casillas con escalera, el cual tiene como claves las casillas de inicio
	//y como valor las casillas a las que lleva la escalera. Las casillas son aleatorias y dependen del tamaño del tablero.
	public static Map<Integer, Integer> dictEscaleraAleatorio(int tamanio){
		
		Map<Integer, Integer> dictEscalera = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < 4; i ++) {
			int clave = (int)(Math.random()*(tamanio - 10) + 1);
			int valor = (int)(Math.random()*(tamanio - 1) + clave);
			dictEscalera.put(clave, valor);
		}
		
		return dictEscalera;
	}
	
	//Esta función crea un diccionario para las casillas con serpientes, el cual tiene como claves las casillas de inicio
	//y como valor las casillas a las que lleva la serpiente. Las casillas son aleatorias y dependen del tamaño del tablero.
	public static Map<Integer, Integer> dictSerpienteAleatorio(Map<Integer, Integer> dictEscalera, int tamanio){
		
		Map<Integer, Integer> dictSerpiente = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < 4; i ++) {
			int clave = (int)(Math.random()*(tamanio - 5) + 1);
			while(true) {
				if(!dictEscalera.containsKey(clave)) {
					int valor = (int)(Math.random()*(1) + (clave-1));
					dictSerpiente.put(clave, valor);
				}
				break;
			}
		}
		
		return dictSerpiente;
	}
	
	//Este es el programa ogininal, sin ningún bonus. Un juego de escalera donde el tablero es de 5x5. Hay 4 escaleras que te permiten escalar posiciones y 
	// 4 serpientes que te hacen bajar posiciones. Si alcanzas o superas la casilla 25 ganas.
	public static void escaleraOriginal(Map<Integer, Integer> dictEscalera, Map<Integer, Integer> dictSerpiente) {
		
		int posicionJugador1 = 0;
		
		while(true) {
			
			//Math random para tener un dado de 1-6.
			int dado = (int)(Math.random()*6 + 1);
			
			posicionJugador1 = posicionJugador1 + dado;
			
			System.out.println("Dado arroja " + dado);
			
			if(posicionJugador1 == 25) {
				System.out.println("Jugador llega al cuadro 25");
				break;
			}else if(posicionJugador1 > 25) {
				System.out.println("Jugador supera el cuadro 25");
				break;
			}else {
				System.out.println("Jugador avanza al cuadro " + posicionJugador1);
				
				if(dictEscalera.containsKey(posicionJugador1)) {
					posicionJugador1 = dictEscalera.get(posicionJugador1);
					System.out.println("Jugador sube por la escalera al cuadro " + posicionJugador1);
				}else if(dictSerpiente.containsKey(posicionJugador1)) {
					posicionJugador1 = dictSerpiente.get(posicionJugador1);
					System.out.println("Jugador desciende al cuadro " + posicionJugador1);
				}
			}
		}
		System.out.println("Fin");
	}

	//Al superar la casilla 25 debe retroceder el número de veces que supera esta, es decir que solo puede terminar el juego si cae exactamente en la casilla 25.
	public static void escaleraBonus2(Map<Integer, Integer> dictEscalera, Map<Integer, Integer> dictSerpiente) {
		
		int posicionJugador1 = 0;
		
		while(true) {
			
			int dado = (int)(Math.random()*6 + 1);
			
			posicionJugador1 = posicionJugador1 + dado;
			
			System.out.println("Dado arroja " + dado);
			
			if(posicionJugador1 == 25) {
				System.out.println("Jugador llega al cuadro 25");
				break;
				
			//Se verifica si la puntuación es mayor a 25; de ser así, se retrocede la cantidad de casillas que el jugador se pasó.
			}else if(posicionJugador1 > 25) {
				posicionJugador1 = 25-(posicionJugador1 - 25);
				System.out.println("Jugador supera el cuadro 25 y regresa al cuadro " + posicionJugador1);
			}else {
				System.out.println("Jugador avanza al cuadro " + posicionJugador1);
				
				if(dictEscalera.containsKey(posicionJugador1)) {
					posicionJugador1 = dictEscalera.get(posicionJugador1);
					System.out.println("Jugador sube por la escalera al cuadro " + posicionJugador1);
				}else if(dictSerpiente.containsKey(posicionJugador1)) {
					posicionJugador1 = dictSerpiente.get(posicionJugador1);
					System.out.println("Jugador desciende al cuadro " + posicionJugador1);
				}
			}
		}
		System.out.println("Fin");
	}
	
	//Agregue la opción de indicar el número de jugadores.
	public static void escaleraBonus3(Map<Integer, Integer> dictEscalera, Map<Integer, Integer> dictSerpiente, int NJugadores) {
		

		Map<Integer, Integer> posiciones = new HashMap<Integer, Integer>();
		
		//Se inicializan las claves para la cantidad de jugadores que se ingresan por consola.
		for(int i = 1; i <= NJugadores; i++) {
			posiciones.put(i, 0);
		}
		
		int jugadorActual = 1;
		int posicionJugadorActual = 0;
		
		while(true) {
			
			int dado = (int)(Math.random()*6 + 1);
			
			//Se modifica la posición de cada jugador dependiendo de la puntuación obtenida en los dados.
			posiciones.put(jugadorActual, posiciones.get(jugadorActual)+dado);
			
			posicionJugadorActual = posiciones.get(jugadorActual);
			
			System.out.println("Dado de jugador " + jugadorActual + " arroja " + dado);
			
			if(posicionJugadorActual == 25) {
				System.out.println("Jugador " + jugadorActual +  " llega al cuadro 25");
				break;
			}else if(posicionJugadorActual > 25) {
				System.out.println("Jugador " + jugadorActual +  " supera el cuadro 25");
				break;
			}else {
				System.out.println("Jugador " + jugadorActual + " avanza al cuadro " + posicionJugadorActual);
				
				if(dictEscalera.containsKey(posicionJugadorActual)) {
					posiciones.put(jugadorActual, dictEscalera.get(posicionJugadorActual));
					posicionJugadorActual = posiciones.get(jugadorActual);
					System.out.println("Jugador " + jugadorActual + " sube por la escalera al cuadro " + posicionJugadorActual);
				}else if(dictSerpiente.containsKey(posicionJugadorActual)) {
					posiciones.put(jugadorActual, dictSerpiente.get(posicionJugadorActual));
					posicionJugadorActual = posiciones.get(jugadorActual);
					System.out.println("Jugador " + jugadorActual + " desciende al cuadro " + posicionJugadorActual);
				}
			}
			
			//Se hace el cambio de jugador.
			if(jugadorActual == 3) {
				jugadorActual = 1;
			}else {
				jugadorActual++;
			}
		}
		System.out.println("Fin");
	}

	//Agregue la opción para que el tamaño (e.g: 30x30, 40x20) del tablero se pueda indicar
	//antes de iniciar el juego (Las posiciones de escaleras y serpientes pueden ser
	//dinámicas o aleatorias en este nuevo tablero)
	//
	//Se recibe, además, el tamaño del tablero para personalizar el juego.
	public static void escaleraBonus4(Map<Integer, Integer> dictEscalera, Map<Integer, Integer> dictSerpiente, int tamanio) {
		
		int posicionJugador1 = 0;
		
		while(true) {
			
			int dado = (int)(Math.random()*6 + 1);
			
			posicionJugador1 = posicionJugador1 + dado;
			
			System.out.println("Dado arroja " + dado);
			
			if(posicionJugador1 == tamanio) {
				System.out.println("Jugador llega al cuadro " + tamanio);
				break;
			}else if(posicionJugador1 > tamanio) {
				System.out.println("Jugador supera el cuadro " + tamanio);
				break;
			}else {
				System.out.println("Jugador avanza al cuadro " + posicionJugador1);
				
				if(dictEscalera.containsKey(posicionJugador1)) {
					posicionJugador1 = dictEscalera.get(posicionJugador1);
					System.out.println("Jugador sube por la escalera al cuadro " + posicionJugador1);
				}else if(dictSerpiente.containsKey(posicionJugador1)) {
					posicionJugador1 = dictSerpiente.get(posicionJugador1);
					System.out.println("Jugador desciende al cuadro " + posicionJugador1);
				}
			}
		}
		System.out.println("Fin");
	}
}
