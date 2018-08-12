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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Index {

    private static boolean flag = false;
    @Autowired
    CreateWebdriver createWebdriver;
    @Autowired
    Codenvy codenvy;
    WebDriver webDriver;

    @RequestMapping(value = "/beginVerify", method = RequestMethod.GET)
    public @ResponseBody
    String getCapTypeBase64() {
        try {
            if (!flag) {
                Thread startThread = new Thread() {
                    @Override
                    public void run() {
                        flag = true;
                        try {
                            webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
                            codenvy.Start(webDriver);
                        } catch (Exception e) {
                        }
                    }
                };
                startThread.start();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "running";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
