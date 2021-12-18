package net.mrscauthd.beyond_earth.util;

import java.util.function.Consumer;

public interface ThreeConsumer<T, U, V> {
   static <T, U, V> Consumer<T> bindArgs(ThreeConsumer<? super T, U, V> c, U arg2, V arg3) {
      return arg1 -> c.accept(arg1, arg2, arg3);
   }

   void accept(T var1, U var2, V var3);
}