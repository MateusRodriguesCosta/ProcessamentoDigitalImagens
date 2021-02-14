
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


public class RuidoGauss extends JFrame {

        public static void main(String [] args){

            int nc1 = 0, nc2 = 10;

            File file = null;
            int result;

            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setDialogTitle("Abre imagem");
            result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();

            } else {
                System.out.println("Escolher imagem");
                System.exit(0);
            }

            if ((nc1 < 0 || nc2 < 0)) {
                System.out.println("valores de 'nc1' e 'nc2' devem ser positivos");
                System.exit(0);
            }
            
            if  (nc1 == nc2) {
                System.out.println("valores de 'nc1' e 'nc2' devem ser diferentes");
                System.exit(0);
            }
            
            
            // Mostra JFrame decorado pelo Swing
            JFrame.setDefaultLookAndFeelDecorated(true);

            // Chama o construtor passando os parametros
            JFrame ruido = new RuidoGauss(file, nc1, nc2);

            //Encerra a aplicação clicando os parametros
            ruido.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ruido.setVisible(true);    
        }

        public RuidoGauss (File file, int nc1, int nc2){

            //Define objeto BufferedImage para encapsular a imagem
            BufferedImage src = null, dest = null;
            JLabel img1, img2;
            int w, h;

            try{
                src = ImageIO.read(file);
            } catch (Exception e){
                System.out.println("Imagem " + file + "Nao Existe");
                System.exit(0);
            }

            //Atribui nome ao frame
            setTitle("Ruido Gauss: " + file.getName());

            dest = gaussOp(src, nc1, nc2);

            w = dest.getWidth();
            h = dest.getHeight();

            //Cria o gridLayut de 1 x 2
            getContentPane().setLayout(new GridLayout(1, 2));
            img1 = new JLabel(new ImageIcon(src));
            img2 = new JLabel(new ImageIcon(dest));
            getContentPane().add(new JScrollPane (img1));
            getContentPane().add(new JScrollPane (img2));
            setSize(2*w, h);
        }

        public BufferedImage gaussOp(BufferedImage src, int nc1, int nc2){

            double range, randonBright;
            int w, h, nc, tipo;

            h = src.getHeight();
            w = src.getWidth();

            tipo = BufferedImage.TYPE_BYTE_GRAY;
            BufferedImage outImage = new BufferedImage(w, h, tipo);
            WritableRaster outWR = outImage.getRaster();
            Raster srcR = src.getRaster();
            Raster outR = outImage.getRaster();

            if (nc1 > nc2){
                int tmp = nc2;
                nc2 = nc1;
                nc1 = tmp;
            }

            range = nc2 - nc1;

            //Copia a imagem original
            src.copyData(outWR);

            //Simula ruido
            for (int Y = 0; Y < h; Y++)
                for (int X = 0; X < w; X++){
                    randonBright = Math.random()*range;
                    nc = outR.getSample(X, Y, 0);
                    outWR.setSample(X, Y, 0, nc + (byte)randonBright);                   

            }
            
            try {
                System.out.println("salvando arquivo 'RuidoGauss.png' em 'C:\\temp\\'");
                ImageIO.write(outImage, "png", new File("C:\\temp\\RuidoGauss.png"));
            } catch (IOException e) {
                System.out.println("problema ao gravar o arquivo " + e);
                System.exit(0);
            }
            
            return outImage;
        }
}