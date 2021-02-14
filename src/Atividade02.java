import com.sun.jndi.toolkit.url.Uri;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.imageio.ImageIO;

public final class Atividade02 extends JFrame{	

	private Atividade02(double angulo, int interpolador) {
        	
            BufferedImage bfImagem = null, bfImagemDest = null;    	            
            JLabel destLargura;	 
            int interp = 1, largura, altura;
            File file = null;
            
            try {
                URI arquivo = getClass().getResource("Imagens/Atividade02.jpg").toURI();
                file = new File(arquivo);
                bfImagem = ImageIO.read(file);
            } catch (IOException | URISyntaxException e) {
        	System.out.println("Erro ao abrir imagem!" + e);
        	System.exit(0);
            }
   	 
    	
            setTitle("Transformação: " + file.getName());
   	 
            /* DEFINE TIPO DO INTERPOLADOR */             
            switch (interpolador) {
        	case 1:
                    interp = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
            	break;
        	case 2:
                    interp = AffineTransformOp.TYPE_BILINEAR;
            	break;
            }  
   	 
            /* REALIZA A TRANSFORMAÇÃO */          
            bfImagemDest = Transformacao(bfImagem, angulo, interp);
            largura = bfImagemDest.getWidth();
            altura = bfImagemDest.getHeight();
   	 
            destLargura = new JLabel(new ImageIcon(bfImagemDest));
            getContentPane().add(new JScrollPane(destLargura));
            setSize(largura, altura);           
	}
        
        public static void main(String[] args) {
   	                         
            double t = 15.0;             
            int tipoInterpolador = 1;
   	 
            /* CERTIFICA SE VALORES DO INTERPOLADOR SÃO VÁLIDOS */
            if ((tipoInterpolador != 1) && (tipoInterpolador != 2)) System.exit(0);
   	     	
            JFrame.setDefaultLookAndFeelDecorated(true);   	             
            JFrame ta2 = new Atividade02(t, tipoInterpolador);
            ta2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ta2.setVisible(true);

	}    
    
        protected BufferedImage Transformacao(BufferedImage bfImagem, double angulo, int interp) {
   	 
            /* ÂNGULOS ESPECIFICADOS EM RADIANOS */
            angulo = Math.toRadians(angulo);
   	 
            /* PONTO ÂNCORA */
            double eixox = bfImagem.getWidth()/2.0;
            double eixoy = bfImagem.getHeight()/2.0;
            
            String opcao = JOptionPane.showInputDialog("Qual transformação deseja realizar? \n"
                    + "1 - Translação \n"
                    + "2 - Rotação \n"
                    + "3 - Mudança de Escala \n"
                    + "4 - Distorção e Reflexão \n");   	             	 
       	 
            switch (opcao) {
                case "1":
                {
                    AffineTransform af = AffineTransform.getTranslateInstance(eixox, eixoy);
                    AffineTransformOp afOp = new AffineTransformOp(af, interp);
                    BufferedImage bfImagemFinal = afOp.filter(bfImagem, null);
                    return bfImagemFinal;
                }
                case "2":
                {
                    AffineTransform af = AffineTransform.getRotateInstance(angulo, eixox, eixoy);
                    AffineTransformOp afOp = new AffineTransformOp(af, interp);
                    BufferedImage bfImagemFinal = afOp.filter(bfImagem, null);
                    return bfImagemFinal;
                }
                case "3":
                {
                    AffineTransform af = AffineTransform.getScaleInstance(1, 10);
                    AffineTransformOp afOp = new AffineTransformOp(af, interp);
                    BufferedImage bfImagemFinal = afOp.filter(bfImagem, null);
                    return bfImagemFinal;
                }
                case "4":
                {
                    AffineTransform af = AffineTransform.getShearInstance(2, 3);
                    AffineTransformOp afOp = new AffineTransformOp(af, interp);
                    BufferedImage bfImagemFinal = afOp.filter(bfImagem, null);
                    return bfImagemFinal;
                }
                default:
                    JOptionPane.showMessageDialog(null, "Digite o número de uma das opções informadas");
                    System.exit(0);
                break;
            }
            
            return null;           
        }
}