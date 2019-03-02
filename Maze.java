import java.util.* ;
import java.io.* ;
public class Maze {
  private char[][] maze ;
  private boolean animate ; // false by default
  private int rowOfS, colOfS, rowOfE, colOfE, lengthOfSolution ;
  /*Constructor loads a maze text file, and sets animate to false by default.

  1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

  2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

  3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
  */
  public Maze(String filename) throws FileNotFoundException {
    //COMPLETE CONSTRUCTOR
    lengthOfSolution = 0 ;
    int len = 0 ;
    int width = 0 ;
    int tempW = 0 ;
    animate = false ;
    File f = new File(filename) ;
    Scanner s = new Scanner(f) ;
    while (s.hasNextLine()) {
      String line = s.nextLine() ;
      len++ ;
      tempW = 0 ;
      for (int i = 0 ; i < line.length() ; i++) {
        tempW++ ;
      }
      if (tempW > width) width = tempW ;
      //System.out.println(line) ;
    }
    maze = new char[len][width] ;
    Scanner adding = new Scanner(f) ;
    int row = 0 ;
    rowOfS = -1 ;
    colOfS = -1 ;
    rowOfE = -1 ;
    colOfE = -1 ;
    while (s.hasNextLine() && row < maze.length) {
      String line = s.nextLine() ;
      for (int i = 0 ; i < line.length() ; i++) {
        maze[row][i] = line.charAt(i) ;
        if (line.charAt(i) == 'S') {
          rowOfS = row ;
          colOfS = i ;
        }
        if (line.charAt(i) == 'E') {
          rowOfE = row ;
          colOfE = i ;
        }
        row++ ;
      }
      //System.out.println(line) ;
    }
    if (rowOfS == -1 || colOfS == -1 || rowOfE == -1 || colOfE == -1) {
      throw new IllegalStateException("The start and end are either missing, or there's too many of them!") ;
    }
  }

  private void wait(int millis) {
    try {
      Thread.sleep(millis) ;
    }
    catch (InterruptedException e) {
    }
  }
  // mutator method
  public void setAnimate(boolean b) {
    animate = b ;
  }

  public void clearTerminal() {
    //erase terminal, go to top left of screen.
    System.out.println("\033[2J\033[1;1H") ;
  }
  /*Return the string that represents the maze.
     It should look like the text file with some characters replaced.
  */
  public String toString() {
    String res = "" ;
    for (int row = 0 ; row < maze.length ; row++) {
      for (int col = 0 ; col < maze[0].length ; col++) {
        res += maze[row][col] ;
      }
      res += "\n" ;
    }
    return res ;
  }


  /*Wrapper Solve Function returns the helper function
  Note the helper function has the same name, but different parameters.
  Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
  */
  public int solve() {
    //find the location of the S.
    // maze[rowOfS][colOfS] is the starting point (found in the constructor)
    //erase the S
    maze[rowOfS][colOfS] = '@' ;
    //and start solving at the location of the s.
    if (solve(rowOfS, colOfS)) return lengthOfSolution ;
    else {
      return -1 ;
    }
  }

  /*
  Recursive Solve function:
    A solved maze has a path marked with '@' from S to E.
    Returns the number of @ symbols from S to E when the maze is solved,
    Returns -1 when the maze has no solution.

  Postcondition:
    The S is replaced with '@' but the 'E' is not.
    All visited spots that were not part of the solution are changed to '.'
    All visited spots that are part of the solution are changed to '@'
  */
  private boolean solve(int row, int col) { //you can add more parameters since this is private
    //automatic animation! You are welcome.
    if (animate) {
      clearTerminal() ;
      System.out.println(this) ;
      wait(20) ;
    }
    //COMPLETE SOLVE
    if (maze[row][col] == '#') {
      // we have found a wall/border! --> we can't go any further!
      return false ;
    }
    return false ; //so it compiles
  }
}
