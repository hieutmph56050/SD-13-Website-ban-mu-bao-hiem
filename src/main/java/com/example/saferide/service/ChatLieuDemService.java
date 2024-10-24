package com.example.saferide.service;

import com.example.saferide.entity.ChatLieuDem;
import com.example.saferide.repository.ChatLieuDemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public ChatLieuDem add(ChatLieuDem chatLieuDem) {
        return chatlieudemRepository.save(chatLieuDem);
    }

    public ChatLieuDem update(ChatLieuDem chatLieuDem, Integer id) {
        Optional<ChatLieuDem> optional = chatlieudemRepository.findById(id);
        return optional.map(chatLieuDem1 -> {
            chatLieuDem1.setMa(chatLieuDem.getMa());
            chatLieuDem1.setTen(chatLieuDem.getTen());
            chatLieuDem1.setMoTa(chatLieuDem.getMoTa());
            chatLieuDem1.setNguoiCapNhat(chatLieuDem.getNguoiCapNhat());
            chatLieuDem1.setNgayCapNhat(LocalDateTime.now());
            chatLieuDem1.setTt(chatLieuDem.getTt());
            return chatlieudemRepository.save(chatLieuDem1);
        }).orElse(null);
    }
    public ChatLieuDem delete(Integer id) {
        Optional<ChatLieuDem> optional = chatlieudemRepository.findById(id);
        return optional.map(chatLieuDem1 -> {
            chatlieudemRepository.delete(chatLieuDem1);
            return chatLieuDem1;
        }).orElse(null);
    }
}
