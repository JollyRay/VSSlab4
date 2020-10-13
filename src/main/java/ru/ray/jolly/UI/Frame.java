package ru.ray.jolly.UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import ru.ray.jolly.Accountant.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Frame {
    private static JFrame jFrame;
    private static JPanel graphPanel = new JPanel();
    private static JPanel inPutPanel= new JPanel();
    private static JTextField x0Field = new JTextField(5);
    private static JTextField y0Field = new JTextField(5);
    private static JTextField x1Field = new JTextField(5);
    private static JTextField accuracyField = new JTextField(8);
    private static JComboBox comboBox = new JComboBox(new String[]{"y'=sin(x)-y", "y'=e^(x)-y", "y'=4y-x", "y'=4x"});

    public static void createFrame() {
        jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Lab4 Kamyshnikov Vlad");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 500, dimension.height/2 - 250, 1000, 540);
        Container container = jFrame.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new BorderLayout());

        installationGraphPanel();
        installationInPutPanel();

    }

    private static void installationGraphPanel(){
        graphPanel.setMinimumSize(new Dimension(1000, 470));
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        jFrame.add(graphPanel, BorderLayout.CENTER);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "", "X", "Y", null,
                PlotOrientation.VERTICAL, true, false, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        graphPanel.add(chartPanel);
        graphPanel.revalidate();
    }

    private static void installationInPutPanel(){
        inPutPanel.setMinimumSize(new Dimension(1000, 70));
        inPutPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        jFrame.add(inPutPanel, BorderLayout.SOUTH);

        inPutPanel.add(new JLabel("X0"));
        inPutPanel.add(x0Field);
        inPutPanel.add(new JLabel("Y0"));
        inPutPanel.add(y0Field);
        inPutPanel.add(new JLabel("X1"));
        inPutPanel.add(x1Field);
        inPutPanel.add(new Label("Точсность"));
        inPutPanel.add(accuracyField);
        inPutPanel.add(comboBox);
        inPutPanel.add(new JButton(new CreatorAction()));

        inPutPanel.revalidate();
    }

    private  static class CreatorAction extends AbstractAction{
        public CreatorAction() {
            putValue(AbstractAction.NAME, "Построить");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double x0, x1, y0, accuracy;
                x0 = Double.parseDouble(x0Field.getText());
                x1 = Double.parseDouble(x1Field.getText());
                y0 = Double.parseDouble(y0Field.getText());
                accuracy = Double.parseDouble(accuracyField.getText());
                graphPanel.remove(0);
                Calculator calculator = new Calculator(x0, y0, x1, accuracy, comboBox.getSelectedIndex());
                JFreeChart chart = ChartFactory.createXYLineChart(
                        "", "X", "Y", calculator.getDataset(),
                        PlotOrientation.VERTICAL, true, false, false);
                graphPanel.add(new ChartPanel(chart));
                graphPanel.revalidate();
            }catch (Exception ex){
                x0Field.setText("");
                x1Field.setText("");
                y0Field.setText("");
                accuracyField.setText("");
            }
        }
    }
}
