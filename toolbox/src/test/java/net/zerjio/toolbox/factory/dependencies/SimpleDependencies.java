package net.zerjio.toolbox.factory.dependencies;

import net.zerjio.toolbox.factory.FactoryObjectsProducer;
import net.zerjio.toolbox.factory.FactoryItem;
import net.zerjio.toolbox.factory.classes.*;

/*
   Simple lineal dependencies:

       +---+     +---+     +---+
       | A |---->| B |---->| C |
       +---+     +---+     +---+

*/
public class SimpleDependencies implements FactoryObjectsProducer {

   private int idCounter = 0;

   @FactoryItem
   public Class_A class_A(Class_B class_B) {
      return new Class_A_Impl(++idCounter, class_B);
   }

   @FactoryItem
   public Class_B class_B(Class_C class_C) {
      return new Class_B_Impl(++idCounter, class_C);
   }

   @FactoryItem
   public Class_C class_C() {
      return new Class_C_Impl(++idCounter);
   }
}
