package net.zerjio.toolbox.factory.dependencies;

import net.zerjio.toolbox.factory.FactoryObjectsProducer;
import net.zerjio.toolbox.factory.FactoryItem;
import net.zerjio.toolbox.factory.classes.*;

/*
   Wrong dependencies:

      Dependency not found (not declared in Dependencies class)

          +---+     +---+
          | B |---->| ? |
          +---+     +---+

*/
public class NotFoundDependency implements FactoryObjectsProducer {

   private int idCounter = 0;

   @FactoryItem
   public Class_B class_B(Class_C class_C) {
      return new Class_B_Impl(++idCounter, class_C);
   }

}
