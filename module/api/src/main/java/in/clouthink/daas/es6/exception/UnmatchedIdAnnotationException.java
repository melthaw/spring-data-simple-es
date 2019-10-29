package in.clouthink.daas.es6.exception;

public class UnmatchedIdAnnotationException extends AnnotationRequiredException {

    /**
     * Constructor for UnmatchedIdAnnotationException.
     */
    public UnmatchedIdAnnotationException() {
    }

    /**
     * Constructor for UnmatchedIdAnnotationException.
     *
     * @param message the detail message
     */
    public UnmatchedIdAnnotationException(String message) {
        super(message);
    }

    /**
     * Constructor for UnmatchedIdAnnotationException.
     *
     * @param message the detail message
     * @param cause   the root cause (usually from using a underlying
     *                data access API such as JDBC)
     */
    public UnmatchedIdAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public UnmatchedIdAnnotationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            the detail message
     * @param cause              the root cause (usually from using a underlying
     *                           data access API such as JDBC)
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UnmatchedIdAnnotationException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
