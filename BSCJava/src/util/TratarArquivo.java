package util;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import canvas.BMC;
import canvas.PostIt;

/** Realiza todo o tratamento com arquivos do Projeto
 * @author Fernando Anselmo
 * @version 1.0
 */
public class TratarArquivo {
    
    // Referência
    private BMC principal;
    
    /** Construtor inicial
     * @param principal Janela Principal
     */
    public TratarArquivo(BMC principal) {
        this.principal = principal;
    }
    
    /** Abre o arquivo do Projeto
     * @return Lógico informando o sucesso ou fracasso da operação
     */
    public boolean abrirArquivo() {
        boolean ret = false;
        FileDialog dig = new FileDialog(principal, "Abrir Arquivo", FileDialog.LOAD);
        dig.setDirectory("");
        dig.setFile("nome.bmc");
        dig.setVisible(true);
        String nomArq = dig.getDirectory() + dig.getFile();
        try {
            if (new File(nomArq).exists()) {
                BufferedReader arqEntrada = new BufferedReader(
                new FileReader(nomArq));
                String linMnt = "";
                if ((linMnt = arqEntrada.readLine()) != null) {
                    separarDados(linMnt);
                    while ((linMnt = arqEntrada.readLine()) != null)
                        separarPostIt(linMnt);
                }
                arqEntrada.close();
            }
            ret = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }
    
    /** Salva o arquivo do Projeto
     * @return Lógico informando o sucesso ou fracasso da operação
     */
    public boolean salvarArquivo() {
        boolean ret = false;
        FileDialog dig = new FileDialog(principal, "Salvar Arquivo", FileDialog.SAVE);
        dig.setDirectory("");
        dig.setFile("nome.bmc");
        dig.setVisible(true);
        String nomArq = dig.getDirectory() + dig.getFile();
        try {
            PrintWriter arqSaida = new PrintWriter(
            new FileWriter(nomArq));
            TextArea saida = gerarSaida();
            arqSaida.print(saida.getText());
            arqSaida.close();
            ret = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }
    
    private void separarDados(String lin) {
        StringTokenizer strTok = new StringTokenizer(lin,"|");
        if (strTok.countTokens() == 4) {
            principal.setObjetivo(strTok.nextToken());
            principal.setAutor(strTok.nextToken());
            principal.setData(strTok.nextToken());
            principal.setNumero(strTok.nextToken());
        }
    }
    
    private void separarPostIt(String lin) {
        StringTokenizer strTok = new StringTokenizer(lin,"|");
        if (strTok.countTokens() == 3) {
            String nome = strTok.nextToken();
            int posX = Integer.parseInt(strTok.nextToken());
            int posY = Integer.parseInt(strTok.nextToken());
            // Criar o objeto na Janela
            System.out.println(nome + " " + posX + " " + posY);
            principal.criarPostIt(nome, posX, posY);
        }
    }
    
    private TextArea gerarSaida() {
        TextArea ret = new TextArea();
        ret.append(
        		principal.getObjetivo() + "|" +
        		principal.getAutor() + "|" +
        		principal.getData() + "|" +
        		principal.getNumero() + '\n');
        Component [] cmps = principal.getContentPane().getComponents();
        for (Component cmp: cmps) {
        	if (cmp instanceof PostIt) {
        		ret.append(cmp.getName() + '|' +
        			cmp.getX() + '|' + 
        			cmp.getY() + '\n');
        	}
        }
        return ret;
    }    
}