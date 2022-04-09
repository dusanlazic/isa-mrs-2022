package com.team4.isamrs.service;

import com.team4.isamrs.dto.TagCreationDTO;
import com.team4.isamrs.model.entity.advertisement.Option;
import com.team4.isamrs.model.entity.advertisement.Tag;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Collection<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    public Long createTag(Tag tag) {
        try {
            tagRepository.save(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tag.getId();
    }
}
