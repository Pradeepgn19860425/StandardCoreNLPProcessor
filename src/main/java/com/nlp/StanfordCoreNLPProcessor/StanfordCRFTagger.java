package com.nlp.StanfordCoreNLPProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sequences.SeqClassifierFlags;

public class StanfordCRFTagger {

	static List<String> nounPOS = new ArrayList<String>();

	static {

		nounPOS.add("NN");
		nounPOS.add("NNS");
		nounPOS.add("NNP");
		nounPOS.add("NNPS");
	}

	public static void main(String[] args) throws URISyntaxException {

		String propsFile = "/stanfordNER.prop";
		String trainFile = Paths.get(ClassLoader.getSystemResource("Training_Data.txt").toURI()).toString();
		String modelFile = Paths.get(ClassLoader.getSystemResource("stanfordCRF.model").toURI()).toString();
		StanfordCRFTagger stanfordCRFTagger = new StanfordCRFTagger();
		stanfordCRFTagger.trainAndWrite(propsFile, trainFile, modelFile);
		CRFClassifier model = stanfordCRFTagger.getModel(modelFile);
		DataInput dataInput = new DataInput();

		String text = "Hey! My DELL Laptop g780 is not working";
		StanfordCoreNLP stanfordCoreNLP = PipeLine.getPipeline();
		CoreDocument coreDocument = new CoreDocument(text);

		stanfordCoreNLP.annotate(coreDocument);
		List<CoreLabel> coreLabelList = coreDocument.tokens();
		/*
		 * coreLabelList.stream() .forEach(x -> System.out.println(x + " : " +
		 * x.get(CoreAnnotations.PartOfSpeechAnnotation.class)));
		 */coreLabelList.stream().filter(x -> nounPOS.contains(x.get(CoreAnnotations.PartOfSpeechAnnotation.class)))
				.distinct().forEach(x -> stanfordCRFTagger.doTagging(model, x.originalText(), dataInput));
		;
		System.out.println(dataInput);

	}

	private void doTagging(CRFClassifier model, String input, DataInput dataInput) {

		input = input.trim();
		System.out.println(input + "==>" + model.classifyToString(input));
		String[] strArr=model.classifyToString(input).split("/");
		for (String s : strArr)
		{
		if (s.equals("brand")) {
				dataInput.setBrand(input);
			} else if (s.equals("category")) {
				dataInput.setCategory(input);
			}
			else if (s.equals("model")) {
				dataInput.setModel(input);
			}
		}

	}

	private CRFClassifier getModel(String modelFile) {

		return CRFClassifier.getClassifierNoExceptions(modelFile);
	}

	private void trainAndWrite(String propsFile, String trainFile, String modelFile) {
		InputStream input = StanfordCRFTagger.class.getClass().getResourceAsStream(propsFile);
		Properties props = new Properties();
		try {
			props.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(Paths.get(ClassLoader.getSystemResource("Training_Data.txt").toURI()).toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		props.setProperty("serializeTo", modelFile);
		props.setProperty("trainFile", trainFile);
		SeqClassifierFlags classifierFlags = new SeqClassifierFlags(props);
		CRFClassifier<CoreLabel> crf = new CRFClassifier<>(classifierFlags);
		crf.train();
		crf.serializeClassifier(modelFile);

	}

}
