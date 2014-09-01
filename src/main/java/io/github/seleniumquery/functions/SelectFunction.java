package io.github.seleniumquery.functions;

import io.github.seleniumquery.SeleniumQueryObject;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectFunction {
	
	public static SeleniumQueryObject selectOptionByVisibleText(SeleniumQueryObject caller, List<WebElement> elements, String text) {
		// TODO filter only select items, so no exceptions are thrown below
		for (WebElement element : elements) {
			new Select(element).selectByVisibleText(text);
		}
		return caller;
	}
	
	public static SeleniumQueryObject selectOptionByValue(SeleniumQueryObject caller, List<WebElement> elements, String value) {
		// TODO filter only select items, so no exceptions are thrown below
		for (WebElement element : elements) {
			new Select(element).selectByValue(value);
		}
		return caller;
	}
	
}