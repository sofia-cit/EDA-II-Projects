import javax.swing.*;
import java.awt.*;


public class PanelLaberinto extends JPanel{

    private final Generador controlador; //Logica del laberinto
    private final int tamanoCelda = 30;
    private int filas;
    private int columnas;

    //Posicion jugador(coordenadas)
    private int jugadorFila=0;
    private int jugadorColumna=0;

    public PanelLaberinto(Generador controlador,int filas,int columnas){
        this.controlador=controlador;
        this.filas=filas;
        this.columnas=columnas;

        setPreferredSize(new Dimension(columnas*tamanoCelda, filas*tamanoCelda));
        setBackground(Color.WHITE);
        
        this.jugadorFila=controlador.getCeldaEntrada().getFila();
        this.jugadorColumna=controlador.getCeldaEntrada().getColumna();


    }

    //Dibujar componentes
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D) g;

        //Mejorar calidad de dibujo
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Celdas y paredes
        dibujarLaberinto(g2d);
        dibujarEntradaSalida(g2d);
        dibujarJugador(g2d);

    }

    private void dibujarLaberinto(Graphics2D g2d){
        for(int i=0;i<filas;i++){
            for (int j=0;j<columnas;j++){
                Celda celda = controlador.getLaberinto().getCelda(i, j);
                if (celda==null){
                    continue;
                }
                boolean[] paredes = celda.getParedes();
                int x=j*tamanoCelda;
                int y=i*tamanoCelda;

                //grosor linea
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));

                //Bordes exteriores
                //Norte
                if(paredes[0]){
                    g2d.drawLine(x, y, x+tamanoCelda, y);
                }
                //Este
                if(paredes[1]){
                    g2d.drawLine(x+tamanoCelda, y, x+tamanoCelda, y+tamanoCelda);
                }
                //Sur
                if(paredes[2]){
                    g2d.drawLine(x, y+tamanoCelda, x+tamanoCelda, y+tamanoCelda);
                }
                //Oeste
                if(paredes[3]){
                    g2d.drawLine(x, y, x, y+tamanoCelda);
                }
            }
        }

    }
    private void dibujarEntradaSalida(Graphics2D g2d){
        int entradaX= controlador.getCeldaEntrada().getColumna();
        int entradaY=controlador.getCeldaEntrada().getFila();

        g2d.setColor(Color.GREEN.darker());
        g2d.fillRect(entradaX+1,entradaY+1,tamanoCelda-2,tamanoCelda-2);

        int salidaX=controlador.getCeldaSalida().getColumna();
        int salidaY=controlador.getCeldaSalida().getFila();
        g2d.setColor(Color.RED.darker());
        g2d.fillRect(salidaX+1,salidaY+1,tamanoCelda-2,tamanoCelda-2);


    }

    private void dibujarJugador(Graphics2D g2d){
        int x=jugadorColumna*tamanoCelda;
        int y=jugadorFila*tamanoCelda;
        int margen=tamanoCelda-5;

        g2d.setColor(Color.BLUE);
        g2d.fillOval(x+margen, y+margen,tamanoCelda-2*margen, tamanoCelda-2*margen);

    }

    public void moverJugador(int dFila,int dColumna){
        int nuevaFila=jugadorFila+dFila;
        int nuevaColumna=jugadorColumna+dColumna;

        if(nuevaFila<0 || nuevaFila>= filas || nuevaColumna <0 || nuevaColumna>=columnas){
            return;
        }
        if(puedoMover(dFila,dColumna)){
            jugadorFila=nuevaFila;
            jugadorColumna=nuevaColumna;
        }
        repaint();

        //Checar si gano
        if(jugadorGano()){


        }

    }

    private boolean puedoMover(int dFila, int dColumna){
        Celda celdaActual=controlador.getLaberinto().getCelda(jugadorFila, jugadorColumna);
        if(celdaActual==null){
            return false;
        }
        boolean[] paredes = celdaActual.getParedes();
        if(dFila ==-1 && dColumna==0){//Norte
            return !paredes[0];
        }
        else if(dFila==0 && dColumna==1){
            return !paredes[1];
        }
        else if (dFila==1 && dColumna==0){
            return !paredes[2];
        }
        else if (dFila==0 && dColumna == -1){
            return !paredes[3];
        }
        return false;

    }

    private boolean jugadorGano(){
        return(jugadorFila==controlador.getCeldaSalida().getFila() && jugadorColumna == controlador.getCeldaSalida().getColumna());
    }
}


    

