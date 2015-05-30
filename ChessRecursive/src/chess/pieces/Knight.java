package chess.pieces;



public class Knight extends AbstractChessPiece {

	public char getSymbol() {
		return 'N';
	}

	public boolean canAttackPosition(int column, int row) {
		int drow = Math.abs(getRow() - row);
		int dcolumn = Math.abs(getColumn() - column);
		if((drow == 2 && dcolumn == 1) || (drow == 1 && dcolumn == 2))
			return true;
		return false;
	}

}
