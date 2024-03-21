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
import org.testng.annotations.BeforeMethod;
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
        candidature = new Candidature(driver, base);
    }

    @AfterTest
    public void closeWindow() {
        driver.close();
    }

    @DataProvider(name = "dataProvider")
    private static Object[] dtProv() throws IOException, ParseException {
        String data = "src/assets/dataTest/candidate.json";
        return Base.readJsonFile(data);
    }

    @Test(priority = 2)
    public void moveTo() {
        candidature.scrollInto();
    }

    @Test(dataProvider = "dataProvider", priority = 2)
    public void inputNameTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputNameTest(data));
    }

    @Test(dataProvider = "dataProvider", priority = 4)
    public void inputLatsNameTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputLatsNameTest(data));
    }

    @Test(dataProvider = "dataProvider", priority = 5)
    public void inputEmailTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputEmailTest(data));
    }

    @Test(dataProvider = "dataProvider", priority = 6)
    public void inputPhoneTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputPhoneTest(data));
    }

    @Test(dataProvider = "dataProvider", priority = 7)
    public void inputCVTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputCVTest(data));
    }

    @Test(dataProvider = "dataProvider", priority = 8)
    public void inputSkillTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputSkillTest(data));
    }

    @Test(dataProvider = "dataProvider", priority = 9)
    public void inputRefTest(JSONObject data) throws InterruptedException {
        Assert.assertTrue(candidature.inputRefTest(data));
    }

    // @Test(dataProvider = "dataProvider", priority = 10)
    // public void sendCandidature(JSONObject data_json) {
    // candidature.depotCandidature(data_json);
    // driver.navigate().refresh();
    // }
    @BeforeMethod
    public void refreshPage() {
        driver.navigate().to("http://127.0.0.1:8000/formateurs");
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
