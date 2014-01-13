package kendzi.math.geometry.skeleton.path;

import java.util.Iterator;

public class PathQueue<T extends PathQueueNode<T>> implements Iterable<T> {

    private int size = 0;
    private PathQueueNode<T> first = null;

    public void addPush(PathQueueNode<T> node, PathQueueNode<T> newNode) {
        if (newNode.list != null) {
            throw new IllegalStateException("Node is allready assigned to different list!");
        }

        if (node.getNext() != null && node.getPrevious() != null) {
            throw new IllegalStateException(
                    "Can't push new node. Node is inside a Quere. New node can by added only at the end of queue.");
        }

        newNode.list = this;
        this.size++;

        if (node.getNext() == null) {

            newNode.setPrevious(node);
            newNode.setNext(null);

            node.setNext(newNode);

        } else {

            newNode.setPrevious(null);
            newNode.setNext(node);

            node.setPrevious(newNode);
        }
    }

    public int size() {
        return this.size;
    }

    public void addFirst(T node) {
        if (node.list != null) {
            throw new IllegalStateException("Node is already assigned to different list!");
        }

        if (this.first == null) {
            this.first = node;

            node.list = this;
            node.setNext(null);
            node.setPrevious(null);

            this.size++;

        } else {

            throw new IllegalStateException("First element already exist!");
        }

    }

    public PathQueueNode<T> pop(PathQueueNode<T> node) {
        if (node.list != this) {
            throw new IllegalStateException("Node is not assigned to this list!");
        }

        if (this.size <= 0) {
            throw new IllegalStateException("List is empty can't remove!");
        }
        if (!node.isEnd()) {
            throw new IllegalStateException("Can pop only from end of queue!");
        }

        node.list = null;

        PathQueueNode<T> previous = null;

        if (this.size == 1) {
            this.first = null;

        } else {

            if (this.first == node) {
                if (node.getNext() != null) {
                    this.first = node.getNext();
                } else if (node.getPrevious() != null) {
                    this.first = node.getPrevious();
                } else {
                    throw new RuntimeException("Ups ?");
                }

            }
            if (node.getNext() != null) {
                node.getNext().setPrevious(null);
                previous = node.getNext();
            } else if (node.getPrevious() != null) {
                node.getPrevious().setNext(null);
                previous = node.getPrevious();
            }
        }

        // XXX
        node.setPrevious(null);
        node.setNext(null);

        this.size--;

        return previous;
    }

    public PathQueueNode<T> first() {
        return this.first;
    }

    public class PathQueueIterator implements Iterator<T> {

        int i = 0;

        @SuppressWarnings("unchecked")
        T current = (T) (PathQueue.this.first != null ? PathQueue.this.first.findEnd() : null);

        @Override
        public boolean hasNext() {
            return this.i < PathQueue.this.size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            T ret = this.current;
            this.current = (T) this.current.getNext();
            this.i++;
            return ret;
        }

        @Override
        public void remove() {
            throw new RuntimeException("TODO");
        }

    }

    /**
     * Make one round around list. Start from first element end on last.
     * 
     * @return iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new PathQueueIterator();
    }

    public int getSize() {
        return size;
    }

}
