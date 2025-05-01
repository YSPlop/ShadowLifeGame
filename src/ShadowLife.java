import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import bagel.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// This is the main class
public class ShadowLife extends AbstractGame{

    private final Image background = new Image("res/images/background.png");

    public static final int TILE_SIZE = 64;

    // Used to store all the actors
    private ArrayList<Actor> actors = new ArrayList<Actor>();

    // The amount of time that has to pass before a tick
    private  long tickTime;
    // Maximum Number of ticks
    private  int maxTicks;
    private long lastTick = 0;
    // Counts the number of ticks
    private int tickCounter = 0;


    /** This method is used to read the data from the file "args.txt"
     * @return  the input as an array of Strings*/
    private static String[] argsFromFile() {
        try {
            return Files.readString(Path.of("args.txt"), Charset.defaultCharset())
                    .split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** This method is used to display the error message in the input */
    public void inputErrorDisplay(){

        System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
        System.exit(-1);
    }
    /** This method is used to display the error message in the data
     * @param  worldFile used for displaying error
     * @param lineNumber used for displaying error*/
    public void dataErrorDisplay(String worldFile, int lineNumber){

        System.out.println("error in file " + worldFile + " at line " + lineNumber);
        System.exit(-1);
    }

    /** in UML diagram this is readFile
     * This method reads, creates respective actors and assigns it into the actors array
     */
    protected void readCSV(){

        String[] input = argsFromFile();

        // validData turns false when any of the data in the file given is of the wrong format
        boolean validData = true;

        // the line number in the file that your currently reading
        int lineNumber = 0;

        // Checking if the input statement has exactly three arguments
        if ((input == null)  || (input.length != 3)){
            inputErrorDisplay();
        }

        // holds the name of the file that consists the world
        String worldFile;

        tickTime = Long.parseLong(input[0]);
        maxTicks = Integer.parseInt(input[1]);
        worldFile = input[2];

        // Checking if the maxTicks and the tickTime are positive
        if ((tickTime < 0) || (maxTicks < 0)){
            inputErrorDisplay();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(worldFile))){

            String line;
            while ((line = br.readLine()) != null){
                // Line format is type,x,y
                String[] parts = line.split(",");
                lineNumber++;

                // making sure the data in the file is of correct length
                if (parts.length != 3){
                    dataErrorDisplay(worldFile,lineNumber);
                }

                String type = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);

                // making sure all the objects provided in the file are in the right location
                if ((0 > x) || (x > Window.getWidth()) || (0 > y) || (y > Window.getHeight())) {
                    dataErrorDisplay(worldFile, lineNumber);
                }
                // creating the objects
                switch(type){
                    case Tree.TYPE:
                        actors.add(new Tree(x,y));
                        break;
                    case Gatherer.TYPE:
                        actors.add(new Gatherer(x,y));
                        break;
                    case Fence.TYPE:
                        actors.add(new Fence(x,y));
                        break;
                    case GoldenTree.TYPE:
                        actors.add(new GoldenTree(x,y));
                        break;
                    case Hoard.TYPE:
                        actors.add(new Hoard(x,y));
                        break;
                    case MitosisPools.TYPE:
                        actors.add(new MitosisPools(x,y));
                        break;
                    case SignDown.TYPE:
                        actors.add(new SignDown(x,y));
                        break;
                    case SignUp.TYPE:
                        actors.add(new SignUp(x,y));
                        break;
                    case SignLeft.TYPE:
                        actors.add(new SignLeft(x,y));
                        break;
                    case SignRight.TYPE:
                        actors.add(new SignRight(x,y));
                        break;
                    case Stockpile.TYPE:
                        actors.add(new Stockpile(x,y));
                        break;
                    case Thief.TYPE:
                        actors.add(new Thief(x,y));
                        break;
                    case Pad.TYPE:
                        actors.add(new Pad(x,y));
                        break;
                    default:
                        dataErrorDisplay(worldFile, lineNumber);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /** This method is the constructor*/
    public ShadowLife(){
        readCSV();
    }


    @Override
    protected void update(Input input) {
        // If enough time has passed, run the next tick
        if (System.currentTimeMillis() - lastTick >= tickTime) {
            lastTick = System.currentTimeMillis();
            tickCounter += 1;
            // we make a copy so that when adjustments are made to the arrayList it doesnt affect the loop
            ArrayList<Actor> actorsCopy = new ArrayList<>();
            actorsCopy.addAll(actors);
            for (Actor actor : actors) {
                if (actor != null) {
                    actor.tick(actorsCopy);
                }
            }
            actors = actorsCopy;
        }

        // If we reach maximum number of ticks
        if (tickCounter >= maxTicks){
            System.out.println("Timed out");
            System.exit(-1);
        }

        // drawing everything
        background.drawFromTopLeft(0, 0);
        for (Actor actor : actors) {
            if (actor != null) {
                // this will render the image
                actor.render();
                // this will render the fruit number
                if (actor.getType().equals(Tree.TYPE)){
                    Tree tempTree = (Tree) actor;
                    tempTree.renderFruitCounter();
                }
                if (actor.getType().equals(Stockpile.TYPE)){
                    Stockpile tempStockpile = (Stockpile) actor;
                    tempStockpile.renderFruitCounter();
                }
                if (actor.getType().equals(Hoard.TYPE)){
                    Hoard tempHoard = (Hoard) actor;
                    tempHoard.renderFruitCounter();
                }
            }
        }

        // true when all thieves and gatherers are not active and at the beginning
        boolean executionComplete = true;

        // Checking if the execution of the code is complete before macTicks is reached
        for (Actor actor : actors){
            if (actor.getType().equals(Gatherer.TYPE)){
                Gatherer tempGatherer = (Gatherer) actor;
                if (tempGatherer.isActive()) {
                    executionComplete = false;
                }
            }
            if (actor.getType().equals(Thief.TYPE)){
                Thief tempThief = (Thief) actor;
                if (tempThief.isActive()){
                    executionComplete = false;
                }
            }

        }

        // if it is reached then we print out the fruitNumber in stockpile and hoard in the same order it is in the
        // input file
        if ((executionComplete)){
            System.out.println(tickCounter + " ticks");
            for (Actor actor: actors){
                if (actor.getType().equals(Stockpile.TYPE)){
                    Stockpile tempStockpile = (Stockpile) actor;
                    System.out.println(tempStockpile.getFruitNumber());
                }
                if (actor.getType().equals(Hoard.TYPE)){
                    Hoard tempHoard = (Hoard) actor;
                    System.out.println(tempHoard.getFruitNumber());
                }
            }
            System.exit(0);
        }


    }

    /** This method is the main class and it runs the game*/
    public static void main(String[] args) {
        // reads the lines of the file
        ShadowLife game = new ShadowLife();
        game.run();
    }
}
