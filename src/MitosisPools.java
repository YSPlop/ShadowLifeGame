import java.util.ArrayList;

public class MitosisPools extends Actor{

    public static final String TYPE = "Pool";

    public MitosisPools(int x, int y){
        super("res/images/pool.png", TYPE,x,y);
    }

    /**
     * returns the direction value of the 90 degrees clockwise of direction provided
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

    /**
     * returns the direction value of the 90 degrees anticlockwise of direction provided
     * @param direction is used to check which direction it should be flipped to
     * @return returns the direction value of the 90 degrees anticlockwise of direction provided
     */
    public int turnNinetyDegreesAnticlockwise(int direction){
        if (direction == Direction.UP) {
            return Direction.LEFT;
        }
        if (direction == Direction.DOWN) {
            return Direction.RIGHT;
        }
        if (direction == Direction.LEFT) {
            return Direction.DOWN;
        }
        if (direction == Direction.RIGHT) {
            return Direction.UP;
        }
        // This statement should never be triggered, if triggered the wrong direction was sent in
        return -1;
    }

    /**
     * creates two thieves when you reach a mitosis pool
     * @param actors is needed to add/remove the thief
     * @param thief is needed to set the values for the new thieves
     */
    public void createThief(ArrayList<Actor> actors, Thief thief){

        int currentDirection = thief.getDirection();
        int clockwiseDirection = turnNinetyDegreesClockwise(currentDirection);
        int anticlockwiseDirection = turnNinetyDegreesAnticlockwise(currentDirection);

        Thief thief1 = new Thief(thief.getX(), thief.getY());
        thief1.setDirection(clockwiseDirection);
        thief1.move();

        Thief thief2 = new Thief(thief.getX(), thief.getY());
        thief2.setDirection(anticlockwiseDirection);
        thief2.move();

        actors.add(thief1);
        actors.add(thief2);
        actors.remove(thief);

    }

    /**
     * creates two gatherers when you reach a mitosis pool
     * @param actors is needed to add/remove the gatherer
     * @param gathereris needed to set the values for the new gatherers
     */
    public void createGatherer(ArrayList<Actor> actors, Gatherer gatherer){

        int currentDirection = gatherer.getDirection();
        int clockwiseDirection = turnNinetyDegreesClockwise(currentDirection);
        int anticlockwiseDirection = turnNinetyDegreesAnticlockwise(currentDirection);

        Gatherer gatherer1 = new Gatherer(gatherer.getX(), gatherer.getY());
        gatherer1.setDirection(clockwiseDirection);
        gatherer1.move();

        Gatherer gatherer2 = new Gatherer(gatherer.getX(), gatherer.getY());
        gatherer2.setDirection(anticlockwiseDirection);
        gatherer2.move();

        actors.add(gatherer1);
        actors.add(gatherer2);
        actors.remove(gatherer);

    }

    @Override
    public void update(){

    }

}
