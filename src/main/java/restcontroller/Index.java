/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restcontroller;

import ConstantVariable.Constant;
import Service.Codenvy;
import Service.CreateWebdriver;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Index {

    private static boolean flag = false;
    @Autowired
    CreateWebdriver createWebdriver;
    @Autowired
    Codenvy codenvy;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String greeding() {
//        getInfo();
        return "index";
    }

    public String getInfo() {
        String output = "";
        try {
            if (!flag) {

                Thread startThread = new Thread() {
                    @Override
                    public void run() {
                        flag = true;
                        try {
                            WebDriver webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
                            codenvy.LoginCodenvy(Constant.userCodenvy, Constant.passCodenvy, webDriver);
                        } catch (Exception e) {
                        }

                    }
                };
                startThread.start();

            }

            return output;
        } catch (Exception e) {
            e.getMessage();
            return "loi : " + e.getMessage();
        }

    }
}
