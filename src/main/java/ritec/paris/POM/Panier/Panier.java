package ritec.paris.POM.Panier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ritec.paris.POM.Base;

public class Panier {
    WebDriver driver;
    Base base;
    By btndeleteCommande = By.xpath("//a[contains(@href, '/panier/remove-panier')]");
    By saveTemp = By.xpath("//a[contains(@href, '/panier/save-panier-temporaire')]");
    By couresSimilaire = By.linkText("Voir des cours similaires");
    By priceClasse = By.className("prix");
    By username = By.name("email");
    By password = By.name("password");
    By bntConnect = By.xpath("//button[@type='submit']");
    By emptyCourse = By.xpath("//p");
    By saveCourse = By.xpath("/html/body/main/div[2]/div/div/div/div[3]/div/div/div/div");

    public Panier(WebDriver driver, Base base) {
        this.driver = driver;
        this.base = base;
    }

    // click on btn 'supprimer'
    public Boolean resetCommande() {
        driver.findElement(btndeleteCommande).click();
        base.isVisible(priceClasse, 2);
        if (!driver.findElement(priceClasse).isDisplayed()) {
            return false;
        }
        return true;
    }

    // click on btn 'mettre de cote'
    public Boolean saveCourse() {
        driver.findElement(saveTemp).click();
        String vide = driver.findElement(emptyCourse).getText();
        if (!driver.findElement(saveCourse).isDisplayed()) {
            return false;
        }
        return true;
    }

    // click on btn 'coures similaire'
    public String coureSimilaire() {
        driver.findElement(couresSimilaire).click();
        return driver.getTitle();
    }

    public void auth(String userEmail, String pwd) {
        driver.navigate().to("http://127.0.0.1:8000/login");
        driver.findElement(username).sendKeys(userEmail);
        driver.findElement(password).sendKeys(pwd);
        driver.findElement(bntConnect).click();
    }
}
