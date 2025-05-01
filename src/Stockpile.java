public class Stockpile extends Actor {

    public static final String TYPE = "Stockpile";

    /**
     * gets fruit number
     * @return fruitNumber
     */
    public int getFruitNumber() {
        return fruitNumber;
    }

    /**
     * sets fruitNumber
     * @param fruitNumber sets to this fruit number
     */
    public void setFruitNumber(int fruitNumber) {
        this.fruitNumber = fruitNumber;
    }

    private int fruitNumber;

    public Stockpile(int x, int y){
        super("res/images/cherries.png", TYPE, x, y);
        fruitNumber = 0;
    }

    /**
     * renders fruitCounter onto the window
     */
    public void renderFruitCounter(){
        drawFruitCounter(fruitNumber);
    }

    @Override
    public void update(){

    }

}
