import bagel.Font;
import bagel.Image;

import java.util.ArrayList;

public abstract class Actor{

    /**
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }

    private int x;
    private int y;

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    private final String type;
    private final Image image;

    public static final int TEXT_SIZE = 15;
    public static final int COORDINATE_ADJUSTER = 3;

    /** This method is is the constructor for the actor class
     * @param fileName is the name of the file in which the image is stored in
     * @type is the type of the actor
     * @param x is the x coordinate of the actor
     * @param y is the y coordinator of the actor
     */
    public Actor(String fileName, String type, int x, int y){
        image = new Image(fileName);
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     *  This method renders the image with the top left on (x,y)
     */
    public void render(){
        image.drawFromTopLeft(x, y);
    }

    /**This method Will be executed upon a tick and overridden by classes if needed if not the method class will
     * be called
     * @param actors is an ArrayList with all the actors in it
     */
    public void tick(ArrayList<Actor> actors){
        update();
    }

    /**This method is used to draw the number of fruits in a stockpile/hoard/tree
     * @param fruitNumber is the current fruit number and is rendered as an String on the display Window
     * */
    public void drawFruitCounter(int fruitNumber){
        Font fruitCount = new Font("res/VeraMono.ttf",TEXT_SIZE);
        String currentFruitNumber = Integer.toString(fruitNumber);
        fruitCount.drawString( currentFruitNumber, getX(), (getY() - COORDINATE_ADJUSTER));
        // System.out.println("drawFruitCounter was called" + currentFruitNumber + " " + getX() + " " + getY());
    }

    /**This method is
     * @param direction is the direction and is used to check which direction it should be flipped to
     * @return the direction value of the opposite direction of direction provided
     */
    public int turnToOppositeDirection(int direction){
        if (direction == Direction.UP) {
            return Direction.DOWN;
        }
        if (direction == Direction.DOWN) {
            return Direction.UP;
        }
        if (direction == Direction.LEFT) {
            return Direction.RIGHT;
        }
        if (direction == Direction.RIGHT) {
            return Direction.LEFT;
        }
        // This statement should never be triggered, if triggered the wrong direction was sent in
        return -1;
    }

    /** This method is used to turn the direction provided 90 degrees clockwise
     * @param direction is used to check which direction it should be flipped to
     * @return the direction value of the 90 degrees clockwise of direction provided
     */
    public int turnNinetyDegreesClockwise(int direction){
        if (direction == Direction.UP) {
            return Direction.RIGHT;
        }
        if (direction == Direction.DOWN) {
            return Direction.LEFT;
        }
        if (direction == Direction.LEFT) {
            return Direction.UP;
        }
        if (direction == Direction.RIGHT) {
            return Direction.DOWN;
        }
        // This statement should never be triggered, if triggered the wrong direction was sent in
        return -1;
    }

    /**@param type is used to the type of actors that you used to search the ArrayList with
     * @param actors an ArrayList of actors that is looped across
     * @return all the actors of the type of object your searching for
     */
    public ArrayList<Actor> findActor(String type, ArrayList<Actor> actors){
        ArrayList<Actor> actorSubSet = new ArrayList<Actor>();
        for (Actor actor: actors){
            if (actor.getType().equals(type)) {
                actorSubSet.add(actor);
            }
        }
        return actorSubSet;

    }

    /** This method checks if the object exists
     * @param type is used to the type of actors that you used to search the ArrayList with
     * @param actors an ArrayList of actors that is looped across
     * @return returns true when the object of type, type exists in  the arraylist actors
     */
    public boolean doesObjectExist(String type, ArrayList<Actor> actors){
        for (Actor actor: actors){
            if (actor.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param deltaX amount in which actor is moved in the x direction
     * @param deltaY amount in which actor is moved in the y direction
     */
    public void movement(int deltaX, int deltaY){
        x += deltaX;
        y += deltaY;
    }

    public abstract void update();


}
