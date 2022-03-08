package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_L_Impl implements Class_L {

   private int id;

   private Class_I class_I;

   public Class_L_Impl(int id, Class_I class_I) {
      this.id = id;
      this.class_I = class_I;
   }

   @Override
   public String call() {
      return String.format("L.%d(%s)", id, class_I.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_L_Impl other = (Class_L_Impl) o;
      return id == other.id &&
         Objects.equals(class_I, other.class_I);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_I);
   }

   @Override
   public String toString() {
      return call();
   }
}
