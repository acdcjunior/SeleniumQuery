package io.github.seleniumquery.selectors.pseudoclasses;

import io.github.seleniumquery.selector.DriverSupportService;
import io.github.seleniumquery.selector.xpath.XPathExpression;
import io.github.seleniumquery.selector.xpath.XPathExpressionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * :empty
 *
 * @author acdcjunior
 * @since 1.0.0
 */
public class EmptyPseudoClass implements PseudoClass {
	
	private static final Log LOGGER = LogFactory.getLog(EmptyPseudoClass.class);
	private static final String EMPTY_PSEUDO_CLASS_NO_COLON = "empty";

    private final ParentPseudoClass parentPseudoClass = new ParentPseudoClass();
	
	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return EMPTY_PSEUDO_CLASS_NO_COLON.equals(pseudoClassValue);
	}
	
	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		boolean isEmpty = !parentPseudoClass.isParent(element);
		// #Cross-Driver
		if (isEmpty && DriverSupportService.isHtmlUnitDriverEmulatingIE(driver)) {
			LOGGER.warn("The outcome of the selector with the pseudo-class \":empty\" could be affected:" +
					" HtmlUnidDriver emulating IE considers elements " +
					" with space-only content (e.g. \"<div> </div>\") to be empty, while for other browsers" +
					" they are not! There is no workaround for this, as HtmlUnitDriver ignored the spaces during" +
					" the DOM parsing phase, and we have no means to know now if the elements had spaces (that" +
					" were ignored) or if they were just empty.");
		}
		return isEmpty;
	}
	
	@Override
	public XPathExpression pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		return XPathExpressionFactory.createNoFilterSelector("[count(.//*) = 0]");
	}
	
}