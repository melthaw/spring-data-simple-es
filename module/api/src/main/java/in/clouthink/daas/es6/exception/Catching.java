package in.clouthink.daas.es6.exception;


public class Catching {

    @FunctionalInterface
    public interface Supplier<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface Executor {

        /**
         * Gets a result.
         *
         * @return a result
         */
        void execute() throws Exception;
    }

    public static <T> T around(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public static void around(Executor executor) {
        try {
            executor.execute();
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

}
