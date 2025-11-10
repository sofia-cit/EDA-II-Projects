import java.util.Random;
//Generador del laberinto
public class Generador {
    //Implementacion del generador del laberinto

    private Laberinto laberinto;
    private Celda celdaEntrada;
    private Celda celdaSalida;
    private Random random = new java.util.Random();

    public Generador() {
        this.laberinto = new Laberinto();
        this.celdaEntrada = null;
        this.celdaSalida = null;

    }

    public Laberinto getLaberinto() {
        return laberinto;
    }

    public void generarLaberinto(int filas, int columnas) {
        //Generar celdas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto.agregarCelda(i, j);
            }
        }
    }

    

    

    

    public void caminoLaberinto() {
        //Implementar algoritmo para generar el camino del laberinto

    }
}
