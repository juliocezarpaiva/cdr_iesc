package principal_cdr;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComparaRegistros {

    private static final int PACIENTE = 0;
    private static final int MAE = 1;

    private static List<Map<String, Integer>> count_completo = new ArrayList<Map<String, Integer>>();
    private static String[] pontos;
    private static int contador_criterios;

    //Formata as notas para terem ate 2 casas decimais, no caso de decimais zero. ex: 2.53000000 -> 2.53
    private static DecimalFormat df = new DecimalFormat("0.00##");

    //															9			11		13			10		12			14
    public static void inicia(String filepath, String saida, int Nome1, int Mae1, int Nasc1, int Nome2, int Mae2, int Nasc2) {

        // Arquivos contendo as tabelas de frequencias
        String filepath1 = "01_Frequencia_primeiro_nome_paciente.csv" ;
        String filepath2 = "02_Frequencia_nome_do_meio_paciente.csv" ;
        String filepath3 = "03_Frequencia_ultimo_nome_paciente.csv" ;
        String filepath4 = "04_Frequencia_primeiro_nome_mae.csv" ;
        String filepath5 = "05_Frequencia_nome_do_meio_mae.csv" ;
        String filepath6 = "06_Frequencia_ultimo_nome_mae.csv" ;

        //imprime erro de falta de arquivo de registros e sai do programa
        if((!new File(filepath1).exists()) || (!new File(filepath2).exists()) || (!new File(filepath3).exists()) || (!new File(filepath4).exists())	|| (!new File(filepath5).exists()) || (!new File(filepath6).exists())) {
            TelaGUI.emiteMensagemFaltaFrequencias();
            System.exit(0);
        }

        // Realiza contagem de frequencia dos nomes.
        count_completo = TransformaBase.guardaFrequencias(filepath1, filepath2, filepath3, filepath4, filepath5, filepath6);

        // Retorna frequencia de acordo com a base atual
        //count_completo = TransformaBase.contaFrequencia(filepath, Nome1, Mae1, Nome2, Mae2);

        try {
            // prepara arquivo da base para leitura
            CsvReader dados = new CsvReader(filepath, '|'); // ATENCAO: o separador pode variar dependendo do arquivo
            dados.readHeaders();
            String[] header = dados.getHeaders();

            // ************* define titulo das colunas especificando cada criterio *************
            String[] criterio = new String[21];

            criterio[0] = "prim frag igual";
            criterio[1] = "ult frag igual";
            criterio[2] = "qtd frag iguais";
            criterio[3] = "qtd frag raros";
            criterio[4] = "qtd frag comuns";
            criterio[5] = "qtd frag muito parec";
            criterio[6] = "qtd frag abrev";

            criterio[7] = "mae prim frag igual";
            criterio[8] = "mae ult frag igual";
            criterio[9] = "mae qtd frag iguais";
            criterio[10] = "mae qtd frag raros";
            criterio[11] = "mae qtd frag comuns";
            criterio[12] = "mae qtd frag muito parec";
            criterio[13] = "mae qtd frag abrev";

            criterio[14] = "dt iguais";
            criterio[15] = "dt ap 1digi";
            criterio[16] = "dt inv dia";
            criterio[17] = "dt inv mes";
            criterio[18] = "dt inv ano";
            criterio[19] = "lvnstn apli";

            criterio[20] = "nota final";

            String[] header_novo = juntaString(header, criterio);
            // *********************************************************************************

            // ************* prepara arquivos de saida **************
            String csv = saida+".csv";
            CsvWriter writer = new CsvWriter(csv);
            writer.setDelimiter(';');
            writer.writeRecord(header_novo);
			/*
			String csv2 = saida+"2.csv";
			CsvWriter writer2 = new CsvWriter(csv2);
			writer2.setDelimiter('|');
			writer2.writeRecord(header_novo);
			*/
            // ******************************************************

            //loop de leitura/processamento dos dados
            while (dados.readRecord())
            {
                double nota = 0;
                String[] dados_originais = dados.getValues();
                pontos = new String[21];
                contador_criterios = 0;
                for (int i = 0; i < 21; i++){
                    pontos[i] = "0,0";
                }

                // Obtem dados dos pacientes atraves do arquivo csv, e coloca no formato padrao.
                String nome_paciente1 = Utilidade.padroniza(dados.get(Nome1));
                String mae_paciente1 = Utilidade.padroniza(dados.get(Mae1));
                String nascimento_paciente1 = dados.get(Nasc1);

                String nome_paciente2 = Utilidade.padroniza(dados.get(Nome2));
                String mae_paciente2 = Utilidade.padroniza(dados.get(Mae2));
                String nascimento_paciente2 = dados.get(Nasc2);

                //Confere se vieram preenchidos os campos de nome das maes dos pacientes, para depois processar.
                if ((mae_paciente1.length() > 0) && (mae_paciente2.length() > 0)){
                    nota = comparaNomes(mae_paciente1, mae_paciente2, nota, MAE);
                }

                // Confere se vieram preenchidos os campos de nome dos pacientes, para depois processar.
                if ((nome_paciente1.length() > 0) && (nome_paciente2.length() > 0)){
                    nota = comparaNomes(nome_paciente1, nome_paciente2, nota, PACIENTE);
                }

                //Confere se vieram preenchidos os campos de nascimento dos pacientes, para depois processar.
                if ((nascimento_paciente1.length() == 8) && (nascimento_paciente2.length() == 8)){
                    nota = comparaDatas(nascimento_paciente1, nascimento_paciente2, nota);
                }

                //TODO: Nao esta fazendo o criterio 20 de endereco, eh pra fazer?

                //Imprime nota final formatada
                pontos[20] = df.format(nota);


                String[] dados_novos = juntaString(dados_originais, pontos);
                writer.writeRecord(dados_novos);
                //writer2.writeRecord(dados_novos);

                //System.out.println(nascimento_paciente1 + " - " + nome_paciente1 + " - " + nome_paciente2 + " ; " + mae_paciente1 + " - " + mae_paciente2 + " - " + nascimento_paciente2 + " : " + nota);

            }

            //fecha arquivos
            dados.close();
            writer.close();
            //writer2.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // aplica os criterios de comparacao de nome de paciente/mae entre dois registros
    private static double comparaNomes(String nome1, String nome2, double nota, int flag){

        //Separa o nome completo dos dados em fragmentos.
        String[] nome1_fragmentado = nome1.split(" ");
        String[] nome2_fragmentado = nome2.split(" ");

        int tamanho_nome1 = nome1_fragmentado.length;
        int tamanho_nome2 = nome2_fragmentado.length;

        //Criterio 1/8: Primeiro fragmento do nome igual nas duas bases.
        if(nome1_fragmentado[0].equals(nome2_fragmentado[0])){
            nota++;
            pontos[contador_criterios] = "1,0";
        }
        contador_criterios++;


        //Criterio 2/9: ultimo fragmento do nome igual nas duas bases.
        if(nome1_fragmentado[tamanho_nome1 - 1].equals(nome2_fragmentado[tamanho_nome2 - 1])){
            nota++;
            pontos[contador_criterios] = "1,0";
        }
        contador_criterios++;


        //Criterio 3/10: razão de fragmentos iguais nos nomes pelo tamanho dos dois nomes.
        double fragmentos_iguais = 0;
        for (String fragmento1 : nome1_fragmentado){
            for (String fragmento2 : nome2_fragmentado){
                if (fragmento1.equals(fragmento2)){
                    fragmentos_iguais++;
                    break;
                }
            }
        }
        nota += ((fragmentos_iguais)/(tamanho_nome1+tamanho_nome2))*1.0;
        pontos[contador_criterios] = df.format(((fragmentos_iguais)/(tamanho_nome1+tamanho_nome2))*1.0);
        contador_criterios++;


        // Recupera o Mapa de frequencia de cada parte do nome
        Map<String, Integer> count_primeiro_nome = new HashMap<String, Integer>();
        count_primeiro_nome = count_completo.get(0 + 3*flag);

        Map<String, Integer> count_nome_do_meio = new HashMap<String, Integer>();
        count_nome_do_meio = count_completo.get(1 + 3*flag);

        Map<String, Integer> count_ultimo_nome = new HashMap<String, Integer>();
        count_ultimo_nome = count_completo.get(2 + 3*flag);

        Integer frequencia_max_prim_nome = 47781;
        Integer frequencia_max_meio_nome = 14715;
        Integer frequencia_max_ult_nome = 86624;
        Integer frequencia_max_prim_nome_mae = 105837;
        Integer frequencia_max_meio_nome_mae = 44094;
        Integer frequencia_max_ult_nome_mae = 57293;

        //Criterio 4/11: Quantidade de fragmentos raros no nome.
        //TODO: so ta fazendo usando o nome do primeiro paciente! (DONE)
        //double fragmentos_raros = 0;
        float score_de_raridade = 0;

        // verifica se frequencia do primeiro nome eh baixa (paciente 1)
        System.out.println("tamanho nome1: "+tamanho_nome1);
        Integer frequencia = count_primeiro_nome.get(nome1_fragmentado[0]);
        System.out.println(nome1_fragmentado[0]);
        if (frequencia == null) frequencia = 0;
        System.out.println("freq primeiro nome pac1: "+frequencia);
        score_de_raridade += (((frequencia_max_prim_nome/2) - frequencia)/(frequencia_max_prim_nome/2));
        System.out.println("score prim nome pac1: "+score_de_raridade);



        if (tamanho_nome1 > 1){
            // verifica se frequencia dos nomes do meio sao baixas
            // mesma coisa. vou fazer a volta tambem

            for (int i = 1; i < tamanho_nome1 - 1; i++){
                System.out.println(nome1_fragmentado[i]);
                frequencia = count_nome_do_meio.get(nome1_fragmentado[i]);
                if (frequencia == null) frequencia = 0;
                System.out.println("freq meio nome pac1: "+frequencia);
                score_de_raridade += (((frequencia_max_meio_nome/2) - frequencia)/(frequencia_max_meio_nome/2));
                System.out.println("score prim+meio nome pac1: "+score_de_raridade);
            }
            // verifica se frequencia do ultimo nome eh baixa
            // fazer a volta tambem
            System.out.println(nome1_fragmentado[tamanho_nome1-1]);
            frequencia = count_ultimo_nome.get(nome1_fragmentado[tamanho_nome1 - 1]);
            if (frequencia == null) frequencia = 0;
            System.out.println("freq uli nome pac1: "+frequencia);
            score_de_raridade += (((frequencia_max_ult_nome/2) - frequencia)/(frequencia_max_ult_nome/2));
            System.out.println("prim+meio+ult nome pac1: "+score_de_raridade);
        }

        // essa eh a volta da freq do primeiro nome (paciente 2)
        System.out.println("tamanho nome2: "+tamanho_nome2);
        frequencia = count_primeiro_nome.get(nome2_fragmentado[0]);
        if (frequencia == null) frequencia = 0;
        System.out.println("freq primeiro nome pac2: "+frequencia);
        score_de_raridade += (((frequencia_max_prim_nome/2) - frequencia)/(frequencia_max_prim_nome/2));
        System.out.println("score prim nome pac1+pac2: "+score_de_raridade);

        if (tamanho_nome2 > 1){
            // verifica se frequencia dos nomes do meio sao baixas
            // fazendo a volta
            for (int i = 1; i < tamanho_nome2 - 1; i++){
                frequencia = count_nome_do_meio.get(nome2_fragmentado[i]);
                if (frequencia == null) frequencia = 0;
                System.out.println("freq meio nome pac2: "+frequencia);
                score_de_raridade += (((frequencia_max_meio_nome/2) - frequencia)/(frequencia_max_meio_nome/2));
                System.out.println("score prim+meio nome pac1+pac2: "+score_de_raridade);
            }
            // verifica se frequencia do ultimo nome eh baixa
            // fazendo a volta
            frequencia = count_ultimo_nome.get(nome2_fragmentado[tamanho_nome2 - 1]);
            if (frequencia == null) frequencia = 0;
            System.out.println("freq ult nome pac2: "+frequencia);
            score_de_raridade += (((frequencia_max_ult_nome/2) - frequencia)/(frequencia_max_ult_nome/2));
            System.out.println("score prim+meio+ult nome pac1+pac2: "+score_de_raridade);
        }
        nota += (score_de_raridade/(tamanho_nome1+tamanho_nome2));
        pontos[contador_criterios] = df.format(score_de_raridade/(tamanho_nome1+tamanho_nome2));
        contador_criterios++;

        //Criterio 5/12: Quantidade de fragmentos comuns no nome.
        double fragmentos_comuns = 0;

        // verifica se frequencia do primeiro nome eh alta (paciente 1)
        frequencia = count_primeiro_nome.get(nome1_fragmentado[0]);
        if (frequencia != null && frequencia > 1000){
            fragmentos_comuns++;
        }
        // verifica se frequencia do primeiro nome eh alta (paciente 2)
        frequencia = count_primeiro_nome.get(nome2_fragmentado[0]);
        if (frequencia != null && frequencia > 1000){
            fragmentos_comuns++;
        }
        if (tamanho_nome1 > 1){
            // verifica se frequencia dos nomes do meio sao altas
            for (int i = 1; i < nome1_fragmentado.length - 1; i++){
                frequencia = count_nome_do_meio.get(nome1_fragmentado[i]);
                if (frequencia != null && frequencia > 1000){
                    fragmentos_comuns++;
                }
            }
            // verifica se frequencia do ultimo nome eh alta
            frequencia = count_ultimo_nome.get(nome1_fragmentado[tamanho_nome1 - 1]);
            if ( frequencia != null && frequencia > 1000){
                fragmentos_comuns++;
            }
        }
        if (tamanho_nome2 > 1){
            // verifica se frequencia dos nomes do meio sao altas
            for (int i = 1; i < nome2_fragmentado.length - 1; i++){
                frequencia = count_nome_do_meio.get(nome2_fragmentado[i]);
                if (frequencia != null && frequencia > 1000){
                    fragmentos_comuns++;
                }
            }
            // verifica se frequencia do ultimo nome eh alta
            frequencia = count_ultimo_nome.get(nome2_fragmentado[tamanho_nome2 - 1]);
            if ( frequencia != null && frequencia > 1000){
                fragmentos_comuns++;
            }
        }
        nota -= (fragmentos_comuns/(tamanho_nome1 + tamanho_nome2));
        pontos[contador_criterios] = df.format(-fragmentos_comuns/(tamanho_nome1 + tamanho_nome2));
        contador_criterios++;

        //Criterio 6/13: Quantidade de fragmentos muito parecidos no nome.
        double fragmentos_parecidos = 0;
        for (String fragmento1 : nome1_fragmentado){

            String soundex_fragmento1 = Utilidade.soundex(fragmento1);

            for (String fragmento2 : nome2_fragmentado){
                int elementos_iguais = 0;
                String soundex_fragmento2 = Utilidade.soundex(fragmento2);

                //TODO: nao precisa comparar os que nao comecam com a mesma letra? e se tiver um "Heraldo" com um "Eraldo"?
                //Compara o soundex dos fragmentos que comecam com a mesma letra.
				/*if (soundex_fragmento1.charAt(0) == soundex_fragmento2.charAt(0)){
					for (int i = 1; i < 4; i++){
						if (soundex_fragmento1.charAt(i) == soundex_fragmento2.charAt(i)){
							elementos_iguais++;
						}
					}

					//Como a primeira letra vai ser igual, so e necessario mais dois elementos iguais.
					if (elementos_iguais >= 2){
						fragmentos_parecidos++;

						break;
					}
				}*/
                //TODO: to fazendo geral sem depender da primeira letra
                for (int i = 0; i < 4; i++){
                    if (soundex_fragmento1.charAt(i) == soundex_fragmento2.charAt(i)){
                        elementos_iguais++;
                    }
                }
                if (elementos_iguais >= 3){
                    fragmentos_parecidos++;

                    break;
                }
            }
        }
        nota += (fragmentos_parecidos/tamanho_nome1)*0.8;
        pontos[contador_criterios] = df.format((fragmentos_parecidos/tamanho_nome1)*0.8);
        contador_criterios++;

        //Criterio 7/14: Quantidade  de fragmentos do nome que esta abreviada na primeira base e completa na segunda.
        //TODO: nao tem que fazer o inverso tbm? abreviado na segunda e completo na primeira
        double abreviacoes_parecidas = 0;
        boolean veri;
        for (String fragmento1 : nome1_fragmentado){
            veri = false;
            //TODO: aqui so considera abreviacao de uma letra, e se tiver abreviacao tipo P. ou Perei ou algo do tipo?
            if (fragmento1.length() == 1){
                for (String fragmento2 : nome2_fragmentado){
                    for (String frag : nome1_fragmentado) {
                        if (fragmento2.equals(frag)) break;
                        else if (fragmento2.startsWith(fragmento1)){
                            abreviacoes_parecidas++;
                            veri = true;
                            System.out.println("fragmento1:" + fragmento1 + ".   fragmento2: " + fragmento2);
                            break;
                        }
                    }

                }
                if (veri) break;
            }
        }
        //TODO: to fazendo a volta
        for (String fragmento2 : nome2_fragmentado){
            veri = false;
            if (fragmento2.length() == 1){
                for (String fragmento1 : nome1_fragmentado){
                    for (String frag : nome2_fragmentado) {
                        if (fragmento1.equals(frag)) break;
                        if (fragmento1.startsWith(fragmento2)){
                            abreviacoes_parecidas++;
                            veri = true;
                            //System.out.println("fragmento1:" + fragmento1 + ".   fragmento2: " + fragmento2);
                            break;
                        }
                    }
                }
                if (veri) break;
            }
        }
        nota += (abreviacoes_parecidas/tamanho_nome1)*0.5;
        pontos[contador_criterios] = df.format((abreviacoes_parecidas/tamanho_nome1)*0.5);
        contador_criterios++;
        return nota;
    }

    //Formato da data vinda do arquivo csv: YYYYMMDD
    private static double comparaDatas(String data1, String data2, double nota){

        //Criterio 15: Datas de nascimento dos pacientes iguais.
        if(data1.equals(data2)){
            nota++;
            pontos[14] = "1,0";
        }

        //Criterio 16: Datas de nascimento dos pacientes com apenas um digito trocado.
        int diferenca = Utilidade.LevenshteinDistance(data1, data2);
        if (diferenca == 1){
            nota++;
            pontos[15] = "1,0";
        }else if(diferenca == 2){
            String dia1 = data1.substring(6, 8);
            String dia2 = data2.substring(6, 8);
            String mes1 = data1.substring(4, 6);
            String mes2 = data2.substring(4, 6);
            String ano1 = data1.substring(0, 4);
            String ano2 = data2.substring(0, 4);

            //Criterio 17: Datas de nascimento dos pacientes com inversao entre os digitos do dia.
            if((Utilidade.LevenshteinDistance(dia1, dia2) == 2) && (dia1.charAt(0) == dia2.charAt(1)) && (dia1.charAt(1) == dia2.charAt(0))){
                nota += 0.8;
                pontos[16] = "0,8";

                //Criterio 18: Datas de nascimento dos pacientes com inversao entre os digitos do mes.
            }else if((Utilidade.LevenshteinDistance(mes1, mes2) == 2) && (mes1.charAt(0) == mes2.charAt(1)) && (mes1.charAt(1) == mes2.charAt(0))){
                nota += 0.8;
                pontos[17] = "0,8";

                //Criterio 19: Datas de nascimento dos pacientes com inversao entre os digitos do ano.
            }else if(Utilidade.LevenshteinDistance(ano1, ano2) == 2){
                int i = 0;
                while(ano1.charAt(i) == ano2.charAt(i)){
                    i++;
                }
                int j = i+1;
                while(ano1.charAt(j) == ano2.charAt(j)){
                    j++;
                }
                if((ano1.charAt(i) == ano2.charAt(j)) && (ano1.charAt(j) == ano2.charAt(i))){
                    nota += 0.8;
                    pontos[18] = "0,8";
                }
            }
        }

        //criterio 20: aplicação da diferença entre 1 e a distancia de Levenshtein por uma constante 0,8
        nota += 1 - (diferenca*0.125);
        pontos[19] =  df.format((1 - (diferenca*0.125)));

        return nota;
    }

    public static String[] juntaString(String[] a, String[] b){
        int tamanho = a.length + b.length;
        String[] completa = new String[tamanho];
        System.arraycopy(a, 0, completa, 0, a.length);
        System.arraycopy(b, 0, completa, a.length, b.length);
        return completa;
    }

}

