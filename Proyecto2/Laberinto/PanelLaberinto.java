import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelLaberinto extends JPanel {

    private final Generador controlador;
    private final int tamanoCelda = 20;
    private int filas;
    private int columnas;

    private int jugadorFila;
    private int jugadorColumna;

    public PanelLaberinto(Generador controlador, int filas, int columnas){
        this.controlador = controlador;
        this.filas = filas;
        this.columnas = columnas;

        setPreferredSize(new Dimension(columnas * tamanoCelda, filas * tamanoCelda));
        setBackground(Color.WHITE);

        Celda entrada = controlador.getCeldaEntrada();
        if (entrada != null) {
            this.jugadorFila = entrada.getFila();
            this.jugadorColumna = entrada.getColumna();
        } else {
            this.jugadorFila = 0;
            this.jugadorColumna = 0;
        }

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        moverJugador(-1, 0);
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        moverJugador(1, 0);
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        moverJugador(0, -1);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        moverJugador(0, 1);
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarLaberinto(g2d);
        dibujarEntradaSalida(g2d);
        dibujarJugador(g2d);
    }

    private void dibujarLaberinto(Graphics2D g2d) {
        Laberinto lab = controlador.getLaberinto();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));

        for (Celda celda : lab.celdas) {
            int fila = celda.getFila();
            int col = celda.getColumna();

            int x = col * tamanoCelda;
            int y = fila * tamanoCelda;

            boolean[] p = celda.getParedes(); // N, E, S, O

            // Norte
            if (p[0]) {
                g2d.drawLine(x, y, x + tamanoCelda, y);
            }
            // Este
            if (p[1]) {
                g2d.drawLine(x + tamanoCelda, y, x + tamanoCelda, y + tamanoCelda);
            }
            // Sur
            if (p[2]) {
                g2d.drawLine(x, y + tamanoCelda, x + tamanoCelda, y + tamanoCelda);
            }
            // Oeste
            if (p[3]) {
                g2d.drawLine(x, y, x, y + tamanoCelda);
            }
        }
    }

    private void dibujarEntradaSalida(Graphics2D g2d) {
        Celda entrada = controlador.getCeldaEntrada();
        if (entrada != null) {
            int eCol = entrada.getColumna();
            int eFila = entrada.getFila();
            g2d.setColor(new Color(0, 180, 0));
            g2d.fillRoundRect(
                    eCol * tamanoCelda + 3,
                    eFila * tamanoCelda + 3,
                    tamanoCelda - 6,
                    tamanoCelda - 6,
                    10, 10
            );
        }

        Celda salida = controlador.getCeldaSalida();
        if (salida != null) {
            int sCol = salida.getColumna();
            int sFila = salida.getFila();
            g2d.setColor(new Color(200, 30, 30));
            g2d.fillRoundRect(
                    sCol * tamanoCelda + 3,
                    sFila * tamanoCelda + 3,
                    tamanoCelda - 6,
                    tamanoCelda - 6,
                    10, 10
            );
        }
    }

    private void dibujarJugador(Graphics2D g2d){
        int x = jugadorColumna * tamanoCelda;
        int y = jugadorFila * tamanoCelda;

        int size = tamanoCelda - 8;

        g2d.setColor(Color.BLUE);
        g2d.fillOval(x + 4, y + 4, size, size);
    }

    public void moverJugador(int dFila, int dCol) {
        int nuevaFila = jugadorFila + dFila;
        int nuevaColumna = jugadorColumna + dCol;

        if(nuevaFila < 0 || nuevaFila >= filas || nuevaColumna < 0 || nuevaColumna >= columnas){
            return;
        }

        if(puedoMover(dFila, dCol)){
            jugadorFila = nuevaFila;
            jugadorColumna = nuevaColumna;
        }

        repaint();

        if(jugadorGano()){
            JOptionPane.showMessageDialog(this, "¡Has llegado a la salida!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean puedoMover(int dFila, int dCol){
        Celda celda = controlador.getLaberinto().getCelda(jugadorFila, jugadorColumna);
        if (celda == null) return false;
        
        boolean[] paredes = celda.getParedes();

        if(dFila == -1 && dCol == 0) return !paredes[0];
        if(dFila == 0 && dCol == 1) return !paredes[1];
        if(dFila == 1 && dCol == 0) return !paredes[2];
        if(dFila == 0 && dCol == -1) return !paredes[3];

        return false;
    }

    private boolean jugadorGano() {
        Celda salida = controlador.getCeldaSalida();
        if (salida == null) return false;
        
        return jugadorFila == salida.getFila() && jugadorColumna == salida.getColumna();
    }
}