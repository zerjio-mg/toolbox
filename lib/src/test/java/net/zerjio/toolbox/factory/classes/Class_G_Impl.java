package net.zerjio.toolbox.factory.classes;

import java.util.Objects;

public class Class_G_Impl implements Class_G {

   private int id;

   public Class_G_Impl(int id) {
      this.id = id;
   }

   @Override
   public String call() {
      return String.format("G.%d", id);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Class_G_Impl other = (Class_G_Impl) o;
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
