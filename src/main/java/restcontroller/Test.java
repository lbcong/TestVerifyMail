package restcontroller;

import Service.Dply_co;
import Service.GitHub;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import ConstantVariable.*;
import Service.CreateWebdriver;
import javax.servlet.ServletContext;

import org.openqa.selenium.interactions.Actions;

@RestController
public class Test {

    @Autowired
    GitHub gitHub;
    @Autowired
    Dply_co dply_co;
    WebDriver webDriver;
    @Autowired
    CreateWebdriver createWebdriver;

    @RequestMapping(value = "/create/{username}/{pass}", method = RequestMethod.GET)
    public String createVPS(
            @PathVariable("username") String username,
            @PathVariable("pass") String pass) {
        String output = "";
        try {
            webDriver = new ChromeDriver();
            //webDriver = new HtmlUnitDriver();
            dply_co.OpenDly(webDriver);
            gitHub.LoginGitHub(username, pass, webDriver);
            //dply_co.CreateServer(webDriver);
            //dply_co.getIP(webDriver);

            //closeBrowser();
            return output;
        } catch (Exception e) {
            e.getMessage();
            return "loi : " + e.getMessage();
        }

    }

    @Autowired
    ServletContext servletContext;

    // click nhieu vi tri
    @RequestMapping(value = "/manyclick", method = RequestMethod.GET)
    public String manyclick() {
        String output = "";
        try {
            String realpath = servletContext.getRealPath("");

            System.out.println("real"+realpath);
            
            System.out.println("use"+System.getProperty("user.dir"));

            WebDriver webDriver = createWebdriver.getGoogle(Constant.binaryGoogleWindows);

            webDriver.navigate().to("https://codeanywhere.com/");

            Actions myAction1 = new Actions(webDriver);
            myAction1.moveByOffset(471, 552).click().perform();

            myAction1.moveByOffset(-471, -552).perform();

            myAction1.moveByOffset(1031, 41).click().perform();

            myAction1.moveByOffset(-1031, -41).perform();

            myAction1.moveByOffset(914, 478).click().perform();

            webDriver.quit();
            return output;
        } catch (Exception e) {
            e.getMessage();
            return "loi : " + e.getMessage();
        }

    }

    @RequestMapping(value = "/getInfo/{username}/{pass}", method = RequestMethod.GET)
    public String getInfo(
            @PathVariable("username") String username,
            @PathVariable("pass") String pass) {
        String output = "";
        try {

            dply_co.OpenDly(webDriver);
            gitHub.LoginGitHub(username, pass, webDriver);
            output = dply_co.getIP(webDriver);

            //closeBrowser();
            return output;
        } catch (Exception e) {
            e.getMessage();
            return "loi : " + e.getMessage();
        }

    }

    public void closeBrowser() {
        webDriver.close();
    }

    public void openTestSite() {
        //webDriver.navigate().to("https://github.com/login");
        webDriver.navigate().to("https://dply.co/");
    }

}