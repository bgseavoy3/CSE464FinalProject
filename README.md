Project Part 3:
1) Refractors done:
   1. Extract Method - I had constantly reused code that stored characters from my parsed lines in an array list, I changed this to a method callend findWords
   2. Extract Method - I also had reused code for reading new lines, which I changed to a separate new method called readALine
   3. Extract Method - I also had some code that was consistently being used in a while loop that was if statements, so I made it into a method called checkType
   4. Extract Method - I reused a lot of comparisons for the separate expected tokens in parseGraph, so I made a method called typeOfInput that could be called continuously and return an int that
   aligns with the token read most recently
   5. Extract Method - I simply made an if statement that would choose between my DFS and BFS and copied and pasted the code into the if and else, so I put both into separate methods called BFS and DFS

2) I applied the template pattern by creating the class Search, which is an abstract class that holds all  the shared methods between BFS and DFS. The three main things that were in common between
the two were the initialization, obtaining the neighbors of nodes, and the management of the path when exploring the neighbors found. Since one uses a queue and one uses a stack, I decided to use
a list, and simply add to the BFS from the end of it and to the front for the DFS. This matches the FIFO and LIFO nature of both while still reusing code. This took place in the exploreNeighbors
method, as I used this difference to achieve the correct stack and queue properties of both using the same code. The getNeighbors method was the exact same for both, as that method simply finds all
the available neighbors of the current node. Finally, initialization was the same for both, as I used the same data structures for both algorithms. The BFS and DFS classes' main difference is the
search algorithm, which is somewhat similar between both, but I decided to keep separate due to the fields and local variables needed.



Continuous integration - as I did not have this set up for the last project, I set it up for this one, I will add screenshots for that along with expected outputs below 
