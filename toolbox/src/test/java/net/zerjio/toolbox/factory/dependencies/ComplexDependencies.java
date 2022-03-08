package net.zerjio.toolbox.factory.dependencies;

import net.zerjio.toolbox.factory.FactoryObjectsProducer;
import net.zerjio.toolbox.factory.FactoryItem;
import net.zerjio.toolbox.factory.classes.*;

/*
   Complex dependencies:

      +---+       +---+              +---+
      | D |--+---→| E |----+    +---→| G |
      +---+  |    +---+    |    |    +---+
             |             ↓    |
             |           +---+  |    +---+
             +----------→| F |--+---→| H |
                         +---+       +---+
*/
public class ComplexDependencies implements FactoryObjectsProducer {

   private int idCounter = 0;

   @FactoryItem
   public Class_D class_D(Class_E class_E, Class_F class_F) {
      return new Class_D_Impl(++idCounter, class_E, class_F);
   }

   @FactoryItem
   public Class_E class_E(Class_F class_F) {
      return new Class_E_Impl(++idCounter, class_F);
   }

   @FactoryItem
   public Class_F class_F(Class_G class_G, Class_H class_H) {
      return new Class_F_Impl(++idCounter, class_G, class_H);
   }

   @FactoryItem
   public Class_G class_G() {
      return new Class_G_Impl(++idCounter);
   }

   @FactoryItem
   public Class_H class_H() {
      return new Class_H_Impl(++idCounter);
   }
}
