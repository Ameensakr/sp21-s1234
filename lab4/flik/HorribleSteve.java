package flik;

import org.junit.Test;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class HorribleSteve {
    @Test
    public void test1() {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
                assertEquals("i:%d not same as j:%d ??" , i , j);
        }
        System.out.println("i is " + i);
    }
    @Test
    public void test2() {
        int i = 1;
        for (int j = 1; i < 500; ++i, ++j) {
            assertEquals("i:%d not same as j:%d ??" , i , j);
        }
        System.out.println("i is " + i);
    }




}
