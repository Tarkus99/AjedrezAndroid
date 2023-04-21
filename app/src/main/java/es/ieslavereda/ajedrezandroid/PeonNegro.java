package es.ieslavereda.ajedrezandroid;

import java.util.HashSet;
import java.util.Set;

public final class PeonNegro extends Peon {
    private Set<Coordenada> coordenadasKills;

    public PeonNegro(Celda celda) {
        super(PieceType.BLACK_PEON, celda);
        coordenadasKills = new HashSet<>();
    }

    @Override
    public void transform() {
        new ReinaNegra(getCelda());
        celda = null;
    }

    @Override
    public void moveTo(Coordenada nuevaPosición) {
        Movimientos.moveTo(this, nuevaPosición);
        if (celda.getCoordenada().getRow() == 8)
            transform();
    }

    @Override
    public Set<Coordenada> getNextKills() {
        coordenadasKills.clear();
        Coordenada posicion = getCelda().getCoordenada();
        Coordenada c;
        Tablero t = getCelda().getTablero();
        c = posicion.diagoDownLeft();
        verificarCasilla(c, t);
        c = posicion.diagoDownRight();
        verificarCasilla(c, t);
        return coordenadasKills;
    }

    public void verificarCasilla(Coordenada c, Tablero t) {
        if (t.getCelda(c) != null) {
            if (!t.getCelda(c).isEmpty() && t.getCelda(c).getPiece().getColor() != this.getColor()) {
                coordenadasKills.add(c);
            }
        }
    }



    @Override
    public Set<Coordenada> getComplexMoves() {
        coordenadasMovimientos.clear();
        coordenadasKills.clear();
        Coordenada posicion = getCelda().getCoordenada();
        Coordenada c;
        Tablero t = getCelda().getTablero();

        //Up
        c = posicion.casillaDown();
        verificarComplexCasilla(c, t, this.celda);
        //DoubleUp
        if (getCelda().getCoordenada().getRow() == 2) {
            c = posicion.casillaDown().casillaDown();
            verificarComplexCasilla(c, t, this.celda);
        }
        //Matar
        c = posicion.diagoDownLeft();
        verificarComplexMatar(c, t, getCelda());
        c = posicion.diagoDownRight();
        verificarComplexMatar(c, t, getCelda());
        coordenadasMovimientos.addAll(coordenadasKills);
        return coordenadasMovimientos;
    }

    public void verificarComplexCasilla(Coordenada coordenada, Tablero t, Celda celda1) {
        if (t.getCelda(coordenada) != null) {
            if (t.getCelda(coordenada).isEmpty()) {
                Movimientos.moveSinMatar(this, coordenada);
                if (!t.estaJaqueColor(this.getColor())) {
                    coordenadasMovimientos.add(coordenada);
                }
                deshacerMove(celda1.getCoordenada(), coordenada, null);
            }
        }
    }

    public void verificarComplexMatar(Coordenada c, Tablero t, Celda celda1) {
        Pieza provisional;
        if (t.getCelda(c) != null) {
            if (!t.getCelda(c).isEmpty() && t.getCelda(c).getPiece().getColor() != this.getColor()) {
                provisional = t.getCelda(c).getPiece();
                Movimientos.moveSinMatar(this, c);
                if (!t.estaJaqueColor(this.getColor())) {
                    coordenadasKills.add(c);
                }
                deshacerMove(celda1.getCoordenada(), c, provisional);
            }
        }
    }
}
