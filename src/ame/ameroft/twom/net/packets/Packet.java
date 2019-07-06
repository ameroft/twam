package ame.ameroft.twom.net.packets;

import ame.ameroft.twom.net.Client;

public abstract class Packet {
	public static enum PacketTypes{
		INVALID(-1),LOGIN(00),DISCONNECT(01), MOVE(02);

		private int packetID;

		private PacketTypes(int packetId) {
			this.packetID = packetId;
		}
		public int getId() {
			return packetID;
		}
	}
	public byte packetID;

	public Packet(int packetID) {
		this.packetID = (byte)packetID;
	}
	public Packet() {

	}

	public abstract void writeData(Client client);

	public String readData(byte[] data) {
		String message = new String(data).trim();
	
		return message;
	}

	public static PacketTypes lookupPacket(int id) {
		for(PacketTypes p:PacketTypes.values()) {
			if(p.getId() == id) {
				return p;
			}

		}
		return PacketTypes.INVALID;
	}
	public static PacketTypes lookupPacket(String packetID) {
		try {
			return lookupPacket(Integer.parseInt(packetID));
		}
		catch(NumberFormatException e){
			return PacketTypes.INVALID;
		}
	}
	public abstract  byte[] getData();


}
