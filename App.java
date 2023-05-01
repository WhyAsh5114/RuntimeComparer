import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import SortingAlgorithms.*;

public class App extends JPanel implements ActionListener {
    JRadioButton bubbleSortButton;
    JRadioButton mergeSortButton;
    JRadioButton selectionSortButton;
    JRadioButton quickSortButton;

    JButton startButton;
    GraphPanel graphArea;
    JProgressBar progressBar;

    public App() {
        super(new BorderLayout(10, 10));
        bubbleSortButton = new JRadioButton("Bubble Sort");
        bubbleSortButton.setActionCommand("Bubble Sort");
        bubbleSortButton.setSelected(false);

        mergeSortButton = new JRadioButton("Merge Sort");
        mergeSortButton.setActionCommand("Merge Sort");
        mergeSortButton.setSelected(false);

        selectionSortButton = new JRadioButton("Selection Sort");
        selectionSortButton.setActionCommand("Selection Sort");
        selectionSortButton.setSelected(false);

        quickSortButton = new JRadioButton("Quick Sort");
        quickSortButton.setActionCommand("Quick Sort");
        quickSortButton.setSelected(false);

        JPanel checkPanel = new JPanel(new GridLayout(1, 0));
        JRadioButton[] sortButtons = { bubbleSortButton, mergeSortButton, selectionSortButton, quickSortButton };
        ButtonGroup group = new ButtonGroup();
        for (JRadioButton button : sortButtons) {
            button.addActionListener(this);
            group.add(button);
            checkPanel.add(button);
        }

        startButton = new JButton("Select an algorithm");
        startButton.setFont(new Font("Calibri", 1, 20));
        startButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        startButton.setHorizontalTextPosition(AbstractButton.CENTER);
        startButton.setActionCommand("");
        startButton.addActionListener(this);

        graphArea = new GraphPanel(new ArrayList<Double>());
        graphArea.setPreferredSize(new Dimension(1000, 600));
        graphArea.setVisible(true);

        add(checkPanel, BorderLayout.NORTH);
        add(startButton, BorderLayout.SOUTH);
        add(graphArea, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != startButton) {
            startButton.setText("Run " + e.getActionCommand());
            startButton.setActionCommand(e.getActionCommand());
        } else if (!e.getActionCommand().equals("")) {
            String sortingAlgo = e.getActionCommand();
            try {
                String fileName = "SortingAlgorithms/allInputs.txt";
                if (sortingAlgo.equals("Bubble Sort") || sortingAlgo.equals("Selection Sort")) {
                    fileName = "SortingAlgorithms/slowInputs.txt";
                }
                FileReader inputFile = new FileReader(fileName);
                BufferedReader br = new BufferedReader(inputFile);
                String line;
                ArrayList<Double> timesTaken = new ArrayList<Double>();
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    int[] arr = new int[words.length];
                    for (int i = 0; i < words.length; i++) {
                        arr[i] = Integer.parseInt(words[i]);
                    }
                    Thread t1;
                    if (sortingAlgo.equals("Bubble Sort")) {
                        t1 = new BubbleSort(arr);
                    } else if (sortingAlgo.equals("Merge Sort")) {
                        t1 = new MergeSort(arr);
                    } else if (sortingAlgo.equals("Quick Sort")) {
                        t1 = new QuickSort(arr);
                    } else {
                        t1 = new SelectionSort(arr);
                    }
                    long startTime = System.currentTimeMillis();
                    t1.start();
                    t1.join();
                    long timeTaken = System.currentTimeMillis() - startTime;
                    timesTaken.add((double) timeTaken);
                }
                graphArea.setDataPoints(timesTaken);
                br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Runtime Comparer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new App());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
