package application.bluemarble;

public class Owner {
    String owner;
    int buyBuildNum;



    String owner(){
        return owner;
    }

    int buyBuildNum(){
        return buyBuildNum;
    }

    void setOwner(String owner){
        this.owner = owner;
    }

    void setBuyBuildNum(int buyBuildNum){
        this.buyBuildNum = buyBuildNum;
    }
}
