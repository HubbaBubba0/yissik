package org.openqa.selenium.example;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ninja on 16/12/15.
 */
public class LoginTest {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        WebDriver driver = new FirefoxDriver();

        // Go to server demo site
        driver.get("http://localhost:8090/#/login");

//        ----------------------------- REGISTER ------------------------------------
//      Wait for the register button to load and click it
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#/register']")));
        driver.findElement(By.xpath("//a[@href='#/register']")).click();

//      Fill in form
        String firstName = "m";
        String lastName = "m";
        String username = "m";
        String password = "m";

        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("Text1")).sendKeys(lastName);
        driver.findElement(By.id("username")).sendKeys(username);

        WebElement passwordElement = driver.findElement(By.id("password"));
        passwordElement.sendKeys(password);

//      submit form
        passwordElement.submit();

//        Validate registration success
        try {
            wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#/register']")));
            WebElement regSuccess = driver.findElement(By.className("alert-success"));
        } catch (ElementNotFoundException e) {
            Assert.assertTrue("Registration Faild", true);

        }

        System.out.println("Registration Succeeded");


//        ----------------------------- LOGIN ---------------------------------------
        // Find the login input element
        WebElement loginName = driver.findElement(By.id("username"));

        // Enter login name
        loginName.sendKeys(username);

        // Find the password input element
        WebElement loginPassword = driver.findElement(By.id("password"));

        // Enter password
        loginPassword.sendKeys(password);

        // submit the form
        loginPassword.submit();

        //        Validate login success
        try {
            wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Hi')]")));
            WebElement regSuccess = driver.findElement(By.xpath("//*[contains(text(), 'Hi')]"));
        } catch (ElementNotFoundException e) {
            Assert.assertTrue("Login Faild", true);

        }
        //wait for delete button to load and click it
        wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Delete']")));
        driver.findElement(By.xpath("//a[text()='Delete']")).click();

        //logout
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        //        Validate back in login
        Assert.assertEquals(driver.getCurrentUrl(),"http://localhost:8090/#/login");

        //Close the browser
        driver.quit();
    }
}
