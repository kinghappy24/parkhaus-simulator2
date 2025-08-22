package delal.parkhaus;

import java.util.Scanner;

public class Main {
    // Scanner bleibt static, aber Anfänger schreiben oft keinen guten Namen
    static Scanner scann = new Scanner(System.in);
    static Parkhaus ph;       // kurz + unklar
    static Ticket tik;        // Ticket -> tik (Anfänger kürzen komisch ab)

    public static void main(String[] args) {
        while (true) {
            System.out.println("Das Parkhaus Simulator 2");  // \n fehlt manchmal
            System.out.println("1 scenario");                // Anfänger vergessen Punkte
            System.out.println("2 scenario");
            System.out.println("exit"); 
            System.out.print(">> ");   // anderer Prompt

            String eingabe = scann.nextLine();
            eingabe = eingabe.toLowerCase();   // extra Zeile, obwohl man es direkt machen könnte

            if (eingabe.equals("1")) {
                ph = new Parkhaus(20,0);   // Anfänger lassen Leerzeichen weg
                startMenue();
            } else if (eingabe.equals("2")) {
                ph = new Parkhaus(20,20);
                System.out.println("OH OH Ticket nicht moeglich Parkhaus voll!!!"); // chaotischer Text
            } else if (eingabe.equals("exit")) {
                System.out.println("tschüss"); // uneinheitliche Schreibweise
                break;
            } else {
                System.out.println("Falsche eingabe tippen sie nochmal");
            }
        }
    }

    static void startMenue() {  // "Menue" statt "Menu"
        tik = null;  // Reset
        while (true) {
            System.out.println("Willkommen im Parkhaus 2"); // Begrüßung ohne \n
            System.out.println("frei " + ph.freiePlaetze() + " von " + ph.kapazitaet);
            System.out.println("Befehle: ticket | preisliste | bezahlen | exit | back");
            System.out.print(">> ");

            String cmd = scann.nextLine();
            cmd = cmd.toLowerCase();

            if (cmd.equals("ticket")) {
                zieheTicket();
            } else if (cmd.equals("preisliste")) {
                preise();
            } else if (cmd.equals("bezahlen")) {
                zahlen();
            } else if (cmd.equals("exit")) {
                raus();
            } else if (cmd.equals("back")) {
                return;
            } else {
                System.out.println("unbekannt");
            }
        }
    }

    static void zieheTicket() {
        if (tik != null && tik.exited==false) { // Anfänger nutzen ==false statt !
            System.out.println("Sie haben schon ein Ticket Kosten: " + kosten(tik) + " CHF");
            return;
        }
        if (ph.kannEinfahren()==false) {
            System.out.println("OH OH voll!");
            return;
        }
        tik = new Ticket();
        ph.einfahren();
        System.out.println("ticket gemacht.");
        System.out.println("schranke offen");
    }

    static void preise() {
        System.out.println("1 min=10CHF");
        System.out.println("2 min=15CHF");
        System.out.println("3-4min=20CHF");
        System.out.println("5-9min=21CHF");
        System.out.println(">=10min=25CHF");
    }

    static void zahlen() {
        if (tik==null || tik.exited) {
            System.out.println("Kein Ticket jetzt");
            return;
        }
        if (tik.paid==true) { // Anfänger nutzen immer ==true
            System.out.println("Schon bezahlt jetzt raus");
            return;
        }
        while (true) {
            int k = kosten(tik);
            System.out.println("Kosten " + k + " CHF");
            System.out.println("zahlen? ja nein back");
            String ant = scann.nextLine();
            ant = ant.toLowerCase();
            if (ant.equals("ja")) {
                tik.paid=true;
                System.out.println("danke bezahlt");
                break;
            } else if (ant.equals("nein")) {
                System.out.println("okay nicht");
            } else if (ant.equals("back")) {
                break;
            } else {
                System.out.println("bitte ja nein back");
            }
        }
    }

    static void raus() {
        if (tik==null || tik.exited) {
            System.out.println("Kein Ticket exit");
            return;
        }
        if (!tik.paid) {
            System.out.println("nicht bezahlt noch");
            return;
        }
        ph.ausfahren();
        tik.exited=true;
        System.out.println("schranke hoch");
    }

    static int kosten(Ticket t) {
        long jetzt = System.currentTimeMillis() / 60000;
        long min = jetzt - t.startMinute; 
        if (min<1) min=1; // Anfänger Logik doppelt schreiben
        if (min==1) return 10;
        if (min==2) return 15;
        if (min<=4) return 20;
        if (min<=9) return 21;
        else return 25;  // else unnötig
    }
}
