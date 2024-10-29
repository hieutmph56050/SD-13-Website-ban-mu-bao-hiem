package com.example.saferide.service;

import com.example.saferide.entity.ChatLieuVo;
import com.example.saferide.repository.ChatLieuVoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuVoService {
    @Autowired
    ChatLieuVoRepository chatlieuvoRepository;

    public List<ChatLieuVo> getList() {
        return chatlieuvoRepository.findAll();
    }

    public ChatLieuVo findById(Integer id) {
        return chatlieuvoRepository.findById(id).get();
    }

    public ChatLieuVo add(ChatLieuVo chatLieuVo) {
        return chatlieuvoRepository.save(chatLieuVo);
    }

    public ChatLieuVo update(ChatLieuVo chatLieuVo, Integer id) {
        Optional<ChatLieuVo> optional = chatlieuvoRepository.findById(id);
        return optional.map(chatLieuVo1 -> {
            chatLieuVo1.setMa(chatLieuVo.getMa());
            chatLieuVo1.setTen(chatLieuVo.getTen());
            chatLieuVo1.setMoTa(chatLieuVo.getMoTa());
            chatLieuVo1.setNguoiCapNhat(chatLieuVo.getTt());
            chatLieuVo1.setNgayCapNhat(LocalDateTime.now());
            chatLieuVo1.setTt(chatLieuVo.getTt());
            return chatlieuvoRepository.save(chatLieuVo1);
        }).orElse(null);
    }
    public ChatLieuVo delete(Integer id) {
        Optional<ChatLieuVo> optional = chatlieuvoRepository.findById(id);
        return optional.map(chatLieuVo1 -> {
            chatlieuvoRepository.delete(chatLieuVo1);
            return chatLieuVo1;
        }).orElse(null);
    }
}
