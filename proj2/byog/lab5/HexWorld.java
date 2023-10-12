package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Position startPoint = new Position(10, 10);
        Position target = startPoint;
        drawRandomVerticalHexagon(3, target, world, 3);
        target = bottomRightHexagon(target, 3);
        drawRandomVerticalHexagon(3,target, world, 4);
        target = bottomRightHexagon(target, 3);
        drawRandomVerticalHexagon(3, target, world, 5);
        target = topRightHexagon(target, 3);
        drawRandomVerticalHexagon(3, target, world, 4);
        target = topRightHexagon(target, 3);
        drawRandomVerticalHexagon(3, target, world, 3);

        ter.renderFrame(world);
    }

    public static int rowWidth(int yi, int size) {
        int weight = yi;
        if(yi >= size) {
            weight = size * 2 - 1 - yi;
        }
        return size + 2 * weight;
    }

    public static int rowOffset(int yi, int size) {
        if(yi >= size) {
            return size * 2 - 1 -yi;
        }
        return yi;
    }

    private static void drawRow(TETile[][] world, Position p, int y, int offset, int rowWidth, TETile t) {
        int xStart = p.x - offset;
        for(int i = 0; i < rowWidth; i++) {
            world[xStart + i][y] = t;
        }
    }



    public static void addHexagon(Position p, TETile[][] world, int size, TETile t) {
        for (int i = 0; i < size * 2; i++) {
            int width = rowWidth(i, size);
            int offset = rowOffset(i, size);
            drawRow(world, p, p.y+i, offset, width, t);
        }
    }

    public static void drawRandomVerticalHexagon(int size, Position p, TETile[][] world, int times) {
        Position positionToDraw = p;

        for (int i = 0; i < times; i++) {
            TETile t;
            int r = (int) Math.floor(Math.random() * 5);
            switch (r) {
                case 1:
                    t = Tileset.WALL;
                    break;
                case 2:
                    t = Tileset.PLAYER;
                    break;
                case 3:
                    t = Tileset.GRASS;
                    break;
                case 4:
                    t = Tileset.TREE;
                    break;
                case 5:
                    t = Tileset.MOUNTAIN;
                    break;
                default:
                    t = Tileset.FLOWER;
            }
            addHexagon(positionToDraw, world, size, t);
            positionToDraw = new Position(positionToDraw.x , positionToDraw.y + size * 2);
        }
    }

    public static Position bottomRightHexagon(Position p, int size) {
        return new Position(p.x + size * 2 - 1, p.y - size);
    }

    public static Position topRightHexagon(Position p, int size) {
        return new Position(p.x + size * 2 - 1, p.y + size);
    }
}
