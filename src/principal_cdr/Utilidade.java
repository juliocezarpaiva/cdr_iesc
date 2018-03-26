package principal_cdr;

import java.text.Normalizer;

public class Utilidade {

    public static int LevenshteinDistance (String s0, String s1) {
        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamicaly computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public static String minusculoSemAcento(String palavra) {
        palavra = Normalizer.normalize(palavra.trim().toLowerCase(), Normalizer.Form.NFD);
        return palavra.replaceAll("[^\\p{ASCII}]", "").replace("\\", "");
    }

    public static String padroniza(String str){
        if(str.trim().length() == 0)
            return str.trim();

        str = minusculoSemAcento(str);
        String string_padronizada = new String();
        char[] str_char_array = str.toCharArray();

        //Retira numeros e caracteres especiais
        for(int i = 0 ; i < str_char_array.length ; i++){
            if(str_char_array[i] < '0' || str_char_array[i] > '9')
            {
                switch(str_char_array[i]){
                    case '(':
                        break;
                    case ')':
                        break;
                    case '\'':
                        break;
                    case '/':
                        break;
                    case '.':
                        break;
                    case ',':
                        break;
                    case ';':
                        break;
                    case ':':
                        break;
                    case '-':
                        break;
                    case '%':
                        break;
                    case '#':
                        break;
                    case '@':
                        break;
                    case '$':
                        break;
                    case '*':
                        break;
                    case '&':
                        break;
                    case '?':
                        break;
                    case '^':
                        break;
                    case '\\':
                        break;
                    default:
                        string_padronizada += str_char_array[i];
                }
            }
        }

        String[] str_split = string_padronizada.split(" ");
        string_padronizada = new String();

        //Remove preposicoes do nome
        for(int i = 0 ; i < str_split.length ; i++){
            if(str_split[i].trim().length() > 0){
                switch(str_split[i]){
                    case "de":
                        break;
                    case "do":
                        break;
                    case "da":
                        break;
                    case "dos":
                        break;
                    case "das":
                        break;
                    default:
                        if(str_split[i].trim().length() > 0)
                            string_padronizada += str_split[i] + " ";
                }
            }
        }

        string_padronizada = string_padronizada.trim();

        if(string_padronizada.endsWith(" junior")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-7);
        }else if(string_padronizada.endsWith(" jr")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-3);
        }else if(string_padronizada.endsWith(" neto")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-5);
        }else if(string_padronizada.endsWith(" bisneto")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-8);
        }else if(string_padronizada.endsWith(" filho")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-6);
        }else if(string_padronizada.endsWith(" filha")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-6);
        }else if(string_padronizada.endsWith(" sobrinha")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-9);
        }else if(string_padronizada.endsWith(" sobrinho")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-9);
        }else if(string_padronizada.endsWith(" segundo")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-8);
        }else if(string_padronizada.endsWith(" terceiro")){
            string_padronizada = string_padronizada.substring(0, string_padronizada.length()-9);
        }

        return string_padronizada;
    }

    public static String soundex(String str){
        String soundex = new String();
        String banidos = "aeiouyweh";
        String um = "bpfv";
        String dois = "cskgjqxz";
        String tres = "dt";
        String quatro = "l";
        String cinco = "mn";
        String seis = "r";

        str = str.trim().toLowerCase();


        if(str.length() > 0){
            char primeira = str.charAt(0);

            //Primeira letra Y -> Primeira letra passa a I
            if( primeira == 'y'){
                primeira = 'i';
            }
            else if(str.length() > 1) {
                char segunda = str.charAt(1);

                switch (primeira){
                    //Primeira letra H -> Elimina a primeira letra
                    case 'h':
                        primeira = segunda;
                        str = (String) str.subSequence(1, str.length()-1);
                        break;
                    //Primeira letra W e segunda A -> Primeira letra passa a V
                    case 'w':
                        if (segunda=='a') primeira = 'v';
                        break;
                    //Primeira letra K e segunda A, O ou U -> Primeira letra passa a C
                    case 'k':
                        if(segunda == 'a' || segunda == 'o' || segunda == 'u'){
                            primeira = 'c';
                        }
                        break;
                    //Primeira letra C e segunda E ou I -> Primeira letra passa a S
                    case 'c':
                        if(segunda == 'e' || segunda == 'i'){
                            primeira = 's';
                        }
                        break;
                    //Primeira letra G e segunda E ou I -> Primeira letra passa a J
                    case 'g':
                        if(segunda == 'e' || segunda == 'i'){
                            primeira = 'j';
                        }
                        break;
                }
            }

            soundex += primeira;
            for(int i = 1; i < str.length() && soundex.length() < 4; i++){
                String ch = "" + str.charAt(i);
                if(banidos.contains(ch));

                else if(um.contains(ch))
                    soundex += soundex.substring(soundex.length() - 1).equals("1") ? "" : "1";
                else if(dois.contains(ch))
                    soundex += soundex.substring(soundex.length() - 1).equals("2") ? "" : "2";
                else if(tres.contains(ch))
                    soundex += soundex.substring(soundex.length() - 1).equals("3") ? "" : "3";
                else if(quatro.contains(ch))
                    soundex += soundex.substring(soundex.length() - 1).equals("4") ? "" : "4";
                else if(cinco.contains(ch))
                    soundex += soundex.substring(soundex.length() - 1).equals("5") ? "" : "5";
                else if(seis.contains(ch))
                    soundex += soundex.substring(soundex.length() - 1).equals("6") ? "" : "6";
            }

            for(int i = soundex.length(); i < 4 ; i++){
                soundex += "0";
            }
        }

        return soundex;
    }
}