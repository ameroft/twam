package ame.ameroft.twom.net.packets;

import ame.ameroft.twom.net.Client;

public class Packet02Move extends Packet {

    private String username;
    private float x, y;

    private int dir = 1;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        
        this.username = dataArray[0].substring(2);
        this.x = Float.parseFloat(dataArray[1]);
        this.y = Float.parseFloat(dataArray[2]);
        this.dir = Integer.parseInt(dataArray[3]);
    }

    public Packet02Move(String username, float x, float y, int dir) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }


    @Override
    public void writeData(Client client) {
    	   client.sendData(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y  + "," + this.dir).getBytes();

    }

    public String getUsername() {
        return username;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getMovingDir() {
        return dir;
    }


}
