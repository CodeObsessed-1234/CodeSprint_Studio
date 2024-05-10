package com.codesprint.main_screen;

import com.codesprint.constants.Constants;
import com.codesprint.splash_screen.SplashScreen;
import com.codesprint.user_interface.EditorPage;

public class Main {
    public static void main(String[] args) {
        SplashScreen splashScreen=new SplashScreen();
        try{
            splashScreen.setVisible(true);
            Thread.sleep(Constants.SPLASH_SCREEN_TIME);
            splashScreen.dispose();
            new EditorPage();
        }catch (InterruptedException ignored){}
    }
}
