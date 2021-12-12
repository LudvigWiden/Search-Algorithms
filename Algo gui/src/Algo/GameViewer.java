package Algo;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class GameViewer implements ActionListener {



    private static final int CLOCK = 25;

    private static final String TITLE = "Algos";

    private final JFrame frame;
    private final Board board;
    private JButton oldButton;

    public GameViewer(final Board board) {
        this.frame = new JFrame(TITLE);
        this.board = board;
        GameComponent component = new GameComponent(board);
        MouseManager mm = new MouseManager(board);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        board.addBoardListener(component);

        frame.add(component, BorderLayout.CENTER);
        component.addMouseListener(mm);
        component.addMouseMotionListener(mm);


        JPanel p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setLayout(new FlowLayout());
        p.setBorder(new EmptyBorder(10,10,10,10));

        frame.getContentPane().add(p, BorderLayout.NORTH);

        int i = 0;
        List<String> algos = List.of("BFS", "DFS", "DIJKSTRA", "A-STAR", "RUN", "START", "OBSTACLE", "END");
        while (!algos.get(i).equals("OBSTACLE")) {
            final JButton button = new JButton(algos.get(i));
            button.setPreferredSize(new Dimension(120,40));
            button.setFont(new Font("Calibri", Font.PLAIN, 14));
            button.setBackground(new Color(0x2dce98));
            button.setForeground(Color.white);
            // customize the button with your own look
            button.setUI(new StyledButtonUI());
            p.add(button);
            if(i==0) {
                highlightButton(button);
                oldButton = button;
            }
            button.addActionListener(this);
            i++;
        }


        init();

        /* Start the timer which updates the game every tick.*/
        final Action doOneStep = new AbstractAction() {
            @Override

            public void actionPerformed(ActionEvent actionEvent) {
                board.tick();

            }
        };

        final Timer clockTimer = new Timer((CLOCK), doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    /** Initializes the frame */
    public void init(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (e.getActionCommand()) {
            case "DFS" -> {
                highlightButton(button);
                board.setAlg(Algos.DFS);
                oldButton = button;
            }
            case "BFS" -> {
                highlightButton(button);
                board.setAlg(Algos.BFS);
                oldButton = button;
            }
            case "A-STAR" -> {
                highlightButton(button);
                board.setAlg(Algos.ASTAR);
                oldButton = button;
            }
            case "DIJKSTRA" -> {
                highlightButton(button);
                board.setAlg(Algos.DIJKSTRA);
                oldButton = button;
            }
            case "RUN" -> board.setRunning(true);
            case "START" -> {
                board.setTileID(Tile.OBSTACLE);
                button.setText("OBSTACLE");
            }
            case "OBSTACLE" -> {
                board.setTileID(Tile.END);
                button.setText("END");
            }
            case "END" -> {
                board.setTileID(Tile.START);
                button.setText("START");
            }
            default -> {
            }
        }
    }


    public void highlightButton(JButton button){
        button.setBackground(new Color(0xFDA1C9B6, true));
        button.setForeground(Color.white);
        if(oldButton != null && button != oldButton) {
            oldButton.setBackground(new Color(0x2dce98));
            oldButton.setForeground(Color.white);
        }
    }
}
