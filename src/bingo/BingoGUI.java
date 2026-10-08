
package bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Jpmi.*;

public class BingoGUI extends JFrame {
    private JTextArea areaResultados;
    private JButton botonJugar;
    private JSpinner spinnerPartidas;
    private JLabel iconoEstado;

    public BingoGUI() {
        setTitle("🎉 Simulacion BINGO - JPMI");
        setSize(650, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Simulacion de BINGO", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        iconoEstado = new JLabel("", JLabel.CENTER);
        iconoEstado.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        panelSuperior.add(iconoEstado, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        areaResultados.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));
        add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInferior.add(new JLabel("Numero de partidas:"));

        spinnerPartidas = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelInferior.add(spinnerPartidas);

        botonJugar = new JButton("🎲 Jugar BINGO");
        botonJugar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonJugar.setBackground(new Color(70, 130, 180));
        botonJugar.setForeground(Color.WHITE);
        panelInferior.add(botonJugar);
        add(panelInferior, BorderLayout.SOUTH);

        botonJugar.addActionListener(e -> jugarPartidas());

        setVisible(true);
    }

    private void jugarPartidas() {
        int partidas = (Integer) spinnerPartidas.getValue();
        areaResultados.setText("");
        iconoEstado.setText("🎮");

        botonJugar.setEnabled(false);
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() {
        for (int ronda = 1; ronda <= partidas; ronda++) {
            UiLog.append(areaResultados, "\n🟡 Partida " + ronda + " -----------------------\n");

            int[] numeros = new int[10];
            for (int i = 0; i < 10; i++) {
                numeros[i] = (int)(Math.random() * 20) + 1;
            }

            CanalSimple[] canalesJugadores = new CanalSimple[10];
            for (int i = 0; i < 10; i++) {
                canalesJugadores[i] = new CanalSimple();
            }

            CanalSimple canalSorteo = new CanalSimple();
            CanalSimple canalEscrutinioIn = new CanalSimple();
            CanalSimple canalEscrutinioOut = new CanalSimple();

            Proceso[] jugadores = new Proceso[10];
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                jugadores[i] = () -> {
                    UiLog.append(areaResultados, "👤 Jugador " + finalI + " eligio el numero " + numeros[finalI] + "\n");
                    new Escribe(canalesJugadores[finalI], numeros[finalI]).run();
                };
            }

            Administracion admin = new Administracion(canalesJugadores, canalSorteo, canalEscrutinioIn, canalEscrutinioOut, areaResultados);
            Sorteo sorteo = new Sorteo(canalSorteo);
            Escrutinio escrutinio = new Escrutinio(canalEscrutinioIn, canalEscrutinioOut);

            Proceso[] todos = new Proceso[13];
            System.arraycopy(jugadores, 0, todos, 0, 10);
            todos[10] = sorteo;
            todos[11] = escrutinio;
            todos[12] = admin;

            new Paralelo(todos).run();
        }

        return null;
            }
            @Override protected void done() {
                try {
                    get();
                    iconoEstado.setText("🏁");
                    UiLog.append(areaResultados, "\nBingo terminado.\n");
                } catch (Exception ex) {
                    UiLog.append(areaResultados, "Error de simulación: " + ex.getMessage() + "\n");
                } finally {
                    botonJugar.setEnabled(true);
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BingoGUI::new);
    }
}
