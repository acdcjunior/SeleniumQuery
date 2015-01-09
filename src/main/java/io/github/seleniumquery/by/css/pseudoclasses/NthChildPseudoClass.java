package io.github.seleniumquery.by.css.pseudoclasses;

import io.github.seleniumquery.by.xpath.component.ConditionSimpleComponent;
import io.github.seleniumquery.by.xpath.component.XPathComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * :nth-child()
 *
 * @author acdcjunior
 *
 * @since 0.9.0
 */
public class NthChildPseudoClass implements PseudoClass<ConditionSimpleComponent> {

	private static final Pattern NTH_CHILD_REGEX = Pattern.compile("([+-]?\\d*)n(?:\\s*([+-]\\s*\\d+))?");

	@Override
	public boolean isApplicable(String pseudoClassValue) {
		return pseudoClassValue.toLowerCase().matches("nth-child\\(.*\\)");
	}

	@Override
	public boolean isPseudoClass(WebDriver driver, WebElement element, PseudoClassSelector pseudoClassSelector) {
		XPathComponent xPathComponent = pseudoClassToXPath(pseudoClassSelector);
		String nthChildExpression = xPathComponent.toSingleXPathExpression();
		String nthChildExpressionRelativeToParent = "../*"+nthChildExpression;
		List<WebElement> elements = element.findElements(By.xpath(nthChildExpressionRelativeToParent));
		return elements.contains(element);
	}

	@Override
	public ConditionSimpleComponent pseudoClassToXPath(PseudoClassSelector pseudoClassSelector) {
		String nthChildContent = pseudoClassSelector.getPseudoClassContent().trim();
		// odd --> 2n+1
		if ("odd".equals(nthChildContent)) {
			return nthChild(2, 1);
		// even --> 2n
		} else if ("even".equals(nthChildContent)) {
			return nthChild(2, 0);
		// b --> 0n+b
		} else if (nthChildContent.matches("[+-]?\\d+")) {
			return nthChild(0, parseInt(nthChildContent));
		// n --> 1n --> everyone
		} else if (nthChildContent.matches("n")) {
			return new ConditionSimpleComponent("[true()]");
			// 0n --> nobody
		} else if (nthChildContent.matches("[+-]?[0]?n")) {
			return new ConditionSimpleComponent("[false()]");
		}
		// an+b --> general case
		Matcher m = NTH_CHILD_REGEX.matcher(nthChildContent);
		if (m.find()) {
			String aString = m.group(1);
			String bString = m.group(2);
			int a = aString.matches("[+-]") ? parseInt(aString + "1") : parseInt(aString);
			int b = (null == bString) ? 0 : parseInt(bString.replaceAll("\\s*", ""));
			return nthChild(a, b);
		}
		throw new IllegalArgumentException("The :nth-child() pseudo-class must have a content like :nth-child(odd), " +
				":nth-child(even), :nth-child(an+b), :nth-child(an) or :nth-child(b), where a and b are integers.");
	}

	public static ConditionSimpleComponent nthChild(int a, int b) {
		// a == 0: 0n+b 0n-b
		if (a == 0) {
			return new ConditionSimpleComponent("[position() = " + b + "]");
		}
		// a < 0: -an+b -an-b
		if (a < 0) {
			return new ConditionSimpleComponent("[(position() - " + b + ") mod " + a + " = 0 and position() <= " + b + "]");
		}
		// a > 0: an+b an-b
		return new ConditionSimpleComponent("[(position() - " + b + ") mod " + a + " = 0 and position() >= " + b + "]");
	}

}