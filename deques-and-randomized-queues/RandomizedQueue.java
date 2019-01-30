/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }               // construct an empty randomized queue

    public boolean isEmpty() {
        return n == 0;
    }               // is the randomized queue empty?

    private void resize(int len) {
        Item[] temp = (Item[]) new Object[len];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

    public int size() {
        return n;
    }                     // return the number of items on the randomized queue

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        n++;
        if (n == q.length) {
            resize(q.length * 2);
        }
        q[n - 1] = item;
        // 插入的时候任意插入一个位置，原位置的元素到最后
        int randomNum = StdRandom.uniform(n);
        Item oldEle = q[randomNum];
        q[randomNum] = item;
        q[n - 1] = oldEle;
    }          // add the item

    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item removeItem = q[n - 1];
        n--;
        q[n] = null;
        if (n == q.length / 4) {
            resize(q.length / 2);
        }
        return removeItem;
    }                // remove and return a random item

    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int randomNum = StdRandom.uniform(n);
        return q[randomNum];
    }                 // return a random item (but do not remove it)

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }        // return an independent iterator over items in random order

    private class RandomIterator implements Iterator<Item> {
        private int currentTotal = n;
        private Item[] temp = (Item[]) new Object[n];

        public RandomIterator() {
            for (int i = 0; i < n; i++) {
                // 相当于和上面一样的enque过程
                Item inserted = q[i];
                temp[i] = inserted;
                int rn = StdRandom.uniform(i + 1);
                Item oldItem = temp[rn];
                temp[rn] = inserted;
                temp[i] = oldItem;
            }
        }

        public boolean hasNext() {
            return currentTotal > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Item returnItem = temp[currentTotal - 1];
            currentTotal--;
            return returnItem;
        }
    }

    public static void main(String[] args) {

    }   // unit testing (optional)
}
