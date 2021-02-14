
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;


/**
 *
 * @author MATEUS
 */
public class FiltrosDirecionais extends JComponent {
    
    BufferedImage src = null;
    int w, h;
    private JComboBox opcoes;
    
    public static void main(String[] args) {
        
        File file = null;
        int result;
        
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Abre imagem");
        result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        } else {
            System.out.println("Escolher imagem");
            System.exit(0);
        }
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        JFrame f = new JFrame("Filtros Direcionais");
        f.getContentPane().add(new FiltrosDirecionais(file));
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 320);
        f.setVisible(true);        
        
    }
    
    public FiltrosDirecionais(File file){
        
        int nc;
        
        try {
            src = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Imagem '" + file + "' nao existe");
            System.exit(0);
        }
        
        w = src.getWidth();
        h = src.getHeight();
        
        setLayout(new BorderLayout());
        
        JPanel controle = new JPanel();
        
        opcoes = new JComboBox(new String[] {"Imagem", "Norte", "Sul", "Leste", "Oeste"});
        
        opcoes.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                repaint();
            }
        });
        
        controle.add(opcoes);
        add(controle, BorderLayout.SOUTH);        
        
}
    
    public void paintComponent(Graphics gc)  {
    
        super.paintComponent(gc);
        Graphics2D g2 = (Graphics2D) gc;
        
        String opcao = (String)opcoes.getSelectedItem();
        
        if (opcao.equals("Imagem")){
            g2.drawImage(src, 0,0, null);
        }
        
        if (opcao.equals("Norte")) {
            long eq_time = System.currentTimeMillis();
            
            Raster srcR = src.getRaster();                        
            WritableRaster WR = null;
            BufferedImage norteImagem = null;
            
            float matrizNorte [] = {
                1f, 1f, 1f,
                1f, -2f, 1f,
                -1f, -1f, -1f
            };
            
            Kernel filtroNorte = new Kernel(3, 3, matrizNorte);
            
            ConvolveOp norte = new ConvolveOp(filtroNorte);
            WR = norte.filter(srcR, null);
            norteImagem = new BufferedImage(src.getColorModel(), WR, false, null);
            g2.drawImage(norteImagem, 0,0, null);
            eq_time = System.currentTimeMillis() - eq_time;
            String msg = "Norte: tempo de execucao";
            System.out.println(msg + eq_time + " milisseg");
            return;
            
        }
        
        if (opcao.equals("Sul")) {
            long eq_time = System.currentTimeMillis();
            
            Raster srcR = src.getRaster();
            WritableRaster WR = null;
            BufferedImage sulImagem = null;
            
            float matrizSul [] = {
                -1f, -1f, -1f,
                1f, -2f, 1f,
                1f, 1f, 1f
            };
            
            Kernel filtroSul = new Kernel(3, 3, matrizSul);
            
            ConvolveOp sul = new ConvolveOp(filtroSul);
            WR = sul.filter(srcR, null);
            sulImagem = new BufferedImage(src.getColorModel(), WR, false, null);
            g2.drawImage(sulImagem, 0,0, null);
            eq_time = System.currentTimeMillis() - eq_time;
            String msg = "Sul: tempo de execucao";
            System.out.println(msg + eq_time + " milisseg");
            return;
        }
        
        if (opcao.equals("Leste")) {
            long eq_time = System.currentTimeMillis();
            
            Raster srcR = src.getRaster();
            WritableRaster WR = null;
            BufferedImage lesteImagem = null;
            
            float matrizLeste [] = {
                -1f, 1f, 1f,
                -1f, -2f, 1f,
                -1f, 1f, 1f
            };
            
            Kernel filtroLeste = new Kernel(3, 3, matrizLeste);
            
            ConvolveOp leste = new ConvolveOp(filtroLeste);
            WR = leste.filter(srcR, null);
            lesteImagem = new BufferedImage(src.getColorModel(), WR, false, null);
            g2.drawImage(lesteImagem, 0,0, null);
            eq_time = System.currentTimeMillis() - eq_time;
            String msg = "Leste: tempo de execucao";
            System.out.println(msg + eq_time + " milisseg");
            return;
        }
        
        if (opcao.equals("Oeste")) {
            long eq_time = System.currentTimeMillis();
            
            Raster srcR = src.getRaster();
            WritableRaster WR = null;
            BufferedImage oesteImagem = null;
            
            float matrizOeste [] = {
                1f, 1f, -1f,
                1f, -2f, -1f,
                1f, 1f, -1f
            };
            
            Kernel filtroOeste = new Kernel(3, 3, matrizOeste);
            
            ConvolveOp oeste = new ConvolveOp(filtroOeste);
            WR = oeste.filter(srcR, null);
            oesteImagem = new BufferedImage(src.getColorModel(), WR, false, null);
            g2.drawImage(oesteImagem, 0,0, null);
            eq_time = System.currentTimeMillis() - eq_time;
            String msg = "Oeste: tempo de execucao";
            System.out.println(msg + eq_time + " milisseg");
            return;
        }
        
    }
    
    
}
