import bingo.*;
import Jpmi.*;
import javax.swing.*;
import java.nio.file.*;
public class ModelCheck {
 public static void main(String[] args) throws Exception {
  Path resultFile=Path.of("resultados_bingo.txt");
  long previousPlayers=Files.exists(resultFile) ? Files.readString(resultFile).lines().filter(l -> l.startsWith("Jugador ")).count() : 0;
  CanalSimple[] jugadores=new CanalSimple[10];
  Proceso[] procesos=new Proceso[13];
  for(int i=0;i<10;i++) { jugadores[i]=new CanalSimple(); procesos[i]=new Escribe(jugadores[i],i+1); }
  CanalSimple sorteo=new CanalSimple(),entrada=new CanalSimple(),salida=new CanalSimple();
  JTextArea log=new JTextArea();
  procesos[10]=new Sorteo(sorteo);
  procesos[11]=new Escrutinio(entrada,salida);
  procesos[12]=new Administracion(jugadores,sorteo,entrada,salida,log);
  new Paralelo(procesos).run();
  SwingUtilities.invokeAndWait(() -> {});
  String texto=Files.readString(Path.of("resultados_bingo.txt"));
  long jugadoresGuardados=texto.lines().filter(l -> l.startsWith("Jugador ")).count();
  if(jugadoresGuardados!=previousPlayers+10 || !texto.contains("Numero ganador:")) throw new AssertionError("Resultado incompleto");
  System.out.println("PASS: diez jugadores, sorteo, escrutinio y resultado guardado sin bloqueo");
 }
}
