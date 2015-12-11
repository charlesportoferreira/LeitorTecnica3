/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitordados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debora
 */
public class LeitorDados {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new LeitorDados().leDados("nohup.out", args[0]);
        } catch (IOException ex) {
            Logger.getLogger(LeitorDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leDados(String filePath, String newFile) throws FileNotFoundException, IOException {
        deletaArquivoExistente(newFile);
        String linhaLida;
        String[] dados;
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr)) {
            while (br.ready()) {
                linhaLida = br.readLine();
                if (linhaLida.contains(newFile)) {
                    if(linhaLida.contains("IBk")){//remover esta parte do codigo no futuro!!!!!
                        br.readLine();
                    }
                    linhaLida = linhaLida.replaceAll("[a-zA-Z:]", "");
                    linhaLida = linhaLida.replaceAll("[\t\\s]", "-");
                    linhaLida = linhaLida.replaceAll("\\.", ",");
                    System.out.println(linhaLida);
                    sb.append(linhaLida).append("\n");
                }
            }
            imprimeArquivo(newFile + ".csv", sb.toString());
            br.close();
            fr.close();
        }
    }

    private void deletaArquivoExistente(String newFile) {
        File arquivo = new File(newFile + ".csv");
        if(arquivo.exists()){
            arquivo.delete();
        }
    }

    private void imprimeArquivo(String fileName, String texto) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(texto);
            bw.close();
            fw.close();
        }
    }
}
