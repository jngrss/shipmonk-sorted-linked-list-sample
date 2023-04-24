package cz.jngrss.shipmonkdemo.model;


public class SortedLinkedList<T> {

    private Node<?> head;
    private Node<?> tail;
    private long size = 0L;

    public SortedLinkedList() {
    }

    public SortedLinkedList(T value) {
        this.head = Node.getInstance(value);
        this.tail = head;
        this.size++;
    }

    public SortedLinkedList(Node<T> node) {
        this.head = node;
        this.tail = head;
        this.size++;
    }

    /**
     * Adds new node with provided value to end of linked list, returns list's head node.
     * The list is not sorted after node is appended. Invoke <code>sortList()</code>
     * to force sort the list.
     *
     * @param value value to add to list
     * @return list's head node
     */
    public Node<?> add(T value) {
        if (head == null) {
            addNode(value);
            return null;
        }
        append(value);
        return head;
    }

    /**
     * Adds new node with provided value to list, sorts the list and return list's head node.
     *
     * @param value value to add to list
     * @return list's head node
     */
    public Node<?> addAndSort(T value) {
        addNode(value);
        return sortList();
    }

    /**
     * Sorts the list, returns list's head node.
     *
     * @return list's head node
     */
    public Node<?> sortList() {
        return sortList(head);
    }

    /**
     * Returns list size.
     *
     * @return list size
     */
    public long getSize() {
        return size;
    }

    private void addNode(T value) {
        Node<?> node = Node.getInstance(value);
        if (head == null) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;
        size++;
    }

    /**
     * Returns list's head node.
     *
     * @return list's head node
     */
    public Node<?> getHead() {
        return head;
    }

    private void prepend(T value) {
        Node<T> node = Node.getInstance(value);
        node.setNext(head);
        head = node;
        size++;
    }

    private void append(T value) {
        Node<T> node = Node.getInstance(value);
        tail.setNext(node);
        tail = node;
        size++;
    }

    private void addAfterNode(Node<?> addAfterNode, T value) {
        Node<?> node = Node.getInstance(value);
        node.setNext(addAfterNode.getNext());
        addAfterNode.setNext(node);
        size++;
    }

    private Node<?> sortList(Node<?> node) {
        if (node == null || node.getNext() == null) {
            return node;
        }

        Node<?>[] halves = splitList(node);
        Node<?> firstHalfNode = halves[0];
        Node<?> secondHalfNode = halves[1];

        firstHalfNode = sortList(firstHalfNode);
        secondHalfNode = sortList(secondHalfNode);

        return mergeSortedList(firstHalfNode, secondHalfNode);
    }

    private static Node<?>[] splitList(Node<?> baseNode) {
        if (baseNode == null || baseNode.getNext() == null) {
            return new Node[]{baseNode, null};
        }

        Node<?> slowRef = baseNode;
        Node<?> fastRef = baseNode.getNext();

        // advance fastRef by two nodes and slowRef by one node
        while (fastRef != null) {
            fastRef = fastRef.getNext();
            if (fastRef != null) {
                slowRef = slowRef.getNext();
                fastRef = fastRef.getNext();
            }
        }

        // slowRef is at the midpoint in the list, split it in two at that point
        Node<?>[] nodeArray = new Node[]{baseNode, slowRef.getNext()};
        slowRef.setNext(null);

        return nodeArray;
    }

    private static Node<?> mergeSortedList(Node<?> firstHalfNode, Node<?> secondHalfNode) {
        if (firstHalfNode == null) {
            return secondHalfNode;
        } else if (secondHalfNode == null) {
            return firstHalfNode;
        }

        // a result pointer that will point to the merged list.
        Node<?> result;

        switch (firstHalfNode.getValue()) {
            case String ignored -> {
                if (((String) firstHalfNode.getValue()).compareTo(((String) secondHalfNode.getValue())) < 0) {
                    result = firstHalfNode;
                    result.setNext(mergeSortedList(firstHalfNode.getNext(), secondHalfNode));
                } else {
                    result = secondHalfNode;
                    result.setNext(mergeSortedList(firstHalfNode, secondHalfNode.getNext()));
                }
            }

            case Integer ignored -> {
                if (((Integer) firstHalfNode.getValue()).compareTo(((Integer) secondHalfNode.getValue())) < 0) {
                    result = firstHalfNode;
                    result.setNext(mergeSortedList(firstHalfNode.getNext(), secondHalfNode));
                } else {
                    result = secondHalfNode;
                    result.setNext(mergeSortedList(firstHalfNode, secondHalfNode.getNext()));
                }
            }

            default -> throw new IllegalArgumentException("No such type allowed!");
        }

        return result;
    }

    @Override
    public String toString() {
        return head.toString();
    }

}
