package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     ChromeDriver driver;

     public Wrappers(ChromeDriver driver) {
        this.driver = driver;
    }

    public void navigateToSite(String webLink) throws InterruptedException {
        driver.get(webLink);
        Thread.sleep(3000);
        System.out.println("Successfully navigated to website");
    }

    public void searchProduct(String product) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Search for Products, Brands and More']")));
        WebElement searcElement = driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
        searcElement.sendKeys(product + "\n");
        Thread.sleep(3000);
        System.out.println("Entered " + product + " in search bar");
    }

    public void sortByPopularity() throws InterruptedException {
        WebElement buttonElement = driver.findElement(By.xpath("//div[@class='zg-M3Z' and text()='Popularity']"));
        buttonElement.click();
        Thread.sleep(3000);
        System.out.println("Clicked on popularity tab");
    }

    public void countItems() throws InterruptedException {
        List<WebElement> itemElements = driver.findElements(By.xpath("//div[@class='yKfJKb row']/..//div[text()<='4']"));
        int count = itemElements.size();
        System.out.println("Count of items with rating less than or equal to 4 stars: " + count);
    }

    public void filterBasedOnRating() throws InterruptedException {
        Thread.sleep(3000);
        WebElement itemElements = driver.findElement(By.xpath("//div[@class='yKfJKb row']"));

        List<WebElement> rightPerElements = itemElements.findElements(By.xpath("//div[@class='UkUFwK']"));
        for(WebElement item : rightPerElements){
            String discountPercentage = item.getText();
            String[] strArray = discountPercentage.split(" ");
            int discountNumber = Integer.valueOf(strArray[0].replaceAll("[^0-9]", ""));
            if (discountNumber > 17){
                System.out.println("Title: " + item.findElement(By.xpath("../../../..//div[@class='KzDlHZ']")).getText());
                System.out.println("Discount: " + discountPercentage);
            }
        }
        
    }

    public void selectRating() throws InterruptedException {
        Thread.sleep(3000);
        WebElement ratingElement = driver.findElement(By.xpath("//div[contains(text(), '4★ & above')]"));
        ratingElement.click();
        Thread.sleep(3000);
    }

    public void review() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> reviewElements = driver.findElements(By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']"));
        Set<Integer> reviewSet = new HashSet<>();
        for(WebElement reviewElement : reviewElements){
            int userReview = Integer.parseInt(reviewElement.getText().replaceAll("[^\\d]", ""));
            reviewSet.add(userReview);
        }

        List<Integer> userReviewCount = new ArrayList<>(reviewSet);
        Collections.sort(userReviewCount, Collections.reverseOrder());
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        LinkedHashMap<String, String> productDetailsMap = new LinkedHashMap<>();
        for(int i=0; i<5; i++){
            String formattedUserReviewCount = "(" + numberFormat.format(userReviewCount.get(i)) + ")";
            String productTitle = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"+formattedUserReviewCount+"')]/../../a[@class='wjcEIp']")).getText();
            String productImgUrl = driver.findElement(By.xpath("//div[@class='slAVV4']//span[contains(text(),'"+formattedUserReviewCount+"')]/../..//img[@class='DByuf4']")).getAttribute("src");
            String highestReviewCount = String.valueOf(i+1)+"highest review count: " + formattedUserReviewCount+ "Title: "+ productTitle;
            productDetailsMap.put(highestReviewCount, productImgUrl);
        }

        for(Map.Entry<String, String> productDetails : productDetailsMap.entrySet()){
            System.out.println(productDetails.getKey() + " and product image url: " + productDetails.getValue());
        }
    }

}
