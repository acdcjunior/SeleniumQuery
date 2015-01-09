package io.github.seleniumquery.by.xpath.component;

import io.github.seleniumquery.by.filter.ElementFilter;
import io.github.seleniumquery.by.filter.ElementFilterList;
import io.github.seleniumquery.by.xpath.CssCombinationType;
import io.github.seleniumquery.by.xpath.component.special.IdConditionComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TagComponent extends XPathComponent {

    private static final boolean ID_OPTIMIZATION = true;

    public TagComponent(String xPathExpression) {
        super(xPathExpression, ComponentUtils.toElementFilterList(ElementFilter.FILTER_NOTHING));
    }

    TagComponent(String xPathExpression, List<XPathComponent> combinatedComponents, ElementFilterList elementFilterList) {
        super(xPathExpression, combinatedComponents, elementFilterList);
    }

    public List<WebElement> findWebElements(SearchContext context) {
        if (canUseById()) {
            return findElementsById(context);
        }
        List<WebElement> elements = findElementsByXPath(context);

        return elementFilterList.filter(context, elements);
    }

    private List<WebElement> findElementsById(SearchContext context) {
        return ((IdConditionComponent) this.combinatedComponents.get(0)).findWebElements(context);
    }

    private List<WebElement> findElementsByXPath(SearchContext context) {
        String finalXPathExpression = this.toXPath();
        return new By.ByXPath(finalXPathExpression).findElements(context);
    }

    private boolean canUseById() {
        //noinspection PointlessBooleanExpression,ConstantConditions
        return ID_OPTIMIZATION
                && this.xPathExpression.equals("*")
                && this.combinatedComponents.size() == 1
                && this.combinatedComponents.get(0) instanceof IdConditionComponent;
    }

    public String toXPath() {
        String xPathExpression;
        if ("*".equals(this.xPathExpression)) {
            xPathExpression = ".//*";
        } else {
            xPathExpression = ".//*[self::" + this.xPathExpression+"]";
        }
        ElementFilterList elementFilterList = this.elementFilterList; // should be a copy

        for (XPathComponent other : combinatedComponents) {
            elementFilterList = other.mergeIntoFilter(elementFilterList);
            xPathExpression = other.mergeIntoExpression(xPathExpression);
        }
        return "(" + xPathExpression + ")";
    }

    public String toXPathCondition() {
        String xPathExpression;
        if ("*".equals(this.xPathExpression)) {
            xPathExpression = CssCombinationType.MATCH_EVERYTHING_XPATH_CONDITIONAL;
        } else {
            xPathExpression = "local-name() = '"+this.xPathExpression+"'";
        }
        ElementFilterList elementFilterList = this.elementFilterList; // should be a copy

        for (XPathComponent other : combinatedComponents) {
            elementFilterList = other.mergeFilterAsCondition(elementFilterList);
            xPathExpression = other.mergeExpressionAsCondition(xPathExpression);
        }
        return xPathExpression;
    }

    @Override
    public String mergeIntoExpression(String sourceXPathExpression) {
        return CssCombinationType.TAG.merge(sourceXPathExpression, this.xPathExpression);
    }

    @Override
    public String mergeExpressionAsCondition(String sourceXPathExpression) {
        return CssCombinationType.TAG.mergeAsCondition(sourceXPathExpression, this.xPathExpression);
    }

}