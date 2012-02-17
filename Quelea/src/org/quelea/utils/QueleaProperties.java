/* 
 * This file is part of Quelea, free projection software for churches.
 * Copyright (C) 2011 Michael Berry
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.utils;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.swing.UIManager;
import org.quelea.bible.Bible;

/**
 * Manages the properties specific to Quelea.
 * @author Michael
 */
public final class QueleaProperties extends Properties {

    public static final Version VERSION = new Version("0.5");
    private static final QueleaProperties INSTANCE = new QueleaProperties();
//    private static final Logger LOGGER = LoggerUtils.getLogger();

    /**
     * Load the properties from the properties file.
     */
    private QueleaProperties() {
        try {
            if (!getPropFile().exists()) {
                getPropFile().createNewFile();
            }
            try (FileReader reader = new FileReader(getPropFile())) {
                load(reader);
            }
        }
        catch (IOException ex) {
//            LOGGER.log(Level.SEVERE, "Couldn't load properties", ex);
//            ex.printStackTrace();
        }
    }

    /**
     * Get the properties file.
     * @return the properties file.
     */
    private File getPropFile() {
        return new File(getQueleaUserHome(), "quelea.properties");
    }

    /**
     * Save these properties to the file.
     */
    private void write() {
        try (FileWriter writer = new FileWriter(getPropFile())) {
            store(writer, "Auto save");
        }
        catch (IOException ex) {
//            LOGGER.log(Level.WARNING, "Couldn't store properties", ex);
        }
    }

    /**
     * Get the singleton instance of this class.
     * @return the instance.
     */
    public static QueleaProperties get() {
        return INSTANCE;
    }
    
    /**
     * Get the languages file that should be used as specified in the properties
     * file.
     * @return the languages file for the GUI.
     */
    public File getLanguageFile() {
        return new File("languages", getProperty("language.file", "gb.lang"));
    }
    
    /**
     * Get the look and feel property that should be used as specified in the 
     * properties file.
     * @return the look and feel string name.
     */
    public String getLaf() {
        String laf = getProperty("laf", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        if(laf.trim().equalsIgnoreCase("System")) {
            return UIManager.getSystemLookAndFeelClassName();
        }
        else {
            return laf;
        }
    }

    /**
     * Get the Quelea home directory in the user's directory.
     * @return the Quelea home directory.
     */
    public static File getQueleaUserHome() {
        File ret = new File(new File(System.getProperty("user.home")), ".quelea");
        if (!ret.exists()) {
            ret.mkdir();
        }
        return ret;
    }
    
    /**
     * Determine whether we should phone home at startup with anonymous 
     * information. Simply put phonehome=false in the properties file to disable
     * phonehome.
     * @return true if we should phone home, false otherwise.
     */
    public boolean getPhoneHome() {
        return Boolean.parseBoolean(getProperty("phonehome", "true"));
    }

    /**
     * Get the directory used for storing the bibles.
     * @return the bibles directory.
     */
    public File getBibleDir() {
        return new File(getProperty("bibles.dir", "bibles"));
    }

    /**
     * Get the extension used for quelea schedules.
     * @return the extension used for quelea schedules.
     */
    public String getScheduleExtension() {
        return getProperty("quelea.schedule.extension", "qsch");
    }

    /**
     * Get the extension used for quelea song packs.
     * @return the extension used for quelea song packs.
     */
    public String getSongPackExtension() {
        return getProperty("quelea.songpack.extension", "qsp");
    }

    /**
     * Get the number of the screen used for the control screen. This is the screen that the main Quelea operator window
     * will be displayed on.
     * @return the control screen number.
     */
    public int getControlScreen() {
        return Integer.parseInt(getProperty("control.screen", "0"));
    }

    /**
     * Set the control screen output.
     * @param screen the number of the screen to use for the output.
     */
    public void setControlScreen(int screen) {
        setProperty("control.screen", Integer.toString(screen));
        write();
    }
    
    /**
     * Get the one line mode.
     * @return true if one line mode should be enabled, false otherwise.
     */
    public boolean getOneLineMode() {
        return Boolean.parseBoolean(getProperty("one.line.mode", "false"));
    }
    
    /**
     * Set the one line mode property.
     * @param val the value of the one linde mode.
     */
    public void setOneLineMode(boolean val) {
        setProperty("one.line.mode", Boolean.toString(val));
        write();
    }

    /**
     * Get the number of the projector screen. This is the screen that the projected output will be displayed on.
     * @return the projector screen number.
     */
    public int getProjectorScreen() {
        return Integer.parseInt(getProperty("projector.screen", "1"));
    }

    /**
     * Set the control screen output.
     * @param screen the number of the screen to use for the output.
     */
    public void setProjectorScreen(int screen) {
        setProperty("projector.screen", Integer.toString(screen));
        write();
    }

    /**
     * Get the maximum number of characters allowed on any one line of projected text. If the line is longer than this,
     * it will be split up intelligently.
     * @return the maximum number of characters allowed on any one line of projected text.
     */
    public int getMaxChars() {
        return Integer.parseInt(getProperty("max.chars", "30"));
    }

    /**
     * Set the max chars value.
     * @param maxChars the maximum number of characters allowed on any one line of projected text.
     */
    public void setMaxChars(int maxChars) {
        setProperty("max.chars", Integer.toString(maxChars));
        write();
    }
    
    /**
     * Get the custom projector co-ordinates.
     * @return the co-ordinates.
     */
    public Rectangle getProjectorCoords() {
        String[] prop = getProperty("projector.coords", "0,0,0,0").trim().split(",");
        return new Rectangle(Integer.parseInt(prop[0]),
                Integer.parseInt(prop[1]),
                Integer.parseInt(prop[2]),
                Integer.parseInt(prop[3]));
    }
    
    /**
     * Set the custom projector co-ordinates.
     * @param coords the co-ordinates to set.
     */
    public void setProjectorCoords(Rectangle coords) {
        String rectStr = Integer.toString((int)coords.getX())
                +","+Integer.toString((int)coords.getY())
                +","+Integer.toString((int)coords.getWidth())
                +","+Integer.toString((int)coords.getHeight());
                
        setProperty("projector.coords", rectStr);
        write();
    }
    
    /**
     * Determine if the projector mode is set to manual co-ordinates or a 
     * screen number.
     * @return true if it's set to manual co-ordinates, false if it's a screen
     * number.
     */
    public boolean isProjectorModeCoords() {
        return "coords".equals(getProperty("projector.mode"));
    }
    
    /**
     * Set the projector mode to be manual co-ordinates.
     */
    public void setProjectorModeCoords() {
        setProperty("projector.mode", "coords");
        write();
    }
    
    /**
     * Set the projector mode to be a screen number.
     */
    public void setProjectorModeScreen() {
        setProperty("projector.mode", "screen");
        write();
    }
    
    /**
     * Get the number of the stage screen. This is the screen that the
     * projected output will be displayed on.
     *
     * @return the stage screen number.
     */
    public int getStageScreen() {
        return Integer.parseInt(getProperty("stage.screen", "1"));
    }
    
    /**
     * Set the stage screen output.
     *
     * @param screen the number of the screen to use for the output.
     */
    public void setStageScreen(int screen) {
        setProperty("stage.screen", Integer.toString(screen));
        write();
    }
    
    /**
     * Get the custom stage screen co-ordinates.
     * @return the co-ordinates.
     */
    public Rectangle getStageCoords() {
        String[] prop = getProperty("stage.coords", "0,0,0,0").trim().split(",");
        return new Rectangle(Integer.parseInt(prop[0]),
                Integer.parseInt(prop[1]),
                Integer.parseInt(prop[2]),
                Integer.parseInt(prop[3]));
    }
    
    /**
     * Set the custom stage screen co-ordinates.
     * @param coords the co-ordinates to set.
     */
    public void setStageCoords(Rectangle coords) {
        String rectStr = Integer.toString((int)coords.getX())
                +","+Integer.toString((int)coords.getY())
                +","+Integer.toString((int)coords.getWidth())
                +","+Integer.toString((int)coords.getHeight());
                
        setProperty("stage.coords", rectStr);
        write();
    }
    
    /**
     * Determine if the stage mode is set to manual co-ordinates or a 
     * screen number.
     * @return true if it's set to manual co-ordinates, false if it's a screen
     * number.
     */
    public boolean isStageModeCoords() {
        return "coords".equals(getProperty("stage.mode"));
    }
    
    /**
     * Set the stage mode to be manual co-ordinates.
     */
    public void setStageModeCoords() {
        setProperty("stage.mode", "coords");
        write();
    }
    
    /**
     * Set the stage mode to be a screen number.
     */
    public void setStageModeScreen() {
        setProperty("stage.mode", "screen");
        write();
    }

    /**
     * Get the minimum number of lines that should be displayed on each page. This purely applies to font sizes, the
     * font will be adjusted so this amount of lines can fit on. This stops small lines becoming huge in the preview
     * window rather than displaying normally.
     * @return the minimum line count.
     */
    public int getMinLines() {
        return Integer.parseInt(getProperty("min.lines", "10"));
    }

    /**
     * Set the min lines value.
     * @param minLines the minimum line count.
     */
    public void setMinLines(int minLines) {
        setProperty("min.lines", Integer.toString(minLines));
        write();
    }

    /**
     * Determine whether the single monitor warning should be shown (this warns the user they only have one monitor
     * installed.)
     * @return true if the warning should be shown, false otherwise.
     */
    public boolean showSingleMonitorWarning() {
        return Boolean.parseBoolean(getProperty("single.monitor.warning", "true"));
    }

    /**
     * Set whether the single monitor warning should be shown.
     * @param val true if the warning should be shown, false otherwise.
     */
    public void setSingleMonitorWarning(boolean val) {
        setProperty("single.monitor.warning", Boolean.toString(val));
        write();
    }

    /**
     * Get the URL to download Quelea.
     * @return the URL to download Quelea.
     */
    public String getDownloadLocation() {
        return getProperty("download.location", "http://code.google.com/p/quelea-projection/downloads/list");
    }

    /**
     * Get the URL to the Quelea website.
     * @return the URL to the Quelea website.
     */
    public String getWebsiteLocation() {
        return getProperty("website.location", "http://www.quelea.org/");
    }

    /**
     * Get the URL to the Quelea discussion forum.
     * @return the URL to the Quelea discussion forum.
     */
    public String getDiscussLocation() {
        return getProperty("discuss.location", "https://groups.google.com/group/quelea-discuss");
    }

    /**
     * Get the URL used for checking the latest version.
     * @return the URL used for checking the latest version.
     */
    public String getUpdateURL() {
        return getProperty("update.url", "http://code.google.com/p/quelea-projection/");
    }

    /**
     * Determine whether we should check for updates each time the program starts.
     * @return true if we should check for updates, false otherwise.
     */
    public boolean checkUpdate() {
        return Boolean.parseBoolean(getProperty("check.update", "true"));
    }

    /**
     * Set whether we should check for updates each time the program starts.
     * @param val true if we should check for updates, false otherwise.
     */
    public void setCheckUpdate(boolean val) {
        setProperty("check.update", Boolean.toString(val));
        write();
    }

    /**
     * Determine whether the first letter of all displayed lines should be a capital.
     * @return true if it should be a capital, false otherwise.
     */
    public boolean checkCapitalFirst() {
        return Boolean.parseBoolean(getProperty("capital.first", "true"));
    }

    /**
     * Set whether the first letter of all displayed lines should be a capital.
     * @param val true if it should be a capital, false otherwise.
     */
    public void setCapitalFirst(boolean val) {
        setProperty("capital.first", Boolean.toString(val));
        write();
    }

    /**
     * Determine whether the song info text should be displayed.
     * @return true if it should be a displayed, false otherwise.
     */
    public boolean checkDisplaySongInfoText() {
        return Boolean.parseBoolean(getProperty("display.songinfotext", "true"));
    }

    /**
     * Set whether the song info text should be displayed.
     * @param val true if it should be displayed, false otherwise.
     */
    public void setDisplaySongInfoText(boolean val) {
        setProperty("display.songinfotext", Boolean.toString(val));
        write();
    }

    /**
     * Get the default bible to use.
     * @return the default bible.
     */
    public String getDefaultBible() {
        return getProperty("default.bible");
    }

    /**
     * Set the default bible.
     * @param bible the default bible.
     */
    public void setDefaultBible(Bible bible) {
        setProperty("default.bible", bible.getName());
        write();
    }

    /**
     * Get the maximum number of verses allowed in any one bible reading. Too many will crash the program!
     * @return the maximum number of verses allowed.
     */
    public int getMaxVerses() {
        return Integer.parseInt(getProperty("max.verses", "100"));
    }

    /**
     * Set the maximum number of verses allowed in any one bible reading. Too many will crash the program!
     * @param val the maximum number of verses allowed.
     */
    public void setMaxVerses(int val) {
        setProperty("max.verses", Integer.toString(val));
        write();
    }

    /**
     * Get the colour used to signify an active list.
     * @return the colour used to signify an active list.
     */
    public Color getActiveSelectionColor() {
        String[] color = getProperty("active.selection.color", "23,130,100").split(",");
        return new Color(Integer.parseInt(color[0].trim()),
                Integer.parseInt(color[1].trim()),
                Integer.parseInt(color[2].trim()));
    }
    
    public static void main(String[] args) {
        System.out.println(INSTANCE.getProperty("active.selection.color"));
    }

    /**
     * Get the thickness of the outline to use for displaying the text.
     * @return the outline thickness in pixels.
     */
    public int getOutlineThickness() {
        return Integer.parseInt(getProperty("outline.thickness", "2"));
    }

    /**
     * Set the outline thickness.
     * @param px the outline thickness in pixels.
     */
    public void setOutlineThickness(int px) {
        setProperty("outline.thickness", Integer.toString(px));
        write();
    }

    /**
     * Get the notice box height (px).
     * @return the notice box height.
     */
    public int getNoticeBoxHeight() {
        return Integer.parseInt(getProperty("notice.box.height", "40"));
    }

    /**
     * Set the notice box height (px).
     * @param height the notice box height.
     */
    public void setNoticeBoxHeight(int height) {
        setProperty("notice.box.height", Integer.toString(height));
        write();
    }

    /**
     * Get the notice box speed.
     * @return the notice box speed.
     */
    public int getNoticeBoxSpeed() {
        return Integer.parseInt(getProperty("notice.box.speed", "8"));
    }

    /**
     * Set the notice box speed.
     * @param speed the notice box speed.
     */
    public void setNoticeBoxSpeed(int speed) {
        setProperty("notice.box.speed", Integer.toString(speed));
        write();
    }
    
    /**
     * Get the specially treated words that are auto-capitalised by the song
     * importer when deciding how to un-caps-lock a line of text.
     * @return the array of God words, separated by commas in the properties
     * file.
     */
    public String[] getGodWords() {
        return getProperty("god.words",
                "god,God,jesus,Jesus,christ,Christ,you,You,he,He,lamb,Lamb,"
                + "lord,Lord,him,Him,son,Son,i,I,his,His,your,Your,king,King,"
                + "saviour,Saviour,savior,Savior,majesty,Majesty,alpha,Alpha,omega,Omega") //Yeah.. default testing properties.
                .trim().split(",");
    }
}
