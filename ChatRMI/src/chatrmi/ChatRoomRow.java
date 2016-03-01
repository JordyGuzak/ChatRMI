/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jordy
 */
public class ChatRoomRow {

    SimpleStringProperty roomName;
    SimpleStringProperty roomCount;

    public ChatRoomRow(String roomName, int roomCount) {
        this.roomName = new SimpleStringProperty(roomName);
        this.roomCount = new SimpleStringProperty(String.valueOf(roomCount));
    }
    
    public String getRoomName()
    {
        return this.roomName.get();
    }
    
    public String getRoomCount()
    {
        return this.roomCount.get();
    }
}
