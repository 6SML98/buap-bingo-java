package bingo;
import Jpmi.*;
import java.util.Random;

public class Sorteo implements Proceso {
    private CanalSimple canalSalida;

    public Sorteo(CanalSimple canalSalida) {
        this.canalSalida = canalSalida;
    }

    public void run() {
        int numeroGanador = new Random().nextInt(20) + 1;
        System.out.println("Sorteo genero el numero ganador: " + numeroGanador);
        new Escribe(canalSalida, numeroGanador).run();
    }
}