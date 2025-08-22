package delal.parkhaus;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Parkhaus parkhaus;
    static Ticket ticket;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nDas Parkhaus Simulator 2");
            System.out.println("1. scenario");
            System.out.println("2. scenario");
            System.out.println("exit");
            System.out.print("> ");

            String input = sc.nextLine().toLowerCase();
            if (input.equals("1")) {
                parkhaus = new Parkhaus(20, 0);   
                startMenu();
            } else if (input.equals("2")) {
                parkhaus = new Parkhaus(20, 20);  
                System.out.println("OH OH, Ticket kann nicht bezogen werden, da das Parkhaus voll ist.");
                System.out.println("Kommen Sie spaeter nochmals.");
                
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Bitte eine gueltige Eingabe machen.");
            }
        }
    }

    static void startMenu() {
        ticket = null;
        while (true) {
            System.out.println("\nHallo, willkommen im Parkhaus 2!");
            System.out.printf("Freie Plaetze: %d/%d\n", parkhaus.freiePlaetze(), parkhaus.kapazitaet);
            System.out.println("Befehle: ticket ziehen | preisliste | bezahlen | pkh exit | back");
            System.out.print("> ");

            String cmd = sc.nextLine().toLowerCase();
            if (cmd.equals("ticket ziehen")) {
                ticketZiehen();
            } else if (cmd.equals("preisliste")) {
                showPreise();
            } else if (cmd.equals("bezahlen")) {
                bezahlen();
            } else if (cmd.equals("pkh exit")) {
                ausfahren();
            } else if (cmd.equals("back")) {
                return;
            } else {
                System.out.println("Unbekannter Befehl.");
            }
        }
    }

    static void ticketZiehen() {
        if (ticket != null && !ticket.exited) {
            System.out.printf("Sie besitzen bereits ein Ticket. Kosten bisher: %d CHF\n", kosten(ticket));
            return;
        }
        if (!parkhaus.kannEinfahren()) {
            System.out.println("OH OH, Parkhaus leider voll!");
            return;
        }
        ticket = new Ticket();
        parkhaus.einfahren();
        System.out.println("Ticket gezogen.");
        System.out.println("Schranke geht hoch.");
        System.out.println("Sie haben geparkt.");
    }

    static void showPreise() {
        System.out.println("1 Min  = 10 CHF");
        System.out.println("2 Min  = 15 CHF");
        System.out.println("3-4 Min= 20 CHF");
        System.out.println("5-9 Min= 21 CHF");
        System.out.println(">=10   = 25 CHF");
    }

    static void bezahlen() {
        if (ticket == null || ticket.exited) {
            System.out.println("Kein Ticket vorhanden.");
            return;
        }
        if (ticket.paid) {
            System.out.println("Schon bezahlt. Sie koennen ausfahren.");
            return;
        }
        while (true) {
            int k = kosten(ticket);
            System.out.println("Ihre aktuellen Kosten betragen " + k + " CHF");
            System.out.println("Wollen Sie jetzt bezahlen? (ja/nein/back)");
            System.out.print("> ");
            String s = sc.nextLine().toLowerCase();
            if (s.equals("ja")) {
                ticket.paid = true;
                System.out.println("Vielen Dank fuer die Zahlung. Sie koennen jetzt das Parkhaus verlassen.");
                break;
            } else if (s.equals("nein")) {
                System.out.println("Okay.");
            } else if (s.equals("back")) {
                break;
            } else {
                System.out.println("Bitte ja/nein/back eingeben.");
            }
        }
    }

    static void ausfahren() {
        if (ticket == null || ticket.exited) {
            System.out.println("Kein Ticket.");
            return;
        }
        if (!ticket.paid) {
            System.out.println("Oops, nicht bezahlt. Bitte erst bezahlen.");
            return;
        }
        parkhaus.ausfahren();
        ticket.exited = true;
        System.out.println("Schranke geht hoch ");
    }

    static int kosten(Ticket t) {
        long jetztMinute = System.currentTimeMillis() / 60000;
        long min = Math.max(1, jetztMinute - t.startMinute);
        if (min == 1) return 10;
        if (min == 2) return 15;
        if (min <= 4) return 20;   
        if (min <= 9) return 21;   
        return 25;                 
    }
}
