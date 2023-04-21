package es.ieslavereda.ajedrezandroid;

import java.io.Serializable;

public interface IDeletePieceManager {

    void addPiece(Pieza p);
    int count(Pieza.PieceType pieceType);
    Pieza getLast();
}
