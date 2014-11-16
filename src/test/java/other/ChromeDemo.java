package other;

import static io.github.seleniumquery.SeleniumQuery.$; // this will allow the short syntax

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDemo {

//    @Test
    public void mainTest() {
    	System.setProperty("webdriver.chrome.driver", "F:\\desenv\\chromedriver.exe");
    	WebDriver driver = new ChromeDriver();
    	
        $.browser.setDefaultDriver(driver);
		
        $.browser.openUrl("http://www.google.com");

        $("input[name='q']").val("selenium");
        $("button[name='btnG']").click();
        System.out.println($("#resultStats").text());

        // Besides the short syntax and the useful assumptions, the most useful capabilities of
        // SeleniumQuery are the .waitUntil. functions, especially handy for Ajax handling:
        
        /*
        $("input[name='q']").waitUntil.isNotPresent();
        */
        
        // The line above throws an exception as that input never goes away in google.com.

        $.browser.quitDefaultDriver();
    }

}