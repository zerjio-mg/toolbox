package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_D_Impl implements Class_D {

   private int id;

   private Class_E class_E;

   private Class_F class_F;

   public Class_D_Impl(int id, Class_E class_E, Class_F class_F) {
      this.id = id;
      this.class_E = class_E;
      this.class_F = class_F;
   }

   @Override
   public String call() {
      return String.format("D.%d(%s,%s)", id, class_E.call(), class_F.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_D_Impl other = (Class_D_Impl) o;
      return id == other.id &&
         Objects.equals(class_E, other.class_E) &&
         Objects.equals(class_F, other.class_F);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_E, class_F);
   }

   @Override
   public String toString() {
      return call();
   }

}
