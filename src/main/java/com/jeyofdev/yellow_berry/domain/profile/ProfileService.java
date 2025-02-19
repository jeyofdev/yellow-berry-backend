package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService extends AbstractDomainService<Profile, ProfileRepository> {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        super(profileRepository, "Profile");
        this.profileRepository = profileRepository;
    }

    public Profile updateById(UUID profileId, Profile updatedProfile) {
        Profile existingProfile = findById(profileId);
        Profile existingProfileUpdated = Profile.builder()
                .id(profileId)
                .firstname(updatedProfile.getFirstname() != null ? updatedProfile.getFirstname() : existingProfile.getFirstname())
                .lastname(updatedProfile.getLastname() != null ? updatedProfile.getLastname() : existingProfile.getLastname())
                .phone(updatedProfile.getPhone() != null ? updatedProfile.getPhone() : existingProfile.getPhone())
                .address(updatedProfile.getAddress() != null ? updatedProfile.getAddress() : existingProfile.getAddress())
                .region(updatedProfile.getRegion() != null ? updatedProfile.getRegion() : existingProfile.getRegion())
                .department(updatedProfile.getDepartment() != null ? updatedProfile.getDepartment() : existingProfile.getDepartment())
                .zipCode(updatedProfile.getZipCode() != null ? updatedProfile.getZipCode() : existingProfile.getZipCode())
                .city(updatedProfile.getCity() != null ? updatedProfile.getCity() : existingProfile.getCity())
                .build();

        return profileRepository.save(existingProfileUpdated);
    }

    public String deleteById(UUID profileId) {
        findById(profileId);
        profileRepository.deleteById(profileId);

        return ConfirmMessage.PROFILE_DELETE;
    }
}
