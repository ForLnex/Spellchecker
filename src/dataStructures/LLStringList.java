package dataStructures;

import errorMatchers.VowMatcher;

public class LLStringList{

	private final LLStringNode	firstNode;
	public int						elements;
	public String					name;
	public LLStringNode			lastNode;

	public LLStringList(String wordIn){
		this.name = wordIn;
		firstNode = null;
	}

	public LLStringList(HLKey keyIn, String wordIn){
		this.elements = 1;
		this.firstNode = new LLStringNode(wordIn);
		this.lastNode = firstNode;
		this.name = keyIn.keyName;
	}

	public boolean contains(String wordIn){
		LLStringNode node = firstNode;
		while (node != null)
			if (wordIn.equals(node.word))
				return true;
			else
				node = node.link;
		return false;
	}

	public int cost(String wordA, String wordB){
		int cost = Math.abs(wordA.length() - wordB.length());
		VowMatcher vowelsA = new VowMatcher(wordA);
		VowMatcher vowelsB = new VowMatcher(wordB);

		cost += Math.abs(vowelsA.vowels - vowelsB.vowels);

		while (vowelsA.find() && vowelsB.find())
			if (vowelsA.match != vowelsB.match)
				++cost;
		return cost;
	}

	public String casedWord(String wordIn){
		LLStringNode node = firstNode;
		while (node != null)
			if (wordIn.equalsIgnoreCase(node.word))
				return node.word;
			else
				node = node.link;
		return null;
	}

	public String closestWord(String wordIn){
		int currentCost, newCost;
		LLStringNode node;
		String temp;

		node = firstNode;
		temp = node.word;
		currentCost = cost(wordIn, node.word);
		node = node.link;

		while (node != null){
			newCost = cost(wordIn, node.word);
			if (currentCost > newCost){
				currentCost = newCost;
				temp = node.word;
			}
			node = node.link;
		}

		return temp;
	}

	public String closestWord(String wordIn, LLStringList linkedListIn){
		String tempA, tempB;

		tempA = this.closestWord(wordIn);
		tempB = linkedListIn.closestWord(wordIn);

		if (cost(wordIn, tempA) < cost(wordIn, tempB))
			return tempA;
		else
			return tempB;
	}

	public String toString(){
		String tempWord = firstNode.word;
		LLStringNode node = firstNode.link;

		while (node != null){
			tempWord += ", " + node.word;
			node = node.link;
		}

		return tempWord;
	}

	public void insert(String wordIn){
		LLStringNode newNode = new LLStringNode(wordIn);
		lastNode.link = newNode;
		lastNode = newNode;
		++elements;
	}
}
