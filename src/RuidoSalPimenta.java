import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class RuidoSalPimenta extends JFrame {
    
    public static void main(String[] args) {
        
        double percent = 0.5;
        
        File file = null;
        int result;
        
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
        
        if ((percent < 0.0) || (percent > 1.0)) {
            System.out.println("Porcentagem deve estar em [0.0, 1.0]");
            System.exit(0);
        }
        
        //mostra JFrame decorado pelo swing
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //chama construtor passando os parametros
        JFrame ruido = new RuidoSalPimenta(file, percent);
                
        //encerra a aplicação clicando no close
        ruido.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ruido.setVisible(true);
    }

    private RuidoSalPimenta(File file, double percent) {
        
        //define objeto BufferedImage para encapsular 
        BufferedImage src = null, dest = null;
        JLabel img1, img2;
        int w, h;
        
        //armazena arquivo imagem numa BufferedImage
        try {
            src = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Imagem" + file + " nao existe");
            System.exit(0);
        }
        
        //atribui nome ao frame
        setTitle("Ruido 'salt&pepper': " + file.getName());
        
        dest = saltpepperOp(src, percent);
        
        w = dest.getWidth();
        h = dest.getHeight();
        
        //cria gridLayout de 1 x 2
        getContentPane().setLayout(new GridLayout(1, 2));
        img1 = new JLabel(new ImageIcon(src));
        img2 = new JLabel(new ImageIcon(dest));
        getContentPane().add(new JScrollPane(img1));
        getContentPane().add(new JScrollPane(img2));
        setSize(2*w, h);
    }

    private BufferedImage saltpepperOp(BufferedImage src, double percent) {
        
        int length, n, xmin, xmax, ymin, ymax, h, w, tipo, nc;
        double rx, ry;
        
        h = src.getHeight();
        w = src.getWidth();
        n = (int)(percent*w*h);
        xmin = 0;
        xmax = w - 1;
        ymin = 0;
        ymax = h - 1;
        
        tipo = BufferedImage.TYPE_BYTE_GRAY;
        BufferedImage outImage = new BufferedImage(w, h, tipo);
        Raster srcR = src.getRaster();
        WritableRaster outWR = outImage.getRaster();
        
        //copia imagem original
        for (int Y=0; Y<h; Y++)
            for (int X=0; X<w; X++) {
                nc = srcR.getSample(X, Y, 0);
                outWR.setSample(X, Y, 0, nc);
        }
        
        //simula ruido
        for (int i = 0; i < n/2; i++) {
            rx = Math.random()*(xmax);
            ry = Math.random()*(ymax);
            outWR.setSample((int)rx, (int)ry, 0, (byte)255);
            rx = Math.random()*(xmax);
            ry = Math.random()*(ymax);
            outWR.setSample((int)rx, (int)ry, 0, (byte)0);
        }
        
        //salva arquivo formato png
        try {
            System.out.println("salvando arquivo 'ruidoS&P.png' em 'C:\\temp\\'");
            ImageIO.write(outImage, "png", new File("C:\\temp\\RuidoS&P.png"));
        } catch (IOException e) {
            System.out.println("problema ao gravar o arquivo ");
            System.exit(0);
        }
        
        return outImage;
    }
}
