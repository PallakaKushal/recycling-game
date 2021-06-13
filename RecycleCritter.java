import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

//Extends Critter class from grid jar
public class RecycleCritter extends Critter {
	private int totalNonRecycle = 0; //counter to score the no of non recycle items processed by critter
	private int totalRecycle = 0;///counter to score the no of recycle items processed by critter

	/**
	 * Default constructor
	 */
	public RecycleCritter() {
		setColor(Color.GREEN);
	}
	@Override
	//act method calls the methods listed below
	public void act()
    {
        if (getGrid() == null)
            return;
        ArrayList<Location> moveLocs = getMoveLocations();
        Location loc = selectMoveLocation(moveLocs);
        if(getGrid().isValid(loc)){
          ArrayList<Actor> actors = getActors();
          processActors(actors);
		      makeMove(loc);
        }
    }
	
	//gets actor and add to arraylist which is in current direction of the critter actor.
  @Override
	public ArrayList<Actor> getActors()
    {
		  ArrayList<Actor> actors =new ArrayList<Actor>();
		  actors.add(getGrid().get(getLocation().getAdjacentLocation(getDirection())));
      return actors;
    }

	

	
	/*
	 * process Actor method does below Collects recycle[Metal,Carton,Plastic] and
	 * non recycle items[Flower,Rock] Randomly Adds/Drops recycle items in grid if
	 * the item is collected Counter the no of items picked to Score ,end game
	 */
    @Override
	public void processActors(ArrayList<Actor> actors) {
		//System.out.println("processActors-----" + actors);
		for (Actor actor : actors) {
			boolean addRecycleItem = false;
			boolean addNonRecycleItem = false;
			//System.out.println("actor--" + actor); // metal,plastic,carton printing numbers
			if (actor instanceof Carton || actor instanceof Plastic || actor instanceof Metal) {
				actor.removeSelfFromGrid();
				addRecycleItem = true;
				totalRecycle++;
			}

			if (actor instanceof Flower || actor instanceof Rock) {
				actor.removeSelfFromGrid();
				addNonRecycleItem = true;
				totalNonRecycle++;
			}

			int randomNum = 1 + (int) (Math.random() * 3);
			if (addRecycleItem)
				randomDropRecycle(randomNum);

			if (addNonRecycleItem)
				randomDropNonRecycle(1 + (int) (Math.random() * 2));

		}
	}
	
	   // if n == 0 return null rather than current location and n!=0 return the next location in current direction.
		@Override
		public Location selectMoveLocation(ArrayList<Location> locs) {
			int n = locs.size();
			if (n == 0)
				return null;
			Location inFront = getLocation().getAdjacentLocation(getDirection());
			return inFront;
		}

		// if loc == null, remove self; otherwise, moves this critter to next location
		@Override
		public void makeMove(Location loc) {
			//System.out.println("makeMove-----" + loc);
			Location old = getLocation();
			if (loc == null) {
				removeSelfFromGrid();
			} else {
				moveTo(loc);
			}

		}
	
	//this method is used to drop non recycle items in grid if critter collects accidentally a non recycle item
	private void randomDropNonRecycle(int i) {
		Grid<Actor> gr = getGrid();
		if (i == 1) {
			Flower flower = new Flower();
			flower.putSelfInGrid(gr, getRandomEmptyLocation());
		}

		if (i == 2) {
			Rock rock = new Rock();
			rock.putSelfInGrid(gr, getRandomEmptyLocation());
		}

	}
	
	//this method is used to drop  recycle items in grid when critter collects recycle item
		private void randomDropRecycle(int i) {
			Grid<Actor> gr = getGrid();
			if (i == 1) {
				Carton carton = new Carton();
				carton.putSelfInGrid(gr, getRandomEmptyLocation());
			}

			if (i == 2) {
				Metal metal = new Metal();
				metal.putSelfInGrid(gr, getRandomEmptyLocation());
			}

			if (i == 3) {
				Plastic plastic = new Plastic();
				plastic.putSelfInGrid(gr, getRandomEmptyLocation());
			}

		}

    //on time up (with in 60 sec max item to be picked) or if critter picks non recycle items >3 this method is called
	public void gameOver(Color color) {

		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		Location loc = null;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				loc = new Location(row, col);
				if(loc !=null) {
				Actor rr = gr.get(loc);
				if (rr != null)
					rr.removeSelfFromGrid();
				// System.out.println("gameOver--"+row+"-"+col+"-"+loc);
				gr.put(loc, new Rock(color));
				}
			}
		}

	}
	
	//This method is to find the empty location in the grid with out actors
	private Location getRandomEmptyLocation() {
		Grid<Actor> gr = getGrid();
		Random r = new Random();
		Location loc = null;
		while (true) {
			int row = r.nextInt(gr.getNumRows());
			int col = r.nextInt(gr.getNumCols());
			loc = new Location(row, col);
			Actor a = gr.get(loc);
			//System.out.println("no--" + row + "--" + col + "--" + a);
			if (a == null)
				return loc;
		}
	}

	

	

	public int getTotalNonRecycle() {
		return totalNonRecycle;
	}

	public void setTotalNonRecycle(int totalNonRecycle) {
		this.totalNonRecycle = totalNonRecycle;
	}

	public int getTotalRecycle() {
		return totalRecycle;
	}

	public void setTotalRecycle(int totalRecycle) {
		this.totalRecycle = totalRecycle;
	}

}