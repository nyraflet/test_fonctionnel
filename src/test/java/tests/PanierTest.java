package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ritec.paris.POM.Base;
import ritec.paris.POM.HomePage.HomePage;
import ritec.paris.POM.Panier.Panier;

public class PanierTest {
    WebDriver driver;
    Panier panier;
    Base base;
    HomePage home;

    @BeforeTest()
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://127.0.0.1:8000");
        base = new Base(driver);
        base.acceptCoukie();
        driver.navigate().refresh();
        panier = new Panier(driver, base);
        home = new HomePage(driver, base);
    }

    @AfterTest

    public void closeDriver() {
        driver.quit();
    }

    @Test(priority = 3)
    public void reseteCommande() {
        driver.navigate().to("http://127.0.0.1:8000/panier/list");
        // fill card, this is method from Home POM
        home.addCardFomPannierPage();
        Assert.assertTrue(panier.resetCommande());
    }

    @Test(priority = 4)
    public void saveCourse() {
        panier.auth("martin@ritec.mg", "tata");
        driver.navigate().to("http://127.0.0.1:8000/panier/list");
        // fill card, this is method from Home POM
        home.addCardFomPannierPage();
        Assert.assertTrue(panier.saveCourse());
    }

    @Test(priority = 5)
    public void viewCouseSimilaire() {
        driver.navigate().to("http://127.0.0.1:8000/panier/list");
        // fill card, this is method from Home POM
        home.addCardFomPannierPage();
        Assert.assertEquals(panier.coureSimilaire(), "Nos formations");
    }

    @AfterMethod
    public void screenShot(ITestResult result) throws InterruptedException {
        if (ITestResult.FAILURE == result.getStatus()) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            Thread.sleep(500);
            base.takeScreenShoot("src/assets/ScreenShoot/" + result.id() + ".png");
        }
    }
}
