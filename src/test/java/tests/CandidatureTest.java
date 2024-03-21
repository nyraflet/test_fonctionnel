package tests;

import java.io.IOException;
import java.time.Duration;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ritec.paris.POM.Base;
import ritec.paris.POM.Candidature.Candidature;

public class CandidatureTest {
    WebDriver driver;
    Candidature candidature;
    Base base;

    @BeforeTest
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://127.0.0.1:8000");
        base = new Base(driver);
        base.acceptCoukie();
        driver.navigate().refresh();
        candidature = new Candidature(driver, base);
    }

    // @AfterTest
    // public void closeWindow() {
    // driver.close();
    // }

    @DataProvider(name = "dataProvider")
    private static Object[] dtProv() throws IOException, ParseException {
        String data = "src/assets/dataTest/candidate.json";
        return Base.readJsonFile(data);
    }

    @Test(priority = 2)
    public void moveTo() {
        candidature.scrollInto();
    }

    // @Test(dataProvider = "dataProvider", priority = 2)
    // public void inputNameTest(JSONObject data) throws InterruptedException {
    // driver.navigate().to("http://127.0.0.1:8000/formateurs");
    // Assert.assertTrue(candidature.inputNameTest(data));
    // driver.navigate().refresh();
    // }

    @Test(dataProvider = "dataProvider", priority = 3)
    public void sendCandidature(JSONObject data_json) {
        candidature.depotCandidature(data_json);
        driver.navigate().refresh();
    }

    @AfterMethod
    public void screenShot(ITestResult result) throws InterruptedException {
        if (ITestResult.FAILURE == result.getStatus()) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            Thread.sleep(1000);
            base.takeScreenShoot("src/assets/ScreenShoot/" + result.getName() + "_" + result.id() + ".png");
        }
    }
}
