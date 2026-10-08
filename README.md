# Bingo

Simulación concurrente de diez jugadores, sorteo, administración y escrutinio mediante canales JPMI, con interfaz Swing.

## Requisitos

JDK 17, NetBeans o Ant y la biblioteca académica JpmiRossainz.jar en lib/. No se redistribuye esta biblioteca; obténla de tu curso o de su autor.

## Ejecutar

Abre el proyecto en NetBeans o usa `ant jar`. Inicio: `bingo.BingoGUI`.

Prueba del núcleo sin abrir ventanas:

```powershell
New-Item -ItemType Directory build/test-classes -Force | Out-Null
$fuentes = (Get-ChildItem src -Recurse -Filter *.java).FullName
javac -encoding UTF-8 -cp "lib/*" -d build/test-classes $fuentes tests/ModelCheck.java
Set-Location build/test-classes
java -Djava.awt.headless=true -cp ".;../../lib/*" ModelCheck
```

## Verificación del 8 de octubre de 2026

Construcción JAR con Ant y una partida completa comprobadas: diez jugadores y resultado escrito. La simulación se mueve a segundo plano y los mensajes Swing se actualizan en su hilo. La partida no garantiza ganador: el sorteo puede elegir un número que ningún jugador tenga. No se verificó visualmente la ventana de escritorio.
