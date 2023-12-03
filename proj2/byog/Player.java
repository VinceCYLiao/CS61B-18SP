package byog;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {
    private final TETile[][] world;
    private final Random r;
    private final TERenderer ter;
    private Position position;

    private final boolean playWithKeyboard;

    public Player(TETile[][] world, TERenderer ter, boolean playWithKeyboard, Random r) {
        this.world = world;
        this.ter = ter;
        this.playWithKeyboard = playWithKeyboard;
        this.r = r;
        setupPlayer();
    }

    private void setupPlayer() {
        while (true) {
            int randomX = r.nextInt(this.world.length);
            int randomY = r.nextInt(this.world[0].length);
            TETile target = this.world[randomX][randomY];
            if (target.equals(Tileset.FLOOR)) {
                this.position = new Position(randomX, randomY);
                this.world[randomX][randomY] = Tileset.PLAYER;
                break;
            }
        }
        if (this.playWithKeyboard) {
            ter.renderFrame(this.world);
        }
    }

    public void moveRight() {
        Position nextPosition = new Position(this.position.x + 1, this.position.y);
        if (nextPosition.x < this.world.length - 1 && this.world[nextPosition.x][nextPosition.y].equals(Tileset.FLOOR)) {
            updatePlayerPosition(nextPosition);
        }
    }

    public void moveLeft() {
        Position nextPosition = new Position(this.position.x - 1, this.position.y);
        if (nextPosition.x > 0 && this.world[nextPosition.x][nextPosition.y].equals(Tileset.FLOOR)) {
            updatePlayerPosition(nextPosition);
        }
    }

    public void moveUp() {
        Position nextPosition = new Position(this.position.x, this.position.y + 1);
        if (nextPosition.y < this.world[0].length - 1 && this.world[nextPosition.x][nextPosition.y].equals(Tileset.FLOOR)) {
            updatePlayerPosition(nextPosition);
        }
    }

    public void moveDown() {
        Position nextPosition = new Position(this.position.x, this.position.y - 1);
        if (nextPosition.y > 0 && this.world[nextPosition.x][nextPosition.y].equals(Tileset.FLOOR)) {
            updatePlayerPosition(nextPosition);
        }
    }

    private void updatePlayerPosition(Position nextPosition) {
        this.world[this.position.x][this.position.y] = Tileset.FLOOR;
        this.world[nextPosition.x][nextPosition.y] = Tileset.PLAYER;
        this.position = nextPosition;
        if (this.playWithKeyboard) {
            StdDraw.enableDoubleBuffering();
            ter.renderFrame(this.world);
        }
    }
}
