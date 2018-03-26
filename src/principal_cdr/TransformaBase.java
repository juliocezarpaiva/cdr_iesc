package principal_cdr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformaBase {

    private static Map<String, Integer> count_primeiro_nome;
    private static Map<String, Integer> count_ultimo_nome;
    private static Map<String, Integer> count_nome_do_meio;

    private static Map<String, Integer> count_primeiro_nome_mae;
    private static Map<String, Integer> count_ultimo_nome_mae;
    private static Map<String, Integer> count_nome_do_meio_mae;

    //Funcao que retorna mapas de frequencias de acordo com tabela de frequencia externa
    public static List<Map<String, Integer>> guardaFrequencias(String filepath_prim_nome_pac, String filepath_nome_meio_pac, String filepath_ult_nome_pac, String filepath_prim_nome_mae, String filepath_nome_meio_mae, String filepath_ult_nome_mae) {
        List<Map<String, Integer>> count_completo = new ArrayList<Map<String, Integer>>();
        try {
            LeTabela(filepath_prim_nome_pac, count_primeiro_nome, count_completo);
            LeTabela(filepath_nome_meio_pac, count_nome_do_meio, count_completo);
            LeTabela(filepath_ult_nome_pac, count_ultimo_nome, count_completo);
            LeTabela(filepath_prim_nome_mae, count_primeiro_nome_mae, count_completo);
            LeTabela(filepath_nome_meio_mae, count_nome_do_meio_mae, count_completo);
            LeTabela(filepath_ult_nome_mae, count_ultimo_nome_mae, count_completo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count_completo;
    }

    public static void LeTabela(String Filepath, Map<String, Integer> mapa_frequencias, List<Map<String, Integer>> count_completo) throws Exception{
        CsvReader dados = new CsvReader(Filepath, ';');
        dados.readHeaders();

        mapa_frequencias = new HashMap<String, Integer>();
        while (dados.readRecord())
        {
            //este if que verifica se o arquivo está lendo "" vazio teve que ser implementado para que o programa pare de rodar! :)
            if(dados.get(dados.getHeader(0)).toLowerCase() == "") break;
            mapa_frequencias.put(dados.get(dados.getHeader(0)).toLowerCase(), Integer.valueOf(dados.get(dados.getHeader(1))));
        }
        count_completo.add(0, mapa_frequencias);
        dados.close();
    }


    //Funcao que retorna mapas de frequancias de acordo com a base atual
    public static List<Map<String, Integer>> contaFrequencia(String filepath, int Nome1, int Mae1, int Nome2, int Mae2) {
        List<Map<String, Integer>> count_completo = new ArrayList<Map<String, Integer>>();
        try {

            CsvReader dados = new CsvReader(filepath, ';');
            dados.readHeaders();

            while (dados.readRecord())
            {

                //Obtem dados dos pacientes atraves do arquivo csv
                String nome_paciente1 = Utilidade.padroniza(dados.get(dados.getHeader(Nome1)));
                String mae_paciente1 = Utilidade.padroniza(dados.get(dados.getHeader(Mae1)));

                String nome_paciente2 = Utilidade.padroniza(dados.get(dados.getHeader(Nome2)));
                String mae_paciente2 = Utilidade.padroniza(dados.get(dados.getHeader(Mae2)));

                if (nome_paciente1.length() > 0){
                    contaNomes(nome_paciente1);
                }
                if (nome_paciente2.length() > 0){
                    contaNomes(nome_paciente2);
                }
                if (mae_paciente1.length() > 0){
                    contaNomes(mae_paciente1);
                }
                if (mae_paciente2.length() > 0){
                    contaNomes(mae_paciente2);
                }



            }
            count_completo.add(0, count_primeiro_nome);
            count_completo.add(1, count_nome_do_meio);
            count_completo.add(2, count_ultimo_nome);
            dados.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count_completo;
    }

    private static void contaNomes(String str){
        if(count_primeiro_nome == null) count_primeiro_nome = new HashMap<String, Integer>();
        if(count_ultimo_nome == null) count_ultimo_nome = new HashMap<String, Integer>();
        if(count_nome_do_meio == null) count_nome_do_meio = new HashMap<String, Integer>();

        String[] str_split = str.split(" ");

        if(str_split.length > 0){

            //Faz a contagem de primeiro nome.
            String primeiro_nome = str_split[0];
            adicionaNome(count_primeiro_nome, primeiro_nome);
        }

        if(str_split.length > 1){
            //Faz a contagem de nomes do meio
            for(int i = 1; i < str_split.length - 1 ; i++){
                String nome_do_meio = str_split[i];
                adicionaNome(count_nome_do_meio, nome_do_meio);
            }

            //Faz a contagem de ultimo nome
            String ultimo_nome = str_split[str_split.length - 1];
            adicionaNome(count_ultimo_nome, ultimo_nome);
        }
    }

    private static void adicionaNome(Map<String, Integer> contador, String nome){
        if(contador.containsKey(nome)){
            int count_atual = contador.get(nome);
            contador.remove(nome);
            contador.put(nome, count_atual + 1);

        } else {
            contador.put(nome, 1);
        }
    }

}
