import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
  private int movesCounter; // Keeps count of karel's total moves
  private int height; // map height
  private int width; // map width
  private int squareLength; // length of the square that is formed by the 4 inner chambers
  
  // the run method specifies what karel does after pressing start program
  public void run() {
    initialize();
    this.width = calcWidth();
    this.height = calcAndFillHeight();
    if (isSmallMap()) {
      return;
    }
    this.squareLength = Math.min(this.width, this.height) - 2; // calculate square length
    fillInnerChambers();
    fillHorizontalOuterChambers();
    System.out.println("Total moves required: " + this.movesCounter); // print total moves
  }
  
  // initialize beepers in bag and moves counter
  private void initialize() {
    setBeepersInBag(1000);
    this.movesCounter = 0;
  }
  
  private int calcWidth() {
    return calcAndPutBeeper(false); // only calculates, no beepers
  }
  
  // calculate height and fill vertical outer chamber at once
  private int calcAndFillHeight() {
    moveToCenterCol();
    putBeeper();
    int height = calcAndPutBeeper(true); // to fill while also calculating
    if (isEven(this.width)) { // if width is even we need double vertical lines
      handleEven(height - 1);
    }
    return height;
  }
  
  // handle special cases (small maps)
  private boolean isSmallMap() {
    // 5 is the smallest length allowed: 1 outer + 2 inner + 2 spaces away from walls
    if (this.width < 5 || this.height < 5) {
      System.out.println("The map is too small, width and height should be greater than 4.");
      return true;
    }
    return false;
  }
  
  private void fillInnerChambers() {
    moveToSquareStart();
    moveAndPutBeeper((this.squareLength - 1) / 2); // fill first half first side
    for (int i = 0; i < 3; i++) { // fill second, third, and fourth sides
      fillSquareSide(i);
    }
    turnRight();
    moveAndPutBeeper((this.squareLength - 1) / 2 - 1); // fill second half first side
  }
  
  private void fillHorizontalOuterChambers() {
    moveToCenterRow();
    putBeeper();
    turnRight();
    moveAndPutBeeper((this.width - 1) / 2 - 1); // fill half the width
    // if height is even we need double horizontal lines
    if (isEven(this.height)) {
      handleEven(this.width - 1); // fill second horizontal line
      handleEven((this.width - 1) / 2 - 1); // fill second half of the width
    } else {
      turnAround();
      moveAndPutBeeper(this.width - 1); // fill second half of the width
    }
  }
  
  // calculates the length until it encounters a wall, can put beepers or not based on argument
  private int calcAndPutBeeper(boolean beeper) {
    int counter = 1;
    while (frontIsClear()) {
      move();
      if (beeper) {
        putBeeper();
      }
      counter++;
    }
    return counter;
  }
  
  // moves karel to the center column (to start filling the vertical outer chambers)
  private void moveToCenterCol() {
    turnAround();
    moveMultiple((this.width - 1) / 2); // move half the width
    turnRight();
  }
  
  // moves karel to the inner chambers start (to start filling the inner chambers)
  private void moveToSquareStart() {
    turnAround();
    // since the inner chambers can't be adjacent to the walls, we move at least one from
    // the top/bottom wall, more if width is greater than height
    int steps = this.width >= this.height ? 1 : (this.height - this.width) / 2 + 1;
    moveMultiple(steps);
    turnLeft();
  }
  
  private void fillSquareSide(int side) { // fills one side of the square
    int steps = this.squareLength - 1;
    // cover the cases where only one of the width and height are even
    if (isEven(this.height) && !isEven(this.width) && side % 2 == 0) {
      steps++;
    }
    if (isEven(this.width) && !isEven(this.height) && side % 2 == 1) {
      steps++;
    }
    turnRight();
    moveAndPutBeeper(steps);
  }
  
  private void moveAndPutBeeper(int n) {
    for (int i = 0; i < n; i++) {
      move();
      putBeeper();
    }
  }
  
  private boolean isEven(int n) {
    return n % 2 == 0;
  }
  
  // moves karel to the center row (to start filling the horizontal outer chambers)
  private void moveToCenterRow() {
    turnRight();
    moveMultiple((this.squareLength - 1) / 2); // move half the square length
  }
  
  // used when we need to turn and start second line of beepers when even
  private void handleEven(int length) {
    turnLeft();
    putBeeperAndTurn();
    moveAndPutBeeper(length);
  }
  
  private void moveMultiple(int n) {
    for (int i = 0; i < n; i++)
      move();
  }
  
  private void putBeeperAndTurn() {
    move();
    putBeeper();
    turnLeft();
  }
  
  // override the move method to print a counter each time karel moves
  @Override
  public void move() {
    super.move();
    movesCounter++;
    System.out.println("Karel moves counter: " + movesCounter);
  }
  
  // override the putBeeper method to only put beepers if there are no beepers present
  @Override
  public void putBeeper() {
    if (noBeepersPresent()) {
      super.putBeeper();
    }
  }
  
}
