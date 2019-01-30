/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    // memory use：对象的16字节开销，引用类型实例变量两个16字节，int型实例变量 8字节，
    // 4个填充字节，每个元素需要72字节，一个Node对象48字节（16字节的对象开销，指向Item和node对象以及node对象的3个指针
    // 加起来需要24字节，8字节额外开销）和一个比如Integer对象24字节
    // 课件中是这么算的，但是实际作业中Integer对象是不计算在作业中的内存需求里，所以我的实际内存占用是48n+40
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public Deque() {

    }                        // construct an empty deque

    public boolean isEmpty() {
        return n == 0;
    }                 // is the deque empty?

    public int size() {
        return n;
    }                      // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        if (n >= 1) {
            first.prev = newNode;
        }
        first = newNode;
        if (n == 0) {
            last = first;
        }
        n++;
    }        // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = last;
        if (n >= 1) {
            last.next = newNode;
        }
        last = newNode;
        if (n == 0) {
            first = newNode;
        }
        n++;
    }        // add the item to the end

    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item oldFirstItem = first.item;
        Node next = first.next;
        first = first.next;
        if (n > 1) {
            next.prev = null;
        }

        if (n == 1) {
            last = null;
        }
        n--;
        return oldFirstItem;
    }             // remove and return the item from the front

    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item oldLastItem = last.item;
        Node prev = last.prev;
        if (n > 1) {
            prev.next = null;
        }
        last = prev;
        if (n == 1) {
            first = null;
        }
        n--;
        return oldLastItem;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }       // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    // unit testing (optional)

    public static void main(String[] args) {

    }
}
