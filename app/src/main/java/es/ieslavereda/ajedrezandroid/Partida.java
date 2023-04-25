//package es.ieslavereda.ajedrezandroid;
//
//import android.content.Context;
//
//import es.ieslavereda.ajedrezandroid.Jugador.Jugador;
//import es.ieslavereda.ajedrezandroid.Timer.IClock;
//import es.ieslavereda.ajedrezandroid.Timer.Timer;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// * Clase que gestiona la partida y cuándo y cómo debe pararse.
// */
//public class Partida implements Serializable, IClock  {
//    private static final int CONTINUAR = 0;
//    private static final int GUARDAR = 1;
//    private static final int RENDIRSE = 2;
//    /**
//     * Indica si se ha parado la partida. Una partida puede pararse porque uno de los jugadores se rinde o porqueç
//     * uno de los jugadores ha guardado la partida.
//     */
//    private boolean pararPartida = false;
//    /**
//     * Indica si alguno de los relojes de los jugadores ha llegado a 0.
//     */
//    private boolean fueraDeTiempo = false;
//    /**
//     * Indica si se ha producido jaque mate. La partida se para automáticamente.
//     */
//    private boolean mate = false;
//    /**
//     * Indica si se han producido tablas.
//     */
//    private boolean tablas = false;
//    /**
//     * Variable donde guardo el valor que devuelve el usuario tras jugar su turno.
//     * Este valor puede ser 1 (GUARDAR), 2 (RENDIRSE), ó 0 (CONTINUAR).
//     */
//    private int valorDelTurno = 0;
//    private List<Jugador> listaJugadores;
//    private Tablero tablero;
//    /**
//     * Indica que color debe jugar el turno. Su valor cambia continuamente.
//     */
//    private Color turno;
//    /**
//     * Indica de cuántos minutos será la partida.
//     */
//    private int tiempo;
//
//    /**
//     * Constructor de una nueva partida.
//     */
//    public Partida(Context context) {
//        fueraDeTiempo = false;
//        tablero = new Tablero(context, null);
//        listaJugadores = new ArrayList<>();
//        turno = Color.WHITE;
//    }
//
//    /**
//     * Constructor de una partida guardada.
//     * @param path nombre del archivo donde está guardada la partida.
//     */
//    public Partida(String path) {
//        cargarPartida(path);
//    }
//
//    public List<Jugador> getListaJugadores() {
//        return listaJugadores;
//    }
//
//    public Tablero getTablero() {
//        return tablero;
//    }
//
//    public Color getTurno() {
//        return turno;
//    }
//
//    /**
//     * Cuando se crea una partida nueva, ésta se inicializa, preguntando el tiempo, el nombre de los jugadores
//     * e informando de la asignación de colores.
//     * @return
//     */
//    public String inicializarPartida() {
//        tiempo = Tools.getTiempo();
//        String txt = "Introduce el nombre de jugador ";
//        Color col = Tools.randColor();
//        listaJugadores.add(new Jugador(Tools.getText(txt + 1), col, tablero, new Timer(tiempo, this)));
//        listaJugadores.add(new Jugador(Tools.getText(txt + 2), col.next(), tablero, new Timer(tiempo, this)));
//        listaJugadores.sort(Jugador.SORT_BY_COLOR);
//        return listaJugadores.get(0) + " juega con blancas.\n" + listaJugadores.get(1) + " juega con negras.\n";
//    }
//
//    /**
//     * Método que devuelve el jugador inmediatamente siguiente al pasado por parámetro.
//     * @param j jugador actual.
//     * @return jugador siguiente al actual.
//     */
//    public Jugador nextJugador(Jugador j) {
//        int i = (listaJugadores.indexOf(j) + 1) % listaJugadores.size();
//        return listaJugadores.get(i);
//    }
//
//    /**
//     * Método para obtener un jugador a partir de un color.
//     * @param c color del jugador que queremos obtener.
//     * @return jugador de color 'c'.
//     */
//    public Jugador jugadorPorColor(Color c) {
//        if (c == Color.WHITE)
//            return listaJugadores.get(0);
//        else
//            return listaJugadores.get(1);
//    }
//
//    /**
//     * Bucle al cual entra la partida una vez se empieza a jugar. Gestiona los turnos y verifica tras cada turno
//     * que no se ha producido jaque mate, no se han producido tablas, no se ha acabado el tiempo de ningún jugador y
//     * no se ha parado la partida.
//     */
//    public void jugar() {
//        Jugador j = jugadorPorColor(turno);
//        do {
//            j.getReloj().start();
//            valorDelTurno = turnoUser();
//            j.getReloj().stop();
//            pararPartida = valorDelTurno == GUARDAR || valorDelTurno == RENDIRSE;
//            if (!pararPartida && !fueraDeTiempo) {
//                j = nextJugador(j);
//                turno = turno.next();
//                mate = getTablero().estaMateColor(j.getColor());
//                tablas = getTablero().hayTablasColor(j.getColor());
//            }
//        } while (!mate && !tablas && !pararPartida && !fueraDeTiempo);
//        //direccionTablero(turno);
//    }
//
//    /**
//     * Método para obtener el resultado de la partida, en función del motivo que haya provocado la salida
//     * del bucle jugar().
//     * @return string
//     */
//    public String resultadoPartida() {
//        if (mate) {
//            if (turno == Color.BLACK) {
//                return "Ganan blancas por jaque mate.\n\n";
//            } else {
//                return "Ganan negras por jaque mate.\n\n";
//            }
//        } else if (pararPartida) {
//            if (valorDelTurno == GUARDAR) {
//                guardarPartida(Tools.getText("Introduce el nombre del archivo."));
//                return "¡Hasta pronto!\n\n";
//            } else
//                return jugadorPorColor(turno) + " se ha rendido. Ganan " + turno.next().toString() + "\n\n";
//        } else if (fueraDeTiempo) {
//            return jugadorPorColor(turno) + " se ha quedado sin tiempo.\n\n";
//        } else return "Empate por tablas.\n\n";
//    }
//
//    /**
//     * Método que permite al jugador elegir una pieza para mover, guardar la partida o rendirse.
//     * Se pedirá al jugador que elija una nueva pieza siempre y cuando la pieza escogida no pueda moverse por el
//     * contexto de la partida o siempre que la nueva coordenada no se encuentre entre los posibles movimientos de
//     * la pieza escogida.
//     * @return valor númerico, pudiendo ser éste un 1 (GUARDAR), 2 (RENDIRSE) o 0 (CONTINUAR).
//     */
//    public int turnoUser() {
//        Coordenada coordenada;
//        Pieza piezaUsuario;
//        boolean piezaSeleccionadaNoPuedeMoverse;
//        boolean movimientosPiezaContieneCoordenada;
//        Set<Coordenada> complexMoves;
//        do {
//            movimientosPiezaContieneCoordenada = true;
//            //direccionTablero(turno);
//            do {
//                coordenada = Tools.selectPieza(tablero, turno);
//                if (coordenada == null)
//                    return GUARDAR;
//                piezaUsuario = tablero.getCelda(coordenada).getPiece();
//                complexMoves = piezaUsuario.getComplexMoves();
//                piezaSeleccionadaNoPuedeMoverse = complexMoves.isEmpty();
//                if (piezaSeleccionadaNoPuedeMoverse)
//                    System.out.println("Esa pieza no la puedes mover en este momento.");
//            } while (piezaSeleccionadaNoPuedeMoverse);
//
//            tablero.getCelda(coordenada).resaltarLastMovement();
//            tablero.resaltarTablero(complexMoves);
//            //direccionTablero(turno);
//            coordenada = Tools.selectMove(tablero, complexMoves, coordenada);
//            if (coordenada == null) movimientosPiezaContieneCoordenada = false;
//        } while (!movimientosPiezaContieneCoordenada);
//
//        if (coordenada.equals(piezaUsuario.getCelda().getCoordenada())) {
//            return RENDIRSE;
//        }
//        piezaUsuario.moveTo(coordenada, );
//        return CONTINUAR;
//    }
//
//    /**
//     * Método para guardar el tablero, los jugadores y el turno en un archivo externo.
//     * @param path nombre del nuevo archivo.
//     */
//    public void guardarPartida(String path) {
//        try (FileOutputStream f = new FileOutputStream(path);
//             ObjectOutputStream out = new ObjectOutputStream(f)) {
//            out.writeObject(tablero);
//            out.writeObject(listaJugadores);
//            out.writeObject(turno);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            System.out.println("La partida se ha guardado en " + path + ".");
//        }
//    }
//
//    /**
//     * Método para cargar los atributos de una partida cargada en una nueva partida.
//     * @param path nombre del archivo que se desea guardar.
//     */
//    public void cargarPartida(String path) {
//        try (FileInputStream f = new FileInputStream(path);
//             ObjectInputStream inp = new ObjectInputStream(f)) {
//
//            tablero = (Tablero) inp.readObject();
//            listaJugadores = (List<Jugador>) inp.readObject();
//            turno = (Color) inp.readObject();
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("No se ha encontrado el archivo.");
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } finally {
//            System.out.println("Se ha cargado la partida '" + path + "'.");
//        }
//    }
//
//    /**
//     * Método que se dispara cuando el reloj de alguno de los jugadores llega a 0.
//     */
//    @Override
//    public void finish() {
//        System.out.println("TIME OUT!");
//        fueraDeTiempo = true;
//    }
//
//    /**
//     * Método que llama a la clase Screen para mostrar por pantalla el tablero, dependiendo del color del turno.
//     * @param c color del turno. (Si es blanco es porque juegan blancas).
//     */
////    public void direccionTablero(Color c) {
////        if (c == Color.WHITE)
////            Screen.tableroDefault(this, tablero);
////        else {
////            Screen.tableroReverse(this, tablero);
////        }
////    }
//
//    /**
//     * Pequeño menú que ofrece al usuario la posibilidad de elegir entre dos opciones: empezar una nueva partida
//     * o cargar una ya existente. En el segundo caso, el bucle se ejecutará mientras el nombre del archivo introducido
//     * no exista.
//     * @return nueva partida si se elige la opción 1, partida cargada a partir de una archivo si se elige la opción 2 y
//     * null si el valor de la variable opcion es igual a 0.
//     */
////    public static Partida medMenu() {
////        File archivo;
////        while (true) {
////            int opcion = Tools.littleMenu();
////            switch (opcion) {
////                case 1:
////                    // Crear una nueva partida
////                    return new Partida();
////                case 2:
////                    // Cargar una partida guardada
////                    archivo = new File(Tools.getText("Indica el nombre de la partida guardada."));
////                    if (!archivo.exists()) {
////                        System.out.println("El archivo no existe");
////                    } else {
////                        return new Partida(archivo.toString());
////                    }
////                    break;
////                default:
////                    // Opción inválida
////                    return null;
////            }
////        }
////    }
//}
