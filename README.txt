=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: smehta1
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D Arrays: in order to model the 20 x 20 grid, and to display the pieces each player will be 
  placing on the grid, a 2D array is perhaps the most efficient and effective data structure. The 
  grid will be JPanels, each of different colors. In order to organize the colors for each of the 
  JPanels, I plan on utilizing a 20 x 20 2D array composed of Color objects, which will reflect the 
  colors on the grid. Initially, the grid will be empty (no pieces, and hence no colors), and in 
  turn, the 2D array will be instantiated with Color objects representing gray. After each turn, 
  the 2D array (and visually, the grid), will be updated to mirror the addition of the game pieces 
  to the board. The game will end after every piece remaining in each player's set of pieces are 
  traversed and none can be placed on the board without violating Blokus rules. 

  2. Collections: I used the Set (Hashset) collection to organize each player’s pieces since the 
  pieces need not be ordered and each piece within a player’s set of pieces is unique, which makes 
  it the most efficient data structure for the task (O(1) best lookup-time). After a player places a 
  piece down onto the grid, the particular piece will be removed from the player’s Set (holding the 
  set of pieces). Because the HashSet will be holding pieces, I developed a Piece class which 
  creates Piece objects, holding a particular structure (represented as a 2D array) as well as a 
  particular color.
  Moreover, I implemented LinkedLists in order to organize snapshots of the grid/2D array after 
  every move is completed during the game in case players wish to undo their moves. 
  Methods associated with the List structure provide an efficient way to access objects near its 
  head and tail, and therefore, using a LinkedList for undo operations would be effective. Undoing
  will, as mentioned, undo every completed move (uncompleted moves are not undone as a player
  can simply reselect and place another piece down---and therefore an Undo for such scenarios
  is unnecessary.)
  

  3. File I/O: In case players wish to pause and resume the current game for later, the current 
  board will be saved along with each players’ set of pieces into a text file. I originally 
  intended to use Apache's POI library and store the board into an Excel file, but I realized
  that using a text file would be much more efficient and it would enable me to store the board's
  colors in one long string (by binary conversion). Therefore, I simply took every Color object 
  of the Blokus board (2D array), extracted an RGB value, converted it to binary, and added it to 
  the file. Because the RGB value is an int or 4 bytes, it is always 32 digits long, and so reading
  and parsing a constant 32 bits at a time was convenient. Furthermore, I also stored the player's
  pieces by name, as a long string, and by parsing two characters at time, I was able to use the 
  GamePieces enum to swiftly recreate the Piece objects and add them into the player's set of 
  pieces; in turn, the score could be dynamically calculated. Moreover, I stored the current
  player's color and the turn number (in hex), and in binary, the number(s) of the player(s) who
  won (in case a game that was completed was loaded). The two benefits of using the binary/hex 
  encoding were (1) being able to know exactly when a piece of data begins and ends 
  and (2) a type of "encapsulation", as it is difficult to modify hex/binary knowingly.
  

  4.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
Board: creates the Blokus board by creating a square grid of Square objects of length 
20 (the size of a Blokus board) based on the 2D array representing the Board in State.java. 
Listens for mouseClick/mouseDrag events across JPanels so that the position of the mouseClick/
mouseDrag can be relayed to State.java to update the 2D array representing the board. 
InfoWindowOpener: an ActionListener that opens any of the tabs below Help ("About", "Instructions",
etc.) by creating a JDialog with text specified by values within the AboutExcerpts enum.
OpenGame: an ActionListener that will create a JFileDialog, open, and parse the information
within the file using State.java methods in order to recreate the game.
SaveGame: an ActionListener that will create a JFileDialog, open, and store the information
of the current game (board's colors, current player, players' pieces, turn number, and game status)
so that it can be loaded later to continue play.
Piece: extends JComponent to have a graphics representation, but also an internal representation 
modelled by a 2D array and color object. The 2D array specified on creation is taken from the 
GamePieces enum, which contains the structures/names of all Blokus pieces. 
Player: fields include the player's name, set of pieces (Set<Piece> object), identifying number, 
color, and score (which is determined dynamically based on the pieces in the Set<Piece> object).
Four players are created when the game begins. Includes methods for acquiring the set of pieces,
getting player's color, as well as other setter/getter methods.
PlayerPieceSet: extends JComponent; when next turn is pressed, the current player is obtained, 
and their pieces within their Set<Piece> object are painted. An ActionListener is added to 
each of these pieces to indicate if they are selected; if they are selected, the State.java's 
pieceToMove is updated.
Square: simply a JPanel that can be colored by the Board class when created and assembled into
the Board's JPanel 2D array.
State: the main backend of the game. Includes methods for validating piece placement, actually
changing the 2D array representing the board when a player clicks on the board, and adjusts
the game state after undo/next turn/open operations.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  Being able to implement the Undo functionality was somewhat difficult as the preview
  and movePiece methods were originally designed to temporarily store the board state
  within the LinkedList. However, I later was able to successfully redesign so that the 
  board state is only updated and stored within the LinkedList boardHistory when the 
  user clicks "Next Turn".
  Additioinally, creating the graphics/repaint method for the PlayerPieceSet was time-consuming. I
  spent a good amount of time determining the easiest way to paint these pieces without 
  rewriting large chunks of code; I therefore used 2D rectangular arrays to do this, creating
  individual Piece graphics, and then assembling them into the PlayerPieceSet JComponent. I 
  believe my implementation was able to keep rewriting code to a minimum, although I still 
  would enjoy thinking of new ways to more effectively create Piece graphics with corresponding
  actionListeners.
  Finally, I stumbled on the use of HashSets, as I used an erroneous hashCode. My initial 
  hashCode would make pieces that were actually equivalent have different hashCodes, and it 
  took me some time to debug and recognize the problem was my hashCode design. 
  
  


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I would split up the State.java methods into distinct classes based on what they do. 
The current file is quite long and if additional features are added it would be difficult
to understand how each method within the class fits into the bigger puzzle. I believe 
breaking apart the file into smaller files would make it easier to identify redundancies
and expand on already implemented features.
Moreover, I would split up the JComponent aspect of the Piece class as it somewhat violates
the MVC framework, even though it is easier to hold all of Piece related characteristics
together. Nevertheless, I believe for the most part, that there is a good separatin of functionality
across the other classes. The State.java file, though long, is dedicated solely to the backend
and does not rely on GUI operations: a terminal version of Blokus could very easily be 
developed with existing methods taken from State.java.
The private state (fields of State.java as well as other objects) is encapsulated. Setters/getters
only retrieve and return copies of arguments, and there are a few methods that have been written
within State.java that create deep copies of Board, Player, and Piece objects. However, for testing, 
a few methods have been package-visible instead of completely private.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I did not use much beyond the Oracle documentation and using StackOverflow
  for clarifications.
