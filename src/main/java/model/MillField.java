package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static model.Constants.N;

/**
 * mill
 * Created on 26.05.17.
 */

public class MillField {
    private List<List<Chip>> field = new ArrayList<>();

    public MillField() {
        for (int i = 0; i < 2*N + 1; ++i) {
            field.add(new ArrayList<>());
        }

        for (int i = 0; i < 2*N + 1; ++i) {
            if (i == N) {
                final List<Chip> chips = field.get(i);

                for (int j = 0; j < 2*N; ++j) {
                    chips.add(new Chip(Color.RED));
                }
            } else {
                final List<Chip> chips = field.get(i);

                for (int j = 0; j < N; ++j) {
                    chips.add(new Chip(Color.RED));
                }
            }
        }
    }

    public void paint() {
        for (int i = 0; i < N; ++i) {
            printList(field.get(i), (N - i)*5 );
            System.out.println();
        }
        printList(field.get(N), 1);
        System.out.println();
        for (int i = 0; i < N; ++i) {
            printList(field.get(N+i+1), (i+1)*5 );
            System.out.println();
        }
    }

    private static void printList(List<Chip> chips, int spaces) {
        chips.forEach(
                chip -> {
                    System.out.print(chip.getColor());
                    printSpaces(spaces);
                }
        );
    }

    private static void printSpaces(int spaces) {
        for (int i = 0; i < spaces; ++i) {
            System.out.print(' ');
        }
    }
}
