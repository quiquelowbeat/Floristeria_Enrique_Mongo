package tools;

import vista.View;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Keyboard {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * Este método se encarga de leer un String de teclado
     * @param message
     * @return String
     */
    public static String readString(String message) {
        String cadena = "";
        boolean correcto = false;

        do {
            View.showMessage(message);
            try {
                cadena = sc.nextLine();
                correcto = true;
            } catch (Exception ex) {
                System.out.println("Error en la introducción de la string.");
            }
        } while (!correcto);
        return cadena;
    }

    /**
     * Este método se encarga de leer un float de teclado
     * @param message
     * @return float
     */
    public static float readFloat(String message) {
        float numero = 0.0f;
        boolean correcto = false;

        do {
            View.showMessage(message);
            try {
                numero = sc.nextFloat();
                correcto = true;
            } catch (InputMismatchException ex) {
                System.out.println("Error de formato.");
            }
            sc.nextLine();
        } while (!correcto);
        return numero;
    }

    /**
     * Este método se encarga de leer un double de teclado
     * @param message
     * @return double
     */
    public static double readDouble(String message) {
        double numero = 0.0;
        boolean correcto = false;

        do {
            View.showMessage(message);
            try {
                numero = sc.nextDouble();
                correcto = true;
            } catch (InputMismatchException ex) {
                System.out.println("Error de formato.");
            }
            sc.nextLine();
        } while (!correcto);
        return numero;
    }

    /**
     * Este método se encarga de leer un entero de teclado
     * @param message
     * @return int
     */
    public static int readInt(String message) {
        int numero = 0;
        boolean correcto = false;

        do {
            View.showMessage(message);
            try {
                numero = sc.nextInt();
                correcto = true;
            } catch (InputMismatchException ex) {
                System.out.println("Error de formato.");
            }
            sc.nextLine();
        } while (!correcto);
        return numero;
    }

    /**
     * Este método se encarga de leer un byte de teclado
     * @param message
     * @return byte
     */
    public static byte readByte(String message) {
        byte numero = 0;
        boolean correcto = false;

        do {
            View.showMessage(message);
            try {
                numero = sc.nextByte();
                correcto = true;
            } catch (InputMismatchException ex) {
                View.showMessage("FORMAT ERROR");
            }
            sc.nextLine();
        } while (!correcto);
        return numero;
    }

    /**
     * Este método se encarga de leer un char de teclado
     * @param message
     * @return char
     */
    public static char readChar(String message) {
        char caracter = 0;
        boolean correcto = false;

        do {
            View.showMessage(message);
            try {
                caracter = sc.next().charAt(0);// Cogemos el primer carácter en una lectura de cadena
                correcto = true;
            } catch (InputMismatchException ex) {
                View.showMessage("FORMAT ERROR");
            } catch (Exception ex) {
                View.showMessage(ex.toString());
            }
            sc.nextLine();
        } while (!correcto);
        return caracter;
    }

    /**
     * Este método se encarga de leer un boleano de teclado
     * @param message
     * @return boolean
     */
    public static boolean leerSiNo(String message) {
        boolean retorno = false;
        boolean correcto = false;
        String cadena = "";

        do {
            View.showMessage(message);
            try {
                cadena = sc.nextLine().toUpperCase();
                correcto = true;
                if (cadena.charAt(0) == 'Y') {
                    retorno = true;
                } else if (cadena.charAt(0) == 'N') {
                    retorno = false;
                } else {
                    View.showMessage("INPUT NOT VALID");
                    correcto = false;
                }
            } catch (Exception ex) {
                View.showMessage("ERROR INPUT STRING.");
            }
        } while (!correcto);
        return retorno;
    }
}
