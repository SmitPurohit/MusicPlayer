//This class will serve as the main "runner/player" class of the MusicPlayer
//The code here will play the music, and be the user interaction class
import java.awt.event.*;  
import javax.swing.*;    
import java.awt.*;
public class MusicUI implements ItemListener
{
   boolean playing; //is music playing
   
   static Player player;
   static JToggleButton button; 
   public static void main(String args[])
   {
      
      
      
      button = new JToggleButton("Play");  
      button.addItemListener(
         new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
               if(ev.getStateChange()==ItemEvent.SELECTED){
                  player.play = true;
               
               } else if(ev.getStateChange()==ItemEvent.DESELECTED){
                  player.play = false;
               }
            }
         });
      //setAction();  
      JFrame f=new JFrame("MP3 Player");  
      JButton b=new JButton("Skip");  
    /*b.setBounds(50,100,95,30);  
     b.addActionListener(new ActionListener(){  
   public void actionPerformed(ActionEvent e){  
            player.nextSong();
        }  
    });  */
      f.add(button);
      f.add(b);
      f.setSize(400,400);  
      f.setLayout(new GridLayout(3,3));   
      f.setVisible(true);
      f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      player = new Player(button, b);
      
      while(true)
      {
         player.playSong(0);
      }
   }
    
  
    
     
   public void itemStateChanged(ItemEvent eve) {  
      if (button.isSelected())  
         button.setText("OFF");  
      else  
         button.setText("ON");  
   }  
}
   
