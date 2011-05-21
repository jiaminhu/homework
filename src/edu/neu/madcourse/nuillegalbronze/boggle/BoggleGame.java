package edu.neu.madcourse.nuillegalbronze.boggle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class BoggleGame {
	
	private static Trie trie;
	private static BoggleGame game;
	private char[] letters;
	private Random rand;
	private int score;
	private Map<String,Boolean> possibleWords;
	
	public enum TestResult{
		NOT_EXISTS,			/* word not in dictionary */
		HIT,				/* hit a valid entry in dictionary */
		ALREADY_TRIED		/* word already been scored */
	}
	
	/*
	 * get an instance of BoggleGame
	 * @return instance of BoggleGame
	 */
	public static BoggleGame GetInstance(){
		if(null==game){
			game=new BoggleGame();
		}
		return game;
	}
	
	/*
	 * Start a new boggle game
	 */
	public char[] NewGame(){
		score = 0;
		
		for(int i=0;i<16;i++){
			
			int num=rand.nextInt(26);
			letters[i]=(char) (num+65);
			

//			System.out.print(letters[i]);
//			System.out.print(" ");
		}
		for(int n=0;n<16;n++){
			System.out.print(letters[n]+" ");
		}
		System.out.println();
		possibleWords.clear();
		computePossibleWords();		
		return letters;
	}
	
	/*
	 * get standard solution and user's answer history in this round
	 * @return answer history and the standard solution
	 */
	public Map<String,Boolean> GetAnswerHistory(){
		return possibleWords;
	}
	
	/*
	 * Test whether word is in dictionary and not being tried
	 * at the mean time also computing the score
	 * @word the word to be test
	 * @return TestResult
	 */
	public TestResult Test(String word){
		if(possibleWords.containsKey(word)){
			if(possibleWords.get(word)){
				return TestResult.ALREADY_TRIED;
			}else{
				possibleWords.put(word, true);
				score += (word.length()-2) * 2;
				return TestResult.HIT;
			}
		}
		return TestResult.NOT_EXISTS;
	}
	
	/*
	 * get user's score
	 * return score
	 */
	public int GetScore(){
		return score;
	}
	
	/*
	 * Constructor of BoggleGame
	 * invisible from out of this class
	 * reads words from wordlist and construct a trie
	 */
	private BoggleGame(){
		trie = Trie.fromFile("wordlist.txt");
		letters = new char[16];
		rand = new Random();
		possibleWords = new HashMap<String, Boolean>();
	}
	
	private void computePossibleWords(){		
		boolean[] selected=new boolean[16];
		
		for(int i=0;i<16;i++){
			selected[i]=false;
		}
		
		int[] neighbors=new int[8];	
		
		for(int x=0;x<16;x++){
			selected[x] = true;
			String prefix = "";
			prefix += letters[x];
			
			selected[x]=true;
			neighbors[0]=x-5;
			neighbors[1]=x-4;
			neighbors[2]=x-3;
			neighbors[3]=x-1;
			neighbors[4]=x+1;
			neighbors[5]=x+3;
			neighbors[6]=x+4;
			neighbors[7]=x+5;
			
			for(int z=0;z<8;z++){
				int m=neighbors[z];
				//if valid neighbor
				
				if(m>=0&&m<16){
					//if not been selected
					if(!selected[m]){
						
						selected[m]=true;
						computePossibleWordsHelper(
								m,
								selected,
								prefix + letters[m]);

						selected[m]=false;
					}
					else{
						continue;
					}
				}

			}
			selected[x]=false;
		}
	}

	private void computePossibleWordsHelper(int x,boolean[] selected,String prefix){
		int[] neighbors=new int[8];
		//all possible neighbors
		neighbors[0]=x-5;
		neighbors[1]=x-4;
		neighbors[2]=x-3;
		neighbors[3]=x-1;
		neighbors[4]=x+1;
		neighbors[5]=x+3;
		neighbors[6]=x+4;
		neighbors[7]=x+5;

		for(int z=0;z<8;z++){
			int m=neighbors[z];
			//if valid neighbor
			if(m>=0&&m<16){
				//if not been selected
				if(!selected[m]){
					selected[m]=true;

					if(trie.contains(prefix+letters[m])){
						possibleWords.put(
								prefix+letters[m],
								false);
						
						if(trie.containsPrefix(prefix+letters[m])){
							computePossibleWordsHelper(
									m,
									selected,
									prefix+letters[m]);
						}
						
						selected[m]=false;
					}else{
						
						if(trie.containsPrefix(prefix+letters[m])){
							computePossibleWordsHelper(
									m,
									selected,
									prefix+letters[m]);
						}
						
						selected[m]=false;
						continue;
					}
				}
				
				selected[m]=false;
				continue;				
			}
		}		
	}
}
