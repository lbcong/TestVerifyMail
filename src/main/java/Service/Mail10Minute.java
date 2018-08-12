/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Utils.Utils;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mail10Minute {
    @Autowired
    Utils utils;

    public String getCode(WebDriver webDriver) {
        try {

            boolean flag_wait = false;
            WebElement element = null;
            List<WebElement> listElement = null;
            Thread.sleep(10000);
            // wait
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//table[@id='maillist']");
            }
            flag_wait = false;
            listElement = webDriver.findElements(By.xpath("//table[@id='maillist']//tbody//tr"));
            listElement.get(1).click();
            // wait
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//td[contains(text(),'Security code: ')]");
            }

            element = webDriver.findElement(By.xpath("//td[contains(text(),'Security code: ')]//span"));

            // tro ve tab cu
            return element.getText();
        } catch (Exception e) {
        }
        return null;
    }

    public String getAddress(WebDriver webDriver) {
        try {
            boolean flag_wait = false;
            WebElement element = null;


            // wait
            while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//i[@class='fa fa-user-secret fa-fw fa-lg ']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//i[@class='fa fa-user-secret fa-fw fa-lg ']"));
            element.click();
             while (!flag_wait) {
                flag_wait = utils.waitForPresence(webDriver, 5000, "//input[@id='fe_text']");
            }
            flag_wait = false;
            element = webDriver.findElement(By.xpath("//input[@id='fe_text']"));
            String mail = element.getAttribute("value");
            return mail;
        } catch (Exception e) {
        }
        return null;
    }
}
