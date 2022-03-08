package net.zerjio.toolbox.factory;

public class FactoryException extends RuntimeException {

   public FactoryException(String reason, Object... args) {
      super(String.format(reason, args));
   }

   public FactoryException(Throwable error, String reason, Object... args) {
      super(String.format(reason, args), error);
   }
}
