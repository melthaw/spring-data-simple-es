package in.clouthink.daas.es6.exception;

public class IncorrectResultSizeDataAccessException extends DataAccessException {

    private int expectedSize;

    private long actualSize;

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     *
     * @param expectedSize the expected result size
     */
    public IncorrectResultSizeDataAccessException(int expectedSize) {
        super("Incorrect result size: expected " + expectedSize);
        this.expectedSize = expectedSize;
        this.actualSize = -1;
    }

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     *
     * @param expectedSize the expected result size
     * @param actualSize   the actual result size (or -1 if unknown)
     */
    public IncorrectResultSizeDataAccessException(int expectedSize, long actualSize) {
        super("Incorrect result size: expected " + expectedSize + ", actual " + actualSize);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     *
     * @param msg          the detail message
     * @param expectedSize the expected result size
     */
    public IncorrectResultSizeDataAccessException(String msg, int expectedSize) {
        super(msg);
        this.expectedSize = expectedSize;
        this.actualSize = -1;
    }

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     *
     * @param msg          the detail message
     * @param expectedSize the expected result size
     * @param ex           the wrapped exception
     */
    public IncorrectResultSizeDataAccessException(String msg, int expectedSize, Throwable ex) {
        super(msg, ex);
        this.expectedSize = expectedSize;
        this.actualSize = -1;
    }

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     *
     * @param msg          the detail message
     * @param expectedSize the expected result size
     * @param actualSize   the actual result size (or -1 if unknown)
     */
    public IncorrectResultSizeDataAccessException(String msg, int expectedSize, long actualSize) {
        super(msg);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     *
     * @param msg          the detail message
     * @param expectedSize the expected result size
     * @param actualSize   the actual result size (or -1 if unknown)
     * @param ex           the wrapped exception
     */
    public IncorrectResultSizeDataAccessException(String msg, int expectedSize, long actualSize, Throwable ex) {
        super(msg, ex);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }


    /**
     * Return the expected result size.
     */
    public int getExpectedSize() {
        return this.expectedSize;
    }

    /**
     * Return the actual result size (or -1 if unknown).
     */
    public long getActualSize() {
        return this.actualSize;
    }

}
