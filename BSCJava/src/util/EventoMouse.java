package util;

import javax.swing.*;

import canvas.PostIt;

import java.awt.*;
import java.awt.event.*;

public class EventoMouse {

    // private JComponent cmpSelecionado;
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
            public void mouseClicked(MouseEvent evt) {
                //aoClicarMouse(evt);
            }
            public void mousePressed(MouseEvent evt) {
                aoPressionarMouse(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                aoSoltarMouse(evt);
            }
            public void mouseEntered(MouseEvent evt) {
            }
            public void mouseExited(MouseEvent evt) {
            }
        });
        cmp.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent evt) {
                aoArrastarMouse(evt);
            }
            public void mouseMoved(MouseEvent evt) {
                aoMoverMouse(evt);
            }
        });
        // cmpSelecionado = cmp;
    }

    /*
    private void excObjeto(ActionEvent e) {
        if (cmpSelecionado != null) {
            pai.remove(cmpSelecionado);
            pai.repaint();
        }
    }
    */
    
    private void aoPressionarMouse(MouseEvent evt) {
        Component cmp = evt.getComponent();
        if (cmp != null) {
            startMove(cmp, evt.getX(), evt.getY());
        }
    }
    
    private void aoSoltarMouse(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        PostIt cmp = (PostIt)evt.getComponent();
        if (cmp != null) {
            deleteCaixaMov();
            finishMove(x, y);
            pai.repaint();
            System.out.println(cmp.getX() + " " + cmp.getY());
        }
    }
    
    private synchronized void aoArrastarMouse(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        Component cmp = evt.getComponent();
        if (cmp != null)
            drawCaixaMov(getMoveBox(x+2, y+23));
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
                g.drawRect(oldCaixaMov.x, oldCaixaMov.y,
                oldCaixaMov.width, oldCaixaMov.height);
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