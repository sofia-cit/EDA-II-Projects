import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Laberinto {
    //Implementacion del laberinto

    private List<List<Celda>> listaAdyacencia;
    private List<Celda> celdas;
    boolean conexion=false;
    
    public Laberinto() {
        this.celdas = new java.util.ArrayList<>();
        this.listaAdyacencia = new java.util.ArrayList<>();
    }
    public void agregarCelda(int fila, int columna) {
        if (existeCelda(fila, columna)) {
            return;
        }
        Celda nuevaCelda = new Celda(fila, columna);
        celdas.add(nuevaCelda);
        listaAdyacencia.add(new java.util.ArrayList<>());
        
   
    }
    public boolean existeCelda(int fila, int columna) {
        for (Celda c : celdas) {
            if (c.getCoordenadas()[0] == fila && c.getCoordenadas()[1] == columna) {
                return true;
            }
        }
        return false;
    }
    public void agregarConexion(Celda c1, Celda c2) {
        conexion=false;
        //Conectar dos celdas contiguas
        if (c1.getCoordenadas()[0] == c2.getCoordenadas()[0]) {
            //Misma fila
            if (c1.getCoordenadas()[1] + 1 == c2.getCoordenadas()[1]) {
                //c2 esta a la derecha de c1
                conexion=true;
                c1.eliminarPared(1); //Eliminar pared derecha de c1
                c2.eliminarPared(3); //Eliminar pared izquierda de c2
            } else if (c1.getCoordenadas()[1] - 1 == c2.getCoordenadas()[1]) {
                //c2 esta a la izquierda de c1
                conexion=true;
                c1.eliminarPared(3); //Eliminar pared izquierda de c1
                c2.eliminarPared(1); //Eliminar pared derecha de c2
            }
        } else if (c1.getCoordenadas()[1] == c2.getCoordenadas()[1]) {
            //Misma columna
            if (c1.getCoordenadas()[0] + 1 == c2.getCoordenadas()[0]) {
                //c2 esta abajo de c1
                conexion=true;
                c1.eliminarPared(2); //Eliminar pared abajo de c1
                c2.eliminarPared(0); //Eliminar pared arriba de c2
            } else if (c1.getCoordenadas()[0] - 1 == c2.getCoordenadas()[0]) {
                //c2 esta arriba de c1
                conexion=true;
                c1.eliminarPared(0); //Eliminar pared arriba de c1
                c2.eliminarPared(2); //Eliminar pared abajo de c2
            }
        }
        //Agregar conexion en la lista de adyacencia
        if (conexion) {
           listaAdyacencia.get(celdas.indexOf(c1)).add(c2);
           listaAdyacencia.get(celdas.indexOf(c2)).add(c1);
        }
    }

    public void recorrerCeldasDFS(Celda inicio) {
        limpiarCeldas();
        if (celdas.contains(inicio)) {
            dfsRecorrer(inicio);
        }
    }

    private void dfsRecorrer(Celda actual) {
        actual.setVisitada(true);

        int indiceActual = celdas.indexOf(actual);
        List<Celda> vecinos = listaAdyacencia.get(indiceActual);

        for (Celda vecino : vecinos) {
            if (!vecino.isVisitada()) {
                dfsRecorrer(vecino);
            }
        }
    }

    public void limpiarCeldas() {
        for (Celda c : celdas) {
            c.setVisitada(false);
        }
    }

    
    
    



    
}
