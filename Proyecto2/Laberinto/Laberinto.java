
import java.util.List;


public class Laberinto {
    //Implementacion del laberinto

    public List<List<Celda>> listaAdyacencia;
    public List<Celda> celdas;
    
    public Laberinto() {
        this.celdas = new java.util.ArrayList<>();
        this.listaAdyacencia = new java.util.ArrayList<>();
    }
     public Celda getCelda(int fila, int columna) {
        for (Celda c : celdas) {
            if(c.getFila() == fila && c.getColumna() == columna) {
                return c;
            }
        }
        return null;
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
        return getCelda(fila, columna) != null;
    }

    public void agregarConexion(Celda c1, Celda c2) {
        //Conectar dos celdas contiguas
        int dirC1=-1;
        int dirC2=-1;
        if (c1.getFila() == c2.getFila()) {
            if (c1.getColumna() + 1 == c2.getColumna()) {
                dirC1 = 1; // Derecha
                dirC2 = 3; // Izquierda
            } else if (c1.getColumna() - 1 == c2.getColumna()) {
                dirC1 = 3; // Izquierda
                dirC2 = 1; // Derecha
            }
        } else if (c1.getColumna() == c2.getColumna()) {
            if (c1.getFila() + 1 == c2.getFila()) {
                dirC1 = 2; // Abajo
                dirC2 = 0; // Arriba
            } else if (c1.getFila() - 1 == c2.getFila()) {
                dirC1 = 0; // Arriba
                dirC2 = 2; // Abajo
            }
        }
        if(dirC1 != -1 && dirC2 != -1) {
            c1.eliminarPared(dirC1);
            c2.eliminarPared(dirC2);
            int indiceC1 = celdas.indexOf(c1);
            int indiceC2 = celdas.indexOf(c2);
            listaAdyacencia.get(indiceC1).add(c2);
            listaAdyacencia.get(indiceC2).add(c1);
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
