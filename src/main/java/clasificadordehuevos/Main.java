package clasificadordehuevos;

import org.opencv.core.Core;

import javax.swing.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Main{

    public static void main(String[] args) {
        loadLibraries();
        /*
        clasificacionhuevos c = new clasificacionhuevos();
        double[] b = c.crispInference(new double[] {55,230});
        System.out.println(b[0]);
        */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                Principal p = new Principal();
                p.load();

            }
        });
    }
    private static void loadLibraries() {

        try {
            InputStream in = null;
            File fileOut = null;
            String osName = System.getProperty("os.name");
            String opencvpath = System.getProperty("user.dir");
            if(osName.startsWith("Windows")) {

                    opencvpath=opencvpath+"\\opencv\\x64\\";

            }
            else if(osName.equals("Mac OS X")){
                opencvpath = opencvpath+"Your path to .dylib";
            }
            System.load(opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load opencv native library", e);
        }
    }
}