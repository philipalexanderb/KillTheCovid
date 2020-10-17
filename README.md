# KillTheCovid
Anywhr Coding Challenge
## Instructions:
1. Go into the folder directory and compile the class using the following command: 
	"javac KillTheCovid.java"

2. Execute the file by using the following command:
	"java KillTheCovid"

3. You will be asked if you want to initialize arrays of hexagon as in diagram 1.4. Type "Y" or "N"

4. Subsequent interactions will be done from the command line (System input)

## Types of queries:

1. Querying the neighbours of a particular hexagon:
	"query A" where A is the hexagon of interest.
	
	Example usage: 
	
	- given an array of hexagons like given in diagram 1.4, 
	
		"query A" will yield: "Here are the neighbours for A: [1, C] [3, Z]"
	
		"query Y" will yield: "Here are the neighbours for Y: [2, W] [3, V] [4, X] [5, Z]"

2. Inserting a hexagon into the array of hexagons:
	"insert X Y B" where X is the new hexagon to be inserted,
			     Y is the neighbouring hexagon to which the new hexagon is attached to,
			     B is the border on hexagon Y
	
	Example usage:
	
	- given an array of hexagons like given in diagram 1.4,
	
		"insert L C 2" will yield: "Inserted! Here are the neighbours for L: [2, E] [5, C]"
		
		"insert K Y 0" will yield: "Inserted! Here are the neighbours for K: [0, C] [1, L] [3, Y] [4, Z] [5, A]"

3. Removing a hexagon from the array of hexagons:
	
	"remove X" where X is the hexagon to be removed
	
	Example usage:
	
	- given an array of hexagons like given in diagram 1.4,
	
		"remove Y" will yield: "Y has been removed from: [W, 5] [V, 0] [X, 1] [Z, 2]"

4. Terminating the program:
	
	"quit"


## Features:
1. all the name for hexagons will be converted to uppercase
2. instructions can consist of lower or upper case characters

## Limit:
1. queries must be of the exact format as given above, otherwise, program may not run as expected and may not terminate.
2. Invalid queries such as "inser A B 3", "quer 2", "rm 5" will result in program termination.
