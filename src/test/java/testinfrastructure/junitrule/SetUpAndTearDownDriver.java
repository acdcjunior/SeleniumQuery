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

package testinfrastructure.junitrule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import testinfrastructure.EndToEndTestUtils;

import static testinfrastructure.testutils.EnvironmentTestUtils.isRunningAtCodeShip;
import static testinfrastructure.testutils.EnvironmentTestUtils.isRunningAtContinuousIntegrationServer;

public class SetUpAndTearDownDriver implements TestRule {

	private static final String NOT_SPECIFIED = null;

	public static final DriverToRunTestsIn driverToRunTestsIn = whatDriversShouldTestsRun();

	private final String testUrl;
	boolean driverHasJavaScriptEnabled = false;

	public SetUpAndTearDownDriver() {
		this.testUrl = NOT_SPECIFIED;
	}

	public SetUpAndTearDownDriver(Class<?> htmlTestUrlClass) {
		this.testUrl = EndToEndTestUtils.classNameToTestFileUrl(htmlTestUrlClass);
	}

	private static DriverToRunTestsIn whatDriversShouldTestsRun() {
		if (isRunningAtCodeShip()) {
			// will also run DriverToRunTestsIn.REMOTE if [run sauce] is at last commit message
			return DriverToRunTestsIn.HTMLUNIT_CHROME_JS_ON_ONLY;
		}
		if (isRunningAtContinuousIntegrationServer()) {
			return DriverToRunTestsIn.HEADLESS_DRIVERS_JS_ON_AND_OFF;
		}
		return DriverToRunTestsIn.HTMLUNIT_CHROME_JS_ON_ONLY;
	}

	private String url(Description description) {
		//noinspection StringEquality
		if (this.testUrl == NOT_SPECIFIED) {
			return EndToEndTestUtils.classNameToTestFileUrl(description.getTestClass());
		}
		return this.testUrl;
	}

	private boolean browserWasStarted = false;

	@Override
	public Statement apply(final Statement base, final Description description) {
		if (description.isSuite()) {
			browserWasStarted = true;
			// TODO check if all methods of the class are annotated with JSOnly and skip driver creation when it wont have JS ON
			return new RunTestMethodsInChosenDrivers(driverToRunTestsIn, base, url(description), this);
		}
		Statement runTestMethodStatement = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				boolean isJavaScriptOnlyTest = description.getAnnotation(JavaScriptOnly.class) != null;
				if (isJavaScriptOnlyTest && !SetUpAndTearDownDriver.this.driverHasJavaScriptEnabled) {
					System.out.println("\t\t-> Skipping JavaScript-only test: " + description);
					return;
				}
				beforeMethod(description);
				base.evaluate();
				afterMethod(description);
			}
		};

		if (!browserWasStarted) {
            // The test class has this as @Rule only and not as @ClassRule/@Rule, so we restart the browser every method
            // because if it had a @ClassRule, the if above would have been triggered and the browser would be already started
			return new RunTestMethodsInChosenDrivers(driverToRunTestsIn, runTestMethodStatement, url(description), this);
		}
		return runTestMethodStatement;
	}

    @SuppressWarnings("unused") private void beforeClass() { }
    @SuppressWarnings("unused") private void afterClass() { }

	private void beforeMethod(Description description) {
		String urlToOpen = url(description);
		EndToEndTestUtils.openUrl(urlToOpen);
	}

	@SuppressWarnings("unused") private void afterMethod(Description description) { }

}
