package ru.ifmo.second;

import java.util.*;

public class FibonacciHeap<T> {
    private FibonacciHeapNode<T> min;
    private int size;

    public FibonacciHeap() {
        this.min = null;
        this.size = 0;
    }

    public void insert(T data, double key) {
        FibonacciHeapNode<T> node = new FibonacciHeapNode<>(data, key);
        min = mergeLists(min, node);
        size++;
    }

    public T extractMin() {
        if (min == null) return null;
        T minData = min.data;

        if (min.child != null) {
            FibonacciHeapNode<T> child = min.child;
            do {
                child.parent = null;
                child = child.next;
            } while (child != min.child);
            min = mergeLists(min, min.child);
        }

        if (min == min.next) {
            min = null;
        } else {
            min.prev.next = min.next;
            min.next.prev = min.prev;
            min = min.next;
            consolidate();
        }
        size--;
        return minData;
    }

    private void consolidate() {
        Map<Integer, FibonacciHeapNode<T>> degreeTable = new HashMap<>();
        List<FibonacciHeapNode<T>> nodes = new ArrayList<>();
        FibonacciHeapNode<T> current = min;
        if (current != null) {
            do {
                nodes.add(current);
                current = current.next;
            } while (current != min);
        }
        for (FibonacciHeapNode<T> node : nodes) {
            int degree = node.degree;
            while (degreeTable.containsKey(degree)) {
                FibonacciHeapNode<T> other = degreeTable.get(degree);
                if (node.key > other.key) {
                    FibonacciHeapNode<T> temp = node;
                    node = other;
                    other = temp;
                }
                link(other, node);
                degreeTable.remove(degree);
                degree++;
            }
            degreeTable.put(degree, node);
        }
        min = null;
        for (FibonacciHeapNode<T> node : degreeTable.values()) {
            min = mergeLists(min, node);
        }
    }

    private void link(FibonacciHeapNode<T> child, FibonacciHeapNode<T> parent) {
        child.prev.next = child.next;
        child.next.prev = child.prev;
        child.parent = parent;
        if (parent.child == null) {
            parent.child = child;
            child.next = child;
            child.prev = child;
        } else {
            child.next = parent.child;
            child.prev = parent.child.prev;
            parent.child.prev.next = child;
            parent.child.prev = child;
        }
        parent.degree++;
        child.mark = false;
    }

    private FibonacciHeapNode<T> mergeLists(FibonacciHeapNode<T> a, FibonacciHeapNode<T> b) {
        if (a == null) return b;
        if (b == null) return a;
        if (a.key > b.key) {
            FibonacciHeapNode<T> temp = a;
            a = b;
            b = temp;
        }
        FibonacciHeapNode<T> an = a.next;
        FibonacciHeapNode<T> bp = b.prev;
        a.next = b;
        b.prev = a;
        an.prev = bp;
        bp.next = an;
        return a;
    }
}