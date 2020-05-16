package io.github.XaNNy0;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

final class str8ts extends JPanel {

    private static final Font FONT = new Font("Verdana", Font.BOLD, 10);
    private final JTextField infoField;
    private final JTextField[][] grid;
    private final Map<JTextField, Point> mapFieldToCoordinates = new HashMap<>();
    private final int dimension;
    private final JPanel gridPanel;
    private final JPanel infoPanel;
    private final JPanel buttonPanel;
    private final JButton solveButton;
    private final JButton solveCompleteButton;
    private final JPanel[][] minisquarePanels;

    str8ts(final int dimension, final Board board) {
        this.grid = new JTextField[dimension][dimension];
        this.dimension = dimension;

        this.gridPanel = new JPanel();
        this.infoPanel = new JPanel();
        this.buttonPanel = new JPanel();

        final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        final Dimension fieldDimension = new Dimension(70, 70);

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                final JTextField field = new JTextField();
                this.mapFieldToCoordinates.put(field, new Point(x, y));
                this.grid[y][x] = field;
                field.setBorder(border);
                field.setFont(FONT);
                field.setPreferredSize(fieldDimension);
            }
        }


        final int minisquareDimension = (int) Math.sqrt(dimension);
        this.gridPanel.setLayout(new GridLayout(minisquareDimension, minisquareDimension));
        this.minisquarePanels = new JPanel[minisquareDimension][minisquareDimension];

        for (int y = 0; y < minisquareDimension; ++y) {
            for (int x = 0; x < minisquareDimension; ++x) {
                final JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(minisquareDimension, minisquareDimension));
                this.minisquarePanels[y][x] = panel;
                this.gridPanel.add(panel);
            }
        }

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                final int minisquareX = x / minisquareDimension;
                final int minisquareY = y / minisquareDimension;

                this.minisquarePanels[minisquareY][minisquareX].add(this.grid[y][x]);
            }
        }
        this.solveButton = new JButton("Solve");
        this.solveCompleteButton = new JButton("SolveComplete");
        this.buttonPanel.setLayout(new BorderLayout());
        this.buttonPanel.add(this.solveButton, BorderLayout.EAST);
        this.buttonPanel.add(this.solveCompleteButton, BorderLayout.WEST);

        this.infoField = new JTextField();
        this.infoField.setHorizontalAlignment(JTextField.CENTER);
        this.infoField.setEditable(false);
        this.infoPanel.setLayout(new BorderLayout());
        this.infoPanel.add(this.infoField, BorderLayout.CENTER);

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setLayout(new BorderLayout());
        this.add(this.infoPanel, BorderLayout.NORTH);
        this.add(this.gridPanel, BorderLayout.CENTER);
        this.add(this.buttonPanel, BorderLayout.SOUTH);


        this.solveButton.addActionListener((ActionEvent e) -> {
            board.nextStep();
            this.refreshGrid(dimension, board);
        });

        this.solveCompleteButton.addActionListener((ActionEvent e) -> {
            board.nextStep();
            this.refreshGridDebug(dimension, board);
        });

    }

    private void refreshGrid(final int dimension, final Board board) {
        if (board.getCurrentStep() != null) {
            this.infoField.setText(board.getCurrentStep().getDescription() + " - Solved: " + board.isCurrentStepSolved());
        }
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                final JTextField currentField = this.grid[x][y];
                if (!board.getFields().getArray()[x][y].getCandidates().isEmpty() && board.getFields().getArray()[x][y].getCandidates().size() > 1) {
                    currentField.setText(board.getFields().getArray()[x][y].getCandidates().toString());
                } else {
                    currentField.setText(board.getFields().getArray()[x][y].getValue() + "");
                    currentField.setEditable(false);
                    currentField.setHorizontalAlignment(JTextField.CENTER);
                    currentField.setForeground(Color.BLACK);
                    currentField.setBackground(Color.WHITE);
                    currentField.setFont(new Font("Verdana", Font.PLAIN, 30));
                }
                if (board.getFields().getArray()[x][y].isBlack()) {
                    currentField.setEditable(false);
                    currentField.setForeground(Color.WHITE);
                    currentField.setHorizontalAlignment(JTextField.CENTER);
                    currentField.setBackground(Color.BLACK);
                    if (board.getFields().getArray()[x][y].getValue() == 0) {
                        currentField.setText("");
                    } else {
                        currentField.setText(board.getFields().getArray()[x][y].getValue() + "");
                    }
                }
            }
        }
    }

    private void refreshGridDebug(final int dimension, final Board board) {
        if (board.getCurrentStep() != null) {
            this.infoField.setText(board.getCurrentStep().getDescription() + " - Solved: " + board.isCurrentStepSolved());
        }
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                final JTextField currentField = this.grid[x][y];
                currentField.setText(board.getFields().getArray()[x][y].getValue() + "");
                currentField.setEditable(false);
                currentField.setHorizontalAlignment(JTextField.CENTER);
                currentField.setForeground(Color.BLACK);
                currentField.setBackground(Color.WHITE);
                currentField.setFont(new Font("Verdana", Font.PLAIN, 30));

                if (board.getFields().getArray()[x][y].isBlack()) {
                    currentField.setEditable(false);
                    currentField.setForeground(Color.WHITE);
                    currentField.setHorizontalAlignment(JTextField.CENTER);
                    currentField.setBackground(Color.BLACK);
                    if (board.getFields().getArray()[x][y].getValue() == 0) {
                        currentField.setText("");
                    } else {
                        currentField.setText(board.getFields().getArray()[x][y].getValue() + "");
                    }
                }
            }
        }
    }
}
