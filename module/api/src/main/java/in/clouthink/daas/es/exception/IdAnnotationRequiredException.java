package in.clouthink.daas.es.exception;

public class IdAnnotationRequiredException extends AnnotationRequiredException {

    /**
     * Constructor for IdAnnotationRequiredException.
     */
    public IdAnnotationRequiredException() {
    }

    /**
     * Constructor for IdAnnotationRequiredException.
     *
     * @param message the detail message
     */
    public IdAnnotationRequiredException(String message) {
        super(message);
    }

    /**
     * Constructor for IdAnnotationRequiredException.
     *
     * @param message the detail message
     * @param cause   the root cause (usually from using a underlying
     *                data access API such as JDBC)
     */
    public IdAnnotationRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public IdAnnotationRequiredException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            the detail message
     * @param cause              the root cause (usually from using a underlying
     *                           data access API such as JDBC)
     * @param enableSuppression
     * @param writableStackTrace
     */
    public IdAnnotationRequiredException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
