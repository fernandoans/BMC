package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

import canvas.PostIt;

public class EventoMouse {

  private JFrame pai;
  private Rectangle oldCaixaMov;
  private Component cmpMove;
  private int moveStartX;
  private int moveStartY;

  public void insEvento(JFrame pai, JComponent cmp, String nome, String hint) {
    this.pai = pai;
    cmp.setName(nome);
    cmp.setToolTipText(hint);
    // Eventos de Mouse
    cmp.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent evt) {
        aoPressionarMouse(evt);
      }

      @Override
      public void mouseReleased(MouseEvent evt) {
        aoSoltarMouse(evt);
      }
    });
    cmp.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent evt) {
        aoArrastarMouse(evt);
      }

      @Override
      public void mouseMoved(MouseEvent evt) {
        aoMoverMouse(evt);
      }
    });
  }

  private void aoPressionarMouse(MouseEvent evt) {
    Component cmp = evt.getComponent();
    if (cmp != null) {
      startMove(cmp, evt.getX(), evt.getY());
    }
  }

  private void aoSoltarMouse(MouseEvent evt) {
    int x = evt.getX();
    int y = evt.getY();
    PostIt cmp = (PostIt) evt.getComponent();
    if (cmp != null) {
      deleteCaixaMov();
      finishMove(x, y);
      pai.repaint();
    }
  }

  private synchronized void aoArrastarMouse(MouseEvent evt) {
    int x = evt.getX();
    int y = evt.getY();
    Component cmp = evt.getComponent();
    if (cmp != null)
      drawCaixaMov(getMoveBox(x + 2, y + 23));
  }

  private synchronized void aoMoverMouse(MouseEvent evt) {
    Component cmp = evt.getComponent();
    if (cmp != null)
      startMove(cmp, evt.getX(), evt.getY());
  }

  private void drawCaixaMov(Rectangle box) {
    Graphics g = pai.getGraphics();
    if (g != null) {
      deleteCaixaMov();
      g.setColor(Color.red);
      g.drawRect(box.x, box.y, box.width, box.height);
      oldCaixaMov = box;
    }
  }

  private void deleteCaixaMov() {
    if (oldCaixaMov != null) {
      Graphics g = pai.getGraphics();
      if (g != null) {
        g.setColor(pai.getBackground());
        g.drawRect(oldCaixaMov.x, oldCaixaMov.y, oldCaixaMov.width, oldCaixaMov.height);
        oldCaixaMov = null;
      }
    }
  }

  private void startMove(Component child, int x, int y) {
    cmpMove = child;
    moveStartX = x;
    moveStartY = y;
  }

  private Rectangle getMoveBox(int mx, int my) {
    int x = cmpMove.getLocation().x;
    int y = cmpMove.getLocation().y;
    int w = cmpMove.getSize().width;
    int h = cmpMove.getSize().height;
    x = x + mx - moveStartX;
    y = y + my - moveStartY;
    return new Rectangle(x, y, w, h);
  }

  private void finishMove(int mx, int my) {
    Rectangle box = getMoveBox(mx, my);
    cmpMove.setBounds(box.x, box.y, box.width, box.height);
    deleteCaixaMov();
    cmpMove = null;
  }
}