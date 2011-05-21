package edu.neu.madcourse.nuillegalbronze.boggle;
public class Main {

	/**
	 * @param args
	 */
	public static void print(BoggleGame.TestResult result,String word){
		switch(result){
		case HIT:
			System.out.println(word + " is corrected");
			break;
		case NOT_EXISTS:
			System.out.println(word + " not exists");
			break;
		case ALREADY_TRIED:
			System.out.println(word + " not exists");
		}
	}
	
	public static void main(String[] args) {
		BoggleGame boggle=BoggleGame.GetInstance();
		
		char[] list=boggle.NewGame();
		for(int i=0;i<list.length;i++){
			System.out.print(list[i]+" ");
		}
		System.out.println();

		print(boggle.Test("AND"),"AND");
		print(boggle.Test("NEED"),"NEED");
		print(boggle.Test("HEY"),"HEY");
		print(boggle.Test("PICO"),"PICO");
		System.out.println(boggle.GetScore());
	}

}
