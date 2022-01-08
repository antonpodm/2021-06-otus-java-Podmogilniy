package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.crm.repository.GoodRepository;
import ru.otus.crm.service.DBServiceEmail;
import ru.otus.crm.service.DBServiceGood;
import ru.otus.crm.service.DBServiceProfile;
import ru.otus.dto.EmailDto;
import ru.otus.dto.GoodDto;
import ru.otus.dto.ProfileDto;
import ru.otus.exceptions.ProfileNotFoundException;

import java.util.List;

import static ru.otus.toolbox.RestControllerHelper.*;

@Controller()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestApiController {

    private static final Logger log = LoggerFactory.getLogger(RestApiController.class);

    private final DBServiceEmail dbServiceEmail;
    private final DBServiceGood dbServiceGood;
    private final DBServiceProfile dbServiceProfile;
    private final GoodRepository goodRepository;

    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileDto>> getProfiles() {
        return new ResponseEntity<>(makeProfilesDto(dbServiceProfile.findAll()), HttpStatus.OK);
    }

    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long profileId) {
        return new ResponseEntity<>(new ProfileDto(dbServiceProfile.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId))), HttpStatus.OK);
    }

    @PostMapping("/profiles")
    public ResponseEntity<List<ProfileDto>> addProfile(@RequestBody @NotNull ProfileDto profileDto) {
        //TODO зависит от веба
        dbServiceProfile.save(profileDto.toProfile());
        return new ResponseEntity<>(makeProfilesDto(dbServiceProfile.findAll()), HttpStatus.OK);
    }

    @DeleteMapping("/profiles/{profileId}")
    public ResponseEntity<List<ProfileDto>> deleteProfile(@PathVariable Long profileId) {
        dbServiceProfile.deleteById(profileId);
        return new ResponseEntity<>(makeProfilesDto(dbServiceProfile.findAll()), HttpStatus.OK);
    }

    @GetMapping("/profiles/{profileId}/emails")
    public ResponseEntity<List<EmailDto>> getEmails(@PathVariable Long profileId) {
        return new ResponseEntity<>(makeEmailsDto(dbServiceEmail.findByProfileId(profileId)), HttpStatus.OK);
    }

    @PostMapping("/profiles/{profileId}/emails")
    public ResponseEntity<List<EmailDto>> addEmail(@PathVariable Long profileId, @RequestBody @NotNull EmailDto emailDto) {
        //TODO зависит от веба
        emailDto.setProfileId(profileId);
        dbServiceEmail.save(emailDto.toEmail());
        return new ResponseEntity<>(makeEmailsDto(dbServiceEmail.findByProfileId(profileId)), HttpStatus.CREATED);
    }

    @DeleteMapping("/profiles/{profileId}/emails/{emailId}")
    public ResponseEntity<List<EmailDto>> deleteEmail(@PathVariable Long profileId, @PathVariable Long emailId) {
        dbServiceEmail.deleteById(emailId);
        return new ResponseEntity<>(makeEmailsDto(dbServiceEmail.findByProfileId(profileId)), HttpStatus.OK);
    }

    @GetMapping("/profiles/{profileId}/goods")
    public ResponseEntity<List<GoodDto>> getProfileGoods(@PathVariable Long profileId) {
        return new ResponseEntity<>(makeGoodsDto(dbServiceGood.findByProfileId(profileId)), HttpStatus.OK);
    }

    @PostMapping("/profiles/{profileId}/goods")
    public ResponseEntity<List<GoodDto>> addGood(@PathVariable Long profileId, @RequestBody @NotNull GoodDto goodDto) {
        //TODO зависит от веба
        goodDto.setProfileId(profileId);
        dbServiceGood.save(goodDto.toGood());
        return new ResponseEntity<>(makeGoodsDto(dbServiceGood.findByProfileId(profileId)), HttpStatus.CREATED);
    }

    @DeleteMapping("/profiles/{profileId}/goods/{goodId}")
    public ResponseEntity<List<GoodDto>> deleteGood(@PathVariable Long profileId, @PathVariable Long goodId) {
        dbServiceGood.deleteById(goodId);
        return new ResponseEntity<>(makeGoodsDto(dbServiceGood.findByProfileId(profileId)), HttpStatus.OK);
    }

}
