# Deque

## Overview
A double-ended queue, also known as deque, is a linear data structure — or more abstractly an ordered collection of items. Deques are a generalization of the queue data structure. Therefore, elements can be added to the back and removed at the front. However, deques can also add elements to the front and remove them from the back, which distinguishes them from queues. In other words, deques are less restricted when adding and removing elements.
to read more about Deque: https://en.wikipedia.org/wiki/Double-ended_queue#:~:text=the%20technical%20details.%20(-,April%202022),)%20or%20back%20(tail).

## Methods
1. `addFirst`: Adds an item to the front of the deque.
2. `addLast`: Adds an item to the back of the deque.
3. `isEmpty`: Returns true if deque is empty, false otherwise.
4. `size`: Returns the number of items in the deque.
5. `printDeque`: Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
6. `removeFirst`: Removes and returns the item at the front of the deque. If no such item exists, returns null.
7. `removeLast`: Removes and returns the item at the back of the deque. If no such item exists, returns null.
8. `get`: Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
9. `equals`: Returns whether or not the passed deque parameter is equal to the Deque. It is considered equal if it is a Deque and if it contains the same contents.
