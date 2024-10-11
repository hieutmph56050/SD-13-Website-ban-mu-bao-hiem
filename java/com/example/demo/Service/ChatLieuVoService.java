package com.example.demo.Service;

import com.example.demo.Entity.ChatLieuVo;
import com.example.demo.Repository.ChatLieuVoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void delete(Integer id) {
        chatlieuvoRepository.deleteById(id);
    }

    public ChatLieuVo add(ChatLieuVo chatLieuVo) {
        return chatlieuvoRepository.save(chatLieuVo);
    }
    public ChatLieuVo update(ChatLieuVo chatLieuVo) {
        return chatlieuvoRepository.save(chatLieuVo);
    }
}
