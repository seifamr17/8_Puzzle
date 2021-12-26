import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Home
 */
public class Main extends JPanel {
    
    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        JPanel panel = new JPanel();
        
        String fileName = "Input.txt";
        String line = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        line=lines.get(0);
        String text[] = line.split(",");        
        
        byte[][] inputCellDfs = new byte[3][3];
        inputCellDfs[0][0] = (byte)Integer.parseInt(text[0]);
        inputCellDfs[0][1] = (byte)Integer.parseInt(text[1]);
        inputCellDfs[0][2] = (byte)Integer.parseInt(text[2]);
        inputCellDfs[1][0] = (byte)Integer.parseInt(text[3]);
        inputCellDfs[1][1] = (byte)Integer.parseInt(text[4]);
        inputCellDfs[1][2] = (byte)Integer.parseInt(text[5]);
        inputCellDfs[2][0] = (byte)Integer.parseInt(text[6]);
        inputCellDfs[2][1] = (byte)Integer.parseInt(text[7]);
        inputCellDfs[2][2] = (byte)Integer.parseInt(text[8]);
        
        byte[][] inputCellAM = new byte[3][3];
        inputCellAM[0][0] = (byte)Integer.parseInt(text[0]);
        inputCellAM[0][1] = (byte)Integer.parseInt(text[1]);
        inputCellAM[0][2] = (byte)Integer.parseInt(text[2]);
        inputCellAM[1][0] = (byte)Integer.parseInt(text[3]);
        inputCellAM[1][1] = (byte)Integer.parseInt(text[4]);
        inputCellAM[1][2] = (byte)Integer.parseInt(text[5]);
        inputCellAM[2][0] = (byte)Integer.parseInt(text[6]);
        inputCellAM[2][1] = (byte)Integer.parseInt(text[7]);
        inputCellAM[2][2] = (byte)Integer.parseInt(text[8]);
        
        byte[][] inputCellAE = new byte[3][3];
        inputCellAE[0][0] = (byte)Integer.parseInt(text[0]);
        inputCellAE[0][1] = (byte)Integer.parseInt(text[1]);
        inputCellAE[0][2] = (byte)Integer.parseInt(text[2]);
        inputCellAE[1][0] = (byte)Integer.parseInt(text[3]);
        inputCellAE[1][1] = (byte)Integer.parseInt(text[4]);
        inputCellAE[1][2] = (byte)Integer.parseInt(text[5]);
        inputCellAE[2][0] = (byte)Integer.parseInt(text[6]);
        inputCellAE[2][1] = (byte)Integer.parseInt(text[7]);
        inputCellAE[2][2] = (byte)Integer.parseInt(text[8]);
        
        byte[][] goalCell = new byte[3][3];
        goalCell[0][0] = 0;
        goalCell[0][1] = 1;
        goalCell[0][2] = 2;
        goalCell[1][0] = 3;
        goalCell[1][1] = 4;
        goalCell[1][2] = 5;
        goalCell[2][0] = 6;
        goalCell[2][1] = 7;
        goalCell[2][2] = 8;
              
        //A* MANATTAN RUN
        System.out.println("AStar Agent Manhattan");
        long startTimeAStarManhattan = System.nanoTime();
  //      AStarAgent agent = new AStarAgent(inputCellAM,goalCell, "Manhattan");
        long endTimeAStarManhattan   = System.nanoTime();
        long totalTimeAStarManhattan = endTimeAStarManhattan - startTimeAStarManhattan;
        System.out.println("Time for AStar Manhattan Heuristic(nanoseconds)="+totalTimeAStarManhattan);
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println();
        
        //A* EUCLIDEAN RUN
        System.out.println("AStar Agent Euclidean");
        long startTimeAStarEuclidean = System.nanoTime();
 //       AStarAgent agent2 = new AStarAgent(inputCellAE,goalCell, "Euclidean");
        long endTimeAStarEuclidean   = System.nanoTime();
        long totalTimeAStarEuclidean = endTimeAStarEuclidean - startTimeAStarEuclidean;
        System.out.println("Time for AStar Euclidean Heuristic(nanoseconds)="+totalTimeAStarEuclidean);
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println();
        
        //DFS RUN
        System.out.println("DFS Agent");
        long startTimeDFS = System.nanoTime();
        DfsAgent agent3=new DfsAgent(inputCellDfs,goalCell);
        long endTimeDFS   = System.nanoTime();
        long totalTimeDFS = endTimeDFS - startTimeDFS;
        System.out.println("Time for DFS(nanoseconds)="+totalTimeDFS);
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println();
//               
//        int size = agent.getPathStates().size();
//        panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, size * 200));
//        int n = 0;
//        for (int i = size - 1; i >= 0; i--) {
//
//            AStar_State state = agent.getPathStates().get(i);
//            for (int k = 0; k < 3; k++) {
//                for (int j = 0; j < 3; j++) {
//                    
//                    Button b = new Button("");
//                    if (state.getCellNode()[k][j] == 0) {
//                        b.setBackground(Color.BLUE);
//                    } else {
//                        b = new Button(Integer.toString(state.getCellNode()[k][j]));
//                        b.setBackground(Color.white);
//                    }
//                    panel.setLayout(null);
//                    b.setBounds(50 * j, 50 * n + (10 - i) * 20, 50, 50);
//                    panel.add(b);
//                }
//                n++;
//            }
//        }
//        JScrollPane pane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        frame.add(pane);
//        frame.setVisible(true);
        
    }
    
}
