import java.util.Random;
import java.util.ArrayList;
//Generador del laberinto
public class Generador {
    //Implementacion del generador del laberinto

    private Laberinto laberinto;
    private Celda celdaEntrada;
    private Celda celdaSalida;
    private Random random = new Random();
 

    public Generador() {
        this.laberinto = new Laberinto();
        this.celdaEntrada = null;
        this.celdaSalida = null;

    }

    public Laberinto getLaberinto() {
        return laberinto;
    }

    public void generarLaberinto(int filas, int columnas) {
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto.agregarCelda(i, j);
            }
        }
        establecerEntradaSalida(filas, columnas);
        LaberintoBase(filas, columnas);
        agregarCaminos(20);
    }

    public void LaberintoBase(int filas, int columnas) {
        laberinto.limpiarCeldas();
        if(celdaEntrada == null || celdaSalida == null) {
            establecerEntradaSalida(filas, columnas);
            return;
        }
        generarDFS(celdaEntrada);

            
        }
    private void generarDFS(Celda celdaActual) {
        celdaActual.setVisitada(true);
        ArrayList<Celda> vecinos = obtenerVecinosNoVisitados(celdaActual);
        for (Celda celdaSiguiente:vecinos){
            laberinto.agregarConexion(celdaActual, celdaSiguiente);
            generarDFS(celdaSiguiente);
        }

    }

    private ArrayList<Celda> obtenerVecinosNoVisitados(Celda celda) {
        ArrayList<Celda> vecinos = new ArrayList<>();
        int fila = celda.getFila();
        int columna = celda.getColumna();

        //Arriba
        Celda vecino = laberinto.getCelda(fila - 1, columna);
        if (vecino != null && !vecino.isVisitada()) {
            vecinos.add(vecino);
        }
        //Derecha
        vecino = laberinto.getCelda(fila, columna + 1);
        if (vecino != null && !vecino.isVisitada()) {
            vecinos.add(vecino);
        }
        //Abajo
        vecino = laberinto.getCelda(fila + 1, columna);
        if (vecino != null && !vecino.isVisitada()) {
            vecinos.add(vecino);
        }
        //Izquierda
        vecino = laberinto.getCelda(fila, columna - 1);
        if (vecino != null && !vecino.isVisitada()) {
            vecinos.add(vecino);
        }

        java.util.Collections.shuffle(vecinos);//Mezcla el orden de los vecinos
        return vecinos;
    }

    public void establecerEntradaSalida(int filas, int columnas) { 
        //Generar celda de entrada y salida aleatoriamente diferentes
        boolean fila_columna = random.nextBoolean();
        if (fila_columna) { //Entrada y salida en fila arriba y abajo
            celdaEntrada = laberinto.getCelda(0, random.nextInt(columnas));
            
            celdaSalida = laberinto.getCelda(filas-1, random.nextInt(columnas));
            
            
        } else { //Entrada y salida en columna izquierda y derecha
            celdaEntrada = laberinto.getCelda(random.nextInt(filas), 0);
            
            celdaSalida = laberinto.getCelda(random.nextInt(filas), columnas-1);
        }
        
    
    }

    public Celda getCeldaEntrada(){
        return celdaEntrada;
    }
    public Celda getCeldaSalida(){
        return celdaSalida;
    }

    


    public void agregarCaminos(int porcentaje) {
        int totalCeldas = laberinto.celdas.size();
        int murosInternos= totalCeldas*2;
        int murosAEliminar = murosInternos * porcentaje / 100;

       int[][] direcciones = { {-1, 0},{1, 0},{0, -1}, {0, 1} };//arriba, abajo, izquierda, derecha 

        for (int i = 0; i < murosAEliminar; i++) {
            Celda celda = laberinto.celdas.get(random.nextInt(totalCeldas));
            int direccion = random.nextInt(4);
            
            int nuevaFila = celda.getFila() + direcciones[direccion][0];
            int nuevaColumna = celda.getColumna() + direcciones[direccion][1];
            

            Celda celdaVecina = laberinto.getCelda(nuevaFila, nuevaColumna);
            if (celdaVecina != null && celda.getParedes()[direccion]) {
                laberinto.agregarConexion(celda, celdaVecina);
            }
        }
        
    }
}

