package com.sparkystudios.traklibrary.game.service.mapper;

import com.sparkystudios.traklibrary.game.domain.Developer;
import com.sparkystudios.traklibrary.game.service.dto.DeveloperDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DeveloperMapperImpl.class,
})
class DeveloperMapperTest {

    @Autowired
    private DeveloperMapper developerMapper;

    @Test
    void fromDeveloper_withNull_returnsNull() {
        // Act
        DeveloperDto result = developerMapper.fromDeveloper(null);

        // Assert
        Assertions.assertThat(result).isNull();
    }

    @Test
    void fromDeveloper_withDeveloper_mapsFields() {
        // Arrange
        Developer developer = new Developer();
        developer.setId(5L);
        developer.setName("test-name");
        developer.setDescription("test-description");
        developer.setFoundedDate(LocalDate.now());
        developer.setCreatedAt(LocalDateTime.now());
        developer.setUpdatedAt(LocalDateTime.now());
        developer.setVersion(1L);

        // Act
        DeveloperDto result = developerMapper.fromDeveloper(developer);

        // Assert
        Assertions.assertThat(result.getId()).isEqualTo(developer.getId());
        Assertions.assertThat(result.getName()).isEqualTo(developer.getName());
        Assertions.assertThat(result.getDescription()).isEqualTo(developer.getDescription());
        Assertions.assertThat(result.getFoundedDate()).isEqualTo(developer.getFoundedDate());
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(developer.getCreatedAt());
        Assertions.assertThat(result.getUpdatedAt()).isEqualTo(developer.getUpdatedAt());
        Assertions.assertThat(result.getVersion()).isEqualTo(developer.getVersion());
    }

    @Test
    void toDeveloper_withNull_returnsNull() {
        // Act
        Developer result = developerMapper.toDeveloper(null);

        // Assert
        Assertions.assertThat(result).isNull();
    }

    @Test
    void toDeveloper_withDeveloperDto_mapsFields() {
        // Arrange
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(5L);
        developerDto.setName("Test Name");
        developerDto.setDescription("test-description");
        developerDto.setFoundedDate(LocalDate.now());
        developerDto.setCreatedAt(LocalDateTime.now());
        developerDto.setUpdatedAt(LocalDateTime.now());
        developerDto.setVersion(1L);

        // Act
        Developer result = developerMapper.toDeveloper(developerDto);

        // Assert
        Assertions.assertThat(result.getId()).isEqualTo(developerDto.getId());
        Assertions.assertThat(result.getName()).isEqualTo(developerDto.getName());
        Assertions.assertThat(result.getDescription()).isEqualTo(developerDto.getDescription());
        Assertions.assertThat(result.getFoundedDate()).isEqualTo(developerDto.getFoundedDate());
        Assertions.assertThat(result.getSlug()).isEqualTo("test-name");
        Assertions.assertThat(result.getCreatedAt()).isNull();
        Assertions.assertThat(result.getUpdatedAt()).isNull();
        Assertions.assertThat(result.getVersion()).isEqualTo(developerDto.getVersion());
    }
}
