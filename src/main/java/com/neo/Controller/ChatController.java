package com.neo.Controller;

import com.neo.Constants;
import com.neo.DAO.ChatDAO;
import com.neo.DAO.UserDAO;
import com.neo.DatabaseModel.Message.Chat;
import com.neo.DatabaseModel.Users.User;
import com.neo.Model.RAdvertisement;
import com.neo.Model.RChatBox;
import com.neo.Model.RMessage;
import com.neo.Model.RUser;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by localadmin on 16/7/17.
 */

@RestController
@CrossOrigin(Constants.FRONT_END)
public class ChatController {

    private final ChatDAO chatDAO;
    private final UserDAO userDAO;
    private final SimpleDateFormat simpleDateFormat;

    @Autowired
    public ChatController(ChatDAO chatDAO, UserDAO userDAO) {
        this.chatDAO = chatDAO;
        this.userDAO = userDAO;
        simpleDateFormat=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public boolean chat(@RequestHeader("Authorization") String token, @RequestBody RMessage rChat){
        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return false;

        User receiver=userDAO.get(rChat.getReceiver());
        if(receiver==null) return false;

        Chat chat=new Chat(rChat.getMessage(), user, receiver);
        chatDAO.save(chat);
        return true;
    }

    @RequestMapping("/getChats")
    public List<RChatBox> getChats(@RequestHeader("Authorization") String token){
        List<RChatBox> rChatBoxes=new ArrayList<>();
        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return rChatBoxes;

        HashMap<String, RChatBox> rChatBoxHashSet=new HashMap<>();

        List<Chat> chats=chatDAO.getUnread(user.getId());
        for(Chat chat: chats){
            User sender=chat.getSender();
            //if(!sentByMe) rChat.setSender(new RUser(sender.getCoverage(), sender.getName()));

            RChatBox rChatBox;
            if(rChatBoxHashSet.containsKey(sender.getId())) rChatBox=rChatBoxHashSet.get(sender.getId());
            else{
                rChatBox=new RChatBox();
                rChatBox.setSender(new RUser(sender.getId(), sender.getName()));
                rChatBoxHashSet.put(sender.getId(), rChatBox);
            }
            boolean sentByMe=sender.getId().equals(user.getId());
            String time=simpleDateFormat.format(new Date(chat.getTimestamp()));
            rChatBox.addMessage(chat.getMessage(), sentByMe? "You: ":sender.getName()+": ", time);
        }

        for(Map.Entry<String, RChatBox> entry: rChatBoxHashSet.entrySet()){
            rChatBoxes.add(entry.getValue());
        }
        return rChatBoxes;
    }

    @RequestMapping("/readChat")
    public boolean readChat(@RequestHeader("Authorization") String token, String senderId){
        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return false;

        chatDAO.markRead(senderId, user.getId());
        return true;
    }
}
