package eu.venthe.javadoc;

public class NotYetImplementedException extends UnsupportedOperationException {
    public NotYetImplementedException(Version version) {
        super(version.toString());
    }
}
