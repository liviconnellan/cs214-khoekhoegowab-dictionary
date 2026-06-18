/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: Khoe.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * This is the main entry point of the program. It reads input data,
 * processes dictionary entries using the Manager class, and handles
 * output based on the hand-in mode (printing dictionary or performing searches).
 */

public class Khoe {

    /**
     * Main entry point for the dictionary program.
     *
     * Reads the input file, processes all dictionary lines, and either prints
     * the dictionary or enters search mode depending on the handin argument.
     *
     * @param args Command-line arguments:
     *             mode, input file, handin number, and optional search type
     */
    public static void main(String[] args) {
        String mode = args[0];
        String inputFile = args[1];
        int handin = Integer.parseInt(args[2]);

        if (!mode.equals("noGUI")) {
            return;
        }

        In in = new In(inputFile);

        while (!in.isEmpty()) {
            String line = in.readLine();

            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            Manager.processLine(line);
        }

        if (handin == 1) {
            Manager.printDictionary();

        } else if (handin == 2) {
            String searchType = args[3];
            In stdin = new In();

            while (!stdin.isEmpty()) {
                String query = stdin.readLine();

                if (query == null) {
                    break;
                }

                if (query.trim().isEmpty()) {
                    continue;
                }
                Manager.search(query, searchType);
                
            }
        }
    }
}
