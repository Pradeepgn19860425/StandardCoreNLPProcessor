package com.nlp.StanfordCoreNLPProcessor;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class NLPTester {

	static List<String> nounPOS = new ArrayList<String>();

	static {

		nounPOS.add("NN");
		nounPOS.add("NNS");
		nounPOS.add("NNP");
		nounPOS.add("NNPS");
	}

	public static void main(String[] args) {
		
	}

}
