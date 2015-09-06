package io.github.seleniumquery.by2.parser.translator.selector;

import io.github.seleniumquery.by.csstree.selector.SQCssSelector;
import io.github.seleniumquery.by.csstree.selector.SQCssTagNameSelector;
import io.github.seleniumquery.by2.parser.SQParseTreeBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SQCssTagNameSelectorTranslatorTest {

    @Test
    public void translate() {
        // given
        // when
        SQCssSelector cssSelector = SQParseTreeBuilder.parse("tag").firstSelector();
        // then
        assertThat(cssSelector, instanceOf(SQCssTagNameSelector.class));
        assertThat(((SQCssTagNameSelector) cssSelector).getTagName(), is("tag"));
    }

}