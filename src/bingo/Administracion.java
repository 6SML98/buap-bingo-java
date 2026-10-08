package bingo;

import Jpmi.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javax.swing.JTextArea;

public class Administracion implements Proceso {
    private CanalSimple[] canalesEntradaJugadores;
    private CanalSimple canalSorteo;
    private CanalSimple canalEscrutinioIn;
    private CanalSimple canalEscrutinioOut;
    private JTextArea area;

    public Administracion(CanalSimple[] canalesEntradaJugadores, CanalSimple canalSorteo,
                          CanalSimple canalEscrutinioIn, CanalSimple canalEscrutinioOut,
                          JTextArea area) {
        this.canalesEntradaJugadores = canalesEntradaJugadores;
        this.canalSorteo = canalSorteo;
        this.canalEscrutinioIn = canalEscrutinioIn;
        this.canalEscrutinioOut = canalEscrutinioOut;
        this.area = area;
    }

    private void guardarResultados(Integer[] numeros, int[] resultados) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados_bingo.txt", true))) {
            writer.println("--- RESULTADO DE PARTIDA ---");
            for (int i = 0; i < resultados.length; i++) {
                String estado = resultados[i] == 1 ? "GANADOR" : "PERDEDOR";
                writer.println("Jugador " + i + ": " + numeros[i] + " -> " + estado);
            }
            writer.println("Numero ganador: " + numeros[10]);
            writer.println("----------------------------");
        } catch (IOException e) {
            area.append("Error al guardar resultados: " + e.getMessage() + "\n");
        }
    }

    public void run() {
        Integer[] numeros = new Integer[11];
        Lee[] lectores = new Lee[10];

        for (int i = 0; i < 10; i++) {
            lectores[i] = new Lee(canalesEntradaJugadores[i], 0);
        }

        new Paralelo(lectores).run();

        for (int i = 0; i < 10; i++) {
            numeros[i] = lectores[i].objInt;
            area.append("Administracion recibio de Jugador " + i + ": " + numeros[i] + "\n");
        }

        numeros[10] = (Integer) canalSorteo.receive();
        area.append("Administracion recibio número ganador del Sorteo: " + numeros[10] + "\n");

        canalEscrutinioIn.send(numeros);

        int[] resultados = (int[]) canalEscrutinioOut.receive();
        for (int i = 0; i < resultados.length; i++) {
            String estado = resultados[i] == 1 ? "GANADOR" : "PERDEDOR";
            area.append("Jugador " + i + ": " + estado + "\n");
        }

        guardarResultados(numeros, resultados);
    }
}
