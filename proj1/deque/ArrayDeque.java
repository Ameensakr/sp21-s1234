package deque;

import java.util.Iterator;

<<<<<<< HEAD
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int f, l;
=======
public class ArrayDeque <T> implements Deque<T> , Iterable<T> {
    private int f , l;
>>>>>>> feature
    private T[] array;
    private int size, capacity;

    public ArrayDeque() {
        size = 0;
        capacity = 8;
        array = (T[]) new Object[capacity];
        f = 7;
        l = 0;
    }

    private void print(int x) {
        System.out.println(x);
    }


    public void addFirst(T item) {
        if (size == capacity) {
            capacityTrick(capacity * 2);
        }
        array[f] = item;
        f = sube(f, 1);
        size++;
    }

    public void addLast(T item) {
        if (size == capacity) {
            capacityTrick(capacity * 2);
        }
        array[l] = item;
        l = (l + 1) % capacity;
        size++;
    }


    public int size() {
        return size;
    }

    public void printDeque() {
        if (isEmpty()) return;
        int idx = (f + 1) % capacity;
        print(idx);
        for (int i = 0; i < size; i++) {
            System.out.print(array[idx] + " ");
            idx = (idx + 1) % capacity;
        }
        System.out.println();
    }

    private int sube(int a, int b) {
        return ((((a % capacity) - (b % capacity)) % capacity) + capacity) % capacity;
    }

    private void is_waste_memory() {
        double p = capacity;
        p = p / 4;
        if (p >= size && capacity >= 16) {

            capacityTrick(capacity / 2);
        }

    }

    private void capacityTrick(int newCapacity) // I want to copy first l element to the front of the new array & copy the last f elements from the old array to the new array
    {
        int old = capacity;
        capacity = newCapacity;
        T[] temp = (T[]) new Object[capacity];
        int idx = (f + 1) % old;
        int i = 0;
        for (int p = 0; p < size; p++) {
            temp[i] = array[idx];
            idx = (idx + 1) % old;
            i++;
        }
        f = capacity - 1;
        l = size;
        array = temp;
    }

    public T removeLast() {
        if (isEmpty()) return null;
        is_waste_memory();
        l = sube(l, 1);
        T temp = array[l];
        size--;
        return temp;
    }

    public T removeFirst() {
        if (isEmpty()) return null;
        is_waste_memory();
        f = (f + 1) % capacity;
        T temp = array[f];
        size--;
        return temp;
    }

    public T get(int index) {
        if (index >= size) return null;
        return array[(f + 1 + index) % capacity];
    }

    private int getCapacity() {
        return capacity;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
<<<<<<< HEAD
=======

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = array[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }
    public boolean equals(Object o)
    {
        if(o instanceof ArrayDeque)
        {
            Deque<T> p = (Deque<T>) o;
            //check the 2 deqeu with same size
            if(this.size() != p.size())
            {
                return false;
            }
            for(T x : this)
            {
                if(!p.find(x))
                {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    public boolean find(T item)
    {
        for (int i = 0 ; i < size ; i++)
        {
            if(array[i] == item)return true;
        }
        return false;
    }


>>>>>>> feature

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        private ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = array[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            Deque p = (Deque) o;
            //check the 2 deqeu with same size
            if (this.size() != p.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!(get(i).equals(p.get(i))))return false;
            }
            return true;
        }
        return false;
    }


}
