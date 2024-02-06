package deque;

import java.util.stream.StreamSupport;

public class LinkedListDeque <T> {

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

    LinkedListDeque()
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

    public boolean isEmpty()
    {
        return (size == 0); // if size equal zero will return true otherwise will return false
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





}


