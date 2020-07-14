package org.cleartax;

import org.cleartax.Parse.LogParser;
import org.cleartax.Service.LogParsingService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException{
        if (args.length != 0) {
            readFromFile(args[0]);

        }
    }


    static void readFromFile(String filepath) throws IOException {
        BufferedReader reader;
        LogParsingService logParsingService=new LogParsingService();
        try {
            reader = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            throw new FileNotFoundException();
        }
        String line;
        while ((line = reader.readLine()) != null) {
            LogParser.parse(line,logParsingService);
        }
        logParsingService.printTopFiveFrequentRequest();
        logParsingService.printAllRequest();
    }
}
