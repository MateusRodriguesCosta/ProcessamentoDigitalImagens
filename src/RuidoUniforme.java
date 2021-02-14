
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
/**
 *
 * @author MATEUS
 */
public class RuidoUniforme extends JFrame {
    
    public static void main(String[] args) {
        int nc1 = 0, nc2 = 10;
        
        File file = null;
        int result;
        
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Abre imagem");
        result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        } else {
            System.out.println("Escolher imagem.");
            System.exit(0);
        }
        
        if ((nc1 < 0) || (nc2 < 0)) {
            System.out.println("valores de 'nc1' e 'nc2' devem ser positivos");            
            System.exit(0);
        }
        
        if ((nc1 == nc2)) {
            System.out.println("Valores de 'nc1' e 'nc2' devem ser diferentes");
            System.exit(0);            
        }
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        JFrame ruido = new RuidoUniforme(file, nc1, nc2);
        
        ruido.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ruido.setVisible(true);
    }
    
    public RuidoUniforme(File file, int nc1, int nc2){
        BufferedImage src = null, dest = null;
        JLabel img1, img2;
        int w, h;
        
        try {
            src = ImageIO.read(file);            
        } catch (IOException e) {
            System.out.println("Imagem '" + file + "' nao existe.");
            System.exit(0);            
        }
        
        setTitle("Ruido uniforme: " + file.getName());
        
        dest = uniformeOp(src, nc1, nc2);
        
        w = dest.getWidth();
        h = dest.getHeight();
        
        getContentPane().setLayout(new GridLayout(1, 2));
        img1 = new JLabel(new ImageIcon(src));
        img2 = new JLabel(new ImageIcon(dest));
        getContentPane().add(new JScrollPane(img1));
        getContentPane().add(new JScrollPane(img2));
        setSize(2*w, h);        
    }
    
    public BufferedImage uniformeOp(BufferedImage src, int nc1, int nc2){
        double range, randomBright;
        int w, h, nc, tipo, v;
        
        h = src.getHeight();
        w = src.getWidth();
        
        tipo = BufferedImage.TYPE_BYTE_GRAY;
        BufferedImage outImage = new BufferedImage(w, h, tipo);
        WritableRaster outWR = outImage.getRaster();
        Raster srcR = src.getRaster();
        Raster outR = outImage.getRaster();
        
        if (nc1 > nc2) {
            int tmp = nc2;
            nc2 = nc1;
            nc1 = tmp;            
        }
        range = nc2 - nc1;
        
        for (int Y = 0; Y < h; Y++)
            for (int X = 0; X < w; X++){
                nc = srcR.getSample(X, Y, 0);
                outWR.setSample(X, Y, 0, nc);
        }
        
        for (int Y = 0; Y < h; Y++)
            for (int X = 0; X < w; X++) {
                randomBright = Math.random()*range;
                nc = outR.getSample(X, Y, 0);
                v = (nc & 0xff) + (int) randomBright;
                
                if (v < 0)
                    v = 0;
                if (v > 255)
                    v = 255;
                outWR.setSample(X, Y, 0, v);
        }
        
        try {
                System.out.println("salvando arquivo 'RuidoUniforme.png' em 'C:\\temp\\'");
                ImageIO.write(outImage, "png", new File("C:\\temp\\RuidoUniforme.png"));
            } catch (IOException e) {
                System.out.println("problema ao gravar o arquivo " + e);
                System.exit(0);
            }
        
        return outImage;
    }
}
