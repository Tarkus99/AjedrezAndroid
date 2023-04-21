//package es.ieslavereda.ajedrezandroid;
//
//import es.ieslavereda.ajedrezandroid.Jugador.Jugador;
//import es.ieslavereda.model.Jugador.Jugador;
//import com.diogonunes.jcolor.Attribute;
//
//import java.io.Serializable;
//import java.util.*;
//
//import static com.diogonunes.jcolor.Ansi.colorize;
//
///**
// * Clase encargada de gestionar las muertes y la puntuación durante la partida.
// */
//public class DeletePieceManager implements IDeletePieceManager, Serializable {
//
//    public static final Attribute a = Attribute.BACK_COLOR(57, 81, 68);
//    public static final Attribute b = Attribute.BACK_COLOR(164, 126, 59);
//    /**
//     * Lista de Piezas que almacena las piezas eliminadas durante la partida.
//     */
//    private List<Pieza> listaMuertes;
//    private Partida partida;
//
//    /**
//     * Constructor del gestor de piezas.
//     * @param p partida que gestiona.
//     */
//    public DeletePieceManager(Partida p) {
//        this.partida = p;
//        listaMuertes = new LinkedList<>();
//    }
//
//    /**
//     * Método para obtener la diferencia de puntos entre dos jugadores.
//     * @return número entero positivo.
//     */
//    public int diferenciaPuntos(){
//        int a = puntosPorColor(Color.BLACK);
//        int b = puntosPorColor(Color.WHITE);
//        return Math.abs(a-b);
//    }
//
//    /**
//     * Método que devuelve la cantidad de puntos de un jugador sumando todos los puntos de las piezas
//     * que ha ganado a su rival.
//     * @param color color con que juega un jugador.
//     * @return total de puntos de las piezas que son distintas al parámetro 'color' (piezas enemigas tomadas).
//     */
//    public int puntosPorColor(Color color){
//        int sum = 0;
//        for (Pieza p:listaMuertes) {
//            if(p.getColor()!=color)
//                sum+=p.getValor();
//        }
//        return sum;
//    }
//
//    /**
//     * Método para saber si la diferencia de puntos de un jugador es positiva o negativa.
//     * @param color color con que juega un jugador.
//     * @return <ul><li><b>True:</b> si la puntuación del jugador con el color 'color' es mayor a la
//     * puntuación del jugador contrario.</li>
//     * <li><b>False:</b> si la puntuación del jugador con el color 'color' es meor
//     * a la puntuación del jugador contrario.</li></ul>
//     */
//    public boolean miPuntEsMayor(Color color){
//        int miPuntuacion;
//        int suPuntuacion;
//        miPuntuacion= puntosPorColor(color);
//        suPuntuacion= puntosPorColor(color.next());
//        return miPuntuacion>suPuntuacion;
//    }
//
//    /**
//     * Añade una pieza a la lista de piezas eliminadas.
//     * @param p pieza eliminada.
//     */
//    @Override
//    public void addPiece(Pieza p) {
//        listaMuertes.add(p);
//    }
//
//    /**
//     * Método para contar el total de piezas de cierto tipo.
//     * @param pieceType tipo de pieza que queremos contar (caballo blanco, alfil negro,...).
//     * @return total de piezas del mismo tipo que la pieza recibida por parámetro.
//     */
//    @Override
//    public int count(Pieza.PieceType pieceType) {
//        int i = 0;
//        for (Pieza p : listaMuertes) {
//            if (p.getType() == pieceType) {
//                i++;
//            }
//        }
//        return i;
//    }
//
//    /**
//     * Método para eliminar la última pieza añadida a la lista de piezas.
//     * @return
//     */
//    @Override
//    public Pieza getLast() {
//        return listaMuertes.remove(listaMuertes.size() - 1);
//    }
//
//    @Override
//    public String toString() {
//        String str = "";
//        Jugador j = partida.jugadorPorColor(Color.WHITE);
//
//        str+=outDelet1(j);
//        j = partida.nextJugador(j);
//        str+=outDelet1(j);
//        return str;
//    }
//
//    /**
//     * Método para mostrar por pantalla el gestor de piezas de cada jugador.
//     * @param j jugador
//     * @return string
//     */
//    private String outDelet1(Jugador j){
//        int i, q, k;
//        if (j.getColor()==Color.WHITE){
//            q = 0;
//            k = 6;
//        }else{
//            q = 6;
//            k = 12;
//        }
//        String str = "     ";
//        str+=j + " " + (miPuntEsMayor(j.getColor())? "(+" + diferenciaPuntos() +")": "") +"\n";
//
//        Pieza.PieceType p;
//        str+="     ";
//        i = q;
//        while (i<k) {
//            p = Pieza.PieceType.values()[i];
//            str +=  colorize(" ", a)
//                    + colorize(String.valueOf(p), p.color.getColor(), a)
//                    + colorize(" ", a);
//            i++;
//        }
//        str += "\n     ";
//        i=q;
//        while (i<k){
//            str += colorize(" ", b) +
//                    colorize(String.valueOf(count(Pieza.PieceType.values()[i++])), b, Attribute.TEXT_COLOR(255, 255, 255)) +
//                    colorize(" ", b);
//        }
//        str += "\n";
//        return str;
//    }
//}
