package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void test1() {
        StudentArrayDeque<Integer> out = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();
        int size = 0;
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            int type = StdRandom.uniform(4);

            if (type == 0) {
                // add last
                size++;
                int num = StdRandom.uniform(8000);
                st.append("addLast(" + num + ')' + '\n');
                out.addLast(num);
                expected.addLast(num);
            } else if (type == 1) {
                // add first
                size++;
                int num = StdRandom.uniform(8000);
                st.append("addFirst(" + num + ')' + '\n');
                out.addFirst(num);
                expected.addFirst(num);
            } else if (type == 2) {
                if (size == 0) continue;
                // remove first
                size--;
                Integer ex = expected.removeFirst();
                Integer o = out.removeFirst();
                st.append("removeFirst()\n");
                assertEquals(st.toString() , ex , o);
            } else {
                // remove last
                if (size == 0) continue;
                size--;
                Integer ex = expected.removeLast();
                Integer o = out.removeLast();
                st.append("removeLast()\n");
                assertEquals( st.toString() , ex, o);
            }
        }

    }

    @Test
    public void test2() {
        StudentArrayDeque<Integer> out = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();
        int size = 0;
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            int type = StdRandom.uniform(4);

            if (type == 0) {
                // add last
                size++;
                int num = StdRandom.uniform(8000);
                st.append("addLast(" + num + ')' + '\n');
                out.addLast(num);
                expected.addLast(num);
            } else if (type == 1) {
                // add first
                size++;
                int num = StdRandom.uniform(8000);
                st.append("addFirst(" + num + ')' + '\n');
                out.addFirst(num);
                expected.addFirst(num);
            } else if (type == 2) {
                if (size == 0) continue;
                // remove first
                Integer ex = expected.removeFirst();
                Integer o = out.removeFirst();
                st.append("removeFirst()\n");
                assertEquals(st.toString() , ex , o);
            } else {
                // remove last
                if (size == 0) continue;
                Integer ex = expected.removeLast();
                Integer o = out.removeLast();
                st.append("removeLast()\n");
                assertEquals( st.toString() , ex, o);
            }
        }

    }

}
