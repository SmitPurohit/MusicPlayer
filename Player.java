//This class is the player of the music ... duh

import java.util.ArrayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
import javafx.*;
import javafx.embed.swing.JFXPanel;
import org.jaudiotagger.audio.*;
import java.util.Date;

import java.awt.event.*;

import javax.swing.*;

public class Player extends JPanel implements KeyListener, ActionListener, FocusListener
{
   public String nameSong;
   public ArrayList<String> songList = new ArrayList<String>();
   public boolean skip = false;
   private static Media hit;
   private static MediaPlayer mediaPlayer;
   int songIndex;
   public boolean shuffle = false; //shuffle the songs?
   JToggleButton button;
   JFrame f;
   public boolean play = false;
   public Player(JToggleButton button)
   {
      
      this.button = button;
      button.addItemListener(
         new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
               if(ev.getStateChange()==ItemEvent.SELECTED){
                  skip = true;
               } else if(ev.getStateChange()==ItemEvent.DESELECTED){
               
               }
            }
         });
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      f = new JFrame("");
      songIndex = 0;
      JFXPanel pp = new JFXPanel();
      File dir = new File(System.getProperty("user.dir"));
      
      //only opens .mo3 files
      File[] filesInDir = dir.listFiles(
         new FilenameFilter() {
            public boolean accept(File dir, String name) {
               return name.toLowerCase().endsWith(".mp3");
            }});
   
      //fills in the songlist with the files in the same directory as this file if the extension is .mp3
   
      for(File file: filesInDir)
      {
         String fileName = file.getName();
      
         songList.add(fileName);
         System.out.println(fileName);
      }
      
      
      
   
   }
   
   public void playSong()
   {
      //if there is no shuffle
      int current;
      int count = 0;
      if(!shuffle)
      {
         current = (int)(System.currentTimeMillis()/1000);
         String song = songList.get(songIndex);
         System.out.println(song);
         hit = new Media(new File(song).toURI().toString());
         mediaPlayer = new MediaPlayer(hit);
         
         while(songGoingOn(current, song))//doesnt do anything until the song is done
         {
            if(count == 0)
               mediaPlayer.play();
            count = 1;
            if(skip)
            {
               skip = false;
               
               break;
            }
           
            
         }
         nextSong();
         
      
      }
      //if shuffle is on
      else{
         current = (int)(System.currentTimeMillis()/1000);
      
         songIndex = (int)(Math.random()*songList.size());
         String song = songList.get(songIndex);
         hit = new Media(new File(song).toURI().toString());
         mediaPlayer = new MediaPlayer(hit);
         mediaPlayer.play();
         while(songGoingOn(current, song))//doesnt do anything until the song is done
         {
            if(skip)
            {
               button = new JToggleButton("ON");  
               break;
            }
         }
         
      }
      
      
      
   }

   public boolean songGoingOn(int current, String song)
   {
      int duration = 3;
      if(!play)
      {
         while(!play){System.out.println(play);}
      }
      try {
         AudioFile audioFile = AudioFileIO.read(new File(song));
         duration += audioFile.getAudioHeader().getTrackLength();
      
      } catch (Exception e) {
         e.printStackTrace();
      
      }
      int newCurrent = (int)(System.currentTimeMillis()/1000);
      //skip = newCurrent-current == 10;
      
      
      System.out.println(newCurrent-current);
      
      return (newCurrent-current)<=duration;
   }
   public void nextSong()
   {
      f.getContentPane().remove(button);
      button = new JToggleButton("ON");  
      f.add(button);
      mediaPlayer.stop();
      songIndex++;
      button.setEnabled(false);
      if(songIndex>=songList.size())
         songIndex = 0;
      this.playSong();
   }
   
   @Override
   public void actionPerformed(ActionEvent e){  
            //this.nextSong();
   } 
   
   @Override
   public void keyTyped(KeyEvent e){
      int keyCode = e.getKeyCode();
      if(keyCode == KeyEvent.VK_LEFT)
      {
       
         skip = true;
         System.out.println(skip);
      }}
    
   @Override
    public void keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_S)
      {
         skip = true;
         System.out.println(skip);
         mediaPlayer.stop();
         songIndex++;
      }
   }
    
   @Override
    public void keyReleased(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_S)
      {
         skip = false;
         System.out.println("Skip: " + skip);
      }}
   @Override
   public void focusGained(FocusEvent e){}
   
   @Override
   public void focusLost(FocusEvent e){}
   
}