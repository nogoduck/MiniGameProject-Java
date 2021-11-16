package application;

public class Test {
    public static void main(String[] args) {

    String str = "@@payload:##nickname##angryduck22";
    String str2 = "@@payload:##nickname##angryduck22##true";


    if(str2.contains("@@payload:")) {
        String payload[] = str2.replace("@@payload:", "").split("##");

        System.out.printf("payload >> ");
        for(String s:payload){
            System.out.printf("%s ", s);
        }
        System.out.println();

    }






    }

}
