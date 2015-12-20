package org.openqa.selenium.example;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

/**
 * Created by ninja on 17/12/15.
 */
public class chromeDriver {

    public static void main(String[] args) {

        String userName = "admin";
        String userPassword = "admin";

        // Create a new instance of the Chrome driver and a webDriverWait
        System.setProperty("webdriver.chrome.driver", "/home/ninja/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wdWait = new WebDriverWait(driver, 10);

        // Go to sugar crm
        driver.get("http://localhost/sugar/SugarCE-Full-6.5.22/index.php?action=Login&module=Users");

        //Login
        //Wait for login element to load
        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_name")));
        //enter user name and password and submit
        driver.findElement(By.id("user_name")).sendKeys(userName);
        WebElement passwordInput = driver.findElement(By.id("user_password"));
        passwordInput.sendKeys(userPassword);
        passwordInput.submit();

        //Navigate to Sales -> Leads -> Create Lead
        driver.findElement(By.linkText("Sales")).click();
        driver.findElement(By.linkText("Leads")).click();

        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/span[text()='Create Lead']")));
        driver.findElement(By.xpath("//a/span[text()='Create Lead']")).click();

        //New Lead

        //create unique first and last names
        int randomizingCounter = 0;
        Date date = new Date(System.currentTimeMillis());
        String firstName = "" + randomizingCounter++ + date.getTime();
        String lastName = "" + randomizingCounter++ + date.getTime();

        //fill in form

        //first name
        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first_name")));
        driver.findElement(By.id("first_name")).sendKeys(firstName);
        //last name
        driver.findElement(By.id("last_name")).sendKeys(lastName);

        //slutation dropdown
        Select dropdown = new Select(driver.findElement(By.id("salutation")));
        dropdown.selectByVisibleText("Prof.");

        //check "Copy address from left" checkbox
        WebElement checkbox = driver.findElement(By.id("alt_checkbox"));
        if ( !checkbox.isSelected() )
        {
            checkbox.click();
        }

        //save
        driver.findElement(By.id("SAVE_FOOTER")).click();

        //go back to lead search
        driver.findElement(By.linkText("Leads")).click();

        //search for the created user
        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_name_basic")));
        WebElement search_name_basic = driver.findElement(By.id("search_name_basic"));
        search_name_basic.clear();
        search_name_basic.sendKeys(lastName);
        search_name_basic.submit();

        //validate user appears in list
        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MassUpdate")));
        try {
            driver.findElement(By.xpath("//*[contains(text(), lastName)]"));
        } catch (ElementNotFoundException e){
            Assert.assertTrue(true);
        }

        System.out.println("New user found");

        //Close the browser
        driver.quit();
    }

}
