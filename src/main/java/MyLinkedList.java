import java.util.Collection;
import java.util.Iterator;

public class MyLinkedList <T extends Comparable<? super T>> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public MyLinkedList() {}

    public MyLinkedList(Collection<? extends T> c) {
        addAll(c);
    }

    public void add(T data) {
        Node<T> node;
        if (size == 0) {
            node = new Node<>(null, data, null);
            head = node;
        } else {
            node = new Node<>(tail, data, null);
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @SuppressWarnings("unchecked")
    public void addAll(Collection<? extends T> other) {
        Object[] array = other.toArray();
        for (Object o : array) {
            add((T) o);
        }
    }

    public T get(int index) {
        Node<T> node = getNode(index);
        return node.data;
    }

    public void remove(int index) {
        Node<T> node = getNode(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private Node<T> getNode(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node;
        if(index > size / 2) {
            node = tail;
            for(int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        } else {
            node = head;
            for(int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone() {
        MyLinkedList<T> clone;
        try {
            clone = (MyLinkedList<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        clone.size = 0;
        clone.head = null;
        clone.tail = null;

        for(Node<T> x = head; x != null; x = x.next) {
            clone.add(x.data);
        }
        return clone;
    }

    public void sort(boolean isDescending) {
        boolean isSorted = true;
        for(Node<T> x = head; x.next != null; x = x.next) {
            if(x.data.compareTo(x.next.data) > 0 && !isDescending || x.data.compareTo(x.next.data) < 0 && isDescending) {
                isSorted = false;
                break;
            }
        }
        if(!isSorted) {
            for(Node<T> x = head; x.next != null; x = x.next) {
                for(Node<T> y = head; y.next != null; y = y.next) {
                    if(y.data.compareTo(y.next.data) > 0 && !isDescending || y.data.compareTo(y.next.data) < 0 && isDescending) {
                        T tmp = y.data;
                        y.data = y.next.data;
                        y.next.data = tmp;
                    }
                }
            }
        }
    }

    private static class Node<T extends Comparable<? super T>> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
