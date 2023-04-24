package cz.jngrss.shipmonkdemo.model;

import lombok.Data;

@Data
public class Node<T> {

    private T value;
    private Node<?> next;

    private Node() {
    }

    private Node(T value) {
        this.value = value;
    }

    public static Node<String> getInstance(String value) {
        return new Node<>(value);
    }

    public static Node<Integer> getInstance(Integer value) {
        return new Node<>(value);
    }

    public static <T> Node<T> getInstance(T value) {
        return new Node<>(value);
    }

    @Override
    public String toString() {
        if (next != null) {
            return this.value.toString() + " -> " + next.toString();
        } else {
            return this.value.toString();
        }
    }

}
