package dataSaving;

public class AppSaveData {

    //just a containers for game modes
    private final GameData snake = new GameData();
    private final GameData spaceInvaders = new GameData();

    public GameData getSnake(){ return snake; }
    public GameData getSpaceInvaders() { return spaceInvaders; }


}
