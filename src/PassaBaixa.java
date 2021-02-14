
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
public class PassaBaixa extends JFrame {
    
    public static void main(String[] args) {
        
        int filtro = 2, result;
        
        File file = null;        
        
        //comandos swing para abrir janela com arquivos
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Abre imagem");
        result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        } else {
            System.out.println("Escolher imagem");
            System.exit(0);
        }
        
        if (filtro <=0 || filtro > 4) {        
            System.out.println("Filtro: valores validos -> 1, 2, 3, 4");
            System.exit(0);
        }
        
        long e_time = System.currentTimeMillis();
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        PassaBaixa lpFiltro = new PassaBaixa(file, filtro);
        
        lpFiltro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lpFiltro.setVisible(true);
        
        e_time = System.currentTimeMillis() - e_time;
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Tempo de execucao: " + df.format(e_time) + " milisseg.");
    }
    
    public PassaBaixa(File file, int filtro){
            
        BufferedImage src = null, dest = null;
        JLabel img1, img2;
        int w, h;
    
        try {
            src = ImageIO.read(file);            
        } catch (IOException e) {
            System.out.println("Imagem '" + file.getName() + "' nao existe!");
            System.exit(0);
        }
        
        setTitle("Filtro passa-baixa (media): " + file.getName());
        
        System.out.println("Calculando filtro de passa-baixa... ");
        dest = filtropb(src, filtro);
        
        w = dest.getWidth();
        h = dest.getHeight();
        
        getContentPane().setLayout(new GridLayout(1, 2));
        img1 = new JLabel(new ImageIcon(src));
        img2 = new JLabel(new ImageIcon(dest));
        getContentPane().add(new JScrollPane(img1));
        getContentPane().add(new JScrollPane(img2));
        setSize(2*w,h);        
        
    }
    
    public BufferedImage filtropb(BufferedImage src, int filtro){
    
        
        float matrizMedia[] = new float[9];
        
        Raster srcR = src.getRaster();
        WritableRaster WR = null;
        BufferedImage outImage = null;
        
        switch (filtro) {
            case 1: matrizMedia[0] = 1/10f;
            matrizMedia[1] = 1/10f;
            matrizMedia[2] = 1/10f;
            matrizMedia[3] = 1/10f;
            matrizMedia[4] = 2/10f;
            matrizMedia[5] = 1/10f;
            matrizMedia[6] = 1/10f;
            matrizMedia[7] = 1/10f;
            matrizMedia[8] = 1/10f;
            break;
            case 2: matrizMedia[0] = 1/12f;
            matrizMedia[1] = 1/12f;
            matrizMedia[2] = 1/12f;
            matrizMedia[3] = 1/12f;
            matrizMedia[4] = 4/12f;
            matrizMedia[5] = 1/12f;
            matrizMedia[6] = 1/12f;
            matrizMedia[7] = 1/12f;
            matrizMedia[8] = 1/12f;
            break;
            case 3: matrizMedia[0] = 1/20f;
            matrizMedia[1] = 1/20f;
            matrizMedia[2] = 1/20f;
            matrizMedia[3] = 1/20f;
            matrizMedia[4] = 12/20f;
            matrizMedia[5] = 1/20f;
            matrizMedia[6] = 1/20f;
            matrizMedia[7] = 1/20f;
            matrizMedia[8] = 1/20f;
            break;
            case 4: matrizMedia[0] = 1/9f;
            matrizMedia[1] = 1/9f;
            matrizMedia[2] = 1/9f;
            matrizMedia[3] = 1/9f;
            matrizMedia[4] = 1/9f;
            matrizMedia[5] = 1/9f;
            matrizMedia[6] = 1/9f;
            matrizMedia[7] = 1/9f;
            matrizMedia[8] = 1/9f;
            break;
        }
        
        Kernel filtroMedia = new Kernel(3, 3, matrizMedia);
        
        ConvolveOp blur = new ConvolveOp(filtroMedia);
        WR = blur.filter(srcR, null);
        
        outImage = new BufferedImage(src.getColorModel(), WR, false, null);
        
        try {
            System.out.println("salvando arquivo 'FiltroPassabaixa.png' em 'C:\\temp\\'");
            ImageIO.write(outImage, "png", new File("C:\\temp\\FiltroPassabaixa.png"));
        } catch (IOException e) {
            System.out.println("problema ao gravar o arquivo ");
            System.exit(0);
        }
        
        return outImage;    
    }
    
}
