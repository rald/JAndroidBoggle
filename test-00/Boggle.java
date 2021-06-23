import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;



class Boggle {

	static String dice[] = {
		"AAEEGN",
		"ABBJOO",
		"ACHOPS",
		"AFFKPS",
		"AOOTTW",
		"CIMOTU",
		"DEILRX",
		"DELRVY",
		"DISTTY",
		"EEGHNW",
		"EEINSU",
		"EHRTVW",
		"EIOSST",
		"ELRTTY",
		"HIMNUQ",
		"HLNNRZ"
	};

	static Random random = new Random();

	static Trie dict;

	static int BOARD_WIDTH=4;
	static int BOARD_HEIGHT=4;

	static char[] board;

	static boolean[] graph;

	static ArrayList<String> words;

	static Comparator<String> myComparator=new Comparator<String>() {
	    @Override
	    public int compare(String a, String b) {
	        if(a.length()<b.length()) return -1;
	        else if(a.length()>b.length()) return 1;
	        else if(a.length()==b.length()) return a.compareTo(b);
	        return 0;
	    }
	};

	public static void main(String[] args) {

		dict=new Trie();

		try {
			BufferedReader br = new BufferedReader(new FileReader("NewWordList.txt"));
			String line="";
			while((line=br.readLine())!=null) {
				dict.addWord(line.trim());
			}
			br.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		graph=new boolean[BOARD_WIDTH*BOARD_HEIGHT];

		int k=0;
		for(int j=0;j<BOARD_HEIGHT;j++) {
			for(int i=0;i<BOARD_WIDTH;i++) {
				graph[k]=false;
				k++;
			}
		}

		shuffleDice();

		board=new char[BOARD_WIDTH*BOARD_HEIGHT];

		k=0;
		for(int j=0;j<BOARD_HEIGHT;j++) {
			for(int i=0;i<BOARD_WIDTH;i++) {
				board[k]=dice[k].charAt(random.nextInt(dice[k].length()));
				k++;
			}
		}

		k=0;
		for(int j=0;j<BOARD_HEIGHT;j++) {
			for(int i=0;i<BOARD_WIDTH;i++) {
				System.out.print(Character.toUpperCase(board[k])+" ");
				k++;
			}
			System.out.println();
		}

		words=new ArrayList<String>();

		for(int j=0;j<BOARD_HEIGHT;j++) {
			for(int i=0;i<BOARD_WIDTH;i++) {
				dfs(i,j,"");
			}
		}

		words.sort(myComparator);

		for(int i=0;i<words.size();i++) {
			if(i!=0) System.out.print(", ");
			System.out.print(words.get(i));
		}
		System.out.println();


	}

	static void shuffleDice() {
		for(int i=dice.length-1;i>0;i--) {
			int j=random.nextInt(i+1);
			String t=dice[i];
			dice[i]=dice[j];
			dice[j]=t;
		}
	}

	static void dfs(int x,int y,String w) {

		if(x<0 || x>=BOARD_WIDTH || y<0 || y>=BOARD_HEIGHT) return;

		if(graph[x+y*BOARD_WIDTH]) return;

		graph[x+y*BOARD_WIDTH]=true;

		w+=Character.toString(board[x+y*BOARD_WIDTH]);

		if(dict.findWord(w)) {
			boolean found=false;
			for(int i=0;i<words.size();i++) {
				if(w.toLowerCase().equals(words.get(i).toLowerCase())) {
					found=true;
					break;
				}
			}
			if(!found) {
				words.add(w.toLowerCase());
			}
		}

		for(int j=-1;j<=1;j++) {
			for(int i=-1;i<=1;i++) {
				if(j!=0 || i!=0) dfs(x+i,y+j,w);
			}
		}

		graph[x+y*BOARD_WIDTH]=false;
	}

}


