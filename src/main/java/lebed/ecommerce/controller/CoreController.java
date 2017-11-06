package lebed.ecommerce.controller;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

interface CoreController {
    default Link createHateoasLink(long id) {
        return linkTo(getClass()).slash(id).withSelfRel();
    }
}
