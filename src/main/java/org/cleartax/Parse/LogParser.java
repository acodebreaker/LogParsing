package org.cleartax.Parse;

import org.cleartax.Model.Input;
import org.cleartax.Service.LogParsingService;

public class LogParser {

    private static final String TIMESTAMP="timestamp";



    public static void parse(String inputString,LogParsingService logParsingService) {

        String [] inputs= inputString.split(",");
        if(inputs==null || inputString.length()==0)
            return;
        if(inputs[0].equals(TIMESTAMP))
            return;

        Input input=new Input(Long.parseLong(inputs[0]), inputs[1],inputs[2] ,Integer.parseInt(inputs[4]),Integer.parseInt(inputs[3]) );
        logParsingService.processInput(input);



    }


}
