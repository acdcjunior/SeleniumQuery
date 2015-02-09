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

package io.github.seleniumquery.browser;

import io.github.seleniumquery.browser.driver.SeleniumQueryDriver;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Represents the seleniumQuery global browser instance.
 * 
 * @author acdcjunior
 *
 * @since 0.9.0
 */
@SuppressWarnings({"deprecation", "unused"})
public class BrowserFunctionsWithDeprecatedFunctions extends BrowserFunctions {
	
	/**
	 * <p>
	 * Series of functions that set the default browser behavior.
	 * </p>
	 *
	 * <p>
	 * This field has been deprecated.
	 * </p>
	 *
	 * <p>
	 * The new place for the functions that were in this object is either {@code $.} or {@code $.driver()}.<br>
	 * In other words, the function {@code $.browser.function();} will either be {@code $.function();} or
	 * {@code $.driver().function();}.<br>
	 * The name may also have changed. To be sure, check the old function's docs for the new name.
	 * </p>
	 *
	 * @since 0.9.0
	 */
	@Deprecated public final OldBrowserFunctions browser = new OldBrowserFunctions(this);

	/**
	 * The seleniumQuery browser. Adds several utility functions to the WebDriver class.
	 *
	 * @since 0.9.0
	 *
	 * @deprecated This class refers to a deprecated way of accessing some functions and will be removed by the next release.
	 * You'll find the functions for this object in <b>{@code $.function();}</b> or <b>{@code $.driver().function();}</b>.
	 */
	public class OldBrowserFunctions {

		private BrowserFunctionsWithDeprecatedFunctions browser;
		public OldBrowserFunctions(BrowserFunctionsWithDeprecatedFunctions browser) { this.browser = browser; }

		/** @deprecated Use: <code>$.driver();</code>
		 * @return A self reference. */
		public SeleniumQueryDriver globalDriver() { return browser.driver(); }

		/** @deprecated Use: <code>$.driver().use(webDriverInstance);</code>
		 * @param defaultDriver The new global WebDriver instance.
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriver(WebDriver defaultDriver) { browser.driver().use(defaultDriver); return this; }

		/** @deprecated Use: <code>$.driver().useHtmlUnit();</code>
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsHtmlUnit() { browser.driver().useHtmlUnit(); return this; }

		/** @deprecated Use: <code>$.driver().useFirefox();</code>
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsFirefox() { browser.driver().useFirefox(); return this; }

		/** @deprecated Use: <code>$.driver().useChrome();</code>
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsChrome() { browser.driver().useChrome(); return this; }

		/** @deprecated Use: <code>$.driver().useChrome().withPathToChromeDriver(path);</code>
		 * @param path path to ChromeDriver executable (<code>chromedriver.exe</code>/<code>chromedriver</code>).
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsChrome(String path) { browser.driver().useChrome().withPathToChromeDriver(path); return this; }

		/** @deprecated Use: <code>$.driver().useInternetExplorer();</code>
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsIE() { browser.driver().useInternetExplorer(); return this; }

		/** @deprecated Use: <code>$.driver().useInternetExplorer().withPathToIEDriverServerExe(path);</code>
		 * @param path path to IEDriverServer.exe.
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsIE(String path) { browser.driver().useInternetExplorer().withPathToIEDriverServerExe(path); return this; }

		/** @deprecated Use: <code>$.driver().usePhantomJS();</code>
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsPhantomJS() { browser.driver().usePhantomJS(); return this; }

		/** @deprecated Use: <code>$.driver().usePhantomJS().withPathToPhantomJS(path);</code>
		 * @param path path to PhantomJS executable (<code>phantomjs.exe</code>/<code>phantomjs.exe</code>).
		 * @return A self reference. */
		public OldBrowserFunctions setDefaultDriverAsPhantomJS(String path) { browser.driver().usePhantomJS().withPathToPhantomJS(path); return this; }

		/** @deprecated Use: <code>$.driver().get();</code>
		 * @return the currently set {@link WebDriver}. */
		public WebDriver getDefaultDriver() { return browser.driver().get(); }

		/** @deprecated Use: <code>$.driver().quit();</code> */
		public void quitDefaultDriver() { browser.driver().quit(); }

		/** @deprecated Use: <code>$.driver().quit();</code> */
		public void quitDefaultBrowser() { browser.driver().quit(); }

		/** @deprecated Use: <code>$.driver().quit();</code> */
		public void quit() { browser.driver().quit(); }

		/** @deprecated Use: <code>$.url(url);</code>
		 * @param url the URL to be opened. */
		public void openUrl(String url) { browser.url(url); }

		/** @deprecated Use: <code>$.url(file);</code>
		 * @param file the file to be opened as URL. */
		public void openUrl(File file) { browser.url(file); }

		/** @deprecated Use: <code>$.url(url);</code>
		 * @param url the URL to be opened. */
		public void open(String url) { browser.url(url); }

		/** @deprecated Use: <code>$.url(file);</code>
		 * @param file the file to be opened as URL. */
		public void open(File file) { browser.url(file); }

		/** @deprecated Use: <code>$.url(url);</code>
		 * @param url the URL to be opened. */
		public void url(String url) { browser.url(url); }

		/** @deprecated Use: <code>$.url(file);</code>
		 * @param file the file to be opened as URL. */
		public void url(File file) { browser.url(file); }

		/** @deprecated Use: <code>$.url();</code>
		 * @return the currently loaded URL. */
		public String url() { return browser.url(); }

		/** @deprecated Use: <code>$.url();</code>
		 * @return the currently loaded URL. */
		public String getCurrentUrl() { return browser.url(); }

		/** @deprecated Use: <code>$.pause(timeToPauseInMillis);</code>
		 * @param timeToWait time to wait
		 * @param timeUnit a given unit of time granularity */
		public void sleep(int timeToWait, TimeUnit timeUnit) { browser.pause(MILLISECONDS.convert(timeToWait, timeUnit)); }

		/** @deprecated Use: <code>$.pause(timeToPauseInMillis);</code>
		 * @param millis pause duration, in milliseconds. */
		public void sleep(int millis) { browser.pause(millis); }

		/** @deprecated Use: <code>$.maximizeWindow();</code> */
		public void maximizeWindow() {
			browser.maximizeWindow();
		}

	}
	
}