package application.bluemarble;

import javafx.scene.image.Image;

public class GoldCard {
	byte goldCardNum;
	BluemarbleGameController bgc;

	GoldCard(byte no, BluemarbleGameController bgc){
		goldCardNum = no;
		this.bgc = bgc;
	}
	// 황금카드에 도착한경우 작동하는 메소드
	void choiceRandomGoldCard() {
		int n = (int) Math.floor(Math.random() *goldCardNum);	// 카드를 랜덤으로 뽑기 위해숫자
//			Image cardImg = new Image(getClass().getResourceAsStream("/application/texture/bluemarbleGoldCard"+n));

		switch(n+1) {
			case 1:
				System.out.println(("은행에서 100만원 받기"));
				bgc.player[bgc.turnCount].setMoney(bgc.player[bgc.turnCount].money()+1000000);
				bgc.refreshMoney();
				bgc.player[bgc.turnCount].refreshAsset();
				break;
			case 2:
				System.out.println("내가 갖고있는 돈의 10%를 각 플레이어에게 나눠주기");
				long tenPercent = (bgc.player[bgc.turnCount].money()/10);
				for(int i = 1 ; i<=bgc.turnCount ; i++) {
					if(i != bgc.turnCount) {
						bgc.player[i].setMoney(bgc.player[i].money()+tenPercent);
						bgc.player[bgc.turnCount].setMoney(bgc.player[bgc.turnCount].money() - tenPercent);
						bgc.player[i].refreshAsset();
					}
				}
				bgc.refreshMoney();
				break;
			case 4:
				System.out.println("다음 주사위를 굴린 수치에 2배를 이동");
				break;
			case 5:
				System.out.println("다음 주사위를 굴린 수치만큼 뒤로 이동");
				break;
			case 6:
				System.out.println("다음턴을 한정으로 상대땅을 밟으면 통행료 면제");
				break;
		}
	}

}