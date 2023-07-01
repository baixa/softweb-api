package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.category.CategoryDto;
import com.softweb.api.store.model.dto.license.LicenseDto;
import com.softweb.api.store.model.dto.user.UserGetDto;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.utils.NumParser;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract Dto class are used as generation view for GET methods.
 * <p>
 * The class contains base information about Application object, its License and Category info and base data about author of this app
 * <p>
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Getter @Setter
public abstract class AbstractApplicationGetDto {
    /**
     * ID of application
     */
    private final Long id;
    /**
     * Name of application
     */
    private final String name;
    /**
     * Short description of application
     */
    private final String shortDescription;
    /**
     * Long description of application
     */
    private final String longDescription;
    /**
     * Logo path of application
     */
    private final String logoPath;
    /**
     * License DTO of application
     */
    private final LicenseDto license;
    /**
     * Category DTO of application
     */
    private final CategoryDto category;
    /**
     * Date and time of last update of application
     */
    private final int[] lastUpdate;
    /**
     * Downloads of application
     */
    private final int downloads;
    /**
     * Views of application
     */
    private final int views;
    /**
     * User DTO of application
     */
    private final UserGetDto user;

    /**
     * Constructor
     *
     * @param application Linked application
     */
    public AbstractApplicationGetDto(Application application) {
        this.id = application.getId();
        this.name = application.getName();
        this.shortDescription = application.getShortDescription();
        this.longDescription = application.getLongDescription();
        this.logoPath = application.getLogoPath();
        this.license = new LicenseDto(application.getLicense());
        this.lastUpdate = NumParser.parseDateToNumArray(application.getLastUpdate().plusHours(3));
        this.downloads = application.getDownloads();
        this.views = application.getViews();
        this.category = new CategoryDto(application.getCategory());
        this.user = new UserGetDto(application.getUser());
    }
}
