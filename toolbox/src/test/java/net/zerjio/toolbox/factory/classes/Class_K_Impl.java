package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_K_Impl implements Class_K {

   private int id;

   private Class_L class_L;

   public Class_K_Impl(int id, Class_L class_L) {
      this.id = id;
      this.class_L = class_L;
   }

   @Override
   public String call() {
      return String.format("K.%d(%s)", id, class_L.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_K_Impl other = (Class_K_Impl) o;
      return id == other.id &&
         Objects.equals(class_L, other.class_L);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_L);
   }

   @Override
   public String toString() {
      return call();
   }
}
