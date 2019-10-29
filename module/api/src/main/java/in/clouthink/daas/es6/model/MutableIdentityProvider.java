package in.clouthink.daas.es6.model;

public interface MutableIdentityProvider<T> extends IdentityProvider<T> {

    void setId(T id);

}
