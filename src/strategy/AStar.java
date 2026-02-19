package strategy;
import java.util.ArrayList;
import java.util.List;

import game.Arena;
import game.Snake;

/**
 *  @see https://www.youtube.com/watch?v=i0x5fj4PqP4&ab_channel=Tarodev
 */
public class AStar implements IGameStrategy 
{
    private Node[][] nodes;
    private Node start;
    private Node target;

    /**Link the list of nodes to the arena
     * 
     * @param arena     Represents the arena of the game
     */
    public void setup(Arena arena)
    {
        int size = arena.getXUnits();
        Arena.Tile[][] grid = arena.getGrid();

        nodes = new Node[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                nodes[i][j] = new Node(i, j);
                if(grid[i][j].getMark().equals("S ") || grid[i][j].getMark().equals("O "))
                    nodes[i][j].setWalkable(false);
                else if(grid[i][j].getMark().equals("F "))
                    target = nodes[i][j];
                else if(grid[i][j].getMark().equals("H "))
                    start = nodes[i][j];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j > 0) nodes[j][i].addNeighbor(nodes[j - 1][i]);
                if (j < size-1) nodes[j][i].addNeighbor(nodes[j + 1][i]);
                if (i > 0) nodes[j][i].addNeighbor(nodes[j][i - 1]);
                if (i < size-1) nodes[j][i].addNeighbor(nodes[j][i + 1]);
            }
        }
    }

    /** Change the direction of the snake based on the best path
     * 
     * @param snake     Represents the snake
     */
    public void execute(Snake snake)
    {
        List<Node> path = findPath(start, target);
        if(path != null)
        {
            Node nextNode = path.get(path.size()-1);
            changeDirection(start, nextNode, snake);
        }
    }

    /**
     * Finds the best path between two nodes
     * @param startNode
     * @param targetNode
     * @return
     */
    private List<Node> findPath(Node startNode, Node targetNode) {
        List<Node> toSearch = new ArrayList<>();
        toSearch.add(startNode);
        List<Node> processed = new ArrayList<>();

        while (!toSearch.isEmpty()) {
            Node current = toSearch.get(0);
            for (Node t : toSearch) {
                if (t.getF() < current.getF() || (t.getF() == current.getF() && t.getH() < current.getH())) {
                    current = t;
                }
            }
            processed.add(current);
            toSearch.remove(current);

            if (current == targetNode) {
                List<Node> path = new ArrayList<>();
                Node currentPathTile = targetNode;
                while (currentPathTile != startNode) {
                    path.add(currentPathTile);
                    currentPathTile = currentPathTile.getConnection();
                }
                return path;
            }

            for (Node neighbor : current.getNeighbors()) {
                if (neighbor.isWalkable() && !processed.contains(neighbor)) {
                    boolean inSearch = toSearch.contains(neighbor);
                    float costToNeighbor = current.getG() + current.getDistance(neighbor);

                    if (!inSearch || costToNeighbor < neighbor.getG()) {
                        neighbor.setG(costToNeighbor);
                        neighbor.setConnection(current);

                        if (!inSearch) {
                            neighbor.setH(neighbor.getDistance(targetNode));
                            toSearch.add(neighbor);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Change the direction of the snake
     * @param start     Represents the current node
     * @param next      Represents the next node
     * @param snake     Represents the snake
     */
    private void changeDirection(Node start, Node next, Snake snake) {
        int x = next.getX() - start.getX();
        int y = next.getY() - start.getY();
        
        if(y==-1)
           snake.moveUp();
        else if(y==1)
            snake.moveDown();
        else if(x==-1)
            snake.moveLeft();
        else if(x==1)
            snake.moveRight();
    }

    private static class Node {
        private Node connection;
        private float g;
        private float h; // Assuming you missed this in your original code
        private boolean walkable;
        private List<Node> neighbors;
        private int x;
        private int y;

        Node(int x, int y) {
            this.connection = null;
            this.g = 0.0f;
            this.h = 0.0f;
            this.walkable = true;
            this.neighbors = new ArrayList<>();
            this.x = x;
            this.y = y;
        }

        public Node getConnection() {
            return connection;
        }

        public void setConnection(Node node) {
            this.connection = node;
        }

        public float getG() {
            return g;
        }

        public void setG(float g) {
            this.g = g;
        }

        public float getH() {
            return h;
        }

        public void setH(float h) {
            this.h = h;
        }

        public float getF() {
            return g + h;
        }

        public boolean isWalkable() {
            return walkable;
        }

        public void setWalkable(boolean walkable) {
            this.walkable = walkable;
        }

        public List<Node> getNeighbors() {
            return neighbors;
        }

        public void addNeighbor(Node neighbor) {
            this.neighbors.add(neighbor);
        }

        public int getX() {
            return x;
        }
    
        public int getY() {
            return y;
        }
    
        public float getDistance(Node node) {
            int dx = Math.abs(this.x - node.getX());
            int dy = Math.abs(this.y - node.getY());
            return dx + dy;
        }
    }
}
