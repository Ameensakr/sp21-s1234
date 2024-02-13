# Deque

## Overview
Developed and implemented efficient Deque data structures utilizing both linked list and array-based approaches, optimizing performance for various use cases.

To read more about Deque: [Deque](https://en.wikipedia.org/wiki/Double-ended_queue)


generated tests using random operations using the `StdRandom` function that helps me to make everything randomly. Use `JUnit library` to print to user useful message to understand what is the error.

## Methods
1. `addFirst`: Adds an item to the front of the deque in O(1).
2. `addLast`: Adds an item to the back of the deque in O(1).
3. `isEmpty`: Returns true if deque is empty, false otherwise.
4. `size`: Returns the number of items in the deque.
5. `printDeque`: Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
6. `removeFirst`: Removes and returns the item at the front of the deque. If no such item exists, returns null in O(1).
7. `removeLast`: Removes and returns the item at the back of the deque. If no such item exists, returns null in O(1).
8. `get`: Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
9. `equals`: Returns whether or not the passed deque parameter is equal to the Deque. It is considered equal if it is a Deque and if it contains the same contents.
