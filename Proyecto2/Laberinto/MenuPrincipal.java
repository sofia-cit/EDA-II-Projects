import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame implements ActionListener {
    //botones
    private JButton iniciarJuego;
    private JButton instrucciones;
    private JButton salir;
    //Menu desplegable
    private final JComboBox<String> selectorTamanoFila;
    private final JComboBox<String> selectorTamanoColumna;
    private final JComboBox<String> selectorDificultad;

    //Opciones predefinidas
    
    private static final String[] TAMANOS={"10","20","30","40","50"};
    private static final String[] DIFICULTADES={"Facil","Medio","Dificil"};
    int filas=10;
    int columnas=10;



    //Dimensiones de la ventana
    private final int ANCHO = 400;
    private final int ALTO = 500;
    
    int porcentaje = 15; //Porcentaje por defecto
    private final Generador controlador;

    public MenuPrincipal(Generador controlador) {
        this.controlador=controlador;

        
        //Configuracion de la ventana
        setTitle("Menu Principal - Laberinto"); // Titulo de la ventana
        setSize(ANCHO, ALTO); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Cerrar aplicacion al cerrar ventana
        setResizable(false);//No permitir redimensionar ventana
        setLocationRelativeTo(null);//Centrar ventana

        //Inicializar selectores
        selectorTamanoFila = new JComboBox<>(TAMANOS);
        selectorTamanoColumna = new JComboBox<>(TAMANOS);
        selectorDificultad= new JComboBox<>(DIFICULTADES);


        //Inicializar botones
        iniciarJuego = new JButton("Iniciar Juego");
        instrucciones = new JButton("Instrucciones");
        salir = new JButton("Salir");

        selectorTamanoFila.setSelectedItem("10");
        selectorTamanoColumna.setSelectedItem("10");


        //Agregar ActionListener a los botones
        iniciarJuego.addActionListener(this);
        instrucciones.addActionListener(this);
        salir.addActionListener(this);

        //Panel para tamaños
        JPanel panelDimension = new JPanel();
        panelDimension.setLayout(new GridLayout(1, 2, 5, 0));
        JPanel grupoFilas=new JPanel(new BorderLayout());
        grupoFilas.add(selectorTamanoFila,BorderLayout.CENTER);
        JPanel grupoColumnas=new JPanel(new BorderLayout());
        grupoColumnas.add(selectorTamanoColumna,BorderLayout.CENTER);

        panelDimension.add(grupoFilas);
        panelDimension.add(grupoColumnas);

       //Panel Menu principal
       JPanel panelMenu = new JPanel();
       panelMenu.setLayout(new GridLayout(8, 1, 15, 15));
       panelMenu.setBorder(BorderFactory.createEmptyBorder(30,50,30,50));
       // Fila 1: Título principal
        panelMenu.add(new JLabel("<html><h1>MENÚ LABERINTO</h1></html>", SwingConstants.CENTER));
        panelMenu.add(new JLabel("Seleccionar Dimensiones:", SwingConstants.CENTER));
        panelMenu.add(panelDimension);
        panelMenu.add(new JLabel("Seleccionar Dificultad:", SwingConstants.CENTER));
        panelMenu.add(selectorDificultad);
        panelMenu.add(iniciarJuego);
        panelMenu.add(instrucciones);
        panelMenu.add(salir);
        

        add(panelMenu, BorderLayout.CENTER);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("Iniciar Juego")){
            try{
                String filasStr = (String) selectorTamanoFila.getSelectedItem();
                this.filas=Integer.parseInt(filasStr);

                String columnasStr = (String) selectorTamanoColumna.getSelectedItem();
                this.columnas=Integer.parseInt(columnasStr);

            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Error de dimensiones", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

        

        String dificultadStr=(String) selectorDificultad.getSelectedItem();

        switch (dificultadStr) {
            case "Facil":
                //Preguntas y enemigos faciles
                break;
            case "Medio":
                //Preguntas y enemigos medio
                break;
            case "Dificil":
                //Preguntas y enemigos dificil
                break;
        
            default:
                break;
        }
        this.controlador.generarLaberinto(this.filas, this.columnas);
        JFrame ventanaJuego = new JFrame("Laberinto " + filas + "x" + columnas);
        PanelLaberinto panelJuego = new PanelLaberinto(controlador, filas, columnas);
        
        ventanaJuego.add(panelJuego);
        ventanaJuego.pack(); // Ajusta el tamaño de la ventana al panel
        ventanaJuego.setLocationRelativeTo(null); // Centrar la ventana
        ventanaJuego.setVisible(true);
        
        this.dispose(); // Opcional: Cierra la ventana del menú principal

        JOptionPane.showMessageDialog(this,"Laberinto", dificultadStr,JOptionPane.INFORMATION_MESSAGE);

        //Llamada al generador
        //controlador.generarLaberinto(filas,columnas);
        //controlador.agregarCaminos(porcentajes,filas,columnas);
        //ventana laberinto
    }else if (comando.equals("Instrucciones")){

        JOptionPane.showMessageDialog(this,"Encuentra la salida del laberinto","Instrucciones",JOptionPane.INFORMATION_MESSAGE);
    }else if(comando.equals("Salir")){

        System.exit(0);
    }

       
    }


    
    // Método main para probar la interfaz
    public static void main(String[] args) {
        // En un entorno real, pasarías la instancia de Generador aquí
        Generador generador=new Generador();
        SwingUtilities.invokeLater(() -> new MenuPrincipal(generador)); 
    }
}