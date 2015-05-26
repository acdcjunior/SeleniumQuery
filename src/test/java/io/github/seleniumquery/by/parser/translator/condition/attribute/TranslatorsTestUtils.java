package io.github.seleniumquery.by.parser.translator.condition.attribute;

import io.github.seleniumquery.by.csstree.condition.SQCssCondition;
import io.github.seleniumquery.by.csstree.selector.SQCssConditionalSelector;
import io.github.seleniumquery.by.csstree.selector.SQCssSelector;
import io.github.seleniumquery.by.csstree.selector.SQCssTagNameSelector;
import io.github.seleniumquery.by.parser.SQParseTreeBuilder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TranslatorsTestUtils {

    /**
     * Parses {@code selector} and checks if it yields a CSS condition that is an instance of {@code conditionClass}.
     * If so, returns it.
     *
     * @param selector The string selector to be parsed.
     * @param conditionClass The class the condition is expected to be.
     * @param <T> The type of the condition.
     * @return The condition instance, after parse.
     */
    @SuppressWarnings("unchecked")
    public static <T extends SQCssCondition> T parseAndAssertFirstCssCondition(String selector, Class<T> conditionClass) {
        SQCssSelector cssSelector = SQParseTreeBuilder.parse(selector).firstSelector();
        assertThat(cssSelector, instanceOf(SQCssConditionalSelector.class));
        // when
        SQCssSelector sqCssSelector = ((SQCssConditionalSelector) cssSelector).getSqCssSelector();
        SQCssCondition sqCssCondition = ((SQCssConditionalSelector) cssSelector).getSqCssCondition();
        // then
        assertThat(sqCssSelector, instanceOf(SQCssTagNameSelector.class));
        assertThat(sqCssCondition, instanceOf(conditionClass));
        assertThat(((SQCssTagNameSelector) sqCssSelector).getTagName(), is("*"));
        return (T) sqCssCondition;
    }

}