package es.ieslavereda.ajedrezandroid;

public class DynamicColor {

   private int whiteCell;
   private int blackCell;
   private static final DynamicColor INSTANCE = new DynamicColor();

   private DynamicColor(){
       whiteCell = R.color.white;
       blackCell = R.color.black;
   }

    public static DynamicColor getInstance(){
       return INSTANCE;
    }

    public int getWhiteCell() {
        return whiteCell;
    }

    public int getBlackCell() {
        return blackCell;
    }

    public void setWhiteCell(int whiteCell) {
        this.whiteCell = whiteCell;
    }

    public void setBlackCell(int blackCell) {
        this.blackCell = blackCell;
    }
}
