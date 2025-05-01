import java.util.ArrayList;

public class Thief extends Actor implements Move{

    public static final String TYPE = "Thief";

    /**
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction sets direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    private int direction;
    private boolean carrying = false;
    private boolean consuming = false;

    public boolean isActive() {
        return active;
    }

    private boolean active = false;

    /**
     * This method is the constructor
     * @param x used to set value
     * @param y used to set value
     */
    public Thief(int x, int y){
        super("res/images/thief.png", TYPE,x,y);
        thiefInitialize();
    }

    /**
     * This method initializes the thief
     */
    public void thiefInitialize(){
        direction = Direction.UP;
        carrying = false;
        consuming = true;
        active = true;
    }

    /**
     * This method is used to move the thief depending on the direction
     */
    public void move(){
        switch (direction) {
            case Direction.UP:
                movement(0, -ShadowLife.TILE_SIZE);
                break;
            case Direction.DOWN:
                movement(0, ShadowLife.TILE_SIZE);
                break;
            case Direction.LEFT:
                movement(-ShadowLife.TILE_SIZE, 0);
                break;
            case Direction.RIGHT:
                movement(ShadowLife.TILE_SIZE, 0);
                break;
        }
    }

    /**
     * This method tells what the thief does when a tick has passed
     * @param actors is needed in the method as values of actors get affected due to thief
     */
    public void thiefTick(ArrayList<Actor> actors){
        // Will be used alot by this method to index the arraylist
        int index;

        if (active){
            move();
        }

        // if there is a fence in the file
        if (doesObjectExist(Fence.TYPE, actors)) {
            // you loop through all the coordinates of fences
            for (Actor actor : findActor(Fence.TYPE, actors)) {
                // to check if gatherer is standing on any of the fences, if so
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    // the gatherer doesn't move anymore and
                    active = false;
                    // flipping direction so that the gatherer would move a step back in the right direction
                    direction = turnToOppositeDirection(direction);
                    move();
                }
            }
        }

        // if there is a mitosis pool
        if (doesObjectExist(MitosisPools.TYPE, actors)) {
            for (Actor actor : findActor(MitosisPools.TYPE, actors)) {
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    MitosisPools mitosisPools = (MitosisPools) actor;
                    mitosisPools.createThief(actors, this);
                    return;
                }
            }
        }

        // if there is a left sign in the file
        if (doesObjectExist(SignDown.TYPE, actors)){
            for (Actor actor : findActor(SignDown.TYPE, actors)) {
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    direction = Direction.DOWN;
                }
            }
        }
        // if there is a right sign in the file
        if (doesObjectExist(SignRight.TYPE, actors)){
            for (Actor actor : findActor(SignRight.TYPE, actors)) {
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    direction = Direction.RIGHT;
                }

            }
        }
        // if there is a left sign in the file
        if (doesObjectExist(SignLeft.TYPE, actors)){
            for (Actor actor : findActor(SignLeft.TYPE, actors)) {
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    direction = Direction.LEFT;
                }

            }
        }
        // if there is a up sign in the file
        if (doesObjectExist(SignUp.TYPE, actors)){
            for (Actor actor : findActor(SignUp.TYPE, actors)) {
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    direction = Direction.UP;
                }
            }
        }

        // if there is a pad in the file
        if (doesObjectExist(Pad.TYPE, actors)) {
            // you go through all the pads
            for (Actor actor : findActor(Pad.TYPE, actors)) {
                // if the thief is standing on the pad
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    consuming = true;
                }
            }
        }

        // if thief is standing on a gatherer
        // if there is a gatherer in a file
        if (doesObjectExist(Gatherer.TYPE, actors) && (!carrying)) {
            // you loop through all the gatherers
            for (Actor actor : findActor(Gatherer.TYPE, actors)) {
                // and check if the gatherer is standing through any of the trees
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    // ninety degrees anticlockwise is the same as 270 degrees clockwise
                    direction = turnToOppositeDirection(direction);
                    direction = turnNinetyDegreesClockwise(direction);
                }
            }
        }

        // if there is a tree in the file
        if (doesObjectExist(Tree.TYPE, actors) && (!carrying)) {
            // you loop through all the Trees
            for (Actor actor : findActor(Tree.TYPE, actors)) {
                // and check if the thief is standing through any of the trees
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    // and if the tree has at least one fruit (by this time its confirmed that actor is of class
                    // type tree)
                    Tree tree = (Tree) actor;
                    if (tree.getFruitNumber() > 0) {
                        // reduce the fruit counter by 1
                        index = actors.indexOf(actor);
                        tree.setFruitNumber(tree.getFruitNumber() - 1);
                        actors.set(index, tree);

                        // now the thief is carrying the fruit
                        carrying = true;
                    }
                }
            }
        }

        // if there is a golden tree in the file
        if (doesObjectExist(GoldenTree.TYPE, actors) && (!carrying)) {
            // you loop through all the GoldenTrees
            for (Actor actor : findActor(GoldenTree.TYPE, actors)) {
                // and check if the gatherer is standing through any of the golden trees
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    carrying = true;
                }
            }
        }

        // if there is a hoard in the file
        if (doesObjectExist(Hoard.TYPE, actors)) {
            // you loop through all the Hoards
            for (Actor actor : findActor(Hoard.TYPE, actors)) {
                // if the thief is standing through any of the hoards
                if (((this.getX() == actor.getX()) && (this.getY() == actor.getY()))) {
                    if (consuming){
                        consuming = false;
                        if (!carrying){
                            Hoard hoard = (Hoard) actor;
                            if (hoard.getFruitNumber() > 0){
                                carrying = true;
                                index = actors.indexOf(actor);
                                hoard.setFruitNumber(hoard.getFruitNumber() - 1);
                                actors.set(index, hoard);
                            }else{
                                direction = turnNinetyDegreesClockwise(direction);
                            }

                        }
                    // if the thief is carrying and not consuming
                    }else if (carrying){
                        carrying = false;
                        Hoard hoard = (Hoard) actor;
                        hoard.setFruitNumber(hoard.getFruitNumber() + 1);
                        direction = turnNinetyDegreesClockwise(direction);
                    }
                }
            }
        }

        // if there is a thief in the file
        if (doesObjectExist(Stockpile.TYPE, actors)) {
            // you loop through all the Stockpiles
            for (Actor actor : findActor(Stockpile.TYPE, actors)) {
                // if the thief is standing through any of the stockpiles
                if (((this.getX() == actor.getX()) && (this.getY() == actor.getY()))) {
                    if (!carrying){
                        Stockpile stockpile = (Stockpile) actor;
                        if(stockpile.getFruitNumber() > 0){
                            carrying = true;
                            consuming = false;
                            index = actors.indexOf(actor);
                            stockpile.setFruitNumber(stockpile.getFruitNumber() - 1);
                            direction = turnNinetyDegreesClockwise(direction);
                        }
                    }else{
                        direction = turnNinetyDegreesClockwise(direction);
                    }
                }
            }
        }

    }

    /**
     * This method is used to override tick in actors and execute its own version instead of updating every single
     * time
     * @param actors is an ArrayList with all the actors in it
     */
    public void tick(ArrayList<Actor> actors){
        thiefTick(actors);
    }

    @Override
    public void update(){

    }
}
