package net.zerjio.toolbox.factory;

public interface Factory {

    int size();

    <T> T instanceOf(Class <T> theClass);
}
