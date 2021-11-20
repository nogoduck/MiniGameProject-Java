package application;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {



        MainController mc = null;
        System.out.println("mc >> " + mc);
        mc = new MainController();
        System.out.println("mc new() >> " + mc);




        String[] strArr = {"123", "321", "889"};
        int[] intARr = {1, 2, 3, 4, 5};

        System.out.println("strArr.length >> " + strArr.length);
        System.out.println("intARr.length >> " + intARr.length);

        String tc = "TestClass";
        final String TestCaseTag = "TestClass";
        final String TestCaseTag2 = "TestClass2";
        final String TestCaseTag3 = "TestClass3";

        switch(tc){
            case TestCaseTag3:
                System.out.println("3");
            case TestCaseTag:
                System.out.println("1");
                break;
            case TestCaseTag2:
                System.out.println("2");
        }




        String str = "@@payload:##nickname##angryduck22";
        String str2 = "@@payload:##nickname##angryduck22##true";
        String str3 = "@@payload:##checkNickname##Foxconn";

        if(str3.contains("@@payload:")) {
            String payload[] = str3.replace("@@payload:##", "").split("##");
            System.out.println("payload >> ");
            for(int i = 0; i < payload.length; i++){
                System.out.println(i + ": " + payload[i]);
            }
            System.out.println();
        }

        String phoneNum = "010-8888-2231-00321";
        String removeFirst = phoneNum.replace("010-", "");
        String splitPhoneNum[] = removeFirst.split("-");
        for(int i = 0; i < splitPhoneNum.length; i++){
            System.out.println(i + ": " + splitPhoneNum[i]);
        }
    }
}

