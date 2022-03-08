package net.zerjio.toolbox.factory;

import java.util.*;

public class BasicFactory implements Factory {

   private final Builder builder;

   private final Map<Class, Object> instances;

   public BasicFactory(FactoryObjectsProducer... dependencies) {
      this.builder = new Builder(dependencies);
      this.instances = new HashMap<>();
   }

   @Override
   public int size() {
      return instances.size();
   }

   @Override
   public <T> T instanceOf(Class<T> theClass) {
      return instanceOf(theClass, new Stack<>());
   }

   private <T> T instanceOf(Class<T> theClass, Stack<ObjectCreator> stack) {
      T instance = (T)instances.get(theClass);
      return (instance != null)? instance : lookup(theClass, stack);
   }

   private <T> T lookup(Class<T> theClass, Stack<ObjectCreator> stack) {
      ObjectCreator objectCreator = builder.objectCreator(theClass);
      if (objectCreator == null) {
         throw new FactoryException("Instance of '%s' not found", theClass.getName());
      }
      if (stack.contains(objectCreator)) {
         throw new FactoryException("Cyclic dependency");
      }
      return lookup(objectCreator, theClass, stack);
   }

   private <T> T lookup(ObjectCreator objectCreator, Class<T> theClass, Stack<ObjectCreator> stack) {
      try {
         stack.push(objectCreator);
         T instance = createInstance(objectCreator, stack);
         stack.pop();
         if (objectCreator.isSingleton()) {
            instances.put(theClass, instance);
         }
         return instance;
      } catch (Exception error) {
         throw new FactoryException(
            error,
            "Error creating instance of '%s' : %s",
            objectCreator.getType(),
            error.getMessage()
         );
      }
   }

   private <T> T createInstance(ObjectCreator objectCreator, Stack<ObjectCreator> stack) throws Exception {
      Class<?>[] parametersClasses = objectCreator.getParameters();
      List<Object> parameters = new ArrayList<>();
      for (Class parameterClass : parametersClasses) {
         Object instance = instanceOf(parameterClass, stack);
         parameters.add(instance);
      }
      return (T)objectCreator.create(parameters);
   }

   @Override
   public String toString() {
      return String.format(
         "BasicFactory{instances: %d, builder: %s}",
         instances.size(),
         builder
      );
   }
}
