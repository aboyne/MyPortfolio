package myportfolio.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 *  An wrapper class to make a {@code javax.ws.rs.client.Client} {@code AutoCloseable} and therefore allow its use
 *  within try-with-resources blocks thus reducing the amount of unnecessary boilerplate code.
 */
public class CloseableClient implements Client, AutoCloseable
{
    private final Client client;

    public CloseableClient(Client client) { this.client = client; }

    @Override
    public void close() { client.close(); }

    @Override
    public WebTarget target(String s) { return client.target(s); }

    @Override
    public WebTarget target(URI uri) { return client.target(uri); }

    @Override
    public WebTarget target(UriBuilder uriBuilder) { return client.target(uriBuilder); }

    @Override
    public WebTarget target(Link link) { return client.target(link); }

    @Override
    public Invocation.Builder invocation(Link link) { return client.invocation(link); }

    @Override
    public SSLContext getSslContext() { return client.getSslContext(); }

    @Override
    public HostnameVerifier getHostnameVerifier() { return client.getHostnameVerifier(); }

    @Override
    public Configuration getConfiguration() { return client.getConfiguration(); }

    @Override
    public Client property(String s, Object o) { return client.property(s, o); }

    @Override
    public Client register(Class<?> aClass) { return client.register(aClass); }

    @Override
    public Client register(Class<?> aClass, int i) { return client.register(aClass, i); }

    @Override
    public Client register(Class<?> aClass, Class<?>... classes) { return client.register(aClass, classes); }

    @Override
    public Client register(Class<?> aClass, Map<Class<?>, Integer> map) { return client.register(aClass, map); }

    @Override
    public Client register(Object o) { return client.register(o); }

    @Override
    public Client register(Object o, int i) { return client.register(o, i); }

    @Override
    public Client register(Object o, Class<?>... classes) { return client.register(o, classes); }

    @Override
    public Client register(Object o, Map<Class<?>, Integer> map) { return client.register(o, map); }
}
