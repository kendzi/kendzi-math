package kendzi.math.geometry.skeleton.path;

public class PathQueueNode<T extends PathQueueNode<T>> {

    private PathQueueNode<T> next;
    private PathQueueNode<T> previous;

    protected PathQueue<T> list;

    public PathQueue<T> list() {
        return this.list;
    }

    public void addPush(PathQueueNode<T> node) {
        this.list.addPush(this, node);
    }

    public boolean isEnd() {
        return this.next == null || this.previous == null;
    }

    public PathQueueNode<T> next(PathQueueNode<T> pPrevious) {
        if (pPrevious == null || pPrevious == this) {
            if (!isEnd()) {
                throw new RuntimeException("Can't get next element don't knowing previous one. Directon is unknown");
            } else if (this.next == null) {
                return this.next;
            } else if (this.previous == null) {
                return this.previous;
            } else {
                return null;
            }
        }

        if (this.next == pPrevious) {
            return this.previous;
        } else {
            return this.next;
        }
    }

    public PathQueueNode<T> prevoius() {
        if (!isEnd()) {
            throw new RuntimeException("Can get previous only from end of queue");
        }

        if (this.next == null) {
            return this.previous;
        } else if (this.previous == null) {
            return this.next;
        } else {

        }
        return null;
    }

    public PathQueueNode<T> addQueue(PathQueueNode<T> queue) {

        if (this.list == queue.list) {
            // TODO ? cycle ?!
            return null;
        }

        PathQueueNode<T> currentQueue = this;

        PathQueueNode<T> current = queue;
        PathQueueNode<T> next = null;

        while (current != null) {

            next = current.pop();
            currentQueue.addPush(current);
            currentQueue = current;

            current = next;
        }

        return currentQueue;

    }

    public PathQueueNode<T> findEnd() {
        if (isEnd()) {
            return this;
        }

        PathQueueNode<T> current = this;
        while (current.previous != null) {
            current = current.previous;
        }
        return current;

    }

    public PathQueueNode<T> pop() {
        return this.list.pop(this);
    }

    public PathQueueNode<T> getNext() {
        return next;
    }

    public void setNext(PathQueueNode<T> next) {
        this.next = next;
    }

    public PathQueueNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(PathQueueNode<T> previous) {
        this.previous = previous;
    }

    public PathQueue<T> getList() {
        return list;
    }

    public void setList(PathQueue<T> list) {
        this.list = list;
    }

}
