package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int MAX_ROOM = 20;
    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    private Random rand;
    private Room[] rooms = new Room[MAX_ROOM];

    private class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Room {
        private int roomWidth;
        private int roomHeight;
        private Position position;
        private static final int maxRoomWidth = 20;
        private static final int maxRoomHeight = 20;

        public Room() {
            this.position = new Position(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            this.roomWidth = rand.nextInt(maxRoomWidth - 5) + 5;
            this.roomHeight = rand.nextInt(maxRoomHeight - 5) + 5;
        }

        public void putRoomIntoWorld(TETile[][] world) {
            for (int i = 0; i < this.roomWidth; i++) {
                int x = position.x + i;
                for (int j = 0; j < this.roomHeight; j++) {
                    int y = position.y + j;
                    TETile tile;
                    if (i == 0 || i == this.roomWidth - 1 || j == 0 || j == this.roomHeight - 1) {
                        tile = Tileset.WALL;
                    } else {
                        tile = Tileset.FLOOR;
                    }
                    world[x][y] = tile;
                }
            }
        }

        public boolean isRoomOverlappedOrOutOfBound(TETile[][] world) {
            for (int i = 0; i < this.roomWidth; i++) {
                int x = position.x + i;
                for(int j = 0; j < this.roomHeight; j++) {
                    int y = position.y + j;
                    if(x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
                        return true;
                    }
                    if(world[x][y] != Tileset.NOTHING) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Room{" +
                    "width=" + roomWidth +
                    ", height=" + roomHeight +
                    ", positionX=" + position.x +
                    ", positionY=" + position.y +
                    '}';
        }

        
    }

    public void test() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        int failedCount = 0;
        int successfulCount = 0;
        while(successfulCount != MAX_ROOM) {
//            if(failedCount >= 20) {
//                break;
//            }
//            System.out.println(failedCount);
//            System.out.println("Success" + successfulCount);
            Room room = new Room();
            System.out.println(room);
            if(!room.isRoomOverlappedOrOutOfBound(this.world)) {
                rooms[successfulCount] = room;
                room.putRoomIntoWorld(this.world);
                successfulCount++;
            } else {
                failedCount++;
            }
        }


        ter.renderFrame(this.world);
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    public Game(int seed) {
        this.rand = new Random(seed);
    }
}
