import Gielda.Symulacja;

public class Main {
    public static void main(String[] args) {
        String nazwaPliku = args[0];
        int liczbaTur = Integer.parseInt(args[1]);
        Symulacja symulacja = new Symulacja(nazwaPliku, liczbaTur);
        symulacja.start();
    }
}