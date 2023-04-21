package es.ieslavereda.ajedrezandroid;

import java.util.Set;

/**
 * Clase abstracta que gestiona el comportamiento del caballo.
 */
public abstract class Caballo extends Pieza {

    public Caballo(PieceType pieceType, Celda celda) {
        super(pieceType, celda);
    }

    /**
     * Método para mover esta pieza a una nueva coordenada.
     * @param nuevaPosición nueva coordenada dónde mover la pieza.
     */
    @Override
    public void moveTo(Coordenada nuevaPosición){
        Movimientos.moveTo(this, nuevaPosición);
    }

    /**
     * Método para obtener todas las coordenadas donde puede moverse la pieza sin restricciones.
     * Útil para determinar si el la coordenada del rey coincide con alguna coordenada de las piezas enemigas.
     * @return conjunto de coordenadas sin repetición.
     */
    @Override
    public Set<Coordenada> getNextKills() {
        coordenadasMovimientos.clear();
        Coordenada posicion = getCelda().getCoordenada();

        Coordenada c;
        //Up
        c = posicion.casillaUp().casillaUp().casillaLeft();
        verificarCasillas(c);
        c = posicion.casillaUp().casillaUp().casillaRight();
        verificarCasillas(c);
        //Down
        c = posicion.casillaDown().casillaDown().casillaLeft();
        verificarCasillas(c);
        c = posicion.casillaDown().casillaDown().casillaRight();
        verificarCasillas(c);
        //Left
        c = posicion.casillaLeft().casillaLeft().casillaDown();
        verificarCasillas(c);
        c = posicion.casillaLeft().casillaLeft().casillaUp();
        verificarCasillas(c);
        //Right
        c = posicion.casillaRight().casillaRight().casillaUp();
        verificarCasillas(c);
        c = posicion.casillaRight().casillaRight().casillaDown();
        verificarCasillas(c);

        return coordenadasMovimientos;
    }

    /**
     * Método para obtener las coordenadas donde puede moverse la pieza sin dejar a su rey en jaque.
     * Útil para mostrar al usuario donde puede mover su pieza en el contexto de la partida.
     * @return conjunto de coordenadas sin repetición.
     */
    @Override
    public Set<Coordenada> getComplexMoves() {
        coordenadasMovimientos.clear();
        Coordenada posicion = getCelda().getCoordenada();

        Coordenada c;
        //Up
        c = posicion.casillaUp().casillaUp().casillaLeft();
        verificarComplexCasillas(c);
        c = posicion.casillaUp().casillaUp().casillaRight();
        verificarComplexCasillas(c);
        //Down
        c = posicion.casillaDown().casillaDown().casillaLeft();
        verificarComplexCasillas(c);
        c = posicion.casillaDown().casillaDown().casillaRight();
        verificarComplexCasillas(c);
        //Left
        c = posicion.casillaLeft().casillaLeft().casillaDown();
        verificarComplexCasillas(c);
        c = posicion.casillaLeft().casillaLeft().casillaUp();
        verificarComplexCasillas(c);
        //Right
        c = posicion.casillaRight().casillaRight().casillaUp();
        verificarComplexCasillas(c);
        c = posicion.casillaRight().casillaRight().casillaDown();
        verificarComplexCasillas(c);

        return coordenadasMovimientos;
    }

    /**
     * Verifica que la coordenada esté dentro del tablero, que la celda de la coordenada esté vacía y, si no lo está,
     * que la pieza en la celda de la coordenada sea de color distinto.
     * @param c coordenada donde la pieza podría moverse.
     */
    private void verificarCasillas(Coordenada c) {
        Tablero t = getCelda().getTablero();

        if (t.getCelda(c) != null) {
            if (t.getCelda(c).isEmpty()) {
                coordenadasMovimientos.add(c);
            } else {
                if (t.getCelda(c).getPiece().getColor() != this.getColor())
                    coordenadasMovimientos.add(c);
            }
        }
    }

    /**
     * Verifica que la coordenada esté dentro del tablero, que la celda de la coordenada esté vacía y, si no lo está,
     * que la pieza en la celda de la coordenada sea de color distinto, y que al mover la pieza a esa coordenada, el rey
     * de la pieza no quede en jaque.
     * @param c coordenada donde la pieza podría moverse.
     */
    private void verificarComplexCasillas(Coordenada c) {
        Tablero t = getCelda().getTablero();
        Pieza provisional;
        Celda celda = this.celda;

        if (t.getCelda(c) != null) {
            if (t.getCelda(c).isEmpty()) {
                Movimientos.moveSinMatar(this, c);
                if (!t.estaJaqueColor(this.getColor())) {
                    coordenadasMovimientos.add(c);
                }
                deshacerMove(celda.getCoordenada(), c, null);
            } else {
                provisional = t.getCelda(c).getPiece();
                if (t.getCelda(c).getPiece().getColor() != this.getColor()) {
                    Movimientos.moveSinMatar(this, c);
                    if (!t.estaJaqueColor(this.getColor())) {
                        coordenadasMovimientos.add(c);
                    }
                    deshacerMove(celda.getCoordenada(), c, provisional);
                }
            }
        }
    }
}

