package sample;

public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(int anotherX, int anotherY) {
        return (this.x == anotherX && this.y == anotherY);
    }
    public boolean equals(Position another) {
        return (this.x == another.x && this.y == another.y);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}