import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



class Filter3To16Letters {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("CleanWordList.txt"));
			FileWriter fw = new FileWriter("NewWordList.txt");
			String newLine = System.getProperty("line.separator");

			String line="";
			while((line=br.readLine())!=null) {
				String word=line.trim().toLowerCase();
				int n=word.length();
				if(n>=3 && n<=16) {
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
