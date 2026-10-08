package bingo;

import Jpmi.*;

// Proceso que recibe de otro proceso un mensaje por su canal de entrada
public class Lee implements Proceso
  {
   CanalSimple canal_in;
   Integer objInt;
   
   public Lee(CanalSimple canal_in, Integer objInt)
     {
      this.canal_in=canal_in;
      this.objInt=objInt; 
     }
   public void run()
     {
      objInt=(Integer)canal_in.receive();
     }
  }
  