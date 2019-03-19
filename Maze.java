import java.util.* ;
import java.io.* ;
public class Maze {
  private char[][] maze ;
  private boolean animate ; // false by default
  private int rowOfS, colOfS ;
  private int[][] directions ;
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
    animate = false ;
    int[][] moves = { {0, -1}, {-1, 0}, {0, 1}, {1, 0} } ;
    //                  left      up    right    down
    directions = moves ;
    //// THIS SECTION FINDS THE DIMENSIONS OF THE MAZE
    int len = 0, width = 0 ;
    File f = new File(filename) ;
    Scanner s = new Scanner(f) ;
    String line = "" ;
    while (s.hasNextLine()) {
      line = s.nextLine() ;
      len++ ;
      //System.out.println(line) ;
    }
    width = line.length() ;
    maze = new char[len][width] ;
    // After creating the 2D array, we need to check how many starts and ends there are & add the chars to the 2D array
    Scanner adding = new Scanner(f) ;
    int row = 0, numberOfStarts = 0, numberOfEnds = 0 ;
    rowOfS = -1 ;
    colOfS = -1 ;
    while (adding.hasNextLine() && row < maze.length) {
      line = adding.nextLine() ;
      for (int i = 0 ; i < line.length() ; i++) {
        maze[row][i] = line.charAt(i) ;
        if (line.charAt(i) == 'S') {
          rowOfS = row ;
          colOfS = i ;
          numberOfStarts+= 1 ;
        }
        if (line.charAt(i) == 'E') numberOfEnds+= 1 ;
      }
      row++ ;
      //System.out.println(line) ;
    }
    if (rowOfS == -1 || numberOfStarts != 1 || numberOfEnds != 1) {
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
    return solve(rowOfS, colOfS,0) ;
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
  private int solve(int row, int col, int lengthOfSolution) {
    //you can add more parameters since this is private
    //automatic animation! You are welcome.
    /*if (animate) {
      clearTerminal() ;
      System.out.println(this) ;
      wait(20) ;
    }*/
    if (maze[row][col] == 'E') {
      //setAnimate(false) ;
      //System.out.println(lengthOfSolution) ;
      return lengthOfSolution ;
    }
    int tempR, tempC ;
    for (int i = 0 ; i < 4 ; i++) {
      //System.out.println(this) ;
      tempR = row + directions[i][0] ;
      tempC = col + directions[i][1] ;
      if (tempR >= 0 && tempR <= maze.length - 1 && tempC >= 0
      && tempC <= maze[0].length - 1 && maze[tempR][tempC] == ' ') {
        // this move is valid so let's try it!
        maze[tempR][tempC] = '@' ;
        int xx = solve(tempR, tempC, lengthOfSolution + 1) ;
        if (xx != -1) return xx ;
      }
    }
    //System.out.println("Finished the first for loop!") ;
    for (int a = 0 ; a < 4 ; a++) {
      tempR = row + directions[a][0] ;
      tempC = col + directions[a][1] ;
      if (maze[tempR][tempC] == 'E') return lengthOfSolution;
      //solve(tempR, tempC, lengthOfSolution) ;
    }
    maze[row][col] = '•' ;
    lengthOfSolution-- ;
    return -1 ; //so it compiles
  }
}
