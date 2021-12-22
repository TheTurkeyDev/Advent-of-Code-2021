package dev.theturkey.aoc2021;

import java.util.*;

public class Day21 extends AOCPuzzle {
    public Day21() {
        super("21");
    }

    private Map<GameState, long[]> cache;

    @Override
    void solve(List<String> input) {
        int player1Start = Integer.parseInt(input.get(0).substring(28));
        int player2Start = Integer.parseInt(input.get(1).substring(28));
        cache = new HashMap<>();

        //PART 1
        int diceValue = 1;
        int rolls = 0;
        int player1Score = 0;
        int player1Pawn = player1Start;
        int player2Score = 0;
        int player2Pawn = player2Start;

        while (player1Score < 1000 && player2Score < 1000) {
            player1Pawn += (diceValue++) + (diceValue++) + (diceValue++);
            rolls += 3;
            while (player1Pawn > 10)
                player1Pawn -= 10;
            player1Score += player1Pawn;

            if (player1Score >= 1000)
                continue;

            player2Pawn += (diceValue++) + (diceValue++) + (diceValue++);
            rolls += 3;
            while (player2Pawn > 10)
                player2Pawn -= 10;
            player2Score += player2Pawn;
        }

        lap(rolls * (long) (Math.min(player1Score, player2Score)));
        long[] wins = new long[]{0, 0};
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 1; k < 4; k++) {
                    long[] winsRoll = playGame(true, i + j + k, player1Start, 0, player2Start, 0);
                    wins[0] += winsRoll[0];
                    wins[1] += winsRoll[1];
                }
            }
        }

        lap(Math.max(wins[0], wins[1]));
    }

    public long[] playGame(boolean player1, int roll, int player1Pawn, int player1Score, int player2Pawn, int player2Score) {
        GameState state = new GameState(player1, roll, player1Pawn, player1Score, player2Pawn, player2Score);
        if(cache.containsKey(state))
            return cache.get(state);

        if (player1) {
            player1Pawn += roll;
            while (player1Pawn > 10)
                player1Pawn -= 10;
            player1Score += player1Pawn;
            if (player1Score >= 21)
                return new long[]{1, 0};
        } else {
            player2Pawn += roll;
            while (player2Pawn > 10)
                player2Pawn -= 10;
            player2Score += player2Pawn;
            if (player2Score >= 21)
                return new long[]{0, 1};
        }

        long[] wins = new long[]{0, 0};
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 1; k < 4; k++) {
                    long[] winsRoll = playGame(!player1, i + j + k, player1Pawn, player1Score, player2Pawn, player2Score);
                    wins[0] += winsRoll[0];
                    wins[1] += winsRoll[1];
                }
            }
        }

        cache.put(state, wins);

        return wins;
    }

    private static class GameState {
        public boolean player1;
        public int roll;
        public int player1Pawn;
        public int player1Score;
        public int player2Pawn;
        public int player2Score;

        public GameState(boolean player1, int roll, int player1Pawn, int player1Score, int player2Pawn, int player2Score) {
            this.player1 = player1;
            this.roll = roll;
            this.player1Pawn = player1Pawn;
            this.player1Score = player1Score;
            this.player2Pawn = player2Pawn;
            this.player2Score = player2Score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return player1 == gameState.player1 && roll == gameState.roll && player1Pawn == gameState.player1Pawn && player1Score == gameState.player1Score && player2Pawn == gameState.player2Pawn && player2Score == gameState.player2Score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(player1, roll, player1Pawn, player1Score, player2Pawn, player2Score);
        }
    }
}
