package bingo;
import Jpmi.*;

public class EscuchaJugador implements Proceso {
    private int id;
    private CanalSimple canalJugador;
    private CanalSimple canalCentral;

    public EscuchaJugador(int id, CanalSimple canalJugador, CanalSimple canalCentral) {
        this.id = id;
        this.canalJugador = canalJugador;
        this.canalCentral = canalCentral;
    }

    @Override
    public void run() {
        while (true) {
            int numero = (Integer) canalJugador.receive();
            canalCentral.send(new Object[]{id, numero});
        }
    }
}
