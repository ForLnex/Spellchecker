import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import dataStructures.HLDictionary;

public class SpellCheck{
	static ErrorGenerator	eg;
	static HLDictionary		dictionary;
	static Spellchecker		sc;
	static Scanner				input;
	static int					corrections, failures;

	static String				failuresList	= "";

	public static void main(String[] args){
		String[] command;
		String word;
		File dictionaryFile;
		JFileChooser chooser;
		GeneralFileFilter filter;

		chooser = new JFileChooser();
		filter = new GeneralFileFilter();
		filter.addExtension("txt");
		filter.setDescription("text files");
		chooser.setFileFilter(filter);

		input = new Scanner(System.in);
		dictionaryFile = null;
		word = "";

		while (dictionaryFile == null && !word.equalsIgnoreCase("exit"))
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				dictionaryFile = chooser.getSelectedFile();
			else{
				System.out.println("Error: Could not locate a wordlist.");
				System.out.println("If you would like to try again, simply press enter.  If you would like to stop, simply type 'exit'.");
				System.out.print(">");
				word = input.next();
			}

		if (word.equalsIgnoreCase("exit"))
			System.exit(0);

		try{
			dictionary = new HLDictionary(dictionaryFile);
		}
		catch (FileNotFoundException e){
			System.out.println("Error: Could not find the wordlist at " + dictionaryFile.getAbsolutePath());
			dictionary = null;
			System.exit(0);
		}

		sc = new Spellchecker(dictionary);

		System.out
				.println("Enter \"eg N\" to automatically generate N spelling errors.\nIf no N is entered, you will be prompted to continue after each correction.\nYou may use the -v flag after your N value if you wish to see the generated misspellings and their corresponding corrections.\nThis requires that you enter an N value");
		System.out.println("Or press enter and input your word at the \">\" prompt");

		command = input.nextLine().trim().split(" ");
		if (command[0].equals("eg"))
			useGenerator(command);
		else
			while (true){
				System.out.print(">");
				word = input.next();
				System.out.println(sc.spellCheck(word));
			}
	}

	public static void useGenerator(String[] command){
		int numTests = 0;
		boolean verbose = false;

		corrections = failures = 0;
		eg = new ErrorGenerator(dictionary);

		if (command.length >= 2)
			try{
				numTests = Integer.parseInt(command[1]);
			}
			catch (Exception e){
				System.out.println("Invalid N entered!  You will be prompted for each generated error.");
				System.out.print("> (Press enter to continue)");
				input.nextLine();
				numTests = 0;
			}
		if (command.length >= 3)
			verbose = command[2].equalsIgnoreCase("-v");

		if (numTests <= 0)
			while (true){
				genErrorV();
				System.out.print("> (Press enter to continue)");
				input.nextLine();
			}
		else
			genBulkErrors(numTests, verbose);
	}

	public static void genErrorS(){
		String[] word = eg.errorGen();

		if (sc.spellCheck(word[1]).equals("NO SUGGESTION")){
			++failures;
			failuresList += ">" + word[0] + " was changed to " + word[1] + ".\n";
		}else
			++corrections;
	}

	public static void genErrorV(){
		String correction = "";
		String[] word = {};

		word = eg.errorGen();
		correction = sc.spellCheck(word[1]);
		if (correction.equals("NO SUGGESTION")){
			++failures;
			System.out.println(">" + word[0] + " was changed to " + word[1] + ".  " + failures + " failed corrections so far.");
			System.out.println(correction);
		}else{
			++corrections;
			System.out.println(">" + word[1]);
			System.out.println(correction);
			if (corrections % 10 == 0)
				System.out.println(corrections + " corrections generated.");
		}
	}

	public static void genBulkErrors(int numTests, boolean verbose){
		if (verbose)
			for (int i = 0; i < numTests; ++i)
				genErrorV();
		else{
			for (int i = 0; i < numTests; ++i)
				genErrorS();
			System.out.print(failuresList);
		}

		System.out.println("\n" + corrections + " total corrections generated.");
		System.out.println(failures + " total failed corrections.");
	}
}