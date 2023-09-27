import java.io.*;
import java.util.ArrayList;

public class Points {
    static boolean wildcardControl = false;
    static char wildcardSuit = 0;
    static int wildcardPoints = 0;
    static boolean cardControl = false;
    static char wildcardFace = 0;
    static int cardpoints = 0;
    boolean didGetPoints;
    int getTotalPointCards;


    public int getTotalPointCardsFromBoard() {
        int i = 0;
        getTotalPointCards = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("points.txt"))) {
            String line;
            while (i < Players.Board.size()) {
                didGetPoints = false;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 2) {
                        String card = parts[0];
                        int points = Integer.parseInt(parts[1]);
                        processCard(card, points);
                    }
                    String card = parts[0];
                    if (wildcardControl && wildcardSuit == Players.Board.get(i).charAt(0)) {
                        getTotalPointCards += processCard(Players.Board.get(i), wildcardPoints);
                        didGetPoints = true;
                        break;
                    } else if (cardControl && wildcardFace == Players.Board.get(i).charAt(1)) {
                        getTotalPointCards += processCard(Players.Board.get(i), cardpoints);
                        didGetPoints = true;
                        break;
                    } else if (!wildcardControl && !cardControl && Players.Board.get(i).equals(card)) {
                        int points = Integer.parseInt(parts[1]);
                        getTotalPointCards += processCard(Players.Board.get(i), points);
                        didGetPoints = true;
                        break;
                    }


                }
                if (didGetPoints == false) {
                    getTotalPointCards += processCard(Players.Board.get(i), 1);
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTotalPointCards;
    }


    public static int processCard(String card, int points) {
        char suit = card.charAt(0);
        char cardface = card.charAt(1);

        // Update wildcard control variables if the current card is a wildcard
        if (cardface == '*') {
            wildcardControl = true;
            wildcardSuit = suit;
            wildcardPoints = points;
        }
        if (suit == '*') {
            cardControl = true;
            wildcardFace = cardface;
            cardpoints = points;
        }

        int cardPoints;
        if (wildcardControl && wildcardSuit == card.charAt(0)) {
            cardPoints = wildcardPoints;
        }
        if (cardControl && wildcardFace == card.charAt(1)) {
            cardPoints = cardpoints;
        } else {
            switch (suit) {
                case 'S':
                    cardPoints = points;
                    break;
                case 'C':
                    cardPoints = points;
                    break;
                case 'H':
                    cardPoints = points;
                    break;
                case 'D':
                    cardPoints = points;
                    break;
                default:
                    cardPoints = 1;
                    break;
            }
        }

        // System.out.println(card + " - Points: " + cardPoints);
        return cardPoints;
    }
}
