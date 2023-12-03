package byog.Core;

import byog.Player;
import byog.Position;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private static final int MAX_ROOM = 15;
    private static final int WORLD_PADDING = 3;
    private static final int ROOM_PADDING = 2;
    private static final int MAX_TRAIL = 500;
    private static final int WORLD_WIDTH = WIDTH - WORLD_PADDING * 2;
    private static final int WORLD_HEIGHT = HEIGHT - WORLD_PADDING * 2;
    private TETile[][] world = new TETile[WORLD_WIDTH][WORLD_HEIGHT];
    private Random rand;
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<ArrayList<Position>> hallways = new ArrayList<>();

    private Player player;
    private static final String SAVE_FILE_NAME = "save.txt";
    private static class GameData implements Serializable {
        TETile[][] world;
        Player player;

        public GameData(TETile[][] world, Player player) {
            this.world = world;
            this.player = player;
        }
    }

    private void createHallwayBetweenRooms(Room room1, Room room2) {
        Position start = room1.randomInnerPoint();
        Position end = room2.randomInnerPoint();
        ArrayList<Position> hallway = new ArrayList<>();
        hallway.add(start);
        int direction = this.rand.nextInt(2);
        int xStep = end.x > start.x ? 1 : -1;
        int yStep = end.y > start.y ? 1 : -1;
        Position current = new Position(start.x, start.y);
        // start vertically
        if (direction == 0) {
            while (current.x != end.x) {
                Position next = new Position(current.x + xStep, current.y);
                current = next;
                hallway.add(next);
            }
            while (current.y != end.y) {
                Position next = new Position(current.x, current.y + yStep);
                current = next;
                hallway.add(next);
            }
        } else {
            while (current.y != end.y) {
                Position next = new Position(current.x, current.y + yStep);
                current = next;
                hallway.add(next);
            }
            while (current.x != end.x) {
                Position next = new Position(current.x + xStep, current.y);
                current = next;
                hallway.add(next);
            }
        }
        hallways.add(hallway);
    }

    private void buildHallway(Position p) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = p.x + i;
                int y = p.y + j;
                if (i == 0 && j == 0) {
                    world[x][y] = Tileset.FLOOR;
                } else if (world[x][y].equals(Tileset.NOTHING)) {
                    world[x][y] = Tileset.WALL;
                }

            }
        }
    }

    private void buildHallways() {
        hallways.forEach(hallway -> hallway.forEach(this::buildHallway));
    }

    private class Room {
        private int roomWidth;
        private int roomHeight;
        private Position position;
        private static final int MAX_ROOM_WIDTH = 30;
        private static final int MAX_ROOM_HEIGHT = 30;

        public Room() {
            this.position = new Position(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            this.roomWidth = rand.nextInt(MAX_ROOM_WIDTH - 5) + 5;
            this.roomHeight = rand.nextInt(MAX_ROOM_HEIGHT - 5) + 5;
        }

        public void putRoomIntoWorld() {
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

        public boolean isRoomOverlappedOrOutOfBound() {
            for (int i = 0; i < this.roomWidth; i++) {
                int x = position.x + i;
                for (int j = 0; j < this.roomHeight; j++) {
                    int y = position.y + j;
                    // out of bound
                    if (
                            x >= WORLD_WIDTH || y >= WORLD_HEIGHT || x < 0 || y < 0
                    ) {
                        return true;
                    }
                    // overlap
                    if (world[x][y] != Tileset.NOTHING) {
                        return true;
                    }
                }
            }
            return false;
        }

        public Position randomInnerPoint() {
            int x = this.position.x;
            int y = this.position.y;
            return new Position(
                    rand.nextInt(this.roomWidth - 2) + 1 + x,
                    rand.nextInt(this.roomHeight - 2) + 1 + y
            );
        }
    }


    public void renderWorld(boolean playWithKeyboard) {
        for (int i = 0; i < WORLD_WIDTH; i++) {
            for (int j = 0; j < WORLD_HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        int trial = 0;
        while (rooms.toArray().length != MAX_ROOM && trial != MAX_TRAIL) {
            Room room = new Room();
            trial++;
            if (!room.isRoomOverlappedOrOutOfBound()) {
                rooms.add(room);
                room.putRoomIntoWorld();
            }
        }

        for (int i = 0; i < rooms.size() - 1; i++) {
            createHallwayBetweenRooms(rooms.get(i), rooms.get(i + 1));
        }

        buildHallways();

        if (playWithKeyboard) {
            ter.initialize(WIDTH, HEIGHT, WORLD_PADDING, WORLD_PADDING);
            ter.renderFrame(this.world);
        }

    }

    private static void drawMenu() {
        StdDraw.setCanvasSize(1000, 500);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.ITALIC, 60));
        StdDraw.text(0.5, 0.8, "CS61B: The Game");
        StdDraw.setFont();
        double yStep = 0.05;
        double menuActionY = 0.4;
        for (MenuActions action : MenuActions.values()) {
            StdDraw.text(0.5, menuActionY, action + "(" + action.getActionChar() + ")");
            menuActionY -= yStep;
        }

    }

    private static boolean isValidAction(char action) {
        action = Character.toUpperCase(action);
        for (MenuActions menuAction : MenuActions.values()) {
            if (menuAction.getActionChar() == action) {
                return true;
            }
        }
        return false;
    }

    private enum MenuActions {
        NEW_GAME('N'),
        LOAD_GAME('L'),
        QUIT('Q');;

        private final char actionChar;

        MenuActions(char n) {
            this.actionChar = n;
        }

        public char getActionChar() {
            return this.actionChar;
        }

        public static MenuActions getMenuAction(char c) {
            for (MenuActions action : MenuActions.values()) {
                if (action.getActionChar() == Character.toUpperCase(c)) {
                    return action;
                }
            }
            return null;
        }
    }

    private static void drawPrompt(String seed) {
        StdDraw.clear(Color.black);
        StdDraw.text(0.5, 0.8, "Please enter a seed");
        StdDraw.text(0.5, 0.7, "Press S to stop input");
        if (seed.length() > 0) {
            StdDraw.text(0.5, 0.5, seed);
        }
    }

    private void showGameMenu() {
        drawMenu();
        MenuActions action = null;
        while (action == null) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (isValidAction(c)) {
                    action = MenuActions.getMenuAction(c);
                }
            }

        }
        StdDraw.clear();

        if (action.equals(MenuActions.NEW_GAME)) {
            StringBuilder seed = new StringBuilder();
            drawPrompt(seed.toString());
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    if (Character.isDigit(c)) {
                        seed.append(c);
                        drawPrompt(seed.toString());
                    }
                    if (Character.toLowerCase(c) == 's') {
                        break;
                    }
                }

            }
            this.rand = new Random(Integer.parseInt(seed.toString()));
            this.renderWorld(true);
            this.player = new Player(this.world, ter, true, this.rand);
        }
        if (action.equals(MenuActions.LOAD_GAME)) {
            GameData data = deserializeObject(SAVE_FILE_NAME);
            this.world = data.world;
            this.player = data.player;
            ter.initialize(WIDTH, HEIGHT, WORLD_PADDING, WORLD_PADDING);
            ter.renderFrame(this.world);
        }
    }

    private void drawHUD(String content) {
        ter.renderFrame(this.world);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(0, 3, content);
        StdDraw.show();
    }

    private void handleOperation(char operation) {
        if (Character.toLowerCase(operation) == 'd') {
            this.player.moveRight();
        }
        if (Character.toLowerCase(operation) == 'a') {
            this.player.moveLeft();
        }
        if (Character.toLowerCase(operation) == 'w') {
            this.player.moveUp();
        }
        if (Character.toLowerCase(operation) == 's') {
            this.player.moveDown();
        }
        if (Character.toLowerCase(operation) == 'q') {
            serializeObject(new GameData(this.world, this.player), SAVE_FILE_NAME);
        }
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        this.showGameMenu();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                handleOperation(c);
                if (Character.toLowerCase(c) == 'q') {
                    System.exit(1);
                    break;
                }
            }
        }
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
        input = input.toUpperCase();
        MenuActions gameMode = MenuActions.getMenuAction(input.charAt(0));
        if (gameMode != null) {
            int seedEndIndex = input.indexOf('S');
            if (gameMode.equals(MenuActions.NEW_GAME)) {
                String seed = input.substring(1, seedEndIndex);
                String operations = input.substring(seedEndIndex + 1);
                this.rand = new Random(Long.parseLong(seed));
                this.renderWorld(false);
                this.player = new Player(this.world, this.ter, false, this.rand);
                for (char operation : operations.toCharArray()) {
                    handleOperation(operation);
                }
            }
            if (gameMode.equals(MenuActions.LOAD_GAME)) {
                GameData data = deserializeObject(SAVE_FILE_NAME);
                this.world = data.world;
                this.player = data.player;
                String operations = input.substring(1);
                for (char operation : operations.toCharArray()) {
                    handleOperation(operation);
                }
            }
        }

        return this.world;
    }

    private static void serializeObject(GameData obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static GameData deserializeObject(String fileName) {
        GameData deserializedObj = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            deserializedObj = (GameData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedObj;
    }
}
