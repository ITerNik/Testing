package ru.ifmo.second;

public class FibonacciHeapNode<T> {
    T data;
    double key;
    int degree;
    FibonacciHeapNode<T> parent, child, next, prev;
    boolean mark;

    public FibonacciHeapNode(T data, double key) {
        this.data = data;
        this.key = key;
        this.next = this;
        this.prev = this;
    }
}
