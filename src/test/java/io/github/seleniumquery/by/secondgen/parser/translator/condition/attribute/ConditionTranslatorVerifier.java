/*
 * Copyright (c) 2015 seleniumQuery authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.seleniumquery.by.secondgen.parser.translator.condition.attribute;

import io.github.seleniumquery.by.secondgen.csstree.condition.SQCssConditionImplementedFinders;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("deprecation")
abstract class ConditionTranslatorVerifier {

    protected String prefix;

    public ConditionTranslatorVerifier(String prefix) {
        this.prefix = prefix;
    }

    public abstract SQCssConditionImplementedFinders verifyTranslation(String actualSelector, String expectedId);

    public void verifyTranslationAndReturn(String actualSelector, String expectedId) {
        SQCssConditionImplementedFinders condition = verifyTranslation(actualSelector, expectedId);
        String cssStringGeneratedByCondition = TranslatorsTestUtils.getCssStringGeneratedByCondition(condition);
        assertThat(cssStringGeneratedByCondition, is(prefix + actualSelector));
    }

    public void verify() {
        verifyTranslationOfRegularIdentifiers();
        verifyTranslationOfEscapedIdentifiers();
    }

    private void verifyTranslationOfRegularIdentifiers() {
        verifyTranslationAndReturn("abc", "abc");
        verifyTranslationAndReturn("a1b2c", "a1b2c");
    }

    private void verifyTranslationOfEscapedIdentifiers() {
        verifyTranslationAndReturn("x\\+y", "x+y");
        verifyTranslation("x\\2b y", "x+y");
        verifyTranslation("x\\00002by", "x+y");
        verifyTranslationAndReturn("\\E9 fg", "éfg");
        verifyTranslationAndReturn("\\\"", "\"");
        verifyTranslationAndReturn("\\\"a\\\"b\\\"c\\\"", "\"a\"b\"c\"");
        verifyTranslation("♥", "♥");
        verifyTranslationAndReturn("\\2665", "♥");
        verifyTranslation("©", "©");
        verifyTranslationAndReturn("\\A9", "©");

//        verifyTranslationAndReturn("“‘’”", "“‘’”");
//        verifyTranslationAndReturn("☺☃", "☺☃");
//        verifyTranslationAndReturn("⌘⌥", "⌘⌥");
//        verifyTranslationAndReturn("𝄞♪♩♫♬", "𝄞♪♩♫♬");
//        verifyTranslationAndReturn("💩", "💩");

        verifyTranslationAndReturn("\\?", "?");
        verifyTranslationAndReturn("\\@", "@");
        verifyTranslationAndReturn("\\.", ".");
        verifyTranslationAndReturn("\\3A\\)", ":)");
        verifyTranslation("\\3A \\)", ":)");
        verifyTranslationAndReturn("\\3A\\`\\(", ":`(");
        verifyTranslation("\\3A \\`\\(", ":`(");
        verifyTranslationAndReturn("\\31 23", "123");
        verifyTranslationAndReturn("\\31 a2b3c", "1a2b3c");
        verifyTranslationAndReturn("\\<p\\>", "<p>");
        verifyTranslationAndReturn("\\<\\>\\<\\<\\<\\>\\>\\<\\>", "<><<<>><>");
        verifyTranslationAndReturn("\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\[\\>\\+\\+\\+\\+\\+\\+\\+\\>\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\>\\+\\+\\+\\>\\+\\<\\<\\<\\<-\\]\\>\\+\\+\\.\\>\\+\\.\\+\\+\\+\\+\\+\\+\\+\\.\\.\\+\\+\\+\\.\\>\\+\\+\\.\\<\\<\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\.\\>\\.\\+\\+\\+\\.------\\.--------\\.\\>\\+\\.\\>\\.", "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.");
        verifyTranslation("\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\[\\>\\+\\+\\+\\+\\+\\+\\+\\>\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\>\\+\\+\\+\\>\\+\\<\\<\\<\\<\\-\\]\\>\\+\\+\\.\\>\\+\\.\\+\\+\\+\\+\\+\\+\\+\\.\\.\\+\\+\\+\\.\\>\\+\\+\\.\\<\\<\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+\\.\\>\\.\\+\\+\\+\\.\\-\\-\\-\\-\\-\\-\\.\\-\\-\\-\\-\\-\\-\\-\\-\\.\\>\\+\\.\\>\\.", "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.");
        verifyTranslationAndReturn("\\#", "#");
        verifyTranslationAndReturn("\\#\\#", "##");
        verifyTranslationAndReturn("\\#\\.\\#\\.\\#", "#.#.#");
        verifyTranslationAndReturn("\\_", "_");
        verifyTranslationAndReturn("\\{\\}", "{}");
        verifyTranslationAndReturn("\\#fake-id", "#fake-id");
        verifyTranslation("\\#fake\\-id", "#fake-id");
        verifyTranslationAndReturn("foo\\.bar", "foo.bar");
        verifyTranslationAndReturn("\\3Ahover", ":hover");
        verifyTranslation("\\3A hover", ":hover");
        verifyTranslationAndReturn("\\3Ahover\\3A focus\\3A active", ":hover:focus:active");
        verifyTranslation("\\3A hover\\3A focus\\3A active", ":hover:focus:active");
        verifyTranslationAndReturn("\\[attr\\=value\\]", "[attr=value]");
        verifyTranslationAndReturn("f\\/o\\/o", "f/o/o");
        verifyTranslationAndReturn("f\\\\o\\\\o", "f\\o\\o");
        verifyTranslationAndReturn("f\\*o\\*o", "f*o*o");
        verifyTranslationAndReturn("f\\!o\\!o", "f!o!o");
        verifyTranslationAndReturn("f\\'o\\'o", "f'o'o");
        verifyTranslationAndReturn("f\\~o\\~o", "f~o~o");
        verifyTranslationAndReturn("f\\+o\\+o", "f+o+o");
    }

}