package seaBattle.game;

public class EnemyCell extends Cell {
    private boolean isShip;
    private String result;
    private String name;
    private int deck;
    public Cell difference;
    public int hp;
    public String position;

    public EnemyCell(int x, int y) {
        super(x, y);
    }

    public EnemyCell(int x, int y, boolean isShip, String name, String result, int deck, Cell sp, int hp, String position) {
        super(x, y);
        this.isShip = isShip;
        this.result = result;
        this.name = name;
        this.deck = deck;
        difference = sp;
        this.hp = hp;
        this.position = position;
    }

    public int getX() {
        return difference.x;
    }

    public int getY() {
        return difference.y;
    }

    public String getPosition() {
        return position;
    }

    public int getHp() {
        return hp;
    }

    public EnemyCell(int x, int y, boolean isShip) {
        super(x, y);
        this.isShip = isShip;
    }

    public void setIsShip(boolean isShip) {
        this.isShip = isShip;
    }

    public boolean getIsShip() {
        return isShip;
    }

    public int getDeck() {
        return deck;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeck(int deck) {
        this.deck = deck;
    }

    public String send() {
        return toString() + "/" + isShip + "/" + name + "/" + result + "/" + deck + "/" + difference.x + "/" + difference.y + "/" + hp + "/" + position;
    }

    public void setupImage() {
    }
}
