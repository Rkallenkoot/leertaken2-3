import javax.swing.*;
import java.awt.*;

public class DobbelsteenMVC extends JApplet {

    private DobbelsteenModel model;
    private TekstView tekstView;
    private DobbelsteenView dobbelsteenView;
    private DobbelsteenController controller;
    private DobbelsteenStatisticsView statisticsView;

    public void init() {
        resize(250, 225);

        model = new DobbelsteenModel();

        controller = new DobbelsteenController(model);
        controller.setBackground(Color.cyan);
        getContentPane().add(controller, BorderLayout.NORTH);

        dobbelsteenView = new DobbelsteenView(Color.red);
        dobbelsteenView.setBackground(Color.black);
        getContentPane().add(dobbelsteenView, BorderLayout.CENTER);

        statisticsView = new DobbelsteenStatisticsView();
        getContentPane().add(statisticsView, BorderLayout.EAST);

        tekstView = new TekstView();
        getContentPane().add(tekstView, BorderLayout.SOUTH);

        // Registreer de views bij het model
        model.addActionListener(tekstView);
        model.addActionListener(dobbelsteenView);
        model.addActionListener(statisticsView);

//        model.addActionListener(mytekstview);

        // Eerste worp
        model.gooi();
    }
}
