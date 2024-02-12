package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void test1()
    {
        StudentArrayDeque<Integer> out = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();
        int size = 0;
        for (int i = 0; i < 1000; i++)
        {
            int type = StdRandom.uniform(4);
            if(type == 0)
            {
                // add last
                size++;
                int num = StdRandom.uniform(8000);
                out.addLast(num);
                expected.addLast(num);
            }
            else if(type == 1)
            {
                size++;
                // add first
                int num = StdRandom.uniform(8000);
                out.addFirst(num);
                expected.addFirst(num);
            }
            else if(type == 2)
            {
                if(size == 0)continue;
                // remove first
                Integer ex = expected.removeFirst();
                Integer o = out.removeFirst();
                assertEquals("addFirst(" + ex + ')' + '\n' +  "addFirst(" + ex + ')' + '\n' + "removeFirst(): " +
                        o + '\n' + "removeFirst(): " +
                        ex , ex , o);
            }
            else {
                // remove last
                if(size == 0)continue;
                Integer ex = expected.removeLast();
                Integer o = out.removeLast();
                assertEquals("addLast(" + ex + ')' + '\n' +  "addLast(" + ex + ')' + '\n' + "removeLast(): " +
                        o + '\n' + "removeLast(): " +
                        ex , ex , o);
            }
        }

    }

    @Test
    public void test2()
    {
        StudentArrayDeque<Integer> out = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();
        int size = 0;
        for (int i = 0; i < 2000; i++)
        {
            int type = StdRandom.uniform(4);
            if(type == 0)
            {
                // add last
                size++;
                int num = StdRandom.uniform(8000);
                out.addLast(num);
                expected.addLast(num);
            }
            else if(type == 1)
            {
                // add first
                size++;
                int num = StdRandom.uniform(8000);
                out.addFirst(num);
                expected.addFirst(num);
            }
            else if(type == 2)
            {
                if(size == 0)continue;
                // remove first
                Integer ex = expected.removeFirst();
                Integer o = out.removeFirst();
                assertEquals("addFirst(" + ex + ')' + '\n' +  "addFirst(" + ex + ')' + '\n' + "removeFirst(): " +
                        o + '\n' + "removeFirst(): " +
                        ex , ex , o);
            }
            else {
                // remove last
                if(size == 0)continue;
                Integer ex = expected.removeLast();
                Integer o = out.removeLast();
                assertEquals("addLast(" + ex + ')' + '\n' +  "addLast(" + ex + ')' + '\n' + "removeLast(): " +
                        o + '\n' + "removeLast(): " +
                        ex , ex , o);
            }
        }

    }

}
