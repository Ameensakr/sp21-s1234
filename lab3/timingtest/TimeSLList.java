package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer>Ns = new AList<>();
        AList<Double>t = new AList<>();
        AList<Integer>op = new AList<>();
        for (int N = 1000 ; N <= 128000 ; N*=2)
        {
            SLList<Integer> sl = new SLList<>();
            for (int i = 0; i < N ; i++)
            {
                sl.addLast(i);
            }
            Stopwatch st = new Stopwatch();
            int M = 10000;
            for (int i = 1; i <= M ; i++)sl.getLast();
            Double time = st.elapsedTime();

            Ns.addLast(N);
            t.addLast(time);
            op.addLast(M);

        }
        printTimingTable(Ns , t , op);

    }

}
