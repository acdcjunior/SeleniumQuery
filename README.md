# [seleniumQuery](http://seleniumquery.github.io)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.seleniumquery/seleniumquery.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.seleniumquery%22%20AND%20a%3A%22seleniumquery%22)

[![Codacy Badge](https://api.codacy.com/project/badge/grade/6f25f5fe245746a4a7a53f426e0e1288)](https://www.codacy.com/app/acdcjunior/seleniumQuery)
[![codecov.io](https://codecov.io/gh/seleniumQuery/seleniumQuery/branch/master/graph/badge.svg)](https://codecov.io/gh/seleniumQuery/seleniumQuery)
[![Dependency Status](https://www.versioneye.com/user/projects/56861ab2eb4f47003c000e43/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56861ab2eb4f47003c000e43)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/seleniumQuery/seleniumQuery/master/LICENSE.txt)
[![Join the chat at https://gitter.im/seleniumQuery/seleniumQuery](https://badges.gitter.im/seleniumQuery/seleniumQuery.svg)](https://gitter.im/seleniumQuery/seleniumQuery?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/seleniumQuery/seleniumQuery.svg?branch=master)](https://travis-ci.org/seleniumQuery/seleniumQuery)
[![Windows Build Status](https://ci.appveyor.com/api/projects/status/mwvctg5o8ws7l7jg?svg=true)](https://ci.appveyor.com/project/acdcjunior/seleniumQuery/branch/master)
[![Build status](https://codeship.com/projects/7b37d0c0-d5b4-0133-1efe-62329e93051f/status?branch=master)](https://codeship.com/projects/142644)
[![wercker status](https://app.wercker.com/status/b772beb5c952865d659e548bf7d64f48/s "wercker status")](https://app.wercker.com/project/bykey/b772beb5c952865d659e548bf7d64f48)
[![Circle CI](https://circleci.com/gh/seleniumQuery/seleniumQuery.svg?style=svg)](https://circleci.com/gh/seleniumQuery/seleniumQuery)
[![Run Status](https://api.shippable.com/projects/58b5bc1b1304cc0500e0c7b0/badge?branch=master)](https://app.shippable.com/github/seleniumQuery/seleniumQuery)

[![Sauce Test Status](https://saucelabs.com/open_sauce/build_matrix/acdcjunior.svg)](https://saucelabs.com/u/acdcjunior)

### Feature-rich jQuery-like Java interface for Selenium WebDriver

seleniumQuery is a feature-rich *cross-driver* Java library that brings a **jQuery-like** interface for [Selenium WebDriver](http://docs.seleniumhq.org/projects/webdriver/).

It is designed to be a **thin** layer over Selenium. You can use seleniumQuery to manage the WebDriver for you, or you can use seleniumQuery on top of your favorire selenium framework just
to make some cases simpler when needed.

### Example snippet:

```java
// getting the value
String oldStreet = $("input.street").val();
// setting the value
$("input.street").val("4th St!");
```

### No special configuration needed - use it in your project right now:

On a regular `WebElement`...

```java
// an existing WebElement...
WebElement existingWebElement = driver.findElement(By.id("myId"));
// call jQuery functions
String elementVal = $(existingWebElement).val();
boolean isButton = $(existingWebElement).is(":button"); // enhanced selector!
for (WebElement child: $(existingWebElement).children()) {
  System.out.println("That element's child: "+child);
}
```

Or an existing `WebDriver`...

```java
// an existing WebDriver...
WebDriver driver = new FirefoxDriver();
// set it up
$.driver().use(driver);
// and use all the goods
for (WebElement e: $(".myClass:contains('My Text!'):not(:button)")) {
  System.out.println("That element: " + e);
}
```

## What can you do with it?

Allows querying elements by:

- **CSS3 Selectors** - `$(".myClass")`, `$("#table tr:nth-child(3n+1)")`;
- **jQuery enhanced selectors** - `$(":text:eq(3)")`, `$(".myClass:contains('My Text!')")`;
- **XPath** - `$("//div/*/label/preceding::*")`;
- and even some own **seleniumQuery selectors**: `$("#myOldDiv").is(":not(:present)")`.

Built using Selenium WebDriver's capabilities, no `jQuery.js` is embedded at the page, no side-effects are generated.

## Quickstart: A running example

Try it out now with the running example below:

```java
import static io.github.seleniumquery.SeleniumQuery.$; // this will allow the short syntax

public class SeleniumQueryExample {
  public static void main(String[] args) {
    // The WebDriver will be instantiated only when first used
    $.driver()
        .useChrome() // sets Chrome as the driver (this is optional, if omitted, will default to HtmlUnit)
        .headless() // configures chrome to be headless
        .autoDriverDownload() // automatically downloads and configures chromedriver.exe
        .autoQuitDriver(); // automatically quits the driver when the JVM shuts down

    // or, instead, use any previously existing driver
    // $.driver().use(myExistingInstanceOfWebDriver);

    // starts the driver (if not started already) and opens the URL
    $.url("http://www.google.com/?hl=en");

    // interact with the page
    $(":text[name='q']").val("seleniumQuery"); // the keys are actually typed!

    // Besides the short syntax and the jQuery behavior you already know,
    // other very useful function in seleniumQuery is .waitUntil(),
    // handy for dealing with user-waiting actions (specially in Ajax enabled pages):

    // the command below waits until the button is visible and then performs a real user click (not just the JS event)
    $(":button[value='Google Search']").waitUntil().isVisible().then().click();

    // this waits for the #resultStats to be visible using a selector and, when it is visible, returns its text content
    String resultsText = $("#resultStats").waitUntil().is(":visible").then().text();

    // .assertThat() functions: fluently asserts that the text contains the string "seconds", ignoring case
    $("#resultStats").assertThat().text().containsIgnoreCase("seconds");

    System.out.println(resultsText);
    // should print something like: About 4,100 results (0.42 seconds)

    // $.quit(); // would quit the driver, but it is not needed as .autoQuitDriver() was used
  }
}
```

To get the latest version of seleniumQuery, add to your **`pom.xml`**:

```xml
<dependency>
    <groupId>io.github.seleniumquery</groupId>
    <artifactId>seleniumquery</artifactId>
    <version>0.18.0</version>
</dependency>
```



<br><br>

## Looking for more examples?

Download and execute the **[seleniumQuery showcase project](https://github.com/acdcjunior/seleniumQuery-showcase)**.
 It contains many demonstrations of what seleniumQuery is capable of.

<br>

# Features

seleniumQuery implements all jQuery functions that are useful to browser manipulation.
On top of it, we add many other useful functions (see `$("selector").waitUntil()` and `$("selector").assertThat()` below).
 
Our main goal is to make emulating user actions and reading the state of pages easier than ever, with a consistent behavior across drivers.


### Readable jQuery syntax you already know

Make your code/tests more readable and easier to maintain. Leverage your knowledge of jQuery.

```java
// Instead of regular Selenium code:
WebElement element = driver.findElement(By.id("mySelect"));
new Select(element).selectByValue("ford");

// You can have the same effect writing just:
$("#mySelect").val("ford");
```

Get to know what jQuery functions seleniumQuery supports and what else it brings to the table on our [seleniumQuery API wiki page](https://github.com/seleniumQuery/seleniumQuery/wiki/seleniumQuery-API).

### Powerful selector system

Let the tool do the hard work and find elements easily:

- CSS3 Selectors - `$(".myClass")`, `$("#table tr:nth-child(3n+1)")`
- jQuery/Sizzle enhancements - `$(".claz:eq(3)")`, `$(".claz:contains('My Text!')")`
- XPath - `$("//div/*/label/preceding::*")`
- and even some own seleniumQuery selectors: `$("#myOldDiv").is(":not(:present)")`.

You pick your style. Whatever is more interesting at the moment. Mixing is OK:

```java
$("#tab tr:nth-child(3n+1)").find("/img[@alt='calendar']/preceding::input").val("2014-11-12")
```
Find more about them in [seleniumQuery Selectors wiki page.](https://github.com/seleniumQuery/seleniumQuery/wiki/seleniumQuery-Selectors)

<br>

### Waiting capabilities for improved Ajax testing

Other important feature is the leverage of `WebDriver`'s `FluentWait` capabilities **directly** in the element (no boilerplate code!) through the use of the `.waitUntil()` function:

```java
// WebDriver cannot natively detect the end of an Ajax call.
// To test your application's behavior, you can and should always work with the
// Ajax's expected effects, visible for the end user.
// Below is an example of a <div> that should be hidden as effect of an Ajax call.
// The code will hold until the modal is gone. If it is never gone, seleniumQuery
// will throw a timeout exception.
$("#modalDiv :button:contains('OK')").click();
$("#modalDiv :button:contains('OK')").waitUntil().is(":not(:visible)");

// Or, fluently:
$("#modalDivOkButton").click().waitUntil().is(":not(:visible)");
```

And, that's right, the `.is()` function above is your old-time friend that takes a selector as argument!

Check out what else `.waitUntil()` can do in the [seleniumQuery API wiki page](https://github.com/seleniumQuery/seleniumQuery/wiki/seleniumQuery-API).

<br>

### Plugin System

seleniumQuery supports plugins through the `.as(PLUGIN)` function, such as:

```java
$("div").as(YOURPLUGIN).someMethodFromYourPlugin();
```

There are some default plugins. To check them out, call `.as()` without arguments. Example:

```java
// the .select() plugin
$("#citiesSelect").as().select().selectByVisibleText("New York");
// picks an <option> in the <select> based in the <option>'s visible text
```

For an example of how to create your own plugin, check the [seleniumQuery Plugin wiki page](https://github.com/seleniumQuery/seleniumQuery/wiki/seleniumQuery-Plugin-Support---.as()-function).


<br>

### Flexible WebDriver builder system

How to setup the `WebDriver`? Simply use our builder. The driver will be instantiated only when first used.

Supported drivers (and those that are -- or can be -- headless):

| Chrome | Firefox | Opera | Edge | Internet<br>Explorer | PhantomJS | HtmlUnit |
| ------ | ------- | ----- | ---- | ----------------- | --------- | -------- |
| ![chrome](https://github.com/alrra/browser-logos/blob/master/src/chrome/chrome_64x64.png?raw=true) | ![firefox](https://github.com/alrra/browser-logos/blob/master/src/firefox/firefox_64x64.png?raw=true) | ![opera](https://github.com/alrra/browser-logos/blob/master/src/opera/opera_64x64.png?raw=true) | ![edge](https://github.com/alrra/browser-logos/blob/master/src/edge/edge_64x64.png?raw=true) | ![internet explorer](doc/ie.png) | ![PhantomJS](doc/phantomjs.png) | ![HtmlUnit](doc/htmlunit.png)
| Headless | Headless | - | - | - | Headless | Headless |

#### How to use a driver

You can download [their executables](https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver) before or you can
let seleniumQuery automatically download and configure them (powered by [webdrivermanager](https://github.com/bonigarcia/webdrivermanager)).
 Setting them up in seleniumQuery is all too easy:

```java
// Using Chrome
$.driver().useChrome(); // will look for chromedriver/exe to you, including in the classpath!
// if you don't have chromedriver.exe and want seleniumQuery to automatically download and configure it
$.driver().useChrome().headless().autoDriverDownload();
// If you want to set the path to chromedriver.exe yourself
$.driver().useChrome().withPathToChromeDriver("path/to/chromedriver.exe")
// General example:
$.driver()
    .useChrome() // sets Chrome as the driver (this is optional, if omitted, will default to HtmlUnit)
    .headless() // configures chrome to be headless
    .autoDriverDownload() // automatically downloads and configures chromedriver.exe
    .autoQuitDriver(); // automatically quits the driver when the JVM shuts down
// using options
$.driver()
    .useChrome()
    .withOptions(<some ChromeOptions instance>)

// Using firefox
$.driver()
    .useFirefox() // sets Firefox as the driver
    .headless() // configures Firefox to be headless
    .autoDriverDownload() // automatically downloads and configures geckodriver.exe
    .autoQuitDriver(); // automatically quits the driver when the JVM shuts down

// InternetExplorerDriver
$.driver().useInternetExplorer(); // we search IEDriverServer.exe for you
// Or you set the path yourself
$.driver().useInternetExplorer().withPathToIEDriverServerExe("C:\\IEDriverServer.exe");

// PhantomJS (GhostDriver)
$.driver().usePhantomJS(); // again, we'll find phantomjs[.exe] to you
// Or you may set the path yourself
$.driver().usePhantomJS().withPathToPhantomJS("path/to/phantomjs.exe");
````

##### HtmlUnit

So many possibilities to set up `HtmlUnitDriver`... If only there was a simple way to use them. Oh, wait:

```java
// HtmlUnit default (Chrome/JavaScript ON)
$.driver().useHtmlUnit();
// Want disabled JavaScript, just call .withoutJavaScript()
$.driver().useHtmlUnit().withoutJavaScript();

// HtmlUnit emulating Chrome
$.driver().useHtmlUnit().emulatingChrome();
$.driver().useHtmlUnit().emulatingChrome().withoutJavaScript();
// HtmlUnit emulating Firefox
$.driver().useHtmlUnit().emulatingFirefox(); // could disable JS here as well
// And IE
$.driver().useHtmlUnit().emulatingInternetExplorer11(); // JS is disableable as well
$.driver().useHtmlUnit().emulatingInternetExplorer(); // will pick latest IE
````

#### But there is more

Explore the auto-complete. There are additional options to every driver, such as `.withCapabilities(DesiredCapabilities)` or some specific, such as `.withProfile(FirefoxProfile)` or `.withOptions(ChromeOptions)`.

Finally, if you want to create the `WebDriver` yourself:

```java
WebDriver myDriver = ...;
$.driver().use(myDriver);
```

<br>

## seleniumQuery still is Selenium - with "just" a jQuery interface

That's why it can work with disabled JavaScript!

But there is a more important aspect to it: Although our functions yield the same result as if you were using jQuery, remember we always execute them from the user perspective.
In other words, when you call:
```java
$(":input[name='email']").val("seleniumQuery@example.com");
```

We don't change  the `value` attribute directly like jQuery does. We actually do as a user would: We **clear** the input
and **type, key by key**, the string provided as argument!

And we go the *extra mile* whenever possible:
- Our **`$().val()` even works on `contenteditable` elements AND `documentMode=on <iframe>`s**: They don't have `value`, but we type the text in them, again, key by key, as an user would;
- If it is an `<input type="file">` we select the file;
- When the element is a `<select>`, we choose the `<option>` by the value given (same as `$("selector").as().select().selectByValue("123")`).

### Always from the user perspective

On the same tone, when selecting/checking `<option>`s or checkboxes or radios, try not to use `$().prop("selected", true)` directly to them (which to work, of course, would need JS to be enabled on the driver).
Do as an user would: call `.click()`! Or, better yet, use seleniumQuery's `.as().select()` functions: `$().as().select().selectByVisibleText("My Option")` or `$().as().select().selectByValue("123")`.

<br><br>

### Alternate symbols

If the dollar symbol, `$`, gives you the yikes -- we know, it is used for internal class names --, it is important to notice that the `$` symbol in seleniumQuery is not a class name, but a `static` method (and field). Still, if you don't feel like using it, you can resort to `sQ()` or good ol' `jQuery()` and benefit from all the same goodies:

```java
import static io.github.seleniumquery.SeleniumQuery.sQ;
import static io.github.seleniumquery.SeleniumQuery.jQuery;
...
String oldStreet = sQ("input.street").val();
sQ("input.street").val("4th St!");

String oldStreetz = jQuery("input.street").val();
jQuery("input.street").val("5th St!");
```

<br>


# Using multiple browsers/drivers simultaneously

Typically, the `$` is a static variable, thus every command you issue only affects the one same instance of WebDriver.

But... what if you want/need to use two WebDrivers at the same time?

We've got your back, see the [example](src/test/java/endtoend/browser/SeleniumQueryBrowserTest.java):

```java
public static void main(String[] args) {
  String demoPage = "https://cdn.rawgit.com/seleniumQuery/seleniumQuery-showcase/master/Agent.html";

  // using two drivers (chrome and firefox) at the same time
  SeleniumQueryBrowser chrome = new SeleniumQueryBrowser();
  chrome.$.driver().useHtmlUnit().emulatingChrome().autoQuitDriver();
  chrome.$.url(demoPage);

  SeleniumQueryBrowser firefox = new SeleniumQueryBrowser();
  firefox.$.driver().useHtmlUnit().emulatingFirefox().autoQuitDriver();
  firefox.$.url(demoPage);

  chrome.$("#agent").assertThat().text().contains("Chrome");
  firefox.$("#agent").assertThat().text().contains("Firefox");
}
```

<br>

# More

Find more on our [wiki](https://github.com/seleniumQuery/seleniumQuery/wiki).

<br>

# Changelog/Roadmap

See [releases](https://github.com/seleniumQuery/seleniumQuery/releases).

# Contributing

The tool quite simple, so there's a lot of room for improvement. If you think something would be useful for you, it
 would probably be useful to us all, [tell us what you want](https://github.com/seleniumQuery/seleniumQuery/issues/new)!

