package com.codesprint.constants;

import javax.swing.ImageIcon;
import java.util.Objects;

public class Constants {
    public static final int SPLASH_SCREEN_WIDTH=800;
    public static final int SPLASH_SCREEN_HEIGHT=500;
    public static final long SPLASH_SCREEN_TIME=5000L;
    public static final ImageIcon mainIcon=new ImageIcon(Objects.requireNonNull(Constants.class.getResource("/main_icon.jpg")));
}
