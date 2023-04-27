package es.ieslavereda.ajedrezandroid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rey extends Pieza {
    private boolean movido;

    public Rey(Pieza.PieceType p, Celda c) {
        super(p, c);
        movido = false;
    }

    @Override
    public void moveTo(Coordenada nuevaPosición, VisorPiezasMuertas visor) {
        if (movido || !nuevaPosición.esEnroque(getCelda().getCoordenada())) {
            Movimientos.moveTo(this, nuevaPosición, visor);
        } else if (nuevaPosición.esEnroqueCorto(getCelda().getCoordenada())) {
            Movimientos.enrocarseCorto(this);
        } else {
            Movimientos.enrocarseLargo(this);
        }
        movido = true;
    }

    @Override
    public Set<Coordenada> getNextKills() {
        coordenadasMovimientos = new HashSet<>();
        Coordenada posicion = getCelda().getCoordenada();
        Coordenada c;
        Tablero t = getCelda().getTablero();

        //Up
        c = posicion.casillaUp();
        verificarCasillaSinJaque(c, t);
        //Down
        c = posicion.casillaDown();
        verificarCasillaSinJaque(c, t);
        //Left
        c = posicion.casillaLeft();
        verificarCasillaSinJaque(c, t);
        //Right
        c = posicion.casillaRight();
        verificarCasillaSinJaque(c, t);
        //UpRigth
        c = posicion.diagoUpRight();
        verificarCasillaSinJaque(c, t);
        //UpLeft
        c = posicion.diagoUpLeft();
        verificarCasillaSinJaque(c, t);
        //DownRigth
        c = posicion.diagoDownRight();
        verificarCasillaSinJaque(c, t);
        //DownLeft
        c = posicion.diagoDownLeft();
        verificarCasillaSinJaque(c, t);

        return coordenadasMovimientos;
    }

    @Override
    public Set<Coordenada> getComplexMoves() {
        coordenadasMovimientos.clear();
        Coordenada posicion = getCelda().getCoordenada();
        Coordenada c;
        Tablero t = getCelda().getTablero();
        //Up
        c = posicion.casillaUp();
        verificarCasillaConJaque(c, t);
        //Down
        c = posicion.casillaDown();
        verificarCasillaConJaque(c, t);
        //Left
        c = posicion.casillaLeft();
        verificarCasillaConJaque(c, t);
        //Right
        c = posicion.casillaRight();
        verificarCasillaConJaque(c, t);
        //UpRigth
        c = posicion.diagoUpRight();
        verificarCasillaConJaque(c, t);
        //UpLeft
        c = posicion.diagoUpLeft();
        verificarCasillaConJaque(c, t);
        //DownRigth
        c = posicion.diagoDownRight();
        verificarCasillaConJaque(c, t);
        //DownLeft
        c = posicion.diagoDownLeft();
        verificarCasillaConJaque(c, t);
        //
        if (!movido && !coordenadasMovimientos.isEmpty())
            coordenadasMovimientos.addAll(añadirEnroque(t));

        if (!estaEnJaque(getCelda().getCoordenada()))
            coordenadasMovimientos.add(getCelda().getCoordenada());

        return coordenadasMovimientos;
    }

    private Set<Coordenada> añadirEnroque(Tablero t) {
        Set<Coordenada> listaAux = new HashSet<>();
        Coordenada posicion = getCelda().getCoordenada();
        if (!estaEnJaque(posicion)) {

            if (t.getTorreCorta(getColor(), posicion)) {
                if (enroqueCorto()) {
                    listaAux.add(posicion.casillaRight().casillaRight());
                }
            }
            if (t.getTorreLarga(getColor(), posicion)) {
                if (enroqueLargo()) {
                    listaAux.add(posicion.casillaLeft().casillaLeft());
                }
            }
        }
        return listaAux;
    }

    public boolean enroqueCorto() {
        Tablero t = getCelda().getTablero();
        Coordenada posicion = getCelda().getCoordenada();
        Coordenada coordenada = posicion.casillaRight();
        List<Pieza> vectorPiezas = getCelda().getTablero().piezasEnemigas(getColor());
        Set<Coordenada> allMovements = Tools.allMovements(vectorPiezas);
        int i = 0;
        while (i < 2) {
            if (!t.getCelda(coordenada).isEmpty() || allMovements.contains(coordenada))
                return false;
            coordenada = coordenada.casillaRight();
            i++;
        }
        return true;
    }

    public boolean enroqueLargo() {
        Tablero t = getCelda().getTablero();
        Coordenada coordenada = getCelda().getCoordenada();
        coordenada = coordenada.casillaLeft();
        List<Pieza> vectorPiezas = getCelda().getTablero().piezasEnemigas(getColor());
        Set<Coordenada> allMovements = Tools.allMovements(vectorPiezas);
        int i = 0;
        while (i < 3) {
            if (!t.getCelda(coordenada).isEmpty() || allMovements.contains(coordenada))
                return false;
            coordenada = coordenada.casillaLeft();
            i++;
        }
        return true;
    }

    private void verificarCasillaSinJaque(Coordenada coordenada, Tablero t) {

        if (t.getCelda(coordenada) != null) {
            if
            (t.getCelda(coordenada).isEmpty()
                    ||
                    (!t.getCelda(coordenada).isEmpty() && t.getCelda(coordenada).getPiece().getColor() != this.getColor())) {
                coordenadasMovimientos.add(coordenada);
            }
        }
    }

    private void verificarCasillaConJaque(Coordenada coordenada, Tablero t) {
        Coordenada origen = getCelda().getCoordenada();
        Pieza prov;

        if (t.getCelda(coordenada) != null) {
            if (!t.getCelda(coordenada).isEmpty()) {
                prov = t.getCelda(coordenada).getPiece();
                if (prov.getColor() != this.getColor()) {
                    Movimientos.moveSinMatar(this, coordenada);
                    if (!estaEnJaque(getCelda().getCoordenada()))
                        coordenadasMovimientos.add(coordenada);
                    deshacerMove(origen, coordenada, prov);
                }
            } else {
                Movimientos.moveSinMatar(this, coordenada);
                if (!estaEnJaque(getCelda().getCoordenada()))
                    coordenadasMovimientos.add(coordenada);
                deshacerMove(origen, coordenada, null);
            }
        }
    }

    public boolean estaEnJaque(Coordenada c) {
        List<Pieza> vectorPiezas = getCelda().getTablero().piezasEnemigas(getColor());
        Set<Coordenada> allMovements = Tools.allMovements(vectorPiezas);
        return allMovements.contains(c);
    }

    public boolean mate() {
        return getCelda().getTablero().movesAliadosVacios(getColor()) && estaEnJaque(getCelda().getCoordenada());
    }

    public boolean tablas(Coordenada coordenada) {
        return getCelda().getTablero().movesAliadosVacios(getColor()) && !estaEnJaque(coordenada);
    }
}
