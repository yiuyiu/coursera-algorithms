/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomQue = new RandomizedQueue<String>();
        int capacity = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String a = StdIn.readString();
            randomQue.enqueue(a);
        }
        for (int i = 0; i < capacity; i++) {
            StdOut.println(randomQue.dequeue());
        }

    }
}
