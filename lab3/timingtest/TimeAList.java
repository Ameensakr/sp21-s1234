package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        Stopwatch temp = new Stopwatch();
        AList<Integer> Ns = new AList<>();
        AList<Integer> opCounts = new AList<>();
        AList<Double> time = new AList<>();
        for (int i = 1000 ; i <= 128000 ; i *= 2)
        TimeAList.push(i , Ns , time , opCounts);
        TimeAList.printTimingTable(Ns , time , opCounts);
    }


    public static void push(int n , AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts)
    {
        Stopwatch temp = new Stopwatch();
        AList<Integer> test = new AList<>();
        for (int i = 0 ; i < n; i++)
            test.addLast(i);
        Double timeInSeconds = temp.elapsedTime();
        times.addLast(timeInSeconds);
        Ns.addLast(n);
        opCounts.addLast(n);
    }
}
