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
import ritec.paris.POM.HomePage.HomePage;

public class HomePageTest {
    WebDriver driver;
    HomePage homePage;
    Base base;

    @BeforeTest()
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://127.0.0.1:8000");
        base = new Base(driver);
        base.acceptCoukie();
        driver.navigate().refresh();
        homePage = new HomePage(driver, base);

    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }

    @Test(priority = 2)
    public void viewDetail() throws InterruptedException {
        driver.navigate().to("http://127.0.0.1:8000");
        Assert.assertTrue(homePage.viewDetail());
    }

    // test return is failed when the text 'Les Catégories de Formation' is not
    // found after clicking on sub-category
    @Test(priority = 3)
    public void selectFormation() throws InterruptedException {
        driver.navigate().to("http://127.0.0.1:8000");
        Assert.assertTrue(homePage.selectFormation());
    }

    @Test(priority = 4)
    public void viewAllFormation() {
        driver.navigate().to("http://127.0.0.1:8000");
        Assert.assertTrue(homePage.viewAllFormation());
    }

    @Test(priority = 5)
    public void addTopCoursToCard() throws InterruptedException {
        driver.navigate().to("http://127.0.0.1:8000");
        Assert.assertTrue(homePage.addToCard());
    }

    // move to fiancement page
    @Test(priority = 6)
    public void moveToFinacement() throws InterruptedException {
        driver.navigate().to("http://127.0.0.1:8000");
        Assert.assertTrue(homePage.moveToFinacement());
    }

    @DataProvider(name = "dataProvider")
    private static Object[] dtProv() throws IOException, ParseException {
        String data = "src/assets/dataTest/recherche.json";
        return Base.readJsonFile(data);
    }

    @Test(dataProvider = "dataProvider", priority = 7)
    public void recherche(JSONObject jsonData) {
        driver.navigate().to("http://127.0.0.1:8000");
        // the test is failed when the text 'Les Catégories de Formation' is not found
        Assert.assertTrue(homePage.makeReaserche(jsonData));
    }

    @Test(priority = 8)
    public void sendEmailAdd() throws Exception {
        driver.navigate().to("http://127.0.0.1:8000");
        // the test is failed when the text 'Les Catégories de Formation' is not found
        Assert.assertTrue(homePage.sendMailForActuality());
    }

    @Test(priority = 9)
    public void addCardFomPannierPage() throws InterruptedException {
        driver.navigate().to("http://127.0.0.1:8000/panier/list");
        Assert.assertTrue(homePage.addCardFomPannierPage());
    }

    @AfterMethod
    public void screenShot(ITestResult result) throws InterruptedException {
        if (ITestResult.FAILURE == result.getStatus()) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            Thread.sleep(500);
            base.takeScreenShoot("src/assets/ScreenShoot/" + result.getName() + ".png");
        }
    }
}
