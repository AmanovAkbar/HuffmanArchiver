package com.akbaramanov.huffmanarchiver.auxiliaryClasses;

public class Pair<F, S> extends java.util.AbstractMap.SimpleImmutableEntry<F, S> {

    public  Pair( F f, S s ) {
        super( f, s );
    }

    public F getKey() {
        return super.getKey();
    }

    public S getValue() {
        return super.getValue();
    }

    public String toString() {
        return "["+getKey()+","+getValue()+"]";
    }

}