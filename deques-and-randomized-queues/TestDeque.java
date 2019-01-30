/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestDeque {
    private Deque<String> deque = new Deque<String>();

    public void addFirstAndremoveLast(String[] args) {
        emptyDeque();
        for (int i = 0; i < args.length; i++) {
            deque.addFirst(args[i]);
        }
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals(deque.removeLast())) {
                StdOut.printf("failed1");
                break;
            }
        }
        StdOut.printf("success1");
    }

    public void addLastAndRemoveFirst(String[] args) {
        emptyDeque();
        for (int i = 0; i < args.length; i++) {
            deque.addLast(args[i]);
        }
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals(deque.removeFirst())) {
                StdOut.printf("failed2");
                break;
            }
        }
        StdOut.printf("success2");
    }

    public void emptyDeque() {
        while (deque.isEmpty()) {
            deque.removeFirst();
        }
    }

    public static void main(String[] args) {
        String[] input = In.readStrings(args[0]);
        TestDeque test = new TestDeque();
        test.addFirstAndremoveLast(input);
        test.addLastAndRemoveFirst(input);
    }
}
