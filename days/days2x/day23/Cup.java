package days.days2x.day23;

public class Cup {
    public final int value;
    private Cup next;

    public Cup(int val) {
        this.value = val;
        this.next = this;
    }

    public Cup next() {
        return next;
    } 

    public int getValue() {
        return value;
    }

    public int removeNext() {
        Cup old = next;
        next = old.next;
        return old.value;
    }

    public Cup addNext(int value) {
        Cup n = new Cup(value);
        n.next = this.next;
        this.next = n;
        return n;
    }

    public long size() {
        long count = 1;
        Cup c = next;

        while (c != this) {
            count++;
            c = c.next();
        }

        return count;
    }

    public boolean contains(int value) {
        Cup c = next;
        while (c != this) {
            if (c.value == value) {
                return true;
            }
            c = c.next();
        }
        return this.value == value;
    }

    public void addAll(Cup Cups) {
        Cup after = next;

        next = Cups;
        Cup c = Cups;
        while (c.next != Cups) {
            c = c.next;
        }
        c.next = after;
    }

    public Cup removeNext3() {
        Cup first = next;
        
        Cup last = first.next.next;

        next = last.next;
        last.next = first;

        return first;
    }

    public void insert3(Cup first) {
        first.next.next.next = next;
        next = first;
    }

    public boolean contains3(int value) {
        return this.value == value ||
                this.next.value == value ||
                this.next.next.value == value;
    }
}