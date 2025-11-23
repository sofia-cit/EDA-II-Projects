import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Generador {

    private Laberinto laberinto;
    private Celda celdaEntrada;
    private Celda celdaSalida;
    private Random random = new Random();
    private int porcentajeCaminosExtra = 0;

    public Generador() {
        this.laberinto = new Laberinto();
        this.celdaEntrada = null;
        this.celdaSalida = null;
    }

    public Laberinto getLaberinto() {
        return laberinto;
    }

    public void setPorcentajeCaminosExtra(int porcentaje) {
        this.porcentajeCaminosExtra = porcentaje;
    }

    public void generarLaberinto(int filas, int columnas) {
        //limpia laberinto anterior
        laberinto.celdas.clear();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto.agregarCelda(i, j);
            }
        }

        laberinto.reiniciarParedes();


        LaberintoBase(filas, columnas);//genera el laberinto base
        establecerEntradaSalida(filas, columnas);//establece entrada y salida
        agregarCaminos(this.porcentajeCaminosExtra);//agrega caminos extra segun el porcentaje (esto lo vemos pq falta ajustar la generacion de paredes)
    }

 
    public void LaberintoBase(int filas, int columnas) {
        laberinto.limpiarCeldas();
        
        if (laberinto.celdas.isEmpty()) {//se agregan las celdas si no existen
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    laberinto.agregarCelda(i, j);
                }
            }
        }
        
        Celda celdaInicio;
        if (celdaEntrada == null) {//Se asegura que haya celda de inicio
            celdaInicio = laberinto.getCelda(0, 0);
        } else {
            celdaInicio = celdaEntrada;
        }
        
        generarDFS(celdaInicio);//Genera el laberinto usando DFS
    }

    private void generarDFS(Celda celdaActual) {
        celdaActual.setVisitada(true);//Marca la celda como visitada
        ArrayList<Celda> vecinos = obtenerVecinosNoVisitados(celdaActual);//vecinos sin visitar de la celda actual

        Collections.shuffle(vecinos);//mezcla los vecinos para generar caminos aleatorios
        //shuffle cambia el orden de los elementos en la lista de forma aleatoria

        for (Celda vecino : vecinos) {
            if (!vecino.isVisitada()) {
                //conecta la celda actual con el vecino
                laberinto.agregarConexion(celdaActual, vecino);
                generarDFS(vecino);//llamada recursiva hasta que no queden vecinos sin visitar
            }
        }
    }

    private ArrayList<Celda> obtenerVecinosNoVisitados(Celda celda) {//obtiene los vecinos no visitados de una celda
        ArrayList<Celda> vecinos = new ArrayList<>();

        int fila = celda.getFila();
        int col = celda.getColumna();

        Celda v;

        v = laberinto.getCelda(fila - 1, col);
        //celda arriba, abajo, izquierda, derecha
        if (v != null && !v.isVisitada()) vecinos.add(v);

        v = laberinto.getCelda(fila, col + 1);
        if (v != null && !v.isVisitada()) vecinos.add(v);

        v = laberinto.getCelda(fila + 1, col);
        if (v != null && !v.isVisitada()) vecinos.add(v);

        v = laberinto.getCelda(fila, col - 1);
        if (v != null && !v.isVisitada()) vecinos.add(v);

        return vecinos;
    }

    public void establecerEntradaSalida(int filas, int columnas) {
        boolean fila_columna = random.nextBoolean();//decide si la entrada y la salida sera en una fila o columna(pared a pared)

        if (fila_columna) { //en columnas
            int colEntrada = random.nextInt(columnas);//posicion aleatoria en la columna
            int colSalida = random.nextInt(columnas);
            
            celdaEntrada = laberinto.getCelda(0, colEntrada);//establece entrada y salida
            celdaSalida = laberinto.getCelda(filas - 1, colSalida);
            
        } else { //lo mismo pero en filas
            int filaEntrada = random.nextInt(filas);
            int filaSalida = random.nextInt(filas);
            
            celdaEntrada = laberinto.getCelda(filaEntrada, 0);
            celdaSalida = laberinto.getCelda(filaSalida, columnas - 1);
        }
        
        // Asegurarse de que no sean null
        if (celdaEntrada == null) {
            celdaEntrada = laberinto.getCelda(0, 0);
        }
        if (celdaSalida == null) {
            celdaSalida = laberinto.getCelda(filas - 1, columnas - 1);
        }
    }

    public Celda getCeldaEntrada() { 
        return celdaEntrada; 
    }

    public Celda getCeldaSalida() { 
        return celdaSalida; 
    }

 
    public void agregarCaminos(int porcentaje) {
        if (porcentaje <= 0) return;
        
        int totalCeldas = laberinto.celdas.size();
        int murosInternos = totalCeldas * 2; //cada celda tiene aprox dos muros internos que pueden ser eliminados
        int murosAEliminar = murosInternos * porcentaje / 100; //calcula cuántos muros eliminar

        // N, E, S, O
        int[][] direcciones = {{-1,0}, {0,1}, {1,0}, {0,-1}}; //direcciones para moverse en el laberinto

        for (int i = 0; i < murosAEliminar; i++) {//elimina muros aleatoriamente
            Celda celda = laberinto.celdas.get(random.nextInt(totalCeldas));
            int dir = random.nextInt(4);//direccion random

            int nuevaFila = celda.getFila() + direcciones[dir][0];
            int nuevaCol = celda.getColumna() + direcciones[dir][1];

            Celda vecino = laberinto.getCelda(nuevaFila, nuevaCol);//establece el vecino segun la direccion
            
            if (vecino != null && celda.getParedes()[dir]) {//si el vecino existe y hay pared entre ellos
                laberinto.agregarConexion(celda, vecino);
            }
        }
    }
}