
package processamentoimagem;

import java.awt.Rectangle;
import java.io.File;

import java.text.DecimalFormat;

import java.awt.image.*;

import javax.swing.*;

import javax.imageio.ImageIO;

/**
 *
 * @author MATEUS
 */
public class Regioes extends jFrame{
    
    public static void main(String[] args) {
        
    }
    
    public Regioes(String aFile, int x, int y, int w, int h){
        BufferedImage bimage = null;
        
        File file = new File(aFile);

        try {
            
            bimage = ImageIO.read(file);
            
        } catch (Exception e) {
            
            System.out.println("Imagem '" + aFile + "' nao existe.");
            System.exit(0);
            
        }
        
        setTitle("Media da ROI: " + file.getName());
                
        if (w > bimage.getWidth()) {
            
        }
        
    }
    
    public double media(BufferedImage img, Rectangle REGIAO){
        
    }
    
    public double media(BufferedImage src){
        
    }
    
}
