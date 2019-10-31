package in.clouthink.daas.es.exception;

public class DataNotFoundException extends DataAccessException {

    /**
     * Constructor for DataNotFoundException.
     */
    public DataNotFoundException() {
    }

    /**
     * Constructor for DataNotFoundException.
     *
     * @param message the detail message
     */
    public DataNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for DataNotFoundException.
     *
     * @param message the detail message
     * @param cause   the root cause (usually from using a underlying
     *                data access API such as JDBC)
     */
    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            the detail message
     * @param cause              the root cause (usually from using a underlying
     *                           data access API such as JDBC)
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DataNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
