/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restcontroller;

import Bean.SystemConfig;
import ConstantVariable.Constant;
import Service.Codenvy;
import Service.CreateWebdriver;
import Service.UploadServices;

import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KeepCodenvy {

    @Autowired
    UploadServices uploadServices;
    @Autowired
    Codenvy codenvy;
    @Autowired
    CreateWebdriver createWebdriver;

    private static WebDriver webDriver = null;

    @RequestMapping(value = "/loginCodenvy", method = RequestMethod.GET)
    public String inputvideo() {

        return "CodenvyLogin";
    }

    @RequestMapping(value = "/Codenvy", method = RequestMethod.GET)
    public String seleniumGoogle(
            @RequestParam(value = "user", required = true) String user,
            @RequestParam(value = "pass", required = true) String pass) {
        String output = "";
        try {

            Thread startThread = new Thread() {
                @Override
                public void run() {
                    try {

                        openBrowser(user, pass);
                    } catch (Exception e) {
                        e.getMessage();
                    }

                }
            };
            startThread.start();

        } catch (Exception e) {
            e.getMessage();
            return "loi : " + e.getMessage();
        }
        return "CodenvyLogin";
    }

    public void openBrowser(String user, String pass) {
        System.out.println("booo"+SystemConfig.statusRun);
        try {
            if (!SystemConfig.statusRun) {
                webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
                codenvy.LoginCodenvy(user, pass, webDriver);
                SystemConfig.statusRun = true;
            }

        } catch (Exception e) {
            System.out.println("openbrowser : " + e.getMessage());
        }

    }

}
