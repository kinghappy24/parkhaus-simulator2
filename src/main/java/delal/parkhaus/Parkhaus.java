package delal.parkhaus;

public class Parkhaus {
    int kapazitaet;
    int belegt;

        public Parkhaus(int kapazitaet, int start) {
            this.kapazitaet = kapazitaet;

            this.belegt = start;
        }

    public int freiePlaetze() {


        return kapazitaet - belegt;
    }

            public boolean kannEinfahren() {
                return belegt < kapazitaet;


            }

    public void einfahren() {
        if (kannEinfahren()) belegt++;

    }

    public void ausfahren() {
        if (belegt > 0) belegt--;

    }
}
