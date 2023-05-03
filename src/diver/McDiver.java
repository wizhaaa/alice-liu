package diver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import graph.Node;
import graph.NodeStatus;
import graph.ScramState;
import graph.SeekState;
import graph.SewerDiver;


public class McDiver extends SewerDiver {

    /** Find the ring in as few steps as possible. Once you get there, <br>
     * you must return from this function in order to pick<br>
     * it up. If you continue to move after finding the ring rather <br>
     * than returning, it will not count.<br>
     * If you return from this function while not standing on top of the ring, <br>
     * it will count as a failure.
     *
     * There is no limit to how many steps you can take, but you will receive<br>
     * a score bonus multiplier for finding the ring in fewer steps.
     *
     * At every step, you know only your current tile's ID and the ID of all<br>
     * open neighbor tiles, as well as the distance to the ring at each of <br>
     * these tiles (ignoring walls and obstacles).
     *
     * In order to get information about the current state, use functions<br>
     * currentLocation(), neighbors(), and distanceToRing() in state.<br>
     * You know you are standing on the ring when distanceToRing() is 0.
     *
     * Use function moveTo(long id) in state to move to a neighboring<br>
     * tile by its ID. Doing this will change state to reflect your new position.
     *
     * A suggested first implementation that will always find the ring, but <br>
     * likely won't receive a large bonus multiplier, is a depth-first walk. <br>
     * Some modification is necessary to make the search better, in general. */
    @Override
    public void seek(SeekState state) {
        // TODO : Look for the ring and return.
        // DO NOT WRITE ALL THE CODE HERE. DO NOT MAKE THIS METHOD RECURSIVE.
        // Instead, write your method (it may be recursive) elsewhere, with a
        // good specification, and call it from this one.
        //
        // Working this way provides you with flexibility. For example, write
        // one basic method, which always works. Then, make a method that is a
        // copy of the first one and try to optimize in that second one.
        // If you don't succeed, you can always use the first one.
        //
        // Use this same process on the second method, scram.
    	
    	// calling my own method
//    	mySeek1(state);
    	mySeek2(state, Long.valueOf(0));

    }
    
    /**
     * Data structure to hold the ID's of the nodes that have been visited. 
     */
    ArrayList<Long[]> visited = new ArrayList<>();

//    /** 
//     * 
//     * @param state
//     * Method does a DFS walk through the graph to find the ring. Returns and the 
//     * McDiver stops once he finds the ring. 
//     * 
//     * <br> 
//     * 
//     * Uses the helper method visited we created below. 
//     * 
//     */
//    public void mySeek1(SeekState state) { 
//    	long currLoc = state.currentLocation();
//    	int ringDis = state.distanceToRing();
//    	if(ringDis == 0) return;
//    	visited.add(currLoc);
//    	for (NodeStatus w: state.neighbors()) { 
//    		if(!visited(w.getId())) { 
//    			state.moveTo(w.getId());
//    			mySeek1(state);
//    			if(state.distanceToRing() == 0) return;
//    			state.moveTo(currLoc);
//    			
//    		}
//    	}
//    }
    public void mySeek2(SeekState state, Long n) { 
    	long currLoc = state.currentLocation();
    	int ringDis = state.distanceToRing();
    	int minDis = state.distanceToRing()+2;
    	Long minVisited = 2*n;
    	NodeStatus destination = null;
    	NodeStatus destinationVisited = null;
    	if(ringDis == 0) return;
    	int i = 0;
    	while (i < visited.size()) {
    		if (currLoc == (visited.get(i)[0])) {
    			visited.remove(i);
    		} else {
    			i++;
    		}
    	}
    	visited.add(new Long[] {currLoc, n});
    	for (NodeStatus w: state.neighbors()) { 
    		if(!visited(w.getId())) { 
    			int wDis = w.getDistanceToRing();
    			if (wDis < minDis) { 
    				minDis = wDis;
    				destination = w;
    			}
    		} else {
    			for (Long[] l : visited) {
    				if (l[0].equals(w.getId()) ) {
    					if (l[1] < minVisited) {
    						minVisited = l[1];
    						destinationVisited = w;
    					}
    					break;
    				}
    			}
    		}
    	}
    	if(destination != null) { 
    		state.moveTo(destination.getId());
			mySeek2(state, n+1);
			if(state.distanceToRing() == 0) return;
			state.moveTo(currLoc);
    	} else {
    		state.moveTo(destinationVisited.getId());
    		mySeek2(state, n+1);
			if(state.distanceToRing() == 0) return;
			state.moveTo(currLoc);
    	}
    }

    /**
     * 
     * @param nodeId
     * @return true if the node with the corresponding node ID has been marked as visited.
     * 
     * <br> 
     * 
     * Used as a helper method for the mySeek1 method in the seek phase. 
     * 
     */
    public boolean visited(long nodeId) { 

    	for (Long[] id: visited) { 
    		if(id[0] == nodeId) return true; 
    	}
    	
    	return false;
    }

    /** Scram --get out of the sewer system before the steps are all used, trying to <br>
     * collect as many coins as possible along the way. McDiver must ALWAYS <br>
     * get out before the steps are all used, and this should be prioritized above<br>
     * collecting coins.
     *
     * You now have access to the entire underlying graph, which can be accessed<br>
     * through ScramState. currentNode() and exit() return Node objects<br>
     * of interest, and allNodes() returns a collection of all nodes on the graph.
     *
     * You have to get out of the sewer system in the number of steps given by<br>
     * stepsToGo(); for each move along an edge, this number is <br>
     * decremented by the weight of the edge taken.
     *
     * Use moveTo(n) to move to a node n that is adjacent to the current node.<br>
     * When n is moved-to, coins on node n are automatically picked up.
     *
     * You must return from this function while standing at the exit. Failing <br>
     * to do so before steps run out or returning from the wrong node will be<br>
     * considered a failed run.
     *
     * Initially, there are enough steps to get from the starting point to the<br>
     * exit using the shortest path, although this will not collect many coins.<br>
     * For this reason, a good starting solution is to use the shortest path to<br>
     * the exit. */
    @Override
    public void scram(ScramState state) {
        // TODO: Get out of the sewer system before the steps are used up.
        // DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
        // with a good specification, and call it from this one.
//    	safeScram(state);
    	//scramVersion2(state);
    	scramFinal(state);
    }
    
    
    

  
    /**
     * 
     * @param s
     * Uses A7 dijkstra's shortest path algorithm to return a list of the shortest path from the 
     * start node to the end node (the exit). Then we move through this given list to the exit. This results in the shortest time to exit, but not the most coins.
     * 
     * 
     */
    public void safeScram(ScramState s) { 
    	List<Node> shortestPath = A7.dijkstra(s.currentNode(), s.exit());
    	shortestPath.remove(0);
    	for ( Node n: shortestPath) { 
    		if( n.equals(s.exit())) { 
    			s.moveTo(n);
    			return;
    		}
    		else {
    			s.moveTo(n);
    		}
    	}  	
    	
    }
    
    
    ArrayList<Node> ScramVisited = new ArrayList<>();
    
    public void scramFinal(ScramState s) { 

    	int stepsLeft = s.stepsToGo();
		List<Node> pathToExit = A7.dijkstra(s.currentNode(), s.exit());
    	int currentNodeToExitSteps = A7.sumOfPath(pathToExit); 
    	
    	
    	
    	while(stepsLeft >= currentNodeToExitSteps ) { 
    		stepsLeft = s.stepsToGo();
    		double maxPoints = 0;
    		Node highestNode = null;
    		for( Node n: s.allNodes()) { 
//    			if(n.getTile().type().name().equals("RING")) { 
//    				break;
//    			}
    			int tileValue = n.getTile().coins();
    			double denom =  ((double) (Math.pow(A7.sumOfPath(A7.dijkstra(s.currentNode(), n)), 3) * A7.sumOfPath(A7.dijkstra(n, s.exit()))));
    			//double curPoints = Math.pow(tileValue, 10) / ((double) A7.sumOfPath(A7.dijkstra(s.currentNode(), n)) * Math.pow(A7.sumOfPath(A7.dijkstra(n, s.exit())), 3));

    			//double curPoints = Math.pow(tileValue, 10) / ((double) (Math.pow(A7.sumOfPath(A7.dijkstra(s.currentNode(), n)), 3) * Math.pow(A7.sumOfPath(A7.dijkstra(n, s.exit())), 3)));
    			double curPoints = Math.pow(tileValue, 10) / denom;

    		
    			if( curPoints > maxPoints && !scramVisited(n)) {
        			ScramVisited.add(n);
    				maxPoints = curPoints;
    				highestNode = n;
    			}
    		}
    		if ( highestNode != null) { 
    			List<Node> pathToBestTile = A7.dijkstra(s.currentNode(), highestNode);
    			List<Node> pathFromBestTileToExit = A7.dijkstra(highestNode, s.exit());
    			if(stepsLeft > (A7.sumOfPath(pathToBestTile) + A7.sumOfPath(pathFromBestTileToExit))) {
    				pathToBestTile.remove(0);
    				for ( Node w : pathToBestTile) { 
    					s.moveTo(w);
    		
    				}
    			}
    			else { 
    				break;
    			} 
    			
    			
    		} else {
    			break;
    		}
        	
    	}
    	pathToExit = A7.dijkstra(s.currentNode(), s.exit());
    	pathToExit.remove(0);
    	
    	
    	for ( Node n: pathToExit) { 
    		s.moveTo(n);
    		if( n.equals(s.exit())) { 
    			return;
    		}
    	} 
    	
    }
    
    public void scramA(ScramState s) { 
    	PriorityQueue<Node> closedList = new PriorityQueue<>();
    	PriorityQueue<Node> openList = new PriorityQueue<>();
    	Node start = s.currentNode();
    	for(Node n: s.allNodes()) { 
    		closedList.add(n);
    	}
    	
    	
    	
    }

    
    public void scramVersion2(ScramState s) { 

    	int stepsLeft = s.stepsToGo();
		List<Node> pathToExit = A7.dijkstra(s.currentNode(), s.exit());
//		pathToExit.remove(0);
    	int currentNodeToExitSteps = A7.sumOfPath(pathToExit); 
    	
    	System.out.println("Started with " + stepsLeft + " steps.");
    	
    	
    	while(stepsLeft >= currentNodeToExitSteps ) { 
    		stepsLeft = s.stepsToGo();
    		double maxPoints = 0;
    		Node highestNode = null;
    		for( Node n: s.allNodes()) { 
    			if(n.getTile().type().name().equals("RING")) { 
    				System.out.println("Is a ring");

    				break;
    			}
    			int tileValue = n.getTile().coins();
    			double curPoints = Math.pow(tileValue, 10) / ((double) (Math.pow(A7.sumOfPath(A7.dijkstra(s.currentNode(), n)), 3) * A7.sumOfPath(A7.dijkstra(n, s.exit()))));
    			if( curPoints > maxPoints && !scramVisited(n)) {
        			//System.out.println("Made it here");
        			ScramVisited.add(n);
    				maxPoints = curPoints;
    				highestNode = n;
    			}
    		}
    		if ( highestNode != null) { 
    			List<Node> pathToBestTile = A7.dijkstra(s.currentNode(), highestNode);
    			
    			List<Node> pathFromBestTileToExit = A7.dijkstra(highestNode, s.exit());
    			if(stepsLeft > (A7.sumOfPath(pathToBestTile) + A7.sumOfPath(pathFromBestTileToExit))) {
    				pathToBestTile.remove(0);
    				// System.out.println("Steps left is: " + stepsLeft + ". We will be taking " + A7.sumOfPath(pathFromBestTileToExit) + " steps to the next highest tile. And then take " +A7.sumOfPath(pathToExit) + " steps to the exit." );
    				for ( Node w : pathToBestTile) { 
    					
    					
    					s.moveTo(w);
    		
    				}
    			}
    			else { 
    				System.out.println("Steps left is: " + stepsLeft);
    				System.out.println("Path to Exit: " + A7.sumOfPath(pathToExit));
//    				System.out.println("Current tile is: " + s.currentNode() + " highest tile is: " + highestNode);
    				System.out.println("Path to Best Tile to Exit " + A7.sumOfPath(pathFromBestTileToExit));
    				System.out.println("Path to Best Tile: " + A7.sumOfPath(pathToBestTile));
    				int sumOfSteps = A7.sumOfPath(pathFromBestTileToExit) +A7.sumOfPath(pathToBestTile);
    				System.out.println(stepsLeft - sumOfSteps);

    				
    				break;
    			} 
    			
    			
    		} else {
    			break;
    		}
        	
    	}
    	System.out.println("Going home!");
    	pathToExit = A7.dijkstra(s.currentNode(), s.exit());
    	pathToExit.remove(0);
    	
    	
    	for ( Node n: pathToExit) { 
//    		System.out.println("predicted: " + A7.sumOfPath(pathToExit));
//    		stepsLeft = s.stepsToGo();
//    		System.out.println("steps left is: " + stepsLeft);
//    		int edgeLen = n.edge(s.currentNode()).length;
//    		System.out.println("edge weight is: " +  edgeLen);
//    		System.out.println("next steps left should be: " + (stepsLeft - edgeLen));
    		s.moveTo(n);
    		
    		if( n.equals(s.exit())) { 
    			return;
    		}
    	}  		
    }
    
    
    /**
     * Helper function for scram stage. 
     * @param node
     * @return true if the node has been visited
     * 
     */
    public boolean scramVisited(Node node) { 

    	for (Node n: ScramVisited) { 
    		if(n.equals(node)) return true; 
    	}
    	
    	return false;
    }

}
