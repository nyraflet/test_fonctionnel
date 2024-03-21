package ritec.paris.POM.Candidature;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.github.dockerjava.api.model.Driver;

import ritec.paris.POM.Base;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Candidature {
    WebDriver driver;
    Base base;
    By candidature_lastname = By.id("candidature_lastname");
    By candidature_firstname = By.id("candidature_firstname");
    By candidature_email = By.id("candidature_email");
    By candidature_phonenumber = By.id("candidature_phonenumber");
    By candidature_daily_rate = By.id("candidature_daily_rate");
    By addFileButton = By.id("candidature_cv");// *[@id="cvFileNameDisplay"]/div
    By btUpload = By.id("//*[@id=\"cvFileNameDisplay\"]/div/a");
    By candidature_skills = By.id("candidature_skills");
    By candidature_ref = By.id("candidature_professionalReference");
    By send_candidature = By.id("send-candidature");
    By manage_Cdture = By.linkText("Géger les candidatures");
    By btnToFomeCdt = By.xpath("//*[@id=\"carouse3-example-generic\"]/div/div/div/a");
    // By error = By.xpath("//*[contains(@id, '-error')]");
    By error = By.id("lastname-error");

    public Candidature(WebDriver driver, Base base) {
        this.driver = driver;
        this.base = base;
    }

    public void scrollInto() {
        base.scrollToBottom();
        driver.findElement(btnToFomeCdt).click();
    }

    public String extractData(JSONObject jsonData, String field, int fieldIn) {
        JSONArray jsonArray = (JSONArray) jsonData.get(field);
        return jsonArray.toArray()[fieldIn].toString();
    }

    public JSONArray extractData(JSONObject jsonData, String field) {
        JSONArray jsonArray = (JSONArray) jsonData.get(field);
        return (JSONArray) jsonArray.toArray()[1];
    }

    public static String applyFunction(JSONArray name, Function<JSONArray, String> function) {
        return function.apply(name);
    }

    public Boolean depotCandidature(JSONObject jsonData) {
        JSONArray jsonfile = extractData(jsonData, "str_addFileButton");

        driver.findElement(candidature_lastname).sendKeys(extractData(jsonData, "str_lastname", 1));
        driver.findElement(candidature_firstname).sendKeys(extractData(jsonData, "str_firstname", 1));
        driver.findElement(candidature_email).sendKeys(extractData(jsonData, "str_email", 1));
        driver.findElement(candidature_phonenumber).sendKeys(extractData(jsonData, "str_phonenumber", 1));
        driver.findElement(candidature_daily_rate).sendKeys(extractData(jsonData, "str_daily_rate", 1));
        for (Object object : jsonfile) {
            File file = new File(object.toString()).getAbsoluteFile();
            driver.findElement(addFileButton).sendKeys(file.getAbsolutePath());
        }
        // driver.findElement(addFileButton).sendKeys();
        driver.findElement(candidature_skills).sendKeys(extractData(jsonData, "str_candidature_skills", 1));
        driver.findElement(candidature_ref).sendKeys(extractData(jsonData, "str_candidature_ref", 1));
        base.scrollDown(manage_Cdture);
        if (base.isClickable(send_candidature, 2)) {
            driver.findElement(send_candidature).click();
        }
        return true;

    }

    // function to fill input
    public Boolean inputTest(JSONObject jsonData, By inputText, By errorMessage, String field) {
        // retrive specific field from json
        String dataValue = extractData(jsonData, field, 1);
        // send value into the input form
        driver.findElement(inputText).sendKeys(dataValue);
        new Actions(driver).sendKeys(Keys.TAB).perform();
        int jsonError = extractData(jsonData, field, 0).length();
        // verify error message
        int erroText = driver.findElement(errorMessage).getText().length();
        if (jsonError > 0 && erroText <= 0) {
            return false;
        }
        return true;
    }

    public Boolean inputNameTest(JSONObject jsonData) {
        return inputTest(jsonData, candidature_lastname, error, "str_lastname");
    }
}
