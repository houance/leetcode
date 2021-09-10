import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        String number = "35525";
        int k = 3;
        ArrayList<Integer> result = new ArrayList<>();

        backTracking(number, k-1, 0, result);

        System.out.println(Collections.min(result));
    }

    public static void backTracking(String number, int k, int startIndex,
                                    ArrayList<Integer> result){

        if (k==0){
            int sum=0;
            String[] segments = number.split("\\.");
            for (String segment : segments) {
                ArrayList<Integer> integers = new ArrayList<>();
                for (String s : segment.split("")) {
                    integers.add(Integer.parseInt(s));
                }
                sum+=balance(integers);
            }
            result.add(sum);
            return;
        }

        for (int i = startIndex; i < number.length(); i++) {
            number = number.substring(0, i+1) + "." + number.substring(i+1);
            k--;
            backTracking(number, k, i+2, result);
            k++;
            number = number.substring(0, i + 1) + number.substring(i + 2);
        }
    }


    public static int balance(ArrayList<Integer> segment){

        if (segment.size()==1) return 0;

        return Collections.max(segment) - Collections.min(segment);
    }


}
