package acme.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customisations.Customisation;
import acme.features.administrator.customisation.AdministratorCustomisationRepository;

@Service
public class SpamChecker {

	@Autowired
	private AdministratorCustomisationRepository repository;
	
	public boolean isSpamText(final String textToCheck) {
		
		boolean result = false;
		Double numSpWordsInText = 0.;
		final Integer numOfWords = textToCheck.split(" ").length;
		final List<Customisation> customization = (List<Customisation>) this.repository.findAllCustomisations();
		final String spamWords = customization.get(0).getSpamWords();
		final String[] spamWordsArray = spamWords.split(";");
		final List<String> spamWordsList = Arrays.asList(spamWordsArray);
		
		for (final String sw : spamWordsList) {
			
			numSpWordsInText = numSpWordsInText + this.timesAppearSpamWord(textToCheck.toLowerCase(), sw.toLowerCase(), 0.);
			final Double percentage = numSpWordsInText / numOfWords * 100;
			
			if (percentage >= customization.get(0).getThreshold()) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	private double timesAppearSpamWord(final String textToCheck, final String spamWord, Double numSpWord) {
		
		if (textToCheck.contains(spamWord)) {
			final Integer index = textToCheck.indexOf(spamWord) + spamWord.length();
			numSpWord += 1;
			return this.timesAppearSpamWord(textToCheck.substring(index).trim(), spamWord, numSpWord);
		}
		
		return numSpWord;
	}
}
