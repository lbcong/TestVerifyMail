/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckCapcha {

    @Autowired
    Utils utils;

    public boolean Check(WebDriver webDriver, String capchaText) {
        try {
            WebElement input_capcha = webDriver.findElement(By.xpath("//input[@type='text']"));
            WebElement button_submit = webDriver.findElement(By.xpath("//input[@type='submit' and @id='iSignupAction']"));
            input_capcha.sendKeys(capchaText);
            button_submit.click();
            // ktra nhap capcha dung hay khong
            utils.waitForPageLoaded(webDriver);
            input_capcha = webDriver.findElement(By.xpath("//button[@class='iconButton nextButton lowerButton']"));
            System.out.println("nhap than cong");
            return true;
        } catch (Exception ex) {
            // ip loi
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
