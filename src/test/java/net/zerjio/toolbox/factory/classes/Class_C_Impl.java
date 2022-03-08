package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_C_Impl implements Class_C {

   private int id;

   public Class_C_Impl(int id) {
      this.id = id;
   }

   @Override
   public String call() {
      return String.format("C.%d", id);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_C_Impl other = (Class_C_Impl) o;
      return id == other.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

   @Override
   public String toString() {
      return call();
   }

}
