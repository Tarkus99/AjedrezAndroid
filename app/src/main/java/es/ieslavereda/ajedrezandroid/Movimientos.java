package es.ieslavereda.ajedrezandroid;

/**
 * Clase para mover las piezas sobre el tablero.
 */
public class Movimientos {
    /**
     * Método para mover una pieza a una coordenada.
     * @param p pieza que se quiere mover.
     * @param nuevaPosicion coordenada a donde se quiere mover la pieza.
     */
    public static void moveTo(Pieza p, Coordenada nuevaPosicion) {
        Tablero t = p.getCelda().getTablero();
        Celda celda = p.getCelda();
        Pieza enemiga;

        if (t.getCelda(nuevaPosicion).isEmpty()) {
            t.getCelda(celda.getCoordenada()).setPieza(null);
            p.celda = t.getCelda(nuevaPosicion);
            t.getCelda(nuevaPosicion).setPieza(p);
        } else {
            t.getCelda(celda.getCoordenada()).setPieza(null);
            enemiga = t.getCelda(nuevaPosicion).getPiece();
            MainActivity.getVisor(p.getColor()).addPiece(enemiga);
            p.celda = t.getCelda(nuevaPosicion);
            t.getCelda(nuevaPosicion).getPiece().celda = null;
            t.getCelda(nuevaPosicion).setPieza(p);
        }
        t.resetColors();
        celda.resaltarLastMovement();
        t.getCelda(nuevaPosicion).resaltarLastMovement();
    }

    /**
     * Función para mover una pieza superficialmente sin intercambiar piezas ni añadirlas al DeletePieceManager.
     * Útil para verificar si una pieza deja en jaque a su rey al moverse a la nueva coordenada.
     * @param p que se quiere mover.
     * @param nuevaPosicion coordenada a donde se quiere mover la pieza.
     */
    public static void moveSinMatar(Pieza p, Coordenada nuevaPosicion) {
        Tablero t = p.getCelda().getTablero();
        Celda celda = p.getCelda();

        if (t.getCelda(nuevaPosicion).isEmpty()) {
            t.getCelda(celda.getCoordenada()).setPieza(null);
            p.celda = t.getCelda(nuevaPosicion);
            t.getCelda(nuevaPosicion).setPieza(p);
        } else if (t.getCelda(nuevaPosicion).getPiece().getColor() != p.getColor()) {
            t.getCelda(celda.getCoordenada()).setPieza(null);
            p.celda = t.getCelda(nuevaPosicion);
            t.getCelda(nuevaPosicion).getPiece().celda = null;
            t.getCelda(nuevaPosicion).setPieza(p);
        }
    }

    /**
     * Método para efectuar el movimiento de enroque corto. Se obtiene la torre a la derecha del rey
     * para mover ambas piezas sucesivamente.
     * @param p rey.
     */
    public static void enrocarseCorto(Pieza p) {
        Tablero t = p.getCelda().getTablero();
        Coordenada origen = p.getCelda().getCoordenada();
        Coordenada coordenadaTorre;
        Pieza torreCorta = t.getCelda(new Coordenada('h', origen.getRow())).getPiece();
        coordenadaTorre = torreCorta.getCelda().getCoordenada();
        Movimientos.moveTo(p, origen.casillaRight().casillaRight());
        Movimientos.moveTo(torreCorta, coordenadaTorre.casillaLeft().casillaLeft());
    }

    /**
     * Función para efectuar el movimiento de enroque largo. Se obtiene la torre a la izquierda del rey para
     * mover ambas piezas sucesivamente.
     * @param p
     */
    public static void enrocarseLargo(Pieza p) {
        Tablero t = p.getCelda().getTablero();
        Coordenada origen = p.getCelda().getCoordenada();
        Coordenada coordenadaTorre;
        Pieza torreLarga = t.getCelda(new Coordenada('a', origen.getRow())).getPiece();
        coordenadaTorre = torreLarga.getCelda().getCoordenada();
        Movimientos.moveTo(p, origen.casillaLeft().casillaLeft());
        Movimientos.moveTo(torreLarga, coordenadaTorre.casillaRight().casillaRight().casillaRight());
    }
}
