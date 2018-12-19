package the_biber_project.model;

import static the_biber_project.controller.WebSocketController.getCurrentTimeStamp;

public class Message {

    private MessageType type;
    private String content;
    private String sender;
    private String recepient;
    private String usercolor;
    private String time;

    public Message(){
        this.setTime(getCurrentTimeStamp());
    }
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getUsercolor() { return usercolor; }

    public void setUsercolor(String usercolor) {
        this.usercolor = usercolor;
    }

    public String getRecepient() {
        return recepient;
    }

    public void setRecepient(String recepient) {
        this.recepient = recepient;
    }
}