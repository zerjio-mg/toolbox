package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_B_Impl implements Class_B {

   private int id;

   private Class_C class_C;

   public Class_B_Impl(int id, Class_C class_C) {
      this.id = id;
      this.class_C = class_C;
   }

   @Override
   public String call() {
      return String.format("B.%d(%s)", id, class_C.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_B_Impl other = (Class_B_Impl) o;
      return id == other.id &&
         Objects.equals(class_C, other.class_C);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_C);
   }

   @Override
   public String toString() {
      return call();
   }

}
