package edu.hogwarts.siesta.boggle;

class Trie {

	static final int LETTER_MAX=26;
	boolean mark;
	Trie[] next=new Trie[LETTER_MAX];

	Trie() {
		mark=false;
		for(int i=0;i<LETTER_MAX;i++) {
			next[i]=null;
		}
	}

	boolean addWord(String word) {

		int i;
		for(i=0;i<word.length();i++) {
			int j=Character.toUpperCase(word.charAt(i))-65;
			if(j<0 || j>=LETTER_MAX) return false;
		}

		i=0;
		Trie curr=this;
		while(i<word.length()) {

			int j=Character.toUpperCase(word.charAt(i))-65;

			if(curr.next[j]==null) {
				curr.next[j]=new Trie();
			}

			curr=curr.next[j];

			i++;
		}

		curr.mark=true;
		return true;
	}

	boolean findWord(String word) {
		int i=0;
		Trie curr=this;
		while(i<word.length()) {
			int j=Character.toUpperCase(word.charAt(i))-65;
			if(curr.next[j]==null) {
				return false;
			}
			curr=curr.next[j];
			i++;
		}
		return curr.mark;
	}


}
