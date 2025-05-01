import java.util.ArrayList;


public class Gatherer extends Actor implements Move{

    public static final String TYPE = "Gatherer";

    /**
     * gets direction
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * sets direction
     * @param direction direction used to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    private int direction;
    private boolean carrying = false;

    /**
     * getter for active
     * @return whether t he gatherer/thief is active
     */
    public boolean isActive() {
        return active;
    }

    private boolean active = true;

    /**
     * Constructor for gatherer
     * @param x sets x
     * @param y sets y
     */
    public Gatherer(int x, int y){
        super("res/images/gatherer.png", TYPE, x,y);
        gathererInitialize();

    }

    /**
     * initializes gatherer
     */
    public void gathererInitialize(){
        direction = Direction.LEFT;
        carrying = false;
        active = true;
    }

    /**
     * This method is used when you want to move the gatherer in its direction
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
     * @param actors is needed in  the method as values of actors get affected due to gatherer
     */
    public void gathererTick(ArrayList<Actor> actors) {
        // will be used for indexing arrayList throughout this method
        int index;

        if (active) {
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

        // if there is a mitosis pool in the file
        if (doesObjectExist(MitosisPools.TYPE, actors)) {
            for (Actor actor : findActor(MitosisPools.TYPE, actors)) {
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    MitosisPools mitosisPools = (MitosisPools) actor;
                    mitosisPools.createGatherer(actors, this);
                    return;
                }
            }
        }


        // if there is a down sign in the file
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

        // if there is a tree in the file
        if (doesObjectExist(Tree.TYPE, actors) && (!carrying)) {
            // you loop through all the Trees
            for (Actor actor : findActor(Tree.TYPE, actors)) {
                // and check if the gatherer is standing through any of the trees
                if ((this.getX() == actor.getX()) && (this.getY() == actor.getY())) {
                    // and if the tree has at least one fruit (by this time its confirmed that actor is of class
                    // type tree)
                    Tree tree = (Tree) actor;
                    if (tree.getFruitNumber() > 0) {
                        // reduce the fruit counter by 1
                        index = actors.indexOf(actor);
                        tree.setFruitNumber(tree.getFruitNumber() - 1);
                        actors.set(index, tree);

                        // now the gatherer is carrying the fruit
                        carrying = true;

                        // and you turn by 180 degrees
                        direction = turnToOppositeDirection(direction);
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
                    direction = turnToOppositeDirection(direction);
                }
            }
        }

        // Had to split the hoard and stockpile into two blocks of code even though they do similar tasks due to
        // the way methods and arrayList were made

        // if there is a hoard in the file and the gatherer is carrying a fruit
        if (doesObjectExist(Hoard.TYPE, actors)){
            // you loop through all the Hoards
            for (Actor actor : findActor(Hoard.TYPE, actors)) {
                // if the gatherer is standing through any of the stockpiles
                if (((this.getX() == actor.getX()) && (this.getY() == actor.getY()))) {
                    // and if the gatherer is carrying a fruit
                    if (carrying) {
                        // then you set the gatherer drops the fruit and doesn't carrying it anymore
                        carrying = false;
                        // and the hoard gets one more fruit(by this time you have confirmed that the actor
                        // is of class type Hoard)
                        Hoard hoard = (Hoard) actor;
                        index = actors.indexOf(actor);
                        hoard.setFruitNumber(hoard.getFruitNumber() + 1);
                        actors.set(index, hoard);

                    }
                    // finally you turn by 180 degrees
                    direction = turnToOppositeDirection(direction);
                }
            }

        }

        // if there is a stockpile in the file
        if (doesObjectExist(Stockpile.TYPE, actors)){
            // you loop through all the Stockpiles
            for (Actor actor : findActor(Stockpile.TYPE, actors)) {
                // if the gatherer is standing through any of the stockpiles
                if (((this.getX() == actor.getX()) && (this.getY() == actor.getY()))) {
                    // and if the gatherer is carrying a fruit
                    if (carrying) {

                        // then you set the gatherer drops the fruit and doesn't carrying it anymore
                        carrying = false;
                        // and the stockpile gets one more fruit(by this time you have confirmed that the actor
                        // is of class type Stockpile)
                        Stockpile stockpile = (Stockpile) actor;
                        index = actors.indexOf(actor);
                        stockpile.setFruitNumber(stockpile.getFruitNumber() + 1);
                        actors.set(index, stockpile);

                    }
                    // finally you turn by 180 degrees
                    direction = turnToOppositeDirection(direction);
                }
            }
        }
    }

    /**
     * This method is used to override tick in actors and execute its own version instead of updating every single
     * time
     * @param actors is an ArrayList with all the actors in it
     */
    @Override
    public void tick(ArrayList<Actor> actors){
        gathererTick(actors);
    }

    @Override
    public void update(){

    }

}
