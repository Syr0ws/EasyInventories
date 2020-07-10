package fr.syrows.easyinventories.exceptions;

public class InvalidPositionException extends RuntimeException {

    private int position;

    public InvalidPositionException(String message, int position) {
        super(message);
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
