package hu.elte.fi.szofttech.bomberman.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KeyReaderWriter {
    private String keys;
    private int[][] moves;

    public KeyReaderWriter(String keys) throws FileNotFoundException, IOException {
        this.keys = keys;
        moves = new int[3][5];
        fileReader();
    }

    private void fileReader() throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(keys))) {
            String str = "";
            int I = 0;
            while ((str = br.readLine()) != null) {
                String[] tokens = str.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    moves[I][i] = Integer.parseInt(tokens[i]);
                }
                I = I + 1;
            }
        }
    }

    public int[] getMoves(int index) {
        if (index > moves.length && index < 0) {
            return null;
        }
        index = index - 1;
        int[] kc = new int[moves[index].length];
        for (int i = 0; i < kc.length; i++) {
            kc[i] = moves[index][i];
        }
        return kc;
    }

    public void setMoves(int index, int[] keyCodes) throws FileNotFoundException, IOException {
        if (index > moves.length && index < 0) {
            return;
        }
        index = index - 1;
        moves[index] = keyCodes;
    }

    public void save() throws FileNotFoundException, IOException {
        fileWriter();
    }

    private void fileWriter() throws FileNotFoundException, IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(keys))) {
            for (int i = 0; i < moves.length; i++) {
                int[] keyCodes = moves[i];
                String str = "";
                for (int j = 0; j < keyCodes.length; j++) {
                    str += keyCodes[j] + " ";
                }
                str.substring(0, str.length() - 1);
                bw.write(str);
                bw.newLine();
            }
        }
    }
}