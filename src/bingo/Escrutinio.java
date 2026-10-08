package bingo;
import Jpmi.*;

public class Escrutinio implements Proceso {
    private CanalSimple canalEntrada;
    private CanalSimple canalSalida;

    public Escrutinio(CanalSimple canalEntrada, CanalSimple canalSalida) {
        this.canalEntrada = canalEntrada;
        this.canalSalida = canalSalida;
    }

    public void run() {
        Integer[] numeros = (Integer[]) canalEntrada.receive();
        int[] resultados = new int[10];

        int numeroGanador = numeros[10];
        for (int i = 0; i < 10; i++) {
            resultados[i] = (numeros[i].equals(numeroGanador)) ? 1 : 0;
        }

        canalSalida.send(resultados);
    }
}
