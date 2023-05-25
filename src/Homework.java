import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {

    public void run() {
      int width = calcWidth();
      moveToCenterCol(width);
      int height = calcAndFillHeight();
      int min=Math.min(width,height);
      int squareLength=min-2;
      moveToSquareStart(width,height);
      fillInnerChamber(squareLength);
      moveToCenterRow(squareLength);
      fillWidth(width);
    }
    
    private int calcWidth(){
      return calcAndPutBeeper(false);
    }
    
    private void moveToCenterCol(int width){
      turnAround();
      moveMultiple((width-1)/2);
      turnRight();
    }
    
    private int calcAndFillHeight(){
      putBeeper();
      return calcAndPutBeeper(true);
    }
    
    private void moveToSquareStart(int width,int height){
      turnAround();
      int steps=width>=height?1:(height-width)/2+1;
      moveMultiple(steps);
      turnLeft();
    }
    
    private void fillInnerChamber(int length){
      moveAndPutBeeper((length-1)/2);
      for(int i=0;i<3;i++){
        turnRight();
        moveAndPutBeeper(length-1);
      }
      turnRight();
      moveAndPutBeeper((length-1)/2);
    }
    
    private void moveToCenterRow(int length){
      turnRight();
      moveMultiple((length-1)/2);
    }
    
    private void fillWidth(int width){
      turnRight();
      moveAndPutBeeper((width-1)/2);
      turnAround();
      moveAndPutBeeper(width-1);
    }
    
    private void moveMultiple(int n){
      for(int i=0;i<n;i++)
        move();
    }
    
    private void moveAndPutBeeper(int n){
      for(int i=0;i<n;i++){
        move();
        if(noBeepersPresent()){
          putBeeper();
        }
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

}
