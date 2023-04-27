package es.ieslavereda.ajedrezandroid;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que gestiona el comportamiento de los alfiles.
 */
public abstract class Alfil extends Pieza {

    public Alfil(Pieza.PieceType p, Celda c){
        super(p, c);
    }

    /**
     * Método para obtener las coordenadas donde se puede mover la pieza como un alfil (diagonal).
     * @param p la pieza que solicita las coordenadas.
     * @return conjunto de coordenadas sin repetición
     */
    public static Set<Coordenada> getNextKillsAsAlfil(Pieza p){
        Set<Coordenada> nextKills = new HashSet<>();
        Celda c = p.getCelda();
        Tablero t = p.getCelda().getTablero();
        Color color = p.getColor();
        //
        Coordenada posicionOriginal = c.getCoordenada();
        Coordenada coordenadaAux;
        //UpLeft
        coordenadaAux=posicionOriginal.diagoUpLeft();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            nextKills.add(coordenadaAux);
            coordenadaAux = coordenadaAux.diagoUpLeft();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            nextKills.add(coordenadaAux);
        }

        //UpRight
        coordenadaAux=posicionOriginal.diagoUpRight();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            nextKills.add(coordenadaAux);
            coordenadaAux = coordenadaAux.diagoUpRight();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            nextKills.add(coordenadaAux);
        }
        //DownLeft
        coordenadaAux=posicionOriginal.diagoDownLeft();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            nextKills.add(coordenadaAux);
            coordenadaAux = coordenadaAux.diagoDownLeft();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            nextKills.add(coordenadaAux);
        }
        //DownRight
        coordenadaAux=posicionOriginal.diagoDownRight();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            nextKills.add(coordenadaAux);
            coordenadaAux = coordenadaAux.diagoDownRight();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            nextKills.add(coordenadaAux);
        }
        return nextKills;
    }

    /**
     * Método para obtener las coordenadas de movimientos como un alfil siempre que la pieza no deje a su rey en jaque.
     * @param p pieza que solicita las coordenadas.
     * @return conjunto de coordenadas sin repetición.
     */
    public static Set<Coordenada> getComplexMovesAsAlfil(Pieza p){
        if (!(p instanceof Reina))
            p.coordenadasMovimientos.clear();
        Celda c = p.getCelda();
        Tablero t = p.getCelda().getTablero();
        Color color = p.getColor();
        Pieza provisional;
        //
        Coordenada posicionOriginal = c.getCoordenada();
        Coordenada coordenadaAux;
        //UpLeft
        coordenadaAux=posicionOriginal.diagoUpLeft();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.diagoUpLeft();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }

        //UpRight
        coordenadaAux=posicionOriginal.diagoUpRight();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p,coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.diagoUpRight();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }
        //DownLeft
        coordenadaAux=posicionOriginal.diagoDownLeft();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.diagoDownLeft();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }
        //DownRight
        coordenadaAux=posicionOriginal.diagoDownRight();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.diagoDownRight();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }
        return p.coordenadasMovimientos;
    }

    @Override
    public void moveTo(Coordenada nuevaPosición){
        Movimientos.moveTo(this, nuevaPosición);
    }

    @Override
    public Set<Coordenada> getComplexMoves() {
        return getComplexMovesAsAlfil(this);
    }

    @Override
    public Set<Coordenada> getNextKills(){
       return getNextKillsAsAlfil(this);
    }

}
