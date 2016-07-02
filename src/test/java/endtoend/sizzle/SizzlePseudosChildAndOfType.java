/*
 * Copyright (c) 2016 seleniumQuery authors
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

package endtoend.sizzle;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import testinfrastructure.junitrule.SetUpAndTearDownDriver;
import testinfrastructure.junitrule.annotation.JavaScriptEnabledOnly;

public class SizzlePseudosChildAndOfType extends SizzleTest {

    @ClassRule @Rule public static SetUpAndTearDownDriver setUpAndTearDownDriverRule = new SetUpAndTearDownDriver(SizzleTest.class);

    // pseudos: (first|last|only)-(child|of-type)
    @Test
    public void first_last_only__child_ofType() throws Exception {
        t("First Child", "p:first-child", new String[]{"firstp", "sndp"});
        t("First Child (leading id)", "#qunit-fixture p:first-child", new String[]{"firstp", "sndp"});
        t("First Child (leading class)", ".nothiddendiv div:first-child", new String[]{"nothiddendivchild"});
// TODO(issue#30) - pseudos case-insensitive
//        t("First Child (case-insensitive)", "#qunit-fixture p:FIRST-CHILD", new String[]{"firstp", "sndp"});

// TODO(issue#43) - add :last-child
//        t("Last Child", "p:last-child", new String[]{"sap"});
//        t("Last Child (leading id)", "#qunit-fixture a:last-child", new String[]{"simon1", "anchor1", "mark", "yahoo", "anchor2", "simon", "liveLink1", "liveLink2"});

        t("Only Child", "#qunit-fixture a:only-child", new String[]{"simon1", "anchor1", "yahoo", "anchor2", "liveLink1", "liveLink2"});

// TODO(issue#44) - add :first-of-type
//        t("First-of-type", "#qunit-fixture > p:first-of-type", new String[]{"firstp"});

// TODO(issue#45) - add :last-of-type
//        t("Last-of-type", "#qunit-fixture > p:last-of-type", new String[]{"first"});

// TODO(issue#46) - add :only-of-type
//        t("Only-of-type", "#qunit-fixture > :only-of-type", new String[]{"name+value", "firstUL", "empty", "floatTest", "iframe", "table", "last"});
    }

    @Test @JavaScriptEnabledOnly
    public void nth_child() {
        // Verify that the child position isn't being cached improperly
        t("Test before adding second child", "p:nth-child(2)", new String[]{"ap", "en"});
        executeJS("jQuery('p:nth-child(2)').before('<div class=\"secondChildrenTestDiv\"></div>');");
        t("No longer second child", "p:nth-child(2)", new String[]{});
        executeJS("jQuery('.secondChildrenTestDiv').remove()");
        t("Restored second child", "p:nth-child(2)", new String[]{"ap", "en"});
    }

    @Test
    public void nth_child_2() {
        t("Nth-child", "p:nth-child(1)", new String[]{"firstp", "sndp"});
        t("Nth-child (with whitespace)", "p:nth-child( 1 )", new String[]{"firstp", "sndp"});
        t("Nth-child (case-insensitive)", "#form select:first option:NTH-child(3)", new String[]{"option1c"});
        t("Not nth-child", "#qunit-fixture p:not(:nth-child(1))", new String[]{"ap", "en", "sap", "first"});

        t("Nth-child(2)", "#qunit-fixture form#form > *:nth-child(2)", new String[]{"text1"});
        t("Nth-child(2)", "#qunit-fixture form#form > :nth-child(2)", new String[]{"text1"});

        t("Nth-child(-1)", "#form select:first option:nth-child(-1)", new String[]{});
        t("Nth-child(3)", "#form select:first option:nth-child(3)", new String[]{"option1c"});
        t("Nth-child(0n+3)", "#form select:first option:nth-child(0n+3)", new String[]{"option1c"});
        t("Nth-child(1n+0)", "#form select:first option:nth-child(1n+0)", new String[]{"option1a", "option1b", "option1c", "option1d"});
        t("Nth-child(1n)", "#form select:first option:nth-child(1n)", new String[]{"option1a", "option1b", "option1c", "option1d"});
        t("Nth-child(n)", "#form select:first option:nth-child(n)", new String[]{"option1a", "option1b", "option1c", "option1d"});
        t("Nth-child(even)", "#form select:first option:nth-child(even)", new String[]{"option1b", "option1d"});
        t("Nth-child(odd)", "#form select:first option:nth-child(odd)", new String[]{"option1a", "option1c"});
        t("Nth-child(2n)", "#form select:first option:nth-child(2n)", new String[]{"option1b", "option1d"});
        t("Nth-child(2n+1)", "#form select:first option:nth-child(2n+1)", new String[]{"option1a", "option1c"});
        t("Nth-child(2n + 1)", "#form select:first option:nth-child(2n + 1)", new String[]{"option1a", "option1c"});
        t("Nth-child(+2n + 1)", "#form select:first option:nth-child(+2n + 1)", new String[]{"option1a", "option1c"});
        t("Nth-child(3n)", "#form select:first option:nth-child(3n)", new String[]{"option1c"});
        t("Nth-child(3n+1)", "#form select:first option:nth-child(3n+1)", new String[]{"option1a", "option1d"});
        t("Nth-child(3n+2)", "#form select:first option:nth-child(3n+2)", new String[]{"option1b"});
        t("Nth-child(3n+3)", "#form select:first option:nth-child(3n+3)", new String[]{"option1c"});
        t("Nth-child(3n-1)", "#form select:first option:nth-child(3n-1)", new String[]{"option1b"});
        t("Nth-child(3n-2)", "#form select:first option:nth-child(3n-2)", new String[]{"option1a", "option1d"});
        t("Nth-child(3n-3)", "#form select:first option:nth-child(3n-3)", new String[]{"option1c"});
        t("Nth-child(3n+0)", "#form select:first option:nth-child(3n+0)", new String[]{"option1c"});
        t("Nth-child(-1n+3)", "#form select:first option:nth-child(-1n+3)", new String[]{"option1a", "option1b", "option1c"});
        t("Nth-child(-n+3)", "#form select:first option:nth-child(-n+3)", new String[]{"option1a", "option1b", "option1c"});
        t("Nth-child(-1n + 3)", "#form select:first option:nth-child(-1n + 3)", new String[]{"option1a", "option1b", "option1c"});

        // http://jsbin.com/nosikenise/4/edit?html,js,output
        t(":nth-child seleniumQuery additional tests", "#form select:first option:nth-child(-1n+3)", new String[]{"option1a", "option1b", "option1c"});
        t(":nth-child seleniumQuery additional tests", "#form select:first option:nth-child(-2n+3)", new String[]{"option1a", "option1c"});
        t(":nth-child seleniumQuery additional tests", "#form select:first option:nth-child(-3n+3)", new String[]{"option1c"});
        t(":nth-child seleniumQuery additional tests", "#form select:first option:nth-child(-4n+3)", new String[]{"option1c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(even)", new String[]{"option1b", "option1d", "option2b", "option2d", "option3b", "option3d", "option4b", "option4d", "option5b"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(odd)", new String[]{"option1a", "option1c", "option2a", "option2c", "option3a", "option3c", "option3e", "option4a", "option4c", "option4e", "option5a", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(1)", new String[]{"option1a", "option2a", "option3a", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+1)", new String[]{"option1a", "option2a", "option3a", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-1)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(2)", new String[]{"option1b", "option2b", "option3b", "option4b", "option4d", "option5b"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+2)", new String[]{"option1b", "option2b", "option3b", "option4b", "option4d", "option5b"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-2)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(n)", new String[]{"option1a", "option1b", "option1c", "option1d", "option2a", "option2b", "option2c", "option2d", "option3a", "option3b", "option3c", "option3d", "option3e", "option4a", "option4b", "option4c", "option4d", "option4e", "option5a", "option5b", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(0n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+0n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-0n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(1n)", new String[]{"option1a", "option1b", "option1c", "option1d", "option2a", "option2b", "option2c", "option2d", "option3a", "option3b", "option3c", "option3d", "option3e", "option4a", "option4b", "option4c", "option4d", "option4e", "option5a", "option5b", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+1n)", new String[]{"option1a", "option1b", "option1c", "option1d", "option2a", "option2b", "option2c", "option2d", "option3a", "option3b", "option3c", "option3d", "option3e", "option4a", "option4b", "option4c", "option4d", "option4e", "option5a", "option5b", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-1n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(2n)", new String[]{"option1b", "option1d", "option2b", "option2d", "option3b", "option3d", "option4b", "option4d", "option5b"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(+2n)", new String[]{"option1b", "option1d", "option2b", "option2d", "option3b", "option3d", "option4b", "option4d", "option5b"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-2n)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(1n+0)", new String[]{"option1a", "option1b", "option1c", "option1d", "option2a", "option2b", "option2c", "option2d", "option3a", "option3b", "option3c", "option3d", "option3e", "option4a", "option4b", "option4c", "option4d", "option4e", "option5a", "option5b", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(2n+0)", new String[]{"option1b", "option1d", "option2b", "option2d", "option3b", "option3d", "option4b", "option4d", "option5b"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(3n+0)", new String[]{"option1c", "option2c", "option3c", "option4c", "option4e", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(4n+0)", new String[]{"option1d", "option2d", "option3d"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-1n+0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-2n+0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-3n+0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-4n+0)", new String[]{});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(1n+1)", new String[]{"option1a", "option1b", "option1c", "option1d", "option2a", "option2b", "option2c", "option2d", "option3a", "option3b", "option3c", "option3d", "option3e", "option4a", "option4b", "option4c", "option4d", "option4e", "option5a", "option5b", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(2n+1)", new String[]{"option1a", "option1c", "option2a", "option2c", "option3a", "option3c", "option3e", "option4a", "option4c", "option4e", "option5a", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(3n+1)", new String[]{"option1a", "option1d", "option2a", "option2d", "option3a", "option3d", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(4n+1)", new String[]{"option1a", "option2a", "option3a", "option3e", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-1n+1)", new String[]{"option1a", "option2a", "option3a", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-2n+1)", new String[]{"option1a", "option2a", "option3a", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-3n+1)", new String[]{"option1a", "option2a", "option3a", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-4n+1)", new String[]{"option1a", "option2a", "option3a", "option4a", "option5a"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(1n+3)", new String[]{"option1c", "option1d", "option2c", "option2d", "option3c", "option3d", "option3e", "option4c", "option4e", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(2n+3)", new String[]{"option1c", "option2c", "option3c", "option3e", "option4c", "option4e", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(3n+3)", new String[]{"option1c", "option2c", "option3c", "option4c", "option4e", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(4n+3)", new String[]{"option1c", "option2c", "option3c", "option4c", "option4e", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-1n+3)", new String[]{"option1a", "option1b", "option1c", "option2a", "option2b", "option2c", "option3a", "option3b", "option3c", "option4a", "option4b", "option4c", "option4d", "option4e", "option5a", "option5b", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-2n+3)", new String[]{"option1a", "option1c", "option2a", "option2c", "option3a", "option3c", "option4a", "option4c", "option4e", "option5a", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-3n+3)", new String[]{"option1c", "option2c", "option3c", "option4c", "option4e", "option5c"});
        t(":nth-child seleniumQuery additional tests", "#form option:nth-child(-4n+3)", new String[]{"option1c", "option2c", "option3c", "option4c", "option4e", "option5c"});

        //deepEqual(Sizzle(":nth-child(n)", null, null,[document.createElement("a")].concat(q("ap"))), q("ap"), "Seeded nth-child");
    }

    // TODO(issue#47) - add :nth-last-child
    //@Test
    public void nth_last_child() throws Exception {
        // jQuery("#qunit-fixture").append("<form id='nth-last-child-form'/><i/><i/><i/><i/>");
        executeJS("jQuery(\"#qunit-fixture\").append(\"<form id='nth-last-child-form'/><i/><i/><i/><i/>\");");

        t("Nth-last-child", "form:nth-last-child(5)", new String[]{"nth-last-child-form"});
        t("Nth-last-child (with whitespace)", "form:nth-last-child( 5 )", new String[]{"nth-last-child-form"});

        t("Nth-last-child (case-insensitive)", "#form select:first option:NTH-last-child(3)", new String[]{"option1b"});
        t("Not nth-last-child", "#qunit-fixture p:not(:nth-last-child(1))", new String[]{"firstp", "ap", "sndp", "en", "first"});

        t("Nth-last-child(-1)", "#form select:first option:nth-last-child(-1)", new String[]{});
        t("Nth-last-child(3)", "#form select:first :nth-last-child(3)", new String[]{"option1b"});
        t("Nth-last-child(3)", "#form select:first *:nth-last-child(3)", new String[]{"option1b"});
        t("Nth-last-child(3)", "#form select:first option:nth-last-child(3)", new String[]{"option1b"});
        t("Nth-last-child(0n+3)", "#form select:first option:nth-last-child(0n+3)", new String[]{"option1b"});
        t("Nth-last-child(1n+0)", "#form select:first option:nth-last-child(1n+0)", new String[]{"option1a", "option1b", "option1c", "option1d"});
        t("Nth-last-child(1n)", "#form select:first option:nth-last-child(1n)", new String[]{"option1a", "option1b", "option1c", "option1d"});
        t("Nth-last-child(n)", "#form select:first option:nth-last-child(n)", new String[]{"option1a", "option1b", "option1c", "option1d"});
        t("Nth-last-child(even)", "#form select:first option:nth-last-child(even)", new String[]{"option1a", "option1c"});
        t("Nth-last-child(odd)", "#form select:first option:nth-last-child(odd)", new String[]{"option1b", "option1d"});
        t("Nth-last-child(2n)", "#form select:first option:nth-last-child(2n)", new String[]{"option1a", "option1c"});
        t("Nth-last-child(2n+1)", "#form select:first option:nth-last-child(2n+1)", new String[]{"option1b", "option1d"});
        t("Nth-last-child(2n + 1)", "#form select:first option:nth-last-child(2n + 1)", new String[]{"option1b", "option1d"});
        t("Nth-last-child(+2n + 1)", "#form select:first option:nth-last-child(+2n + 1)", new String[]{"option1b", "option1d"});
        t("Nth-last-child(3n)", "#form select:first option:nth-last-child(3n)", new String[]{"option1b"});
        t("Nth-last-child(3n+1)", "#form select:first option:nth-last-child(3n+1)", new String[]{"option1a", "option1d"});
        t("Nth-last-child(3n+2)", "#form select:first option:nth-last-child(3n+2)", new String[]{"option1c"});
        t("Nth-last-child(3n+3)", "#form select:first option:nth-last-child(3n+3)", new String[]{"option1b"});
        t("Nth-last-child(3n-1)", "#form select:first option:nth-last-child(3n-1)", new String[]{"option1c"});
        t("Nth-last-child(3n-2)", "#form select:first option:nth-last-child(3n-2)", new String[]{"option1a", "option1d"});
        t("Nth-last-child(3n-3)", "#form select:first option:nth-last-child(3n-3)", new String[]{"option1b"});
        t("Nth-last-child(3n+0)", "#form select:first option:nth-last-child(3n+0)", new String[]{"option1b"});
        t("Nth-last-child(-1n+3)", "#form select:first option:nth-last-child(-1n+3)", new String[]{"option1b", "option1c", "option1d"});
        t("Nth-last-child(-n+3)", "#form select:first option:nth-last-child(-n+3)", new String[]{"option1b", "option1c", "option1d"});
        t("Nth-last-child(-1n + 3)", "#form select:first option:nth-last-child(-1n + 3)", new String[]{"option1b", "option1c", "option1d"});

        //deepEqual(Sizzle(":nth-last-child(n)", null, null,[document.createElement("a")].concat(q("ap"))), q("ap"), "Seeded nth-last-child");
    }

    // TODO(issue#48) - add :nth-of-type
    //@Test
    public void nth_of_type() throws Exception {
        t("Nth-of-type(-1)", ":nth-of-type(-1)", new String[]{});
        t("Nth-of-type(3)", "#ap :nth-of-type(3)", new String[]{"mark"});
        t("Nth-of-type(n)", "#ap :nth-of-type(n)", new String[]{"google", "groups", "code1", "anchor1", "mark"});
        t("Nth-of-type(0n+3)", "#ap :nth-of-type(0n+3)", new String[]{"mark"});
        t("Nth-of-type(2n)", "#ap :nth-of-type(2n)", new String[]{"groups"});
        t("Nth-of-type(even)", "#ap :nth-of-type(even)", new String[]{"groups"});
        t("Nth-of-type(2n+1)", "#ap :nth-of-type(2n+1)", new String[]{"google", "code1", "anchor1", "mark"});
        t("Nth-of-type(odd)", "#ap :nth-of-type(odd)", new String[]{"google", "code1", "anchor1", "mark"});
        t("Nth-of-type(-n+2)", "#qunit-fixture > :nth-of-type(-n+2)", new String[]{"firstp", "ap", "foo", "nothiddendiv", "name+value", "firstUL", "empty", "form", "floatTest", "iframe", "lengthtest", "table", "last"});
    }

    // TODO(issue#49) - add :nth-last-of-type
    //@Test
    public void nth_last_of_type() throws Exception {
        t("Nth-last-of-type(-1)", ":nth-last-of-type(-1)", new String[]{});
        t("Nth-last-of-type(3)", "#ap :nth-last-of-type(3)", new String[]{"google"});
        t("Nth-last-of-type(n)", "#ap :nth-last-of-type(n)", new String[]{"google", "groups", "code1", "anchor1", "mark"});
        t("Nth-last-of-type(0n+3)", "#ap :nth-last-of-type(0n+3)", new String[]{"google"});
        t("Nth-last-of-type(2n)", "#ap :nth-last-of-type(2n)", new String[]{"groups"});
        t("Nth-last-of-type(even)", "#ap :nth-last-of-type(even)", new String[]{"groups"});
        t("Nth-last-of-type(2n+1)", "#ap :nth-last-of-type(2n+1)", new String[]{"google", "code1", "anchor1", "mark"});
        t("Nth-last-of-type(odd)", "#ap :nth-last-of-type(odd)", new String[]{"google", "code1", "anchor1", "mark"});
        t("Nth-last-of-type(-n+2)", "#qunit-fixture > :nth-last-of-type(-n+2)", new String[]{"ap", "name+value", "first", "firstUL", "empty", "floatTest", "iframe", "table", "name-tests", "testForm", "liveHandlerOrder", "siblingTest", "last"});
    }

}
