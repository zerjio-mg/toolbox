package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_A_Impl implements Class_A {

   private int id;

   private Class_B class_B;

   public Class_A_Impl(int id, Class_B class_B) {
      this.id = id;
      this.class_B = class_B;
   }

   @Override
   public String call() {
      return String.format("A.%d(%s)", id, class_B.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_A_Impl other = (Class_A_Impl) o;
      return id == other.id &&
         Objects.equals(class_B, other.class_B);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_B);
   }

   @Override
   public String toString() {
      return call();
   }
}
