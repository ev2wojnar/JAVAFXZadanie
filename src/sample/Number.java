package sample;

public class Number extends Thread {
    private int value;
    private Position position;
    private boolean toKill = false;
    public boolean shouldMove = false;


    public Number(int value, Position position) {
        this.value = value;
        this.position = position;
    }

    @Override
    public void run() {
//        System.out.println("me!");
        while (true) {
            try {
                Thread.sleep(Main.PLANCK_TIME);
            } catch (InterruptedException e) {
                System.out.println("och, no!");
            }
            if (toKill) return;
            boolean isZero = false;
            if (this.value == 0) {
                if (NumbersMovementsService.numbers.size() == 1) this.kill();
                isZero=true;
            }
            if (shouldMove) {
                position = NumbersMovementsService.move(position, isZero);
                shouldMove = false;
            }
//            System.out.println("Jestem "+ this.value + ", jestem na " + this.getPosition().x + ", " + this.getPosition().y);
        }
    }

    public void kill() {
//        System.out.println("Bye, bye!");
        this.toKill = true;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
