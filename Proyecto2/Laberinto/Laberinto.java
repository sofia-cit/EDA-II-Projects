import java.util.List;
import java.util.ArrayList;

public class Laberinto {

    public List<Celda> celdas;
    
    public Laberinto() {
        this.celdas = new ArrayList<>();
    }

    public Celda getCelda(int fila, int columna) {
        for (Celda c : celdas) {
            if (c.getFila() == fila && c.getColumna() == columna) {
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
    }

    public boolean existeCelda(int fila, int columna) {
        return getCelda(fila, columna) != null;
    }

    public void agregarConexion(Celda a, Celda b) {
        int filaA = a.getFila();
        int colA = a.getColumna();
        int filaB = b.getFila();
        int colB = b.getColumna();
        //determina si a y b son adyacentes y si si, elimina la pared entre ellas
        //b arriba de a
        if (filaB == filaA - 1 && colB == colA) {
            a.eliminarPared(0); 
            b.eliminarPared(2);
        }
        //b a la derecha de a
        else if (filaB == filaA && colB == colA + 1) {
            a.eliminarPared(1);
            b.eliminarPared(3); 
        }
        // b abajo de a
        else if (filaB == filaA + 1 && colB == colA) {
            a.eliminarPared(2); 
            b.eliminarPared(0); 
        }
        // b a la izquierda de a
        else if (filaB == filaA && colB == colA - 1) {
            a.eliminarPared(3); 
            b.eliminarPared(1);
        }
    }

    public void limpiarCeldas() {//establece las celdas como no visitadas
        for (Celda c : celdas) {
            c.setVisitada(false);
        }
    }

    public void reiniciarParedes() {//Coloca todas las paredes en true para reiniciar el laberinto
        for (Celda c : celdas) {
            c.reiniciarParedes();
        }
    }
}