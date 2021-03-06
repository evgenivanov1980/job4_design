package ru.job4j.collection.hashtable;

import java.util.*;

public class HashTable<K, V> implements Iterable<HashTableItem<K, V>> {
    private HashTableItem[] array;
    private int capacity;
   private int size;
   private int modcount = 0;
   private final double loadfactor = 0.75;

    public HashTable(int capacity) {
        this.capacity = capacity;
        array =  new HashTableItem[capacity];
    }

     public boolean insert(K key, V value) {
         boolean res = false;
         if (getLoadfactor() >= loadfactor) {
             this.resize();
         }
        int index = Math.abs(key.hashCode()) % capacity;
        if (array[index] == null) {
            array[index] = new HashTableItem(key, value);
            size++;
            modcount++;
            res = true;
        } else {
            if (array[index].key.equals(key)) {
                array[index].value = value;
                res = true;
            }
        }

        return res;
     }

    private double getLoadfactor() {
        return size / capacity;
    }

    private void resize() {
        capacity = capacity * 2;
        var oldArray = array;
        size = 0;
        array = new HashTableItem[capacity];
        for (var item: oldArray) {
            if (item != null) {
              this.insert((K) item.key, (V) item.value);
            }
        }
    }

     public V get(K key) {
        V value = null;
        int index = key.hashCode() % capacity;
        if (array[index] != null && array[index].key.equals(key)) {
             value = (V) array[index].value;
        }
        return value;
     }

     public boolean delete(K key) {
        boolean flag = false;
        int index = key.hashCode() % capacity;
        if (array[index] != null && array[index].key.equals(key)) {
            array[index] = null;
            flag = true;
            size--;
            modcount++;
        }
        return flag;
     }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public Iterator<HashTableItem<K, V>> iterator() {
        Iterator<HashTableItem<K, V>> it = new Iterator<>() {
            int count = 0;
            int currentIndex = 0;
            int expectedModCount = modcount;

            @Override
            public boolean hasNext() {

                return count < size;
            }

            @Override
            public HashTableItem<K, V> next() {
                if (!hasNext()) {
                    throw  new NoSuchElementException();
                }
                if (expectedModCount != modcount) {
                    throw new ConcurrentModificationException();
                }
                while (currentIndex < capacity) {
                    if (array[currentIndex] != null) {
                        break;
                    }
                    currentIndex++;
                }
                count++;
                return array[currentIndex++];
            }
        };
        return it;
    }
}
