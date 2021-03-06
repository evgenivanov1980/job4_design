package ru.job4j.collection;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleArrayTest {

    @Test
    public void whenAddThenGet() {
       SimpleArray<String> array = new SimpleArray<String>();
        array.add("first");
        String rs1 = array.get(0);
        assertThat(rs1, is("first"));
    }

    @Test
    public void whenAddThenIterator() {
        SimpleArray<String> array = new SimpleArray<String>();
        array.add("first");
        String rs1 = array.iterator().next();
        assertThat(rs1, is("first"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetEmpty() {
        SimpleArray<String> array = new SimpleArray<String>();
        array.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetOutOfBoundsException() {
        SimpleArray<String> array = new SimpleArray<String>();
        array.add("first");
        array.get(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetEmptyFromIt() {
        SimpleArray<String> array = new SimpleArray<String>();
        array.iterator().next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedIt() {
        SimpleArray<String> array = new SimpleArray<String>();
        array.add("first");
        Iterator<String> it = array.iterator();
        array.add("second");
        it.next();
    }



}