package chess.pieces;


public class Queen extends AbstractChessPiece {

	public char getSymbol() {
		return 'Q';
	}

	public boolean canAttackPosition(int column, int row) {
		if(Math.abs(getRow() - row) == Math.abs(getColumn() - column))
			return true;
		if(getRow() == row || getColumn() == column)
			return true;
		return false;
	}
}