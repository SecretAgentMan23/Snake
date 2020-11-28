package personal.casey.snale;

public class BodyPart {
    private int x,y;
    //the two if statements here handle the wrapping of the gameboard
    public BodyPart(int x, int y, int boardSize){
        this.x = x% boardSize;
        if(this.x < 0) this.x += boardSize;

        this.y = y % boardSize;
        if(this.y < 0) this.y += boardSize;
    }

    public int getX(){
        return x;
    }

    public  int getY(){
        return y;
    }
}
