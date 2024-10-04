import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Topgpt extends JFrame {

    public Topgpt() {
        setTitle("Top Oyunu");
        setSize(1900, 1200);

        TopgptPanel topgptPanel = new TopgptPanel();
        add(topgptPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new Topgpt(); 
    }

    public class TopgptPanel extends JPanel implements ActionListener {

        private ArrayList<Top> toplar; 
        private Timer timer;

        public TopgptPanel() {
            toplar = new ArrayList<>();
            setBackground(Color.BLACK); 
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Random random = new Random();
                    toplar.add(new Top(e.getX(), e.getY(), random.nextInt(5) -1, random.nextInt(5) + 1, 2000)); 
                }
            });
            
            timer = new Timer(5, this); 
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Top top : toplar) {
                top.draw(g);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Top top : toplar) {
                top.move(getWidth(), getHeight());
            }
            repaint();  
        }
    }

    public class Top {
        private int x, y;  
        private double dx, dy;  
        private double radius;  
        private Color color; 
        private Random random;

        public Top(int x, int y, int dx, int dy, double radius) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.radius = radius;
            this.random = new Random();
            this.color = getRandomColor();  
        }

        public void move(int width, int height) {
            x += dx;
            y += dy;
            
            if (x < 0 || x + radius > width) {
                dx = -dx; 
            }
            
            if (y < 0 || y + radius > height) {
                if (radius > 1) {
                    radius *= 0.9; 
                }
                dy = -dy * 1.1;  
                this.color = getRandomColor();  
            }
        }

        private Color getRandomColor() {
            Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN };
            return colors[random.nextInt(colors.length)];
        }

        public void draw(Graphics g) {
            g.setColor(color);  
            g.fillOval(x, y, (int) radius, (int) radius);  
        }
    }
}
