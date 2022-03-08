package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_F_Impl implements Class_F {

   private int id;

   private Class_G class_G;

   private Class_H class_H;

   public Class_F_Impl(int id, Class_G class_G, Class_H class_H) {
      this.id = id;
      this.class_G = class_G;
      this.class_H = class_H;
   }

   @Override
   public String call() {
      return String.format("F.%d(%s,%s)", id, class_G.call(), class_H.call());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_F_Impl other = (Class_F_Impl) o;
      return id == other.id &&
         Objects.equals(class_G, other.class_G) &&
         Objects.equals(class_H, other.class_H);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, class_G, class_H);
   }

   @Override
   public String toString() {
      return call();
   }

}
