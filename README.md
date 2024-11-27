Project Part 3:
Refractors done:
Extract Method - I had constatly reused code that stored characters from my parsed lines in an array list, I changed this to a method callend findWords
Extract Method - I also had resued code for reading new lines, which I changed to a seperate new method called readALine
Extract Method - I also had some code that was consistenly being used in a while loop that was if statements, so I made it into a method called checkType
Extract Method - I reused a lot of comparisons for the seperate expected tokens in parseGraph, so I made a method called typeOfInput that could be called continuously and return an int that
aligns with the token read most recently
Extract Method - I simply made an if statement that would choose between my DFS and BFS and copied and pasted the code into the if and else, so I put both into separate methods called BFS and DFS



Continuous integration - as I did not have this set up for the last project, I set it up for this one, I will add screenshots for that along with expected outputs below 
