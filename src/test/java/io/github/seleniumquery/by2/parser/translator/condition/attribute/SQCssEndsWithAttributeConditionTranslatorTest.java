package io.github.seleniumquery.by2.parser.translator.condition.attribute;

import io.github.seleniumquery.by.csstree.condition.attribute.SQCssEndsWithAttributeCondition;
import org.junit.Test;

import static io.github.seleniumquery.by2.parser.translator.condition.attribute.TranslatorsTestUtils.parseAndAssertFirstCssCondition;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SQCssEndsWithAttributeConditionTranslatorTest {

    @Test
    public void translate() {
        // given
        String selector = "[attr$=\"end\"]";
        // when
        SQCssEndsWithAttributeCondition cssCondition = parseAndAssertFirstCssCondition(selector, SQCssEndsWithAttributeCondition.class);
        // then
        assertThat(cssCondition.getAttributeName(), is("attr"));
        assertThat(cssCondition.getWantedValue(), is("end"));
    }

}