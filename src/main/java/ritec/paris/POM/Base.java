package ritec.paris.POM;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {
    WebDriver driver;
    By accpetCoukie = By.xpath("//button[@type='submit' and @onclick='acceptSession(event)']");

    public Base(WebDriver driver) {
        this.driver = driver;
        // PageFactory.initElements(driver, this);
    }

    // coukie agreement
    public void acceptCoukie() {
        if (isClickable(accpetCoukie, 5)) {
            driver.findElement(accpetCoukie).click();
        }
    }

    public void scrollDown(By webelement) {
        try {
            new Actions(driver).scrollToElement(driver.findElement(webelement)).perform();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrollToBottom() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Object[] readJsonFile(String filePath) throws IOException, ParseException {
        FileReader fileReader = new FileReader(filePath);
        JSONArray jsonData = (JSONArray) (new JSONParser()).parse(fileReader);
        return jsonData.toArray();

    }

    public void takeScreenShoot(String filePath) {
        try {
            TakesScreenshot scrShoot = ((TakesScreenshot) driver);
            File fileOutput = scrShoot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(fileOutput, destFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Boolean isVisible(By webElement, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(webElement)));
            return true;
        } catch (Exception e) {
            System.out.println("Unable to locate the element " + webElement);
            return false;
        }
    }

    public Boolean isClickable(By webElement, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(webElement)));
            return true;
        } catch (Exception e) {
            System.out.println("unable to click on the element " + webElement);
            return false;
        }

    }

    public String extractData(JSONObject jsonData, String field, int fieldIn) {
        JSONArray jsonArray = (JSONArray) jsonData.get(field);
        return jsonArray.toArray()[fieldIn].toString();
    }

}
