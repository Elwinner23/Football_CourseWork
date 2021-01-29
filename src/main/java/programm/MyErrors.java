package programm;

public class MyErrors extends Exception {
    private String message;

    public MyErrors(String myMessage) {
        this.message = myMessage;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
