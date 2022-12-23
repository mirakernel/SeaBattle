package seaBattle.game;

public class Cell {
		public int x;
		public int y;
		
		char[] abc;
		
		public Cell(int x, int y) {
			abc = "јЅ¬√ƒ≈∆«» 000".toCharArray();
			try {
			if(!(x<0||x>9))
			this.x = x;
		else {throw new Exception("Ѕарьер или точка по x вылез за пределы пол€");}
			if(!(y<0||y>9))
			this.y = y;
		else {throw new Exception("Ѕарьер или точка по y вылез за пределы пол€");}
			}
			catch(Exception e) {
				this.x = 11;
				this.y = 11;
			}
		}
		
		@Override
		public int hashCode() {
			if(this.x != 11&&this.y != 11)
				return toString().hashCode();
			else
				return "ME".hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			Cell notThisPoint = (Cell) o;
			return this.x == notThisPoint.x && this.y == notThisPoint.y;
		}
		
		@Override
		public String toString() {
			return abc[x]+""+(y+1);
		}
		
	}
	
