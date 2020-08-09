package canvas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import util.Atributo;
import util.EventoMouse;
import util.TratarArquivo;

@SuppressWarnings("serial")
public class BMC extends JFrame {

  private JPopupMenu popMenu;
  // Campos
  private String objetivo;
  private String autor;
  private String data;
  private String numero;

  public BMC() {
    super(Atributo.TITULO);
    this.setSize(930, 650);
    this.setLocationRelativeTo(null);

    // Objetos do Menu Interno
    popMenu = new JPopupMenu();
    JMenuItem itmObjetivo = new JMenuItem("Objetivo");
    JMenuItem itmAutor = new JMenuItem("Autor");
    JMenuItem itmData = new JMenuItem("Data");
    JMenuItem itmNumero = new JMenuItem("N\372mero");
    JMenuItem itmPostIt = new JMenuItem("Post-It");
    JMenuItem itmAbrir = new JMenuItem("Abrir");
    JMenuItem itmSalvar = new JMenuItem("Salvar");

    // Eventos do Menu
    itmObjetivo.addActionListener(e -> obterObjetivo());
    itmAutor.addActionListener(e -> obterAutor());
    itmData.addActionListener(e -> obterData());
    itmNumero.addActionListener(e -> obterNumero());
    itmPostIt.addActionListener(e -> adicionarPostIt());
    itmAbrir.addActionListener(e -> abrirArquivo());
    itmSalvar.addActionListener(e -> salvarArquivo());
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent evt) {
        clkMouse(evt);
      }
    });

    // Imagem de Fundo
    JLabel lb = new JLabel("");
    lb.setIcon(getImage("ModelCanvas.png"));
    lb.setBounds(new Rectangle(0, -10, 930, 630));
    this.add(lb, null);
    
    JPanel pnInferior = new JPanel();
    pnInferior.add(new JLabel(Atributo.COPYRIGHT));
    this.add(pnInferior, BorderLayout.SOUTH);

    // Montagem do Menu
    popMenu.add(itmObjetivo);
    popMenu.add(itmAutor);
    popMenu.add(itmData);
    popMenu.add(itmNumero);
    popMenu.add(itmPostIt);
    popMenu.addSeparator();
    popMenu.add(itmAbrir);
    popMenu.add(itmSalvar);

    this.setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setFont(new Font(g.getFont().getFamily(), Font.BOLD, 14));
    g.drawString(getObjetivo(), 320, 75);
    g.drawString(getAutor(), 645, 75);
    g.setFont(new Font(g.getFont().getFamily(), Font.BOLD, 10));
    g.drawString(getData(), 854, 54);
    g.drawString(getNumero(), 858, 76);
  }

  // M�todos Eventos

  private void obterObjetivo() {
    this.setObjetivo((String)JOptionPane.showInputDialog(this, "Informe o Objetivo", Atributo.TITULO, 3, null, null, getObjetivo()));
  }

  private void obterAutor() {
    this.setAutor((String)JOptionPane.showInputDialog(this, "Informe o Autor", Atributo.TITULO, 3, null, null, getAutor()));
  }

  private void obterData() {
    this.setData((String)JOptionPane.showInputDialog(this, "Informe a Data (DD MM AAAA)", Atributo.TITULO, 3, null, null, getData()));
  }

  private void obterNumero() {
    this.setNumero((String)JOptionPane.showInputDialog(this, "Informe o N\372mero da Vers\343o", Atributo.TITULO, 3, null, null, getNumero()));
  }

  private void adicionarPostIt() {
    String nome = JOptionPane.showInputDialog(this, "Descri\347\343o (m\341x. 2 palavras significativas)", "Conte\372do");
    if (nome != null) {
      criarPostIt(nome, 50, 50);
    }
  }

  private void abrirArquivo() {
    TratarArquivo ta = new TratarArquivo(this);
    ta.abrirArquivo();
  }

  private void salvarArquivo() {
    if (objetivo != null && autor != null && data != null && numero != null) {
      TratarArquivo ta = new TratarArquivo(this);
      ta.salvarArquivo();
    } else {
      JOptionPane.showMessageDialog(null, "Preencha ao menos Objetivo, Autor, Data e N\372mero", Atributo.TITULO, 2);
    }
  }

  // Metodos Auxiliares

  private void clkMouse(MouseEvent e) {
    if (e.isMetaDown()) {
      popMenu.show(this, e.getX(), e.getY());
    }
  }

  public void criarPostIt(String nome, int posX, int posY) {
    PostIt pi = new PostIt(this);
    pi.setBounds(new Rectangle(posX, posY, 120, 40));
    new EventoMouse().insEvento(this, pi, nome, "Post-It");
    this.add(pi, null);
    this.repaint();
  }

  /**
   * Devolve um objeto ImageIcon com determinada imagem
   * 
   * @param s String contendo o nome da imagem
   * @return Objeto ImageIcon
   */
  public ImageIcon getImage(String s) {
    URL url = getClass().getClassLoader().getResource("canvas/img/" + s);
    if (url != null)
      return new ImageIcon(url);
    return null;
  }

  // Campos de Tela

  public String getObjetivo() {
    if (objetivo == null)
      return "";
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    if (objetivo != null) {
      this.objetivo = objetivo;
      repaint();
    }
  }

  public String getAutor() {
    if (autor == null)
      return "";
    return autor;
  }

  public void setAutor(String autor) {
    if (autor != null) {
      this.autor = autor;
      repaint();
    }
  }

  public String getData() {
    if (data == null)
      return "";
    return data;
  }

  public void setData(String data) {
    if (data != null) {
      this.data = data;
      repaint();
    }
  }

  public String getNumero() {
    if (numero == null)
      return "";
    return numero;
  }

  public void setNumero(String numero) {
    if (numero != null) {
      this.numero = numero;
      repaint();
    }
  }

  public static void main(String[] args) {
    new BMC().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}