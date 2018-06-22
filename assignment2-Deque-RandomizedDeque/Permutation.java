import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            que.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            System.out.println(que.dequeue());
        }
    }
}
