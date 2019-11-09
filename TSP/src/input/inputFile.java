package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class inputFile {
    public ArrayList<List> input(String filepath){
        ArrayList<List> result = new ArrayList<>();
        List<Integer> intRes = new ArrayList<>();
        String[] strRes;

        List<String> inputRes = new ArrayList<>();

        try {
            FileReader fr = new FileReader(filepath);
            BufferedReader bf = new BufferedReader(fr);
            String str;

            while ((str = bf.readLine()) != null) {
                inputRes.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        char curChar;
        for(String line: inputRes){
            curChar = line.charAt(0);
            if (((curChar >= 'a' && curChar <= 'z') || (curChar >= 'A' && curChar <= 'Z'))) {
                continue;
            }
            strRes = line.split(" ");
            for (String curStr: strRes){
                if (!curStr.isEmpty()){
                    intRes.add(Integer.parseInt(curStr));
                }
            }
            if (!intRes.isEmpty()){
                result.add(intRes);
                intRes = new ArrayList<>();
            }

        }

        return result;
    }
}
