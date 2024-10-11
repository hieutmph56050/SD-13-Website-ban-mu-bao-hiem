package com.example.demo.Service;

import com.example.demo.Entity.VaiTro;
import com.example.demo.Repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaiTroService {
    @Autowired
    VaiTroRepository vaitroRepository;

    public List<VaiTro> getList() {
        return vaitroRepository.findAll();
    }

    public VaiTro findById(Integer id) {
        return vaitroRepository.findById(id).get();
    }

    public void delete(Integer id) {
        vaitroRepository.deleteById(id);
    }

    public VaiTro add(VaiTro vaiTro) {
        return vaitroRepository.save(vaiTro);
    }

    public VaiTro update(VaiTro vaiTro) {
        return vaitroRepository.save(vaiTro);
    }
}
