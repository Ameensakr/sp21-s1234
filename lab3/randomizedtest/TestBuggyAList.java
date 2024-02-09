package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove()
    {
        AListNoResizing<Integer> trust = new AListNoResizing<>();
        BuggyAList<Integer> bug = new BuggyAList<>();
        trust.addLast(4);
        trust.addLast(5);
        trust.addLast(6);
        bug.addLast(4);
        bug.addLast(5);
        bug.addLast(6);

        for (int i = 0 ; i < 3 ; i++)
        {
            int expected = trust.removeLast();
            int happened = bug.removeLast();
            Assert.assertEquals(expected , happened);
        }
    }
    @Test
    public void randomizedTest()
    {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> bug = new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                bug.addLast(randVal);

            } else if (operationNumber == 1 && L.size() > 0) {
                // remove last
                int expected = L.removeLast();
                int happened =  bug.removeLast();
                Assert.assertEquals(expected , happened);
            }
            else if(operationNumber == 2 && L.size() > 0)
            {
                // getLast
                int expected = L.getLast();
                int happened = bug.getLast();
                Assert.assertEquals(expected , happened);
            }
        }
    }


}
