// linkList.java
// demonstrates linked list
// to run this program: C>java LinkListApp
////////////////////////////////////////////////////////////////
class Link
   {
   public int iData;              // data item
   public double dData;           // data item
   public Link next;              // next link in list
// -------------------------------------------------------------
   public Link(int id, double dd) // constructor
      {
      iData = id;                 // initialize data
      dData = dd;                 // ('next' is automatically
      }                           //  set to null)
// -------------------------------------------------------------
   public void displayLink()      // display ourself
      {
      System.out.print("{" + iData + ", " + dData + "} ");
      }
   }  // end class Link
////////////////////////////////////////////////////////////////
class LinkList
   {
   private Link first;            // ref to first link on list

// -------------------------------------------------------------
   public LinkList()              // constructor
      {
      first = null;               // no links on list yet
      }
// -------------------------------------------------------------
   public boolean isEmpty()       // true if list is empty
      {
      return (first==null);
      }
// -------------------------------------------------------------
                                  // insert at start of list
   public void insertFirst(int id, double dd)
      {                           // make new link
      Link newLink = new Link(id, dd);
      newLink.next = first;       // newLink --> old first
      first = newLink;            // first --> newLink
      }
// -------------------------------------------------------------
   public Link deleteFirst()      // delete first item
      {                           // (assumes list not empty)
      Link temp = first;          // save reference to link
      first = first.next;         // delete it: first-->old next
      return temp;                // return deleted link
      }
// -------------------------------------------------------------
   public void displayList()
      {
      System.out.print("List (first-->last): ");
      Link current = first;       // start at beginning of list
      while(current != null)      // until end of list,
         {
         current.displayLink();   // print data
         current = current.next;  // move to next link
         }
      System.out.println("");
      }
// -------------------------------------------------------------
// -------------------------------------------------------------
   public Link getFirst(){
      return first;
   }

   // -------------------------------------------------------------
   public Link getLast(){
      if(isEmpty()){
         return null;
      }

      Link current = first;
      while(current.next != null){
         current = current.next;
      }
      return current;
   }
   //--------------------------------------------------------------
   @Override
   public String toString(){
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder sb = new StringBuilder("[");
      Link current = first;
      while(current != null){
         sb.append("{").append(current.iData).append(", ").append(current.dData).append("}");
         if(current.next != null){
            sb.append(", ");
         }
         current = current.next;
      }
      sb.append("]");
      return sb.toString();
   }
}  // end class LinkList
////////////////////////////////////////////////////////////////
public class LinkListApp
   {
   public static void main(String[] args)
      {
      LinkList theList = new LinkList();  // make new list

      theList.insertFirst(22, 2.99);      // insert four items
      theList.insertFirst(44, 4.99);
      theList.insertFirst(66, 6.99);
      theList.insertFirst(88, 8.99);

      theList.displayList();              // display list
      System.out.println("List toString: " + theList);

      if(theList.getFirst() != null){
         System.out.println("First Link: " + theList.getFirst().iData + ", " + theList.getFirst().dData);
     }
     if(theList.getLast() != null){
         System.out.println("Last Link: " + theList.getLast().iData + ", " + theList.getLast().dData);
     }

      while( !theList.isEmpty() )         // until it's empty,
         {
         Link aLink = theList.deleteFirst();   // delete link
         System.out.print("Deleted ");         // display it
         aLink.displayLink();
         System.out.println("");
         }
      theList.displayList();           // display list
      System.out.println("List toString: " + theList);
      System.out.println("First link of empty list: " + theList.getFirst());
      System.out.println("Last link of empty list: " + theList.getLast());
      }  // end main()
   }  // end class LinkListApp
////////////////////////////////////////////////////////////////
