package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_E_Impl implements Class_E {

   private int id;

   private Class_F class_F;

   public Class_E_Impl(int id, Class_F class_F) {
      this.id = id;
      this.class_F = class_F;
   }

   @Override
   public String call() {
      return String.format("E.%d(%s)", id, class_F.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_E_Impl other = (Class_E_Impl) o;
      return id == other.id &&
         Objects.equals(class_F, other.class_F);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_F);
   }

   @Override
   public String toString() {
      return call();
   }

}
