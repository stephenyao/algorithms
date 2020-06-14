import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (size() == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
        int position = StdRandom.uniform(n);
        swap(position, n - 1);

    }

    private void swap(int first, int second) {
        Item item = items[first];
        items[first] = items[second];
        items[second] = item;
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            newArray[i] = items[i];
        }
        items = newArray;
    }


    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = items[n - 1];
        items[n - 1] = null;
        n--;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int position = StdRandom.uniform(n);
        return items[position];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] randomizedItems;
        int current;

        public RandomQueueIterator() {
            randomizedItems = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                randomizedItems[i] = items[i];
            }

            StdRandom.shuffle(randomizedItems);
            current = 0;
        }

        public boolean hasNext() {
            return current != n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Reached end of iterator");
            Item item = randomizedItems[current];
            current++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Test deque");
        RandomizedQueue<Integer> randomQueue = testQueue();
        int element = randomQueue.dequeue();
        System.out.println(String.format("Dequeued: %d", element));

        RandomizedQueue<Integer> queueIteratorTest = testQueue();
        for (int i : queueIteratorTest) {
            System.out.println(String.format("Queue iterator: %d", i));
        }
    }

    public static RandomizedQueue<Integer> testQueue() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        return queue;
    }

}
