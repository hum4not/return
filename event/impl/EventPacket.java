package org.returnclient.event.impl;

import net.minecraft.network.Packet;
import org.returnclient.event.Event;

public class EventPacket extends Event {

    public Packet packet;
    public boolean sending;

    public EventPacket(Packet packet, boolean sending) {
        this.packet = packet;
        this.sending = sending;
    }

    public boolean isSending() {
        return this.sending;
    }

    public boolean isRecieving() {
        return !this.sending;
    }

    public Packet getPacket() {
        return packet ;
    }
}
