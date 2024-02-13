###Deque
##Overview
#A dynamic efficient data structure that executes several operations rapidly, used a sentinel node to make the deque cyclic so it's easier to delete and insert objects, implemented the iterable interface so the user can iterate through the deque elements.
Methods
##1- Addfirst
Adds an item to the front of the deque.

##2- Addlast
Adds an item to the back of the deque.

##3- isEmpty
Returns true if deque is empty, false otherwise.

##4- size
Returns the number of items in the deque.

##5- printDeque
Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.

##6- removeFirst
Removes and returns the item at the front of the deque. If no such item exists, returns null.

##7- removeLast
Removes and returns the item at the back of the deque. If no such item exists, returns null.

##8- get
Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!

##9- equals
Returns whether or not the passed deque parameter is equal to the Deque.It is considered equal if it is a Deque and if it contains the same contents
