package net.zerjio.toolbox.factory.dependencies;

import net.zerjio.toolbox.factory.FactoryObjectsProducer;
import net.zerjio.toolbox.factory.FactoryItem;
import net.zerjio.toolbox.factory.classes.*;

/*
   Wrong dependencies:

      Two dependencies declaration : same return type

*/
public class RepeatedDependencies implements FactoryObjectsProducer {

   private int idCounter = 0;

   @FactoryItem
   public Class_H class_H() {
      return new Class_H_Impl(++idCounter);
   }

   @FactoryItem
   public Class_H class_HH() {
      return new Class_HH_Impl(++idCounter);
   }
}
