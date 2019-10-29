package in.clouthink.daas.es6.exception;

public class DuplicatedIdAnnotationException extends AnnotationRequiredException {

    /**
     * Constructor for DuplicatedIdAnnotationException.
     */
    public DuplicatedIdAnnotationException() {
    }

    /**
     * Constructor for DuplicatedIdAnnotationException.
     *
     * @param message the detail message
     */
    public DuplicatedIdAnnotationException(String message) {
        super(message);
    }

    /**
     * Constructor for DuplicatedIdAnnotationException.
     *
     * @param message the detail message
     * @param cause   the root cause (usually from using a underlying
     *                data access API such as JDBC)
     */
    public DuplicatedIdAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public DuplicatedIdAnnotationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            the detail message
     * @param cause              the root cause (usually from using a underlying
     *                           data access API such as JDBC)
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DuplicatedIdAnnotationException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
