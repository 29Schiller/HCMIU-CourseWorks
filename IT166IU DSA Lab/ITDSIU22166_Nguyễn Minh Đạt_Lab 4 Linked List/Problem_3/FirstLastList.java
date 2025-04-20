public class FirstLastList{
    Link3 first;               // ref to first item
    private Link3 last;                // ref to last item
    private int size;                // size of the list
// -------------------------------------------------------------
    public FirstLastList()  {           // constructor
        first = null;                  // no items on list yet
        last = null;
        size = 0; // Initialize size to 0
    }
// -------------------------------------------------------------
    public boolean isEmpty()          // true if no links
        { return first==null; }
// -------------------------------------------------------------
    public void insertLast(long value) {
        Link3 newLink = new Link3(value);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
        }
        last = newLink;
        size++; // Increment size
    }
// -------------------------------------------------------------
    public long deleteFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        long temp = first.dData;
        first = first.next;
        if (first == null) {
            last = null;
        }
        size--; // Decrement size
        return temp;
    }
// -------------------------------------------------------------
    public void displayList(){
        Link3 current = first;          // start at beginning
        while(current != null)         // until end of list,
            {
            current.displayLink();      // print data
            current = current.next;     // move to next link
        }
        System.out.println("");
    }
// -------------------------------------------------------------
    public int size() {
        return size; // Return the size of the list
    }
// -------------------------------------------------------------
}// end class FirstLastList
////////////////////////////////////////////////////////////////
