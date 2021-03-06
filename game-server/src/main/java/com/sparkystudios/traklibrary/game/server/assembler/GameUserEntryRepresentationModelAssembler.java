package com.sparkystudios.traklibrary.game.server.assembler;

import com.sparkystudios.traklibrary.game.domain.ImageSize;
import com.sparkystudios.traklibrary.game.server.controller.GameController;
import com.sparkystudios.traklibrary.game.server.controller.GameImageController;
import com.sparkystudios.traklibrary.game.server.controller.GameUserEntryController;
import com.sparkystudios.traklibrary.game.service.dto.GameUserEntryDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameUserEntryRepresentationModelAssembler implements SimpleRepresentationModelAssembler<GameUserEntryDto> {

    @Override
    public void addLinks(EntityModel<GameUserEntryDto> resource) {
        GameUserEntryDto content = resource.getContent();

        if (content != null) {
            resource.add(WebMvcLinkBuilder.linkTo(methodOn(GameUserEntryController.class).findById(content.getId()))
                    .withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(methodOn(GameController.class).findById(content.getGameId()))
                    .withRel("game"));
            resource.add(linkTo(methodOn(GameController.class).findGameDetailsByGameId(content.getGameId()))
                    .withRel("game_details"));
            resource.add(linkTo(methodOn(GameImageController.class).findGameImageByGameIdAndImageSize(content.getGameId(), ImageSize.SMALL))
                    .withRel("small_image"));
            resource.add(linkTo(methodOn(GameImageController.class).findGameImageByGameIdAndImageSize(content.getGameId(), ImageSize.MEDIUM))
                    .withRel("medium_image"));
            resource.add(linkTo(methodOn(GameImageController.class).findGameImageByGameIdAndImageSize(content.getGameId(), ImageSize.LARGE))
                    .withRel("large_image"));
        }
    }

    @Override
    public void addLinks(@NonNull CollectionModel<EntityModel<GameUserEntryDto>> resources) {
        // Unused. Additional resource links aren't added to collections.
    }
}
