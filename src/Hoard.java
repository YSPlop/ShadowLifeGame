public class Hoard extends Actor{

    public static final String TYPE = "Hoard";

    /**
     * gets number of fruits
     * @return fruitNumber
     */
    public int getFruitNumber() {
        return fruitNumber;
    }

    /**
     * sets number of fruits
     * @param fruitNumber used to set fruit number
     */
    public void setFruitNumber(int fruitNumber) {
        this.fruitNumber = fruitNumber;
    }

    private int fruitNumber;

    public Hoard(int x, int y){
        super("res/images/hoard.png", TYPE,x,y);
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
