
package processamentoimagem;

import java.io.File;

import java.awt.image.BufferedImage;

import javax.swing.*;


import javax.imageio.*;

/**
 *
 * @author MATEUS
 */
public class AmpRed extends JFrame {
    
    public static void main(String args[]){}
    
    public AmpRed(String aFile, int aFator, int aTipoOp){
    
        BufferedImage bimage = null, dest = null;
        
        File file = new File(aFile);
        
        try{
            
            bimage = ImageIO.read(file);
            
        } catch(Exception e){
            
            System.out.println("Imagem '" + aFile + "' nao existe.");
            System.exit(0);
        }
        
        setTitle("Amplia/Reduz imagem: " + file.getName());
        
        dest = zoomImage(bimage, aFator, aTipoOp);
        
        JLabel limg = new JLabel(new ImageIcon(dest));
        getContentPane().add(new JScrollPane(limg));
        setSize(bimage.getWidth(), bimage.getHeight());
                
        
    }
    
    public BufferedImage zoomImage(BufferedImage image, int fator, int tipoOp){
    
        BufferedImage bi = null;
        int w, h;
        
        switch(tipoOp){
            case 1:
                w = fator*image.getWidth();
                h = fator*image.getHeight();
                bi = new BufferedImage(w, h, image.getType());
                for (int j = 0; j < h; j++)
                    for (int i = 0; i < w; i++)
                        bi.setRGB(i, j, image.getRGB(i/fator, j/fator));
                break;
            case 2:
                w = (int) image.getWidth()/fator;
                h = (int) image.getHeight()/fator;
                bi = new BufferedImage(w, h, image.getType());
                for (int j = 0; j < h; j++) {
                    for (int i = 0; i < w; i++) {
                        bi.setRGB(i, j, image.getRGB(i*fator, j*fator));
                    }
                }
                break;
        }
        
        return bi;
        
    }
    

}
