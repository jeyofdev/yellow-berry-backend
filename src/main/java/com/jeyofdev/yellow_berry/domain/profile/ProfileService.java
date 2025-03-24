package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.auth_user.AuthUserService;
import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import com.jeyofdev.yellow_berry.exception.AlreadyTakenException;
import com.jeyofdev.yellow_berry.exception.NotFoundException;
import com.jeyofdev.yellow_berry.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class ProfileService extends AbstractDomainService<Profile, ProfileRepository> {
    private final ProfileRepository profileRepository;
    private final AuthUserService authUserService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, AuthUserService authUserService) {
        super(profileRepository, "Profile");
        this.profileRepository = profileRepository;
        this.authUserService = authUserService;
    }

    public Profile findByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Profile not found for user with id " + userId));
    }

    public Profile save(UUID userId, Profile profile) {
        AuthUser user = authUserService.findUserById(userId);
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(user.getUsername(), false);

        if (user.getProfile() != null) {
            throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "user", "profile"));
        }

        if (profileRepository.existsByPhone(profile.getPhone())) {
            throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "phone", profile.getPhone()));
        }

        profile.setUser(user);

        return profileRepository.save(profile);
    }

    public Profile updateById(UUID profileId, Profile updatedProfile) {
        Profile existingProfile = findById(profileId);
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(existingProfile.getUser().getUsername(), false);

        existingProfile.setFirstname(updatedProfile.getFirstname() != null ? updatedProfile.getFirstname() : existingProfile.getFirstname());
        existingProfile.setLastname(updatedProfile.getLastname() != null ? updatedProfile.getLastname() : existingProfile.getLastname());
        existingProfile.setPhone(updatedProfile.getPhone() != null ? updatedProfile.getPhone() : existingProfile.getPhone());
        existingProfile.setAddress(updatedProfile.getAddress() != null ? updatedProfile.getAddress() : existingProfile.getAddress());
        existingProfile.setRegion(updatedProfile.getRegion() != null ? updatedProfile.getRegion() : existingProfile.getRegion());
        existingProfile.setDepartment(updatedProfile.getDepartment() != null ? updatedProfile.getDepartment() : existingProfile.getDepartment());
        existingProfile.setZipCode(updatedProfile.getZipCode() != null ? updatedProfile.getZipCode() : existingProfile.getZipCode());
        existingProfile.setCity(updatedProfile.getCity() != null ? updatedProfile.getCity() : existingProfile.getCity());

        return profileRepository.save(existingProfile);

    }

    public String deleteById(UUID profileId) {
        Profile profile = findById(profileId);
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(profile.getUser().getUsername(), true);
        profileRepository.deleteById(profileId);

        return MessageFormat.format(ConfirmMessage.CONFIRM_DELETE, "profile");

    }
}
