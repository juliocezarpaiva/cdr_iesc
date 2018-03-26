package principal_cdr;

public class Testa {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        System.out.println("Soundex 1:");
        String[] nome = {"MARCELO", "PIRES", "MORAES"};

        for (String fragmento : nome) {
            System.out.println(Utilidade.soundex(fragmento));

        }
        System.out.println("----");
        System.out.println("Soundex 2");
        String[] nome1 = {"MARCELO", "PIRE", "MORAES"};

        for (String fragmento : nome1) {
            System.out.println(Utilidade.soundex(fragmento));

        }

    }

}
