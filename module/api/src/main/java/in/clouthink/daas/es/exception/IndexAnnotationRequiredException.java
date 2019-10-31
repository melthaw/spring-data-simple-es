package in.clouthink.daas.es.exception;

public class IndexAnnotationRequiredException extends AnnotationRequiredException {

    /**
     * Constructor for IndexAnnotationRequiredException.
     */
    public IndexAnnotationRequiredException() {
    }

    /**
     * Constructor for IndexAnnotationRequiredException.
     *
     * @param message the detail message
     */
    public IndexAnnotationRequiredException(String message) {
        super(message);
    }

    /**
     * Constructor for IndexAnnotationRequiredException.
     *
     * @param message the detail message
     * @param cause   the root cause (usually from using a underlying
     *                data access API such as JDBC)
     */
    public IndexAnnotationRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public IndexAnnotationRequiredException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            the detail message
     * @param cause              the root cause (usually from using a underlying
     *                           data access API such as JDBC)
     * @param enableSuppression
     * @param writableStackTrace
     */
    public IndexAnnotationRequiredException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
