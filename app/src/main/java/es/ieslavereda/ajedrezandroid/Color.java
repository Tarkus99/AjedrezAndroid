package es.ieslavereda.ajedrezandroid;

import com.diogonunes.jcolor.Attribute;

/**
 * Clase dónde guardo el color de las piezas.
 */
public enum Color {
    BLACK(Attribute.TEXT_COLOR(0, 0, 0), "negras"),
    WHITE(Attribute.TEXT_COLOR(255,255,255), "blancas");
    private Attribute color;
    private String str;

    /**
     * Constructor de Color.
     * @param color Atributo en formato rgb.
     * @param str texto descrptivo del color.
     */
    Color(Attribute color, String str){
        this.color = color; this.str=str;
    }

    /**
     * Getter del atributo color.
     * @return Atributo
     */
    public Attribute getColor() {
        return color;
    }

    /**
     * Devuelve el elemento siguiente de la enumeración.
     * @return Color
     */
    public Color next(){
        if (this.equals(WHITE))
            return BLACK;
        return WHITE;
    }
    @Override
    public String toString(){
        return str;
    }
}
