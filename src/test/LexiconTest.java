package test;

import org.junit.jupiter.api.Test;

import com.josephs_projects.apricotLibrary.Lexicon;

class LexiconTest {

	@Test
	void test() {
		Lexicon lex = new Lexicon("C:\\Users\\Joey\\git\\Apricot\\src\\test\\names.txt", 4, 5);
		System.out.println(lex.randName(6, 11));
		System.out.println(lex.randName(6, 11));
		System.out.println(lex.randName(6, 11));
		System.out.println(lex.randName(6, 11));
		System.out.println(lex.randName(6, 11));
	}

}
