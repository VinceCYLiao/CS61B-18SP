class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, createImagePath("starfield.jpg"));
        for (Planet planet : planets) {
            StdDraw.picture(planet.xxPos, planet.yyPos, createImagePath(planet.imgFileName));
        }
        StdDraw.enableDoubleBuffering();
        for (double i = 0; i <= T; i += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int j = 0; j < planets.length; j++) {
                Planet p = planets[j];
                xForces[j] = p.calcNetForceExertedByX(planets);
                yForces[j] = p.calcNetForceExertedByY(planets);
                p.update(dt, xForces[j], yForces[j]);
            }
            StdDraw.picture(0, 0, createImagePath("starfield.jpg"));
            for (Planet p : planets) {
                StdDraw.picture(p.xxPos, p.yyPos, createImagePath(p.imgFileName));
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        System.out.println(planets.length);
        System.out.println(radius);
        for (Planet planet : planets) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s%n", planet.xxPos, planet.yyPos, planet.xxVel, planet.yyVel, planet.mass, planet.imgFileName);
        }
    }

    private static String createImagePath(String fileName) {
        return String.format("images/%s", fileName);
    }

    public static double readRadius(String filePath) {
        In in = new In(filePath);
        int numberOfPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;

    }

    public static Planet[] readPlanets(String filePath) {
        In in = new In(filePath);
        int numberOfPlanets = in.readInt();
        Planet[] arr = new Planet[numberOfPlanets];
        double radius = in.readDouble();
        for (int i = 0; i < numberOfPlanets; i++) {
            arr[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return arr;
    }
}