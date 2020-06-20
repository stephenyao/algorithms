import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        this.last = null;
        this.first = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null to queue");
        Node newNode = new Node();
        newNode.item = item;
        newNode.previous = null;
        if (size == 0) {
            first = newNode;
            last = first;
        }
        else {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null to queue");
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;

        if (size == 0) {
            last = newNode;
            first = last;
        }
        else {
            newNode.previous = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = first.item;
        first = first.next;
        if (first != null) first.previous = null;
        size--;
        if (size == 0) last = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = last.item;
        last = last.previous;
        if (last != null) last.next = null;
        size--;
        if (size == 0) first = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Reached end of iterator");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> addFirstDeque = new Deque<>();
        addFirstDeque.addFirst(4);
        addFirstDeque.removeFirst();

        for (Integer i : addFirstDeque) {
            System.out.println(String.format("addFirstQueue: %d", i));
        }
        //
        // int removeFirst = addFirstDeque.removeFirst();
        // int removeSecond = addFirstDeque.removeFirst();
        // int removeThird = addFirstDeque.removeFirst();
        //
        // System.out.println(
        //         String.format("addFirstDeque: deque: %d, deque: %d, deque: %d", removeFirst,
        //                       removeSecond, removeThird));
        //
        // Deque<Integer> addLastDeque = new Deque<>();
        // addLastDeque.addLast(1);
        // addLastDeque.addLast(2);
        // addLastDeque.addLast(3);
        //
        // for (Integer i : addLastDeque) {
        //     System.out.println(String.format("addLastQueue: %d", i));
        // }
        //
        // System.out.println(String.format("addLastDeque: deque: %d, deque: %d, deque: %d",
        //                                  addLastDeque.removeLast(), addLastDeque.removeLast(),
        //                                  addLastDeque.removeLast()));
        //
        // System.out.println("===== Testing deque first last ======");
        // Deque<Integer> firstLastQueue = new Deque<>();
        // firstLastQueue.addFirst(1);
        // System.out.println("===== Add first 1 ======");
        // firstLastQueue.addFirst(2);
        // System.out.println("===== Add first 2 ======");
        // firstLastQueue.addLast(3);
        // System.out.println("===== Add last 3 ======");
        // int firstLastQueueOne = firstLastQueue.removeFirst();
        // System.out.println(String.format("===== remove first: %d ======", firstLastQueueOne));
        // int firstLastQueueTwo = firstLastQueue.removeLast();
        // System.out.println(String.format("===== remove last: %d ======", firstLastQueueTwo));
        // firstLastQueue.addFirst(4);
        // System.out.println("===== Add first 4 ======");
        // firstLastQueue.addLast(5);
        // System.out.println("===== Add last 5 ======");
        // int firstLastQueueThree = firstLastQueue.removeFirst();
        // System.out.println(String.format("===== remove first: %d ======", firstLastQueueThree));
        // int firstLastQueueFour = firstLastQueue.removeFirst();
        // System.out.println(String.format("===== remove first: %d ======", firstLastQueueFour));
        // int firstLastQueueFive = firstLastQueue.removeLast();
        // System.out.println(String.format("===== remove last: %d ======", firstLastQueueFive));
    }

}
