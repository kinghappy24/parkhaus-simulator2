package delal.parkhaus;

public class Ticket {
    long startMinute;   
    boolean paid;       
    boolean exited;     

    public Ticket() {
        this.startMinute = System.currentTimeMillis() / 60000;
        this.paid = false;
        this.exited = false;
    }
}
