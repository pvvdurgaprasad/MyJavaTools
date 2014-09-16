package org.bonitasoft.example.jndi;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * A JNDI context implementation that uses the memory as a dictionary of objects.
 */
public class SimpleMemoryContext implements Context {

    private final Map<String, Object> dictionary = new ConcurrentHashMap<String, Object>();

    public void clear() {
        dictionary.clear();
    }

    @Override
    public Object lookup(final Name name) throws NamingException {
        return lookup(name.toString());
    }

    @Override
    public Object lookup(final String name) throws NamingException {
        // System.out.println(toString() + " ~~~~ lookup " + name + " contains ? " + dictionary.containsKey(name));
        if (dictionary.containsKey(name)) {
            return dictionary.get(name);
        }
        throw new NameNotFoundException("Name " + name + " is not bound !");
    }

    @Override
    public void bind(final Name name, final Object o) throws NamingException {
        bind(name.toString(), o);
    }

    @Override
    public void bind(final String name, final Object o) throws NamingException {
        // System.out.println(toString() + " ~~~~ binding " + name + " with " + o + " already bound ? " + dictionary.containsKey(name));
        if (dictionary.containsKey(name)) {
            throw new NameAlreadyBoundException("Name " + name + " already bound!");
        }
        rebind(name, o);
    }

    @Override
    public void rebind(final Name name, final Object o) {
        rebind(name.toString(), o);
    }

    @Override
    public void rebind(final String name, final Object o) {
        dictionary.put(name, o);
    }

    @Override
    public void unbind(final Name name) throws NamingException {
        unbind(name.toString());
    }

    @Override
    public void unbind(final String name) throws NamingException {
        if (!dictionary.containsKey(name)) {
            throw new NameNotFoundException("No such name " + name + " is bound!");
        }
        dictionary.remove(name);
    }

    @Override
    public void rename(final Name oldName, final Name newName) throws NamingException {
        rename(oldName.toString(), newName.toString());
    }

    @Override
    public void rename(final String oldName, final String newName) throws NamingException {
        final Object object = lookup(oldName);
        bind(newName, object);
        unbind(oldName);
    }

    @Override
    public NamingEnumeration<NameClassPair> list(final Name name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public NamingEnumeration<NameClassPair> list(final String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public NamingEnumeration<Binding> listBindings(final Name name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public NamingEnumeration<Binding> listBindings(final String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroySubcontext(final Name name) {
        destroySubcontext(name.toString());
    }

    @Override
    public void destroySubcontext(final String name) {
        dictionary.remove(name);
    }

    @Override
    public Context createSubcontext(final Name name) throws NamingException {
        return createSubcontext(name.toString());
    }

    @Override
    public Context createSubcontext(final String name) throws NamingException {
        final Context subContext = new SimpleMemoryContext();
        bind(name, subContext);
        return subContext;
    }

    @Override
    public Object lookupLink(final Name name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object lookupLink(final String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public NameParser getNameParser(final Name name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public NameParser getNameParser(final String name) {
        return new SimpleNameParser(name);
    }

    @Override
    public Name composeName(final Name name, final Name name1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String composeName(final String string, final String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object addToEnvironment(final String string, final Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object removeFromEnvironment(final String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Hashtable<?, ?> getEnvironment() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        // System.out.println("Closing SimpleMemoryContext");
        // Thread.dumpStack();
        // clear();
    }

    @Override
    public String getNameInNamespace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
