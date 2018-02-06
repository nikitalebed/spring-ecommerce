package lebed.ecommerce.controller.rest;

import lebed.ecommerce.hateos.GroupResource;
import lebed.ecommerce.model.product.ProductGroup;
import lebed.ecommerce.service.ECommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
public class GroupController implements CoreController {

    @Autowired
    private ECommerceService eCommerceService;

    @GetMapping
    public List<GroupResource> index() {
        return eCommerceService.getGroups().stream().map(group -> {
            GroupResource groupResource = new GroupResource(group);
            groupResource.add(createHateoasLink(group.getId()));
            return groupResource;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GroupResource view(@PathVariable("id") long id){
        GroupResource groupResource = new GroupResource(eCommerceService.getGroup(id));
        groupResource.add(createHateoasLink(id));
        return groupResource;
    }

    @PostMapping(value = "/{id}")
    public ProductGroup edit(@PathVariable(value = "id", required = false) long id, @RequestBody @Valid ProductGroup group) {
        ProductGroup updatedGroup = eCommerceService.getGroup(id);
        if (updatedGroup == null) {
            return null;
        }
        updatedGroup.setGroupName(group.getGroupName());
        updatedGroup.setPrice(group.getPrice());
        updatedGroup.setGroupVariants(group.getGroupVariants());
        if (updatedGroup.getGroupVariants() != null) {
            updatedGroup.getGroupVariants().forEach(gv -> gv.setGroup(updatedGroup));
        }
        return eCommerceService.saveGroup(updatedGroup);
    }

    @PostMapping
    public ProductGroup create(@RequestBody @Valid ProductGroup group) {
        if (group.getGroupVariants() != null) {
            group.getGroupVariants().forEach(gv -> gv.setGroup(group));
        }
        return eCommerceService.saveGroup(group);
    }

}
