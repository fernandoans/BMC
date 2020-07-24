package canvas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.net.URL;
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
        super("BMC - Versão 0.50 - Fernando Anselmo © 2012");
        this.getContentPane().setLayout(null);
        this.setResizable(false);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new java.awt.Dimension(930, 635));
        this.setLocation((screenSize.width-935)/2,(screenSize.height-635)/2);

        // Objetos do Menu Interno
        popMenu = new JPopupMenu();
        JMenuItem itmObjetivo = new JMenuItem("Objetivo");
        JMenuItem itmAutor = new JMenuItem("Autor");
        JMenuItem itmData = new JMenuItem("Data");
        JMenuItem itmNumero = new JMenuItem("Número");
        JMenuItem itmPostIt = new JMenuItem("Post-It");
        JMenuItem itmAbrir = new JMenuItem("Abrir");
        JMenuItem itmSalvar = new JMenuItem("Salvar");

        // Ações do Menu

        itmObjetivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				obterObjetivo();
			}
		});
        itmAutor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				obterAutor();
			}
		});
        itmData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				obterData();
			}
		});
        itmNumero.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				obterNumero();
			}
		});
        itmPostIt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				adicionarPostIt();
			}
		});
        itmAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirArquivo();
			}
		});
        itmSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvarArquivo();
			}
		});
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                clkMouse(evt);
            }
        });
        
        // Imagem de Fundo
        JLabel lb = new JLabel("");
        lb.setIcon(getImage("ModelCanvas.png"));
        lb.setBounds(new Rectangle(0, -10, 930, 630));
        this.add(lb, null);

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

    public void paint(Graphics g) {
    	super.paint(g);
    	g.drawString(getObjetivo(), 320, 73);
    	g.drawString(getAutor(), 645, 73);
    	g.drawString(getData(), 857, 53);
    	g.drawString(getNumero(), 858, 75);
    }    
    
    // Métodos Eventos

    private void obterObjetivo() {
  		setObjetivo(JOptionPane.showInputDialog(this, "Informe o Objetivo", getObjetivo()));
    }
    
    private void obterAutor() {
    	setAutor(JOptionPane.showInputDialog(this, "Informe o Autor", getAutor()));
    }
    
    private void obterData() {
    	setData(JOptionPane.showInputDialog(this, "Informe a Data (DD MM AAAA)", getData()));
    }
    
    private void obterNumero() {
    	setNumero(JOptionPane.showInputDialog(this, "Informe o Número da Versão", getNumero()));
    }
    
    private void adicionarPostIt() {
    	String nome = JOptionPane.showInputDialog(this, "Descrição (máx. 2 palavras significativas)", "Conteúdo");
    	criarPostIt(nome, 50, 50);
	}
    private void abrirArquivo() {
    	TratarArquivo ta = new TratarArquivo(this);
    	ta.abrirArquivo();
    }
    private void salvarArquivo() {
    	TratarArquivo ta = new TratarArquivo(this);
    	ta.salvarArquivo();
    }

    // Métodos Auxiliares
    
    private void clkMouse(MouseEvent e) {
        if (e.isMetaDown()) {
            popMenu.show(this, e.getX(), e.getY());
        }
    }    
    
    public void criarPostIt(String nome, int posX, int posY) {
        PostIt pi = new PostIt(this);
        pi.setBounds(new Rectangle(posX, posY, 70, 40));
        new EventoMouse().insEvento(this, pi, nome, "Post-It");
        this.add(pi, null);
        this.refresh();
	}
    
    /** Devolve um objeto ImageIcon com determinada imagem
     * @param s String contendo o nome da imagem
     * @return Objeto ImageIcon
     */
    public ImageIcon getImage(String s) {
        URL url = getClass().getClassLoader().getResource("canvas/img/" + s);
        if (url != null)
            return new ImageIcon(url);
        return null;
    }
    
    public void refresh() {
        this.repaint();
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

	// Método Principal

	public static void main(String [] args) {
        new BMC().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }	
}