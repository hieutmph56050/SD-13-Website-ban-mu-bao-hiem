package com.example.saferide.service;


import com.example.saferide.entity.ChatLieuDem;
import com.example.saferide.repository.ChatLieuDemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuDemService {
    @Autowired
    ChatLieuDemRepository chatlieudemRepository;

    public List<ChatLieuDem> getList() {
        return chatlieudemRepository.findAll();
    }

    public ChatLieuDem findById(Integer id) {
        return chatlieudemRepository.findById(id).get();
    }

    public void delete(Integer id) {
        chatlieudemRepository.deleteById(id);
    }

    public ChatLieuDem add(ChatLieuDem chatLieuDem) {
        return chatlieudemRepository.save(chatLieuDem);
    }
    public ChatLieuDem update(ChatLieuDem chatLieuDem) {
        return chatlieudemRepository.save(chatLieuDem);
    }
}
