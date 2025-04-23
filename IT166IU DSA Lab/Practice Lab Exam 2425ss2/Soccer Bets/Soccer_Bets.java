import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int c = scanner.nextInt(); // Number of test cases

        for (int t = 0; t < c; t++) {
            List<String[]> matches = new ArrayList<>();
            // Read 16 matches
            for (int i = 0; i < 16; i++) {
                String t1 = scanner.next();
                String t2 = scanner.next();
                int g1 = scanner.nextInt();
                int g2 = scanner.nextInt();
                matches.add(new String[]{t1, t2, String.valueOf(g1), String.valueOf(g2)});
            }
            // Find the winner
            String winner = findWinner(matches);
            System.out.println(winner);
        }
        scanner.close();
    }

    static String findWinner(List<String[]> matches) {
        // Track teams that lose (except in third-place match)
        Set<String> losers = new HashSet<>();
        String potentialFinalWinner = null;
        String potentialThirdPlaceLoser = null;
        int finalMatchGoalsDiff = Integer.MAX_VALUE;

        // Analyze all matches
        for (String[] match : matches) {
            String t1 = match[0];
            String t2 = match[1];
            int g1 = Integer.parseInt(match[2]);
            int g2 = Integer.parseInt(match[3]);

            if (g1 > g2) {
                losers.add(t2);
                // Check if this could be the final (lowest goal difference)
                int diff = Math.abs(g1 - g2);
                if (diff < finalMatchGoalsDiff) {
                    finalMatchGoalsDiff = diff;
                    potentialFinalWinner = t1;
                    potentialThirdPlaceLoser = t2;
                }
            } else {
                losers.add(t1);
                // Check if this could be the final (lowest goal difference)
                int diff = Math.abs(g1 - g2);
                if (diff < finalMatchGoalsDiff) {
                    finalMatchGoalsDiff = diff;
                    potentialFinalWinner = t2;
                    potentialThirdPlaceLoser = t1;
                }
            }
        }

        // The winner of the tournament is the team that never loses or wins the final
        for (String[] match : matches) {
            String t1 = match[0];
            String t2 = match[1];
            int g1 = Integer.parseInt(match[2]);
            int g2 = Integer.parseInt(match[3]);

            if (g1 > g2) {
                if (!losers.contains(t1) || t1.equals(potentialFinalWinner)) {
                    return t1;
                }
            } else {
                if (!losers.contains(t2) || t2.equals(potentialFinalWinner)) {
                    return t2;
                }
            }
        }

        return potentialFinalWinner; // Fallback to the final winner
    }
}