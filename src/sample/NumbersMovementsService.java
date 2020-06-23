package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static java.lang.Integer.compare;


public class NumbersMovementsService {
    public static final int NUMBER_OF_NUMBERS = 30;
    public static List<Number> numbers = new ArrayList<Number>();
    public static Number getOccupant(Position position) {
        return getOccupant(position.x, position.y);
    }
    public static Thread rollNumberToMove = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(Main.PLANCK_TIME);
            } catch (InterruptedException e) {

            }
            numbers.get((int)Math.floor(Math.random()*NUMBER_OF_NUMBERS)).shouldMove = true;
            if (1<numbers.size())
                rollNumberToMove
                        .start();
        }
    };
    public static Number getOccupant(int x, int y) {
        for (Number elem:numbers) {
            if (elem.getPosition().equals(x, y)){
                return elem;
            }
        }
        return null;
    }
    public static void initialize() {
//        rollNumberToMove.start();
        for (int i = 1; i <= NUMBER_OF_NUMBERS; i++) {
            Position rolledPositions = new Position(rollPosition(), rollPosition());

            if (getOccupant(rolledPositions) != null) {
//                System.out.println(i);
                i--;
                continue;
            }
            Number numberToAdd = new Number(i-1, rolledPositions);
            System.out.println("Stworzono " + numberToAdd.getValue() + " na " + numberToAdd.getPosition().x + ", " +numberToAdd.getPosition().y);
            numberToAdd.start();
            numbers.add(numberToAdd);
        }
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                try {
                    Thread.sleep(Main.PLANCK_TIME);
                } catch (InterruptedException e) {

                }
                numbers.get((int)Math.floor(Math.random()*numbers.size())).shouldMove = true;
                if (1==numbers.size()) return;
            }
        },0,Main.PLANCK_TIME);

    }
    public static int rollPosition() {
        return (int)Math.floor(Math.random()*10)+1;
    }

    public static Position moveZero (Position currentPosition) {
        Position newPosition = rollDirection(currentPosition);
        Number occupant = getOccupant(newPosition);
        if (occupant != null) {
            if (isPrime(occupant.getValue())) {
                System.out.println("Zero move to " + newPosition + ". Kill " + occupant + " at " + occupant.getPosition());
                numbers.remove(occupant);
                occupant.kill();
            } else {
                occupant.setValue(occupant.getValue()+1);
                occupant.setPosition(currentPosition);
            }
        }
        return newPosition;
    }
    public static Position move (Position currentPosition, boolean isZero) {
        if (isZero) return moveZero(currentPosition);
        Position newPosition;
        if (Math.random() <= getGravityPowerToZero(currentPosition)) {
            Position directionToZero = getDirectionToZero(currentPosition);
            newPosition = new Position(currentPosition.x + directionToZero.x, currentPosition.y + directionToZero.y);
        } else newPosition = rollDirection(currentPosition);
        if (getOccupant(newPosition) != null) {
            newPosition = rollDirection(currentPosition);
            if (getOccupant(newPosition) != null) return currentPosition;
        }
        return newPosition;
    }
    public static Position rollDirection(Position currentPosition) {
        double rand = Math.random();
        if (rand < 0.25d) {
            if (currentPosition.y == 10) return rollDirection(currentPosition);
            return new Position(currentPosition.x, currentPosition.y + 1);
        } else if (rand < 0.5d) {
            if (currentPosition.x == 10) return rollDirection(currentPosition);
            return new Position(currentPosition.x + 1, currentPosition.y);
        } else if (rand < 0.75d) {
            if (currentPosition.y == 1) return rollDirection(currentPosition);
            return new Position(currentPosition.x, currentPosition.y - 1);
        } else {
            if (currentPosition.x == 1) return rollDirection(currentPosition);
            return new Position(currentPosition.x - 1, currentPosition.y);
        }
    }
    public static double getGravityPowerToZero(Position position) {
        return 1-(getDistance(position, numbers.get(0).getPosition()))/Math.sqrt(200);
    }
    public static Position getDirectionToZero(Position position) {
        Position difference = new Position (numbers.get(0).getPosition().x-position.x, numbers.get(0).getPosition().y-position.y);
//        System.out.println(Math.abs(difference.x) + " / (" + (Math.abs(difference.x)+ " + " + Math.abs(difference.y) + ")"));
        if (Math.random() < Math.abs(difference.x)/(Math.abs(difference.x)+Math.abs(difference.y))) {
            return new Position(compare(difference.x,0), 0);
        } else return new Position(0, compare(difference.y,0));
    }
    public static double getDistance (Position positionOne, Position positionTwo) {
        return Math.sqrt((positionOne.x-positionTwo.x)*(positionOne.x-positionTwo.x) +
                (positionOne.y-positionTwo.y)*(positionOne.y-positionTwo.y));
    }
    public static boolean isPrime (int number) {
        if (number == 1) return false;
        if (number == 2) return true;
        if (number == 3) return true;
        int half = number/2;
        for (int i = 2; i < half+1; i++){
            if (number%i == 0) return false;
        }
        return true;
    }
}
