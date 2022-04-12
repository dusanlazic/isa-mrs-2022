package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.TagCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.advertisement.Tag;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return tagRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        Tag tag = tagRepository.findById(id).orElseThrow();

        return modelMapper.map(tag, returnType);
    }

    public Tag create(TagCreationDTO dto) {
        Tag tag = modelMapper.map(dto, Tag.class);

        tagRepository.save(tag);
        return tag;
    }
}
