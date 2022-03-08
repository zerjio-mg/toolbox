package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_I_Impl implements Class_I {

   private int id;

   private Class_J class_J;

   private Class_K class_K;

   public Class_I_Impl(int id, Class_J class_J, Class_K class_K) {
      this.id = id;
      this.class_J = class_J;
      this.class_K = class_K;
   }

   @Override
   public String call() {
      return String.format("I.%d(%s, %s)", id, class_J.call(), class_K.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_I_Impl other = (Class_I_Impl) o;
      return id == other.id &&
         Objects.equals(class_J, other.class_J) &&
         Objects.equals(class_K, other.class_K);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_J, class_K);
   }

   @Override
   public String toString() {
      return call();
   }
}
