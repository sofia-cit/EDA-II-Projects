public class Celda {
    int fila;
    int columna;//coordenadas de la celda

    boolean paredes[] = {true, true, true, true};//N,E,S,O
    boolean visitada = false;

    public Celda(int fila, int columna) {
        setCoordenadas(fila, columna);
    }

    public void eliminarPared(int direccion) {
        paredes[direccion] = false;
    }
    
    public int[] getCoordenadas() {
        return new int[]{fila, columna};
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }

    public boolean[] getParedes() {
        return paredes;
    }

    public int[] setCoordenadas(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        return new int[]{fila, columna};
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public void reiniciarParedes() {
        this.paredes = new boolean[]{true, true, true, true};
    }
    
    @Override
    public String toString() {
        return "Celda[" + fila + "," + columna + "]";
    }
}