package es.ieslavereda.ajedrezandroid;

import java.util.Set;

public abstract class Peon extends Pieza {

    public Peon(Pieza.PieceType p, Celda c) {
        super(p, c);
    }



    public abstract void transform();
   /* public abstract ListaCoord getNextMoves();*/
    public abstract Set<Coordenada> getComplexMoves();
    public abstract Set<Coordenada> getNextKills();
}
