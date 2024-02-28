package com.cs206.g2t2.exceptions.badRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class RoleChangeNotAllowedException extends BadRequestException {

    private static final long serialVersionUID = 1L;

    public static final String PROMOTION = "Promotion";
    public static final String DEMOTION = "Demotion";

    public RoleChangeNotAllowedException(String roleChange, String userRole, String memberRole) {
        super(roleChange + " not allowed as user is " + userRole + " and member is " + memberRole);
    }
}
