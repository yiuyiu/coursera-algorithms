/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class TestRandomizedQueue {
    private RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

    public void testIterator(int n) {
        emptyDeque();
        for (int i = 0; i < n; i++) {
            int item = StdRandom.uniform(1000);
            rq.enqueue(item);
        }
        System.out.println("iterator1");
        for (int j : rq) {
            System.out.println(j);
        }
        System.out.println("iterator2");
        for (int z : rq) {
            System.out.println(z);
        }
    }

    public void emptyDeque() {
        while (!rq.isEmpty()) {
            rq.dequeue();
        }
    }

    public static void main(String[] args) {
        System.out.println(StdRandom.uniform(2));
        TestRandomizedQueue trq = new TestRandomizedQueue();
        trq.testIterator(10);
    }
}
