/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import java.io.*;
import java.lang.String;
import java.util.Enumeration;
import javax.microedition.io.*;
import javax.microedition.io.file.FileConnection;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.FileBrowserMod;


/**
 * @author Anoop
 */
public class HelloMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private FileBrowserMod fileBrowser;
    private List list;
    private Alert alert1;
    private Alert alert;
    //</editor-fold>//GEN-END:|fields|0|
private Display display;
private Alert kalert;
private String old_name, new_pre, new_name;
private boolean copynotcut;
private TextField textfield;
private Form form;
private Command ok;
private Command exit=new Command("Exit",Command.EXIT,0);
private Command rename = new Command("Rename",Command.OK,0);
private Command delete = new Command("Delete",Command.OK,0);
private Command copy = new Command("Copy",Command.OK,0);
private Command cut = new Command("Cut",Command.OK,0);
private Command properties = new Command("Properties",Command.OK,0);
private Command save = new Command("Save",Command.OK,0);
private Command create = new Command("Create",Command.OK,0);
private String filecontent;
private String newcontent;
private String currentDir;
private int last;
private TextBox textbox;
private Form form1;

/**
     * The HelloMIDlet constructor.
     */
    public HelloMIDlet() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getFileBrowser("/",null,false));//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == fileBrowser) {//GEN-BEGIN:|7-commandAction|1|24-preAction
            if (command == FileBrowserMod.SELECT_FILE_COMMAND) {//GEN-END:|7-commandAction|1|24-preAction
                // write pre-action user code here
                switchDisplayable(null, getList());//GEN-LINE:|7-commandAction|2|24-postAction
                // write post-action user code here
            }
            else if (command == create)
            {
                form1 = new Form("Enter name for new file");
                textfield=new TextField("Name:","",30,TextField.ANY);
                ok = new Command("Ok", Command.SCREEN, 1);
                exit=getExitCommand();

                  form1.addCommand(exit);
                  form1.addCommand(ok);
                  form1.append(textfield);
                  form1.setCommandListener(this);
                  switchDisplayable(null,form1);
            }

    else if(command == properties)
            {

                   currentDir=fileBrowser.currdirectory();
                   currentDir=currentDir+fileBrowser.selectedop(fileBrowser);
                   try{
                   FileConnection fci = (FileConnection) Connector.open("file:///"+currentDir);
                   long size;
                   String sizestring=currentDir+"\n Size: ";
                   if(fci.isDirectory())
                   {size = fci.directorySize(true);                   
                   }
                    else{size = fci.fileSize();}

                   if (size > 1024 * 1024)
                   {
                       size=size/(1024 *1024);
                       sizestring+=String.valueOf(size)+"MB \n";
                   }
                       else if(size > 1024)
                   {
                       size=size/1024;
                       sizestring+=String.valueOf(size)+" KB \n";
                   }
                   else { sizestring+=String.valueOf(size)+" bytes \n"; }
                   
                   Alert alert = new Alert("alert",sizestring,null,null);
                   alert.setTimeout(Alert.FOREVER);
                   switchDisplayable(alert, fileBrowser);
                   

                   
                }
                      catch(Exception e) {}

            }


         else if(command == exit)
            {
             exitMIDlet();
         }


            else if (command == copy)
            {
                String real=fileBrowser.currdirectory();
                old_name="file:///"+real+fileBrowser.selectedop(fileBrowser);
                fileBrowser=null;
                switchDisplayable(null, getFileBrowser(real,old_name,true));
                
            }
            else if (command == cut)
            {
                String real=fileBrowser.currdirectory();
                old_name="file:///"+real+fileBrowser.selectedop(fileBrowser);
                fileBrowser=null;
                switchDisplayable(null, getFileBrowser(real,old_name,false));
            }




            else if (command == delete)
            {
            currentDir= fileBrowser.currdirectory();
            currentDir=currentDir+(fileBrowser.selectedop(fileBrowser));            
               
            fileBrowser.deletefolder(currentDir);

            currentDir=currentDir.substring(0,currentDir.length()-2);
               
            fileBrowser=null;
            switchDisplayable(null, getFileBrowser(parsepre(currentDir), null, false));

            }


            else if (command == rename)
            {
                display = Display.getDisplay(this);
               form = new Form("Enter new name");
               textfield=new TextField("Name:","",30,TextField.ANY);
               ok = new Command("Ok", Command.SCREEN, 1);
               exit=getExitCommand();

                  form.addCommand(exit);
                  form.addCommand(ok);
                  form.append(textfield);
                  form.setCommandListener(this);
                  switchDisplayable(null,form);
                  //currentDir=fileBrowser.currdirectory();


            }//GEN-BEGIN:|7-commandAction|3|28-preAction
        } else if (displayable == list) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|3|28-preAction
                // write pre-action user code here
                listAction();//GEN-LINE:|7-commandAction|4|28-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|7-postCommandAction
        }//GEN-END:|7-commandAction|5|7-postCommandAction
        // write post-action user code here
        else if(displayable == form) {
            if (command == ok)
            {     new_name=textfield.getString();                                 
                      currentDir=fileBrowser.currdirectory();
                      currentDir=currentDir+fileBrowser.selectedop(fileBrowser);
                      try{
                      FileConnection fci = (FileConnection) Connector.open("file:///"+currentDir);
                      fci.rename(new_name);
                      fci.close();
                      fileBrowser=null;
                      int end=currentDir.length();
                      currentDir=currentDir.substring(0, end-2);
                      currentDir=parsepre(currentDir);
                      switchDisplayable(null, getFileBrowser(currentDir,null,false));
                      }
                      catch(Exception e) {}
                  
            }
 else if (command == exit)
                  {
                      currentDir=fileBrowser.currdirectory();                                            
                      switchDisplayable(null, getFileBrowser(currentDir,null,false));
            }
                /*else if (command == exit){
                switchDisplayable(null, getFileBrowser(new_pre.substring(8),null,false));}*/
            
        }

 else if (displayable == form1)
        {
     if (command == ok)
     {
         new_name=textfield.getString();
         currentDir=fileBrowser.currdirectory();
         currentDir+=new_name;                 
              try {
                    FileConnection fci = (FileConnection) Connector.open("file:///"+currentDir);
                    if(fci.exists())
                    {
                        Alert alert = new Alert("alert","File Exists",null,null);
                        alert.setTimeout(Alert.FOREVER);
                        switchDisplayable(alert,fileBrowser);
                    }
                    fci.create();
                    fci.close();                    
                    currentDir=fileBrowser.currdirectory();
                    fileBrowser=null;
                    switchDisplayable(null, getFileBrowser(currentDir, null, false));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
     }

 else if(command ==exit){                                   
     switchDisplayable(null, fileBrowser);
 }

 }

 else if (displayable == textbox)
        {
     if(command == ok)
     {        
         switchDisplayable(null,fileBrowser);
     }

     if(command == save)
     {
         
         newcontent =textbox.getString();
         byte[] b = newcontent.getBytes();
         old_name=fileBrowser.getSelectedFileURL();
         if(filecontent != newcontent)
         {
                    try {
                        FileConnection f = (FileConnection) Connector.open(old_name,Connector.READ_WRITE);
                        OutputStream fout = f.openOutputStream();
                        fout.write(b);
                        fout.close();
                        f.close();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
         }
 else {  switchDisplayable(null,fileBrowser);
     }
 }
        }

    }//GEN-BEGIN:|7-commandAction|6|
    //</editor-fold>//GEN-END:|7-commandAction|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: fileBrowser ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of fileBrowser component.
     * @return the initialized component instance
     */
    public FileBrowserMod getFileBrowser(String url,String url1,boolean bool) {
        if (fileBrowser == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            fileBrowser = new FileBrowserMod(getDisplay(),url,url1,bool);//GEN-BEGIN:|22-getter|1|22-postInit
            fileBrowser.setTitle("fileBrowser");
            fileBrowser.setCommandListener(this);
            fileBrowser.addCommand(FileBrowserMod.SELECT_FILE_COMMAND);//GEN-END:|22-getter|1|22-postInit
            // write post-init user code here
            fileBrowser.addCommand(rename);
            fileBrowser.addCommand(delete);
            fileBrowser.addCommand(copy);
            fileBrowser.addCommand(cut);
            fileBrowser.addCommand(properties);
            fileBrowser.addCommand(create);
            fileBrowser.addCommand(exit);
        }//GEN-BEGIN:|22-getter|2|
        return fileBrowser;
    }
    //</editor-fold>//GEN-END:|22-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: list ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of list component.
     * @return the initialized component instance
     */
    public List getList() {
        if (list == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            list = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|26-getter|1|26-postInit
            list.append("View", null);
            list.append("Delete", null);         
            list.append("Copy", null);
            list.append("Cut", null);
            list.append("Rename", null);
            list.append("Back", null);
            list.setCommandListener(this);
            list.setSelectedFlags(new boolean[] { false, false, false, false, false, false, false, false });//GEN-END:|26-getter|1|26-postInit
            // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return list;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listAction ">//GEN-BEGIN:|26-action|0|26-preAction
    /**
     * Performs an action assigned to the selected list element in the list component.
     */
    public void listAction() {//GEN-END:|26-action|0|26-preAction
        // enter pre-action user code here
        String __selectedString = getList().getString(getList().getSelectedIndex());//GEN-BEGIN:|26-action|1|31-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("View")) {//GEN-END:|26-action|1|31-preAction
                // write pre-action user code here
                
                display = Display.getDisplay(this);
                String name = fileBrowser.getSelectedFileURL();

                try {
                    FileConnection fcv = (FileConnection) Connector.open(fileBrowser.getSelectedFileURL(),Connector.READ_WRITE);
                    InputStream iis = fcv.openInputStream();
                    byte b[] = new byte[5000];
                    int length=0;
                    while(length!=-1)
                    {
                        length = iis.read(b);
                    }
                    filecontent = new String(b);
                    iis.close();
                    fcv.close();
                    textbox = new TextBox("Contents", filecontent, 5000, 0);
                    ok = new Command("Ok", Command.SCREEN, 1);
                    textbox.addCommand(ok);
                    textbox.addCommand(save);                    
                    textbox.setCommandListener(this);
                    switchDisplayable(null,textbox);
			    //switchDisplayable(null, getTextBox(filecontent));
			    //filecontent=null;
                            //b=null;
                }
               catch(IOException ex) {
                    ex.printStackTrace();
                }

//GEN-LINE:|26-action|2|31-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Delete")) {//GEN-LINE:|26-action|3|32-preAction
                // write pre-action user code here
                try {
                FileConnection fc = (FileConnection) Connector.open(fileBrowser.getSelectedFileURL(),Connector.READ_WRITE);
                          fc.delete();

                }
                catch( Exception e ){
                    }
                String name= parsepre(fileBrowser.getSelectedFileURL());
                fileBrowser=null;
                switchDisplayable(getAlert(), getFileBrowser(name.substring(8),null,false));//GEN-LINE:|26-action|4|32-postAction
                // write post-action user code here
            }//GEN-LINE:|26-action|5|33-preAction
                // write pre-action user code here
//GEN-LINE:|26-action|6|33-postAction
                // write post-action user code here
             else if (__selectedString.equals("Copy")) {//GEN-LINE:|26-action|7|34-preAction
                // write pre-action user code here
                old_name = fileBrowser.getSelectedFileURL();
                String real=parsepre(old_name);
                fileBrowser=null;
                switchDisplayable(null, getFileBrowser(real.substring(8),old_name,true));//GEN-LINE:|26-action|8|34-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Cut")) {//GEN-LINE:|26-action|9|46-preAction
                // write pre-action user code here
                old_name = fileBrowser.getSelectedFileURL();
                String real=parsepre(old_name);
                fileBrowser=null;
                switchDisplayable(null, getFileBrowser(real.substring(8),old_name,false));//GEN-LINE:|26-action|10|46-postAction
                // write post-action user code here
            }//GEN-LINE:|26-action|11|35-preAction
                // write pre-action user code here
                
//GEN-LINE:|26-action|12|35-postAction
                // write post-action user code here
             else if (__selectedString.equals("Rename")) {//GEN-LINE:|26-action|13|36-preAction
                // write pre-action user code here
               display = Display.getDisplay(this);
               form = new Form("Enter new name");
               textfield=new TextField("Name:","",30,TextField.ANY);
               ok = new Command("Ok", Command.SCREEN, 1);
               exit=getExitCommand();

                  form.addCommand(exit);
                  form.addCommand(ok);
                  form.append(textfield);
                  form.setCommandListener(this);
                  switchDisplayable(null,form);



//GEN-LINE:|26-action|14|36-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Back")) {//GEN-LINE:|26-action|15|37-preAction
                // write pre-action user code here

                switchDisplayable(null, fileBrowser);

//GEN-LINE:|26-action|16|37-postAction
                // write post-action user code here
            }//GEN-BEGIN:|26-action|17|26-postAction
        }//GEN-END:|26-action|17|26-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|26-action|18|
    //</editor-fold>//GEN-END:|26-action|18|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert ">//GEN-BEGIN:|40-getter|0|40-preInit
    /**
     * Returns an initiliazed instance of alert component.
     * @return the initialized component instance
     */
    public Alert getAlert() {
        if (alert == null) {//GEN-END:|40-getter|0|40-preInit
            // write pre-init user code here
            alert = new Alert("alert", "File Deleted", null, null);//GEN-BEGIN:|40-getter|1|40-postInit
            alert.setTimeout(Alert.FOREVER);//GEN-END:|40-getter|1|40-postInit
            // write post-init user code here
        }//GEN-BEGIN:|40-getter|2|
        return alert;
    }
    //</editor-fold>//GEN-END:|40-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert1 ">//GEN-BEGIN:|42-getter|0|42-preInit
    /**
     * Returns an initiliazed instance of alert1 component.
     * @return the initialized component instance
     */
    public Alert getAlert1() {
        if (alert1 == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
            alert1 = new Alert("alert1", "New Directory created", null, null);//GEN-BEGIN:|42-getter|1|42-postInit
            alert1.setTimeout(Alert.FOREVER);//GEN-END:|42-getter|1|42-postInit
            // write post-init user code here
        }//GEN-BEGIN:|42-getter|2|
        return alert1;
    }
    //</editor-fold>//GEN-END:|42-getter|2|











    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

        public static String parse(String str) {
        int pos = str.lastIndexOf('/');
        if (pos == -1) {
          return "";
      }
      return str.substring(pos + 1);
  }
        public static String parsepre(String str) {
        int pos = str.lastIndexOf('/');
        if (pos == -1) {
          return "";
      }
      return str.substring(0,pos+1);
  }
 
        



}
