
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author MATEUS
 */
public class FiltroDWMTM extends JFrame {
 
    public static void main(String[] args) {
        double K = 0.0, S = 0.0;
        
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
        
        if (K < 0.0) {
            System.out.println("Valor de 'Kappa' deve ser positivo");
            System.exit(0);
        }
        
        if (S < 0.0) {
            System.out.println("valor de 'Sigma' deve ser positivo");
            System.exit(0);
        }
        
        long e_time = System.currentTimeMillis();
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        FiltroDWMTM rd = new FiltroDWMTM(file, K, S);
        
        rd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rd.setVisible(true);
        
        e_time = System.currentTimeMillis() - e_time;
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println("Tempo de execução: " + df.format(e_time) + "milisseg.");
        
        
        
    }
    
    public FiltroDWMTM(File file, double K, double S){
    
        BufferedImage src = null, dest = null;
        JLabel img1, img2;
        int w, h;
        
        try {
            src = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Imagem '" + file + "' nao existe." + e);
            System.exit(0);
        }
        
        setTitle("Filtro adaptativo DW-MTM: " + file.getName());
        
        System.out.println("Calculando filtro adaptativo... ");
        dest = Filtro(src, K, S);
        
        w = dest.getWidth();
        h = dest.getHeight();
        
        getContentPane().setLayout(new GridLayout(1, 2));
        
        img1 = new JLabel(new ImageIcon(src));
        img2 = new JLabel(new ImageIcon(dest));
        getContentPane().add(new JScrollPane(img1));
        getContentPane().add(new JScrollPane(img2));
        setSize(2*w,h);
        
    }
    
    public BufferedImage Filtro(BufferedImage src, double Kappa, double Sigma){
        
        int w, h, mediana[], med, tmp, sum, gray, i, j, X, Y, x1, y1, R, tipo;
        
        h = src.getHeight();
        w = src.getWidth();
        Raster srcR = src.getRaster();
        tipo = BufferedImage.TYPE_BYTE_GRAY;
        BufferedImage outImage = new BufferedImage(w, h, tipo);
        WritableRaster WR = outImage.getRaster();
        mediana = new int[9];
        R = w;
        
        for (Y = 2; Y < h-2; Y++)
            for (X = 2; X < w-2; X++){
                // Le os 9 pixels para a matriz mediana
                tmp = 0;
                for (y1 = -1; y1 < 2; y1++)
                    for (x1 = -1; x1 < 2; x1++) {
                        mediana[tmp] = srcR.getSample(X+x1, Y+y1, 0);
                        tmp++;                    
                    }
                Arrays.sort(mediana);
                med = mediana[4];
                sum = 0;
                tmp = 0;
                for (y1 = -2; y1 < 3; y1++)
                    for (x1 = -2; x1 < 3; x1++) {
                        gray = srcR.getSample(X+x1, Y+y1, 0);
                        if (gray >= (med - Kappa*Sigma)) 
                            if (gray <= (med + Kappa*Sigma)) {
                                sum = sum + gray;
                                tmp++;
                            }
                    }
                WR.setSample(X, Y, 0, sum/tmp);
            }
        
        try {
                System.out.println("salvando arquivo 'FiltroDWMTM.png' em 'C:\\temp\\'");
                ImageIO.write(outImage, "png", new File("C:\\temp\\FiltroDWMTM.png"));
            } catch (IOException e) {
                System.out.println("problema ao gravar o arquivo " + e);
                System.exit(0);
            }
        
        return outImage;
    }
    
    
}
