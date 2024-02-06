package deque;

public class ArrayDeque <T> {
    private int f , l;
    private T[] array;
    private int size  , capacity ;
    public ArrayDeque()
    {
        size = 0;
        capacity = 1;
        array = (T[]) new Object[capacity];
        f = 0;
        l = 0;
    }

    public void print(int x)
    {
        System.out.println(x);
    }



    public void addFirst(T item)
    {
        if(size == capacity)
        {
            capacityTrick(capacity * 2);
        }
        array[f] = item;
        f--;
        size++;
    }
    public void addLast(T item)
    {
        if(size == capacity)
        {
            capacityTrick(capacity * 2);
        }
        array[l] = item;
        l++;
        size++;
    }

    public boolean isEmpty()
    {
        return (size == 0);
    }

    public int size()
    {
        return size;
    }

    public void printDeque()
    {
        if(isEmpty())return;
        int idx = add(f , 1);
        for (int i = 0 ; i < size ; i++)
        {
            System.out.print(array[idx] + " ");
            idx = add(idx , 1);
        }
        System.out.println();
    }

    private int sube(int a , int b)
    {
        return ((((a%capacity) - (b%capacity))%capacity)+capacity)%capacity;
    }

    private int add(int a , int b)
    {
        return ((a%capacity) + (b%capacity))%capacity;
    }

    public T removeFirst()
    {
        if(isEmpty())return null;
        T temp = array[add(f , 1)];
        f = add(f , 1);
        size--;
        is_waste_memory();
        return temp;
    }

    private void is_waste_memory()
    {
        double p = size;
        p = p / capacity;

        if(p < 0.25)
        {
            capacityTrick(capacity / 2);
        }

    }



    private void capacityTrick(int newCapacity) // I want to copy first l element to the front of the new array & copy the last f elements from the old array to the new array
    {
        capacity = newCapacity;
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < l ; i++)
        {
            temp[i] = array[i];
        }
        int idx = capacity - 1;
        for (int i = size - 1 ; i > f ; i--)
        {
            temp[idx] = array[i];
            idx--;
        }
        f = idx;
        array = temp;
    }

    public T removeLast()
    {
        if(isEmpty())return null;
        T temp = array[sube(l , 1)];
        l = sube(l , 1);
        size--;
        is_waste_memory();
        return temp;
    }

    public T get(int index)
    {
        if(index >= size)return null;
        return array[(f + 1 + index)%capacity];
    }
    public int getCapacity()
    {
        return capacity;
    }
    



}
