
public enum AboutExcerpts {

    ABOUT_GAME("Blokus is a 4-player strategy game (thought it sometimes can be played with "
            + "fewer players). Each player has 21 pieces of a certain color and can place them on "
            + "a 20 x 20 grid: the caveat, however, is that each player can only place his/her "
            + "pieces diagonal to those of their color already on the board, or in other words, "
            + "no two pieces can share a common edge, but only corners. The winner is the player "
            + "who has placed the most of his/her pieces on the board. Score is kept by "
            + "counting the number of squares among all pieces the player has. Therefore, the"
            + " player with the lowest score wins."),
    GAME_INSTRUCTIONS("Click on a piece below to select it; then place it on the board by simply "
            + "clicking where on the board you wish to place the piece. You can move the piece "
            + "around on the board after having placed it by dragging your mouse. In case you "
            + "wish to use a different piece instead "
            + "click on that particular piece and follow the same process. Once you have finalized "
            + "your move, click \"Next Turn\". In case you wish to rotate the piece you have "
            + "selected, click \"Rotate\", and you will be able to see the piece rotate on the "
            + "board itself. \n\nNOTE: \"Undo\" is meant for undoing finalized moves and not "
            + "replacing a piece placed on the board with another during the current "
            + "player's turn, which as mentioned can be done simply by reselecting another " 
            + "piece."),
    GAME_FEATURES("If the piece played is invalid in placement (e.g. it is touching another "
            + "piece of the same color along its edges/not touching a piece of the same color "
            + "along its corners, then the system will automatically move for you; it may not "
            + "be the best move, however. Also, if a player chooses to not play a piece at all "
            + "during his/her turn, the system will also automatically place a piece on the board. "
            + "This feature may come in handy towards the end of the game to ensure that all "
            + "players have maximized their score and no piece that can be played is unplayed.");

    private final String excerpt;

    private AboutExcerpts(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getExcerpt() {
        return excerpt;
    }
}
