import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
  private int movesCounter;
  private int height;
  private int width;
  private int squareLength;
  
  public void run() {
    setBeepersInBag(1000);
    this.movesCounter=0;
    this.width = calcWidth();
    if(this.width<5){
      System.out.println("The map is too small, width should be greater than 4.");
      return;
    }
    moveToCenterCol();
    this.height = calcAndFillHeight();
    if(this.height<5){
      System.out.println("The map is too small, height should be greater than 4.");
      return;
    }
    boolean evenHeight = height%2==0;
    this.squareLength=Math.min(this.width,this.height)-2;
    moveToSquareStart();
    fillInnerChamber();
    moveToCenterRow();
    fillWidth();
    System.out.println("Total moves required: "+ movesCounter);
  }
  
  private int calcWidth(){
    return calcAndPutBeeper(false);
  }
  
  private void moveToCenterCol(){
    turnAround();
    moveMultiple((this.width-1)/2);
    turnRight();
  }
  
  private int calcAndFillHeight(){
    putBeeper();
    int height= calcAndPutBeeper(true);
    if(isEven(this.width)){
      handleEven(height-1);
    }
    return height;
  }
  
  private void moveToSquareStart(){
    turnAround();
    int steps=this.width>=this.height?1:(this.height-this.width)/2+1;
    moveMultiple(steps);
    turnLeft();
  }
  
  private void fillInnerChamber(){
    moveAndPutBeeper((this.squareLength-1)/2);
    for(int i=0;i<3;i++){
      int steps=this.squareLength-1;
      if(isEven(this.height) && !isEven(this.width) && i%2==0){
        steps++;
      }
      if(isEven(this.width) && !isEven(this.height) && i%2==1){
        steps++;
      }
      turnRight();
      moveAndPutBeeper(steps);
    }
    turnRight();
    moveAndPutBeeper((this.squareLength-1)/2);
  }
  
  private void moveToCenterRow(){
    turnRight();
    moveMultiple((this.squareLength-1)/2);
  }
  
  private void fillWidth(){
    turnRight();
    moveAndPutBeeper((this.width-1)/2);
    if (isEven(this.height)){
      handleEven(this.width - 1);
      handleEven((this.width-1)/2-1);
    }
    else {
      turnAround();
      moveAndPutBeeper(this.width - 1);
    }
  }
  
  private void moveMultiple(int n){
    for(int i=0;i<n;i++)
      move();
  }
  
  private void moveAndPutBeeper(int n){
    for(int i=0;i<n;i++){
      move();
      putBeeper();
    }
  }
  
  private int calcAndPutBeeper(boolean beeper){
    int counter=1;
    while(frontIsClear()){
      move();
      if(beeper){
        putBeeper();
      }
      counter++;
    }
    return counter;
  }
  
  private void putBeeperAndTurn(){
    move();
    putBeeper();
    turnLeft();
  }
  
  private void handleEven(int length){
    turnLeft();
    putBeeperAndTurn();
    moveAndPutBeeper(length);
  }
  
  private boolean isEven(int n){
    return n%2==0;
  }
  
  public void move(){
    super.move();
    movesCounter++;
    System.out.println("Karel moves counter: "+ movesCounter);
  }
  
  public void putBeeper(){
    if(noBeepersPresent()){
      super.putBeeper();
    }
  }
  
}
