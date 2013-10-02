
package org;

import java.util.*;
import java.io.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;

/**
 * The <code>FileBrowser</code> custom component lets the user list files and
 * directories. It's uses FileConnection Optional Package (JSR 75). The FileConnection
 * Optional Package APIs give J2ME devices access to file systems residing on mobile devices,
 * primarily access to removable storage media such as external memory cards.
 * @author breh
 */

public class FileBrowserMod extends List implements CommandListener {

    /**
     * Command fired on file selection.
     */
    public static final Command SELECT_FILE_COMMAND = new Command("Select", Command.OK, 1);
    private String p;
    private String currDirName;
    private String currFile;
    private Image dirIcon;
    private String pasteURL;
    private boolean copycut;
    private Image fileIcon;
    private Image[] iconList;
    private Command back = new Command("Back",Command.BACK,0);
    private CommandListener commandListener;
    private Command newfolder = new Command("New Folder",Command.OK,0);
    private Command paste = new Command("Paste",Command.OK,0);

    /* special string denotes upper directory */
    private static final String UP_DIRECTORY = "..";

    /* special string that denotes upper directory accessible by this browser.
     * this virtual directory contains all roots.
     */
    private static final String MEGA_ROOT = "/";

    /* separator string as defined by FC specification */
    private static final String SEP_STR = "/";

    /* separator character as defined by FC specification */
    private static final char SEP = '/';

    private Display display;

    private String selectedURL;

    private String filter = null;

    private String title;

    /**
     * Creates a new instance of FileBrowser for given <code>Display</code> object.
     * @param display non null display object.
     */
    public FileBrowserMod(Display display,String url,String url2,boolean bool) {
        super("", IMPLICIT);

        currDirName = url;
        pasteURL=url2;
        copycut=bool;
        this.display = display;
        super.setCommandListener(this);
        setSelectCommand(SELECT_FILE_COMMAND);
        addCommand(back);
        addCommand(newfolder);
        addCommand(paste);

        try {
            dirIcon = Image.createImage("/org/netbeans/microedition/resources/dir.png");
        } catch (IOException e) {
            dirIcon = null;
        }
        try {
            fileIcon = Image.createImage("/org/netbeans/microedition/resources/file.png");
        } catch (IOException e) {
            fileIcon = null;
        }
        iconList = new Image[]{fileIcon, dirIcon};

        showDir();
    }

    private void showDir() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    showCurrDir();
                } catch (SecurityException e) {
                    Alert alert = new Alert("Error", "You are not authorized to access the restricted API", null, AlertType.ERROR);
                    alert.setTimeout(2000);
                    display.setCurrent(alert, FileBrowserMod.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Indicates that a command event has occurred on Displayable d.
     * @param c a <code>Command</code> object identifying the command. This is either
     * one of the applications have been added to <code>Displayable</code> with <code>addCommand(Command)</code>
     * or is the implicit <code>SELECT_COMMAND</code> of List.
     * @param d the <code>Displayable</code> on which this event has occurred
     */
    public void commandAction(Command c, Displayable d) {
        if (c.equals(SELECT_FILE_COMMAND)) {
            List curr = (List) d;
            currFile = curr.getString(curr.getSelectedIndex());
            new Thread(new Runnable() {

                public void run() {
                    if (currFile.endsWith(SEP_STR) || currFile.equals(UP_DIRECTORY)) {
                        openDir(currFile);
                    } else {
                        //switch To Next
                        doDismiss();
                    }
                }
            }).start();
        }  else if (c.equals(back)) {
            int end = currDirName.length();
            String newname = parsepre(currDirName.substring(0,end-2));
            if(newname == "")
            {
                currDirName="/";
                }
                else
                {
                currDirName = newname;}
              showDir();
        }
        else if (c.equals(newfolder)){

            String newname="New Folder";
            try {
                int i=0;

                while(true)
                {
                if(i!=0){ newname="New Folder"+"("+p+")";}
                FileConnection fc = (FileConnection) Connector.open("file:///"+currDirName+newname);
            if (!fc.exists()) {
                    fc.mkdir();
                    Alert al1 = new Alert("alert","Folder Created.",null,null);
                    al1.setTimeout(Alert.FOREVER);
                    display.setCurrent(al1);
                    break;
                }
                else{
                    p=Integer.toString(i);
                    i++;
                    fc.close();}
                }
            }
            catch(Exception e){

            }
            showDir();
        }

        else if (c.equals(paste)){
           
           paste(pasteURL);
           if(pasteURL != null && copycut == false)
           {
               deletefolder(pasteURL.substring(8));
           }
            showDir();
        }

        else {
            commandListener.commandAction(c, d);
        }
    }

    /**
     * Sets component's title.
     *  @param title component's title.
     */
    public String currdirectory()
    {
        return currDirName;
    }


    public String selectedop(Displayable d)
    {

        List curr = (List) d;
        currFile = curr.getString(curr.getSelectedIndex());
        return currFile; 


    }


    public void setTitle(String title) {
        this.title = title;
        super.setTitle(title);
    }

    /**
     * Show file list in the current directory .
     */
    private void showCurrDir() {
        if (title == null) {
            super.setTitle(currDirName);
        }
        Enumeration e = null;
        FileConnection currDir = null;

        deleteAll();
        if (MEGA_ROOT.equals(currDirName)) {
            append(UP_DIRECTORY, dirIcon);
            e = FileSystemRegistry.listRoots();
        } else {
            try {
                currDir = (FileConnection) Connector.open("file:///" + currDirName);
                e = currDir.list();
            } catch (IOException ioe) {
            }
            append(UP_DIRECTORY, dirIcon);
        }

        if (e == null) {
            try {
                currDir.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }

        while (e.hasMoreElements()) {
            String fileName = (String) e.nextElement();
            if (fileName.charAt(fileName.length() - 1) == SEP) {
                // This is directory
                append(fileName, dirIcon);
            } else {
                // this is regular file
                if (filter == null || fileName.indexOf(filter) > -1) {
                    append(fileName, fileIcon);
                }
            }
        }

        if (currDir != null) {
            try {
                currDir.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void openDir(String fileName) {
        /* In case of directory just change the current directory
         * and show it
         */
        if (currDirName.equals(MEGA_ROOT)) {
            if (fileName.equals(UP_DIRECTORY)) {
                // can not go up from MEGA_ROOT
                return;
            }
            currDirName = fileName;
        } else if (fileName.equals(UP_DIRECTORY)) {
            // Go up one directory
            // TODO use setFileConnection when implemented
            int i = currDirName.lastIndexOf(SEP, currDirName.length() - 2);
            if (i != -1) {
                currDirName = currDirName.substring(0, i + 1);
            } else {
                currDirName = MEGA_ROOT;
            }
        } else {
            currDirName = currDirName + fileName;
        }
        showDir();
    }

    /**
     * Returns selected file as a <code>FileConnection</code> object.
     * @return non null <code>FileConection</code> object
     */
    public FileConnection getSelectedFile() throws IOException {
        FileConnection fileConnection = (FileConnection) Connector.open(selectedURL);
        return fileConnection;
    }

    /**
     * Returns selected <code>FileURL</code> object.
     * @return non null <code>FileURL</code> object
     */
    public String getSelectedFileURL() {
        return selectedURL;
    }

    /**
     * Sets the file filter.
     * @param filter file filter String object
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Returns command listener.
     * @return non null <code>CommandListener</code> object
     */
    protected CommandListener getCommandListener() {
        return commandListener;
    }

    /**
     * Sets command listener to this component.
     * @param commandListener <code>CommandListener</code> to be used
     */
    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    private void doDismiss() {
        selectedURL = "file:///" + currDirName + currFile;
        CommandListener commandListener = getCommandListener();
        if (commandListener != null) {
            commandListener.commandAction(SELECT_FILE_COMMAND, this);
        }
    }

         public static String parsepre(String str) {
        int pos = str.lastIndexOf('/');
        if (pos == -1) {
          return "";
        }
        return str.substring(0,pos+1);
        }


        public static String parse(String str) {
        int pos = str.lastIndexOf('/');
        if (pos == -1) {
          return "";
      }
      return str.substring(pos + 1);
  }

     private void paste(String pasteurl)
    {
        String temp1,temp2;
         //Alert alert=new Alert("alert",pasteURL,null,null);
         //alert.setTimeout(Alert.FOREVER);
         //display.setCurrent(alert);
          try {
                    FileConnection fci = (FileConnection) Connector.open(pasteurl,Connector.READ_WRITE);
                   

                    if(fci.isDirectory())
                    {
                        Enumeration e =fci.list();
                        pasteurl=pasteurl.substring(0, pasteurl.length()-1);
                        FileConnection fco = (FileConnection)Connector.open("file:///"+currDirName+parse(pasteurl),Connector.READ_WRITE);
                         if(fco.exists())
                        { Alert alert = new Alert("alert","Already Exists!",null,null);
                        alert.setTimeout(Alert.FOREVER);
                        display.setCurrent(alert);
                        return;
                        }
                        fco.mkdir();
                        fco.close();
                        fci.close();
                        while(e.hasMoreElements())
                        {

                            temp1=pasteurl;
                            temp2=currDirName;
                            currDirName=currDirName+parse(pasteurl)+"/";
                            pasteurl+="/"+(String) e.nextElement();
                            paste(pasteurl);
                            pasteurl=temp1;
                            currDirName=temp2;

                        }

                   }


 else{
                    FileConnection fco = (FileConnection)Connector.open("file:///"+currDirName+parse(pasteurl),Connector.READ_WRITE);
                     if(fco.exists())
                    { Alert alert = new Alert("alert","Already Exists!",null,null);
                      alert.setTimeout(Alert.FOREVER);
                      display.setCurrent(alert);
                      return;
                    }
                    fco.create();
                    InputStream is = fci.openInputStream();
                    OutputStream os = fco.openOutputStream();
                    byte e[] = new byte[5000];                
                    int bytesRead;
                    while ((bytesRead = is.read(e)) != -1)
                    {
                        os.write(e, 0, bytesRead);
                        }
                    if (pasteURL!=null && copycut == false)
                    {
                        fci.delete();
                    }
                    fco.close();
                    os.close();
                    fci.close();
                    is.close();
                    pasteURL=null;
              }

            }
            catch (Exception e){}




}
    public void deletefolder(String path)
    {
        int last;
        try {
            FileConnection current = (FileConnection) Connector.open("file:///"+path);
            if(current.isDirectory())
            {
            Enumeration e = current.list();
            while(e.hasMoreElements())
            {
                path=path+(String) e.nextElement();
                deletefolder(path);
                last=path.length()-2;
                path=path.substring(0,last);
                path=parsepre(path);
                }
            }
            current.delete();
            current.close();
            


        } catch (IOException ex) {
            ex.printStackTrace();
        }


}

}
