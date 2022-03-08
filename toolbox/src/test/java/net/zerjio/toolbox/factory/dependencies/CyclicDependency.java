package net.zerjio.toolbox.factory.dependencies;;

import net.zerjio.toolbox.factory.FactoryObjectsProducer;
import net.zerjio.toolbox.factory.FactoryItem;
import net.zerjio.toolbox.factory.classes.*;

/*
   Wrong dependencies:

      Cyclic Dependencies are not allowed !!!

         +---+       +---+
         | I |--+---→| J |
         +---+  |    +---+
           ↑    |
           |    |    +---+       +---+
           |    +---→| K |------→| L |----+
           |         +---+       +---+    |
           |                              |
           +------------------------------+

*/
public class CyclicDependency implements FactoryObjectsProducer {

   private int idCounter = 0;

   @FactoryItem
   public Class_I class_I(Class_J class_J, Class_K class_K) {
      return new Class_I_Impl(++idCounter, class_J, class_K);
   }

   @FactoryItem
   public Class_J class_J() {
      return new Class_J_Impl(++idCounter);
   }

   @FactoryItem
   public Class_K class_K(Class_L class_L) {
      return new Class_K_Impl(++idCounter, class_L);
   }

   @FactoryItem
   public Class_L class_L(Class_I class_I) {
      return new Class_L_Impl(++idCounter, class_I);
   }

}
