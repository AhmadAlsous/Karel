import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
    private int movesCounter;

    public void run() {
      setBeepersInBag(1000);
      movesCounter=0;
      int width = calcWidth();
      if(width<5){
        System.out.println("The map is too small, width should be greater than 4.");
        return;
      }
      moveToCenterCol(width);
      boolean evenWidth = width%2==0;
      int height = calcAndFillHeight(evenWidth);
      if(height<5){
        System.out.println("The map is too small, height should be greater than 4.");
        return;
      }
      boolean evenHeight = height%2==0;
      int min=Math.min(width,height);
      int squareLength=min-2;
      moveToSquareStart(width,height);
      fillInnerChamber(squareLength,evenWidth,evenHeight);
      moveToCenterRow(squareLength);
      fillWidth(width,evenHeight);
      System.out.println("Total moves required: "+ movesCounter);
    }
    
    private int calcWidth(){
      return calcAndPutBeeper(false);
    }
    
    private void moveToCenterCol(int width){
      turnAround();
      moveMultiple((width-1)/2);
      turnRight();
    }
    
    private int calcAndFillHeight(boolean evenWidth){
      putBeeper();
      int height= calcAndPutBeeper(true);
      if(evenWidth){
        handleEven(height-1);
      }
      return height;
    }
    
    private void moveToSquareStart(int width,int height){
      turnAround();
      int steps=width>=height?1:(height-width)/2+1;
      moveMultiple(steps);
      turnLeft();
    }
    
    private void fillInnerChamber(int length, boolean evenWidth, boolean evenHeight){
      moveAndPutBeeper((length-1)/2);
      for(int i=0;i<3;i++){
        int steps=length-1;
        if(evenHeight && !evenWidth && i%2==0){
          steps++;
        }
        if(evenWidth && !evenHeight && i%2==1){
          steps++;
        }
        turnRight();
        moveAndPutBeeper(steps);
      }
      turnRight();
      moveAndPutBeeper((length-1)/2);
    }
    
    private void moveToCenterRow(int length){
      turnRight();
      moveMultiple((length-1)/2);
    }
    
    private void fillWidth(int width, boolean evenHeight){
      turnRight();
      moveAndPutBeeper((width-1)/2);
      if (evenHeight){
        handleEven(width - 1);
        handleEven((width-1)/2-1);
      }
      else {
        turnAround();
        moveAndPutBeeper(width - 1);
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
