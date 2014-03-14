package nlp.wordprocessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

abstract class AbstractWordProcessor
        implements WordProcessor {

    private BufferedReader br;
    private BufferedWriter bw;

    public AbstractWordProcessor(File inFile, File outFile) 
            throws IOException {
        this.br = new BufferedReader(new FileReader(inFile));
        this.bw = new BufferedWriter(new FileWriter(outFile));
    }

    public String processLine(String line) {
        String[] parts = line.split("\t");
        String[] tokens = parts[0].split(" ");
        tokens[0] = process(tokens[0]);
        if (tokens.length > 1) {
            int last = tokens.length - 1;
            tokens[last] = process(tokens[last]);
        }
        StringBuilder sb = new StringBuilder(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            sb.append(' ').append(tokens[i]);
        }
        sb.append('\t').append(parts[parts.length - 1]);
        return sb.toString();
    }

    public void writeOutput() 
            throws IOException {
        String line = null;
        while ((line = this.br.readLine()) != null) {
            this.bw.write(processLine(line));
        }
        this.br.close();
        this.bw.close();
    }
}
