
/**
 * Implements various merge style algorithms.
 *
 * Completion time: About an hour.
 *
 * @author Ian Rodovsky, Acuna, Sedgewick and Wayne
 * @verison 06/08/2021
 */
import java.util.Random;

public class RodovskyMerging {

    /**
     * Entry point for sample output.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue q1 = new ListQueue();
        q1.enqueue("T");
        q1.enqueue("R");
        q1.enqueue("O");
        q1.enqueue("L");
        q1.enqueue("E");
        Queue q2 = new ListQueue();
        q2.enqueue("X");
        q2.enqueue("S");
        q2.enqueue("P");
        q2.enqueue("M");
        q2.enqueue("E");
        q2.enqueue("A");
        Queue q3 = new ListQueue();
        q3.enqueue(20);
        q3.enqueue(17);
        q3.enqueue(15);
        q3.enqueue(12);
        q3.enqueue(5);
        Queue q4 = new ListQueue();
        q4.enqueue(18);
        q4.enqueue(16);
        q4.enqueue(13);
        q4.enqueue(12);
        q4.enqueue(4);
        q4.enqueue(1);

        //Q1 - sample test cases
        Queue merged1 = mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = mergeQueues(q3, q4);
        System.out.println(merged2.toString());

        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a);
        show(a);

        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        shuffle(b);
        show(b);

        shuffle(b);
        show(b);
    }

    public static Queue mergeQueues(Queue<Comparable> q1, Queue<Comparable> q2) {
        Queue newQueue = new ListQueue();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (less((Comparable) q1.front(), (Comparable) q2.front())) {
                newQueue.enqueue(q2.dequeue());
            } else {
                newQueue.enqueue(q1.dequeue());
            }
        }
        while (!q1.isEmpty()) {
            newQueue.enqueue(q1.dequeue());
        }
        while (!q2.isEmpty()) {
            newQueue.enqueue(q2.dequeue());
        }
        return newQueue;
    }

    public static void sort(Comparable[] a) {
        Comparable[] b = mergesort(a);
        for (int idx = 0; idx < b.length; idx++) {
            a[idx] = b[idx];
        }
    }

    public static Comparable[] mergesort(Comparable[] a) {
        if (a.length <= 1) {
            return a;
        }
        int mid = a.length / 2;
        Comparable[] frontHalf = new Comparable[mid];
        Comparable[] backHalf = new Comparable[a.length - mid];
        for (int idx = 0; idx < mid; idx++) {
            frontHalf[idx] = a[idx];
        }
        for (int idx = mid, index = 0; idx < a.length; idx++, index++) {
            backHalf[index] = a[idx];
        }
        frontHalf = mergesort(frontHalf);
        backHalf = mergesort(backHalf);

        return merge(frontHalf, backHalf);
    }

    public static Comparable[] merge(Comparable[] a, Comparable[] b) {
        Comparable[] result = new Comparable[a.length + b.length];
        int idxA = 0, idxB = 0, idxResult = 0;
        while (idxA < a.length || idxB < b.length) {
            if (idxA < a.length && idxB < b.length) {
                if (((Comparable) a[idxA]).compareTo(b[idxB]) < 0) {
                    result[idxResult++] = a[idxA++];
                } else if (((Comparable) a[idxA]).compareTo(b[idxB]) > 0) {
                    result[idxResult++] = b[idxB++];
                } else {
                    result[idxResult++] = a[idxA++];
                    result[idxResult++] = b[idxB++];
                }
            } else if (idxA < a.length && idxB >= b.length) {
                result[idxResult++] = a[idxA++];
            } else if (idxB < b.length && idxA >= a.length) {
                result[idxResult++] = b[idxB++];
            }
        }
        return result;
    }

    public static void shuffle(Object[] a) {
        shuffle(a, 0, a.length - 1);
    }

    public static void shuffle(Object[] a, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        int randomIdx = (new Random()).nextInt(a.length);
        Object midElement = a[mid];
        a[randomIdx] = midElement;
        shuffle(a, start, mid - 1);
        shuffle(a, mid + 1, end);
    }

    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a) {
            System.out.print(a1 + " ");
        }

        System.out.println();
    }

    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }

        return true;
    }
}
