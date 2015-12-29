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

package integration.io.github.seleniumquery.by;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import io.github.seleniumquery.utils.DriverVersionUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class DriverVersionUtilsTest {

	@Test
	@Ignore
	public void isHtmlUnitDriverEmulatingIE__chrome() {
		HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
		assertDriverIsNotHtmlUnitDriverEmulatingIE(htmlUnitDriver);
	}
	
	private void assertDriverIsNotHtmlUnitDriverEmulatingIE(HtmlUnitDriver htmlUnitDriver) {
		boolean isHtmlUnitDriverEmulatingIE = DriverVersionUtils.isHtmlUnitDriverEmulatingIE(htmlUnitDriver);
		assertThat(isHtmlUnitDriverEmulatingIE, is(false));
	}

	@Test
	@Ignore
	@SuppressWarnings("deprecation")
	public void isHtmlUnitDriverEmulatingIE__firefox_deprecated_versions() {
		assertDriverIsNotHtmlUnitDriverEmulatingIE(new HtmlUnitDriver(BrowserVersion.FIREFOX_31));
	}

	@Test
	@Ignore
	public void isHtmlUnitDriverEmulatingIE__firefox_non_deprecated_versions() {
		assertDriverIsNotHtmlUnitDriverEmulatingIE(new HtmlUnitDriver(BrowserVersion.FIREFOX_38));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void isHtmlUnitDriverEmulatingIE__ie_deprecated_versions() {
		System.out.println("#@ Test if is IE8");
		assertDriverIsHtmlUnitEmulatingIE(new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8));
	}

	private void assertDriverIsHtmlUnitEmulatingIE(HtmlUnitDriver htmlUnitDriver) {
        System.out.println("\t@# Sending a "+htmlUnitDriver.getClass());
        boolean isHtmlUnitDriverEmulatingIE = DriverVersionUtils.isHtmlUnitDriverEmulatingIE(htmlUnitDriver);
		assertThat(isHtmlUnitDriverEmulatingIE, is(true));
	}

	@Test
	public void isHtmlUnitDriverEmulatingIE__ie_non_deprecated_versions() {
		System.out.println("#@ Test if is IE11");
		assertDriverIsHtmlUnitEmulatingIE(new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_11));
	}
	
	@Test
	@Ignore
	public void isHtmlUnitDriverEmulatingIE__not_HtmlUnit() {
		WebDriver webDriverMock = mock(WebDriver.class);
		boolean isHtmlUnitDriverEmulatingIE = DriverVersionUtils.isHtmlUnitDriverEmulatingIE(webDriverMock);
		assertThat(isHtmlUnitDriverEmulatingIE, is(false));
	}

}