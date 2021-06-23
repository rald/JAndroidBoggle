import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class FilterBadWords {
	public static void main(String[] args) {

		ArrayList<String> badWords=new ArrayList<String>();

		try {
			BufferedReader br = new BufferedReader(new FileReader("BadWordList.txt"));
			String line="";
			while((line=br.readLine())!=null) {
				String word=line.trim().toLowerCase();
				if(!word.isEmpty()) {
					badWords.add((String)word);
				}
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader("WordList.txt"));
			FileWriter fw = new FileWriter("CleanWordList.txt");
			String newLine = System.getProperty("line.separator");

			String line="";
			while((line=br.readLine())!=null) {
				String word=line.trim().toLowerCase();
				boolean found=false;
				for(int i=0;i<badWords.size();i++) {
					if(word.indexOf((String)badWords.get(i))!=-1) {
						found=true;
						break;
					}
				}
				if(!found) {
					fw.write(word+newLine);
				}
			}
			fw.close();
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
