package bingo;

import Jpmi.*;
//Proceso que envia un mensaje a otro proceso por su canal de salida

public class Escribe implements Proceso
{
 CanalSimple canal_out;
 Integer objInt;
 
 public Escribe(CanalSimple canal_out, Integer objInt)
   {
    this.canal_out=canal_out;
    this.objInt=objInt;
   }	
 
 public void run()
   {
    canal_out.send(objInt);
   }
}
