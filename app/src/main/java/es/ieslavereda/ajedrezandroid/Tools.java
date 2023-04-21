package es.ieslavereda.ajedrezandroid;

import java.util.*;
import java.util.Scanner;

public class Tools {
    public static Scanner sc = new Scanner(System.in);
    public static final String  SI = "si";
    public static final String  NO = "no";
    public static final String guardar = "guardar";
    public static final String cancelar = "cancelar";

    public static Set<Coordenada> allMovements(List<Pieza> vectorPiezas) {  //Devuelve todas las posiciones donde puedan moverse las piezas enemigas sin repetición
        Set<Coordenada> listaMovesCadaPieza;
        Set<Coordenada> allMovements = new HashSet<>();
        for (Pieza p : vectorPiezas) {
            listaMovesCadaPieza = p.getNextKills();
            allMovements.addAll(listaMovesCadaPieza);
        }
        return allMovements;
    }

//    public static String getText(String text) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println(text);
//        String str;
//        str = sc.nextLine();
//        return str;
//    }

    public static int getTiempo() {
        int tiempo = 0;
        boolean entradaValida = false;
            while (!entradaValida) {
                System.out.println("Introduce un número natural para el tiempo.");
                try {
                    tiempo = sc.nextInt();
                    if (tiempo <= 0) {
                        System.out.println("Número no válido.");
                    } else {
                        entradaValida = chooseSN();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Eso no es un número natural.");
                    sc.next();
                }
        }
        return tiempo;
    }

    public static boolean chooseSN(){
        String str = getText("¿Quieres confirmar el tiempo?[Si|No]");
        while (!str.equalsIgnoreCase(SI) && !str.equalsIgnoreCase(NO)){
            str = getText("Perdona, no te he entendido.\n¿Quieres confirmar el tiempo?[Si|No]");
        }
        return str.equalsIgnoreCase(SI);
    }

    public static String getText(String text) {
        Scanner jaja = new Scanner(System.in);
        System.out.println(text);
        String str;
        str = jaja.nextLine();
        return str;
    }

    public static String formato(String text, String flag) {
        String str = Tools.getText(text);
        while ((str.length() != 2 || !Character.isLetter(str.charAt(0)) ||
                !Character.isDigit(str.charAt(1))) && !str.equalsIgnoreCase(flag)) {
            str = Tools.getText("Formato no válido. Prueba otra vez. \n" + text);
        }
        return str;
    }

    public static Coordenada selectPieza(Tablero t, Color color) {
        Coordenada c;
        String txt = Mensajes.moverPieza();
        String str;
        do {
            str=formato(txt, guardar);
            if (str.equalsIgnoreCase(guardar))
                return null;
            c = convertirStringACoordenada(str);
            txt="Parece que te has equivocado.\n"+Mensajes.moverPieza();
        } while (t.getCelda(c) == null || t.getCelda(c).isEmpty() || t.getCelda(c).getPiece().getColor() != color);
        return c;
    }

    public static Coordenada selectMove(Tablero t, Set<Coordenada> set, Coordenada selectedPiece) {
        boolean flag;
        Coordenada c;
        String str;
        String txt = Mensajes.selectMov();
        do {
            flag=true;
            str=formato(txt, cancelar);
            if (str.equalsIgnoreCase(cancelar)) {
                t.resetColors();
                return null;
            }
            c = convertirStringACoordenada(str);
            if (t.getCelda(c) == null || !set.contains(c)) {
                System.out.println(t.getCelda(selectedPiece).getPiece().decirNombre() + " no puede moverse a esa posición.\n");
                flag=false;
            }
        }while (!flag);
        return c;
    }

    public static Coordenada convertirStringACoordenada(String text){
        return new Coordenada(text.charAt(0), Character.getNumericValue(text.charAt(1)));
    }

    public static int littleMenu() {
        Scanner sc = new Scanner(System.in);
        int numero;
        while (true) {
            System.out.println(Mensajes.menu());
            if (sc.hasNextInt()) {
                numero = sc.nextInt();
                if (numero == 1 || numero == 2 || numero == 0) {
                    return numero;
                }
            }
            System.out.println("Opción no válida.");
            sc.nextLine();
        }
    }

    public static Color randColor() {
        int rand;
        rand = (int) (Math.random() * 2);
        if (rand == 1) return Color.WHITE;
        else return Color.BLACK;
    }
}
