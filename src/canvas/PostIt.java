package canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class PostIt extends JPanel {

  private String nome1;
  private String nome2;
  private BMC bmc;
  private JPopupMenu popMenu;

  public PostIt(BMC bmc) {
    // do Menu do Objeto
    popMenu = new JPopupMenu();
    JMenuItem itmDescricao = new JMenuItem("Descri\347\343o");
    JMenuItem itmCor = new JMenuItem("Cor de Fundo");
    JMenuItem itmExcluir = new JMenuItem("Excluir");
    popMenu.add(itmDescricao);
    popMenu.add(itmCor);
    popMenu.add(itmExcluir);
    itmCor.addActionListener(e -> corObjeto());
    itmDescricao.addActionListener(e -> descObjeto());
    itmExcluir.addActionListener(e -> excObjeto());
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent evt) {
        clkMouse(evt);
      }
    });
    this.bmc = bmc;
    this.setSize(120, 40);
    this.setBackground(Color.YELLOW);
  }

  // Desenhar o objeto

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawLine(120, 0, 130, 10);
    g.drawRect(0, 0, 130, 40);
    quebrarNome();
    g.drawString(this.nome1, 3, 18);
    g.drawString(this.nome2, 3, 32);
  }

  private void corObjeto() {
    Color color = this.getBackground();
    color = JColorChooser.showDialog(null, "Escolha a Cor da Atividade", color);
    if (color != null)
      this.setBackground(color);
  }

  private void descObjeto() {
    String nome = JOptionPane.showInputDialog(this, "Descri\347\343o (m\341x. 2 palavras significativas)",
        nome1 + " " + nome2);
    if (nome != null) {
      this.setName(nome);
      this.repaint();
    }
  }

  private void excObjeto() {
    bmc.remove(this);
    bmc.repaint();
  }

  // Metodos Auxiliares

  private void clkMouse(MouseEvent e) {
    if (e.isMetaDown()) {
      popMenu.show(this, e.getX(), e.getY());
    }
  }

  private void quebrarNome() {
    String nome = this.getName();
    if (nome != null && nome.indexOf(' ') > -1) {
      nome1 = nome.substring(0, nome.indexOf(' '));
      nome2 = nome.substring(nome.indexOf(' ') + 1);
    } else {
      nome1 = nome;
      nome2 = "";
    }
  }
}