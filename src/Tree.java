import bagel.Font;

public class Tree extends Actor {

    public static final String TYPE = "Tree";
    private int fruitNumber;

    public int getFruitNumber() {
        return fruitNumber;
    }

    public void setFruitNumber(int fruitNumber) {
        this.fruitNumber = fruitNumber;
    }

    public Tree(int x, int y) {
        super("res/images/tree.png", TYPE, x, y);
        fruitNumber = 3;
    }

    /**
     * renders fruitCounter onto the window
     */
    public void renderFruitCounter(){
        drawFruitCounter(fruitNumber);
    }

    @Override
    public void update() {

    }
}

