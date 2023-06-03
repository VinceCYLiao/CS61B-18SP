public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private final static double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    public double calcForceExertedBy(Planet p) {
        double r = this.calcDistance(p);
        return (G * this.mass * p.mass) / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return F * dx / r;
    }

    public double calcForceExertedByY(Planet p) {
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return F * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double netFx = 0.0;
        for (Planet p : planets) {
            if (this.equals(p)) {
                continue;
            }
            netFx += calcForceExertedByX(p);
        }
        return netFx;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double netFy = 0.0;
        for (Planet p : planets) {
            if (this.equals(p)) {
                continue;
            }
            netFy += calcForceExertedByY(p);
        }
        return netFy;
    }

    public void update(double dt, double fx, double fy) {
        var ax = fx / this.mass;
        var ay = fy / this.mass;
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw() {
    }
}