package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

    @Test
    public void testCase01()
            throws InterruptedException {
        
        System.out.println("Start Test case: testCase01");
        Wrappers navigate = new Wrappers(driver);
        
        String website = "https://www.flipkart.com/";
        navigate.navigateToSite(website);

        navigate.searchProduct("Washing Machine");

        navigate.sortByPopularity();

        navigate.countItems();

        System.out.println("End Test case: testCase01");
    }

    @Test
    public void testCase02()
            throws InterruptedException {
        
        System.out.println("\nStart Test case: testCase02");
        Wrappers navigate = new Wrappers(driver);
        
        String website = "https://www.flipkart.com/";
        navigate.navigateToSite(website);

        navigate.searchProduct("iPhone");

        navigate.filterBasedOnRating();

        System.out.println("End Test case: testCase02");
    }

    @Test
    public void testCase03()
            throws InterruptedException {
        
        System.out.println("\nStart Test case: testCase03");
        Wrappers navigate = new Wrappers(driver);
        
        String website = "https://www.flipkart.com/";
        navigate.navigateToSite(website);

        navigate.searchProduct("Coffee Mug");

        navigate.selectRating();

        navigate.review();

        System.out.println("End Test case: testCase03");
    }
     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}