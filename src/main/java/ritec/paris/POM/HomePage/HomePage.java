package ritec.paris.POM.HomePage;

import java.time.Duration;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;

import ritec.paris.POM.Base;

public class HomePage {
    WebDriver driver;
    Base base;
    By searchFormation = By.xpath("//input[@name='recherche']");// input[@name='recherche']
    By btnSearch = By.className("btn-search");
    // Variable for formation
    By nosFormations = By.xpath("//a[@id='dropdownFormation']");
    By catFormations = By.xpath("//div/div/div/div/ul/li[1]/a");
    By resultRecherche = By.xpath("//*[@id='search-body']/div/div/div/h1");
    By domaineFormation = By.xpath("//*[@id='nosCours']/h1");
    By viewDetail = By.xpath("//a[contains(@href, 'course/details')]");
    By topCourse = By.xpath("//*[@id='pagination-course-top-course']/div/h1");
    By btnStripe = By.xpath("//*[@id='boutton-payer-stripe']/button");
    By btn_TopCourse = RelativeLocator.with(viewDetail).below(topCourse);
    By addToCardFromTopCours = RelativeLocator.with(By.id("showModal")).below(btn_TopCourse);
    By isDisplayDetail = By.xpath("//*[@id='titre']/div/div/h1");
    By allFormations = By.xpath("/html/body/header/nav/nav[3]/div[2]/ul/li/div/div[2]/a");
    By listAllCours = By.xpath("//*[@id='list-all-courses']/div/div/div");
    // Finacement space
    By mvtoFinacement = By.xpath("/html/body/header/nav/nav[3]/div[2]/ul/li[2]/a");
    By finacement = By.xpath("//a[contains(@href, '/finance#content')]");
    By topFomation = By.xpath("//*[@id='finance']/div/div[2]/div[2]/div/div/div/h1");

    // Send email add to get actuality
    By footer = By.xpath("//*[@id='footerContainer']/div");
    By inputEmail = By.id("email1");
    By btnSendEmail = By.name("submitNewsletter");
    By error = By.xpath("//div[2]/span");
    // Add to card from panier page
    By btn_addToCardPanier = By.xpath("//a[contains(@href, '/panier/add')]");

    public HomePage(WebDriver driver, Base base) {
        this.driver = driver;
        this.base = base;
    }

    // fill the input to make a reseach
    public Boolean makeReaserche(JSONObject jsonData) {
        driver.findElement(searchFormation).sendKeys(jsonData.get("params").toString());
        driver.findElement(btnSearch).click();
        base.isVisible(resultRecherche, 2);
        return driver.findElement(resultRecherche).isDisplayed();
    }

    // cliquer sur le sous-cathegories de "Nos Formation"
    public Boolean selectFormation() throws InterruptedException {
        base.isVisible(nosFormations, 2);
        new Actions(driver).moveToElement(driver.findElement(nosFormations)).perform();
        base.isClickable(catFormations, 2);
        driver.findElement(catFormations).click();
        return driver.findElement(resultRecherche).isDisplayed();
    }

    // voir tous les formation
    public Boolean viewAllFormation() {
        new Actions(driver).moveToElement(driver.findElement(nosFormations)).perform();
        base.isClickable(allFormations, 2);
        driver.findElement(allFormations).click();
        if (!driver.findElement(listAllCours).isDisplayed()) {
            return false;
        }
        return true;
    }

    public Boolean moveToFinacement() {
        new Actions(driver).moveToElement(driver.findElement(mvtoFinacement)).perform();
        base.isClickable(finacement, 2);
        driver.findElement(finacement).click();
        if (!driver.findElement(topFomation).isDisplayed()) {
            return false;
        }
        return true;
    }
    // view detail from top cours

    public Boolean viewDetail() {
        base.scrollDown(domaineFormation);
        driver.findElement(btn_TopCourse).click();
        if (!driver.findElement(isDisplayDetail).isDisplayed()) {
            return false;
        }
        return true;
    }

    public Boolean addToCard() {
        base.scrollDown(domaineFormation);
        driver.findElement(addToCardFromTopCours).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (!driver.findElement(btnStripe).isDisplayed()) {
            return false;
        }
        return true;
    }

    public Boolean sendMailForActuality() {
        base.scrollDown(footer);
        driver.findElement(inputEmail).sendKeys("nyraflet@gmail.boom");
        driver.findElement(btnSendEmail).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (driver.findElement(error).isDisplayed()) {
            return false;
        }
        return true;
    }

    // add cours into card from pannier page
    public Boolean addCardFomPannierPage() {
        base.scrollToBottom();
        driver.findElement(btn_addToCardPanier).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (!driver.findElement(btnStripe).isDisplayed()) {
            return false;
        }
        return true;
    }
}
