package io.github.seleniumquery.by2.parser.translator.condition.attribute;

import io.github.seleniumquery.by.csstree.condition.attribute.SQCssContainsSubstringAttributeCondition;
import org.junit.Test;

import static io.github.seleniumquery.by2.parser.translator.condition.attribute.TranslatorsTestUtils.parseAndAssertFirstCssCondition;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SQCssContainsSubstringAttributeConditionTranslatorTest {

    @Test
    public void translate() {
        // given
        String selector = "[attrib*=stringToContain]";
        // when
        SQCssContainsSubstringAttributeCondition cssCondition = parseAndAssertFirstCssCondition(selector, SQCssContainsSubstringAttributeCondition.class);
        // then
        assertThat(cssCondition.getAttributeName(), is("attrib"));
        assertThat(cssCondition.getWantedValue(), is("stringToContain"));
    }

}