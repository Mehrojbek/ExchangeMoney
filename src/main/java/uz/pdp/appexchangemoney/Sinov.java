package uz.pdp.appexchangemoney;

import java.util.*;
import java.util.Date;

public class Sinov {
    public static void main(String[] args) {
        Date date1=new Date();
        Date date2=new Date(System.currentTimeMillis()+1000000);
        System.out.println(date2.after(date1));

        List<Integer> integers1=new ArrayList<>(
                Arrays.asList(
                        1,2,3,4,5,6,7
                )
        );

        List<Integer> integers2 =new ArrayList<>(
                Arrays.asList(
                        8,9,0
                )
        );

        List<Integer> integerList=new ArrayList<>(integers1);
        integerList.addAll(integers2);

        System.out.println(integerList);
    }
}
