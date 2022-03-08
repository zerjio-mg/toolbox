package net.zerjio.toolbox.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

class ObjectCreator {

   private FactoryObjectsProducer factoryObjectsProducer;

   private Method builderMethod;

   private FactoryItem annotation;

   ObjectCreator(
       FactoryObjectsProducer factoryObjectsProducer,
       Method builderMethod,
       FactoryItem annotation
   ) {
      this.factoryObjectsProducer = factoryObjectsProducer;
      this.builderMethod = builderMethod;
      this.annotation = annotation;
   }

   Method getBuilderMethod() {
      return builderMethod;
   }

   Class<?> getType() {
      return builderMethod.getReturnType();
   }

   Class<?>[] getParameters() {
      return builderMethod.getParameterTypes();
   }

   Object create(List<Object> parameters) throws Exception {
      if (parameters.isEmpty()) {
         return builderMethod.invoke(factoryObjectsProducer);
      } else {
         return builderMethod.invoke(factoryObjectsProducer, parameters.toArray());
      }
   }

   boolean isSingleton() {
      return annotation.scope().equals(FactoryItem.Scope.SINGLETON);
   }

   boolean isPrototype() {
      return annotation.scope().equals(FactoryItem.Scope.PROTOTYPE);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ObjectCreator other = (ObjectCreator) o;
      return Objects.equals(factoryObjectsProducer, other.factoryObjectsProducer) &&
         Objects.equals(builderMethod, other.builderMethod);
   }

   @Override
   public int hashCode() {
      return Objects.hash(factoryObjectsProducer, builderMethod);
   }

   @Override
   public String toString() {
      return String.format("ObjectCreator{%s %s(...)}",
         builderMethod.getReturnType(),
         builderMethod.getName()
      );
   }
}
