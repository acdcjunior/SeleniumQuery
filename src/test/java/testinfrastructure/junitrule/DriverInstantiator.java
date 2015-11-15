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

package testinfrastructure.junitrule;

import io.github.seleniumquery.browser.BrowserFunctions;

@SuppressWarnings("deprecation")
abstract class DriverInstantiator {

    private String driverDescription;
    public DriverInstantiator(String driverDescription) { this.driverDescription = driverDescription; }
    public String getDriverDescription() { return driverDescription; }

    abstract void instantiateDriver(BrowserFunctions browser);

    static DriverInstantiator PHANTOMJS = new DriverInstantiator("PhantomJS") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().usePhantomJS();
        }
    };
    static DriverInstantiator FIREFOX_JS_ON = new DriverInstantiator("Firefox - JS ON") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useFirefox();
        }
    };
    static DriverInstantiator FIREFOX_JS_OFF = new DriverInstantiator("Firefox - JS OFF") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useFirefox().withoutJavaScript();
        }
    };
    static DriverInstantiator IE = new DriverInstantiator("IE") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useInternetExplorer();
        }
    };
    static DriverInstantiator CHROME = new DriverInstantiator("Chrome") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useChrome();
        }
    };
    static DriverInstantiator HTMLUNIT_CHROME_JS_ON = new DriverInstantiator("HtmlUnit (Chrome) - JS ON") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useHtmlUnit().emulatingChrome();
        }
    };
    static DriverInstantiator HTMLUNIT_CHROME_JS_OFF = new DriverInstantiator("HtmlUnit (Chrome) - JS OFF") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useHtmlUnit().emulatingChrome().withoutJavaScript();
        }
    };
    static DriverInstantiator HTMLUNIT_FIREFOX_JS_ON = new DriverInstantiator("HtmlUnit (Firefox) - JS ON") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useHtmlUnit().emulatingFirefox();
        }
    };
    static DriverInstantiator HTMLUNIT_FIREFOX_JS_OFF = new DriverInstantiator("HtmlUnit (Firefox) - JS OFF") {
        @Override public void instantiateDriver(BrowserFunctions $) {
            $.driver().useHtmlUnit().emulatingFirefox().withoutJavaScript();
        }
    };
    static DriverInstantiator HTMLUNIT_IE8_JS_ON   = new DriverInstantiator("HtmlUnit (IE8) - JS ON")   { @Override public void instantiateDriver(BrowserFunctions $) { $.driver().useHtmlUnit().emulatingInternetExplorer8(); } };
    static DriverInstantiator HTMLUNIT_IE8_JS_OFF  = new DriverInstantiator("HtmlUnit (IE8) - JS OFF")  { @Override public void instantiateDriver(BrowserFunctions $) { $.driver().useHtmlUnit().emulatingInternetExplorer8().withoutJavaScript(); } };
    static DriverInstantiator HTMLUNIT_IE11_JS_ON  = new DriverInstantiator("HtmlUnit (IE11) - JS ON")  { @Override public void instantiateDriver(BrowserFunctions $) { $.driver().useHtmlUnit().emulatingInternetExplorer11(); } };
    static DriverInstantiator HTMLUNIT_IE11_JS_OFF = new DriverInstantiator("HtmlUnit (IE11) - JS OFF") { @Override public void instantiateDriver(BrowserFunctions $) { $.driver().useHtmlUnit().emulatingInternetExplorer11().withoutJavaScript(); } };

}