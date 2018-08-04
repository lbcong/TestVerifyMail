/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Cookie;

/**
 *
 * @author Alex
 */
public class Doc_file_kieu_binary {

    public static Set<Cookie> readFileBinary(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Set<Cookie> lists = (Set<Cookie>) ois.readObject();
            ois.close();
            return lists;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}
