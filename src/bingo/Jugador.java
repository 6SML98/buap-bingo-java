package bingo;
import Jpmi.*;

public class Jugador implements Proceso {
    private CanalSimple canalSalida;
    private int numero;
    private String nombre;

    public Jugador(String nombre, int numero, CanalSimple canalSalida) {
        this.nombre = nombre;
        this.numero = numero;
        this.canalSalida = canalSalida;
    }

    public void run() {
        System.out.println(nombre + " eligio el numero " + numero);
        new Escribe(canalSalida, numero).run();
    }
}

