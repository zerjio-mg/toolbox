package net.zerjio.toolbox.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Builder {

   private List<FactoryObjectsProducer> factoryObjectsProducers;

   private Map<Class, ObjectCreator> objectCreators;

   public Builder(FactoryObjectsProducer... factoryObjectsProducers) {
      this.factoryObjectsProducers = new ArrayList<>();
      this.objectCreators = new HashMap<>();
      for(FactoryObjectsProducer factoryObjectsProducer: factoryObjectsProducers) {
         init(factoryObjectsProducer);
      }
   }

   private void init(FactoryObjectsProducer factoryObjectsProducer) {
      factoryObjectsProducers.add(factoryObjectsProducer);
      Method[] methods = factoryObjectsProducer.getClass().getMethods();
      for (Method method: methods) {
         FactoryItem annotation = method.getAnnotation(FactoryItem.class);
         if (annotation != null) {
            checkDependencyAlreadyExists(method);
            objectCreators.put(method.getReturnType(), new ObjectCreator(factoryObjectsProducer, method, annotation));
         }
      }
   }

   private void checkDependencyAlreadyExists(Method method) {
      if(objectCreators.containsKey(method.getReturnType())) {
         throw new FactoryException("Dependency already exists : %s", method);
      }
   }

   ObjectCreator objectCreator(Class theClass) {
      return objectCreators.get(theClass);
   }

   @Override
   public String toString() {
      return String.format("Builder{producers: %s, creators: %d}",
         factoryObjectsProducers,
         objectCreators.size()
      );
   }
}
