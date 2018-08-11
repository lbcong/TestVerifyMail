/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import ConstantVariable.Constant;
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
            Thread.sleep(2000);
            button_submit.click();
            // ktra nhap capcha dung hay khong
            while (true) {
                try {
                    input_capcha = webDriver.findElement(By.xpath(Constant.xpathCapcha));
                } catch (Exception e) {
                    break;
                }
            }
            if ("Creating your mailbox".equals(webDriver.getTitle())) {
                System.out.println("nhap than cong");
                return true;
            } else {
                System.out.println("nhap that bai");
                return false;
            }

        } catch (Exception ex) {
            // ip loi
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
