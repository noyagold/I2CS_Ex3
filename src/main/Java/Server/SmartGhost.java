package Server;

import exe.ex3.game.GhostCL;

public class SmartGhost implements GhostCL {
    private int type;
    private String currentPos;
    private int status;

    public SmartGhost(int type, String startPos) {
        this.type = type;
        this.currentPos = startPos;
        this.status = 1; // 1 = Alive/Dangerous
    }

    @Override
    public int getType() { return this.type; }

    @Override
    public String getPos(int tick) { return this.currentPos; }

    public void setPos(String p) { this.currentPos = p; }

    @Override
    public String getInfo() { return ""; }

    @Override
    public double remainTimeAsEatable(int tick) { return 0; }

    @Override
    public int getStatus() { return this.status; }
}