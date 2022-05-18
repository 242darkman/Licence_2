package ia;

import game.Move;

public class Couple{

    private Move action;
    private float score;

    public Couple(Move action,float score){
            this.action = action;
            this.score = score;
    }


    public Move getAction(){
        return this.action;
    }

    
    public void setAction(Move newAction){
        this.action = newAction;
    }

    public float getScore(){
        return this.score;
    }

    
    public void setScore(float newScore){
        this.score = newScore;
    }

}