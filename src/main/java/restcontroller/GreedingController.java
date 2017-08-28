package restcontroller;

import ConstantVariable.Constant;
import Service.CreateWebdriver;

import java.io.IOException;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreedingController {

    public static WebDriver webDriver = null;
    @Autowired
    CreateWebdriver createWebdriver;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String greeding() {

        return "Hello ";
    }

    @RequestMapping(value = "/openbrowser", method = RequestMethod.GET)
    public String selenium() {
        String output = "";
        try {

            //test tren heroku
            webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
            //testVideo();
            openTestSite();
            login("admin", "12345");
            output = getText();

            //closeBrowser();
            return output;
        } catch (Exception e) {
            e.getMessage();
            return "loi : " + e.getMessage();
        }

    }

    public void login(String username, String Password) throws Exception, InterruptedException {

        Thread.sleep(1000);
        WebElement userName_editbox = webDriver.findElement(By.id("usr"));
        Thread.sleep(1000);
        WebElement password_editbox = webDriver.findElement(By.id("pwd"));
        Thread.sleep(1000);
        WebElement submit_button = webDriver.findElement(By.xpath("//input[@value='Login']"));
        Thread.sleep(1000);
        userName_editbox.sendKeys(username);
        Thread.sleep(1000);
        password_editbox.sendKeys(Password);
        Thread.sleep(1000);
        submit_button.click();

    }

    public String getText() throws IOException, InterruptedException {

        Thread.sleep(1000);
        String text = webDriver.findElement(By.xpath("//div[@id='case_login']/h3")).getText();
        return text;

    }

    public void closeBrowser() {
        webDriver.close();
    }

    public void openTestSite() {
        webDriver.navigate().to("http://testing-ground.scraping.pro/login");
    }

}
