package in.clouthink.daas.es.model;

public interface MutableIdentityProvider<T> extends IdentityProvider<T> {

    void setId(T id);

}
