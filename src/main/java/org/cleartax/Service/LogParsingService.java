package org.cleartax.Service;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.cleartax.Model.Input;

import java.util.*;


class Input_Comparator implements Comparator<Input> {
    @Override
    public int compare(Input input1, Input input2)
    {
         if(input1.getFrequency() < input2.getFrequency())
             return 1;
         else if (input1.getFrequency() > input2.getFrequency())
             return -1;

         return 0;
    }
}

public class LogParsingService {


    PriorityQueue<Input> mostFrequentRequests;
    List<Input> inputs;
    Map<Input,ArrayList<Long> > requestTimes;

    public LogParsingService() {
        this.mostFrequentRequests = new PriorityQueue<>(new Input_Comparator());
        this.inputs = new ArrayList<>();
        requestTimes= new HashMap<>();
    }

    public void processInput(Input input) {

        String[] splitted_URL = input.getUrl().split("/");
        String masked_URL = "";
        for (int i = 0; i < splitted_URL.length - 1; i++) {
            if (isNumeric(splitted_URL[i])) {
                masked_URL += "{id}";

            } else
                masked_URL += splitted_URL[i];
            masked_URL += "/";
        }
        if (isNumeric(splitted_URL[splitted_URL.length - 1])) {
            masked_URL += "{id}";

        } else
            masked_URL += splitted_URL[splitted_URL.length - 1];


        input.setUrl(masked_URL);

        if( mostFrequentRequests.contains(input) )
        {
            //System.out.println("inside if");

            Input inputWithFreq=mostFrequentRequests.stream().filter( x -> x.getUrl().equals(input.getUrl() ) && x.getMethod().equals(input.getMethod())  ).findFirst().orElse(null);
            Input updatedInput= new Input( inputWithFreq.getTimestamp(), inputWithFreq.getUrl(),inputWithFreq.getMethod(), inputWithFreq.getHttpStatus(), 0   );
            updatedInput.setFrequency(inputWithFreq.getFrequency() + 1);
            mostFrequentRequests.remove(inputWithFreq);

            mostFrequentRequests.add(updatedInput);
        }
        else
        {
            //System.out.println("inside else");
            input.setFrequency(1);
            mostFrequentRequests.add(input);
        }

        if(!inputs.contains(input))
        {
            inputs.add(input);
        }

        if(requestTimes.get(input)==null) {

            ArrayList<Long> firstTime=new ArrayList<>();
            firstTime.add(input.getResponseTime());
            requestTimes.put(input,firstTime);
        }
        else
            requestTimes.get(input).add(input.getResponseTime());


    }

    public void printAllRequest()
    {
        for(int i=0;i<inputs.size();i++)
        {
            ArrayList<Long> times= requestTimes.get(inputs.get(i));
            Input input=inputs.get(i);
            long maxi=0;
            long mini= Integer.MAX_VALUE;
            int sum=0;

            System.out.println("Method\t" + "URL\t\t\t" + "Min Time\t" + "Max Time\t\t" + "Average Time");
            for(int j=0;j<times.size();j++)
            {
                if( times.get(j)>maxi )
                    maxi = times.get(j);

                if( times.get(j)<mini )
                    mini = times.get(j);

                sum+= times.get(j);


            }
            Double average= (double) sum/times.size();
            System.out.println( input.getMethod() + "\t" + input.getUrl() + "\t\t" + mini + "\t\t" + maxi  + "\t\t\t\t"  + average);
            }


    }

    private boolean isNumeric(String s)
    {
        if(s.length()==0)
            return false;
        for(int i=0;i<s.length();i++)
        {
            if(!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }



    public void printTopFiveFrequentRequest()
    {
        if(mostFrequentRequests.size()< 5 )
        {
            System.out.println("Sorry No 5 frequent requests found");
            return;
        }

        System.out.println("method\t\t\t" + "url\t\t\t\t\t\t" + "frequency"  );
        for(int i=0;i<5;i++)
        {
            Input input= mostFrequentRequests.poll();


            System.out.println( input.getMethod() + "\t\t\t" + input.getUrl() +"\t\t\t\t\t" +input.getFrequency()  );
        }
    }

}
