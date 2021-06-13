import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

public class RecycleWorld extends ActorWorld {
	RecycleCritter recycleCritter;
	long start; // stop watch start time
	int startPoint = 0;
	long end = 0; // stop watch end time

	// Constructor (setting up the grid world)
	public RecycleWorld(int width, int height) {
		super(new BoundedGrid<Actor>(width, height));
		setMessage("Title:Recycle Critter \n" + "Author:Kushal P\n"
				+ "Instructions:This program loads a game that contains a 10*10 Grid world with where various recyle & non recycle actors like Flowers,Rocks,Carton,Metal,Plastic& Critter . \n"
				+ "Once the the user selects the step/arrow keys to collect a  item critter moves that direction one step ahead and collects it. Game will be over if below happens  \n"
				+ "1.Time up(60 sec game time) 2.Collects 3 non recycle Actors 3.Collects Max expected items(25 Recycle Actors in <60 sec).");
		recycleCritter = new RecycleCritter();
		this.add(new Location(4, 4), recycleCritter);
		this.add(new Location(2, 3),new Flower());
		this.add(new Location(0, 0),new Rock());
		this.add(new Rock());
		this.add(new Rock());
		this.add(new Flower());
		this.add(new Location(6, 7),new Metal());
		this.add(new Location(1, 4),new Plastic());
		this.add(new Location(1, 9),new Carton());
		this.add(new Metal());
		this.add(new Plastic());
		this.add(new Carton());

	}

	// Step Click Functionality
	public void step() {
		keyPressedMove();
  }

	// on keyboard pressed or step click perform the actor moves
	public void keyPressedMove() {
		float sec = 0;
		if (startPoint == 0)
			start = System.currentTimeMillis();

		if (startPoint > 2)
			sec = (end - start) / 1000;

		setMessage("Total Recycle Item so far: " + recycleCritter.getTotalRecycle() + "\t\t Total Elapsed Sec:" + sec
				+ "\n Total NonRecycle Item so far: " + recycleCritter.getTotalNonRecycle());
		startPoint++;
		if (recycleCritter.getTotalNonRecycle() > 3) {
			setMessage("GAME OVER! Too Many Non Recycle Items Picked up: " + recycleCritter.getTotalNonRecycle());
			recycleCritter.gameOver(Color.red);
		} else if (sec > 60) {
			setMessage("GAME OVER! TIME UP  \t Total Recycle Item so far: " + recycleCritter.getTotalRecycle()
					+ "\t Total NonRecycle Item so far: " + recycleCritter.getTotalNonRecycle());
			recycleCritter.gameOver(Color.red);
		}else if (recycleCritter.getTotalRecycle() >25) {
			setMessage("YOU SAVE THE WORLD!!!! Total Recycle Items collected : " + recycleCritter.getTotalRecycle());
			recycleCritter.gameOver(Color.green);
		} else{
			recycleCritter.act();
    }
		end = System.currentTimeMillis();
	}

	public boolean keyPressed(String description, Location loc) {
		// make sure the Grid is not null
		Grid<Actor> grid = getGrid();
		if (grid != null) {

			// change the direction of the actor(critter) to process the recycle actor

			if (description.equals("UP")) {
				recycleCritter.setDirection(0);
				this.keyPressedMove();
			}
			if (description.equals("RIGHT")) {
				recycleCritter.setDirection(90);
				this.keyPressedMove();
			}
			if (description.equals("DOWN")) {
				recycleCritter.setDirection(180);
				this.keyPressedMove();
			}
			if (description.equals("LEFT")) {
				recycleCritter.setDirection(270);
				this.keyPressedMove();
			}
		}
		return true;
	}

}
