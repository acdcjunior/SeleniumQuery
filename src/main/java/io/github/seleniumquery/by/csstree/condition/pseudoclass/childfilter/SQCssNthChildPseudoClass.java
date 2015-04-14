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

package io.github.seleniumquery.by.csstree.condition.pseudoclass.childfilter;

import io.github.seleniumquery.by.css.pseudoclasses.PseudoClassSelector;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.SQCssFunctionalPseudoClassCondition;
import io.github.seleniumquery.by.csstree.condition.pseudoclass.locatorgenerationstrategy.MaybeNativelySupportedPseudoClass;
import io.github.seleniumquery.by.locator.CSSLocator;
import io.github.seleniumquery.by.locator.XPathLocator;
import org.openqa.selenium.InvalidSelectorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * :nth-child()
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssNthChildPseudoClass extends SQCssFunctionalPseudoClassCondition {

    public static final String PSEUDO = "nth-child";

    public MaybeNativelySupportedPseudoClass nthChildPseudoClassLocatorGenerationStrategy = new MaybeNativelySupportedPseudoClass() {
        @Override
        public String pseudoClassForCSSNativeSupportCheck() {
            return ":"+PSEUDO+"(1)";
        }

        @Override
        public CSSLocator toCssWhenNativelySupported() {
            NthChildArgument nthChildArgument = getNthChildArgument();
            return new CSSLocator(nthChildArgument.toCSS());
        }

        @Override
        public XPathLocator toXPath() {
            NthChildArgument nthChildArgument = getNthChildArgument();
            return XPathLocator.pureXPath(nthChildArgument.toXPath());
        }
    };

    public SQCssNthChildPseudoClass(PseudoClassSelector pseudoClassSelector) {
        super(pseudoClassSelector);
    }

    @Override
    public MaybeNativelySupportedPseudoClass getSQCssLocatorGenerationStrategy() {
        return nthChildPseudoClassLocatorGenerationStrategy;
    }

    private NthChildArgument getNthChildArgument() {
        return new NthChildArgument(getArgument());
    }


    private static class NthChildArgument {

        private static final Pattern B_REGEX = Pattern.compile("[+-]?\\d+");
        private static final Pattern ANB_REGEX = Pattern.compile("([+-]?\\d*)n(?:\\s*([+-]\\s*\\d+))?");

        private final Integer a;
        private final Integer b;

        public NthChildArgument(String argument) {
            String trimmedArg = argument.trim();
            if (even(trimmedArg)) {
                this.a = 2;
                this.b = null;
            } else if (odd(trimmedArg)) {
                this.a = 2;
                this.b = 1;
            } else if (bOnly(trimmedArg)) {
                this.a = null;
                this.b = parseSupposedInt(trimmedArg);
            } else if (anb(trimmedArg)) {
                Matcher m = extractArgumentsFromRegexGroups(trimmedArg);
                this.a = a(m.group(1));
                this.b = b(m.group(2));
            } else {
                throw createInvalidArgumentException(argument);
            }
        }

        /**
         * Tests if :nth-child(even)
         */
        private boolean even(String trimmedArg) {
            return "even".equals(trimmedArg);
        }

        /**
         * Tests if :nth-child(odd)
         */
        private boolean odd(String trimmedArg) {
            return "odd".equals(trimmedArg);
        }

        /**
         * Tests if arguments is under format :nth-child(b)
         */
        private boolean bOnly(String trimmedArg) {
            return B_REGEX.matcher(trimmedArg).matches();
        }

        /**
         * Tests if arguments is under format :nth-child(an+b)
         */
        private boolean anb(String trimmedArg) {
            return ANB_REGEX.matcher(trimmedArg).matches();
        }

        private int a(String aString) {
            if (aString.isEmpty()) {
                return 1;
            }
            if ("-".equals(aString)) {
                return -1;
            }
            if ("+".equals(aString)) {
                return 1;
            }
            return parseSupposedInt(aString);
        }

        private Integer b(String bString) {
            if (bString == null) {
                return null;
            }
            return parseSupposedInt(bString);
        }

        private int parseSupposedInt(String supposedInteger) {
            String intWithoutSpaces = supposedInteger.replaceAll("\\s", "");
            String intWithoutSpacesAndLeadingPlusSign = intWithoutSpaces.replaceAll("^\\+", "");
            return Integer.parseInt(intWithoutSpacesAndLeadingPlusSign);
        }

        private Matcher extractArgumentsFromRegexGroups(String trimmedArg) {
            Matcher m = ANB_REGEX.matcher(trimmedArg);
            // we know it matches, otherwise this method would not have been called
            // I will not put an IF here "because another method may call this". If another method
            // ever calls this, then whoever made it call this place an if here!
            //noinspection ResultOfMethodCallIgnored
            m.matches();
            return m;
        }

        private InvalidSelectorException createInvalidArgumentException(String argument) {
            String reason = String.format("The :nth-child() pseudo-class must have an argument like" +
                    " :nth-child(odd), :nth-child(even), :nth-child(an+b), :nth-child(an) or" +
                    " :nth-child(b) - where a and b are positive or negative integers -, but was :nth-child(%s).", argument);
            return new InvalidSelectorException(reason);
        }

        public String toCSS() {
            String sa = a != null ? a+"n" : "";
            String sb = b != null && b != 0 ? (b > 0 && a != null? "+"+b : ""+b) : "";
            return ":nth-child("+sa+sb+")";
        }

        public String toXPath() {
            int realA = a != null ? a : 0;
            if (realA == 0) {
                return "position() = "+b;
            }
            int realB = b != null ? b : 0;
            char operator = realA < 0 ? '<' : '>';
            return "(position() - " + realB + ") mod " + realA + " = 0 and position() "+operator+"= " + realB;
        }
    }

}