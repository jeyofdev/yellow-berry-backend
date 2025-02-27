package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.auth_user.AuthUserService;
import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
import com.jeyofdev.yellow_berry.exception.AlreadyTakenException;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
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

    public Profile save(UUID userId, Profile profile) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthUser user = authUserService.findUserById(userId);

        if (username.equals(user.getUsername())) {
            if (user.getProfile() != null) {
                throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "user", "profile"));
            }

            if (profileRepository.existsByPhone(profile.getPhone())) {
                throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "phone", profile.getPhone()));
            }

            profile.setUser(user);

            return profileRepository.save(profile);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public Profile updateById(UUID profileId, Profile updatedProfile) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile existingProfile = findById(profileId);

        if (username.equals(existingProfile.getUser().getUsername())) {
            existingProfile.setFirstname(updatedProfile.getFirstname() != null ? updatedProfile.getFirstname() : existingProfile.getFirstname());
            existingProfile.setLastname(updatedProfile.getLastname() != null ? updatedProfile.getLastname() : existingProfile.getLastname());
            existingProfile.setPhone(updatedProfile.getPhone() != null ? updatedProfile.getPhone() : existingProfile.getPhone());
            existingProfile.setAddress(updatedProfile.getAddress() != null ? updatedProfile.getAddress() : existingProfile.getAddress());
            existingProfile.setRegion(updatedProfile.getRegion() != null ? updatedProfile.getRegion() : existingProfile.getRegion());
            existingProfile.setDepartment(updatedProfile.getDepartment() != null ? updatedProfile.getDepartment() : existingProfile.getDepartment());
            existingProfile.setZipCode(updatedProfile.getZipCode() != null ? updatedProfile.getZipCode() : existingProfile.getZipCode());
            existingProfile.setCity(updatedProfile.getCity() != null ? updatedProfile.getCity() : existingProfile.getCity());

            return profileRepository.save(existingProfile);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public String deleteById(UUID profileId) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Profile profile = findById(profileId);

        if (username.equals(profile.getUser().getUsername()) || roles.equals("[ROLE_ADMIN]")) {
            profileRepository.deleteById(profileId);

            return ConfirmMessage.PROFILE_DELETE;
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }
}
