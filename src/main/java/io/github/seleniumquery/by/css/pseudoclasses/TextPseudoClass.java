package io.github.seleniumquery.by.css.pseudoclasses;

import io.github.seleniumquery.by.xpath.XPathExpression;
import io.github.seleniumquery.by.xpath.XPathExpressionFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * :text
 * http://api.jquery.com/text-selector/
 * 
 * @author acdcjunior
 *
 * @since 1.0.0
 */
class TextPseudoClass implements PseudoClass {
	
	private static final String TEXT_PSEUDO_CLASS_NO_COLON = "text";
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return TEXT_PSEUDO_CLASS_NO_COLON.equalsIgnoreCase(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		return "input".equals(element.getTagName()) &&
				(element.getAttribute("type") == null || "text".equalsIgnoreCase(element.getAttribute("type")));
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathExpressionFactory.createNoFilterSelector("[self::input and (translate(@type,'TEXT','text') = 'text' or not(@type))]");
	}
	
}