package com.traklibrary.game.service.mapper;

import com.traklibrary.game.domain.Developer;
import com.traklibrary.game.service.dto.DeveloperDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {

    DeveloperDto developerToDeveloperDto(Developer developer);

    @Mapping(target = "gameDeveloperXrefs", ignore = true)
    Developer developerDtoToDeveloper(DeveloperDto developerDto);
}