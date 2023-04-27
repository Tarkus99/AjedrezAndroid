package es.ieslavereda.ajedrezandroid;

import java.util.Set;

public abstract class Torre extends Pieza {

    private boolean movido;

    public Torre(Pieza.PieceType pieceType, Celda celda) {
        super(pieceType, celda);
        movido = false;
    }

    @Override
    public void moveTo(Coordenada nuevaPosición, VisorPiezasMuertas visor){
        Movimientos.moveTo(this, nuevaPosición, visor);
        movido = true;
    }

    public boolean seHaMovido(){
        return movido;
    }
    public static Set<Coordenada> getNextKillsAsTorre(Pieza p){
        p.coordenadasMovimientos.clear();
        Celda c = p.getCelda();
        Tablero t = p.getCelda().getTablero();
        Color color = p.getColor();
        //
        Coordenada posicionOriginal = c.getCoordenada();
        Coordenada coordenadaAux;
        //Up
        coordenadaAux=posicionOriginal.casillaUp();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            p.coordenadasMovimientos.add(coordenadaAux);
            coordenadaAux = coordenadaAux.casillaUp();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            p.coordenadasMovimientos.add(coordenadaAux);
        }
        //Down
        coordenadaAux=posicionOriginal.casillaDown();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            p.coordenadasMovimientos.add(coordenadaAux);
            coordenadaAux = coordenadaAux.casillaDown();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            p.coordenadasMovimientos.add(coordenadaAux);
        }
        //Left
        coordenadaAux=posicionOriginal.casillaLeft();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            p.coordenadasMovimientos.add(coordenadaAux);
            coordenadaAux = coordenadaAux.casillaLeft();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            p.coordenadasMovimientos.add(coordenadaAux);
        }
        //Right
        coordenadaAux=posicionOriginal.casillaRight();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            p.coordenadasMovimientos.add(coordenadaAux);
            coordenadaAux = coordenadaAux.casillaRight();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            p.coordenadasMovimientos.add(coordenadaAux);
        }
        return p.coordenadasMovimientos;
    }

    public static Set<Coordenada> getComplexMovesAsTorre(Pieza p){
        p.coordenadasMovimientos.clear();
        Celda c = p.getCelda();
        Tablero t = p.getCelda().getTablero();
        Color color = p.getColor();
        Pieza provisional;
        //
        Coordenada posicionOriginal = c.getCoordenada();
        Coordenada coordenadaAux;
        //Up
        coordenadaAux=posicionOriginal.casillaUp();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.casillaUp();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }
        //Down
        coordenadaAux=posicionOriginal.casillaDown();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
           p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.casillaDown();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }
        //Left
        coordenadaAux=posicionOriginal.casillaLeft();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.casillaLeft();
        }
        if (t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).getPiece().getColor()!=color) {
            provisional = t.getCelda(coordenadaAux).getPiece();
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux, provisional);
        }
        //Right
        coordenadaAux=posicionOriginal.casillaRight();
        while(t.getCelda(coordenadaAux)!=null && t.getCelda(coordenadaAux).isEmpty()) {
            Movimientos.moveSinMatar(p, coordenadaAux);
            if (!t.estaJaqueColor(color)) {
                p.coordenadasMovimientos.add(coordenadaAux);
            }
            p.deshacerMove(c.getCoordenada(), coordenadaAux,null);
            coordenadaAux = coordenadaAux.casillaRight();
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
    public Set<Coordenada> getComplexMoves() {
        return getComplexMovesAsTorre(this);
    }
    @Override
    public Set<Coordenada> getNextKills(){
        return getNextKillsAsTorre(this);
    }
}
