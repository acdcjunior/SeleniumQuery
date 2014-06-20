package io.github.seleniumquery.wait.evaluators;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.github.seleniumquery.wait.getters.Getter;

public class MatchesEvaluator implements Evaluator<String> {

	private Getter<?> getter;

	public MatchesEvaluator(Getter<?> getter) {
		this.getter = getter;
	}

	@Override
	public boolean evaluate(WebDriver driver, List<WebElement> elements, String regex) {
		return getter.get(driver, elements).toString().matches(regex);
	}

	@Override
	public String stringFor(String regex) {
		return getter + " matches(\"" + regex + "\")";
	}

}