/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Codenvy {
    @Autowired
    Utils utils;
    @Autowired
    Mail10Minute mail10Minute;
    @Autowired
    GetTextFromGit getTextFromGit;

    public boolean Start(WebDriver webDriver) {
        List<String> lists = null;
        try {
            lists = getTextFromGit.getStringFromGithubRaw("https://raw.githubusercontent.com/lbcong/SaveFileTemp/master/AccountVerifyOutLook.txt");
        } catch (IOException ex) {
            Logger.getLogger(Codenvy.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean flag_wait = false;
//        Zxcv123123
//        String str_password = "Ahfweh123@#$";
        String str_password = "Zxcv123123";
        String str_email = lists.get(0);
        try {
            WebElement element = null;
            Select select = null;
            List<WebElement> listElement = null;
            JavascriptExecutor js = ((JavascriptExecutor) webDriver);
            webDriver.get("https://outlook.live.com/owa/");
            String currentWindow = webDriver.getWindowHandle();
            // mo 1 tab cho mail 10 minute
            js.executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1));
            webDriver.navigate().to("https://10minutemail.net");
            // tro ve tab trc
            webDriver.switchTo().window(currentWindow);

            // wait
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//a[@class='linkButtonSigninHeader']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//a[@class='linkButtonSigninHeader']"));
            element.click();
            // wait
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@type='email']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//input[@type='email']"));
            element.sendKeys(str_email);
            element = webDriver.findElement(By.xpath("//input[@type='submit']"));
            element.click();
            // wait
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@type='password' and @name='passwd']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//input[@type='password' and @name='passwd']"));
            element.sendKeys(str_password);
            element = webDriver.findElement(By.xpath("//input[@type='submit']"));
            element.click();
            // wait
            utils.waitForUrlLoading(webDriver, "//a[@id='idA_PWD_ForgotPassword']");

            boolean status = false;
            try {
                element = webDriver.findElement(By.xpath("//span[@class='signinTxt']"));
                status = true;
            } catch (Exception e) {
                status = false;
            }

            if (status) {
                while (!flag_wait) {
                    flag_wait = utils.waitForPresence(webDriver, 5000, "//span[@class='signinTxt']");
                }
                flag_wait = false;
                select = new Select(webDriver.findElement(By.xpath("//select[@id='selTz']")));
                select.selectByIndex(3);

                select = new Select(webDriver.findElement(By.xpath("//select[@name='lcid' and @class='languageInputText']")));
                listElement = select.getOptions();

                int index = 0;
                for (int i = 0; i < listElement.size(); i++) {
                    if ("1066".equals(listElement.get(i).getAttribute("value"))) {
                        index = i;
                        break;
                    }
                }
                select.selectByIndex(index);
                WebElement signinTxt = webDriver.findElement(By.xpath("//span[@class='signinTxt']"));
                signinTxt.click();
            }
             // wait doi xuat hien thiet dat
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//div[@title='Thiết đặt']");
            }
            flag_wait = false;

            webDriver.get("https://outlook.live.com/mail/options/mail/accounts");

            //wait 
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//button[contains(@class,'ms-Link') and contains(text(), 'Quản lý hoặc chọn biệt danh chính')]");
            }
            flag_wait = false;

            utils.waitForPageLoaded(webDriver);

            element = webDriver.findElement(By.xpath("//button[contains(@class,'ms-Link') and contains(text(),'Quản lý hoặc chọn biệt danh chính')]"));

            if (utils.isClickable(element, webDriver)) {
                element.click();
            } else {
                while (!flag_wait) {
                    flag_wait = utils.waitForPresence(webDriver, 5000, "//div[contains(@class,'ms-Panel-navigation')]//button");
                }
                flag_wait = false;

                element = webDriver.findElement(By.xpath("//div[contains(@class,'ms-Panel-navigation')]//button"));
                element.click();
                element = webDriver.findElement(By.xpath("//button[contains(@class,'ms-Link') and contains(text(),'Quản lý hoặc chọn biệt danh chính')]"));
                element.click();
            }

//            while (!flag_wait) {
//                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@type='password' and @name='passwd']");
//            }
//            flag_wait = false;
            try {
                element = webDriver.findElement(By.xpath("//input[@type='password' and @name='passwd']"));
                element.sendKeys(str_password);
                element = webDriver.findElement(By.xpath("//input[@type='submit']"));
                element.click();
            } catch (Exception ex) {

            }

            // wait chon xac thuc bang email
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//select[@id='iProofOptions']");
            }
            flag_wait = false;
            select = new Select(webDriver.findElement(By.xpath("//select[@id='iProofOptions']")));
            select.selectByIndex(1);

            // get add mail 10 minute
            webDriver.switchTo().window(tabs.get(1));
            String mail = mail10Minute.getAddress(webDriver);
            webDriver.switchTo().window(currentWindow);
            // input mail
            element = webDriver.findElement(By.xpath("//input[@id='EmailAddress']"));
            element.sendKeys(mail);
            element = webDriver.findElement(By.xpath("//input[@type='submit']"));
            element.click();

            // wait doi de nhap code ben mail sang
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@id='iOttText']");
            }
            flag_wait = false;
            // get code tu mail 10
            webDriver.switchTo().window(tabs.get(1));
            String code = mail10Minute.getCode(webDriver);
            webDriver.switchTo().window(currentWindow);
            element = webDriver.findElement(By.xpath("//input[@id='iOttText']"));
            element.sendKeys(code);
            element = webDriver.findElement(By.xpath("//input[@type='submit']"));
            element.click();

            // wait doi xac thuc lan 2
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//div[@class='table-row']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//div[@class='table-row']"));
            element.click();

            // wait doi hien thi de nhap email xac thuc lan 2
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@id='idTxtBx_SAOTCS_ProofConfirmation']");
            }
            flag_wait = false;
            // get add mail 10 minute

            element = webDriver.findElement(By.xpath("//input[@id='idTxtBx_SAOTCS_ProofConfirmation']"));
            element.sendKeys(mail);
            element = webDriver.findElement(By.xpath("//input[@type='submit']"));
            element.click();

            // wait doi nhap ma xac thuc lan 2
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@id='idTxtBx_SAOTCC_OTC']");
            }
            flag_wait = false;
            // get code tu mail 10 lan 2
            webDriver.switchTo().window(tabs.get(1));
            code = mail10Minute.getCode(webDriver);
            webDriver.switchTo().window(currentWindow);
            element = webDriver.findElement(By.xpath("//input[@id='idTxtBx_SAOTCC_OTC']"));
            element.sendKeys(code);
            element = webDriver.findElement(By.xpath("//input[@type='submit']"));
            element.click();

            // wait hien thi man hinh no thank
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//a[@id='iCancel']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//a[@id='iCancel']"));
            element.click();

            // wait hien thi man hinh them mail
            Random rd = new Random();
            for (int i = 0; i < 10; i++) {
                utils.waitForPageLoaded(webDriver);
                while (!flag_wait) {
                    flag_wait = utils.waitForPresence(webDriver, 5000, "//a[@id='idAddAliasLink']");
                }
                flag_wait = false;
                element = webDriver.findElement(By.xpath("//a[@id='idAddAliasLink']"));
                Thread.sleep(3000);
                element.click();
                // wait tao mail gia dung vong for de tao
                while (!flag_wait) {
                    flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@id='AssociatedIdLive']");
                }
                element = webDriver.findElement(By.xpath("//input[@id='AssociatedIdLive']"));
                element.sendKeys("fwejfjwerefjf" + rd.nextInt(9999));
                element = webDriver.findElement(By.xpath("//input[@type='submit']"));
                Thread.sleep(1000);
                element.click();
            }

            webDriver.quit();
            return true;
        } catch (Exception e) {
            System.out.println("exception:" + e.getMessage());
            webDriver.quit();
            return false;
        }
    }

}
