package principal_cdr;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;


@SuppressWarnings("serial")
public class TelaGUI extends JFrame {

    static private JPanel contentPane;

    private String filepath;

    @SuppressWarnings("rawtypes")
    private JComboBox comboBox;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBox_1;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBox_2;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBox_3;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBox_4;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBox_5;
    private JTextField txtEntrada;
    private JTextField txtSaida;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaGUI().setVisible(true);;


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TelaGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 682, 305);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNome = new JLabel("Nome 1");
        lblNome.setBounds(10, 40, 130, 14);
        contentPane.add(lblNome);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        comboBox.setSelectedIndex(9);
        comboBox.setBounds(10, 55, 40, 20);
        contentPane.add(comboBox);

        JLabel lblNomeDaMe = new JLabel("Nome da Mae 1:");
        lblNomeDaMe.setBounds(10, 96, 130, 14);
        contentPane.add(lblNomeDaMe);

        comboBox_1 = new JComboBox();
        comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        comboBox_1.setSelectedIndex(11);
        comboBox_1.setBounds(10, 111, 40, 20);
        contentPane.add(comboBox_1);

        JLabel lblDataDeNascimento = new JLabel("Data de Nascimento 1:");
        lblDataDeNascimento.setBounds(10, 152, 130, 14);
        contentPane.add(lblDataDeNascimento);

        comboBox_2 = new JComboBox();
        comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        comboBox_2.setSelectedIndex(13);
        comboBox_2.setBounds(10, 167, 40, 20);
        contentPane.add(comboBox_2);

        JLabel lblNome_1 = new JLabel("Nome 2:");
        lblNome_1.setBounds(301, 40, 130, 14);
        contentPane.add(lblNome_1);

        comboBox_3 = new JComboBox();
        comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        comboBox_3.setSelectedIndex(10);
        comboBox_3.setBounds(301, 55, 40, 20);
        contentPane.add(comboBox_3);

        JLabel lblNomeDaMe_1 = new JLabel("Nome da Mae 2:");
        lblNomeDaMe_1.setBounds(301, 96, 130, 14);
        contentPane.add(lblNomeDaMe_1);

        comboBox_4 = new JComboBox();
        comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        comboBox_4.setSelectedIndex(12);
        comboBox_4.setBounds(301, 111, 40, 20);
        contentPane.add(comboBox_4);

        JLabel lblDataDeNascimento_1 = new JLabel("Data de Nascimento 2:");
        lblDataDeNascimento_1.setBounds(301, 152, 130, 14);
        contentPane.add(lblDataDeNascimento_1);

        comboBox_5 = new JComboBox();
        comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        comboBox_5.setSelectedIndex(14);
        comboBox_5.setBounds(301, 167, 40, 20);
        contentPane.add(comboBox_5);

        JButton btnStart = new JButton("Comparar");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        btnStart.setBounds(540, 232, 99, 23);
        contentPane.add(btnStart);


        JLabel liAvisoDeSeparador = new JLabel("Atencao, o separador do arquivo .csv deve ser '|'.");
        liAvisoDeSeparador.setFont(new Font("Tahoma", Font.ITALIC, 13));
        liAvisoDeSeparador.setBounds(10, 20, 503, 14);
        contentPane.add(liAvisoDeSeparador);


        JLabel lblIndiqueAsColunas = new JLabel("Indique as colunas referentes aos seguintes dados no arquivo de entrada:");
        lblIndiqueAsColunas.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblIndiqueAsColunas.setBounds(10, 5, 503, 14);
        contentPane.add(lblIndiqueAsColunas);

        JButton btnProcurar = new JButton("Procurar");
        btnProcurar.setBounds(540, 203, 99, 23);
        btnProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procurarActionPerformed(evt);
            }
        });
        contentPane.add(btnProcurar);

        JLabel lblArquivoDeEntrada = new JLabel("Arquivo de entrada:");
        lblArquivoDeEntrada.setBounds(10, 207, 150, 14);
        contentPane.add(lblArquivoDeEntrada);

        txtEntrada = new JTextField();
        txtEntrada.setBounds(170, 204, 343, 20);
        contentPane.add(txtEntrada);
        txtEntrada.setColumns(10);

        JLabel lblArquivoDeSada = new JLabel("Arquivo de Saida:");
        lblArquivoDeSada.setBounds(10, 236, 150, 14);
        contentPane.add(lblArquivoDeSada);

        txtSaida = new JTextField();
        txtSaida.setBounds(170, 233, 343, 20);
        contentPane.add(txtSaida);
        txtSaida.setColumns(10);
    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt){
        //inicia a parte de processamento do algoritmo
        ComparaRegistros.inicia(filepath,txtSaida.getText() ,comboBox.getSelectedIndex(),comboBox_1.getSelectedIndex(),comboBox_2.getSelectedIndex(),comboBox_3.getSelectedIndex(),comboBox_4.getSelectedIndex(),comboBox_5.getSelectedIndex());

        JOptionPane.showMessageDialog(contentPane, "Comparação Finalizada!\nO separador do arquivo de saida e ponto e virgula");
    }

    private void procurarActionPerformed(java.awt.event.ActionEvent evt){
        //----------------------------- File Chooser -------------------------------
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV files", "csv");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        TelaGUI tela = new TelaGUI() ;
        int returnVal = chooser.showOpenDialog(tela);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Você selecionou o arquivo: " +
                    chooser.getSelectedFile().getName());
        }

        filepath = chooser.getSelectedFile().getPath();
        txtEntrada.setText(chooser.getSelectedFile().getName());
        txtSaida.setText("saida_"+
                chooser.getSelectedFile().getName());
        //--------------------------------------------------------------------------
    }

    public static void emiteMensagemFaltaFrequencias(){
        JOptionPane.showMessageDialog(contentPane, "Algum arquivo de frequencia nao esta na mesma pasta do executavel .jar.\nPor favor, coloque os arquivos na mesma pasta e tente novamente.");
    }

}
