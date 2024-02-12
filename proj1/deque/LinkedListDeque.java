package deque;

import java.util.Iterator;
import java.util.stream.StreamSupport;

public class LinkedListDeque <T> implements Deque<T> {

    public class Item {
        private T first;

        private Item next , prev; // to point to the node next of U and before U

        public Item(T first)
        {
            this.first = first;
        }
    }

    private Item dummy;
    private int size;

    public LinkedListDeque()
    {
        dummy = new Item(null);
        dummy.next = dummy;
        dummy.prev = dummy;
        size = 0;
    }
    public void addFirst(T item) // add num to begin of the LinkedListDeque in O(1)
    {
        Item temp = new Item(item);
        temp.next = dummy.next;
        dummy.next = temp;
        temp.prev = dummy;
        temp.next.prev = temp;
        size += 1;
    }
    public void addLast(T item) // add num to begin of the LinkedListDeque in O(1)
    {
        Item temp = new Item(item);
        temp.next = dummy;
        dummy.prev.next = temp;
        temp.prev = dummy.prev;
        dummy.prev = temp;
        size += 1;
    }


    public int size()
    {
        return size;
    }
    public void printDeque()
    {
        Item temp = dummy.next;
        for(int i = 0; i < size ; i += 1)
        {
            System.out.print(temp.first + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    public T removeFirst() // O(1)
    {
        if(size == 0)return null;
        Item temp = dummy.next;
        dummy.next = dummy.next.next;
        dummy.next.prev = dummy;
        size -= 1;
        return temp.first;
    }
    public T removeLast() // O(1)
    {
        if(size == 0)return null;
        Item temp = dummy.prev;
        dummy.prev = dummy.prev.prev;
        dummy.prev.next = dummy;
        size -= 1;
        return temp.first;
    }

    public T get(int index)
    {

        if(index >= size)return null;

        Item temp = dummy.next;
        for (int i = 0; i < index ; i += 1)
        {
            temp = temp.next;
        }
        return temp.first;
    }


    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }


    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;

        public ArraySetIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }


    public boolean find(T item)
    {
        Item temp = dummy.next;
        for(int i = 0; i < size ; i += 1)
        {
            if(temp.first == item)return true;

            temp = temp.next;
        }
        return false;
    }



    public boolean equals(Object o)
    {
        if(o instanceof LinkedListDeque p)
        {
            //check the 2 deqeu with same size
            if(this.size() != p.size())
            {
                return false;
            }
            for(T x : this)
            {
                if(!p.find(x))return false;
            }
            return true;
        }
        return false;
    }







}


