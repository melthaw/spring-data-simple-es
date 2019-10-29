package in.clouthink.daas.es6.exception;

public class AnnotationRequiredException extends RuntimeException {

    /**
     * Constructor for AnnotationRequiredException.
     */
    public AnnotationRequiredException() {
    }

    /**
     * Constructor for AnnotationRequiredException.
     *
     * @param message the detail message
     */
    public AnnotationRequiredException(String message) {
        super(message);
    }

    /**
     * Constructor for AnnotationRequiredException.
     *
     * @param message the detail message
     * @param cause   the root cause (usually from using a underlying
     *                data access API such as JDBC)
     */
    public AnnotationRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public AnnotationRequiredException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            the detail message
     * @param cause              the root cause (usually from using a underlying
     *                           data access API such as JDBC)
     * @param enableSuppression
     * @param writableStackTrace
     */
    public AnnotationRequiredException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
